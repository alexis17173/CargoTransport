package com.example.android.cargotranport;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.cargotranport.DB.CargoTransReaderDbHelper;
import com.example.android.cargotranport.DB.DAO.GpsTrackDAO;
import com.example.android.cargotranport.Entity.GpsTrack;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Incidencias extends AppCompatActivity {

    Intent i;
    final static int cons = 1;
    Bitmap bmp;
    ImageView img;
    private CargoTransReaderDbHelper openHelper;
    private GpsTrackDAO gpsTrackDAO;
    Spinner spinner;
    EditText etComent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardaIncidencia(view);

            }
        });

        openHelper = OpenHelperManager.getHelper(MyApplication.getInstance().getBaseContext(), CargoTransReaderDbHelper.class);
        etComent= (EditText) findViewById(R.id.etComent);
        img = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.array_Incidencias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

    }

    public void TomaFoto(View v)
    {
        i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,1);

    }
    public void GuardaIncidencia(View v)
    {
        int rowid=1;
        double latitud=0,longitud=0;
        String[] args = new String[] {"PPP-123"};
        Cursor c =  openHelper.getWritableDatabase().rawQuery("SELECT rowid,latitud,longitud FROM mbl_GpsTrack WHERE ROWID IN\n" +
                                                    "(SELECT max(rowid)\n" +
                                                    "FROM mbl_GpsTrack\n" +
                                                    "WHERE placa =?)",args);
        if (c.moveToFirst())
        {
            do
            {
                rowid =c.getInt(0);
                latitud = c.getDouble(1);
                longitud = c.getDouble(2);
            }while (c.moveToNext());
        }
        if (latitud!=0)
        {

            GpsTrack track = new GpsTrack();
            track.setPlaca(args[0].toString());
            track.setLatitud(latitud);
            track.setLongitud(longitud);
            track.setFecha("I");

            gpsTrackDAO = new GpsTrackDAO();
            gpsTrackDAO.inserta_i(track);//marca en mapa


            c =  openHelper.getWritableDatabase().rawQuery("SELECT rowid FROM mbl_GpsTrack WHERE ROWID IN\n" +
                    "\n" +
                    "                    (SELECT max(rowid)\n" +
                    "                    FROM mbl_GpsTrack\n" +
                    "                    WHERE placa =? AND FECHA = \"1\")",args);

            if (c.moveToFirst())
            {
                do
                {
                    rowid =c.getInt(0);
                }while (c.moveToNext());
            }

            openHelper.getWritableDatabase().execSQL("INSERT into mbl_incident (incident_id,event_id,transport_id,incident_obs,incident_image,incident_position)\n" +
                    "VALUES ('"+rowid+"','"+spinner.getSelectedItem().toString()+"','"+args[0].toString()+"','"+etComent.getText()+"','"+"Ruta/imagen"+"','"+latitud+","+longitud+"')");

            Snackbar.make(v, "Incidencia grabada satisfactoriamente!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else
        {
            Snackbar.make(v,"Unidad :"+args[0]+",sin datos GPS. No se grabo la incidencia", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            Log.d("foto","ok");
            Bundle ext = data.getExtras();
            bmp=(Bitmap)ext.get("data");
            img.setImageBitmap(bmp);
        }
    }
}
