package com.example.emergensui.automotive_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergensui.automotive_ui.Adapter.HospitalAdapter;
import com.example.emergensui.automotive_ui.Class.Hospital;
import com.google.android.material.navigation.NavigationView;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

// classes needed to add a marker
// classes to calculate a route
// classes needed to launch navigation UI


public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener, HospitalAdapter.ClickListener {

    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    //Original Point + Destination Point
    private Point destinationPoint;
    private Point originPoint;

    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    //variables for getting current location
    private LocationEngine locationEngine;
    private long DEFAULT_INTERNAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERNAL_IN_MILLISECONDS * 5;

    //Listen to location update
    private MapActivityLocationCallback callback = new MapActivityLocationCallback(this);

    //Variables for getting hospital based on location
    private static final String RESULT_GEOJSON_SOURCE_ID = "destination-source-id";
    private static final String CLICK_CENTER_GEOJSON_SOURCE_ID = "destination-source-id";
    private static final String LAYER_ID = "destination-source-id";
    private static final String RESULT_ICON_ID = "destination-source-id";
    private static final String CLICK_ICON_ID = "destination-source-id";
    private static final String PROPERTY_SELECTED = "selected";
    private static final int CAMERA_MOVEMENT_SPEED_IN_MILSECS = 1200;


    // variables needed to start navigation
    private Button button;

    //Drawer layout
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    //List of locations
    private FeatureCollection featureCollection;
    private List<Hospital> lstHospital; //Store a list of hospitals
    private List<Hospital> newSortedListHospital;

    //RecyclerView
    private RecyclerView hosRecyclerView;

    //Hospital Adapter
    private HospitalAdapter hosAdapter;




    //Create a feature Collection from GEOJSON file
    private void getFeatureCollectionFromJson() throws IOException
    {
        try
        {
            //Use fromJSON method to convert the GeoJSON file into a usable FeatureCollection Object
            featureCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("features.geojson"));
        }
        catch(Exception ex)
        {
            Log.e("MapActivity", "getFeatureCollectionFromJson" + ex);
        }
    }

    private String loadGeoJsonFromAsset(String fileName) {
        try
        {
            //Load the GEOJson file from the local asset folder
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");

        }
        catch(Exception ex)
        {
            Log.e("MapActivity", "loadGeoJsoFromAsset" + ex.toString());
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called in mapview activity
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // Hide status bar for full screen map
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Contains the mapview layout, called after access token configuration.
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //Drawer Navigation
        //Adding Drawer
        dl = findViewById(R.id.drawer_layerout);
        t = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation
        nv = findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;

            }
        });

        //Create a GeoJson feature collection from the GeoJSON file in the assets folder
        try
        {
            getFeatureCollectionFromJson();
        }
        catch(Exception ex)
        {
            Log.e("MapActivity", "onCreate" + ex);
            Toast.makeText(getApplicationContext(), "Failed to load file", Toast.LENGTH_LONG).show();
        }


    }

    //Add hospital icon on the layer
    private void initHospitalLocationIconSymbolLayer() {
        Style style = mapboxMap.getStyle();

        if(style != null)
        {
            //Add icon on the map
            style.addImage("hospital-icon-id", BitmapFactory.decodeResource(this.getResources(), R.drawable.hospital));

            //Create and add the GeoJsonSource to the map
            GeoJsonSource hospitalLocationGeoJsonSource = new GeoJsonSource("hospital-location-source-id");
            style.addSource(hospitalLocationGeoJsonSource);

            //Create and add the hospital location icon SymbolLayer to the map
            SymbolLayer hospitalLocationSymbolLayer = new SymbolLayer("hospitals-symbol-layer-id", "hospital-location-source-id");
            hospitalLocationSymbolLayer.withProperties(
                    iconImage("hospital-icon-id"),
                    iconAllowOverlap(true),
                    iconIgnorePlacement(true)
            );
            style.addLayer(hospitalLocationSymbolLayer);
        }
        else
        {
            Log.d("HospitalFinderActivity", "initHospitalLocationIconSymbolLayer: Style isn't ready yet");
            throw new IllegalStateException("Style isn't ready.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/emergensui/ck2xiz0v702hr1cpiti001i27"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style){
                        //addClickLayer(style);
                        //addResultLayer(style);
                        //enableLocationComponent(style);

                        addDestinationIconSymbolLayer(style);
                        enableLocationComponent(style);

                        //Set up a layer which is used to show hospital's icon
                        initHospitalLocationIconSymbolLayer();

                        //Get the current location.
                        originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                                locationComponent.getLastKnownLocation().getLatitude());

                        //Initialize Hospital list
                        lstHospital = new ArrayList<>();

                        //Create a list of Hospital from Feature Collection
                        List<Feature> featureList = featureCollection.features();

                        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("hospital-location-source-id");
                        if(source != null)
                        {
                            source.setGeoJson(FeatureCollection.fromFeatures(featureList));
                        }

                        if(featureList != null)
                        {
                            for(int i = 0; i < featureList.size(); i++)
                            {
                                Feature singleHospital = featureList.get(i);

                                //Get the single location's String properties to place in its map marker
                                String singleHospitalName = singleHospital.getStringProperty("name");
                                String singleHospitalHour = singleHospital.getStringProperty("hours");
                                String singleHospitalPhone = singleHospital.getStringProperty("phone");
                                String singleHospitalAddress = singleHospital.getStringProperty("address");

                                //Add a boolean property to use for adjusting the icon the selected hospital location
                                singleHospital.addBooleanProperty(PROPERTY_SELECTED, false);

                                //Get the single location's LatLng coordinates
                                Point singleHospitalLocation = (Point)singleHospital.geometry();

                                //Create a new LatLng object with the Position object created above
                                LatLng singleHospitalLatLng = new LatLng(singleHospitalLocation.latitude(), singleHospitalLocation.longitude());

                                //Add all the information into a list for recyclerview later usage
                                lstHospital.add(new Hospital(
                                        singleHospitalName,
                                        singleHospitalAddress,
                                        singleHospitalHour,
                                        singleHospitalPhone,
                                        singleHospitalLatLng
                                ));
                            }
                            //Nearest hospital -> Farthest hospital algorithm
                            //Calculate the distances between the current location to each hospital's coordinate
                            newSortedListHospital = distanceCalculation(lstHospital);

                            setUpRecyclerView(newSortedListHospital);
                        }

                        mapboxMap.addOnMapClickListener(MapActivity.this);
                        button = findViewById(R.id.startButton);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Pass the Original and Destination Points to the Embedded_Navigation
                                Intent navigationIntent = new Intent(MapActivity.this, embedded_navigation.class);
                                navigationIntent.putExtra("Original Point", originPoint);
                                navigationIntent.putExtra("Destination Point", destinationPoint);
                                startActivity(navigationIntent);


                                /*boolean simulateRoute = true;
                                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                        .directionsRoute(currentRoute)
                                        .shouldSimulateRoute(simulateRoute)
                                        .build();
                                // Call this method with Context from within an Activity
                                NavigationLauncher.startNavigation(MapActivity.this, options);*/

                            }
                        });
                    }
                });
    }

    private List<Hospital> distanceCalculation(List<Hospital> hospitalList)
    {
        double initialPointX = originPoint.longitude();
        double initialPointY = originPoint.latitude();
        HashMap<Hospital, Double> hmDistance = new HashMap<>();
        for(Hospital h : hospitalList)
        {
            double desPointX = h.getLocation().getLongitude();
            double desPointY = h.getLocation().getLatitude();
            hmDistance.put(h, (Math.sqrt(Math.pow(initialPointX - desPointX, 2) +
                                        Math.pow(initialPointY - desPointY, 2))));
        }



        return nearestToFarthest(hmDistance);
    }

    private List<Hospital> nearestToFarthest(HashMap<Hospital, Double> hmDistance)
    {
        //Start sorting
        //Stores Hashmap into Entries
        Set<Map.Entry<Hospital, Double>> entries = hmDistance.entrySet();

        //Set up a comparator
        Comparator<Map.Entry<Hospital, Double>> distanceComparator = new Comparator<Map.Entry<Hospital, Double>>() {
            @Override
            public int compare(Map.Entry<Hospital, Double> t1, Map.Entry<Hospital, Double> t2) {
                Double d1 = t1.getValue();
                Double d2 = t2.getValue();
                return d1.compareTo(d2);
            }
        };

        //Convert Set to List, since Sort method needs a List
        List<Map.Entry<Hospital, Double>> lstEntries = new ArrayList<>(entries);

        //Sort HashMap using Comparator
        Collections.sort(lstEntries, distanceComparator);

        //Using a new List to contain the sorted Entries
        List<Hospital> sortedHospital = new ArrayList<>();
        for(Map.Entry<Hospital, Double> entry : lstEntries)
        {
            sortedHospital.add(entry.getKey());
        }
        return sortedHospital;
    }


    private void setUpRecyclerView(List<Hospital> hospitalList)
    {
        //Recycler View
        hosRecyclerView = findViewById(R.id.hospitalRecyclerView);
        hosRecyclerView.setHasFixedSize(true);
        hosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hosAdapter = new HospitalAdapter(this, hospitalList, this);
        hosRecyclerView.setAdapter(hosAdapter);
        hosRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onItemClick(int position)
    {
        //Get the selected individual location via recyclerview
        Hospital singleHos = newSortedListHospital.get(position);

        //Evaluate each Feature's "select state" to appropriately style the location's icon
        List<Feature> featureList = featureCollection.features();
        //Point selectedLocation = (Point)featureCollection.features().get(position).geometry();
        Point selectedLocation = Point.fromLngLat(singleHos.getLocation().getLongitude(),
                                                    singleHos.getLocation().getLatitude());

        for(int i = 0; i < featureList.size(); i++)
        {
            if(featureList.get(i).getStringProperty("name").equals(singleHos.getName()))
            {
                System.out.println(featureList.get(i).getStringProperty("name") + " - " + singleHos.getName());
                if(featureSelectStatus(i))
                {
                    setFeatureSelectState(featureList.get(i), false);
                }
                else
                {
                    setSelected(i);
                }
            }
            else
            {
                setFeatureSelectState(featureList.get(i), false);
            }
        }

        //Reposition the map camera target to the selected marker
        if(selectedLocation != null)
        {
            repositionMapCamera(selectedLocation);
        }

        //Check for an internet connection before making the call to Mapbox Directions API
        if(deviceHasInternetConnection())
        {
            //Start call to the Mapbox Directions API
            if(selectedLocation != null)
            {
                //getInformationFromDirectionApi(selectedLocation, true, null);
                getRoute(originPoint, selectedLocation);
            }
            else
            {
                Toast.makeText(this, "Unable to connect", Toast.LENGTH_LONG).show();
            }
        }

    }

    //Checks whether a Feature's boolean "selected" property is true or false
    private boolean featureSelectStatus(int index)
    {
        if(featureCollection == null)
        {
            return false;
        }
        return featureCollection.features().get(index).getBooleanProperty(PROPERTY_SELECTED);
    }

    //Selects the state of feature
    private void setFeatureSelectState(Feature feature, boolean selectedState)
    {
        feature.properties().addProperty(PROPERTY_SELECTED, selectedState);
        refreshSource();
    }

    //Updates the display of data on the map after the FeatureCollection has been modified
    private void refreshSource()
    {
        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("hospital-location-source-id");
        if(source != null && featureCollection != null)
        {
            source.setGeoJson(featureCollection);
        }
    }

    //Set a feature selected state
    private void setSelected(int index)
    {
        Feature feature = featureCollection.features().get(index);
        setFeatureSelectState(feature, true);
        refreshSource();
    }

    private void repositionMapCamera(Point newTarget)
    {
        CameraPosition newCameraPosition = new CameraPosition.Builder()
                .target(new LatLng(newTarget.latitude(), newTarget.longitude()))
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition), CAMERA_MOVEMENT_SPEED_IN_MILSECS);
    }

    private boolean deviceHasInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


    //ADd a map layer which will show a marker icon where the map was clicked
    private void addClickLayer(@NonNull Style loadedMapStyle)
    {
        loadedMapStyle.addImage(CLICK_ICON_ID, BitmapFactory.decodeResource(this.getResources(), R.drawable.map_marker_dark));
        loadedMapStyle.addLayer(new SymbolLayer("click-layer", CLICK_CENTER_GEOJSON_SOURCE_ID).withProperties(
                iconImage(CLICK_ICON_ID),
                iconOffset(new Float[] {0f, -12f}),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)

        ));
    }

    //Add a map layer which will show marker icons for all of the Tilequery API Results
    private void addResultLayer(@NonNull Style loadedMapStyle)
    {
        //Add the marker image to map
        loadedMapStyle.addImage(RESULT_ICON_ID, BitmapFactory.decodeResource(this.getResources(), R.drawable.map_marker_dark));

        //Retrieve GeoJSON information from the Mapbox Tilequery API
        loadedMapStyle.addSource(new GeoJsonSource(RESULT_GEOJSON_SOURCE_ID));

        loadedMapStyle.addLayer(new SymbolLayer(LAYER_ID, RESULT_GEOJSON_SOURCE_ID).withProperties(
                iconImage(RESULT_ICON_ID),
                iconOffset(new Float[] {0f, -12f}),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)
        ));

    }



    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull final LatLng point) {
        destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        /*originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());*/

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }
        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapboxBlue);
        return true;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

// Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if(PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            locationComponent =
                    mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    //Set up the LocationEngine and the parameters for querying the device's location
    @SuppressLint("MissingPermission")
    private void initLocationEngine()
    {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERNAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this,R.string.user_location_permission_explanation,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted,
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"} )
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Prevent leaks
        if(locationEngine != null)
        {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    //This class is used for listening the current location
    private class MapActivityLocationCallback implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MapActivity> activityWeakReference;
        private TextView txt_nv_header;

        MapActivityLocationCallback(MapActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }


        //These methods will run if the location is changed
        @Override
        public void onSuccess(LocationEngineResult result) {
            MapActivity mapActivity = activityWeakReference.get();
            txt_nv_header = findViewById(R.id.txtGPS);

            if (mapActivity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

                txt_nv_header.setText(result.getLastLocation().getLongitude() + " | " +
                                        result.getLastLocation().getLatitude());

                //Pass new location to the Map SDK's Location Component
                if (mapActivity.mapboxMap != null && result.getLastLocation() != null) {
                    mapActivity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }

        }

        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            MapActivity mapActivity = activityWeakReference.get();
            if (mapActivity != null) {
                Toast.makeText(mapActivity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

}


