package com.example.android.cargotranport;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ListaMandar extends AppCompatActivity {


    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mandar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.end_sync_start_floatingbutton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

}
