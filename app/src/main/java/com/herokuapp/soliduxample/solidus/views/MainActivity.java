package com.herokuapp.soliduxample.solidus.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.herokuapp.soliduxample.solidus.R;
import com.herokuapp.soliduxample.solidus.api.APIClient;
import com.herokuapp.soliduxample.solidus.api.ServiceGenerator;
import com.herokuapp.soliduxample.solidus.app.Config;
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
 * Main activity to display all the products in a list.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final APIClient taskService = ServiceGenerator.createService(APIClient.class);
    private static final String TYPE_ERROR      = "type_error";
    private static final String TYPE_CONNECTION = "type_connection";
    private static final String TYPE_NO_CONTENT = "type_no_content";
    private static final int COLUMNS = 2;

    private ProgressBar mainProgressBar;
    private LinearLayout llMessageView;
    private ImageView ivMessageViewIcon;
    private TextView tvMessageViewTitle;

    private ProductsRecyclerView productsRecyclerView;
    private List<Product> products = new ArrayList<>();
    private int currentPage = 1;
    private int totalProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        mainProgressBar = (ProgressBar) findViewById(R.id.activity_main_pbMainProgressBar);
        llMessageView = (LinearLayout) findViewById(R.id.activity_main_llMessageView);
        ivMessageViewIcon = (ImageView) findViewById(R.id.activity_main_ivMessageViewIcon);
        tvMessageViewTitle = (TextView) findViewById(R.id.activity_main_tvMessageViewTitle);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        Button btnRetry = (Button) findViewById(R.id.activity_main_btnMessageViewRetry);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.space_between_areas)));
        productsRecyclerView = new ProductsRecyclerView(MainActivity.this, products);
        recyclerView.setAdapter(productsRecyclerView);
        btnRetry.setOnClickListener(this);

        setSupportActionBar(toolbar);
        showProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.activity_main_btnMessageViewRetry):
                showProducts();
                break;
        }

    }

    public void showProducts(){
        if (Utility.isNetworkAvailable(this)){
            showProgress();
            Call<Products> call = taskService.getAllProducts(currentPage,Config.TOKEN);
            call.enqueue(new Callback<Products>() {
                @Override
                public void onResponse(Call<Products> call, Response<Products> response) {
                    if (response.isSuccessful()) {
                        hideMessageView();
                        hideProgress();

                        totalProducts  = response.body().getTotalCount();
                        products.addAll(response.body().getProducts());
                        if (totalProducts > 0){
                            productsRecyclerView.notifyDataSetChanged();
                        }else{
                            showMessageView(TYPE_NO_CONTENT);
                        }
                    } else {
                        Log.e(TAG, "Error: " + response.message());
                        hideProgress();
                        showMessageView(TYPE_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<Products> call, Throwable t) {
                    Log.e(TAG, "Error: " + t);
                    hideProgress();
                    showMessageView(TYPE_ERROR);
                }
            });
        }else{
            showMessageView(TYPE_CONNECTION);
        }
    }

    public void showProgress(){
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        mainProgressBar.setVisibility(View.GONE);
    }

    public void showMessageView(String messageType){
        llMessageView.setVisibility(View.VISIBLE);

        switch (messageType){
            case(TYPE_CONNECTION):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_connection_off);
                tvMessageViewTitle.setText(getString(R.string.no_internet_connection_title));
                break;
            case(TYPE_ERROR):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_gears);
                tvMessageViewTitle.setText(getString(R.string.error_connection_title));
                break;
            case(TYPE_NO_CONTENT):
                ivMessageViewIcon.setBackgroundResource(R.drawable.ic_empty_cart);
                tvMessageViewTitle.setText(getString(R.string.no_content_title));
                break;
        }
    }

    public void hideMessageView(){
        llMessageView.setVisibility(View.GONE);
    }

}
