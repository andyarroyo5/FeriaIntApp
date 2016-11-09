package edu.udem.feriaint;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.udem.feriaint.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

       // mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayList<Object> feed= this.getSampleArrayList();

        compAdapter=new ComplexRecyclerViewAdapter(feed);
        mRecyclerView.setAdapter(compAdapter);
        compAdapter.notifyDataSetChanged();


        return rootView;
    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();

        Date p=new Date();
        Calendar c= new GregorianCalendar();
        c.getTime();

        items.add(new Evento("Ceremonia inaugural y conferencia",  c.getTime(),  c.getTime(), "Teatro UDEM", "descripcion"));
        items.add("image");
        items.add(new ContenidoCultural("Corea y su gastronomía",R.drawable.evento));
        items.add(new Evento("Evento2",  c.getTime(),  c.getTime(), "Teatro UDEM", "descripcion"));
        items.add(new ContenidoCultural("Fun Facts",R.drawable.evento2));
        items.add("image");
        items.add(new Evento("Evento3",  c.getTime(),  c.getTime(), "Teatro UDEM", "descripcion"));
        items.add(new ContenidoCultural("Historia de Corea",R.drawable.corea_logo));

        return items;
    }

}