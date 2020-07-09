package sg.edu.rp.c346.p08_locatingplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btnNorth, btnCentral, btnEast;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                // Q1
                LatLng sg = new LatLng(1.3521, 103.8198);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(sg, 11));
                //
                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);
                // Q2
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
                // Q4
                LatLng north = new LatLng(1.461708, 103.8131500);
                final Marker northHQ = map.addMarker(new
                        MarkerOptions()
                        .position(north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 ")
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on)));


                LatLng central = new LatLng(1.300542, 103.841226);
                final Marker centralHQ = map.addMarker(new
                        MarkerOptions()
                .position(central)
                .title("HQ - Central")
                .snippet("Block 3A, Orchard Ave 3, 134542 ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng east = new LatLng(1.350057, 103.934452);
                final Marker eastHQ = map.addMarker(new
                        MarkerOptions()
                        .position(east)
                        .title("HQ - East")
                        .snippet("Block 555, Tampines Ave 3, 287788 ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(northHQ)){
                            Toast.makeText(MainActivity.this, "HQ - North",Toast.LENGTH_LONG).show();
                        } else if (marker.equals(centralHQ)){
                            Toast.makeText(MainActivity.this, "HQ - Central",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "HQ - East",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });

            }
        });

        btnNorth = findViewById(R.id.btnNorth);
        btnCentral = findViewById(R.id.btnCentral);
        btnEast = findViewById(R.id.btnEast);

        // Intermediate Enhancement
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng north = new LatLng(1.461708, 103.8131500);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(north, 11));
            }
        });

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng central = new LatLng(1.300542, 103.841226);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(central, 11));
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng east = new LatLng(1.350057, 103.934452);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(east, 11));
            }
        });

        // Advanced Enhancement
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                Log.d("asdhsdsdsd", "onItemSelected: " + adapterView.getItemAtPosition(i));
                if(selected.equalsIgnoreCase("North")){
                    LatLng north = new LatLng(1.461708, 103.8131500);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(north, 11));
                }
                else if (selected.equalsIgnoreCase("Central")){
                    LatLng central = new LatLng(1.300542, 103.841226);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(central, 11));
                }
                else if (selected.equalsIgnoreCase("East")){
                    LatLng east = new LatLng(1.350057, 103.934452);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(east, 11));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
