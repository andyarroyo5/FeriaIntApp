package edu.udem.feriaint.Parser;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Adapters.TemaAdapter;
import edu.udem.feriaint.Modelos.Tema;

/**
 * Created by Andrea Arroyo on 18/11/2016.
 */

public class TemaJSON extends AsyncTask<Object, Object, ArrayList<Tema>> {

    private Context context;
    RecyclerView mRecyclerView;

    private String url;
    private ArrayList<Tema> listaTemas;
    private TemaAdapter temaAdapter;
    private String tipo;

    private String TAG;

    public TemaJSON(Context context, String url, String tipo) {

        listaTemas=new ArrayList<Tema>();
        this.context = context;
        this.url=url;
        this.tipo=tipo;

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

    }


    @Override
    protected ArrayList<Tema> doInBackground(Object... voids) {

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try{

                JSONArray temaArray = new JSONArray(jsonStr);

                for (int i = 0; i < temaArray.length(); i++) {
                    JSONObject temaJSON = temaArray.getJSONObject(i);

                    Long temaId=temaJSON.getLong("id");
                    String temaNombre=temaJSON.getString("nombre");

                    Tema tema=new Tema(temaId, temaNombre,tipo);
                    Log.e(TAG,tema.toString());
                    listaTemas.add(tema);
                }


                Log.d(TAG,"LISTA TEMAS QUEDO"+listaTemas.size());


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }


        return listaTemas;
    }

    @Override
    protected void onPostExecute( ArrayList<Tema> result) {
        super.onPostExecute(result);

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

        temaAdapter = new TemaAdapter (listaTemas);

        //Especificar Adapter
        mRecyclerView.setAdapter(temaAdapter);
        temaAdapter.notifyDataSetChanged();
    }
}
