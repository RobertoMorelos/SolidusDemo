package com.herokuapp.soliduxample.solidus.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.herokuapp.soliduxample.solidus.R;
import com.herokuapp.soliduxample.solidus.app.Config;
import com.herokuapp.soliduxample.solidus.app.Constants;
import com.herokuapp.soliduxample.solidus.models.Image;
import com.herokuapp.soliduxample.solidus.models.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roberto Morelos on 3/6/17.
 * This is the adapter for displaying the products in the Recycler View.
 */
class ProductsRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ProductsRecyclerView.class.getSimpleName();

    private List<Product> products;
    private Context context;

    /**
     * Public constructor.
     * @param context needed to use certain features.
     * @param  products the list of all products to display.
     */
    ProductsRecyclerView(Context context, List<Product> products) {
        this.products = products;
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
            String imageURL = Config.MAIN_URL + images.get(0).getProductUrl();
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
                Intent registerIntent = new Intent(context, ProductDetails.class);
                registerIntent.putExtra(Constants.PRODUCT, product);
                context.startActivity(registerIntent);
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
