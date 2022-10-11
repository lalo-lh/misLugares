package com.lalo.MisLugares_U7;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    protected Lugares lugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment;
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(LugaresVector.vectorLugares.size() > 0){
            GeoPunto p = LugaresVector.vectorLugares.get(0).getPosicion();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(p.getLatitud(), p.getLongitud()),12));
        }
        for (Lugar lugar: LugaresVector.vectorLugares){
            GeoPunto p = lugar.getPosicion();
            if(p != null && p.getLatitud() != 0){

                BitmapDrawable iconoDrawable = (BitmapDrawable) getResources().getDrawable((lugar.getTipo().getRecurso()));
                Bitmap iGrande = iconoDrawable.getBitmap();
                Bitmap icono = Bitmap.createScaledBitmap(iGrande, iGrande.getWidth() / 7, iGrande.getHeight() / 7, false);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(p.getLatitud(), p.getLongitud()))
                        .title(lugar.getNombre()).snippet(lugar.getDireccion())
                        .icon(BitmapDescriptorFactory.fromBitmap(icono)));
            }
        }

    }
/*
    @Override
    public void onInfoWindowClick(Marker marker) {

        for(int id = 0; id < LugaresVector.vectorLugares.size(); id++){
            if(LugaresVector.vectorLugares.get(id).getNombre().equals(marker.getTitle())){
                Intent intent = new Intent(this, VistaLugar.class);
                intent.putExtra("id", Long.valueOf(id));
                startActivity(intent);
                break;
            }
        }

    }
    */
}
