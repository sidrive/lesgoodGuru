package com.lesgood.guru.ui.map;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
/*import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;*/
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.Order;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
    ConnectionCallbacks, OnConnectionFailedListener, OnCameraIdleListener {

  private GoogleMap mMap;

  private IconGenerator iconGenerator;
  private Marker markerNewLocation;
  private SupportMapFragment mapFragment;
  private LocationRequest locationRequest;
  private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
  private long FASTEST_INTERVAL = 2000; /* 2 sec */
  private GoogleApiClient mGoogleApiClient;
  private LocationManager lm;
  private android.location.Location mlocation;
  private LatLng siswaLL;
  public static void start(Context context,double lat, double lng) {
    Intent starter = new Intent(context, MapsActivity.class);
    starter.putExtra("lat",lat);
    starter.putExtra("lng",lng);
    context.startActivity(starter);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    iconGenerator = new IconGenerator(this);
    iconGenerator.setStyle(IconGenerator.STYLE_BLUE);
    mGoogleApiClient = new Builder(getApplicationContext())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
    mGoogleApiClient.connect();
    locationRequest = new LocationRequest();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(UPDATE_INTERVAL);
    locationRequest.setFastestInterval(FASTEST_INTERVAL);

    if (getIntent().getExtras()!=null){
      double lat = getIntent().getExtras().getDouble("lat");
      double lng = getIntent().getExtras().getDouble("lng");
      siswaLL = new LatLng(lat,lng);
    }
    mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    getCurrentLocationUser();
  }

  public void markUserLocation(LatLng maplocation, String title) {



  }

  private void getCurrentLocationUser() {
    lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (ActivityCompat
        .checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
        && ActivityCompat
        .checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      if (isNetworkEnabled) {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e("getCurrentLocationUser", "MapsActivity" + mlocation);
        markUserLocation(new LatLng(mlocation.getLatitude(),mlocation.getLongitude()),"Pengajar");
      } else if (isGPSEnabled) {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e("getCurrentLocationUser", "MapsActivity" + mlocation);
        markUserLocation(new LatLng(mlocation.getLatitude(),mlocation.getLongitude()),"Pengajar");
      }
    }
  }

  public void mapConnect() {
    mGoogleApiClient.connect();
  }

  public void mapDisconnect() {
    if (mGoogleApiClient.isConnected()) {

      mGoogleApiClient.disconnect();
    }
  }

  private android.location.LocationListener locationListener = new android.location.LocationListener() {
    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };


  public void successAddLocation(Location location) {

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setMapType(MAP_TYPE_NORMAL);
    if (mlocation!=null){
      LatLng guruLL = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guruLL, 16));
      mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(guruLL, 16));
      mMap.addMarker(new MarkerOptions().position(guruLL).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("Guru"))));
    }
    if (siswaLL!=null){
      mMap.addMarker(new MarkerOptions().position(siswaLL).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("siswa"))));
    }
    if (mlocation!=null && siswaLL!=null){
      LatLng origin = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
      LatLng dest = siswaLL;
      /*DrawRouteMaps.getInstance(this)
          .draw(origin, dest, mMap);*/

      LatLngBounds bounds = new LatLngBounds.Builder()
          .include(origin)
          .include(dest).build();
      Point displaySize = new Point();
      getWindowManager().getDefaultDisplay().getSize(displaySize);
      mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
    }

    mMap.setOnCameraIdleListener(this);
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

      return;
    }
    mMap.setMyLocationEnabled(true);
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {

  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override
  public void onCameraIdle() {

  }
}
