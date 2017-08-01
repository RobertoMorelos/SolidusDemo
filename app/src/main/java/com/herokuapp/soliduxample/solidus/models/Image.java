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

package com.herokuapp.soliduxample.solidus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Roberto Morelos on 3/6/17.
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