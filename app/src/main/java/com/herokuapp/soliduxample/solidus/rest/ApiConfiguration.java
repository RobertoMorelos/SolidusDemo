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

import java.util.concurrent.TimeUnit;

/**
 * @author Roberto Morelos
 * @since 3/5/17
 * Contains the main configuration info for the API.
 */
public class ApiConfiguration {
    //Base URL
    //public static final String MAIN_URL = "https://soliduxample.herokuapp.com";
    public static final String MAIN_URL = "https://solidus-lib.herokuapp.com/";
    //Time out
    static final int TIME_OUT = 15;
    static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    //API parameters
    static final String PRODUCT_PARAMETER_PER_PAGE = "per_page";
    static final String PRODUCT_PARAMETER_PAGE = "page";
    static final String PRODUCT_PARAMETER_ID = "id";
    static final String PRODUCT_PARAMETER_TOKEN = "X-Spree-Token";
    //API end points
    static final String URL_PRODUCTS = "/api/products";
    static final String URL_PRODUCT_DETAILS = "/api/products/{" + PRODUCT_PARAMETER_ID + "}";
}