package com.example.appinventiv.roomdatabasesample.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.example.appinventiv.roomdatabasesample.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created at Appinventiv on 9/4/18 at IST 11:39 PM
 * Project StudioProjects
 *
 * @author appinventiv
 * @version 1.0
 * @since 1.0
 */

public class AppUtils {

    private static AppUtils instance;
    private static boolean mNetworkStatus;
    private ProgressDialog mDialog;

    public static AppUtils getInstance() {
        if (instance == null) instance = new AppUtils();
        return instance;
    }


    /**
     * show progress dialog
     */
    public void showProgressDialog(Context context) {
        hideProgressDialog();
        if (context != null) {
            mDialog = new ProgressDialog(context, R.style.ProgressDialogTheme);
            mDialog.setIndeterminate(true);
            mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mDialog.setCancelable(false);
            mDialog.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.progressbar_handler));
            mDialog.show();
        }
    }

    /**
     * Method to show non-cancellable progress dialog
     */
    public void showProgressDialogNonCancellable(Activity mActivity) {
        hideProgressDialog();
        mDialog = new ProgressDialog(mActivity, R.style.ProgressDialogTheme);
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mDialog.setIndeterminateDrawable(ContextCompat.getDrawable(mActivity, R.drawable.progressbar_handler));
        mDialog.show();
    }

    /**
     * hide progress dialog if showing
     */
    public void hideProgressDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }


    /**
     * method to check whether internet connection is vailable or not
     *
     * @param context
     * @param refresh flag to refresh the network status
     * @return false only when there is no network connection and  i.e. {refresh = true}
     * this is required as client needs to completely remove the network error message {@link : https://www.basecamp.com/2897129/projects/13519342/messages/73092430?enlarge=317448584#attachment_317448584}
     */
    public static boolean isNetworkAvailable(Context context, boolean refresh) {
       /* ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();*/

        if (refresh) {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivity != null;
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            mNetworkStatus = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        }

        return mNetworkStatus || !refresh;


    }

    /**
     * Method to request permission
     *
     * @return returns the status of location permissions, if not granted make the request.
     */
    public boolean hasRequiredPermission(Activity mActivity) {
        return (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


    /**
     * Method to make the location request
     */
    public void requestLocationPermission(int requestCode, @Nullable Activity mActivity, @Nullable Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
            } else {
                assert mActivity != null;
                mActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
            }
        }
    }


    /**
     * Method to get the address line with lat and long
     *
     * @param context  context
     * @param location {@link LatLng}
     * @return string
     */
    public String getAddressLine(Context context, com.example.appinventiv.roomdatabasesample.models.Location location) {

        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> addresses = geocoder.getFromLocation(location.getLat(), location.getLng(), 1);

            return addresses.get(0).getAddressLine(0) +
                    addresses.get(0).getLocale() +
                    addresses.get(0).getAdminArea() +
                    addresses.get(0).getCountryName();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
