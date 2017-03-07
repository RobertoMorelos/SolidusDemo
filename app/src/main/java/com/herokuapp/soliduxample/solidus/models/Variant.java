package com.herokuapp.soliduxample.solidus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by office on 3/6/17.
 *
 */
public class Variant implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("height")
    @Expose
    private Object height;
    @SerializedName("width")
    @Expose
    private Object width;
    @SerializedName("depth")
    @Expose
    private Object depth;
    @SerializedName("is_master")
    @Expose
    private Boolean isMaster;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("track_inventory")
    @Expose
    private Boolean trackInventory;
    @SerializedName("cost_price")
    @Expose
    private String costPrice;
    @SerializedName("option_values")
    @Expose
    private List<OptionValue> optionValues = null;
    @SerializedName("images")
    @Expose
    private List<Image_> images = null;
    @SerializedName("display_price")
    @Expose
    private String displayPrice;
    @SerializedName("options_text")
    @Expose
    private String optionsText;
    @SerializedName("in_stock")
    @Expose
    private Boolean inStock;
    @SerializedName("is_backorderable")
    @Expose
    private Boolean isBackorderable;
    @SerializedName("total_on_hand")
    @Expose
    private Integer totalOnHand;
    @SerializedName("is_destroyed")
    @Expose
    private Boolean isDestroyed;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }

    public Object getDepth() {
        return depth;
    }

    public void setDepth(Object depth) {
        this.depth = depth;
    }

    public Boolean getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Boolean isMaster) {
        this.isMaster = isMaster;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getTrackInventory() {
        return trackInventory;
    }

    public void setTrackInventory(Boolean trackInventory) {
        this.trackInventory = trackInventory;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public List<OptionValue> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<OptionValue> optionValues) {
        this.optionValues = optionValues;
    }

    public List<Image_> getImages() {
        return images;
    }

    public void setImages(List<Image_> images) {
        this.images = images;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getOptionsText() {
        return optionsText;
    }

    public void setOptionsText(String optionsText) {
        this.optionsText = optionsText;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Boolean getIsBackorderable() {
        return isBackorderable;
    }

    public void setIsBackorderable(Boolean isBackorderable) {
        this.isBackorderable = isBackorderable;
    }

    public Integer getTotalOnHand() {
        return totalOnHand;
    }

    public void setTotalOnHand(Integer totalOnHand) {
        this.totalOnHand = totalOnHand;
    }

    public Boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(Boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

}