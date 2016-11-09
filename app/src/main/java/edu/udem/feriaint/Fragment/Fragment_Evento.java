package edu.udem.feriaint.Fragment;

import android.os.Bundle;
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
import android.widget.Toast;;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Adapters.EventoAdapter;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Parser.EventoJSON;

import edu.udem.feriaint.R;
import edu.udem.feriaint.RecyclerItemClickListener;

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
    TextView txtError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=this.getClass().getSimpleName();
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

        eJson= new EventoJSON(getContext(),mRecyclerView);

        try {
            if (!eJson.getEventosTodos())
            {
                Log.d(TAG, "falseTodos");
                eJson.execute();
                eventoAdapter=eJson.getAdapter();
                if(eventoAdapter!=null)
                {
                    mRecyclerView.setAdapter(eventoAdapter);
                    eventoAdapter.notifyDataSetChanged();
                }


               /* if(eJson.getListaEventos().size()==0)
               {
                   mRecyclerView.setVisibility(View.GONE);
                   txtError.setVisibility(View.VISIBLE);
               }
                else
               {
                   mRecyclerView.setVisibility(View.VISIBLE);
                   txtError.setVisibility(View.GONE);
               }*/

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    @Override
    public void onRefresh() {

        try {
          //  eJson.getEventosBD();
           System.out.println("On refresh");
           EventoJSON refreshJSON=new EventoJSON(getContext(), mRecyclerView);
           //this.listaEventos=refreshJSON.getListaEventos();
            refreshJSON.execute();
            listaEventos=refreshJSON.getListaEventos();
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


    public void getEventosJSON()
    {
       // eJson=new EventoJSON(getContext(),mRecyclerView, swipeContainer);
        //eJson.execute();
    }



}
