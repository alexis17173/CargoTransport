package com.example.android.cargotranport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EntregaCarga extends AppCompatActivity {
    private final static String[] Clientes = {"" +
            "Hiraoka", "Curacao", "Carsa",
            "Tottus", "Metro", "Paris"};
    public Spinner spCliente;
    private TextView mActionBarTitleTextView;
    private TextView mActionBarSubtitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_carga);
        createActionBar();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Clientes);
        spCliente = (Spinner) findViewById(R.id.cboCliente);
        spCliente.setAdapter(adapter);

    }
    public  void venta1(View view)
    {
        Intent intent = new Intent(this, ListaMandar.class);
        startActivity(intent);
    }

    public  void venta2(View view)
    {
        Intent intent = new Intent(this, TrazabilidadTransporte.class);
        startActivity(intent);
    }

    private void createActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_main, null);
        mActionBarTitleTextView = (TextView) mCustomView.findViewById(R.id.txt_title);
        mActionBarTitleTextView.setText("Entrega de Carga");

        //mActionBarSubtitleTextView = (TextView) mCustomView.findViewById(R.id.txt_subtitle);
        //mActionBarSubtitleTextView.setText("Entrega de Carga");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}
