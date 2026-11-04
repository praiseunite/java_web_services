package com.aptech.session8;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product_input")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"name", "priceNgn", "category"})
public class XmlProductInput {
    private String name;
    private Integer priceNgn;
    private String category;

    public XmlProductInput() { }

    @XmlElement(name = "name", required = true)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement(name = "price_ngn", required = true)
    public Integer getPriceNgn() { return priceNgn; }
    public void setPriceNgn(Integer priceNgn) { this.priceNgn = priceNgn; }

    @XmlElement(name = "category", required = true)
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
