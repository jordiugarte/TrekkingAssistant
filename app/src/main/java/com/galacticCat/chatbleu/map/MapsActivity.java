package com.galacticCat.chatbleu.map;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.galacticCat.chatbleu.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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
        LatLng p1 = new LatLng(-16.504178, -67.908373);
        LatLng p2 = new LatLng(-16.503180, -67.906084);

        LatLng p3 = new LatLng(-16.500372, -67.905266);
        LatLng p4 = new LatLng(-16.494745, -67.901261);

        LatLng p5 = new LatLng(-16.493375, -67.898777);
        LatLng p6 = new LatLng(-16.493361, -67.898764);

        LatLng p7 = new LatLng(-16.491396, -67.897219);
        LatLng p8 = new LatLng(-16.491396, -67.897219);


        LatLng p9 = new LatLng(-16.490813, -67.897069);

        LatLng p10 = new LatLng(-16.487151, -67.890857);
        LatLng p11 = new LatLng(-16.487012, -67.891013);

        LatLng p12 = new LatLng(-16.486786, -67.890875);
        LatLng p13 = new LatLng(-16.486889, -67.890607);

        LatLng p14 = new LatLng(-16.486658, -67.890425);
       LatLng p15 = new LatLng(-16.486581, -67.889191);

        LatLng p16 = new LatLng(-16.486998, -67.889030);
        LatLng p17 = new LatLng(-16.486566, -67.888628);

        LatLng p18 = new LatLng(-16.486875, -67.888478);
        LatLng p19 = new LatLng(-16.487389, -67.888054);

        LatLng p20 = new LatLng(-16.486053, -67.886635);
        LatLng p21 = new LatLng(-16.487082, -67.883824);

        LatLng p22 = new LatLng(-16.485796, -67.880562);
        LatLng p23 = new LatLng(-16.486897, -67.875831);

        LatLng p24 = new LatLng(-16.487494, -67.875772);
        LatLng p25 = new LatLng(-16.487024, -67.875253);

        LatLng p26 = new LatLng(-16.487209, -67.875253);
        LatLng p27 = new LatLng(-16.486659, -67.875074);

        LatLng p28 = new LatLng(-16.486659, -67.875074);
        LatLng p29 = new LatLng(-16.485725, -67.874706);

        LatLng p30 = new LatLng(-16.481363, -67.872775);
        LatLng p31 = new LatLng(-16.478853, -67.868108);

        LatLng p32 = new LatLng(-16.478241, -67.865546);
        LatLng p33 = new LatLng(-16.478241, -67.865546);

        LatLng p34 = new LatLng(-16.477634, -67.864071);
        LatLng p35 = new LatLng(-16.476564, -67.862982);

        LatLng p36 = new LatLng(-16.476739, -67.862762);
        LatLng p37 = new LatLng(-16.476327, -67.862553);

        LatLng p38 = new LatLng(-16.475787, -67.861475);
        LatLng p39 = new LatLng(-16.475787, -67.860611);

        LatLng p40 = new LatLng(-16.475411, -67.860203);
        LatLng p41 = new LatLng(-16.475668, -67.859661);

        LatLng p42 = new LatLng(-16.474655, -67.858234);
        LatLng p43 = new LatLng(-16.474398, -67.857161);

        LatLng p44 = new LatLng(-16.474398, -67.857161);
        LatLng p45 = new LatLng(-16.472752, -67.852323);

        LatLng p46 = new LatLng(-16.472917, -67.852087);
        LatLng p47 = new LatLng(-16.472577, -67.851197);

        LatLng p48 = new LatLng(-16.472335, -67.851218);
        LatLng p49 = new LatLng(-16.472402, -67.850864);

        LatLng p50 = new LatLng(-16.470418, -67.848804);
        LatLng p51 = new LatLng(-16.469399, -67.848493);

        LatLng p52 = new LatLng(-16.465783, -67.841690);
        LatLng p53 = new LatLng(-16.463458, -67.839748);

        LatLng p54 = new LatLng(-16.461164, -67.838235);
        LatLng p55 = new LatLng(-16.461195, -67.838374);

        LatLng p56 = new LatLng(-16.459672, -67.837559);
        LatLng p57 = new LatLng(-16.456561, -67.834835);

        LatLng p58 = new LatLng(-16.454678, -67.832271);
        LatLng p59 = new LatLng(-16.454606, -67.832582);

        LatLng p60 = new LatLng(-16.453042, -67.831091);
        LatLng p61 = new LatLng(-16.453124, -67.831617);

        LatLng p62 = new LatLng(-16.452548, -67.831263);
        LatLng p63 = new LatLng(-16.452383, -67.831354);

        LatLng p64 = new LatLng(-16.451688, -67.830040);
        LatLng p65 = new LatLng(-16.450242, -67.828720);

        LatLng p66 = new LatLng(-16.448208, -67.826124);
        LatLng p67 = new LatLng(-16.447062, -67.825299);

        LatLng p68 = new LatLng(-16.446810, -67.824822);
        LatLng p69 = new LatLng(-16.445632, -67.825206);

        LatLng p70 = new LatLng(-16.443348, -67.825048);
        LatLng p71 = new LatLng(-16.443183, -67.824727);

        LatLng p72 = new LatLng(-16.442473, -67.824915);
        LatLng p73 = new LatLng(-16.441369, -67.824441);

        LatLng p74 = new LatLng(-16.441066, -67.822758);
        LatLng p75 = new LatLng(-16.440791, -67.822136);

        LatLng p76 = new LatLng(-16.440742, -67.821793);
        LatLng p77 = new LatLng(-16.440022, -67.820548);

        LatLng p78 = new LatLng(-16.440218, -67.820338);
        LatLng p79 = new LatLng(-16.439683, -67.817795);

        LatLng p80 = new LatLng(-16.439755, -67.817763);
        LatLng p81 = new LatLng(-16.439709, -67.817404);

        LatLng p82 = new LatLng(-16.439210, -67.816937);







        Drawable drawable;
        drawable=ResourcesCompat.getDrawable(getResources(),R.drawable.inicio_takessi,null);
        MarkerOptions m1 = new MarkerOptions().title("Inicio Takessi").position(inicioTakessi)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("-Inicio en la poblacion de Choquecota");
        mMap.addMarker(m1);


        MarkerOptions m2 = new MarkerOptions().title("Comunidad Takessi").position(comunidadTakessi)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("- Zona de camping \n- Punto Medico \n- Mulas de carga");
        mMap.addMarker(m2);

        MarkerOptions m3 = new MarkerOptions().title("Final Takessi").position(finalTakessi)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("- Final del camino poblacion de Yanacachi \n- Tiendas de comestibles \n- Transporte ");
        mMap.addMarker(m3);



        mMap.addPolyline(new PolylineOptions().add(inicioTakessi,p1).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p1,p2).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p2,p3).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p3,p4).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p4,p5).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p5,p6).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p6,p7).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p7,p8).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p8,p9).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p9,p10).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p10,p11).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p11,p12).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p12,p13).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p13,p14).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p14,p15).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p15,p16).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p16,p17).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p17,p18).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p18,p19).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p19,p20).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p20,p21).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p21,p22).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p22,p23).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p23,p24).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p24,p25).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p25,p26).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p26,p27).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p27,p28).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p28,p29).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p29,p30).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p30,p31).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p31,p32).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p32,p33).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p33,p34).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p34,p35).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p35,p36).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p36,p37).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p37,p38).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p38,p39).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p39,p40).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p40,p41).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p41,p42).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p42,p43).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p43,p44).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p44,p45).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p45,p46).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p46,p47).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p47,p48).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p48,p49).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p49,p50).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p50,p51).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p51,comunidadTakessi).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(comunidadTakessi,p52).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p52,p53).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p53,p54).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p54,p55).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p55,p56).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p56,p57).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p57,p58).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p58,p59).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p59,p60).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p60,p61).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p61,p62).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p62,p63).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p63,p64).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p64,p65).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p65,p66).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p66,p67).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p67,p68).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p68,p69).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p69,p70).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p70,p71).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p71,p72).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p72,p73).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p73,p74).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p74,p75).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p75,p76).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p76,p77).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p77,p78).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p78,p79).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p79,p80).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p80,p81).width(5).color(Color.RED));

        mMap.addPolyline(new PolylineOptions().add(p81,p82).width(5).color(Color.RED));
        mMap.addPolyline(new PolylineOptions().add(p82,finalTakessi).width(5).color(Color.RED));





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
        Drawable drawable= null;
        int image = 0;
        if(marker.getTitle().equals("Inicio Takessi")){
            image = R.drawable.inicio_takessi;
        }else if(marker.getTitle().equals("Comunidad Takessi")){
            image = R.drawable.comundad_takessi;
        }else
            image = R.drawable.final_takessi;

        drawable=ResourcesCompat.getDrawable(getResources(),image,null);

        LinearLayout infoView = new LinearLayout(MapsActivity.this);
        infoView.setBackgroundColor(Color.DKGRAY);
        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        infoView.setOrientation(LinearLayout.HORIZONTAL);
        infoView.setLayoutParams(infoViewParams);

        ImageView infoImageView =new ImageView(MapsActivity.this);
        infoImageView.setImageDrawable(drawable);
        infoView.addView(infoImageView);

        Button button = new Button(MapsActivity.this);
        button.setText("INFO");
        infoView.addView(button);




       return infoView;
    }
}

