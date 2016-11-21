package edu.udem.feriaint.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.udem.feriaint.Adapters.ContenidoCulturalAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.R;

public class Detalle_ContCultural extends AppCompatActivity {

    private String TAG;
    ContenidoCultural contCult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__cont_cultural);

        TAG=this.getClass().getSimpleName();


        TextView tituloContCult=(TextView) findViewById(R.id.txtTituloContCult);
        ImageView img=(ImageView) findViewById(R.id.imgContCultural);


        //getObject
        Bundle b=getIntent().getExtras();
       // contCult=b.getParcelable("contCult");

        contCult=new ContenidoCultural(b.getString("titulo"), b.getString("tema"));

        contCult.setFormato(b.getStringArrayList("formato"));
        contCult.setContenido(b.getStringArrayList("contenido"));

        tituloContCult.setText(contCult.getTitulo());
        Log.d(TAG,contCult.toString());


    }



    public void setLayout()
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container);
       // ArrayList<String> formato=contCult.getFormato();
        for ( String f: contCult.getFormato()) {
            switch (f){


                case "lineaTexto" : break;
                case "areaTexto" : break;

                case "image" : break;
                case "video" : break;
                case "audio" : break;


                case "paginaWeb" : break;

                case "trivia2" : break;
                case "trivia4" : break;






                default: break;
            }


        }

    }


}
