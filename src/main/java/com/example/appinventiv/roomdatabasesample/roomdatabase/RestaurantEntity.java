package com.example.appinventiv.roomdatabasesample.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created at Appinventiv on 16/4/18 at IST 12:29 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

@Entity(tableName = "restaurant_table")
public class RestaurantEntity {

    @SuppressWarnings("NullableProblems")
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "restaurant_name")
    private String restaurantName;

    @ColumnInfo(name = "address")
    private String locationAddress;

    @ColumnInfo(name = "open_hours")
    private String openingHours;

    @ColumnInfo(name = "ratings")
    private String ratings;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @NonNull
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(@NonNull String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RestaurantEntity(@NonNull String restaurantName, String locationAddress, String openingHours, String ratings, String imageUrl) {
        this.restaurantName = restaurantName;
        this.locationAddress = locationAddress;
        this.openingHours = openingHours;
        this.ratings = ratings;
        this.imageUrl = imageUrl;
    }
}
