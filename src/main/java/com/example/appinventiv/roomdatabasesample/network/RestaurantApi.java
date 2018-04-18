package com.example.appinventiv.roomdatabasesample.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appinventiv.roomdatabasesample.interfaces.ApiCallbackListener;
import com.example.appinventiv.roomdatabasesample.interfaces.ApiInterface;
import com.example.appinventiv.roomdatabasesample.models.PlacesResults;
import com.example.appinventiv.roomdatabasesample.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created at Appinventiv on 9/4/18 at IST 11:38 PM
 * Project StudioProjects
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class RestaurantApi {

    private static RestaurantApi instance;

    @SuppressWarnings("FieldCanBeLocal")
    private final String GOOGLE_PLACES_API_KEY = "AIzaSyDWWNHJD2E_x6z4UEIQZ6Z5Yxs-OiasKuc";//2
    private final String KEY = "key";
    private final String KEYWORD = "keyword";
    private final String LOCATION = "location";
    private final String PAGE_TOKEN = "pagetoken";
    private final String RADIUS = "radius";
    private final String RANKBY = "rankBy";
    private final String TYPES = "types";


    public static RestaurantApi getDefault() {
        if (instance == null) instance = new RestaurantApi();
        return instance;
    }

    /**
     * method to get nearby restaurants
     */
    public void getRestaurants(final Context context, double lat, double lng, String mPageToken, final ApiCallbackListener listener) {
        try {
            if (AppUtils.isNetworkAvailable(context, true)) {
                AppUtils.getInstance().showProgressDialog(context);
                ApiInterface service = RestApiForRestaurants.createService(ApiInterface.class);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(KEY, GOOGLE_PLACES_API_KEY);
                hashMap.put(LOCATION, lat + ", " + lng);
                hashMap.put(RADIUS, "50000"); //50km radius max allowed radius
                hashMap.put(RANKBY, "distance");
                hashMap.put(TYPES, "restaurant");
                hashMap.put("offset", "2");

                if (mPageToken != null && !mPageToken.isEmpty() && 1 != Integer.parseInt(mPageToken)) {
                    hashMap.put(PAGE_TOKEN, mPageToken);
                }

                Call<ResponseBody> call = service.getRestaurants(hashMap);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String strResponse = "";
                            strResponse = response.body().string();
                            AppUtils.getInstance().hideProgressDialog();
                            Log.d(TAG, "onResponse: " + strResponse);
                            listener.onSuccess(-1, -1, strResponse);
                        } catch (Exception e) {
                            listener.onError(-1, -1, "response error");
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFailure(-1, -1, "failure");
                        t.printStackTrace();
                        AppUtils.getInstance().hideProgressDialog();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            } else {
//                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
                listener.onFailure(-1, -1, "No internet connection");
            }
        } catch (Exception e) {
            listener.onError(-1, -1, "local error");
            e.printStackTrace();
        }
    }

}
