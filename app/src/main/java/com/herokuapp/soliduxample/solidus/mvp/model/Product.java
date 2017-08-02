/*
* MIT License
*
* Copyright (c) 2017 Roberto Morelos
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

package com.herokuapp.soliduxample.solidus.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Roberto Morelos on 3/6/17.
 *
 */
public class Product implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("display_price")
    @Expose
    private String displayPrice;
    @SerializedName("available_on")
    @Expose
    private String availableOn;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("meta_description")
    @Expose
    private Object metaDescription;
    @SerializedName("meta_keywords")
    @Expose
    private Object metaKeywords;
    @SerializedName("shipping_category_id")
    @Expose
    private Integer shippingCategoryId;
    @SerializedName("taxon_ids")
    @Expose
    private List<Integer> taxonIds = null;
    @SerializedName("total_on_hand")
    @Expose
    private Integer totalOnHand;
    @SerializedName("master")
    @Expose
    private Master master;
    @SerializedName("variants")
    @Expose
    private List<Master> variants = null;
    @SerializedName("option_types")
    @Expose
    private List<OptionType> optionTypes = null;
    @SerializedName("product_properties")
    @Expose
    private List<ProductProperty> productProperties = null;
    @SerializedName("classifications")
    @Expose
    private List<Classification> classifications = null;
    @SerializedName("has_variants")
    @Expose
    private Boolean hasVariants;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getAvailableOn() {
        return availableOn;
    }

    public void setAvailableOn(String availableOn) {
        this.availableOn = availableOn;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Object getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(Object metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Object getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(Object metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public Integer getShippingCategoryId() {
        return shippingCategoryId;
    }

    public void setShippingCategoryId(Integer shippingCategoryId) {
        this.shippingCategoryId = shippingCategoryId;
    }

    public List<Integer> getTaxonIds() {
        return taxonIds;
    }

    public void setTaxonIds(List<Integer> taxonIds) {
        this.taxonIds = taxonIds;
    }

    public Integer getTotalOnHand() {
        return totalOnHand;
    }

    public void setTotalOnHand(Integer totalOnHand) {
        this.totalOnHand = totalOnHand;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public List<Master> getVariants() {
        return variants;
    }

    public void setVariants(List<Master> variants) {
        this.variants = variants;
    }

    public List<OptionType> getOptionTypes() {
        return optionTypes;
    }

    public void setOptionTypes(List<OptionType> optionTypes) {
        this.optionTypes = optionTypes;
    }

    public List<ProductProperty> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(List<ProductProperty> productProperties) {
        this.productProperties = productProperties;
    }

    public List<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<Classification> classifications) {
        this.classifications = classifications;
    }

    public Boolean getHasVariants() {
        return hasVariants;
    }

    public void setHasVariants(Boolean hasVariants) {
        this.hasVariants = hasVariants;
    }

}