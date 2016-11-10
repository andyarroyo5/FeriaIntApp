package edu.udem.feriaint.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.R;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolder_Tema;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class TemaAdapter extends RecyclerView.Adapter<ViewHolder_Tema> {

    private ArrayList<Tema> listaTemas;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TemaAdapter (ArrayList<Tema> myDataset) {
        listaTemas = myDataset;
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

        final Tema temas = listaTemas.get(position);

        holder.getTema().setText((temas.getTema()));
    }


    @Override
    public int getItemCount() {
        return listaTemas.size();
    }
}

