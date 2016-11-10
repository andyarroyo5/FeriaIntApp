package edu.udem.feriaint.Parser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Modelos.ContenidoCultural;

/**
 * Created by Andrea Arroyo on 09/11/2016.
 */

public class ContCulturalJSON extends AsyncTask<Void, Void, Void > {

    private Context context;
    private String url;
    private ArrayList<ContenidoCultural> listaContenidos;

    private String TAG;


    public ContCulturalJSON(Context context) {

        this.context = context;
        url = "https://feriaint.herokuapp.com/app/eventos";
        listaContenidos = new ArrayList<ContenidoCultural>();
        TAG=getClass().getSimpleName();
    }

    public ArrayList<ContenidoCultural> getListaContCultural() { return listaContenidos;  }


    @Override
    protected Void doInBackground(Void... voids) {

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try{
                JSONArray contCultural = new JSONArray(jsonStr);



                }catch (final JSONException e) {
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

    }

}
