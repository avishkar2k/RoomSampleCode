package com.example.appinventiv.roomdatabasesample.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created at Appinventiv on 16/4/18 at IST 1:18 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */


@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RestaurantEntity... restaurantEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<RestaurantEntity> restaurantEntityList);

    @Query("DELETE FROM restaurant_table")
    void deleteAll();

    @Query("DELETE FROM restaurant_table WHERE restaurant_name = :restaurantName")
    void delete(String restaurantName);

    @Query("SELECT * FROM restaurant_table WHERE restaurant_name = :restaurantName")
    RestaurantEntity getAllRestaurants(String restaurantName);

    @Query("SELECT * FROM restaurant_table")
    LiveData<List<RestaurantEntity>> getAllRestaurants();

    @Update
    void updateRestaurantEntities(RestaurantEntity... restaurantEntities);

    @Update
    void updateRestaurantLists(List<RestaurantEntity> restaurantEntityList);

}
