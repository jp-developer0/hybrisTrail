//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.01.02 a las 02:28:46 PM ART 
//


package de.hybris.platform.processengine.definition.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para wait complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="wait"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="event" type="{http://www.hybris.de/xsd/processdefinition}name"/&gt;
 *           &lt;element name="case" type="{http://www.hybris.de/xsd/processdefinition}case"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="timeout" type="{http://www.hybris.de/xsd/processdefinition}timeout" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.hybris.de/xsd/processdefinition}nodeAttributes"/&gt;
 *       &lt;attribute name="then" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *       &lt;attribute name="prependProcessCode" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wait", propOrder = {
    "event",
    "_case",
    "timeout"
})
public class Wait {

    protected String event;
    @XmlElement(name = "case")
    protected Case _case;
    protected Timeout timeout;
    @XmlAttribute(name = "then")
    protected String then;
    @XmlAttribute(name = "prependProcessCode")
    protected Boolean prependProcessCode;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Obtiene el valor de la propiedad event.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvent() {
        return event;
    }

    /**
     * Define el valor de la propiedad event.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvent(String value) {
        this.event = value;
    }

    /**
     * Obtiene el valor de la propiedad case.
     * 
     * @return
     *     possible object is
     *     {@link Case }
     *     
     */
    public Case getCase() {
        return _case;
    }

    /**
     * Define el valor de la propiedad case.
     * 
     * @param value
     *     allowed object is
     *     {@link Case }
     *     
     */
    public void setCase(Case value) {
        this._case = value;
    }

    /**
     * Obtiene el valor de la propiedad timeout.
     * 
     * @return
     *     possible object is
     *     {@link Timeout }
     *     
     */
    public Timeout getTimeout() {
        return timeout;
    }

    /**
     * Define el valor de la propiedad timeout.
     * 
     * @param value
     *     allowed object is
     *     {@link Timeout }
     *     
     */
    public void setTimeout(Timeout value) {
        this.timeout = value;
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
     * Obtiene el valor de la propiedad prependProcessCode.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPrependProcessCode() {
        if (prependProcessCode == null) {
            return true;
        } else {
            return prependProcessCode.booleanValue();
        }
    }

    /**
     * Define el valor de la propiedad prependProcessCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrependProcessCode(Boolean value) {
        this.prependProcessCode = value;
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
