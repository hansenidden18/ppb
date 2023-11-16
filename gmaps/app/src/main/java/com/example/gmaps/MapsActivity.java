package com.example.gmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    FrameLayout map;
    Button go,find;
    EditText edtLat,edtLong,edtZoom,edtLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        map = findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        go = findViewById(R.id.mapBtn);
        find = findViewById(R.id.findBtn);
        edtLoc = findViewById(R.id.gotoloc);
        edtLat = findViewById(R.id.editLat);
        edtLong = findViewById(R.id.editLong);
        edtZoom = findViewById(R.id.editZoom);
        mapFragment.getMapAsync(this);
        go.setOnClickListener(op);
        find.setOnClickListener(op);
    }
    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.mapBtn) {
                gotoLoct();
            } else if (id == R.id.findBtn) {
                goFind();
            }
        }
    };
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        this.gMap= googleMap;
        LatLng mapITS = new LatLng (-7.28,112.79);
        this.gMap.addMarker(new MarkerOptions().position(mapITS).title("ITS"));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapITS));
    }

    private void gotoLoct(){
        Double dbllat = Double.parseDouble(edtLat.getText().toString());
        Double dbllng = Double.parseDouble(edtLong.getText().toString());
        Float dblzoom = Float.parseFloat(edtZoom.getText().toString());
        Toast.makeText(this,"Move to Lat:" +dbllat + " Long:" +dbllng, Toast.LENGTH_LONG).show();
        gotoMap(dbllat,dbllng,dblzoom);
    }
    private void gotoMap(Double lat, Double lng, float z){
        LatLng NewLoct = new LatLng(lat,lng);
        gMap.addMarker(new MarkerOptions().position(NewLoct).title("Marked  " +lat +":" +lng));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NewLoct,z));
    }

    private void goFind(){
        Geocoder g = new Geocoder(getBaseContext());
        try{
            List<Address> daftar = g.getFromLocationName(edtLoc.getText().toString(),1);
            Address alamat = daftar.get(0);

            String nemuAlamat = alamat.getAddressLine(0);
            Double lati = alamat.getLatitude();
            Double longi = alamat.getLongitude();
            Toast.makeText(getBaseContext(), "Found " + nemuAlamat, Toast.LENGTH_LONG).show();
            Float dblzoom = Float.parseFloat(edtZoom.getText().toString());
            Toast.makeText(this,"Move to "+ nemuAlamat +" Lat:" +
                    lati + " Long:" +longi,Toast.LENGTH_LONG).show();
            gotoMap(lati,longi,dblzoom);
            Double dbllat = Double.parseDouble(edtLat.getText().toString());
            Double dbllong = Double.parseDouble(edtLong.getText().toString());
            Distance(dbllat,dbllong,lati,longi);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Distance(double latAsal, double lngAsal, double latTujuan, double lngTujuan){
        Location from = new Location("From");
        Location dest = new Location("Destination");
        dest.setLatitude(latTujuan);
        dest.setLongitude(lngTujuan);
        from.setLatitude(latAsal);
        from.setLongitude(lngAsal);
        float dist = (float) from.distanceTo(dest)/1000;
        String distance = String.valueOf(dist);
        Toast.makeText(getBaseContext(),"Distance : " + distance + " km ",Toast.LENGTH_LONG).show();
    }
}