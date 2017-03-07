package com.herokuapp.soliduxample.solidus.views;

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
import com.herokuapp.soliduxample.solidus.app.Config;
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.models.Classification;
import com.herokuapp.soliduxample.solidus.models.Image;
import com.herokuapp.soliduxample.solidus.models.Product;
import com.herokuapp.soliduxample.solidus.models.ProductProperty;
import com.herokuapp.soliduxample.solidus.models.Variant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roberto Morelos on 3/6/17.
 * This activity displays the detailed information of one product.
 */
public class ProductDetails extends AppCompatActivity {
    //private static final String TAG = ProductDetails.class.getSimpleName();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
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

        product = (Product) getIntent().getSerializableExtra(Constants.PRODUCT);

        setSupportActionBar(toolbar);
        if (null != getSupportActionBar()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadContent();
    }

    public void loadContent(){
        List<Image> images = product.getMaster().getImages();
        showProgress();
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
            Picasso.with(this).load(R.drawable.product_not_available).fit().error(R.drawable.product_not_available).into(ivProduct, new Callback() {
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

        tvName.setText(product.getName());
        tvPrice.setText(product.getDisplayPrice());

        String description = product.getDescription();
        if (null != description){
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(description);
        }else{
            tvDescription.setVisibility(View.GONE);
        }

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

        List<ProductProperty> productProperties = product.getProductProperties();
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

        List<Classification> classifications = product.getClassifications();
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

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

}
