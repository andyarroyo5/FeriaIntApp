package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import edu.udem.feriaint.Adapters.TemaAdapter;
import edu.udem.feriaint.Data.TemaContCulturalBD;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.R;

public class Filtrar_Activity extends AppCompatActivity {


    private RecyclerView rvTema;
    private RecyclerView.LayoutManager mLayoutManager;
    private TemaAdapter temaAdapter;
    private ArrayList<Tema> listaTemas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);

       rvTema=(RecyclerView) findViewById(R.id.rv_tema);

        if (rvTema != null) {
            rvTema.setHasFixedSize(true);
        }

        inicializarDatos();

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

    private void inicializarDatos() {
        listaTemas=new ArrayList<Tema>();
        TemaContCulturalBD temaBD=new TemaContCulturalBD(getApplicationContext());
        listaTemas=temaBD.getTemas();
        listaTemas.add(new Tema("Literatura"));
        listaTemas.add(new Tema("Arte"));
        listaTemas.add(new Tema("Entretenimiento"));
        listaTemas.add(new Tema("Ingeniería"));


    }
}
