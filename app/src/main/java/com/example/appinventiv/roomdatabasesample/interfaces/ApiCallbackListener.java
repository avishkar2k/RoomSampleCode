package com.example.appinventiv.roomdatabasesample.interfaces;

/**
 * Created at Appinventiv on 16/4/18 at IST 11:57 AM
 * Project RoomDatabaseSample
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public interface ApiCallbackListener {

    void onSuccess(int requestCode, int responseCode, String response);

    void onError(int requestCode, int responseCode, String error);

    void onFailure(int requestCode, int responseCode, String failure);
}
