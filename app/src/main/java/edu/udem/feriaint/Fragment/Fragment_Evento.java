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
    private ArrayList<Evento> listaEventosFavoritos;
   // private Usuario currentUsuario;
    TextView txtError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=this.getClass().getSimpleName();
        //tomar todos los favoritos ?
        listaEventosFavoritos=new ArrayList<Evento>();
        eJson= new EventoJSON(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_evento, container, false);

      CalendarView cal=(CalendarView) rootView.findViewById(R.id.calendarView);
      txtError =(TextView) rootView.findViewById(R.id.txtError);

      swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
      swipeContainer.setOnRefreshListener(this);
      mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

       if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

       mLayoutManager = new LinearLayoutManager(getActivity());
       mRecyclerView.setLayoutManager(mLayoutManager);




        eJson.execute();
        listaEventos=eJson.getListaEventos();
        //sample
       // getSampleArrayList();
        layoutAdapter();

        //CON BD

       /* try {
            if (!getEventosTodos())
            {
                Log.d(TAG, "falseTodos");


               /* if(eJson.getListaEventos().size()==0)
               {
                   mRecyclerView.setVisibility(View.GONE);
                   txtError.setVisibility(View.VISIBLE);
               }
                else
               {
                   mRecyclerView.setVisibility(View.VISIBLE);
                   txtError.setVisibility(View.GONE);
               }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        listaEventosFavoritos=getListaFavoritos();
        return rootView;
    }

    @Override
    public void onRefresh() {

        try {
          //  eJson.getEventosBD();
           System.out.println("On refresh");
           EventoJSON refreshJSON=new EventoJSON(getContext());
           //this.listaEventos=refreshJSON.getListaEventos();
            refreshJSON.execute();
            listaEventos=refreshJSON.getListaEventos();
           // getSampleArrayList();
            layoutAdapter();
           /* if(listaEventos.size()==0)
            {
                mRecyclerView.setVisibility(View.GONE);
                txtError.setVisibility(View.VISIBLE);

            }
            else
            {
                mRecyclerView.setVisibility(View.VISIBLE);
                txtError.setVisibility(View.GONE);
            }*/


           swipeContainer.setRefreshing(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getEventosTodos() throws ParseException {

        EventoDB eventoDB= new EventoDB(getContext());
        eventoDB.open();
        listaEventos=eventoDB.getTodosLosEventos();
        eventoDB.close();

        Log.d(TAG, "entrada TODOS"+ listaEventos.size());
        if (listaEventos!=null && listaEventos.size()!=0)
        {
            layoutAdapter();
            return true;
        }
        else
        {
            return false;
        }

    }

    public void layoutAdapter()
    {
        eventoAdapter = new EventoAdapter(listaEventos, listaEventosFavoritos);
        //Especificar Adapter

        mRecyclerView.setAdapter(eventoAdapter);
        eventoAdapter.notifyDataSetChanged();
    }

    private void getSampleArrayList() {


        Date p=new Date();
        Calendar c= new GregorianCalendar();
        c.getTime();

        listaEventos.add(new Evento("Ceremonia inaugural y conferencia",  c.getTime(),  c.getTime(), "Teatro UDEM", "descripcion"));

        listaEventos.add(new Evento("Evento2",  c.getTime(),  c.getTime(), "Teatro UDEM", "descripcion"));
        listaEventos.add(new Evento("Evento3",  c.getTime(),  c.getTime(), "Teatro UDEM", "descripcion"));

    }


    public ArrayList<Evento> getListaFavoritos()
    {
        listaEventosFavoritos=eventoAdapter.getListaEventosFavoritos();


        for  (int i =0; i<listaEventosFavoritos.size(); i++)
        {
            Log.d(TAG, listaEventosFavoritos.get(i).toString());
        }

        Log.d(TAG, "GET ListaEventosFavoritos");


        Intent mandarListaFav=new Intent();
        mandarListaFav.putExtra("lista", (ArrayList<? extends Parcelable>) listaEventosFavoritos);
        mandarListaFav.putExtra("tipo", "evento");


        Bundle lista=new Bundle();
        lista.putString("tipo","ejemplo");
        lista.putParcelableArrayList("lista", (ArrayList<? extends Parcelable>) listaEventosFavoritos);
        Fragment_Perfil perfil=new Fragment_Perfil();
        perfil.setArguments(lista);




        return listaEventosFavoritos;
    }




}
