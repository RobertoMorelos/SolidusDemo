package com.herokuapp.soliduxample.solidus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Roberto Morelos on 3/6/17.
 *
 */
public class Taxon implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pretty_name")
    @Expose
    private String prettyName;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("taxonomy_id")
    @Expose
    private Integer taxonomyId;
    @SerializedName("taxons")
    @Expose
    private List<Object> taxons = null;

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

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(Integer taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public List<Object> getTaxons() {
        return taxons;
    }

    public void setTaxons(List<Object> taxons) {
        this.taxons = taxons;
    }

}