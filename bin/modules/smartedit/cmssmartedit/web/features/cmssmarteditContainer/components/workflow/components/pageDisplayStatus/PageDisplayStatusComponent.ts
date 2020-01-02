/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
import {CrossFrameEventService, ISeComponent, IUriContext, SeComponent, TypedMap} from 'smarteditcommons';
import {ICMSPage, IPageService, ISyncStatus} from 'cmscommons';
import {WorkflowService} from '../../services/WorkflowService';
import {Workflow} from '../../dtos';
import {PageSynchronizationService} from 'cmssmarteditcontainer/dao/PageSynchronizationService';
import "./pageDisplayStatus.scss";

@SeComponent({
	templateUrl: 'pageDisplayStatusTemplate.html',
	inputs: ['pageUuid:?', 'uriContext:?']
})
export class PageDisplayStatusComponent implements ISeComponent {

	private pageUuid: string;
	private page: ICMSPage;
	private uriContext: IUriContext;
	private LOCALIZATION_PREFIX = "se.cms.page.displaystatus.";
	private currentStatusKey: string;
	private statusIconCssClass: string;
	private lastSynchedDate: number;

	private unRegPageSyncEvent: () => void;
	private unRegPageChangeEvent: () => void;

	constructor(
		private pageFacade: any,
		private pageService: IPageService,
		private workflowService: WorkflowService,
		private pageSynchronizationService: PageSynchronizationService,
		private crossFrameEventService: CrossFrameEventService,
		private $q: angular.IQService,
		private SYNCHRONIZATION_POLLING: TypedMap<string>,
		private EVENTS: TypedMap<string>
	) {}

	public $onInit() {
		this.updatePageInfo({
			uuid: this.pageUuid || null
		});
		if (this.pageUuid) {
			// In the page list there's no synchronization polling. Thus the sync status has to be retrieved manually when the component loads. 
			this.retrieveLastSynchedDate(this.pageUuid);
		}

		this.unRegPageSyncEvent = this.crossFrameEventService.subscribe(this.SYNCHRONIZATION_POLLING.FAST_FETCH, this.onPoll.bind(this));
		this.unRegPageChangeEvent = this.crossFrameEventService.subscribe(this.EVENTS.PAGE_UPDATED, this.updatePageInfo.bind(this));
	}

	public $onDestroy() {
		this.unRegPageSyncEvent();
		this.unRegPageChangeEvent();
	}

	public getCurrentStatusKey(): string {
		return this.currentStatusKey;
	}

	public getStatusIconCssClass(): string {
		return this.statusIconCssClass;
	}

	public isPageLockedToCurrentUser(pageInfo: ICMSPage): angular.IPromise<boolean> {
		const pageUuid = pageInfo.uuid;

		return this.workflowService.getActiveWorkflowForPageUuid(pageUuid).then((workflow: Workflow) => {
			return workflow && !workflow.isAvailableForCurrentPrincipal;
		});
	}

	public getTooltipTemplate(): string {
		if (this.hasBeenSynchedBefore()) {
			return `<div class="popover-tooltip">
                <span data-translate="se.cms.page.displaystatus.lastpublished.date"></span>
                <span>{{${this.lastSynchedDate} | date:'short'}}</span
            </div>`;
		} else {
			return `<div class="popover-tooltip">
                    <span data-translate="se.cms.page.displaystatus.not.published"></span>
                </div>`;
		}
	}

	public hasBeenSynchedBefore(): boolean {
		return this.lastSynchedDate !== undefined;
	}

	private onPoll(eventId: string, eventData: ISyncStatus): void {
		if (this.page && this.page.uuid === eventData.itemId) {
			this.lastSynchedDate = eventData.lastSyncStatus;
		}
	}

	private updatePageInfo(eventData: {uuid: string}): void {
		if (this.pageUuid && eventData.uuid !== this.pageUuid) {
			return;
		}
		this.getPage().then((pageInfo: ICMSPage) => {
			const displayStatus = (pageInfo.displayStatus) ? pageInfo.displayStatus.toLowerCase() : '';
			this.currentStatusKey = (displayStatus) ? this.LOCALIZATION_PREFIX + displayStatus : '';

			this.isPageLockedToCurrentUser(pageInfo).then((isPageLocked: boolean) => {
				if (isPageLocked) {
					this.statusIconCssClass = 'icon-locked se-page-status-locked__icon';
				} else {
					this.statusIconCssClass = `se-page-status-${displayStatus}__icon`;
				}
			});
		});
	}

	private getPage(): angular.IPromise<ICMSPage> {
		const promise = this.pageUuid ? this.pageService.getPageByUuid(this.pageUuid) : this.pageService.getCurrentPageInfo();
		return promise.then((page: ICMSPage) => {
			this.page = page;
			return page;
		});
	}

	private retrieveLastSynchedDate(pageUuid: string): void {
		this.getPageUriContext().then((uriContext: IUriContext) => {
			return this.pageSynchronizationService.getSyncStatus(pageUuid, uriContext).then((syncStatus: ISyncStatus) => {
				this.lastSynchedDate = syncStatus.lastSyncStatus;
			});
		});
	}

	private getPageUriContext(): angular.IPromise<IUriContext> {
		if (this.uriContext) {
			return this.$q.when(this.uriContext);
		}

		return this.pageFacade.retrievePageUriContext();
	}
}
