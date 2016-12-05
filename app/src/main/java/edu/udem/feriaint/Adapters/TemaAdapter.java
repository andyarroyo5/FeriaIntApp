package edu.udem.feriaint.Adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.R;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolder_Tema;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class TemaAdapter extends RecyclerView.Adapter<ViewHolder_Tema> {

    private String TAG=getClass().getSimpleName();
    private ArrayList<Tema> listaTemas;
    private String tipo;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TemaAdapter (ArrayList<Tema> myDataset, String tipo)
    {
        listaTemas = myDataset;
        this.tipo=tipo;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder_Tema onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tema, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder_Tema vh = new ViewHolder_Tema(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Tema holder, int position) {

        final Tema tema = listaTemas.get(position);

        holder.setTema(tema);
        if(tema.isSeleccionado())
        {
            holder.getBoxTema().setBackgroundColor(tema.getColor());
        }
        else
        {
           // holder.getBoxTema().setBackgroundColor(Color.WHITE);
            holder.getBoxTema().setBackgroundColor(tema.getColor());
            holder.getBoxTema().setAlpha((float) 0.4);
           // holder.getBoxTema().setBackgroundColor(holder.itemView.getResources().getColor(R.color.accent));

        }
        holder.getNombre().setText((tema.getNombre()));
        holder.seleccionarTema(position,tipo);



    }


    @Override
    public int getItemCount() {
        return listaTemas.size();
    }



}

