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

package com.herokuapp.soliduxample.solidus.mvp.presenter;

import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.mvp.interactor.ProductsInteractor;
import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;
import com.herokuapp.soliduxample.solidus.mvp.model.Products;

import java.util.List;

/**
 * @author Roberto Morelos.
 * @since 8/1/17.
 * Obtains all products from certain user.
 */
public class ProductsPresenter implements ProductsInteractor.InteractorListener {
    private ProductsInteractor interactor;
    private View viewListener;
    private boolean isLoading;
    private int maxPages = 1;
    private int currentPage = 1;
    private String token;
    private boolean isLoadingMore = false;

    public ProductsPresenter(View viewListener, String token) {
        this.interactor = new ProductsInteractor();
        this.viewListener = viewListener;
        this.token = token;
        interactor.setInteractorListener(this);
    }

    /**
     * Gets called when the view is active.
     */
    public void start() {
        currentPage = 1;
        getProducts();
    }

    /**
     * Gets called when the view is no longer active.
     */
    public void stop() {
        interactor.cancelRequest();
    }

    /**
     * Fetches orders and sets them in the adapter.
     */
    public void getProducts() {
        isLoadingMore = currentPage > 1;
        if (currentPage <= maxPages && !isLoading) {
            viewListener.showProgress(true);
            isLoading = true;
            interactor.getProducts(token, Constants.PER_PAGE, currentPage);
        }
    }

    /**
     * Method inherited from ProductsInteractor.InteractorListener.
     */
    @Override
    public void onSuccess(Products products) {
        isLoading = false;
        currentPage += 1;
        maxPages = products.getPages();
        viewListener.showProgress(false);
        viewListener.addProducts(products.getProducts(), isLoadingMore);
    }

    /**
     * Method inherited from ProductsInteractor.InteractorListener.
     */
    @Override
    public void onFail(Error error) {
        isLoading = false;
        viewListener.showProgress(false);
        viewListener.onError(error);
    }

    /**
     * @author Roberto Morelos.
     * @since 8/1/17.
     * Interface for notifying the activity/view.
     */
    public interface View {
        void addProducts(List<Product> products, boolean isLoadingMore);

        void showProgress(boolean state);

        void onError(Error error);
    }
}