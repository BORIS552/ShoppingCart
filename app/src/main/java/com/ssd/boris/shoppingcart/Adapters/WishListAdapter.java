package com.ssd.boris.shoppingcart.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ssd.boris.shoppingcart.IConstants;
import com.ssd.boris.shoppingcart.R;
import com.ssd.boris.shoppingcart.Services.VolleyService;
import com.ssd.boris.shoppingcart.productmodel.Electronics;
import com.ssd.boris.shoppingcart.productmodel.WishList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {
    private Context context;
    private List<WishList> wishListList;
    SharedPreferences sharedPreferences;
    public static final String mypreferences = "a";
    public static final String Email ="email";

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView thumbnail;
        public ProgressBar progressBar;
        public Button buynow;
        public Button removeWishlistItem;


        public MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.productName);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            progressBar = view.findViewById(R.id.progress);
            buynow = view.findViewById(R.id.buynow);
            removeWishlistItem = view.findViewById(R.id.remove_wishlist_item);
        }
    }

    public WishListAdapter(Context context, List<WishList> wishListList) {
        this.context = context;
        this.wishListList = wishListList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_wishlist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        final WishList wishList = wishListList.get(position);
        holder.name.setText(wishList.getProd_name());
        holder.price.setText(String.format("%.2f",wishList.getProd_price()));
        Glide.with(context)
                .load(wishList.getProd_image())
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

        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Buy Now: "+wishList.getProd_category() +" : "+ wishList.getProd_id(), Toast.LENGTH_LONG).show();
            }
        });

        holder.removeWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Removing from your wishlist....");
                progressDialog.show();
                Toast.makeText(context, "Wishlist: "+wishList.getProd_category() +" : "+ wishList.getProd_id(), Toast.LENGTH_LONG).show();
                String url = IConstants.wishlist_remove_item;
                //creating json data for wishlist...
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("prod_id", wishList.getProd_id());
                    jsonObject.put("user_email",sharedPreferences.getString(Email,""));
                    System.out.println("----------------------------------**************************" + jsonObject.toString());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            progressDialog.hide();
                            Toast.makeText(context, "Removed From Wishlist", Toast.LENGTH_SHORT).show();

                            wishListList.remove(position);
                            notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error:.. "+ error.getMessage());
                            progressDialog.hide();
                        }
                    });
                    VolleyService.getInstance().addToRequestQueue(jsonObjectRequest);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return wishListList.size();
    }
}
