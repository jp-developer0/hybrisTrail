//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.01.02 a las 02:28:46 PM ART 
//


package de.hybris.platform.processengine.definition.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para notify complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="notify"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userGroup" type="{http://www.hybris.de/xsd/processdefinition}userGroupType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.hybris.de/xsd/processdefinition}nodeAttributes"/&gt;
 *       &lt;attribute name="then" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notify", propOrder = {
    "userGroup"
})
public class Notify {

    @XmlElement(required = true)
    protected List<UserGroupType> userGroup;
    @XmlAttribute(name = "then")
    protected String then;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Gets the value of the userGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserGroupType }
     * 
     * 
     */
    public List<UserGroupType> getUserGroup() {
        if (userGroup == null) {
            userGroup = new ArrayList<UserGroupType>();
        }
        return this.userGroup;
    }

    /**
     * Obtiene el valor de la propiedad then.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThen() {
        return then;
    }

    /**
     * Define el valor de la propiedad then.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThen(String value) {
        this.then = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
