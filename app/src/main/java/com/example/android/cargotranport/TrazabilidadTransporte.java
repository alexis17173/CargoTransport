package com.example.android.cargotranport;

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

public class TrazabilidadTransporte extends AppCompatActivity {
    private final static String[] Transporte = {"" +
            "P4C-345", "A0C-752", "A0K-808",
            "P6H-425", "P4D-123", "L0L-452"};
    public Spinner spCliente;
    private TextView mActionBarTitleTextView;
    private TextView mActionBarSubtitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActionBar();
        setContentView(R.layout.activity_trazabilidad_transporte);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Transporte);
        spCliente = (Spinner) findViewById(R.id.cboTranporte);
        spCliente.setAdapter(adapter);

    }
    private void createActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_main, null);
        mActionBarTitleTextView = (TextView) mCustomView.findViewById(R.id.txt_title);
        mActionBarTitleTextView.setText("Trazabilidad Transporte");

        //mActionBarSubtitleTextView = (TextView) mCustomView.findViewById(R.id.txt_subtitle);
        //mActionBarSubtitleTextView.setText("Entrega de Carga");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}
