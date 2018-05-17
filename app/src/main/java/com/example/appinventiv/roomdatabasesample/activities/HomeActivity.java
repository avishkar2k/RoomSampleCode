package com.example.appinventiv.roomdatabasesample.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.example.appinventiv.roomdatabasesample.R;
import com.example.appinventiv.roomdatabasesample.SampleApplication;
import com.example.appinventiv.roomdatabasesample.adapters.RestaurantListAdapter;
import com.example.appinventiv.roomdatabasesample.interfaces.ApiCallbackListener;
import com.example.appinventiv.roomdatabasesample.models.PlacesResults;
import com.example.appinventiv.roomdatabasesample.models.Result;
import com.example.appinventiv.roomdatabasesample.network.RestaurantApi;
import com.example.appinventiv.roomdatabasesample.roomdatabase.RestaurantEntity;
import com.example.appinventiv.roomdatabasesample.utils.AppUtils;
import com.example.appinventiv.roomdatabasesample.utils.ViewModelFactory;
import com.example.appinventiv.roomdatabasesample.viewmodels.RestaurantListViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity implements ApiCallbackListener {
    private final int PERMISSION_REQUEST_CODE = 4,
            PLACE_PICKER_REQUEST = 5,
            REQUEST_CHECK_SETTINGS = 6;

    private final String LOG_TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.rv_restaurant_list)
    RecyclerView rvRestaurantList;
    @BindView(R.id.srl_restaurant_list)
    SwipeRefreshLayout srlRestaurantList;
    private LatLng latLng;
    private List<RestaurantEntity> restaurantEntityList;
    private RestaurantListViewModel viewModel;

    /*@Inject
    private ViewModelFactory viewModelFactory;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        restaurantEntityList = new ArrayList<>();

        //instantiate view model
        viewModel = ViewModelProviders.of(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(RestaurantListViewModel.class);


        //setup observer for the live data
        viewModel.getAllRestaurantsList()
                .observe(this, new Observer<List<RestaurantEntity>>() {
            @Override
            public void onChanged(@Nullable List<RestaurantEntity>
                                          restaurantEntities) {

                //update the recycler view with new elements
                if (restaurantEntities != null) {
                    if (!restaurantEntityList.isEmpty())
                        restaurantEntityList.clear();
                    restaurantEntityList.addAll(restaurantEntities);
                    rvRestaurantList.getAdapter().notifyDataSetChanged();
                }

            }
        });

        //set up recycler view
        recyclerViewSetup();
    }

    private void recyclerViewSetup() {

        //initialize the adapter
        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(this,
                restaurantEntityList);
        //set adapter
        rvRestaurantList.setAdapter(restaurantListAdapter);

        //set layout manager
        rvRestaurantList.setLayoutManager(new LinearLayoutManager(HomeActivity.this,
                LinearLayoutManager.VERTICAL, false));


        //recycler view refresh
        srlRestaurantList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    srlRestaurantList.setRefreshing(false);
                    RestaurantApi.getDefault().getRestaurants(HomeActivity.this,
                            latLng.latitude, latLng.longitude, null, HomeActivity.this);
                    Log.d(LOG_TAG, latLng.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    //exception in case latlng is null
                }
            }
        });

    }


    private void placePickerSetup() {
        if (AppUtils.getInstance().hasRequiredPermission(this)) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        } else {
            AppUtils.getInstance().requestLocationPermission(PLACE_PICKER_REQUEST, this, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (!AppUtils.getInstance().hasRequiredPermission(this)) {
                    boolean showRationaleAccessFineLocation =
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                    boolean showRationaleAccessCoarseLocation =
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (showRationaleAccessCoarseLocation || showRationaleAccessFineLocation) {
                        locationAccessDialog(PLACE_PICKER_REQUEST);
                    }
                } else { //call source method
                    placePickerSetup();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);
                    latLng = place.getLatLng();
                    RestaurantApi.getDefault().getRestaurants(this, latLng.latitude, latLng.longitude,
                            null, HomeActivity.this);
                    etAddress.setSingleLine();
                    etAddress.setText(place.getAddress().toString().trim());
                    Log.d(LOG_TAG, String.valueOf(place.getLatLng()));
                }
                break;
        }
    }

    /**
     * Method to request location permission
     */
    private void locationAccessDialog(final int requestCode) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(getString(R.string.s_message_location_permission_request))        // set dialog message
                .setCancelable(false)
                .setPositiveButton(getString(R.string.s_allow), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //request from settings directly
                        displayLocationSettingsRequest();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(R.string.s_deny), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog mAlertDialogLocationAccess = alertDialogBuilder.create();         // create alert dialog
        mAlertDialogLocationAccess.show();
    }

    @OnClick(R.id.et_address)
    public void onViewClicked() {
        placePickerSetup();
    }


    private void displayLocationSettingsRequest() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(10000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(LOG_TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(LOG_TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(LOG_TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOG_TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, String response) {
        try {
            PlacesResults placesResults = new ObjectMapper().readValue(response, PlacesResults.class);

            if (placesResults != null && placesResults.getResults() != null && !placesResults.getResults().isEmpty()) {
                viewModel.removeAll();
                List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
                for (Result r : placesResults.getResults()) {
                    /*viewModel.insert(new RestaurantEntity(r.getName(),
                            AppUtils.getInstance().getAddressLine(HomeActivity.this, r.getGeometry().getLocation()),
                            "open now : " + (r.getOpeningHours().getOpenNow() ? "yes" : "no"),
                            r.getRating().toString(),
                            r.getIcon()));*/

                    String openStatus = "open now : " + (r.getOpeningHours() != null ? (r.getOpeningHours().getOpenNow() ? "yes" : "no") : "n/a");
                    String ratings = r.getRating() != null ? r.getRating().toString() : "n/a";

                    restaurantEntityList.add(new RestaurantEntity(r.getName(),
                            AppUtils.getInstance().getAddressLine(HomeActivity.this, r.getGeometry().getLocation()),
                            openStatus,
                            ratings,
                            r.getIcon()));
                }

                if (!restaurantEntityList.isEmpty())
                    viewModel.insertAll(restaurantEntityList);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(int requestCode, int responseCode, String error) {

    }

    @Override
    public void onFailure(int requestCode, int responseCode, String failure) {

    }
}
