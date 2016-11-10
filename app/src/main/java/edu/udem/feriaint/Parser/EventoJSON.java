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


    private ArrayList<Evento> listaEventos;
    private EventoAdapter eventoAdapter;
    //private SwipeRefreshLayout refreshLayout;

   private EventoDB eventoDB;
    //private SQLiteDatabase bd;

    public EventoJSON(Context context) {

        this.context = context;
        url = "https://feriaint.herokuapp.com/app/eventos";
        listaEventos = new ArrayList<Evento>();
        TAG=getClass().getSimpleName();
        eventoDB= new EventoDB(context);

    }



    public ArrayList<Evento> getListaEventos() { return listaEventos;  }

    @Override
    protected Void doInBackground(Void... voids) {

       // bd.onUpgrade(bd,bd.getBDVersion(),bd.getBDVersion()+1);

      /*  try {

          bdHandler=new BDHandler(context);
          bdHandler.onUpgrade(bdHandler.getReadableDatabase(),bdHandler.getBDVersion(), bdHandler.getBDVersion()+1);
            Log.e(TAG, "UPGRADE db "+bdHandler.getBDVersion());
        } catch (Exception e) {
            Log.e(TAG, "UPGRADE db "+e.toString());
       }*/


        SimpleDateFormat fechaf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

     //   eventoDB.open();

        if (jsonStr != null) {
            try {

                // Getting JSON Array node
                JSONArray eventos = new JSONArray(jsonStr);// = jsonObj.getJSONArray("eventos");

                // looping through All Contacts

                for (int i = 0; i < eventos.length(); i++) {
                    JSONObject evento = eventos.getJSONObject(i);
                   // String idJSON=evento.getString("user_id");
                    String tituloJSON = evento.getString("titulo");
                    Date fechaInicioJSON = fechaf.parse(evento.getString("fechaInicio"));
                    Date fechaFinalJSON = fechaf.parse(evento.getString("fechaFinal"));
                    String lugarJSON = evento.getString("lugar");
                    String descripcionJSON = evento.getString("descripcion");
                    //String tipoJSON = evento.getString("tipo");

                    Evento e = new Evento(tituloJSON, fechaInicioJSON, fechaFinalJSON, lugarJSON, descripcionJSON);
                    e.setId(i);
                   // e.setTipo(tipoJSON);


                    listaEventos.add(e);
                    //Log.e(TAG,"BD, agregar : " + listaEventos.get(i).getTitulo()+" "+listaEventos.get(i).getId());

                        // eventoDB.insert(e);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }
        return null;

    }

    @Override
    protected void onPostExecute( Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
       //layoutAdapter();
     //  eventoDB.close();

    }


}