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

import com.herokuapp.soliduxample.solidus.app.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Roberto Morelos
 * @since 3/5/17
 * Uses the Retrofit service, including some features like logs for debugging
 * and increasing the timeout.
 */
public class RestService {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiConfiguration.MAIN_URL).addConverterFactory(GsonConverterFactory.create());
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(ApiConfiguration.TIME_OUT, ApiConfiguration.TIME_UNIT)
            .readTimeout(ApiConfiguration.TIME_OUT, ApiConfiguration.TIME_UNIT)
            .writeTimeout(ApiConfiguration.TIME_OUT, ApiConfiguration.TIME_UNIT)
            .addInterceptor(getLogs());

    /**
     * Defines the level of the logs.
     */
    private static HttpLoggingInterceptor getLogs() {
        if (Constants.ENABLE_API_LOGS) {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }
    }

    /**
     * Creates the client service.
     */
    public static <S> S createService(Class<S> serviceClass) {
        return builder.client(httpClient.build()).build().create(serviceClass);
    }
}