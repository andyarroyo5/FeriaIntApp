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
import android.webkit.WebView;
import android.webkit.WebViewClient;
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


import java.text.SimpleDateFormat;

import edu.udem.feriaint.Modelos.Edicion;
import edu.udem.feriaint.Parser.AppController;
import edu.udem.feriaint.Parser.EdicionJSON;
import edu.udem.feriaint.R;


/**
 * Created by Andrea Arroyo on 09/10/2016.
 */

public class AcercaDe extends AppCompatActivity {
    private String TAG ; //MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView pais;
    private TextView fecha;



    private String urlJsonObj = "https://feriaint.herokuapp.com/app/edicion";

    public AcercaDe() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_acercade);
        TAG=this.getClass().getSimpleName();

        pDialog=new ProgressDialog(this);
     //   pDialog.setMessage("Please wait...");
      //  pDialog.setCancelable(false);

        //makeJsonRequest();
       // makeJsonArrayRequest();

        //pais=(TextView) findViewById(R.id.pais);

        SimpleDateFormat fechaFormato= new SimpleDateFormat("EEEE MMM dd");

        Bundle b= getIntent().getExtras();


        String fechaInicio= fechaFormato.format(MainActivity.edicion.getFechaInicio());
        String fechaFinal= fechaFormato.format(MainActivity.edicion.getFechaFinal());


        fecha=(TextView) findViewById(R.id.edicion_fecha);
        //fecha.setText(b.getString("fechaInicio")+ "al "+ b.getString("fechaFinal"));
        fecha.setText(fechaInicio+ " -  "+ fechaFinal);


    }

    public void verEdicionesPasadas(View view )
    {
        WebView paginaWeb = new WebView(this);
        paginaWeb.loadUrl("http://www.udem.edu.mx/Esp/Vida-Estudiantil/Feria-Internacional/Paginas/default.aspx");

        paginaWeb.getSettings().setJavaScriptEnabled(true);
        paginaWeb.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        paginaWeb.setWebViewClient(new WebViewClient());
    }

}