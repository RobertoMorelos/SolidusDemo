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

package com.herokuapp.soliduxample.solidus.mvp.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View

import com.herokuapp.soliduxample.solidus.R
import com.herokuapp.soliduxample.solidus.app.Constants
import com.herokuapp.soliduxample.solidus.helper.SpacesItemDecoration
import com.herokuapp.soliduxample.solidus.helper.Utility
import com.herokuapp.soliduxample.solidus.mvp.model.Error
import com.herokuapp.soliduxample.solidus.mvp.model.Product
import com.herokuapp.soliduxample.solidus.mvp.presenter.ProductsPresenter
import com.herokuapp.soliduxample.solidus.mvp.view.adapter.ProductsAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_messages.*

/**
 * Created by Roberto Morelos on 3/5/17.
 * Displays all the products in a recycler view.
 */
class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
        ProductsAdapter.OnItemClickListener, ProductsPresenter.View {

    private lateinit var presenter: ProductsPresenter
    private lateinit var productsAdapter: ProductsAdapter

    private val TAG: String = MainActivity::class.java.simpleName
    private val COLUMNS = 2

    /**
     * Method inherited from AppCompatActivity.
     * Initialize all the components here.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //We set link the view with the activity
        setContentView(R.layout.activity_main)
        //find all the views we will use and assign them to objects
        //ButterKnife.bind(this)

        //set the toolbar as action bar
        setSupportActionBar(toolbar_home_toolbar)
        //set the recycler view with fixed size
        recycler_prducts.setHasFixedSize(true)
        //now we set a grid layout to the layout manager, so we can see the cards as grids.
        recycler_prducts.layoutManager = GridLayoutManager(this, COLUMNS)
        //this method allows us to decor every item in the grid
        recycler_prducts.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space_between_areas)))
        //add a scroll listener for detecting the bottom of the recycler view
        recycler_prducts.addOnScrollListener(onScrollListener)
        //we initialize our recycler view adapter
        productsAdapter = ProductsAdapter(this)
        //set the listener for detecting when we click an item
        productsAdapter.setOnItemClickListener(this)
        //set our customized adapter to the recyclerView
        recycler_prducts.adapter = productsAdapter
        //set one custom color to the swipe refresh layout
        swipe_refresh_view.setColorSchemeResources(R.color.colorAccent)
        //assign the the listeners
        swipe_refresh_view.setOnRefreshListener(this)

        presenter = ProductsPresenter(this, Constants.TOKEN)
        //check internet connection, if there is then start fetching products
        if (Utility.isNetworkAvailable(this)) {
            //start the presenter
            presenter.start()
        } else {
            showMessageView(Constants.TYPE_CONNECTION)
        }
    }

    /**
     * Method inherited from AppCompatActivity.
     */
    override fun onStop() {
        //stop the presenter
        presenter.stop()
        super.onStop()
    }

    /**
     * Method inherited from AppCompatActivity.
     * Override the menu with a customized one.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Method inherited from SwipeRefreshLayout.OnRefreshListener.
     * Fetch the products again from the API.
     */
    override fun onRefresh() {
        //if internet available then fetch products resetting the adapter (from page one).
        if (Utility.isNetworkAvailable(this)) {
            presenter.start()
        } else {
            showProgress(false)
            if (!productsAdapter.hasContent())
                showMessageView(Constants.TYPE_CONNECTION)
        }
    }

    /**
     * Method inherited from ProductsAdapter.OnItemClickListener.
     * Launches the next view to show product details.
     */
    override fun onItemClick(productId: Int) {
        val registerIntent = Intent(this, ProductDetailsActivity::class.java)
        registerIntent.putExtra(Constants.PRODUCT_ID, productId)
        startActivity(registerIntent)
    }

    /**
     * Method inherited from ProductsPresenter.View.
     * Handles products fetched from the API.
     */
    override fun addProducts(products: List<Product>, isLoadingMore: Boolean) {
        //if false, means the adapter must be reset.
        if (!isLoadingMore) productsAdapter.clear()
        //add products to the adapter
        if (products.isNotEmpty()) {
            productsAdapter.add(products)
        } else {
            //if there are not products, display a custom view
            showMessageView(Constants.TYPE_NO_CONTENT)
        }
    }

    /**
     * Method inherited from ProductsPresenter.View.
     * Starts/stops the swipe refresh layout animation.
     */
    override fun showProgress(state: Boolean) {
        //hide the view until the information is fetched
        if (state) hideMessageView()
        //stop or start the refreshing animation
        swipe_refresh_view!!.isRefreshing = state
    }

    /**
     * Method inherited from ProductsPresenter.View.
     */
    override fun onError(error: Error) {
        //there was an error while performing the call
        Log.e(TAG, "Error: " + error.error)
        //and show a custom error view
        showMessageView(Constants.TYPE_ERROR)
    }

    /**
     * Shows certain view according to the messageType.

     * @param messageType This parameter decides the type of message we will display and the icon.
     */
    private fun showMessageView(messageType: String) {
        //if message view is visible then recycler view must not
        linear_message_view!!.visibility = View.VISIBLE
        recycler_prducts!!.visibility = View.GONE

        when (messageType) {
            Constants.TYPE_CONNECTION -> {
                image_message_view_icon!!.setBackgroundResource(R.drawable.ic_connection_off)
                text_message_view_title!!.text = getString(R.string.no_internet_connection_title)
            }
            Constants.TYPE_ERROR -> {
                image_message_view_icon!!.setBackgroundResource(R.drawable.ic_gears)
                text_message_view_title!!.text = getString(R.string.error_connection_title)
            }
            Constants.TYPE_NO_CONTENT -> {
                image_message_view_icon!!.setBackgroundResource(R.drawable.ic_empty_cart)
                text_message_view_title!!.text = getString(R.string.no_content_title)
            }
        }
    }

    /**
     * Hides the message view.
     */
    private fun hideMessageView() {
        //if message view is not visible then recycler view must be
        linear_message_view!!.visibility = View.GONE
        recycler_prducts!!.visibility = View.VISIBLE
    }

    /**
     * Detects when a recycler view is scrolling.
     */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = GridLayoutManager::class.java.cast(recyclerView!!.layoutManager)

            if (dy > 0) {
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                //if the last item is visible
                if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                    //get more products only if internet connection available
                    if (Utility.isNetworkAvailable(baseContext)) presenter.getProducts()
                }
            }
        }
    }
}