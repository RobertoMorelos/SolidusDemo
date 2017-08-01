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

package com.herokuapp.soliduxample.solidus.views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herokuapp.soliduxample.solidus.R;
import com.herokuapp.soliduxample.solidus.api.APIClient;
import com.herokuapp.soliduxample.solidus.api.ServiceGenerator;
import com.herokuapp.soliduxample.solidus.api.Config;
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.helper.SpacesItemDecoration;
import com.herokuapp.soliduxample.solidus.helper.Utility;
import com.herokuapp.soliduxample.solidus.models.Product;
import com.herokuapp.soliduxample.solidus.models.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roberto Morelos on 3/5/17.
 * Main activity to display all the products in a recycler view.
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final APIClient taskService = ServiceGenerator.createService(APIClient.class);
    private static final int COLUMNS = 2;

    private LinearLayout llMessageView;
    private ImageView ivMessageViewIcon;
    private TextView tvMessageViewTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private ProductsRecyclerView productsRecyclerView;
    private List<Product> products = new ArrayList<>();
    private int currentPage = 1;
    private int totalProducts;

    /**
     * Method inherited from AppCompatActivity.
     * Initialize all the components here.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We set link the view with the activity
        setContentView(R.layout.activity_main);
        //find all the views we will use and assign them to objects
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        llMessageView = (LinearLayout) findViewById(R.id.activity_main_llMessageView);
        ivMessageViewIcon = (ImageView) findViewById(R.id.activity_main_ivMessageViewIcon);
        tvMessageViewTitle = (TextView) findViewById(R.id.activity_main_tvMessageViewTitle);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipeContainer);
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);

        //set the toolbar as action bar
        setSupportActionBar(toolbar);
        //set the recycler view with fixed size
        recyclerView.setHasFixedSize(true);
        //now we set a grid layout to the layout manager, so we can see the cards as grids.
        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        //this method allows us to decor every item in the grid
        recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.space_between_areas)));
        //we initialize our recycler view adapter
        productsRecyclerView = new ProductsRecyclerView(MainActivity.this, products);
        //set our customized adapter to the recyclerView
        recyclerView.setAdapter(productsRecyclerView);
        //set one custom color to the swipe refresh layout
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        //assign the the listeners
        swipeRefreshLayout.setOnRefreshListener(this);

        //start fetching the products, so display the swipe refresh animation here
        showProgress();
        showProducts();
    }

    /**
     * Method inherited from AppCompatActivity.
     * Override the menu with a customized one.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Method inherited from SwipeRefreshLayout.OnRefreshListener.
     * Fetch the products again from the API.
     */
    @Override
    public void onRefresh() {
        showProducts();
    }

    /**
     * It allows to fetch the products from the API and then display them in the recycler view.
     */
    private void showProducts(){
        //check the Internet connection
        if (Utility.isNetworkAvailable(this)){
            //start showing the progress bar
            hideMessageView();
            //call the API to obtain all the services
            Call<Products> call = taskService.getAllProducts(currentPage,Config.TOKEN);
            //enqueue the call so we can do it asynchronously
            call.enqueue(new Callback<Products>() {
                @Override
                public void onResponse(Call<Products> call, Response<Products> response) {
                    //check if the API responds with a successful answer
                    if (response.isSuccessful()) {
                        //if so, then hide any previous message view, the progress bar and stop the refreshing animation
                        hideProgress();
                        //obtain how many products are
                        totalProducts  = response.body().getTotalCount();
                        //clear all previous products
                        products.clear();
                        //and assign the new ones to the same object, otherwise the adapter won't work.
                        products.addAll(response.body().getProducts());
                        if (totalProducts > 0){
                            //if there are at least one product, display it
                            recyclerView.setVisibility(View.VISIBLE);
                            productsRecyclerView.notifyDataSetChanged();
                        }else{
                            //if there are not products, display a custom view
                            showMessageView(Constants.TYPE_NO_CONTENT);
                        }
                    } else {
                        //the response was not successful
                        Log.e(TAG, "Error: " + response.message());
                        //so hide the progress bar
                        hideProgress();
                        //and display a custom error view
                        showMessageView(Constants.TYPE_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<Products> call, Throwable t) {
                    //there was an error while performing the call
                    Log.e(TAG, "Error: " + t);
                    //so hide the progress bar
                    hideProgress();
                    //and show a custom error view
                    showMessageView(Constants.TYPE_ERROR);
                }
            });
        }else{
            // there is no Internet connection, so display a custom view to let the user know that.
            hideProgress();
            showMessageView(Constants.TYPE_CONNECTION);
        }
    }

    /**
     * Start the swipe refresh layout animation.
     */
    private void showProgress(){
        swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * Stop the swipe refresh layout animation.
     */
    private void hideProgress(){
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Show certain view according to the messageType.
     * @param messageType This parameter decides the type of message we will display and the icon.
     */
    private void showMessageView(String messageType){
        llMessageView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        switch (messageType){
            case(Constants.TYPE_CONNECTION):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_connection_off);
                tvMessageViewTitle.setText(getString(R.string.no_internet_connection_title));
                break;
            case(Constants.TYPE_ERROR):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_gears);
                tvMessageViewTitle.setText(getString(R.string.error_connection_title));
                break;
            case(Constants.TYPE_NO_CONTENT):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_empty_cart);
                tvMessageViewTitle.setText(getString(R.string.no_content_title));
                break;
        }
    }

    /**
     * Hide the message view.
     */
    private void hideMessageView(){
        llMessageView.setVisibility(View.GONE);
    }

}
