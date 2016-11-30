package edu.udem.feriaint.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.udem.feriaint.Modelos.Evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrea Arroyo on 17/10/2016.
 */

public class EventoDB {

    public static final String TAG = "EVENTODB";

    SQLiteOpenHelper bdhandler;
    SQLiteDatabase bd;


    private static final String[] COLUMNAS = {
            BDHandler.ID_EVENTO,
            BDHandler.TITULO,
            BDHandler.FECHA_INICIO,
            BDHandler.FECHA_FINAL,
            BDHandler.LUGAR,
            BDHandler.DESCRIPCION,
            BDHandler.TIPO,
            BDHandler.FAVORITO

    };


    public EventoDB (Context context){
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


    public void insertarEvento(Evento evento) {


        open();
        ContentValues values = new ContentValues();
        try {
        values.put(BDHandler.ID_EVENTO, evento.getId());
        values.put(BDHandler.TITULO, evento.getTitulo());
        values.put(BDHandler.FECHA_INICIO, String.valueOf(evento.getFechaInicio()));
        values.put(BDHandler.FECHA_FINAL, String.valueOf(evento.getFechaFinal()));
        values.put(BDHandler.LUGAR, evento.getLugar());
        values.put(BDHandler.DESCRIPCION, evento.getDescripcion());
        values.put(BDHandler.TIPO, evento.getDescripcion());
        values.put(BDHandler.FAVORITO, evento.isFavorito());

        bd.insert(BDHandler.TABLA_EVENTO, null, values);
         //   Log.e(TAG, " Agregar en BD "+ evento);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

        close();
    }



    public void eliminarFavoritos(Evento evento)
    {
        open();
        try {


            String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_EVENTO +"WHERE"+BDHandler.ID_EVENTO+"="+evento.getId();

            //bd.delete()
            Log.e(TAG, " Eliminar evento "+ evento);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

        close();
    }

   public Evento getEvento(long id) {

       String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_EVENTO+" WHERE"+BDHandler.ID_EVENTO+"="+id;

       Evento evento = new Evento();
       Cursor cursor = bd.rawQuery(selectQuery, null);
            cursor.moveToFirst();

       evento.setId(Integer.parseInt(cursor.getString(0)));
       evento.setTitulo(cursor.getString(1));
       evento.setFechaInicio(new Date(cursor.getString(2)));
       evento.setFechaFinal(new Date(cursor.getString(3)));
       evento.setLugar(cursor.getString(4));
       evento.setDescripcion(cursor.getString(5));
       evento.setTipo(cursor.getString(6));
      // Log.d(TAG, "Favorito Get"+cursor.getString(7)+ Boolean.parseBoolean(cursor.getString(7)));
       evento.setFavorito(Boolean.parseBoolean(cursor.getString(7)));


        // return contact
        return evento;
    }

    // Getting All Contacts
    public ArrayList<Evento> getTodosLosEventos() throws ParseException {

        open();
        SimpleDateFormat fechaf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        ArrayList<Evento> listaEventos = new ArrayList<Evento>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_EVENTO;

        bd=bdhandler.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Evento evento = new Evento();
                evento.setId(Integer.parseInt(cursor.getString(0)));
                evento.setTitulo(cursor.getString(1));
                evento.setFechaInicio(new Date(cursor.getString(2)));
                evento.setFechaFinal(new Date(cursor.getString(3)));
                evento.setLugar(cursor.getString(4));
                evento.setDescripcion(cursor.getString(5));
                evento.setTipo(cursor.getString(6));
                evento.setFavorito(Boolean.parseBoolean(cursor.getString(7)));

              // Log.e(TAG, " Get evento "+cursor.getString(7)+" "+ evento);
                listaEventos.add(evento);

            } while (cursor.moveToNext());
        }

        else
        {
            //Log.e(TAG, " Get evento no hay");
        }

        // return eventos

        close();
        return listaEventos;
    }



    public void setEventoFavorito(Evento evento, boolean fav)
    {
        open();
       // boolean fav;

        ContentValues cv = new ContentValues();
/*
        Log.e(TAG, String.valueOf(evento.isFavorito()));
        if (evento.isFavorito())
        {
            cv.put(BDHandler.FAVORITO, false);
            cv.put(BDHandler.FAVORITO, 0);
            fav=false;
        }
        else
        {
            cv.put(BDHandler.FAVORITO, true);
            cv.put(BDHandler.FAVORITO, 1);
             fav=true;
        }*/



     //   Log.e(TAG, " SET EVENTO FAV "+ evento+" "+fav);


        //bd.update(BDHandler.TABLA_EVENTO, cv, BDHandler.ID_EVENTO+"= "+evento.getId(), null);

        String query="UPDATE "+BDHandler.TABLA_EVENTO+" SET "+ BDHandler.FAVORITO+" = '"+fav+  "' where "+  BDHandler.ID_EVENTO+"= "+evento.getId();


        bd.execSQL(query);

        Cursor cursor =bd.rawQuery("SELECT * From "+BDHandler.TABLA_EVENTO+" where "+  BDHandler.ID_EVENTO+"= "+evento.getId(),null);
        if (cursor.moveToFirst()) {

            Log.e(TAG,cursor.getString(7));
        }

        close();

    }


}
