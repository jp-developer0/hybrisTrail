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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="contextParameter" type="{http://www.hybris.de/xsd/processdefinition}contextParameter" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;group ref="{http://www.hybris.de/xsd/processdefinition}nodes" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *       &lt;attribute name="start" use="required" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *       &lt;attribute name="onError" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *       &lt;attribute name="processClass" type="{http://www.hybris.de/xsd/processdefinition}name" default="de.hybris.platform.processengine.model.BusinessProcessModel" /&gt;
 *       &lt;attribute name="defaultNodeGroup" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "contextParameter",
    "nodes"
})
@XmlRootElement(name = "process")
public class Process {

    protected List<ContextParameter> contextParameter;
    @XmlElements({
        @XmlElement(name = "action", type = Action.class),
        @XmlElement(name = "scriptAction", type = ScriptAction.class),
        @XmlElement(name = "split", type = Split.class),
        @XmlElement(name = "wait", type = Wait.class),
        @XmlElement(name = "end", type = End.class),
        @XmlElement(name = "join", type = Join.class),
        @XmlElement(name = "notify", type = Notify.class)
    })
    protected List<Object> nodes;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "start", required = true)
    protected String start;
    @XmlAttribute(name = "onError")
    protected String onError;
    @XmlAttribute(name = "processClass")
    protected String processClass;
    @XmlAttribute(name = "defaultNodeGroup")
    protected String defaultNodeGroup;

    /**
     * Gets the value of the contextParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contextParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContextParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContextParameter }
     * 
     * 
     */
    public List<ContextParameter> getContextParameter() {
        if (contextParameter == null) {
            contextParameter = new ArrayList<ContextParameter>();
        }
        return this.contextParameter;
    }

    /**
     * Gets the value of the nodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Action }
     * {@link ScriptAction }
     * {@link Split }
     * {@link Wait }
     * {@link End }
     * {@link Join }
     * {@link Notify }
     * 
     * 
     */
    public List<Object> getNodes() {
        if (nodes == null) {
            nodes = new ArrayList<Object>();
        }
        return this.nodes;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad start.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStart() {
        return start;
    }

    /**
     * Define el valor de la propiedad start.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * Obtiene el valor de la propiedad onError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnError() {
        return onError;
    }

    /**
     * Define el valor de la propiedad onError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnError(String value) {
        this.onError = value;
    }

    /**
     * Obtiene el valor de la propiedad processClass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessClass() {
        if (processClass == null) {
            return "de.hybris.platform.processengine.model.BusinessProcessModel";
        } else {
            return processClass;
        }
    }

    /**
     * Define el valor de la propiedad processClass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessClass(String value) {
        this.processClass = value;
    }

    /**
     * Obtiene el valor de la propiedad defaultNodeGroup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultNodeGroup() {
        return defaultNodeGroup;
    }

    /**
     * Define el valor de la propiedad defaultNodeGroup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultNodeGroup(String value) {
        this.defaultNodeGroup = value;
    }

}
