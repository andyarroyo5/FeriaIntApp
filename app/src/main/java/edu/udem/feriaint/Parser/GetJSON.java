package edu.udem.feriaint.Parser;

import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Edicion;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.Modelos.Usuario;

/**
 * Created by Andrea Arroyo on 28/11/2016.
 */

final public class GetJSON {

    public static Usuario currentUsuario;
    public static ArrayList<Evento> eventos;
    public static ArrayList<ContenidoCultural> contCult;
    public static ArrayList<Tema> listaTemasEventos;
    public static ArrayList<Tema> listaTemasContCult;
    public static Edicion edicion;


   public Context context;
   public GetJSON()
   {

   }

    public void getTemasEventos() throws ExecutionException, InterruptedException {

        TemaJSON temasEJSON=new TemaJSON(context, "evento");
        listaTemasEventos=temasEJSON.execute().get();
    }

}
