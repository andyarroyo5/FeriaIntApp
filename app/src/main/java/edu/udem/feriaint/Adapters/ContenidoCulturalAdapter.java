package edu.udem.feriaint.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.R;
import edu.udem.feriaint.ViewHolderContCultural;

/**
 * Created by Andrea Arroyo on 07/10/2016.
 */

public class ContenidoCulturalAdapter extends RecyclerView.Adapter<ViewHolderContCultural> {

    private ArrayList<ContenidoCultural> listaContenido;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContenidoCulturalAdapter(ArrayList<ContenidoCultural> myDataset) {
        listaContenido = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolderContCultural onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contenido_cultural, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolderContCultural vh = new ViewHolderContCultural(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolderContCultural holder, int position) {
        final ContenidoCultural cont_cult = listaContenido.get(position);
        holder.setContenidoCultural(cont_cult);
        holder.getTitulo().setText(cont_cult.getTitulo());
        holder.getImgContenido().setImageResource(cont_cult.getImg());
        holder.verDetalleContCult();

    }

    @Override
    public int getItemCount() {
        return listaContenido.size();
    }
}
