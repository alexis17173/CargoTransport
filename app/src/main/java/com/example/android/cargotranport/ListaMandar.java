package com.example.android.cargotranport;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


import com.example.android.cargotranport.Adapter.TableAdapter;
import com.example.android.cargotranport.Entity.TableItem;


public class ListaMandar extends AppCompatActivity {
    private ArrayList<TableItem> mItemToSyncList;
    private RecyclerView mPeopleRecyclerView;
    private RecyclerView.Adapter mToSyncAdapter;
    SyncDataSenderAsyncTask mAsyncPostToServer;
    //private SyncModel mSyncModel;
    //SyncDataSenderAsyncTask mAsyncPostToServer;
    private String mExceptionString;
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
        mAsyncPostToServer = new SyncDataSenderAsyncTask();
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
                //initializeData();
                mExceptionString = null;
                mFloatingActionButton.setVisibility(View.GONE);
                //initializeData();
                mAsyncPostToServer = new SyncDataSenderAsyncTask();
                mAsyncPostToServer.execute();
                //initializeData();
            } else {
                //mOperationModel.getSelectedActivity().setEndTime(null);
                mFloatingActionButton.setVisibility(View.VISIBLE);
            }
        }
    };

    /*
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
    */
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


    public class SyncDataSenderAsyncTask extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            mToSyncAdapter.notifyDataSetChanged();
            mFloatingActionButton.setVisibility(View.VISIBLE);

            Toast t = Toast.makeText(getBaseContext(), "Sincronización hacia BD completada.", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (TableItem t : mItemToSyncList) {
                if (t.getName().equals(values[0])) {
                    t.setState(Integer.parseInt(values[1]));
                    mToSyncAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                publishProgress(new String[]{"Productos Detalle", String.valueOf(1)});
                publishProgress(new String[]{"Incidencias", String.valueOf(1)});
                publishProgress(new String[]{"Imagenes", String.valueOf(1)});
                return null;
            } catch (Throwable t) {
                t.printStackTrace();

            }
            return null;
        }
    }
}
