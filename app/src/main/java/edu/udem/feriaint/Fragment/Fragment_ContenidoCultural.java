package edu.udem.feriaint.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.ContenidoCulturalAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.Parser.ContCulturalJSON;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 11/10/2016.
 */

public class Fragment_ContenidoCultural extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public String TAG;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipeContainerCultura;
    ArrayList<ContenidoCultural> listaContenidoCultural;
    ContenidoCulturalAdapter mContenidoCulturalAdapter;

    public Fragment_ContenidoCultural(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cultura, container, false);


        swipeContainerCultura =(SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainerCultura);
        swipeContainerCultura.setOnRefreshListener(this);
        setColorRefresh();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cultura);

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        listaContenidoCultural=new ArrayList<>();
        mLayoutManager =  new GridLayoutManager(getActivity(),2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //swipeContainerCultura.setRefreshing(true);
       // getJSON();

        try {
            listaContenidoCultural = MainActivity.repositorioJSON.getListaContenidoCultural(false);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            layoutAdapter(listaContenidoCultural);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return rootView;
    }


    public void layoutAdapter(ArrayList<ContenidoCultural> listaContenidoCultural) throws ExecutionException, InterruptedException {
        if(listaContenidoCultural.isEmpty())
        {
            listaContenidoCultural = MainActivity.repositorioJSON.getListaContenidoCultural(false);
        }

        checarFavoritos();

        mContenidoCulturalAdapter = new ContenidoCulturalAdapter(listaContenidoCultural);
        //Especificar Adapter
        mRecyclerView.setAdapter(mContenidoCulturalAdapter);
        mContenidoCulturalAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            filtrarTemasContCult();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void filtrarTemasContCult() throws ExecutionException, InterruptedException {
        ArrayList<Tema> listaTemasContCult=new ArrayList<>();

        for (Tema t:MainActivity.repositorioJSON.getTemasContCult(false)) {

            if (t.isSeleccionado())
            {
                listaTemasContCult.add(t);
            }
        }

        ArrayList<ContenidoCultural> listaContenidoFiltrar=new ArrayList<>();

        for (ContenidoCultural cont:listaContenidoCultural) {

            for (Tema t:listaTemasContCult) {

                    if(cont.getTema().getId()==t.getId())
                        listaContenidoFiltrar.add(cont);

            }

        }

        for (int i = 0; i < listaContenidoFiltrar.size(); i++) {

            Log.e(TAG,"Filtrar"+listaContenidoFiltrar.get(i).getTema().getId());

        }

        //listaContenidoCultural=listaContenidoFiltrar;

        layoutAdapter(listaContenidoFiltrar);

    }

    @Override
    public void onRefresh() {

        try {
            //  eJson.getEventosBD();
            System.out.println("On refresh");
            listaContenidoCultural = MainActivity.repositorioJSON.getListaContenidoCultural(true);
            MainActivity.repositorioJSON.getTemasContCult(true);
            swipeContainerCultura.setRefreshing(false);

            layoutAdapter(listaContenidoCultural);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setColorRefresh() {
        swipeContainerCultura.setColorSchemeResources(R.color.accent,
                android.R.color.holo_green_light ,
                android.R.color.holo_orange_light ,
                android.R.color.holo_red_light);
    }


    public void checarFavoritos()
    {

        ArrayList<ContenidoCultural> listaFavoritos=MainActivity.currentUsuario.getListaContCultFavoritos();

        for (ContenidoCultural cont:listaContenidoCultural) {

            for (ContenidoCultural fav:listaFavoritos) {

                if (cont.getId()==fav.getId())
                {
                    cont.setFavorito(true);
                }

            }

        }
    }

}
