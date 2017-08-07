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

import com.herokuapp.soliduxample.solidus.mvp.interactor.ProductDetailsInteractor;
import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;

/**
 * @author Roberto Morelos.
 * @since 8/1/17.
 * Obtains all product details from certain user.
 */
public class ProductDetailsPresenter implements ProductDetailsInteractor.InteractorListener {
    private ProductDetailsInteractor interactor;
    private View viewListener;
    private String token;

    public ProductDetailsPresenter(View viewListener, String token) {
        this.interactor = new ProductDetailsInteractor();
        this.viewListener = viewListener;
        this.token = token;
        this.interactor.setInteractorListener(this);
    }

    /**
     * Gets called when the view is active.
     */
    public void start() {
        viewListener.showProgress(false);
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
    public void getProduct(int id) {
        viewListener.showProgress(true);
        interactor.getProduct(token, id);
    }

    /**
     * Method inherited from ProductsInteractor.InteractorListener.
     */
    @Override
    public void onSuccess(Product product) {
        viewListener.showProgress(false);
        viewListener.onProductFetched(product);
    }

    /**
     * Method inherited from ProductsInteractor.InteractorListener.
     */
    @Override
    public void onFail(Error error) {
        viewListener.showProgress(false);
        viewListener.onError(error);
    }

    /**
     * @author Roberto Morelos.
     * @since 8/1/17.
     * Interface for notifying the activity/view.
     */
    public interface View {
        void onProductFetched(Product product);

        void showProgress(boolean state);

        void onError(Error error);
    }
}