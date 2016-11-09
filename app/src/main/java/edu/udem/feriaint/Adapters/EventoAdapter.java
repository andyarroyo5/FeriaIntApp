package edu.udem.feriaint.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.udem.feriaint.Activities.Detalle_EventoActivity;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;
import edu.udem.feriaint.ViewHolderEvento;

/**
 * Created by Andrea Arroyo on 07/10/2016.
 */

public class EventoAdapter extends RecyclerView.Adapter<ViewHolderEvento> {

    private ArrayList<Evento> listaEvento;


    //Provide a suitable constructor
    public EventoAdapter(ArrayList<Evento> listaEvento){

        this.listaEvento = listaEvento;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public ViewHolderEvento onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eventos,parent,false);

        //set the view's size, margins, paddings and layout parameters

        //ViewHolder vh = new ViewHolder(v);
        ViewHolderEvento vh = new ViewHolderEvento(v);

        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(final ViewHolderEvento holder, final int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element
        final Evento evento = listaEvento.get(position);
        holder.setEvento(evento);
        holder.getTitulo().setText(String.valueOf(evento.getTitulo()));
        holder.getLugar().setText(evento.getLugar());
        holder.agregarFavoritos();
        holder.agregarCalendario();
        holder.verDetalleEvento();
        holder.compartir();
        holder.hacerTweet();

        try {
            //String fechaView=evento.getViewFecha(evento.getFechaInicio()!=null ? evento.getFechaInicio()+ (evento.getFechaFinal()!=null ? "al "+ evento.getFechaFinal():"" ):evento.getFecha());
            String fechaVista="";
            if (evento.getFechaFinal()!=null)
            {
                //fechaVista=evento.formatoFecha(evento.getFechaInicio())+"\n"+evento.getHorarioInicio()+"\n"+evento.formatoFecha(evento.getFechaFinal())+"\n"+evento.getHorarioFinal();
                fechaVista=evento.getFechaFinal()!=null ? evento.formatoFecha(evento.getFechaInicio())+" - "+ evento.formatoFecha(evento.getFechaFinal()): evento.formatoFecha(evento.getFechaInicio());
            }
            else
            {
                if(evento.getFechaInicio()!=null)
                    fechaVista=evento.formatoFecha(evento.getFechaInicio())+"\n"+evento.getHorarioInicio();
            }
            holder.getFecha().setText(fechaVista);


            //  String horarioVista=evento.formatoHora(evento.getHorarioInicio())+"-"+ evento.formatoHora(evento.getHorarioFinal());
            // holder.horario.setText(horarioVista);

        } catch (ParseException e) {
            e.printStackTrace();
        }

       /* holder.getEvento().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast t= Toast.makeText(v.getContext(),
                                "EVENTO DETALLE", Toast.LENGTH_SHORT);

                t.show();

                SimpleDateFormat dia=new SimpleDateFormat("dd");
                SimpleDateFormat mes=new SimpleDateFormat("MMMM");

                Intent intent=new Intent(v.getContext(), Detalle_EventoActivity.class);
                intent.putExtra("titulo", evento.getTitulo());
                intent.putExtra("fecha", evento.getFecha());
                intent.putExtra("fechaInicio",evento.getFechaInicio());
                intent.putExtra("dia",dia.format(evento.getFechaInicio()));
                intent.putExtra("mes",mes.format(evento.getFechaInicio()));
                intent.putExtra("lugar", evento.getLugar());
                intent.putExtra("descripcion", evento.getDescripcion());

                v.getContext().startActivity(intent);
            }
        });*/



    }

    @Override
    public int getItemCount() {
        return listaEvento.size();
    }


    /*
    Añade una lista completa de items
     */
    public void agregarTodos(ArrayList<Evento> listaEventos){
        listaEvento.addAll(listaEventos);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        listaEvento.clear();
        notifyDataSetChanged();
    }

}




