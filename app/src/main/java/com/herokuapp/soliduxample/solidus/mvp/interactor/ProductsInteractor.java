package com.herokuapp.soliduxample.solidus.mvp.interactor;

import com.herokuapp.soliduxample.solidus.api.APIClient;
import com.herokuapp.soliduxample.solidus.api.ServiceGenerator;
import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Products;

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
     * @param token: user token.
     * @param page: current page to fetch.
     */
    public void getProducts(String token, int perPage, int page){
        call = ServiceGenerator.createService(APIClient.class).getAllProducts(token, perPage, page);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()){
                    interactorListener.onSuccess(response.body());
                }else{
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
    public void cancelRequest(){
       if (call != null) if (call.isExecuted()) call.cancel();
    }

    /**
     * Sets the listener.
     * @param interactorListener: instance of the listener.
     */
    public void setOrdersInteractorListener(InteractorListener interactorListener) {
        this.interactorListener = interactorListener;
    }

    /**
     * Listener for interacting with the Presenter.
     */
    public interface InteractorListener{
        void onSuccess(Products products);
        void onFail(Error error);
    }
}