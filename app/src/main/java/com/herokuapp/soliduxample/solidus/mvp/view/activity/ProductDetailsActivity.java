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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.herokuapp.soliduxample.solidus.R;
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.helper.Utility;
import com.herokuapp.soliduxample.solidus.mvp.model.Classification;
import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Image;
import com.herokuapp.soliduxample.solidus.mvp.model.Master;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;
import com.herokuapp.soliduxample.solidus.mvp.model.ProductProperty;
import com.herokuapp.soliduxample.solidus.mvp.presenter.ProductDetailsPresenter;
import com.herokuapp.soliduxample.solidus.rest.ApiConfiguration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Roberto Morelos on 3/6/17.
 * Displays the detailed information of one product.
 */
public class ProductDetailsActivity extends AppCompatActivity implements ProductDetailsPresenter.View {
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    @BindView(R.id.image_product)
    ImageView ivProduct;
    @BindView(R.id.linear_properties)
    LinearLayout llProperties;
    @BindView(R.id.table_properties)
    TableLayout tlTableProperties;
    @BindView(R.id.text_product_name)
    TextView tvName;
    @BindView(R.id.text_product_description)
    TextView tvDescription;
    @BindView(R.id.linear_variants)
    LinearLayout llVariants;
    @BindView(R.id.radio_variants)
    RadioGroup rgVariants;
    @BindView(R.id.text_product_price)
    TextView tvPrice;
    @BindView(R.id.linear_similar)
    LinearLayout llSimilar;
    @BindView(R.id.linear_main_similar)
    LinearLayout llMainSimilar;
    @BindView(R.id.progress_product)
    ProgressBar progressBarImage;
    @BindView(R.id.progress_main_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.linear_wrapper)
    LinearLayout linearWrapper;
    @BindView(R.id.linear_message_view)
    LinearLayout llMessageView;
    @BindView(R.id.image_message_view_icon)
    ImageView ivMessageViewIcon;
    @BindView(R.id.text_message_view_title)
    TextView tvMessageViewTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.scroll_main_scroll)
    ScrollView scrollView;
    private ProductDetailsPresenter presenter;

    /**
     * Method inherited from AppCompatActivity.
     * Initialize all the components here.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We set link the view with the activity
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        //set the toolbar as action bar
        setSupportActionBar(toolbar);
        //if action bar is not null set it with the back arrow
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //obtain the id passed from the previous Activity
        int productId = getIntent().getIntExtra(Constants.PRODUCT_ID, 0);

        presenter = new ProductDetailsPresenter(this, Constants.TOKEN);
        //start the presenter
        presenter.start();
        //check internet connection, if there is then start fetching product details
        if (Utility.isNetworkAvailable(this)) {
            presenter.getProduct(productId);
        } else {
            showMessageView(Constants.TYPE_CONNECTION);
        }
    }

    /**
     * Method inherited from AppCompatActivity.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Method inherited from AppCompatActivity.
     */
    @Override
    protected void onStop() {
        //stop the presenter
        presenter.stop();
        super.onStop();
    }

    /**
     * Method inherited from ProductDetailsPresenter.View.
     */
    @Override
    public void onProductFetched(Product product) {
        loadContent(product);
    }

    /**
     * Method inherited from ProductDetailsPresenter.View.
     */
    @Override
    public void showProgress(boolean state) {
        //hide the view until the information is fetched
        if (state) hideMessageView();
        linearWrapper.setVisibility(state ? View.GONE : View.VISIBLE);
        //stop or start the progressbar animation
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    /**
     * Method inherited from ProductDetailsPresenter.View.
     */
    @Override
    public void onError(Error error) {
        //there was an error while performing the call
        Log.e(TAG, "Error: " + error.getError());
        //and show a custom error view
        showMessageView(Constants.TYPE_ERROR);
    }

    /**
     * Sets the information in the views.
     */
    public void loadContent(Product product) {
        //get the images from the product object
        List<Image> images = product.getMaster().getImages();
        //if there are images then load the first one into the image view using Picasso
        String imageURL = "";
        if (images.size() > 0) {
            imageURL = images.get(0).getProductUrl();
        }
        updatePicture(imageURL);

        //set the product name
        tvName.setText(product.getName());
        //set the product price
        tvPrice.setText(product.getDisplayPrice());

        //obtain the product description
        String description = product.getDescription();
        //if the description is not null then make visible the view and display it in there,
        // otherwise hide the view
        if (null != description) {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(description);
        } else {
            tvDescription.setVisibility(View.GONE);
        }

        //if the product has variants then make the view visible and add radio buttons for each
        // variant, otherwise hide the view
        if (product.getHasVariants()) {
            llVariants.setVisibility(View.VISIBLE);
            for (final Master variant : product.getVariants()) {
                RadioButton radioOptionVariant = new RadioButton(this);
                radioOptionVariant.setText(variant.getOptionsText());
                radioOptionVariant.setId(variant.getId());
                radioOptionVariant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePicture(variant.getImages().get(0).getLargeUrl());
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_UP);
                            }
                        });
                    }
                });
                LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                rgVariants.addView(radioOptionVariant, 0, layoutParams);
            }
        } else {
            llVariants.setVisibility(View.GONE);
        }

        //get a list of properties
        List<ProductProperty> productProperties = product.getProductProperties();
        //if the product has properties then make the view visible and add rows to the view for
        // each property, otherwise hide the view
        if (productProperties.size() > 0) {
            llProperties.setVisibility(View.VISIBLE);
            for (ProductProperty productProperty : productProperties) {
                @SuppressLint("InflateParams") TableRow row = (TableRow) LayoutInflater.from(this)
                        .inflate(R.layout.table_row_properties, null);
                ((TextView) row.findViewById(R.id.text_properties_title))
                        .setText(productProperty.getPropertyName());
                ((TextView) row.findViewById(R.id.text_properties_description))
                        .setText(productProperty.getValue());
                tlTableProperties.addView(row);
            }
        } else {
            llProperties.setVisibility(View.GONE);
        }

        //get a list of classifications
        List<Classification> classifications = product.getClassifications();
        //if the product has classifications then make the view visible and add text views to the
        // view for each classification, otherwise hide the view
        if (classifications.size() > 0) {
            llMainSimilar.setVisibility(View.VISIBLE);
            for (Classification classification : classifications) {
                TextView newTextView = new TextView(this);
                newTextView.setText(classification.getTaxon().getName());
                newTextView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                llSimilar.addView(newTextView, 0, layoutParams);
            }
        } else {
            llMainSimilar.setVisibility(View.GONE);
        }
    }

    /**
     * Changes the image content using a URL.
     *
     * @param URL: picture location.
     */
    private void updatePicture(String URL) {
        String imageURL = ApiConfiguration.MAIN_URL + URL;
        showProgress();
        Picasso.with(this).load(imageURL).centerInside().fit()
                .placeholder(R.drawable.place_holder_default)
                .error(R.drawable.product_not_available)
                .into(ivProduct, new Callback() {
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

    /**
     * Shows/hides the progress bar in the picture.
     */
    public void showProgress() {
        progressBarImage.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the progress bar in the picture
     */
    public void hideProgress() {
        progressBarImage.setVisibility(View.GONE);
    }

    /**
     * Shows certain view according to the messageType.
     *
     * @param messageType This parameter decides the type of message we will display and the icon.
     */
    private void showMessageView(String messageType) {
        llMessageView.setVisibility(View.VISIBLE);
        linearWrapper.setVisibility(View.GONE);

        switch (messageType) {
            case (Constants.TYPE_CONNECTION):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_connection_off);
                tvMessageViewTitle.setText(getString(R.string.no_internet_connection_title));
                break;
            case (Constants.TYPE_ERROR):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_gears);
                tvMessageViewTitle.setText(getString(R.string.error_connection_title));
                break;
        }
    }

    /**
     * Hides the message view.
     */
    private void hideMessageView() {
        llMessageView.setVisibility(View.GONE);
        linearWrapper.setVisibility(View.VISIBLE);
    }
}