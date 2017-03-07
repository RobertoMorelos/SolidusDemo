package com.herokuapp.soliduxample.solidus.api;

import com.herokuapp.soliduxample.solidus.app.Config;
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.models.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Roberto Morelos on 3/5/17.
 * This interface is made to control the HTTP request to the API.
 */
public interface APIClient {

    /**
     * Obtain all the products per page.
     * @param page The page which will get the information.
     * @param token needed for the API.
     */
    @GET(Config.PRODUCTS_URL)
    Call<Products> getAllProducts(
            @Query(Constants.PRODUCT_PARAMETER_PAGE)   int page,
            @Query(Constants.PRODUCT_PARAMETER_TOKEN)   String token
    );
}
