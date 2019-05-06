package com.galacticCat.chatbleu.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.galacticCat.chatbleu.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng inicioTakessi = new LatLng(-16.509014, -67.911909);
        LatLng comunidadTakessi = new LatLng(-16.468670, -67.846208);

        LatLng finalTakessi = new LatLng(-16.398600, -67.738048);



        MarkerOptions m1 = new MarkerOptions().title("Inicio Takessi").position(inicioTakessi).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.addMarker(m1);
        MarkerOptions m2 = new MarkerOptions().title("Comunidad Takessi").position(comunidadTakessi).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.addMarker(m2);
        MarkerOptions m3 = new MarkerOptions().title("Fnal Takessi").position(finalTakessi).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.addMarker(m3);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicioTakessi , 10));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicioTakessi));

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        MarkerOptions marcadorDestino= new MarkerOptions();
        marcadorDestino.position(finalTakessi);
        marcadorDestino.title("Este es tu destino");



    }
}

