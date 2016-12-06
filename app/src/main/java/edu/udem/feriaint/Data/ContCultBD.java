package edu.udem.feriaint.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Modelos.ContenidoCultural;

/**
 * Created by Andrea Arroyo on 06/12/2016.
 */

public class ContCultBD {


    public static final String TAG = "CONTCULTBDD";

    SQLiteOpenHelper bdhandler;
    SQLiteDatabase bd;


    private static final String[] COLUMNAS = {
            BDHandler.ID_CONTCULT,
            BDHandler.TITULO_CONTCULT,
            BDHandler.FAV_CONTCULT

    };


    public ContCultBD (Context context){
        bdhandler = new BDHandler(context);
    }

    public void open(){
        // Log.i(TAG,"BD abierta");
        bd = bdhandler.getWritableDatabase();


    }
    public void close(){
        // Log.i(TAG, "BD cerrada");
        bdhandler.close();

    }

    public void insertarContCult(ContenidoCultural contCult) {


        open();
        ContentValues values = new ContentValues();
        try {
            values.put(BDHandler.ID_CONTCULT, contCult.getId());
            values.put(BDHandler.TITULO_CONTCULT, contCult.getTitulo());
            values.put(BDHandler.FAV_CONTCULT, String.valueOf(contCult.isFavorito()));

            bd.insert(BDHandler.TABLA_CONTCULT, null, values);
            //   Log.e(TAG, " Agregar en BD "+ evento);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

        close();
    }

    public ArrayList<ContenidoCultural> getTodosLosEventos() throws ParseException {

        open();
        ArrayList<ContenidoCultural> listaContCult = new ArrayList<ContenidoCultural>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_CONTCULT;

        bd=bdhandler.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContenidoCultural contenidoCultural = new ContenidoCultural();
                contenidoCultural.setId(Integer.parseInt(cursor.getString(0)));
                contenidoCultural.setTitulo(cursor.getString(1));
                contenidoCultural.setFavorito(Boolean.parseBoolean(cursor.getString(2)));

                listaContCult.add(contenidoCultural);

            } while (cursor.moveToNext());
        }

        else
        {
            //Log.e(TAG, " Get evento no hay");
        }

        // return eventos

        close();
        return listaContCult;
    }

    public void eliminarFavoritos(ContenidoCultural contenidoCultural)
    {
        open();
        try {


            String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_CONTCULT +"WHERE"+BDHandler.ID_CONTCULT+"="+contenidoCultural.getId();

            //bd.delete()
            Log.e(TAG, " Eliminar evento "+ contenidoCultural);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

        close();
    }


    public void setContenidoCulturalFav(ContenidoCultural contenidoCultural, boolean fav)
    {
        open();

        ContentValues cv = new ContentValues();

        String query="UPDATE "+BDHandler.TABLA_CONTCULT+" SET "+ BDHandler.FAV_CONTCULT+" = '"+fav+  "' where "+  BDHandler.ID_CONTCULT+"= "+contenidoCultural.getId();

        bd.execSQL(query);

        Cursor cursor =bd.rawQuery("SELECT * From "+BDHandler.TABLA_CONTCULT+" where "+  BDHandler.ID_CONTCULT+"= "+contenidoCultural.getId(),null);
        if (cursor.moveToFirst()) {

            Log.e(TAG,cursor.getString(7));
        }

        close();

    }


}
