package com.example.android.cargotranport;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;


import com.example.android.cargotranport.Adapter.TableAdapter;
import com.example.android.cargotranport.Entity.TableItem;


public class ListaMandar extends AppCompatActivity {

    private ArrayList<TableItem> mItemToSyncList;
    private RecyclerView mPeopleRecyclerView;
    private RecyclerView.Adapter mToSyncAdapter;

   //private SyncModel mSyncModel;
    //SyncDataSenderAsyncTask mAsyncPostToServer;

    private TextView mActionBarTitleTextView;
    private TextView mActionBarSubtitleTextView;

    //private String mExceptionString;
    //private List<String> mJsonResultString;
    //private List<Images_Requerimiento> lista;

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mandar);
        createActionBar();
        initializeData();
        //mAsyncPostToServer = new SyncDataSenderAsyncTask();
        mToSyncAdapter = new TableAdapter(mItemToSyncList);

        mPeopleRecyclerView = (RecyclerView) findViewById(R.id.rcvListaMandar);
        mPeopleRecyclerView.setHasFixedSize(true);
        mPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPeopleRecyclerView.setAdapter(mToSyncAdapter);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fabListaMandar);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initializeData();
                checkStateSynchronization();

            }
        });
    }

    private void checkStateSynchronization() {

        AlertDialog confirmAlertDialog = new AlertDialog.Builder(ListaMandar.this).create();
        confirmAlertDialog.setTitle("Advertencia");
        confirmAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        confirmAlertDialog.setMessage("Esta operación solo transferirá Cargas Entregadas. Está seguro de continuar?.");
        confirmAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", mOnDialogConfirmSyncButtonClickListener);
        confirmAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", mOnDialogConfirmSyncButtonClickListener);
        confirmAlertDialog.show();

    }

    private DialogInterface.OnClickListener mOnDialogConfirmSyncButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int button) {
            if (AlertDialog.BUTTON_POSITIVE == button) {
                Intent intent = new Intent(getApplicationContext(), Incidencias.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(getApplicationContext(), CargaDatos.class);
                startActivity(intent);

            }
        }
    };


    private void showAlertDialog(String title, String message, Boolean status, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon((status) ? R.drawable.success : R.drawable.fail);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.create();
        builder.show();
    }

    private void createActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_main, null);
        mActionBarTitleTextView = (TextView) mCustomView.findViewById(R.id.txt_title);
        mActionBarTitleTextView.setText("Sincronizar a BD");

        mActionBarSubtitleTextView = (TextView) mCustomView.findViewById(R.id.txt_subtitle);
        mActionBarSubtitleTextView.setText("CargoTrasport");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void initializeData() {
        mItemToSyncList = new ArrayList<>();
        mItemToSyncList.add(new TableItem("Productos Detalle"));
        mItemToSyncList.add(new TableItem("Incidencias"));
        mItemToSyncList.add(new TableItem("Imagenes"));

    }
}
