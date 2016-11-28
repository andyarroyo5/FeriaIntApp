package edu.udem.feriaint.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import edu.udem.feriaint.Modelos.Tema;

/**
 * Created by Andrea Arroyo on 28/11/2016.
 */

public class TemaEventosBD {

    public static final String TAG =this getClass().getSimpleName();

    SQLiteOpenHelper bdhandler;
    SQLiteDatabase bd;
    Tema tema;

    public TemaEventosBD(Context context){

        tema=new Tema();
        bdhandler = new BDHandler(context);
    }

    public void open(){
        Log.i(TAG,"BD abierta");
        bd = bdhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(TAG, "BD cerrada");
        bdhandler.close();

    }


    public void insertar(Tema temaNuevo) {

        open();
        ContentValues values = new ContentValues();
        try {

            values.put(BDHandler.ID_TEMA_CONTCULTURAL, temaNuevo.getId());
            values.put(BDHandler.NOMBRE_TEMA_CONTCULTURAL, temaNuevo.getNombre());

            bd.insert(BDHandler.TABLA_TEMA_CONTCULTURAL, null, values);
            Log.e(TAG, " Agregar en BD "+ temaNuevo);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

        close();
    }



    public ArrayList<Tema> getTemas ()
    {
        open();
        ArrayList<Tema> listaTemas=new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_TEMA_CONTCULTURAL;

        bd=bdhandler.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tema tema=new Tema();
                tema.setId(Long.parseLong(cursor.getString(0)));
                tema.setNombre(cursor.getString(1));

                listaTemas.add(tema);

            }while (cursor.moveToNext());
        }
        else
        {
            Log.e(TAG, " Get evento no hay");
        }
        close();

        return listaTemas;
    }
}
