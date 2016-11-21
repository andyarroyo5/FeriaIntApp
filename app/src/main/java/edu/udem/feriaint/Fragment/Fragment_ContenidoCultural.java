package edu.udem.feriaint.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.udem.feriaint.Adapters.ContenidoCulturalAdapter;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Parser.ContCulturalJSON;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 11/10/2016.
 */

public class Fragment_ContenidoCultural extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

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

        swipeContainerCultura=(SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainerCultura);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cultura);

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        listaContenidoCultural=new ArrayList<>();
        mLayoutManager =  new GridLayoutManager(getActivity(),2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //swipeContainerCultura.setRefreshing(true);
        getJSON();


        return rootView;
    }

    private void getJSON() {

        ContCulturalJSON contCultJson=new ContCulturalJSON(getContext());
        contCultJson.setRecyclerViewer(mRecyclerView);
        contCultJson.setSwipeContainer(swipeContainerCultura);
        contCultJson.execute();

        if (contCultJson.getStatus() == AsyncTask.Status.FINISHED) {
            swipeContainerCultura.setRefreshing(false);
        }

        //Log.d(TAG, String.valueOf(contCultJson.execute().getStatus()));

    }

    @Override
    public void onRefresh() {


        getJSON();

    }
}
