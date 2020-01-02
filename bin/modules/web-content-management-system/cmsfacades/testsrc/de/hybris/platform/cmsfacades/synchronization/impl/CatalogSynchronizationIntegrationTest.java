/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.cmsfacades.synchronization.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.enums.CmsApprovalStatus;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.contents.CMSItemModel;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;
import de.hybris.platform.cms2.model.contents.components.CMSParagraphComponentModel;
import de.hybris.platform.cms2.model.contents.contentslot.ContentSlotModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.restrictions.AbstractRestrictionModel;
import de.hybris.platform.cms2.model.restrictions.CMSTimeRestrictionModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.data.ContentSlotData;
import de.hybris.platform.cms2.servicelayer.services.admin.CMSAdminContentSlotService;
import de.hybris.platform.cms2.servicelayer.services.admin.CMSAdminItemService;
import de.hybris.platform.cms2.servicelayer.services.admin.CMSAdminSiteService;
import de.hybris.platform.cms2.systemsetup.CMSSearchRestrictionsSetup;
import de.hybris.platform.cms2lib.model.components.FlashComponentModel;
import de.hybris.platform.cmsfacades.data.SyncJobData;
import de.hybris.platform.cmsfacades.data.SyncRequestData;
import de.hybris.platform.cmsfacades.util.BaseIntegrationTest;
import de.hybris.platform.cmsfacades.util.models.CMSSiteModelMother;
import de.hybris.platform.cmsfacades.util.models.CMSTimeRestrictionModelMother;
import de.hybris.platform.cmsfacades.util.models.CatalogVersionModelMother;
import de.hybris.platform.cmsfacades.util.models.CatalogVersionSyncJobModelMother;
import de.hybris.platform.cmsfacades.util.models.ContentCatalogModelMother;
import de.hybris.platform.cmsfacades.util.models.ContentPageModelMother;
import de.hybris.platform.cmsfacades.util.models.ContentSlotForPageModelMother;
import de.hybris.platform.cmsfacades.util.models.ContentSlotForTemplateModelMother;
import de.hybris.platform.cmsfacades.util.models.FlashComponentModelMother;
import de.hybris.platform.cmsfacades.util.models.MediaModelMother;
import de.hybris.platform.cmsfacades.util.models.ParagraphComponentModelMother;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.CronJobService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static de.hybris.platform.cmsfacades.util.models.CatalogVersionModelMother.CatalogVersion.ONLINE;
import static de.hybris.platform.cmsfacades.util.models.CatalogVersionModelMother.CatalogVersion.STAGED;
import static de.hybris.platform.cmsfacades.util.models.ContentCatalogModelMother.CatalogTemplate.ID_APPLE;
import static de.hybris.platform.cmsfacades.util.models.ContentPageModelMother.UID_HOMEPAGE;
import static de.hybris.platform.cmsfacades.util.models.ContentSlotModelMother.UID_FOOTER;
import static de.hybris.platform.cmsfacades.util.models.ContentSlotModelMother.UID_HEADER;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CatalogSynchronizationIntegrationTest extends BaseIntegrationTest
{
    private final String RESTRICTION_UID = CMSTimeRestrictionModelMother.UID_TODAY;
    private final String BANNER_COMPONENT_ID = FlashComponentModelMother.UID_FOOTER;
    private final String COMPONENT_IN_SLOT_FOR_PAGE = ParagraphComponentModelMother.UID_HEADER;

    @Resource
    private DefaultSynchronizationFacade defaultSynchronizationFacade;

    @Resource
    private CatalogVersionSyncJobModelMother catalogVersionSyncJobModelMother;

    @Resource
    private ContentCatalogModelMother contentCatalogModelMother;

    @Resource
    private CMSSiteModelMother cmsSiteModelMother;

    @Resource
    private CatalogVersionModelMother catalogVersionModelMother;

    @Resource
    private ContentPageModelMother contentPageModelMother;

    @Resource
    private ContentSlotForPageModelMother contentSlotForPageModelMother;

    @Resource
    private ContentSlotForTemplateModelMother contentSlotForTemplateModelMother;

    @Resource
    private CMSTimeRestrictionModelMother timeRestrictionModelMother;

    @Resource
    private MediaModelMother mediaModelMother;

    @Resource
    private UserService userService;

    @Resource
    private TypeService typeService;

    @Resource
    private ModelService modelService;

    @Resource
    private CronJobService cronJobService;

    @Resource
    private FlexibleSearchService flexibleSearchService;

    @Resource
    private CMSAdminSiteService cmsAdminSiteService;

    @Resource
    private CMSAdminItemService cmsAdminItemService;

    @Resource
    private CMSAdminContentSlotService cmsAdminContentSlotService;

    private CatalogVersionModel stagedCatalogVersion;
    private CatalogVersionModel onlineCatalogVersion;

    @Before
    public void setUp()
    {
        final CMSSearchRestrictionsSetup searchRestrictionsSetup = new CMSSearchRestrictionsSetup();
        searchRestrictionsSetup.setTypeService(typeService);
        searchRestrictionsSetup.setUserService(userService);
        searchRestrictionsSetup.setModelService(modelService);
        searchRestrictionsSetup.setFlexibleSearchService(flexibleSearchService);
        searchRestrictionsSetup.createSyncSearchRestrictions();

        contentCatalogModelMother.createContentCatalogModelFromTemplate(ID_APPLE);

        stagedCatalogVersion = catalogVersionModelMother.createAppleStagedCatalogVersionModel();
        onlineCatalogVersion = catalogVersionModelMother.createAppleOnlineCatalogVersionModel();
        final CMSSiteModel site = cmsSiteModelMother.createSiteWithTemplate(CMSSiteModelMother.TemplateSite.ELECTRONICS, stagedCatalogVersion, onlineCatalogVersion);

        cmsAdminSiteService.setActiveSite(site);
    }

    @Test
    public void givenNewPageIsInDraft_WhenCatalogSyncIsPerformed_ThenPageRelatedContentShouldNotBeSynced()
    {
        // GIVEN
        final ContentPageModel stagedPage = createEmptyPage(stagedCatalogVersion);
        stagedPage.setApprovalStatus(CmsApprovalStatus.UNAPPROVED);
        modelService.save(stagedPage);

        // WHEN
        performCatalogSynchronization();

        // THEN
        assertNull(getCmsItemById(UID_HOMEPAGE, onlineCatalogVersion, ContentPageModel.class));
        assertNull(getCmsItemById(UID_HEADER, onlineCatalogVersion, ContentSlotModel.class));

        // - This is a shared slot. Thus, it must be synced even if the page is not.
        assertNotNull(getCmsItemById(UID_FOOTER, onlineCatalogVersion, ContentSlotModel.class));
    }

    @Test
    public void givenNewPageIsApproved_WhenCatalogSyncIsPerformed_ThenPageShouldBeSynced()
    {
        // GIVEN
        final ContentPageModel stagedPage = createEmptyPage(stagedCatalogVersion);
        approvePage(stagedPage);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final ContentPageModel onlinePage = getCmsItemById(UID_HOMEPAGE, onlineCatalogVersion, ContentPageModel.class);
        final Collection<String> contentSlotsForPage = cmsAdminContentSlotService.getContentSlotsForPage(onlinePage, true)
                .stream().map(ContentSlotData::getUid).collect(Collectors.toList());

        assertNotNull(onlinePage);
        assertNotNull(getCmsItemById(UID_HEADER, onlineCatalogVersion, ContentSlotModel.class));
        assertNotNull(getCmsItemById(UID_FOOTER, onlineCatalogVersion, ContentSlotModel.class));
        assertThat(contentSlotsForPage, contains(UID_HEADER, UID_FOOTER));
    }

    @Test
    public void givenExistingPageIsInDraft_WhenCatalogSyncIsPerformed_ThenPageShouldNotBeSynced()
    {
        // GIVEN
        final String newLabel = "This is a new label for the page";
        final ContentPageModel stagedPage = createEmptyPage(stagedCatalogVersion);
        stagedPage.setLabel(newLabel);
        modelService.save(stagedPage);

        createEmptyPage(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final ContentPageModel onlinePage = getCmsItemById(UID_HOMEPAGE, onlineCatalogVersion, ContentPageModel.class);
        assertThat(onlinePage.getLabel(), not(newLabel));
    }

    @Test
    public void givenExistingPageIsApproved_WhenCatalogSyncIsPerformed_ThenPageShouldBeSynced()
    {
        // GIVEN
        final String newLabel = "This is a new label for the page";
        final ContentPageModel stagedPage = createEmptyPage(stagedCatalogVersion);
        stagedPage.setLabel(newLabel);
        approvePage(stagedPage);

        createEmptyPage(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final ContentPageModel onlinePage = getCmsItemById(UID_HOMEPAGE, onlineCatalogVersion, ContentPageModel.class);
        assertThat(onlinePage.getLabel(), is(newLabel));
    }

    @Test
    public void givenComponentUsedInDraftPage_WhenCatalogSyncIsPerformed_ThenComponentShouldNotBeSynced()
    {
        // GIVEN
        final String componentId = ParagraphComponentModelMother.UID_HEADER;
        final String newContent = "This is the new content for the paragraph";

        final ContentPageModel stagedPage = createPageWithComponentInPageSlot(stagedCatalogVersion);
        setParagraphContent(componentId, newContent);
        unApprovePage(stagedPage); // Page should be un-approved anyways.

        createPageWithComponentInPageSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSParagraphComponentModel onlineParagraph = getCmsItemById(componentId, onlineCatalogVersion, CMSParagraphComponentModel.class);
        assertThat(onlineParagraph.getContent(), not(newContent));
    }

    @Test
    public void givenComponentUsedInApprovedPage_WhenCatalogSyncIsPerformed_ThenComponentShouldBeSynced()
    {
        // GIVEN
        final String componentId = ParagraphComponentModelMother.UID_HEADER;
        final String newContent = "This is the new content for the paragraph";

        final ContentPageModel stagedPage = createPageWithComponentInPageSlot(stagedCatalogVersion);
        setParagraphContent(componentId, newContent);
        approvePage(stagedPage);

        createPageWithComponentInPageSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSParagraphComponentModel onlineParagraph = getCmsItemById(componentId, onlineCatalogVersion, CMSParagraphComponentModel.class);
        assertThat(onlineParagraph.getContent(), is(newContent));
    }

    @Test
    public void givenComponentUsedInSharedSlot_WhenCatalogSyncIsPerformed_ThenComponentShouldBeSynced()
    {
        // GIVEN
        final String componentId = ParagraphComponentModelMother.UID_FOOTER;
        final String newContent = "This is the new content for the paragraph";

        final ContentPageModel stagedPage = createPageWithComponentInSharedSlot(stagedCatalogVersion);
        setParagraphContent(componentId, newContent);
        unApprovePage(stagedPage); // Page should be un-approved anyways.

        createPageWithComponentInSharedSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSParagraphComponentModel onlineParagraph = getCmsItemById(componentId, onlineCatalogVersion, CMSParagraphComponentModel.class);
        assertThat(onlineParagraph.getContent(), is(newContent));
    }

    @Test
    public void givenComponentUsedInSharedSlotAndDraftPage_WhenCatalogSyncIsPerformed_ThenComponentShouldNotBeSynced()
    {
        // GIVEN
        final String componentId = ParagraphComponentModelMother.UID_SHARED;
        final String newContent = "This is the new content for the paragraph";

        final ContentPageModel stagedPage = createPageWithSharedComponent(stagedCatalogVersion);
        setParagraphContent(componentId, newContent);
        unApprovePage(stagedPage); // Page should be un-approved anyways.

        createPageWithSharedComponent(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSParagraphComponentModel onlineParagraph = getCmsItemById(componentId, onlineCatalogVersion, CMSParagraphComponentModel.class);
        assertThat(onlineParagraph.getContent(), not(newContent));
    }

    @Test
    public void givenComponentUsedInSharedSlotAndApprovedPage_WhenCatalogSyncIsPerformed_ThenComponentShouldBeSynced()
    {
        // GIVEN
        final String componentId = ParagraphComponentModelMother.UID_SHARED;
        final String newContent = "This is the new content for the paragraph";

        final ContentPageModel stagedPage = createPageWithSharedComponent(stagedCatalogVersion);
        setParagraphContent(componentId, newContent);
        approvePage(stagedPage); // Page should be un-approved anyways.

        createPageWithSharedComponent(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSParagraphComponentModel onlineParagraph = getCmsItemById(componentId, onlineCatalogVersion, CMSParagraphComponentModel.class);
        assertThat(onlineParagraph.getContent(), is(newContent));
    }

    @Test
	public void givenRestrictionUsedInDraftPage_WhenCatalogSyncIsPerformed_ThenRestrictionShouldNotBeSynced()
	{
        // GIVEN
        final ContentPageModel stagedPage = createEmptyPage(stagedCatalogVersion);
        addRestrictionToPage(stagedPage);
        unApprovePage(stagedPage);

        createEmptyPage(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSTimeRestrictionModel onlineRestriction = getCmsItemById(RESTRICTION_UID, onlineCatalogVersion, CMSTimeRestrictionModel.class);
        assertNull(onlineRestriction);
	}

	@Test
	public void givenRestrictionUsedInApprovedPage_WhenCatalogSyncIsPerformed_ThenRestrictionShouldBeSynced()
	{
        // GIVEN
        final ContentPageModel stagedPage = createEmptyPage(stagedCatalogVersion);
        addRestrictionToPage(stagedPage);
        approvePage(stagedPage);

        createEmptyPage(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSTimeRestrictionModel onlineRestriction = getCmsItemById(RESTRICTION_UID, onlineCatalogVersion, CMSTimeRestrictionModel.class);
        assertNotNull(onlineRestriction);

        final List<String> restrictions = getPageRestrictionsIds();
        assertThat(restrictions, contains(RESTRICTION_UID));
    }

    @Test
	public void givenRestrictionUsedInUnapprovedComponent_WhenCatalogSyncIsPerformed_ThenRestrictionShouldNotBeSynced()
	{
        // GIVEN
        final ContentPageModel stagedPage = createPageWithComponentInPageSlot(stagedCatalogVersion);
        final CMSParagraphComponentModel stagedParagraph = getCmsItemById(COMPONENT_IN_SLOT_FOR_PAGE, stagedCatalogVersion, CMSParagraphComponentModel.class);
        addRestrictionToComponent(stagedParagraph);
        unApprovePage(stagedPage);

        createPageWithComponentInPageSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSTimeRestrictionModel onlineRestriction = getCmsItemById(RESTRICTION_UID, onlineCatalogVersion, CMSTimeRestrictionModel.class);
        assertNull(onlineRestriction);
	}

	@Test
	public void givenRestrictionUsedInApprovedComponent_WhenCatalogSyncIsPerformed_ThenRestrictionShouldNotBeSynced()
	{
        // GIVEN
        final ContentPageModel stagedPage = createPageWithComponentInPageSlot(stagedCatalogVersion);
        final CMSParagraphComponentModel stagedParagraph = getCmsItemById(COMPONENT_IN_SLOT_FOR_PAGE, stagedCatalogVersion, CMSParagraphComponentModel.class);
        addRestrictionToComponent(stagedParagraph);
        approvePage(stagedPage);

        createPageWithComponentInPageSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final CMSTimeRestrictionModel onlineRestriction = getCmsItemById(RESTRICTION_UID, onlineCatalogVersion, CMSTimeRestrictionModel.class);
        assertNotNull(onlineRestriction);

        final List<String> restrictions = getComponentRestrictionsIds();
        assertThat(restrictions, contains(RESTRICTION_UID));
	}

	@Test
	public void givenBannerInDraftPage_WhenCatalogSyncIsPerformed_ThenBannerShouldNotBeSynced()
	{
        // GIVEN
        final ContentPageModel stagedPage = createPageWithBannerInPageSlot(stagedCatalogVersion);
        changeBannerMedia();
        unApprovePage(stagedPage);

        createPageWithBannerInPageSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final FlashComponentModel onlineComponent = getCmsItemById(BANNER_COMPONENT_ID, onlineCatalogVersion, FlashComponentModel.class);
        assertThat(onlineComponent.getMedia().getCode(), not(MediaModelMother.MediaTemplate.THUMBNAIL.getCode()));
	}

	@Test
	public void givenBannerInApprovedPage_WhenCatalogSyncIsPerformed_ThenBannerShouldBeSynced()
	{
        // GIVEN
        final ContentPageModel stagedPage = createPageWithBannerInPageSlot(stagedCatalogVersion);
        changeBannerMedia();
        approvePage(stagedPage);

        createPageWithBannerInPageSlot(onlineCatalogVersion);

        // WHEN
        performCatalogSynchronization();

        // THEN
        final FlashComponentModel onlineComponent = getCmsItemById(BANNER_COMPONENT_ID, onlineCatalogVersion, FlashComponentModel.class);
        assertThat(onlineComponent.getMedia().getCode(), is(MediaModelMother.MediaTemplate.THUMBNAIL.getCode()));
	}

    protected SyncJobData performCatalogSynchronization()
    {
        final SyncRequestData syncRequestData = buildSyncRequestData();

        final SyncJobData data = defaultSynchronizationFacade.createCatalogSynchronization(syncRequestData);
        final CronJobModel cronJob = cronJobService.getCronJob(data.getCode());

        int attempts = 0;
        int maxAttempts = 5;
        do {
            attempts++;
            try
            {
                Thread.sleep(1000);
                modelService.refresh(cronJob);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        } while (!cronJobService.isFinished(cronJob) && attempts <= maxAttempts );

        if (attempts > maxAttempts)
        {
            throw new Error("Couldn't complete the catalog synchronization job");
        }

        return data;
    }

    protected SyncRequestData buildSyncRequestData()
    {
        final SyncRequestData syncRequestData = new SyncRequestData();
        syncRequestData.setTargetVersionId(ONLINE.getVersion());
        syncRequestData.setSourceVersionId(STAGED.getVersion());
        syncRequestData.setCatalogId(ID_APPLE.name());
        catalogVersionSyncJobModelMother.createAppleSyncJobFromStagedToOnline();
        userService.getCurrentUser()
                .setWritableCatalogVersions(Arrays.asList(onlineCatalogVersion));

        return syncRequestData;
    }

    protected ContentPageModel createEmptyPage(final CatalogVersionModel catalogVersion)
    {
        final ContentPageModel homepage = contentPageModelMother.homePage(catalogVersion);
        contentSlotForPageModelMother.HeaderHomepage_Empty(catalogVersion);
        contentSlotForTemplateModelMother.FooterHomepage(catalogVersion);

        return homepage;
    }

    protected ContentPageModel createPageWithComponentInPageSlot(final CatalogVersionModel catalogVersion)
    {
        final ContentPageModel homepage = contentPageModelMother.homePage(catalogVersion);
        contentSlotForPageModelMother.HeaderHomepage_ParagraphOnly(catalogVersion);
        contentSlotForTemplateModelMother.FooterHomepage(catalogVersion);

        return homepage;
    }

    protected ContentPageModel createPageWithBannerInPageSlot(final CatalogVersionModel catalogVersion)
    {
        final ContentPageModel homepage = contentPageModelMother.homePage(catalogVersion);

        // This one is using the footer flash component since that one has media.
        contentSlotForPageModelMother.FooterHomepage_FlashComponentOnly(catalogVersion);

        return homepage;
    }

    protected ContentPageModel createPageWithComponentInSharedSlot(final CatalogVersionModel catalogVersion)
    {
        final ContentPageModel homepage = contentPageModelMother.homePage(catalogVersion);
        contentSlotForPageModelMother.HeaderHomepage_Empty(catalogVersion);
        contentSlotForTemplateModelMother.FooterHomepage_ParagraphOnly(catalogVersion);

        return homepage;
    }

    protected ContentPageModel createPageWithSharedComponent(final CatalogVersionModel catalogVersion)
    {
        final ContentPageModel homepage = contentPageModelMother.homePage(catalogVersion);
        contentSlotForPageModelMother.HeaderHomepage_SharedParagraphOnly(catalogVersion);
        contentSlotForTemplateModelMother.FooterHomepage_SharedParagraphOnly(catalogVersion);

        return homepage;
    }

    protected void addRestrictionToPage(final ContentPageModel page)
    {
        timeRestrictionModelMother.createTodayRestrictionAndAssignToPages(stagedCatalogVersion, page);
    }

    protected void addRestrictionToComponent(final AbstractCMSComponentModel component)
    {
        timeRestrictionModelMother.createTodayRestrictionAndAssignToComponents(stagedCatalogVersion, component);
    }

    protected void approvePage(final AbstractPageModel pageModel)
    {
        pageModel.setApprovalStatus(CmsApprovalStatus.APPROVED);
        modelService.save(pageModel);
    }

    protected void unApprovePage(final AbstractPageModel pageModel)
    {
        pageModel.setApprovalStatus(CmsApprovalStatus.CHECK);
        modelService.save(pageModel);
    }

    protected void setParagraphContent(final String componentId, final String newContent)
    {
        final CMSParagraphComponentModel stagedParagraph = getCmsItemById(componentId, stagedCatalogVersion, CMSParagraphComponentModel.class);
        stagedParagraph.setContent(newContent);
        modelService.save(stagedParagraph);
    }

    protected void changeBannerMedia()
    {
        final FlashComponentModel banner = getCmsItemById(BANNER_COMPONENT_ID, stagedCatalogVersion, FlashComponentModel.class);
        final MediaModel thumbnail = mediaModelMother.createThumbnailMediaModel(stagedCatalogVersion);

        banner.setMedia(thumbnail);
        modelService.save(banner);
    }

    protected <T extends CMSItemModel> T getCmsItemById(final String itemUid, final CatalogVersionModel catalogVersion, Class<T> clazz)
    {
        try
        {
            return clazz.cast(cmsAdminItemService.findByUid(itemUid, catalogVersion));
        }
        catch (final CMSItemNotFoundException exception)
        {
            return null;
        }
    }

    protected List<String> getPageRestrictionsIds()
    {
        final ContentPageModel onlinePage = getCmsItemById(UID_HOMEPAGE, onlineCatalogVersion, ContentPageModel.class);

        return onlinePage.getRestrictions().stream()
                .map(AbstractRestrictionModel::getUid)
                .collect(Collectors.toList());
    }

    protected List<String> getComponentRestrictionsIds()
    {
        final CMSParagraphComponentModel onlineParagraph = getCmsItemById(COMPONENT_IN_SLOT_FOR_PAGE, onlineCatalogVersion, CMSParagraphComponentModel.class);
        return onlineParagraph.getRestrictions().stream()
                .map(AbstractRestrictionModel::getUid)
                .collect(Collectors.toList());
    }

}
