package com.ssd.boris.shoppingcart.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ssd.boris.shoppingcart.R;
import com.ssd.boris.shoppingcart.productmodel.Groceries;
import com.ssd.boris.shoppingcart.productmodel.HotDeals;

import java.util.List;

public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.MyViewHolder> {
    private Context context;
    private List<HotDeals> hotDealsList;

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView thumbnail;
        public ProgressBar progressBar;
        SharedPreferences sharedPreferences;
        public static final String mypreferences = "a";
        public static final String Email ="email";

        public MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.productName);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            progressBar = view.findViewById(R.id.progress);
        }
    }

    public HotDealsAdapter(Context context, List<HotDeals> hotDealsList) {
        this.context = context;
        this.hotDealsList = hotDealsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        final HotDeals hotDeals = hotDealsList.get(position);
        holder.name.setText(hotDeals.getProd_name());
        holder.price.setText(String.format("%.2f",hotDeals.getProd_price()));
        Glide.with(context)
                .load(hotDeals.getProd_image())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return hotDealsList.size();
    }
}
