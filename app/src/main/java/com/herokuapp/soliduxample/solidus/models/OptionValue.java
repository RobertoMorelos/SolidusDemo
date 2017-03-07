package com.herokuapp.soliduxample.solidus.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by office on 3/6/17.
 */

public class OptionValue implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("presentation")
    @Expose
    private String presentation;
    @SerializedName("option_type_name")
    @Expose
    private String optionTypeName;
    @SerializedName("option_type_id")
    @Expose
    private Integer optionTypeId;
    @SerializedName("option_type_presentation")
    @Expose
    private String optionTypePresentation;

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

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getOptionTypeName() {
        return optionTypeName;
    }

    public void setOptionTypeName(String optionTypeName) {
        this.optionTypeName = optionTypeName;
    }

    public Integer getOptionTypeId() {
        return optionTypeId;
    }

    public void setOptionTypeId(Integer optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    public String getOptionTypePresentation() {
        return optionTypePresentation;
    }

    public void setOptionTypePresentation(String optionTypePresentation) {
        this.optionTypePresentation = optionTypePresentation;
    }

}