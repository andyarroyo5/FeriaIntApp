package edu.udem.feriaint.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.EventoAdapter;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.Parser.EventoJSON;

import edu.udem.feriaint.Parser.GetJSON;
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
    CalendarView cal;
    MaterialCalendarView calendario;


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

        cal=(CalendarView) rootView.findViewById(R.id.calendarView);

        cal.setMinDate(MainActivity.edicion.getFechaInicio().getTime());
        cal.setMaxDate(MainActivity.edicion.getFechaFinal().getTime());


        cal.setDate(cal.getDate());


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Toast.makeText(getContext(), ""+dayOfMonth, Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub

            }
        });

        calendario=(MaterialCalendarView) rootView.findViewById(R.id.calendario);
        Calendar c=Calendar.getInstance();
        Log.e(TAG, "today"+ c.getTime());

        CalendarDay today = CalendarDay.from(2016,9,29);
        calendario.setCurrentDate(today);
        calendario.setSelectedDate(today);


      /*  calendario.state().edit()
                .setMinimumDate(MainActivity.edicion.getFechaInicio())
                .setMaximumDate(MainActivity.edicion.getFechaFinal())
                .commit();
*/


        calendario.setTitleAnimationOrientation(MaterialCalendarView.HORIZONTAL);

        //calendario.setOnMonthChangedListener(this);

     //   txtError =(TextView) rootView.findViewById(R.id.txtError);
      swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        setColorRefresh();

        // Setup refresh listener which triggers new data loading
      swipeContainer.setOnRefreshListener(this);

        //Adaptador
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

       if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

       mLayoutManager = new LinearLayoutManager(getActivity());
       mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            listaEventos = MainActivity.repositorioJSON.getListaEventosJSON(false);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        try {
            layoutAdapter(listaEventos);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onRefresh() {

        try {
          //  eJson.getEventosBD();
           System.out.println("On refresh");
           listaEventos = MainActivity.repositorioJSON.getListaEventosJSON(true);
         //  MainActivity.repositorioJSON.getTemasEventos(true);
          // setColorTemas();

            layoutAdapter(listaEventos);

            swipeContainer.setRefreshing(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void layoutAdapter(ArrayList<Evento> listaEventos) throws ExecutionException, InterruptedException {
        if(listaEventos.isEmpty())
        {
            listaEventos = MainActivity.repositorioJSON.getListaEventosJSON(true);
            //MainActivity.repositorioJSON.getTemasEventos(true);
            //setColorTemas();
        }

        eventoAdapter = new EventoAdapter(listaEventos);
        //Especificar Adapter
        mRecyclerView.setAdapter(eventoAdapter);
        eventoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==111)
        Log.e(TAG, "Regreso intent filtrar en FRAGMENT EVENTO");//+REQUEST_CODE_FILTRAR+ "data "+data.toString());
    }

    public void filtrarFecha() throws ExecutionException, InterruptedException {
        ArrayList<Evento> listaFiltrarFecha=new ArrayList<>();

        for (Evento f:listaFiltrarFecha) {

            if (f.getFechaInicio().getMonth()==calendario.getSelectedDate().getMonth())
            {
             listaFiltrarFecha.add(f);
            }
        }
        layoutAdapter(listaFiltrarFecha);
    }

    public void filtrarTemasEventos() throws ExecutionException, InterruptedException {
        ArrayList<Tema> listaTemasEventos=new ArrayList<>();

        for (Tema t:MainActivity.repositorioJSON.getTemasEventos(false)) {

            if (t.isSeleccionado())
            {
                listaTemasEventos.add(t);
            }
        }

        ArrayList<Evento> listaEventosporTemas=new ArrayList<>();

        for (Evento e:listaEventos) {


            for (Tema t:listaTemasEventos) {

                if (t.isSeleccionado())
                {
                    if(e.getTema().getId()==t.getId())
                        listaEventosporTemas.add(e);
                }
            }

        }

        for (int i = 0; i < listaEventosporTemas.size(); i++) {

            Log.e(TAG,"Filtrar"+listaEventosporTemas.get(i).getTema().getId());

        }

       layoutAdapter(listaEventosporTemas);

    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            filtrarTemasEventos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Color del refresh
    private void setColorRefresh() {
        swipeContainer.setColorSchemeResources(R.color.accent,
               android.R.color.holo_green_light ,
               android.R.color.holo_orange_light ,
               android.R.color.holo_red_light);
    }

    public void setColorTemas() throws ExecutionException, InterruptedException {
        for (Evento e:listaEventos) {

            for (Tema t:MainActivity.repositorioJSON.getTemasEventos(false)) {

                if(e.getTema().getId()==t.getId())
                {
                    e.setTema(t);
                }

            }

        }
    }

   /* @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Toast.makeText(getContext(), "Date "+date.getMonth(), Toast.LENGTH_SHORT).show();
    }*/
}
