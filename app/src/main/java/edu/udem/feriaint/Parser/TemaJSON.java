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
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Tema;

/**
 * Created by Andrea Arroyo on 18/11/2016.
 */

public class TemaJSON extends AsyncTask<Void, Void, Void > {

    private Context context;
    RecyclerView mRecyclerView;

    private String url;
    private ArrayList<Tema> listaTemas;
    private TemaAdapter temaAdapter;

    private String TAG;

    public TemaJSON(Context context) {
        this.context = context;
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
    protected Void doInBackground(Void... voids) {



        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try{

                JSONArray temaArray = new JSONArray(jsonStr);

                for (int i = 0; i < temaArray.length(); i++) {
                    JSONObject temaJSON = temaArray.getJSONObject(i);


                    Long temaId=temaJSON.getLong("tema_id");
                    String temaNombre=temaJSON.getString("tema_nombre");



                    //Agregar a bd TemaContCultural
                    Tema tema=new Tema(temaId, temaNombre);


                    Log.e(TAG,tema.toString());
                }

                Log.d(TAG,"LISTA QUEDO"+listaTemas.size());


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }


        return null;
    }

    @Override
    protected void onPostExecute( Void result) {
        super.onPostExecute(result);


        try {
            if (mRecyclerView!=null)
                layoutAdapter();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void layoutAdapter() throws ParseException {

        temaAdapter = new TemaAdapter (listaTemas);

        //Especificar Adapter
        mRecyclerView.setAdapter(temaAdapter);
        temaAdapter.notifyDataSetChanged();
    }
}
