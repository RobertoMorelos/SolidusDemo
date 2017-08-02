/*
* MIT License
*
* Copyright (c) 2017 Magma Labs
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

package com.herokuapp.soliduxample.solidus;

import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.mvp.interactor.ProductDetailsInteractor;
import com.herokuapp.soliduxample.solidus.mvp.model.Error;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Diego Mendoza
 * @since 7/11/17
 * Test for checking if a given order is fetched correctly (not null).
 */

public class GetProductByIdUnitTest{
    private static final String TEST_WRONG_TOKEN = "57f8cccb2d28caf3d0a4f30d60e2262282bfa60ca51e8296";
    private static final int TEST_PRODUCT_ID = 1;
    private Product product;
    private Error error;

    private ProductDetailsInteractor productDetailsInteractor;
    @Before
    public void beforeTest() {
       productDetailsInteractor = new ProductDetailsInteractor();
    }

    @Test
    public void getProductDetailsTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final GetProductByIdUnitTest getProductByIdUnitTest = new GetProductByIdUnitTest();
        productDetailsInteractor.setInteractorListener(new ProductDetailsInteractor.InteractorListener() {
            @Override
            public void onSuccess(Product product) {
                latch.countDown();
                getProductByIdUnitTest.product = product;
            }

            @Override
            public void onFail(Error error) {
                latch.countDown();
                getProductByIdUnitTest.error = error;
            }
        });
        productDetailsInteractor.getProduct(Constants.TOKEN, TEST_PRODUCT_ID);
        latch.await();

        Assert.assertNotNull(getProductByIdUnitTest.product);
        Assert.assertNull(getProductByIdUnitTest.error);
    }

    @Test
    public void getProductDetailsWrongTokenTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final GetProductByIdUnitTest getProductByIdUnitTest = new GetProductByIdUnitTest();
        productDetailsInteractor.setInteractorListener(new ProductDetailsInteractor.InteractorListener() {
            @Override
            public void onSuccess(Product product) {
                latch.countDown();
                getProductByIdUnitTest.product = product;
            }

            @Override
            public void onFail(Error error) {
                latch.countDown();
                getProductByIdUnitTest.error = error;
            }
        });
        productDetailsInteractor.getProduct(TEST_WRONG_TOKEN, TEST_PRODUCT_ID);
        latch.await();

        Assert.assertNull(getProductByIdUnitTest.product);
        Assert.assertNotNull(getProductByIdUnitTest.error);
    }
}