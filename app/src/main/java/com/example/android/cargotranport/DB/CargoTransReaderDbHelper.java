package com.example.android.cargotranport.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class CargoTransReaderDbHelper extends OrmLiteSqliteOpenHelper {

    public CargoTransReaderDbHelper(Context context){
        super(  context,
                DataBaseScript.DATABASE_NAME,
                null,
                DataBaseScript.DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource source) {
        db.execSQL(DataBaseScript.UserTable.SCRIPT);
        db.execSQL(DataBaseScript.TransportTable.SCRIPT);
        db.execSQL(DataBaseScript.EventTypeTable.SCRIPT);
        db.execSQL(DataBaseScript.ClientTable.SCRIPT);
        db.execSQL(DataBaseScript.StatusTable.SCRIPT);
        db.execSQL(DataBaseScript.MenuTable.SCRIPT);
        db.execSQL(DataBaseScript.UserByMenuTable.SCRIPT);
        db.execSQL(DataBaseScript.CargaTable.SCRIPT);
        db.execSQL(DataBaseScript.IncidentTable.SCRIPT);
        db.execSQL(DataBaseScript.GpsTrack.SCRIPT);//EMANUEL
        db.execSQL(DataBaseScript.UserTable.INSERT_);
        db.execSQL(DataBaseScript.IncidentTable.INSERT_);
        //db.execSQL(DataBaseScript.StatusTable.INSERT_);
        //db.execSQL(DataBaseScript.CargaTable.INSERT_);
        db.execSQL(DataBaseScript.GpsTrack.INSERT_);//EMANUEL

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            // DROP TABLA
            db.execSQL(DataBaseScript.DROP + DataBaseScript.UserTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.TransportTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.EventTypeTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.ClientTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.StatusTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.MenuTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.UserByMenuTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.CargaTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.IncidentTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.GpsTrack.TABLE_NAME);//EMANUEL

            onCreate(db);
        }
    }

    /*@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion){
            // DROP TABLA
            db.execSQL(DataBaseScript.DROP + DataBaseScript.ProjectTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.OperationTypeTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.WorkZoneTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.TransportTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.DocumentTypeTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.EquipmentTypeTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.PositionTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.ActivityTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.RequirementTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.PersonTable.TABLE_NAME);
            db.execSQL(DataBaseScript.DROP + DataBaseScript.EquipmentTable.TABLE_NAME);

            onCreate(db);
        }
    }*/

}

