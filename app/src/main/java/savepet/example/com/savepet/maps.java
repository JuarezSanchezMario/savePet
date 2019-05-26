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
                setResult(LOCALIZACION, localizacion);
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng madrid = new LatLng(40.415363, -3.707398);
        MarkerOptions mark = new MarkerOptions().position(madrid).title("Marke en Madrid");
        mMap.addMarker(mark);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(madrid));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.clear();
                    MarkerOptions mark = new MarkerOptions().position(latLng).title("Mark en Madrid");
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
                } else {

                }
                return;
            }
            case COURSE_LOCATION:

        }
    }
}
