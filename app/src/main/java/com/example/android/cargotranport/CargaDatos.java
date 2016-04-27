package com.example.android.cargotranport;

import android.content.DialogInterface;
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

import com.example.android.cargotranport.Adapter.TableAdapter;
import com.example.android.cargotranport.DB.DAO.CargaDAO;
import com.example.android.cargotranport.DB.DAO.ClientDAO;
import com.example.android.cargotranport.DB.DAO.StatusDAO;
import com.example.android.cargotranport.Entity.TableItem;

import java.util.ArrayList;

public class CargaDatos extends AppCompatActivity {

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
        setContentView(R.layout.activity_carga_datos);
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

        AlertDialog confirmAlertDialog = new AlertDialog.Builder(CargaDatos.this).create();
        confirmAlertDialog.setTitle("Advertencia");
        confirmAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        confirmAlertDialog.setMessage("Esta operación Sincroniza Datos. Está seguro de continuar?.");
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
        mActionBarTitleTextView.setText("Sincronizar de BD");

        mActionBarSubtitleTextView = (TextView) mCustomView.findViewById(R.id.txt_subtitle);
        mActionBarSubtitleTextView.setText("CargoTrasport");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void initializeData() {
        mItemToSyncList = new ArrayList<>();
        mItemToSyncList.add(new TableItem("Productos"));
        mItemToSyncList.add(new TableItem("Transportes"));
        mItemToSyncList.add(new TableItem("Clientes"));
        mItemToSyncList.add(new TableItem("Estados"));
        mItemToSyncList.add(new TableItem("Usuarios"));
    }


    public class SyncDataSenderAsyncTask extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            mToSyncAdapter.notifyDataSetChanged();
            mFloatingActionButton.setVisibility(View.VISIBLE);

            Toast t = Toast.makeText(getBaseContext(), "Sincronización completada.", Toast.LENGTH_SHORT);
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
                CargaDAO.getInstance().insertBD();
                publishProgress(new String[]{"Productos", String.valueOf(1)});

                publishProgress(new String[]{"Transportes", String.valueOf(1)});
                ClientDAO.getInstance().insertBD();
                publishProgress(new String[]{"Clientes", String.valueOf(1)});
                StatusDAO.getInstance().insertBD();
                publishProgress(new String[]{"Estados", String.valueOf(1)});

                publishProgress(new String[]{"Usuarios", String.valueOf(1)});
                return null;
            } catch (Throwable t) {
                t.printStackTrace();

            }
            return null;
        }
    }
}
