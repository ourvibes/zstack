//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.25 at 07:45:21 PM CST 
//


package org.zstack.test.deployer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConvergedOfferingConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConvergedOfferingConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{http://zstack.org/schema/zstack}InstanceOfferingConfig">
 *       &lt;sequence>
 *         &lt;element name="networkOutboundBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *         &lt;element name="volumeTotalBandwidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *         &lt;element name="volumeTotalIops" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConvergedOfferingConfig", propOrder = {
    "networkOutboundBandwidth",
    "volumeTotalBandwidth",
    "volumeTotalIops"
})
public class ConvergedOfferingConfig
    extends InstanceOfferingConfig
{

    @XmlSchemaType(name = "unsignedInt")
    protected Long networkOutboundBandwidth;
    @XmlSchemaType(name = "unsignedInt")
    protected Long volumeTotalBandwidth;
    @XmlSchemaType(name = "unsignedInt")
    protected Long volumeTotalIops;

    /**
     * Gets the value of the networkOutboundBandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNetworkOutboundBandwidth() {
        return networkOutboundBandwidth;
    }

    /**
     * Sets the value of the networkOutboundBandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNetworkOutboundBandwidth(Long value) {
        this.networkOutboundBandwidth = value;
    }

    /**
     * Gets the value of the volumeTotalBandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVolumeTotalBandwidth() {
        return volumeTotalBandwidth;
    }

    /**
     * Sets the value of the volumeTotalBandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVolumeTotalBandwidth(Long value) {
        this.volumeTotalBandwidth = value;
    }

    /**
     * Gets the value of the volumeTotalIops property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVolumeTotalIops() {
        return volumeTotalIops;
    }

    /**
     * Sets the value of the volumeTotalIops property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVolumeTotalIops(Long value) {
        this.volumeTotalIops = value;
    }

}
