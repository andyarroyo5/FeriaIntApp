package edu.udem.feriaint.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.udem.feriaint.Adapters.ContenidoCulturalAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 11/10/2016.
 */

public class Fragment_ContenidoCultural extends android.support.v4.app.Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ContenidoCultural> listaContenidoCultural;
    ContenidoCulturalAdapter mContenidoCulturalAdapter;

    public Fragment_ContenidoCultural(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cultura, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cultura);

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        listaContenidoCultural=new ArrayList<>();
        inicializarDatos();
        mLayoutManager =  new GridLayoutManager(getActivity(),2);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mContenidoCulturalAdapter=new ContenidoCulturalAdapter(listaContenidoCultural);
        mRecyclerView.setAdapter(mContenidoCulturalAdapter);
        mContenidoCulturalAdapter.notifyDataSetChanged();


        return rootView;
    }

    private void inicializarDatos() {

        listaContenidoCultural.add(new ContenidoCultural("Corea y su gastronomía",R.drawable.evento));
        listaContenidoCultural.add(new ContenidoCultural("Fun Facts",R.drawable.evento2));
        listaContenidoCultural.add(new ContenidoCultural("Historia de Corea",R.drawable.corea_logo));

    }

}
