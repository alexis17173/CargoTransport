package com.example.android.cargotranport;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.cargotranport.DB.CargoTransReaderDbHelper;
import com.example.android.cargotranport.DB.DAO.GpsTrackDAO;
import com.example.android.cargotranport.Entity.Carga;
import com.example.android.cargotranport.Entity.GpsTrack;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

public class GpsTransporte extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btnCargaRuta;
    EditText etPlaca;
    private GpsTrackDAO gpsTrackDAO;
    private CargoTransReaderDbHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_transporte);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        openHelper = OpenHelperManager.getHelper(MyApplication.getInstance().getBaseContext(), CargoTransReaderDbHelper.class);

        btnCargaRuta = (Button) findViewById(R.id.btnCargaRuta);
        etPlaca= (EditText) findViewById(R.id.etPlaca);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-12.054763,  -77.121059);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,9));
    }

    public void CargaRuta(View v)
    {
        mMap.clear();
        gpsTrackDAO = new GpsTrackDAO();
        List<GpsTrack> tracks;
        tracks = gpsTrackDAO.getGpsTrack(etPlaca.getText().toString().trim());
        LatLng posicion= new LatLng(-34, 151);
        String inc;
        //etPlaca.setText("");
        String event ="";
        PolylineOptions poli = new PolylineOptions().geodesic(true);//NUEVO
        for (int i=0;i<tracks.size();i++)
        {
            //etPlaca.setText(etPlaca.getText()+","+tracks.get(i).getFecha());
            posicion = new LatLng(tracks.get(i).getLatitud(), tracks.get(i).getLongitud());
            inc=tracks.get(i).getFecha();
            if (i==0)
            {
                mMap.addMarker(new MarkerOptions().position(posicion).title("Marker :"+i+" Inicio")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
            } else if (inc.equals("I"))
            {

                String[] args = new String[] {tracks.get(i).getRowId().toString()};
                Cursor c =  openHelper.getWritableDatabase().rawQuery("SELECT  EVENT_ID FROM MBL_INCIDENT WHERE INCIDENT_ID =?",args);
                if (c.moveToFirst())
                {
                    do
                    {
                        event =c.getString(0);

                    }while (c.moveToNext());
                }

                mMap.addMarker(new MarkerOptions().position(posicion).title(event)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.triangulo)));
            }
            else
                mMap.addMarker(new MarkerOptions().position(posicion).title("Marker :"+i));
            poli.add(posicion);//NUEVO
        }
        mMap.addPolyline(poli);//NUEVO

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion,13));
    }
}
