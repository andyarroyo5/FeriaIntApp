package edu.udem.feriaint.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.udem.feriaint.Data.BDHandler;
import edu.udem.feriaint.Modelos.Evento;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
            BDHandler.DESCRIPCION
    };


    public EventoDB (Context context){
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


    public void insert(Evento evento) {


        ContentValues values = new ContentValues();
        try {

        values.put(BDHandler.TITULO, evento.getTitulo());
        values.put(BDHandler.FECHA_INICIO, String.valueOf(evento.getFechaInicio()));
            Log.e(TAG, " FECHA INICIO"+ evento.getFechaInicio());
        values.put(BDHandler.FECHA_FINAL, String.valueOf(evento.getFechaFinal()));
        values.put(BDHandler.LUGAR, evento.getLugar());
        values.put(BDHandler.DESCRIPCION, evento.getDescripcion());
        values.put(BDHandler.TIPO, evento.getDescripcion());

        bd.insert(BDHandler.TABLA_EVENTO, null, values);
            Log.e(TAG, " Agregar en BD "+ evento);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }
    }


    public void eliminar(Evento evento)
    {
        try {

            String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_EVENTO +"WHERE"+BDHandler.ID_EVENTO+"="+evento.getId();

            //bd.delete()
            Log.e(TAG, " Agregar en BD "+ evento);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

    }

   /* public void getEvento(int id) {


        Cursor cursor = bd.query( BDHandler.TABLA_EVENTOS, new String[] { BDHandler.ID_EVENTO,
                        BDHandler.ID_EVENTO,  BDHandler.ID_EVENTO,  BDHandler.ID_EVENTO,  BDHandler.ID_EVENTO },  BDHandler.ID_EVENTO + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null , null);
        if (cursor != null)
            cursor.moveToFirst();

        Evento evento = new Evento(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5));
        // return contact
        return evento;
    }*/

    // Getting All Contacts
    public ArrayList<Evento> getTodosLosEventos() throws ParseException {

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

               Log.e(TAG, " Get evento "+ evento);
                listaEventos.add(evento);

            } while (cursor.moveToNext());
        }

        // return eventos
         System.out.println("Terminar getTODOS..."+String.valueOf(cursor.moveToFirst()));
        return listaEventos;
    }




}
