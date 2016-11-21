package edu.udem.feriaint.Parser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Modelos.Edicion;

/**
 * Created by Andrea Arroyo on 18/11/2016.
 */

public class EdicionJSON  extends AsyncTask<Object, Object, Edicion> {

    private Context context;
    private String url;
    private String TAG;
    private Edicion edicion;


    public EdicionJSON(Context context) {

        this.context = context;
        url="http://feriaint.herokuapp.com/app/edicion";

    }

    public Edicion getEdicion()
    {
        return edicion;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }


    @Override
    protected Edicion doInBackground(Object... voids) {


        SimpleDateFormat fechaf = new SimpleDateFormat("yyyy-MM-dd");
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try{

                JSONArray edicionArray = new JSONArray(jsonStr);

                for (int i = 0; i < edicionArray.length(); i++) {
                    JSONObject edicionJSON = edicionArray.getJSONObject(i);


                    String pais=edicionJSON.getString("pais");
                    Date fechaInicioJSON = fechaf.parse(edicionJSON.getString("fechaInicio"));
                    Date fechaFinalJSON = fechaf.parse(edicionJSON.getString("fechaFinal"));

                    //Agregar a bd TemaContCultural

                   edicion=new Edicion(pais, fechaInicioJSON, fechaFinalJSON);


                }



              //  Log.d(TAG,"LISTA QUEDO"+listaTemas.size());


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }
        return edicion;
    }

    @Override
    protected void onPostExecute(Edicion result) {
        super.onPostExecute(result);
        Log.e(TAG, "AFTER FINISHED"+ MainActivity.edicion.toString());




    }


}

