package com.galacticCat.chatbleu.map;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.galacticCat.chatbleu.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity  implements OnMapReadyCallback , GoogleMap.OnInfoWindowClickListener
, GoogleMap.InfoWindowAdapter {
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




        MarkerOptions m1 = new MarkerOptions().title("Inicio Takessi").position(inicioTakessi)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("");
        mMap.addMarker(m1);


        MarkerOptions m2 = new MarkerOptions().title("Comunidad Takessi").position(comunidadTakessi)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("");
        mMap.addMarker(m2);
        MarkerOptions m3 = new MarkerOptions().title("Final Takessi").position(finalTakessi)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("");
        mMap.addMarker(m3);



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicioTakessi , 10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(finalTakessi));

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnInfoWindowClickListener(this);

        mMap.setInfoWindowAdapter(this);

    }
    //InfoWindow
    @Override
    public void onInfoWindowClick(Marker marker) {

            Fragment.newInstance(marker.getTitle(),marker.getSnippet())
                    .show(getSupportFragmentManager(),null);


    }


    @Override
    public View getInfoWindow(Marker marker) {

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return  prepareInfoView(marker);
    }

    private  View prepareInfoView(Marker marker){

        LinearLayout infoView = new LinearLayout(MapsActivity.this);
        infoView.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        infoView.setOrientation(LinearLayout.HORIZONTAL);
        infoView.setLayoutParams(infoViewParams);

        ImageView infoImageView =new ImageView(MapsActivity.this);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.comundad_takessi,null);
        infoImageView.setImageDrawable(drawable);
        infoView.addView(infoImageView);

        Button button = new Button(MapsActivity.this);
        button.setText("INFO");
        button.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
        infoView.addView(button);




       return infoView;
    }
}

