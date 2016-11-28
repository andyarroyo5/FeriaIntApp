package edu.udem.feriaint.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.R;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolderContCultural;

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

        if(cont_cult.getImgPortada()!=null )
        {
            Log.e("IMGPORTADA ADAPTER",cont_cult.getTitulo()+" "+cont_cult.getImgPortada());
            Glide.with(holder.itemView.getContext())
                    .load(cont_cult.getImgPortada())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.getImgPortada());

            holder.getImgPortada().setVisibility(View.VISIBLE);
        }

        holder.setContenidoCultural(cont_cult);
        holder.getTitulo().setText(cont_cult.getTitulo());
        holder.getTema().setText(cont_cult.getTema().getNombre());
        holder.verDetalleContCult();





    }




    @Override
    public int getItemCount() {
        return listaContenido.size();
    }
}
