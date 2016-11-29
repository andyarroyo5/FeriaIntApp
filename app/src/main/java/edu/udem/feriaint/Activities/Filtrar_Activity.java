package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import edu.udem.feriaint.Adapters.TemaAdapter;
import edu.udem.feriaint.Data.TemaContCulturalBD;
import edu.udem.feriaint.Data.TemaEventosBD;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.R;

public class Filtrar_Activity extends AppCompatActivity {


    private String TAG;
    private RecyclerView rvTema;
    private RecyclerView.LayoutManager mLayoutManager;
    private TemaAdapter temaAdapter;
    private ArrayList<Tema> listaTemas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);

       TAG=getClass().getSimpleName();

       rvTema=(RecyclerView) findViewById(R.id.rv_tema);

        if (rvTema != null) {
            rvTema.setHasFixedSize(true);
        }

        //Get intent de FAB diciendo el tipo de temas dependiendo de su pos en la app
        Bundle b = getIntent().getExtras();
        inicializarDatos(b.getString("tipo"));

        mLayoutManager = new GridLayoutManager(this,3);
        rvTema.setLayoutManager(mLayoutManager);
        temaAdapter = new TemaAdapter(listaTemas);
        //Especificar Adapter
        rvTema.setAdapter(temaAdapter);
        temaAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent resp=new Intent();
        resp.putExtra("temasEscogidos",listaTemas);
        finish();

    }

    private void inicializarDatos(String tipo) {

        listaTemas=new ArrayList<Tema>();

        if (tipo.equalsIgnoreCase("evento"))
        {
            //Con BD
            /*TemaEventosBD temaBDEventos=new TemaEventosBD(getApplicationContext());
            listaTemas=temaBDEventos.getTemas();*/
            Log.e(TAG,"EVENTO filtrar");
            listaTemas=MainActivity.listaTemasEventos;
        }
        else
        {
            //TemaContCulturalBD temaBD=new TemaContCulturalBD(getApplicationContext());
            //listaTemas=temaBD.getTemas();
            Log.e(TAG,"CONT CULT filtrar");
            listaTemas=MainActivity.listaTemasContCult;

        }

        if(listaTemas.isEmpty())
            Log.e(TAG,"Lista de "+tipo+"está vacía");
    }
}
