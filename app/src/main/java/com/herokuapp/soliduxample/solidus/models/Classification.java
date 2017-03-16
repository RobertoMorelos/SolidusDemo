package com.herokuapp.soliduxample.solidus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Roberto Morelos on 3/6/17.
 *
 */
public class Classification implements Serializable {

    @SerializedName("taxon_id")
    @Expose
    private Integer taxonId;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("taxon")
    @Expose
    private Taxon taxon;

    public Integer getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(Integer taxonId) {
        this.taxonId = taxonId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }
}