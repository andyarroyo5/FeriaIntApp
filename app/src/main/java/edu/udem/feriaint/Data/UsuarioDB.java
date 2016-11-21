package edu.udem.feriaint.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Modelos.Usuario;

/**
 * Created by Andrea Arroyo on 06/11/2016.
 */

public class UsuarioDB {

    public static final String TAG = "USUARIODB";

    SQLiteOpenHelper bdhandler;
    SQLiteDatabase bd;



    public UsuarioDB (Context context){
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

    public void insertar(Usuario usuarioNuevo) {

        open();
        ContentValues values = new ContentValues();
        try {

            values.put(BDHandler.NOMBRE, usuarioNuevo.getNombre()!=null?  usuarioNuevo.getNombre(): "");
            values.put(BDHandler.CORREO, usuarioNuevo.getCorreo()!=null?  usuarioNuevo.getCorreo(): "");
            values.put(BDHandler.CARRERA, usuarioNuevo.getCarrera()!=null?  usuarioNuevo.getCarrera(): "");
            values.put(BDHandler.TWITTER, usuarioNuevo.getTwitter()!=null?  usuarioNuevo.getTwitter(): "");
            values.put(BDHandler.PUNTOS, usuarioNuevo.getPuntos());

            long id= bd.insert(BDHandler.TABLA_USUARIO,null, values);
            usuarioNuevo.setId(id);
            Log.e(TAG, " Agregar en BD "+ usuarioNuevo.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

        close();
    }

    public void eliminar(Usuario usuario)
    {
        try {

            String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_USUARIO +" WHERE"+BDHandler.ID_USUARIO+"="+usuario.getId();

            //bd.delete()
            Log.e(TAG, " Agregar en BD "+ usuario);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

    }

    public Usuario getTodosLosUsuarios() throws ParseException {

        open();
       ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_USUARIO;

        bd=bdhandler.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(0)));
                usuario.setNombre(cursor.getString(1));
                usuario.setCorreo(cursor.getString(2));
                usuario.setCarrera(cursor.getString(3));
                usuario.setTwitter(cursor.getString(4));
                usuario.setPuntos(cursor.getInt(5));

                Log.e(TAG, " Get usuario " + usuario);
                listaUsuarios.add(usuario);

            } while (cursor.moveToNext());
            close();
        }
                return listaUsuarios.get(0);
        }



    public void actualizar (Usuario usuario)
    {
        open();


        ContentValues cv = new ContentValues();
        cv.put(BDHandler.NOMBRE, usuario.getNombre());
        cv.put(BDHandler.CORREO, usuario.getCorreo());
        cv.put(BDHandler.CARRERA, usuario.getCarrera());
        cv.put(BDHandler.TWITTER, usuario.getTwitter());

        Log.e(TAG,usuario.toString());

        bd.update(BDHandler.TABLA_USUARIO, cv, BDHandler.ID_USUARIO+"= "+usuario.getId(), null);
        close();

    }



}
