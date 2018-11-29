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

import com.android.volley.Request.Method;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class ElectronicsAdapter extends RecyclerView.Adapter<ElectronicsAdapter.MyViewHolder> {
    private Context context;
    private List<Electronics> electronicsList;
    SharedPreferences sharedPreferences;
    public static final String mypreferences = "a";
    public static final String Email ="email";

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView thumbnail;
        public ProgressBar progressBar;
        public Button buynow;
        public Button wishlist;


        public MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.productName);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            progressBar = view.findViewById(R.id.progress);
            buynow = view.findViewById(R.id.buynow);
            wishlist = view.findViewById(R.id.wishlist);
        }
    }

    public ElectronicsAdapter(Context context, List<Electronics> electronicsList) {
        this.context = context;
        this.electronicsList = electronicsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        final Electronics electronics = electronicsList.get(position);
        holder.name.setText(electronics.getProd_name());
        holder.price.setText(String.format("%.2f",electronics.getProd_price()));
        Glide.with(context)
                .load(electronics.getProd_image())
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
                Toast.makeText(context, "Buy Now: "+electronics.getProd_category() +" : "+ electronics.getProd_id(), Toast.LENGTH_LONG).show();
            }
        });

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Adding to your wishlist....");
                progressDialog.show();
                Toast.makeText(context, "Wishlist: "+electronics.getProd_category() +" : "+ electronics.getProd_id(), Toast.LENGTH_LONG).show();
                String url = IConstants.wishlist_post_url;
                //creating json data for wishlist...
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("prod_id", electronics.getProd_id());
                    jsonObject.put("user_email",sharedPreferences.getString(Email,""));

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            progressDialog.hide();
                            Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
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

                //data post using volley for wishlist....

            }
        });

    }

    @Override
    public int getItemCount() {
        return electronicsList.size();
    }
}
