package edu.udem.feriaint.Parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.udem.feriaint.Adapters.EventoAdapter;

import edu.udem.feriaint.Data.BDHandler;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Modelos.Evento;


/**
 * Created by Andrea Arroyo on 15/10/2016.
 */

public class EventoJSON extends AsyncTask<Object, Object, ArrayList<Evento>> {

    private String TAG;
    private ProgressDialog pDialog;
    private Context context;
    private String url;
    private BDHandler bdHandler;
    private EventoDB eventoDB;
    private ArrayList<Evento> listaBD;
    private SwipeRefreshLayout swipeContainerEventos;

    EventoAdapter eventoAdapter;
    ProgressDialog pd;
    RecyclerView mRecyclerView;

    private ArrayList<Evento> listaEventos;



    //private SQLiteDatabase bd;

    public EventoJSON(Context context) {

        this.context = context;
        url = "https://feriaint.herokuapp.com/app/eventos";
        listaEventos = new ArrayList<Evento>();
        TAG=getClass().getSimpleName();
        eventoDB=new EventoDB(context);
    }

    public void setRecyclerViewer( RecyclerView mRecyclerViewer)
    {
        this.mRecyclerView=mRecyclerViewer;
    }


    public void setSwipeContainer (SwipeRefreshLayout swipeContainerEventos)
    {
        this.swipeContainerEventos=swipeContainerEventos;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (swipeContainerEventos!=null)
        swipeContainerEventos.setRefreshing(true);
    }


    public ArrayList<Evento> getListaEventos() { return listaEventos;  }

    @Override
    protected ArrayList<Evento> doInBackground(Object... voids) {

        SimpleDateFormat fechaf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try {

                // Getting JSON Array node
                JSONArray eventos = new JSONArray(jsonStr);// = jsonObj.getJSONArray("eventos");

                // looping through All Contacts

                for (int i = 0; i < eventos.length(); i++) {
                    JSONObject evento = eventos.getJSONObject(i);


                    Long idJSON=evento.getLong("id");
                    String tituloJSON = evento.getString("titulo");
                    Date fechaInicioJSON = fechaf.parse(evento.getString("fechaInicio"));
                    Date fechaFinalJSON = fechaf.parse(evento.getString("fechaFinal"));
                    String lugarJSON = evento.getString("lugar");
                    String descripcionJSON = evento.getString("descripcion");
                    Long tema_idJSON=evento.getLong("tema_id");
                    String hashtagJSON="#"+evento.getString("hashtag");




                    //TODO get var tipo
                    //String tipoJSON = descripcionJSON;

                    Evento e = new Evento(idJSON,tituloJSON, fechaInicioJSON, fechaFinalJSON, lugarJSON, descripcionJSON,tema_idJSON,hashtagJSON);
                    //e.setFavorito(false);
                    Log.e(TAG, "evento agregado"+ e.getTitulo()+" "+ e.getTema_id() + e.getFechaInicio()+" "+e.getFechaFinal());
                    listaEventos.add(e);
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }



        try {
            agregarEventos();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return listaEventos;

    }

    @Override
    protected void onPostExecute( ArrayList<Evento> result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog

            try {
                if (mRecyclerView!=null)
                layoutAdapter();

                if (swipeContainerEventos!=null)
                swipeContainerEventos.setRefreshing(false);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        //animación mientras espera
      /*  if (pd != null)
        {
            pd.dismiss();
        }*/


        Log.e(TAG,"--TERMINO BACKGROUND--");
    }

    public boolean getEventosTodos() throws ParseException {

        listaBD=eventoDB.getTodosLosEventos();

        if (listaBD!=null && listaBD.size()!=0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void agregarEventos() throws ParseException {
        if (getEventosTodos()) {

            Log.e(TAG, " EVENTOS JSON" + listaEventos.size()+"EVENTOS REFRESH - BD"+listaBD.size());

            //Si la lista actualizada tiene más eventos que los que estan en base de datos



            int size=listaEventos.size();

            if(listaEventos.size()<listaBD.size() )
            {
                Log.d(TAG,"se quito de bd "+listaEventos.size()+listaBD.size());
            }
            else
            {
                for (int i = 0; i < listaEventos.size(); i++) {

                    for (int j = 0; j < listaBD.size(); j++) {
                        if (listaBD.get(j).getId() == (listaEventos.get(i).getId())) {
                            Log.d(TAG, "REMOVE LISTA" + listaEventos.get(i).getId());
                            //listaEventos.add(listaBD.get(i));
                            listaBD.remove(listaEventos.get(i));
                            break;
                        }
                    }
                }

            }

            if(listaEventos.size()>listaBD.size() )
            {
                Log.d(TAG,"LISTA QUEDO"+listaEventos.size()+listaBD.size());
                //si quedaron eventos que no estaban en la BD agregar
                insertarEventos();
            }


        } else {
            Log.e(TAG, "BD vacia");
            Log.e(TAG, " EVENTOS JSON" + listaEventos.size()+"EVENTOS bd"+listaBD.size());

            insertarEventos();
        }
    }

    public void insertarEventos()
    {
        for (int i = 0; i < listaEventos.size(); i++)
        {
            eventoDB.insertarEvento(listaEventos.get(i));
        }
    }


    public void layoutAdapter() throws ParseException {
        listaEventos=eventoDB.getTodosLosEventos();
        eventoAdapter = new EventoAdapter(listaEventos);

        //Especificar Adapter
        mRecyclerView.setAdapter(eventoAdapter);
        eventoAdapter.notifyDataSetChanged();
    }


}