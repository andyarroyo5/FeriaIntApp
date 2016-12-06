package edu.udem.feriaint.Parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.TemaAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Tema;

/**
 * Created by Andrea Arroyo on 18/11/2016.
 */

public class TemaJSON extends AsyncTask<Object, Object, ArrayList<Tema>> {

    private Context context;
    RecyclerView mRecyclerView;

    private String url;
    private ProgressDialog pDialog;
    private ArrayList<Tema> listaTemas;
    private TemaAdapter temaAdapter;
    private String tipo;
    String URL_TEMAS="http://feriaint.herokuapp.com/app/temasEventos";
    String URL_CONTENIDO="http://feriaint.herokuapp.com/app/temasModulos";


    private String TAG;

    public TemaJSON(Context context,String tipo) {

        listaTemas=new ArrayList<Tema>();
        this.context = context;
        this.tipo=tipo;

        if(tipo.equals("evento"))
        {
            url=URL_TEMAS;
        }
        else
        {
            url=URL_CONTENIDO;
        }


    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public ArrayList<Tema> getListaContenidos() {
        return listaTemas;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //pDialog=new ProgressDialog(context);
       // pDialog.show();

    }


    @Override
    protected ArrayList<Tema> doInBackground(Object... voids) {

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try{
                Tema tema = new Tema();
                if (tipo.equals("evento"))
                {



                    JSONArray array=new JSONArray(jsonStr);
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject jObject = array.getJSONObject(i);

                        Long temaId = jObject.getLong("id");
                        String temaNombre = jObject.getString("nombre");
                        tema = new Tema(temaId, temaNombre, tipo);
                        listaTemas.add(tema);

                    }







                }
                else {

                    JSONObject jObject = new JSONObject(jsonStr);
                    Iterator<String> keys = jObject.keys();

                    while (keys.hasNext()) {
                        // Get the key
                        Log.e(TAG, keys.toString());
                        String key = keys.next();
                        JSONObject value = jObject.getJSONObject(key);
                        Long temaId = value.getLong("id");
                        String temaNombre = value.getString("nombre");
                        tema = new Tema(temaId, temaNombre, tipo);
                        Log.e(TAG, tema.toString());
                        listaTemas.add(tema);


                    }


                    for (ContenidoCultural e : MainActivity.repositorioJSON.getListaContenidoCultural(false)) {
                        if (!listaTemas.contains(e.getTema())) {
                            Log.e(TAG, "no contiene a " + e.getTema());
                        }

                        for (Tema t : listaTemas) {

                            if (t.getId() == e.getTema().getId()) {
                                t.setColor(e.getTema().getColor());
                            }

                        }
                    }

                }
                Log.d(TAG,"LISTA TEMAS QUEDO"+listaTemas.size());


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            }else {
            Log.e(TAG, "Couldn't get json from server.");

        }


        return listaTemas;
    }

    @Override
    protected void onPostExecute( ArrayList<Tema> result) {
        super.onPostExecute(result);

     //   pDialog.hide();
        Log.e(TAG,"TERMINO TemaJSON "+tipo);

        /*
         try {
            if (mRecyclerView!=null)
                layoutAdapter();


        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    public void layoutAdapter() throws ParseException {

        temaAdapter = new TemaAdapter (listaTemas,tipo);

        //Especificar Adapter
        mRecyclerView.setAdapter(temaAdapter);
        temaAdapter.notifyDataSetChanged();
    }
}
