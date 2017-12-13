package com.lesgood.guru.ui.map;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.maps.DataParser;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;


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

      // Getting URL to the Google Directions API
      String url = getUrl(origin, dest);
      Log.d("onMapClick", url.toString());
      FetchUrl FetchUrl = new FetchUrl();

      // Start downloading json data from Google Directions API
      FetchUrl.execute(url);
      //move map camera
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



  private String getUrl(LatLng origin, LatLng dest) {

    // Origin of route
    String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

    // Destination of route
    String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


    // Sensor enabled
    String sensor = "sensor=false";

    // Building the parameters to the web service
    String parameters = str_origin + "&" + str_dest + "&" + sensor;

    // Output format
    String output = "json";

    // Building the url to the web service
    String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


    return url;
  }

  /**
   * A method to download json data from url
   */
  private String downloadUrl(String strUrl) throws IOException {
    String data = "";
    InputStream iStream = null;
    HttpURLConnection urlConnection = null;
    try {
      URL url = new URL(strUrl);

      // Creating an http connection to communicate with url
      urlConnection = (HttpURLConnection) url.openConnection();

      // Connecting to url
      urlConnection.connect();

      // Reading data from url
      iStream = urlConnection.getInputStream();

      BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

      StringBuffer sb = new StringBuffer();

      String line = "";
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

      data = sb.toString();
      Log.d("downloadUrl", data.toString());
      br.close();

    } catch (Exception e) {
      Log.d("Exception", e.toString());
    } finally {
      iStream.close();
      urlConnection.disconnect();
    }
    return data;
  }

  // Fetches data from url passed
  private class FetchUrl extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... url) {

      // For storing data from web service
      String data = "";

      try {
        // Fetching the data from web service
        data = downloadUrl(url[0]);
        Log.d("Background Task data", data.toString());
      } catch (Exception e) {
        Log.d("Background Task", e.toString());
      }
      return data;
    }

    @Override
    protected void onPostExecute(String result) {
      super.onPostExecute(result);

      ParserTask parserTask = new ParserTask();

      // Invokes the thread for parsing the JSON data
      parserTask.execute(result);

    }
  }

  /**
   * A class to parse the Google Places in JSON format
   */
  private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

      JSONObject jObject;
      List<List<HashMap<String, String>>> routes = null;

      try {
        jObject = new JSONObject(jsonData[0]);
        Log.d("ParserTask",jsonData[0].toString());
        DataParser parser = new DataParser();
        Log.d("ParserTask", parser.toString());

        // Starts parsing data
        routes = parser.parse(jObject);
        Log.d("ParserTask","Executing routes");
        Log.d("ParserTask",routes.toString());

      } catch (Exception e) {
        Log.d("ParserTask",e.toString());
        e.printStackTrace();
      }
      return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
      ArrayList<LatLng> points;
      PolylineOptions lineOptions = null;

      // Traversing through all the routes
      for (int i = 0; i < result.size(); i++) {
        points = new ArrayList<>();
        lineOptions = new PolylineOptions();

        // Fetching i-th route
        List<HashMap<String, String>> path = result.get(i);

        // Fetching all the points in i-th route
        for (int j = 0; j < path.size(); j++) {
          HashMap<String, String> point = path.get(j);

          double lat = Double.parseDouble(point.get("lat"));
          double lng = Double.parseDouble(point.get("lng"));
          LatLng position = new LatLng(lat, lng);

          points.add(position);
        }

        // Adding all the points in the route to LineOptions
        lineOptions.addAll(points);
        lineOptions.width(10);
        lineOptions.color(Color.RED);

        Log.d("onPostExecute","onPostExecute lineoptions decoded");

      }

      // Drawing polyline in the Google Map for the i-th route
      if(lineOptions != null) {
        mMap.addPolyline(lineOptions);
      }
      else {
        Log.d("onPostExecute","without Polylines drawn");
      }
    }
  }

  protected synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
    mGoogleApiClient.connect();
  }

  /*@Override
  public void onConnected(Bundle bundle) {

    mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(1000);
    mLocationRequest.setFastestInterval(1000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

  }*/

  /*@Override
  public void onConnectionSuspended(int i) {

  }*/

  /*@Override
  public void onLocationChanged(Location location) {

    mLastLocation = location;
    if (mCurrLocationMarker != null) {
      mCurrLocationMarker.remove();
    }

    //Place current location marker
    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng);
    markerOptions.title("Current Position");
    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
    mCurrLocationMarker = mMap.addMarker(markerOptions);

    //move map camera
    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    //stop location updates
    if (mGoogleApiClient != null) {
      LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

  }*/

  /*@Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

  }*/

  public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
  public boolean checkLocationPermission(){
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

      // Asking user if explanation is needed
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
              Manifest.permission.ACCESS_FINE_LOCATION)) {

        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

        //Prompt the user once explanation has been shown
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);


      } else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
      }
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_LOCATION: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          // permission was granted. Do the
          // contacts-related task you need to do.
          if (ContextCompat.checkSelfPermission(this,
                  Manifest.permission.ACCESS_FINE_LOCATION)
                  == PackageManager.PERMISSION_GRANTED) {

            if (mGoogleApiClient == null) {
              buildGoogleApiClient();
            }
            mMap.setMyLocationEnabled(true);
          }

        } else {

          // Permission denied, Disable the functionality that depends on this permission.
          Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
        }
        return;
      }

      // other 'case' lines to check for other permissions this app might request.
      // You can add here other case statements according to your requirement.
    }
  }
}
