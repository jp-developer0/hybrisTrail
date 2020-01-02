//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.01.02 a las 02:28:46 PM ART 
//


package de.hybris.platform.processengine.definition.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para parameterUse.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="parameterUse"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="required"/&gt;
 *     &lt;enumeration value="optional"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "parameterUse")
@XmlEnum
public enum ParameterUse {

    @XmlEnumValue("required")
    REQUIRED("required"),
    @XmlEnumValue("optional")
    OPTIONAL("optional");
    private final String value;

    ParameterUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParameterUse fromValue(String v) {
        for (ParameterUse c: ParameterUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
