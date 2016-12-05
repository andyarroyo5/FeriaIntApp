package edu.udem.feriaint.Parser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class GetJSON {

    private String TAG ;
    public static Usuario currentUsuario;
    public static ArrayList<Evento> listaEventos;
    public static ArrayList<ContenidoCultural> listaContCult;
    public static ArrayList<Tema> listaTemasEventos;
    public static ArrayList<Tema> listaTemasContCult;
    public static Edicion edicion;
    public Context context;

    public final String EVENTO="evento";
    public final String CONTCULT="contenidoCultural";


   public GetJSON(Context context)
   {
       this.context=context;
       TAG=this.getClass().getSimpleName();

       listaEventos=new ArrayList<>();
       listaContCult=new ArrayList<>();
       listaTemasEventos=new ArrayList<>();
       listaTemasContCult =new ArrayList<>();
       currentUsuario=new Usuario();
       edicion=new Edicion();
   }


    public Edicion getEdicionJSON() throws ExecutionException, InterruptedException {

        EdicionJSON edicionJSON=new EdicionJSON(context);
        //edicionJSON.execute();
        edicion= edicionJSON.execute().get();
        //while(!edicionJSON.getStatus().equals(AsyncTask.Status.FINISHED));

        return edicion;
    }

    public ArrayList<Tema> getTemasEventos(boolean refresh) throws ExecutionException, InterruptedException {

        TemaJSON temasEJSON = new TemaJSON(context, EVENTO);
        if(listaTemasEventos.isEmpty() || refresh) {
            // listaTemasEventos=temasEJSON.execute().get();
            listaTemasEventos=temasEJSON.execute().get();
            setColorTemas(EVENTO);
        }

        return listaTemasEventos;

    }


    public ArrayList<Tema> getTemasContCult(boolean refresh) throws ExecutionException, InterruptedException {
        TemaJSON temasContCultJSON = new TemaJSON(context, CONTCULT);

        if(listaTemasContCult.isEmpty() || refresh) {
            //listaTemasEventos=temasEJSON.execute().get();  esto hace esperar al thread principal
            listaTemasContCult= temasContCultJSON.execute().get();
            setColorTemas(CONTCULT);


        }

        return listaTemasContCult;

    }

    public ArrayList<Evento> getListaEventosJSON(boolean refresh) throws ExecutionException, InterruptedException {

        if(listaEventos.isEmpty() || refresh)
        {
            EventoJSON eventosJSON=new EventoJSON(context);
            listaEventos= eventosJSON.execute().get();
            setColorTemas(EVENTO);

        }

        return listaEventos;
    }

    public ArrayList<ContenidoCultural> getListaContenidoCultural(boolean refresh) throws ExecutionException, InterruptedException {

        if(listaContCult.isEmpty() || refresh)
        {
            ContCulturalJSON contCultJSON=new ContCulturalJSON(context);
            listaContCult= contCultJSON.execute().get();
            setColorTemas(CONTCULT);
        }
        return listaContCult;

    }

    public void setColorTemas(String tipo) throws ExecutionException, InterruptedException {
        if(tipo.equals(EVENTO))
        {
            for (Evento e:getListaEventosJSON(false)) {

                for (Tema t:getTemasEventos(false)) {

                    if(e.getTema().getId()==t.getId())
                    {
                        e.setTema(t);
                    }

                }

            }
        }
        else
        {
            for (ContenidoCultural contCult:listaContCult) {

                for (Tema t:getTemasContCult(false)) {

                    if(contCult.getTema().getId()==t.getId())
                    {
                        contCult.setTema(t);
                    }

                }

            }
        }

    }


}
