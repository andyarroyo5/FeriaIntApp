package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Adapters.TemaAdapter;
import edu.udem.feriaint.Data.TemaContCulturalBD;
import edu.udem.feriaint.Data.TemaEventosBD;
import edu.udem.feriaint.Fragment.Fragment_Evento;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.R;

public class Filtrar_Activity extends AppCompatActivity {


    private String TAG;
    private RecyclerView rvTema;
    private RecyclerView.LayoutManager mLayoutManager;
    private TemaAdapter temaAdapter;
    private ArrayList<Tema> listaTemas;
    private String tipo;

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
        tipo=b.getString("tipo");
        try {
            inicializarDatos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mLayoutManager = new GridLayoutManager(this,3);
        rvTema.setLayoutManager(mLayoutManager);
        temaAdapter = new TemaAdapter(listaTemas,tipo);
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

    private void inicializarDatos() throws ExecutionException, InterruptedException {


        listaTemas=new ArrayList<Tema>();

        if (tipo.equalsIgnoreCase("evento"))
        {
             Log.e(TAG,"EVENTO filtrar");
            listaTemas=MainActivity.repositorioJSON.getTemasEventos(false);

        }
        else
        {
            Log.e(TAG,"CONT CULT filtrar");
            listaTemas=MainActivity.repositorioJSON.getTemasContCult(false);

        }

        if(listaTemas.isEmpty())
            Log.e(TAG,"Lista de "+tipo+"está vacía");
        else
            Log.e(TAG,"Lista de "+tipo+" "+listaTemas.size());
    }

    public void aplicarFiltro(View view) throws ExecutionException, InterruptedException {


           finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


}
