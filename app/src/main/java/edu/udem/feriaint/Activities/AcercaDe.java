package edu.udem.feriaint.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import edu.udem.feriaint.Modelos.Edicion;
import edu.udem.feriaint.Parser.AppController;
import edu.udem.feriaint.R;


/**
 * Created by Andrea Arroyo on 09/10/2016.
 */

public class AcercaDe extends AppCompatActivity {
    private String TAG ; //MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView pais;
    private TextView fecha;
    private String jsonResponse;


    private String urlJsonObj = "https://feriaint.herokuapp.com/app/edicion";

    public AcercaDe() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_acercade);
        TAG=this.getClass().getSimpleName();

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //makeJsonRequest();
        makeJsonArrayRequest();

        //pais=(TextView) findViewById(R.id.pais);
        fecha=(TextView) findViewById(R.id.edicion_fecha);



    }


    public void makeJsonRequest() {
       showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String paisJSON = response.getString("pais");
                    String fechaInicio = response.getString("fechaInicio");
                    String fechaFinal = response.getString("fechaFinal");
                    String fecha= fechaInicio+" al "+fechaFinal;


                    jsonResponse = "";
                    jsonResponse += "Pais: " + paisJSON + "\n\n";
                    jsonResponse += "Fecha: " + fecha + "\n\n";

                   // pais.setText(jsonResponse);
                    //edicion=new Edicion(paisJSON,fecha);
                    //System.out.println(edicion.getPais()+" "+edicion.getFecha());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonObj,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject edicion = (JSONObject) response
                                        .get(i);

                                String paisJSON = edicion.getString("pais");
                                String fechaInicio = edicion.getString("fechaInicio");
                                String fechaFinal = edicion.getString("fechaFinal");
                                String fechaJSON= fechaInicio+" al "+fechaFinal;


                                jsonResponse = "";
                                jsonResponse += "Pais: " + paisJSON + "\n\n";
                                jsonResponse += "Fecha: " + fechaJSON + "\n\n";

                                pais.setText(paisJSON);
                                fecha.setText(fechaJSON);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
               hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

   private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}