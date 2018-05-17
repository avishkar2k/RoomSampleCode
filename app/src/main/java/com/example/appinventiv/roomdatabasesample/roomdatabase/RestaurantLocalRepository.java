package com.example.appinventiv.roomdatabasesample.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.appinventiv.roomdatabasesample.SampleApplication;
import com.example.appinventiv.roomdatabasesample.utils.AppUtils;

import java.util.List;

/**
 * Created at Appinventiv on 16/4/18 at IST 2:43 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class RestaurantLocalRepository {
    private RestaurantDao restaurantDao;
    private LiveData<List<RestaurantEntity>> allRestaurants;

    public RestaurantLocalRepository(Context context) {
        RestaurantDatabase restaurantDatabase =
                RestaurantDatabase.getDatabase(context);
        this.restaurantDao = restaurantDatabase.restaurantDao();
        this.allRestaurants = restaurantDao.getAllRestaurants();
    }

    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return allRestaurants;
    }

    public void insert(RestaurantEntity restaurantEntity) {
        new insertAsyncTask(restaurantDao).execute(restaurantEntity);
    }

    public void insertAll(List<RestaurantEntity> restaurantEntityList) {
        //noinspection unchecked
        new insertAllAsyncTask(restaurantDao).execute(restaurantEntityList);
    }

    public void removeAll() {
        new removeAllAsyncTask(restaurantDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<RestaurantEntity,
            Void, Void> {

        private RestaurantDao restaurantDao;

        insertAsyncTask(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtils.getInstance()
                    .showProgressDialog(SampleApplication.getInstance());
        }

        @Override
        protected Void doInBackground(final RestaurantEntity... restaurantEntities) {
            restaurantDao.insert(restaurantEntities);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AppUtils.getInstance().hideProgressDialog();
        }
    }

    private static class insertAllAsyncTask extends
            AsyncTask<List<RestaurantEntity>, Void, Void> {

        private RestaurantDao restaurantDao;

        insertAllAsyncTask(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(List<RestaurantEntity>[] lists) {
            restaurantDao.insertList(lists[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtils.getInstance()
                    .showProgressDialog(SampleApplication.getInstance());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AppUtils.getInstance().hideProgressDialog();
        }
    }

    private static class removeAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private RestaurantDao restaurantDao;

        removeAllAsyncTask(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            restaurantDao.deleteAll();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtils.getInstance().showProgressDialog(SampleApplication.getInstance());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AppUtils.getInstance().hideProgressDialog();
        }
    }

}
