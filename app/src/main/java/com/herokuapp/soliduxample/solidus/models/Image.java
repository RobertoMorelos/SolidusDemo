package com.herokuapp.soliduxample.solidus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by office on 3/6/17.
 *
 */
public class Image implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("attachment_content_type")
    @Expose
    private String attachmentContentType;
    @SerializedName("attachment_file_name")
    @Expose
    private String attachmentFileName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attachment_updated_at")
    @Expose
    private String attachmentUpdatedAt;
    @SerializedName("attachment_width")
    @Expose
    private Integer attachmentWidth;
    @SerializedName("attachment_height")
    @Expose
    private Integer attachmentHeight;
    @SerializedName("alt")
    @Expose
    private Object alt;
    @SerializedName("viewable_type")
    @Expose
    private String viewableType;
    @SerializedName("viewable_id")
    @Expose
    private Integer viewableId;
    @SerializedName("mini_url")
    @Expose
    private String miniUrl;
    @SerializedName("small_url")
    @Expose
    private String smallUrl;
    @SerializedName("product_url")
    @Expose
    private String productUrl;
    @SerializedName("large_url")
    @Expose
    private String largeUrl;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttachmentUpdatedAt() {
        return attachmentUpdatedAt;
    }

    public void setAttachmentUpdatedAt(String attachmentUpdatedAt) {
        this.attachmentUpdatedAt = attachmentUpdatedAt;
    }

    public Integer getAttachmentWidth() {
        return attachmentWidth;
    }

    public void setAttachmentWidth(Integer attachmentWidth) {
        this.attachmentWidth = attachmentWidth;
    }

    public Integer getAttachmentHeight() {
        return attachmentHeight;
    }

    public void setAttachmentHeight(Integer attachmentHeight) {
        this.attachmentHeight = attachmentHeight;
    }

    public Object getAlt() {
        return alt;
    }

    public void setAlt(Object alt) {
        this.alt = alt;
    }

    public String getViewableType() {
        return viewableType;
    }

    public void setViewableType(String viewableType) {
        this.viewableType = viewableType;
    }

    public Integer getViewableId() {
        return viewableId;
    }

    public void setViewableId(Integer viewableId) {
        this.viewableId = viewableId;
    }

    public String getMiniUrl() {
        return miniUrl;
    }

    public void setMiniUrl(String miniUrl) {
        this.miniUrl = miniUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

}