package com.example.appinventiv.roomdatabasesample.interfaces;


import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created at Appinventiv on 10/4/18 at IST 10:25 AM
 * Project StudioProjects
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public interface ApiInterface {

    @GET("/maps/api/place/nearbysearch/json")
    Call<ResponseBody> getRestaurants(@QueryMap HashMap<String, String> map);
}
