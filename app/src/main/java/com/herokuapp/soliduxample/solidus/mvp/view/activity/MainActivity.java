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

import android.content.Intent;
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
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.helper.SpacesItemDecoration;
import com.herokuapp.soliduxample.solidus.helper.Utility;
import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;
import com.herokuapp.soliduxample.solidus.mvp.presenter.ProductsPresenter;
import com.herokuapp.soliduxample.solidus.mvp.view.adapter.ProductsAdapter;

import java.util.List;

/**
 * Created by Roberto Morelos on 3/5/17.
 * Main activity to display all the products in a recycler view.
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        ProductsAdapter.OnItemClickListener, ProductsPresenter.View{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int COLUMNS = 2;

    private LinearLayout llMessageView;
    private ImageView ivMessageViewIcon;
    private TextView tvMessageViewTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private ProductsPresenter presenter;

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
        //add a scroll listener for detecting the bottom of the recycler view
        recyclerView.addOnScrollListener(onScrollListener);
        //we initialize our recycler view adapter
        productsAdapter = new ProductsAdapter(this);
        //set the listener for detecting when we click an item
        productsAdapter.setOnItemClickListener(this);
        //set our customized adapter to the recyclerView
        recyclerView.setAdapter(productsAdapter);
        //set one custom color to the swipe refresh layout
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        //assign the the listeners
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter = new ProductsPresenter(this, Constants.TOKEN);
        presenter.start();
        //start the presenter
        if (Utility.isNetworkAvailable(this)) {
            presenter.getProducts(false);
        }else{
            showMessageView(Constants.TYPE_CONNECTION);
        }
    }

    /**
     * Method inherited from AppCompatActivity.
     */
    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
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
        if (Utility.isNetworkAvailable(this)) {
            presenter.getProducts(true);
        }else{
            showProgress(false);
            if (!productsAdapter.hasContent())
                showMessageView(Constants.TYPE_CONNECTION);
        }
    }

    /**
     * Method inherited from ProductsAdapter.OnItemClickListener.
     */
    @Override
    public void onItemClick(Product product) {
        Intent registerIntent = new Intent(this, ProductDetailsActivity.class);
        registerIntent.putExtra(Constants.PRODUCT, product);
        startActivity(registerIntent);
    }

    /**
     * Method inherited from ProductsPresenter.View.
     * It allows to fetch the products from the API and then display them in the recycler view.
     */
    @Override
    public void addProducts(List<Product> products, boolean isLoadingMore) {
        if (!isLoadingMore) productsAdapter.clear();
        if (products.size() > 0){
            productsAdapter.add(products);
        }else{
            //if there are not products, display a custom view
            showMessageView(Constants.TYPE_NO_CONTENT);
        }
    }

    /**
     * Method inherited from ProductsPresenter.View.
     * Starts/stops the swipe refresh layout animation.
     */
    @Override
    public void showProgress(boolean state) {
        if (state) hideMessageView();
        swipeRefreshLayout.setRefreshing(state);
    }

    /**
     * Method inherited from ProductsPresenter.View.
     */
    @Override
    public void onError(Error error) {
        //there was an error while performing the call
        Log.e(TAG, "Error: " + error.getError());
        //and show a custom error view
        showMessageView(Constants.TYPE_ERROR);
    }

    /**
     * Detects when a recycler view is scrolling.
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            GridLayoutManager layoutManager = GridLayoutManager.class.cast(recyclerView.getLayoutManager());

            if(dy > 0) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    if (Utility.isNetworkAvailable(getBaseContext())) presenter.getProducts(false);
                }
            }
        }
    };

    /**
     * Shows certain view according to the messageType.
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
     * Hides the message view.
     */
    private void hideMessageView(){
        llMessageView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}