package savepet.example.com.savepet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static savepet.example.com.savepet.MainActivity.FINE_LOCATION_PERMISOS;
import static savepet.example.com.savepet.MainActivity.LOCALIZACION;

public class maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button aceptar;
    ImageView buscar;
    EditText busqueda;
    LatLng latLngFinal;
    private static final int COURSE_LOCATION = 5;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] permisos = {
                Manifest.permission.ACCESS_COARSE_LOCATION};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        busqueda = findViewById(R.id.busqueda);
        buscar = findViewById(R.id.buscar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        aceptar = findViewById(R.id.aceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localizacion = new Intent();
                localizacion.putExtra("lat", latLngFinal.latitude);
                localizacion.putExtra("lng", latLngFinal.longitude);
                setResult(RESULT_OK, localizacion);
                finish();
            }
        });
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buscar.setClickable(false);
        } else {
            ActivityCompat.requestPermissions(this,
                    permisos,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocalizacion();
            }
        });
    }


    private void geoLocalizacion() {

        String busquedaString = busqueda.getText().toString();

        Geocoder geocoder = new Geocoder(maps.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(busquedaString, 1);
        } catch (IOException e) {
            Log.e("error", "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.clear();
            MarkerOptions mark = new MarkerOptions().position(latLng);
            mMap.addMarker(mark);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));
            latLngFinal = mark.getPosition();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng latLng = new LatLng(40.415363, -3.707398);
        if(getIntent().getExtras() != null)
        {
            latLng = new LatLng((Double)getIntent().getExtras().get("lat"),(Double)getIntent().getExtras().get("lng"));
            aceptar.setVisibility(View.GONE);
        }
        MarkerOptions mark = new MarkerOptions().position(latLng);
        mMap.addMarker(mark);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        latLngFinal = latLng;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.clear();
                    MarkerOptions mark = new MarkerOptions().position(latLng);
                    mMap.addMarker(mark);
                    latLngFinal = mark.getPosition();
                }
            });
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    FINE_LOCATION_PERMISOS);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permisos[], int[] resultadosDevueltos) {
        switch (requestCode) {
            case FINE_LOCATION_PERMISOS: {

                if (resultadosDevueltos.length > 0
                        && resultadosDevueltos[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }
                return;
            }
            case COURSE_LOCATION:

        }
    }
}
