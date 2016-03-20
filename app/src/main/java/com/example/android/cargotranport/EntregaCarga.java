package com.example.android.cargotranport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EntregaCarga extends AppCompatActivity {
    private final static String[] Clientes = {"" +
            "Hiraoka", "Curacao", "Carsa",
            "Tottus", "Metro", "Paris"};
    public Spinner spCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_carga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Clientes);
        spCliente = (Spinner) findViewById(R.id.cboCliente);
        spCliente.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}
