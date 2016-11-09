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
    Usuario usuario;


    public UsuarioDB (Context context){

        usuario=new Usuario();
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

    public void insert(Usuario usuarioNuevo) {


        ContentValues values = new ContentValues();
        try {

            values.put(BDHandler.TWITTER, usuarioNuevo.getTwitter());
            values.put(BDHandler.NOMBRE, usuarioNuevo.getNombre());
            /*
            values.put(BDHandler.CORREO, usuario.getCorreo());
            values.put(BDHandler.NOMBRE, usuario.getNombre());
            values.put(BDHandler.CARRERA, usuario.getCarrera());
            values.put(BDHandler.PUNTOS, usuario.getPuntos());

            */
            long id = bd.insert(BDHandler.TABLA_USUARIO,null , values);
            usuarioNuevo.setId(id);
            this.usuario=usuarioNuevo;
            Log.e(TAG, " Agregar en BD "+ usuarioNuevo);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }
    }

    public void eliminar(Usuario usuario)
    {
        try {

            String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_USUARIO +"WHERE"+BDHandler.ID_USUARIO+"="+usuario.getId();

            //bd.delete()
            Log.e(TAG, " Agregar en BD "+ usuario);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        }

    }

    public ArrayList<Usuario> getTodosLosUsuarios() throws ParseException {


        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + BDHandler.TABLA_USUARIO;

        bd=bdhandler.getReadableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(0)));
                usuario.setTwitter(cursor.getString(1));

                Log.e(TAG, " Get usuario "+ usuario);
                listaUsuarios.add(usuario);

            } while (cursor.moveToNext());
        }

        // return eventos
        System.out.println("Terminar getTODOSUSUARIO..."+String.valueOf(cursor.moveToFirst()));
        return listaUsuarios;
    }



    public Usuario getUsuario()
    {
        return usuario;
    }


}
