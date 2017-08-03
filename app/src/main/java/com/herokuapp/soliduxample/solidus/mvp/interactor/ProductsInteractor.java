/*
* MIT License

* Copyright (c) 2017 Roberto Morelos

* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
* */

package com.herokuapp.soliduxample.solidus.mvp.interactor;

import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Products;
import com.herokuapp.soliduxample.solidus.rest.RestAPI;
import com.herokuapp.soliduxample.solidus.rest.RestService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Roberto Morelos
 * @since 8/1/17
 * Handles the information request and notifies the ProductsPresenter.
 */
public class ProductsInteractor {
    private InteractorListener interactorListener;
    private Call<Products> call;

    /**
     * Gets all products from certain user.
     *
     * @param token: user token.
     * @param page:  current page to fetch.
     */
    public void getProducts(String token, int perPage, int page) {
        call = RestService.createService(RestAPI.class).getAllProducts(token, perPage, page);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    interactorListener.onSuccess(response.body());
                } else {
                    try {
                        interactorListener.onFail(new Error(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        interactorListener.onFail(new Error(Error.ERROR_TYPE_UNHANDLED));
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                interactorListener.onFail(new Error(t.getMessage()));
            }
        });
    }

    /**
     * Cancels the request when the view is not longer active.
     */
    public void cancelRequest() {
        if (call != null) if (call.isExecuted()) call.cancel();
    }

    /**
     * Sets the listener.
     *
     * @param interactorListener: instance of the listener.
     */
    public void setInteractorListener(InteractorListener interactorListener) {
        this.interactorListener = interactorListener;
    }

    /**
     * Listener for interacting with the Presenter.
     */
    public interface InteractorListener {
        void onSuccess(Products products);

        void onFail(Error error);
    }
}