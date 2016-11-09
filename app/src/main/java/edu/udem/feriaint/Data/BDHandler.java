package edu.udem.feriaint.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.udem.feriaint.Modelos.Evento;

/**
 * Created by laboratorio on 10/16/16.
 */


public class BDHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int BD_VERSION = 1;

    // Database Name
    private static final String BD_NOMBRE = "feriaIntUDEM";

    // Contacts table name
    public static final String TABLA_EVENTO = "Evento";

    // Contacts Table Columns names
    public static final String ID_EVENTO = "id_Evento";
    public static final String TITULO = "titulo";
    public static final String FECHA_INICIO = "fechaInicio";
    public static final String FECHA_FINAL = "fechaFinal";
    public static final String LUGAR = "lugar";
    public static final String DESCRIPCION = "descripcion";
    public static final String TIPO = "tipo";


    String CREAR_TABLA_EVENTO = "CREATE TABLE " + TABLA_EVENTO + "("
            + ID_EVENTO + " INTEGER PRIMARY KEY,"
            + TITULO + " TEXT,"
            + FECHA_INICIO + " TEXT,"
            + FECHA_FINAL + " TEXT,"
            + LUGAR + " TEXT,"
            + DESCRIPCION + " TEXT,"
            + TIPO + " TEXT"+")";


    public static final String TABLA_USUARIO = "Usuario";

    // Contacts Table Columns names
    public static final String ID_USUARIO = "id_Usuario";
    public static final String CORREO = "correo";
    public static final String NOMBRE = "nombre";
    public static final String CARRERA = "carrera";
    public static final String PUNTOS = "puntos";
    public static final String TWITTER="twitter";

    String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "("
            + ID_USUARIO + " INTEGER PRIMARY KEY,"
            + CORREO+ " TEXT,"
            + NOMBRE + " TEXT,"
            + CARRERA + " TEXT,"
            + PUNTOS+ " TEXT,"
            + TWITTER+" TEXT"+")";


    private static final String TABLA_USUARIO_EVENTO = "UsuarioEvento";

    // Contacts Table Columns names
    private static final String ID_USUARIO_EVENTO = "id_UsuarioEvento";
    private static final String USUARIO_ID = "usuario_id";
    private static final String EVENTO_ID = "evento_id";

    String CREAR_TABLA_USUARIO_EVENTO = "CREATE TABLE " + TABLA_USUARIO_EVENTO + "("
            + ID_USUARIO_EVENTO + " INTEGER PRIMARY KEY," + USUARIO_ID + " INTEGER,"
            + EVENTO_ID + " INTEGER"+")";



    public BDHandler(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    public int getBDVersion()
    {
        return BD_VERSION;

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase bd) {

        bd.execSQL(CREAR_TABLA_EVENTO);
        bd.execSQL(CREAR_TABLA_USUARIO);
       // bd.execSQL( CREAR_TABLA_USUARIO_EVENTO);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop older table if existed
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_EVENTO);
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIO);
       // bd.execSQL("DROP TABLE IF EXISTS " + CREAR_TABLA_USUARIO_EVENTO);

        // Create tables again
        onCreate(bd);
    }

}
