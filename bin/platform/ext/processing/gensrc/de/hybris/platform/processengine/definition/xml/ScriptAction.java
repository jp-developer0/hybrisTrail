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
 * <p>Clase Java para scriptAction complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="scriptAction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="script" type="{http://www.hybris.de/xsd/processdefinition}script"/&gt;
 *         &lt;element name="parameter" type="{http://www.hybris.de/xsd/processdefinition}parameter" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="transition" type="{http://www.hybris.de/xsd/processdefinition}transition" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.hybris.de/xsd/processdefinition}nodeAttributes"/&gt;
 *       &lt;attribute name="node" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="nodeGroup" type="{http://www.hybris.de/xsd/processdefinition}name" /&gt;
 *       &lt;attribute name="canJoinPreviousNode" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scriptAction", propOrder = {
    "script",
    "parameter",
    "transition"
})
public class ScriptAction {

    @XmlElement(required = true)
    protected Script script;
    protected List<Parameter> parameter;
    @XmlElement(required = true)
    protected List<Transition> transition;
    @XmlAttribute(name = "node")
    protected Integer node;
    @XmlAttribute(name = "nodeGroup")
    protected String nodeGroup;
    @XmlAttribute(name = "canJoinPreviousNode")
    protected Boolean canJoinPreviousNode;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Obtiene el valor de la propiedad script.
     * 
     * @return
     *     possible object is
     *     {@link Script }
     *     
     */
    public Script getScript() {
        return script;
    }

    /**
     * Define el valor de la propiedad script.
     * 
     * @param value
     *     allowed object is
     *     {@link Script }
     *     
     */
    public void setScript(Script value) {
        this.script = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Parameter }
     * 
     * 
     */
    public List<Parameter> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<Parameter>();
        }
        return this.parameter;
    }

    /**
     * Gets the value of the transition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Transition }
     * 
     * 
     */
    public List<Transition> getTransition() {
        if (transition == null) {
            transition = new ArrayList<Transition>();
        }
        return this.transition;
    }

    /**
     * Obtiene el valor de la propiedad node.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNode() {
        return node;
    }

    /**
     * Define el valor de la propiedad node.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNode(Integer value) {
        this.node = value;
    }

    /**
     * Obtiene el valor de la propiedad nodeGroup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeGroup() {
        return nodeGroup;
    }

    /**
     * Define el valor de la propiedad nodeGroup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeGroup(String value) {
        this.nodeGroup = value;
    }

    /**
     * Obtiene el valor de la propiedad canJoinPreviousNode.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCanJoinPreviousNode() {
        return canJoinPreviousNode;
    }

    /**
     * Define el valor de la propiedad canJoinPreviousNode.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCanJoinPreviousNode(Boolean value) {
        this.canJoinPreviousNode = value;
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
