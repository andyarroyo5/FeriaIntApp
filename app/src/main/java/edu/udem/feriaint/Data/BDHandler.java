package edu.udem.feriaint.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String FAVORITO = "favorito";


    String CREAR_TABLA_EVENTO = "CREATE TABLE " + TABLA_EVENTO + "("
            + ID_EVENTO + " INTEGER PRIMARY KEY,"
            + TITULO + " TEXT,"
            + FECHA_INICIO + " TEXT,"
            + FECHA_FINAL + " TEXT,"
            + LUGAR + " TEXT,"
            + DESCRIPCION + " TEXT,"
            + TIPO + " TEXT,"
            + FAVORITO + " TEXT"+")";


    public static final String TABLA_USUARIO = "Usuario";

    // Contacts Table Columns names
    public static final String ID_USUARIO = "id_Usuario";
    public static final String CORREO = "correo";
    public static final String NOMBRE = "nombre";
    public static final String CARRERA = "carrera";
    public static final String TWITTER="twitter";
    public static final String PUNTOS = "puntos";


    String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "("
            + ID_USUARIO + " INTEGER PRIMARY KEY,"
            + CORREO+ " TEXT,"
            + NOMBRE + " TEXT,"
            + CARRERA + " TEXT,"
            + TWITTER+" TEXT, "
            + PUNTOS+ " TEXT"+")";

    public static final String TABLA_TEMA_CONTCULTURAL = "Tema";

    // Contacts Table Columns names
    public static final String ID_TEMA_CONTCULTURAL = "id_Tema";
    public static final String NOMBRE_TEMA_CONTCULTURAL = "nombre";


    String CREAR_TABLA_TEMA_CONTCULTURAL = "CREATE TABLE " + TABLA_TEMA_CONTCULTURAL + "("
            + ID_TEMA_CONTCULTURAL + " INTEGER PRIMARY KEY,"
            + NOMBRE_TEMA_CONTCULTURAL + " TEXT "+")";

    public static final String TABLA_TEMA_EVENTOS = "Tema_Eventos";

    // Contacts Table Columns names
    public static final String ID_TEMA_EVENTOS = "id_Tema";
    public static final String NOMBRE_TEMA_EVENTOS = "nombre";


    String CREAR_TABLA_TEMA_EVENTOS = "CREATE TABLE " + TABLA_TEMA_EVENTOS + "("
            + ID_TEMA_EVENTOS + " INTEGER PRIMARY KEY,"
            + NOMBRE_TEMA_EVENTOS + " TEXT "+")";

    public static final String TABLA_EDICION = "Edicion";

    // Contacts Table Columns names
    public static final String ID_EDICION = "id_Edicion";
    public static final String PAIS = "pais";
    public static final String FECHA_INICIO_EDICION = "fecha_inicio_edicion";
    public static final String FECHA_FINAL_EDICION = "fecha_final_edicion";

    String CREAR_TABLA_EDICION = "CREATE TABLE " + TABLA_EDICION + "("
            + ID_EDICION + " INTEGER PRIMARY KEY,"
            + PAIS + " TEXT, "
            + FECHA_INICIO_EDICION + " TEXT,"
            + FECHA_FINAL_EDICION + "TEXT"+")";

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
        bd.execSQL(CREAR_TABLA_TEMA_CONTCULTURAL);
        bd.execSQL(CREAR_TABLA_EDICION);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop older table if existed
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_EVENTO);
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIO);
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_TEMA_CONTCULTURAL);
        bd.execSQL("DROP TABLE IF EXISTS " + TABLA_EDICION);
             // Create tables again
        onCreate(bd);
    }

}
