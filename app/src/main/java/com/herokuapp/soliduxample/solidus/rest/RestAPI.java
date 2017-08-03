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

package com.herokuapp.soliduxample.solidus.rest;

import com.herokuapp.soliduxample.solidus.mvp.model.Product;
import com.herokuapp.soliduxample.solidus.mvp.model.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Roberto Morelos.
 * @since 3/5/17.
 * Controls the HTTP requests to the API.
 */
public interface RestAPI {

    /**
     * Obtains all the products per page.
     *
     * @param token:   Needed for the API.
     * @param perPage: How many items per page.
     * @param page:    The page which will get the information.
     */
    @GET(ApiConfiguration.URL_PRODUCTS)
    Call<Products> getAllProducts(
            @Header(ApiConfiguration.PRODUCT_PARAMETER_TOKEN) String token,
            @Query(ApiConfiguration.PRODUCT_PARAMETER_PER_PAGE) int perPage,
            @Query(ApiConfiguration.PRODUCT_PARAMETER_PAGE) int page
    );

    /**
     * Obtains product details by id.
     *
     * @param token:     Needed for the API.
     * @param productId: Id for request.
     */
    @GET(ApiConfiguration.URL_PRODUCT_DETAILS)
    Call<Product> getProductDetails(
            @Header(ApiConfiguration.PRODUCT_PARAMETER_TOKEN) String token,
            @Path(ApiConfiguration.PRODUCT_PARAMETER_ID) int productId
    );
}