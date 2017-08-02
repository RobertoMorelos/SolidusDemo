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

package com.herokuapp.soliduxample.solidus.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.herokuapp.soliduxample.solidus.R;
import com.herokuapp.soliduxample.solidus.rest.ApiConfiguration;
import com.herokuapp.soliduxample.solidus.mvp.model.Image;
import com.herokuapp.soliduxample.solidus.mvp.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto Morelos on 3/6/17.
 * This is the adapter for displaying the products in the Recycler View.
 */
public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //private static final String TAG = ProductsAdapter.class.getSimpleName();
    private List<Product> products = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    /**
     * Public constructor.
     * @param context needed to use certain features.
     */
    public ProductsAdapter(Context context) {
        this.context = context;
    }

    /**
     * Method inherited from RecyclerView.Adapter.
     * Return a custom view holder made with the private class ProductViewHolder.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_view, viewGroup, false));
    }

    /**
     * Method inherited from RecyclerView.Adapter.
     * The view is being bound so fill it with the object data in the current position.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        final Product product = products.get(position);

        List<Image> images = product.getMaster().getImages();
        productViewHolder.showProgress();
        if (images.size() > 0){
            String imageURL = ApiConfiguration.MAIN_URL + images.get(0).getProductUrl();
            Picasso.with(context).load(imageURL).fit().error(R.drawable.product_not_available).into(productViewHolder.ivProduct, new Callback() {
                @Override
                public void onSuccess() {
                    productViewHolder.hideProgress();
                }

                @Override
                public void onError() {
                    productViewHolder.hideProgress();
                }
            });
        }else{
            Picasso.with(context).load(R.drawable.product_not_available).fit().error(R.drawable.product_not_available).into(productViewHolder.ivProduct, new Callback() {
                @Override
                public void onSuccess() {
                    productViewHolder.hideProgress();
                }

                @Override
                public void onError() {
                    productViewHolder.hideProgress();
                }
            });
        }

        productViewHolder.tvTitle.setText(product.getName());
        productViewHolder.tvPrice.setText(product.getDisplayPrice());
        productViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(product);
                }
            }
        });
    }

    /**
     * Method inherited from RecyclerView.Adapter.
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * Method inherited from RecyclerView.Adapter.
     */
    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Sets a product list and updates the reference.
     *
     * @param productList: list of products to be added in the adapter.
     */
    public void add(List<Product> productList) {
        this.products.addAll(productList);
        notifyDataSetChanged();
    }

    /**
     * Clears the adapter.
     */
    public void clear(){
        this.products.clear();
        notifyDataSetChanged();
    }

    public boolean hasContent(){
        return products.size() > 0;
    }

    /**
     * Sets the instance which will communicate the actions.
     * @param onItemClickListener: instance listener.
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Interface for handling clicks in certain item.
     */
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    /**
     * Private class to use as View holder.
     */
    private class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvTitle;
        TextView tvPrice;
        ProgressBar progressBar;
        View view;

        ProductViewHolder(View itemView) {
            super(itemView);
            ivProduct = (ImageView) itemView.findViewById(R.id.product_view_ivProduct);
            tvTitle = (TextView) itemView.findViewById(R.id.product_view_tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.product_view_tvPrice);
            progressBar = (ProgressBar) itemView.findViewById(R.id.product_view_pbProgressBar);
            view = itemView;
        }

        void showProgress(){
            progressBar.setVisibility(View.VISIBLE);
        }

        void hideProgress(){
            progressBar.setVisibility(View.GONE);
        }
    }
}