package edu.udem.feriaint.Parser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Exchanger;

import edu.udem.feriaint.Adapters.EventoAdapter;

import edu.udem.feriaint.Data.BDHandler;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;


/**
 * Created by Andrea Arroyo on 15/10/2016.
 */

public class EventoJSON extends AsyncTask<Void, Void, Void >{

    private String TAG;
    private ProgressDialog pDialog;
    private Context context;
    private String url;
    private BDHandler bdHandler;
    private EventoDB eventoDB;
    private ArrayList<Evento> listaBD;
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


    /*@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();
    }*/


    public ArrayList<Evento> getListaEventos() { return listaEventos;  }

    @Override
    protected Void doInBackground(Void... voids) {

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
                    String idJSON=evento.getString("id");
                    String tituloJSON = evento.getString("titulo");
                    Date fechaInicioJSON = fechaf.parse(evento.getString("fechaInicio"));
                    Date fechaFinalJSON = fechaf.parse(evento.getString("fechaFinal"));
                    String lugarJSON = evento.getString("lugar");
                    String descripcionJSON = evento.getString("descripcion");
                    String tipoJSON = descripcionJSON;

                    //evento.getString("tipo");

                    Evento e = new Evento(tituloJSON, fechaInicioJSON, fechaFinalJSON, lugarJSON, descripcionJSON);
                    e.setId(Long.parseLong(idJSON));
                    e.setFavorito(false);
                    e.setTipo(tipoJSON);

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



        return null;

    }

    @Override
    protected void onPostExecute( Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog


            try {
                if (mRecyclerView!=null)
                layoutAdapter();
            } catch (ParseException e) {
                e.printStackTrace();
            }

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
                Log.d(TAG,"LISTA QUEDO"+listaEventos.size()+listaBD.size());

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