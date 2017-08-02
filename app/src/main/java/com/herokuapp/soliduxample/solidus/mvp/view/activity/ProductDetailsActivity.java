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

package com.herokuapp.soliduxample.solidus.mvp.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.herokuapp.soliduxample.solidus.R;
import com.herokuapp.soliduxample.solidus.api.Config;
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.mvp.model.Classification;
import com.herokuapp.soliduxample.solidus.mvp.model.Image;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;
import com.herokuapp.soliduxample.solidus.mvp.model.ProductProperty;
import com.herokuapp.soliduxample.solidus.mvp.model.Variant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roberto Morelos on 3/6/17.
 * This activity displays the detailed information of one product.
 */
public class ProductDetailsActivity extends AppCompatActivity {
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private ImageView ivProduct;
    private LinearLayout llProperties;
    private TableLayout tlTableProperties;
    private TextView tvName;
    private TextView tvDescription;
    private LinearLayout llVariants;
    private RadioGroup rgVariants;
    private TextView tvPrice;
    private LinearLayout llSimilar;
    private LinearLayout llMainSimilar;
    private ProgressBar progressBar;

    private Product product;

    /**
     * Method inherited from AppCompatActivity.
     * Initialize all the components here.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We set link the view with the activity
        setContentView(R.layout.activity_product_details);
        //find all the views we will use and assign them to objects
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivProduct = (ImageView) findViewById(R.id.activity_product_details_ivProduct);
        llProperties = (LinearLayout) findViewById(R.id.activity_product_details_llProperties);
        tlTableProperties = (TableLayout) findViewById(R.id.activity_product_details_tlTableProperties);
        tvName = (TextView) findViewById(R.id.activity_product_details_tvName);
        tvDescription = (TextView) findViewById(R.id.activity_product_details_tvDescription);
        llVariants = (LinearLayout) findViewById(R.id.activity_product_details_llVariants);
        rgVariants = (RadioGroup) findViewById(R.id.activity_product_details_rgVariants);
        tvPrice = (TextView) findViewById(R.id.activity_product_details_tvPrice);
        llSimilar = (LinearLayout) findViewById(R.id.activity_product_details_llSimilar);
        llMainSimilar = (LinearLayout) findViewById(R.id.activity_product_details_llMainSimilar);
        progressBar = (ProgressBar) findViewById(R.id.activity_product_details_pbProgressBar);

        //obtain the object passed from the previous Activity
        product = (Product) getIntent().getSerializableExtra(Constants.PRODUCT);

        //set the toolbar as action bar
        setSupportActionBar(toolbar);
        //if action bar is not null set it with the back arrow
        if (null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //load the content in the views
        loadContent();
    }

    /**
     * Set the information in the views.
     */
    public void loadContent(){
        //show the progress in the image view
        showProgress();
        //get the images from the product object
        List<Image> images = product.getMaster().getImages();
        //if there are images then load the first one into the image view using Picasso
        if (images.size() > 0){
            String imageURL = Config.MAIN_URL + images.get(0).getProductUrl();
            Picasso.with(this).load(imageURL).fit().error(R.drawable.product_not_available).into(ivProduct, new Callback() {
                @Override
                public void onSuccess() {
                    hideProgress();
                }

                @Override
                public void onError() {
                    hideProgress();
                }
            });
        }else{
            //there are not images available, so show a default one
            Picasso.with(this).load(R.drawable.product_not_available).fit().into(ivProduct, new Callback() {
                @Override
                public void onSuccess() {
                    hideProgress();
                }

                @Override
                public void onError() {
                    hideProgress();
                }
            });
        }

        //set the product name
        tvName.setText(product.getName());
        //set the product price
        tvPrice.setText(product.getDisplayPrice());

        //obtain the product description
        String description = product.getDescription();
        //if the description is not null then make visible the view and display it in there, otherwise hide the view
        if (null != description){
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(description);
        }else{
            tvDescription.setVisibility(View.GONE);
        }

        //if the product has variants then make the view visible and add radio buttons for each variant, otherwise hide the view
        if (product.getHasVariants()){
            llVariants.setVisibility(View.VISIBLE);

            for (Variant variant: product.getVariants()) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(variant.getName());
                newRadioButton.setId(variant.getId());
                LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                rgVariants.addView(newRadioButton, 0, layoutParams);
            }
        }else{
            llVariants.setVisibility(View.GONE);
        }

        //get a list of properties
        List<ProductProperty> productProperties = product.getProductProperties();
        //if the product has properties then make the view visible and add rows to the view for each property, otherwise hide the view
        if (productProperties.size()>0){
            llProperties.setVisibility(View.VISIBLE);
            for (ProductProperty productProperty: productProperties) {
                @SuppressLint("InflateParams") TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_row_properties, null);
                ((TextView)row.findViewById(R.id.table_row_properties_tvTitle)).setText(productProperty.getPropertyName());
                ((TextView)row.findViewById(R.id.table_row_properties_tvDescription)).setText(productProperty.getValue());
                tlTableProperties.addView(row);
            }
        }else{
            llProperties.setVisibility(View.GONE);
        }

        //get a list of classifications
        List<Classification> classifications = product.getClassifications();
        //if the product has classifications then make the view visible and add text views to the view for each classification, otherwise hide the view
        if (classifications.size()>0){
            llMainSimilar.setVisibility(View.VISIBLE);
            for (Classification classification: classifications) {
                TextView newTextView = new TextView(this);
                newTextView.setText(classification.getTaxon().getName());
                newTextView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                llSimilar.addView(newTextView, 0, layoutParams);
            }
        }else{
            llMainSimilar.setVisibility(View.GONE);
        }
    }

    /**
     * Method inherited from AppCompatActivity.
     * If user presses the back arrow, then return to previous Activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Show the progress bar in the picture
     */
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progress bar in the picture
     */
    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

}
