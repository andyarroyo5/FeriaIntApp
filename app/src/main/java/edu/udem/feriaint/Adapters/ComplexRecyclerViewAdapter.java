package edu.udem.feriaint.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolder1;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolder2;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolderContCultural;
import edu.udem.feriaint.Adapters.ViewHolder.ViewHolderEvento;

/**
 * Created by Andrea Arroyo on 23/10/2016.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;
    Evento evento;

    private final int EVENTO = 0, IMAGE = 1, CONTCULT=3;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapter(ArrayList<Object> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Evento) {
            return EVENTO;
        } else if (items.get(position) instanceof String) {
            return IMAGE;
        }else if(items.get(position) instanceof ContenidoCultural) {
            return CONTCULT;
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case EVENTO:
                View v1 = inflater.inflate(R.layout.item_eventos, viewGroup, false);
                viewHolder = new ViewHolderEvento(v1);
                break;
            case IMAGE:
                View v2 = inflater.inflate(R.layout.layout_viewholder2, viewGroup, false);
                viewHolder = new ViewHolder2(v2);
                break;
            case CONTCULT:
                View v3 = inflater.inflate(R.layout.item_contenido_cultural, viewGroup, false);
                viewHolder = new ViewHolderContCultural(v3);
                break;
            default:
                viewHolder=null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case EVENTO:
                ViewHolderEvento vh1 = (ViewHolderEvento) viewHolder;
                configureViewHolderEvento(vh1, position);
                break;
            case IMAGE:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh2);
                break;
            case CONTCULT:
                ViewHolderContCultural vh3 = (ViewHolderContCultural) viewHolder;
                configureViewHolderContCultural(vh3, position);
                break;
            default:
                ViewHolder1 v = (ViewHolder1) viewHolder;
                configureViewHolder1(v);
                break;
        }
    }


    private void configureViewHolderEvento( ViewHolderEvento vh, int position) {
        evento = (Evento) items.get(position);

//        Log.e("COMPLEX", evento.getTitulo());

        vh.setEvento(evento);
        vh.getTitulo().setText(evento.getTitulo());
        vh.getLugar().setText(evento.getLugar());


        vh.getAgregarFavoritos().setVisibility(View.GONE);
        vh.getCompartir().setVisibility(View.GONE);
        vh.getTwitter().setVisibility(View.GONE);
        vh.getAgregarEvento().setVisibility(View.GONE);
        vh.agregarCalendario();
        vh.verDetalleEvento();
        vh.compartir();
        vh.hacerTweet();


    }

    private void configureViewHolder1(ViewHolder1 vh1) {


            vh1.getLabel1().setText("Name: Test1" );
            vh1.getLabel2().setText("Hometown: Test2 ");

    }

    private void configureViewHolderContCultural(ViewHolderContCultural vh3, int position) {

        ContenidoCultural contcult = (ContenidoCultural) items.get(position);
       // Log.e("COMPLEX",contcult.getTitulo());
        vh3.setContenidoCultural(contcult);
        vh3.getTitulo().setText(contcult.getTitulo());
        //vh3.getImgPortada().setImageResource(contcult.getImg());
        vh3.verDetalleContCult();
        vh3.getAgregarFavoritos().setVisibility(View.GONE);
        vh3.getCompartir().setVisibility(View.GONE);

    }


    private void configureViewHolder2(ViewHolder2 vh2) {
        vh2.getImageView().setImageResource(R.drawable.sample_golden_gate);
    }


}
