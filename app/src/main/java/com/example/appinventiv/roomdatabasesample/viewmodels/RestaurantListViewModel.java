package com.example.appinventiv.roomdatabasesample.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.appinventiv.roomdatabasesample.SampleApplication;
import com.example.appinventiv.roomdatabasesample.roomdatabase.RestaurantEntity;
import com.example.appinventiv.roomdatabasesample.roomdatabase.RestaurantLocalRepository;
import com.example.appinventiv.roomdatabasesample.utils.AppUtils;

import java.util.List;

/**
 * Created at Appinventiv on 16/4/18 at IST 3:30 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class RestaurantListViewModel extends AndroidViewModel {

    private RestaurantLocalRepository restaurantLocalRepository;
    private LiveData<List<RestaurantEntity>> mAllRestaurantsList;

    public RestaurantListViewModel(@NonNull Application application) {
        super(application);
        restaurantLocalRepository = new RestaurantLocalRepository(application);
        mAllRestaurantsList = restaurantLocalRepository.getAllRestaurants();
    }

    public LiveData<List<RestaurantEntity>> getAllRestaurantsList() {
        return mAllRestaurantsList;
    }

    public void insert(RestaurantEntity restaurantEntity) {
        restaurantLocalRepository.insert(restaurantEntity);
    }

    public void insertAll(List<RestaurantEntity> restaurantEntityList) {
        restaurantLocalRepository.insertAll(restaurantEntityList);
    }

    public void removeAll() {
        restaurantLocalRepository.removeAll();
    }

}
