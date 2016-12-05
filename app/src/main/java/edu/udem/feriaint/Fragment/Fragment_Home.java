package edu.udem.feriaint.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class Fragment_Home extends Fragment{

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ComplexRecyclerViewAdapter compAdapter;

    public Fragment_Home() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recycler_viewer, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        mLayoutManager = new LinearLayoutManager(getActivity());


       // mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayList<Object> feed= null;
        try {
            feed = this.getSampleArrayList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        compAdapter=new ComplexRecyclerViewAdapter(feed);
        mRecyclerView.setAdapter(compAdapter);
        compAdapter.notifyDataSetChanged();


        return rootView;
    }

    private ArrayList<Object> getSampleArrayList() throws ExecutionException, InterruptedException {
        ArrayList<Object> items = new ArrayList<>();

        Date p=new Date();
        Calendar c= new GregorianCalendar();
        c.getTime();

        ArrayList<Evento>listaEventos;

        ArrayList<ContenidoCultural>listaContCult;

        listaEventos = MainActivity.repositorioJSON.getListaEventosJSON(false);
        listaContCult=MainActivity.repositorioJSON.getListaContenidoCultural(false);

        if(listaEventos.size()>listaContCult.size())
        {

            for (int i = 0; i <listaEventos.size() ; i++) {

                items.add(listaEventos.get(i));
                if(i<listaContCult.size())
                {
                    items.add(listaContCult.get(i));
                }
            }
        }
        else
        {
            for (int i = 0; i <listaContCult.size() ; i++) {

                items.add(listaContCult.get(i));
                if(i<listaEventos.size())
                {
                    items.add(listaEventos.get(i));
                }
            }
        }


        return items;
    }

}