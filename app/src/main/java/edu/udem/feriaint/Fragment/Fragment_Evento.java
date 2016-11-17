package edu.udem.feriaint.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.udem.feriaint.Adapters.EventoAdapter;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Parser.EventoJSON;

import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 07/10/2016.
 */

public class Fragment_Evento extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private String TAG ;
    EventoJSON eJson;
    EventoAdapter eventoAdapter;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Evento> listaEventos;
    private ArrayList<Evento> listaEventosRefresh;

    private EventoDB eventoDB;
   // private Usuario currentUsuario;
    TextView txtError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getSimpleName();
        //tomar todos los favoritos ?



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_evento, container, false);

        eventoDB=new EventoDB(rootView.getContext());

        CalendarView cal=(CalendarView) rootView.findViewById(R.id.calendarView);

     //   txtError =(TextView) rootView.findViewById(R.id.txtError);
      swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
      swipeContainer.setOnRefreshListener(this);

        //Adaptador
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

       if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

       mLayoutManager = new LinearLayoutManager(getActivity());
       mRecyclerView.setLayoutManager(mLayoutManager);

        //sample
       // getSampleArrayList();
        //  agregarEventos();
        getJSON();

        return rootView;
    }

    @Override
    public void onRefresh() {

        try {
          //  eJson.getEventosBD();
           System.out.println("On refresh");

            getJSON();
            //layoutAdapter();

            swipeContainer.setRefreshing(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //agregar lista db
    //if refresh true solo agrega los que no estan en bd


   public void layoutAdapter()
    {
        eventoAdapter = new EventoAdapter(listaEventos);

        //Especificar Adapter
        mRecyclerView.setAdapter(eventoAdapter);
        eventoAdapter.notifyDataSetChanged();
    }


    public void getJSON()
    {
        EventoJSON refreshJSON=new EventoJSON(getContext());
        refreshJSON.setRecyclerViewer(mRecyclerView);
        refreshJSON.execute();
        try {
            listaEventos=eventoDB.getTodosLosEventos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //layoutAdapter();
    }





}
