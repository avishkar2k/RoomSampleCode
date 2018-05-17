package com.example.appinventiv.roomdatabasesample.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.appinventiv.roomdatabasesample.R;
import com.example.appinventiv.roomdatabasesample.roomdatabase.RestaurantEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created at Appinventiv on 16/4/18 at IST 3:36 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {


    private Context context;
    private List<RestaurantEntity> restaurantEntityList;
//    private Picasso picasso;

    public RestaurantListAdapter(Context context, List<RestaurantEntity> restaurantEntityList) {
        this.context = context;
        this.restaurantEntityList = restaurantEntityList;

//        picasso = new Picasso.Builder(context).build();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.layout_recycler_view_item_restaurants,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantEntity restaurantEntity = restaurantEntityList.get(position);

        holder.tvTitle.setText(restaurantEntity.getRestaurantName());
        holder.tvRatings.setText(restaurantEntity.getRatings());

        if (restaurantEntity.getImageUrl() != null && !restaurantEntity.getImageUrl().isEmpty()) {
            Picasso.get().load(restaurantEntity.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.ivLogoIcon);
        }
    }

    @Override
    public int getItemCount() {
        return restaurantEntityList != null && !restaurantEntityList.isEmpty() ? restaurantEntityList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_logo_icon)
        ImageView ivLogoIcon;
        @BindView(R.id.tv_title)
        AppCompatTextView tvTitle;
        @BindView(R.id.tv_ratings)
        AppCompatTextView tvRatings;
        @BindView(R.id.rl_item_container)
        RelativeLayout rlItemContainer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            rlItemContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //show dialog
            showItemDetailDialog(getAdapterPosition());
        }
    }

    private void showItemDetailDialog(int adapterPosition) {
        RestaurantEntity restaurantEntity = restaurantEntityList.get(adapterPosition);
        Dialog dialog = new Dialog(context, R.style.ThemeOverlay_AppCompat_Dialog);
        dialog.setContentView(R.layout.layout_dialog_item_details);
        ImageView ivLogoIcon = dialog.findViewById(R.id.iv_logo_icon);
        AppCompatTextView tvTitle = dialog.findViewById(R.id.tv_title);
        AppCompatTextView tvRatings = dialog.findViewById(R.id.tv_ratings);
        AppCompatTextView tvOpeningHours = dialog.findViewById(R.id.tv_opening_hours);
        AppCompatTextView tvAddress = dialog.findViewById(R.id.tv_address);


        if (restaurantEntity.getImageUrl() != null && !restaurantEntity.getImageUrl().isEmpty()) {
            //set image
            Picasso.get().load(restaurantEntity.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ivLogoIcon);
        }

        //set title
        tvTitle.setText(restaurantEntity.getRestaurantName());

        //set ratings
        tvRatings.setText(restaurantEntity.getRatings());

        //set open hours
        tvOpeningHours.setText(restaurantEntity.getOpeningHours());

        //set address
        tvAddress.setText(restaurantEntity.getLocationAddress());

        dialog.setCancelable(true);
        dialog.show();
    }
}
