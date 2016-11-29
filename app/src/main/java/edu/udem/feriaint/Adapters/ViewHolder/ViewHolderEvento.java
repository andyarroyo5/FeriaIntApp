package edu.udem.feriaint.Adapters.ViewHolder;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import edu.udem.feriaint.Activities.Detalle_EventoActivity;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Fragment.Fragment_Perfil;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 23/10/2016.
 */

public class ViewHolderEvento extends RecyclerView.ViewHolder {

    private String TAG;
    //each data item is just a string in this case
    private TextView titulo;
    private TextView fecha;
    private TextView horario;
    private TextView lugar;

    private  ImageButton agregarEvento;
    private ImageButton agregarFavoritos;
    private ImageButton compartir;
    private CardView cv_evento;
    private ImageButton twitter;
    SimpleDateFormat fechaFormato;

    private Evento evento;

     private EventoDB eventoDB;
    private View v;



    public ViewHolderEvento(View v) {
        super(v);

        TAG=getClass().getSimpleName();



        fechaFormato= new SimpleDateFormat("EEEE dd hh:mm");

        titulo = (TextView) v.findViewById(R.id.evento_titulo);
        fecha = (TextView) v.findViewById(R.id.evento_fecha);
        horario = (TextView) v.findViewById(R.id.evento_horario);
        lugar = (TextView) v.findViewById(R.id.evento_lugar);

        //Botones
        cv_evento=(CardView) v.findViewById(R.id.cv_evento);
        agregarEvento = (ImageButton) v.findViewById(R.id.evento_agregar);
        agregarFavoritos = (ImageButton) v.findViewById(R.id.evento_agregar_favoritos);
        compartir = (ImageButton) v.findViewById(R.id.evento_compartir);
        twitter=(ImageButton)v.findViewById(R.id.btnTwitter);
        //Parte de las vistas



    }

    public TextView getTitulo() {
        return titulo;
    }

    public void setTitulo(TextView titulo) {
        this.titulo = titulo;
    }

    public TextView getFecha() {
        return fecha;
    }

    public void setFecha(TextView fecha) {
        this.fecha = fecha;
    }

    public TextView getHorario() {
        return horario;
    }

    public void setHorario(TextView horario) {
        this.horario = horario;
    }

    public TextView getLugar() {
        return lugar;
    }

    public void setLugar(TextView lugar) {
        this.lugar = lugar;
    }

    public ImageButton getAgregarEvento() {
        return agregarEvento;
    }

    public void setAgregarEvento(ImageButton agregarEvento) {
        this.agregarEvento = agregarEvento;
    }

    public ImageButton getAgregarFavoritos() {
        return agregarFavoritos;
    }

    public void setAgregarFavoritos(ImageButton agregarFavoritos) {
        this.agregarFavoritos = agregarFavoritos;
    }
    public ImageButton getCompartir() {
        return compartir;
    }

    public void setCompartir(ImageButton compartir) {
        this.compartir = compartir;
    }

    public CardView getCVEvento() {
        return cv_evento;
    }

    public void setCVEvento(CardView cv_evento) {
        this.cv_evento = cv_evento;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void verDetalleEvento()
    {
        getCVEvento().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SimpleDateFormat dia=new SimpleDateFormat("dd");
                SimpleDateFormat mes=new SimpleDateFormat("MMMM");

                Intent intent=new Intent(v.getContext(), Detalle_EventoActivity.class);
                intent.putExtra("titulo", evento.getTitulo().toString());
                //intent.putExtra("fecha", evento.getFecha());

                intent.putExtra("fechaInicio",evento.getFechaInicio());
                intent.putExtra("fechaFinal",evento.getFechaFinal());

                intent.putExtra("dia",dia.format(evento.getFechaInicio()));
                intent.putExtra("mes",mes.format(evento.getFechaInicio()));

                intent.putExtra("lugar", evento.getLugar());
                intent.putExtra("descripcion", evento.getDescripcion());

                v.getContext().startActivity(intent);
            }
        });

    }


    public CardView getCv_evento() {
        return cv_evento;
    }

    public void setCv_evento(CardView cv_evento) {
        this.cv_evento = cv_evento;
    }

    public ImageButton getTwitter() {
        return twitter;
    }

    public void setTwitter(ImageButton twitter) {
        this.twitter = twitter;
    }

    public void compartir()
    {
        getCompartir().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String[] TO = {""};
                String[] CC = {""};
                Intent compartir = new Intent(Intent.ACTION_SEND);


                String mensaje="¿Vamos?"+"\n"
                        +evento.getTitulo()
                        +(evento.getLugar()!=null ? "\nLugar: "+evento.getLugar():"")
                        + fechaFormato.format(evento.getFechaInicio());

                compartir.setData(Uri.parse("mailto:"));
                compartir.setType("text/plain");
                compartir.putExtra(Intent.EXTRA_EMAIL, TO);
                compartir.putExtra(Intent.EXTRA_CC, CC);
                compartir.putExtra(Intent.EXTRA_SUBJECT, evento.getTitulo());
                compartir.putExtra(Intent.EXTRA_TEXT, mensaje);

                try {
                    v.getContext().startActivity(Intent.createChooser(compartir, "Compartir..."));
                    //finish();
                    Log.i("Termino mandar...", "");
                }
                catch (android.content.ActivityNotFoundException ex) {
                    //  Toast.makeText(EnviarCorreo.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void agregarCalendario()
    {
        getAgregarEvento().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent mandarCorreo=new Intent(v.getContext(), EnviarCorreo.class);
                //v.getContext().startActivity(mandarCorreo);

                Intent calendario = new Intent(Intent.ACTION_INSERT);

                calendario.setType("vnd.android.cursor.item/event");
                calendario.putExtra(CalendarContract.Events.TITLE, evento.getTitulo());
                calendario.putExtra(CalendarContract.Events.EVENT_LOCATION, evento.getLugar() != null ? evento.getLugar() : "");
                calendario.putExtra(CalendarContract.Events.DTSTART, evento.getLugar() != null ? evento.getLugar() : "");
                calendario.putExtra(CalendarContract.Events.DESCRIPTION, evento.getDescripcion() != null ? evento.getDescripcion() : "");
                calendario.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, evento.getFechaInicio());


                calendario.setData(CalendarContract.Events.CONTENT_URI);
                v.getContext().startActivity(calendario);


            }
        });
    }

    public void hacerTweet()
    {

        getTwitter().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //SimpleDateFormat fecha= new SimpleDateFormat("EEEE dd hh:mm");
                TweetComposer.Builder builder = null;

                    builder = new TweetComposer.Builder(v.getContext())
                            .text(evento.getTitulo()+" en "+evento.getLugar()+ " el "+ fechaFormato.format(evento.getFechaInicio() )+ " #FeriaIntUDEM");

                //.image(myImageUri);
                builder.show();

            }
        });


    }

    public void agregarFavoritos() throws ParseException {
        getAgregarFavoritos().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ImageButton fav= (ImageButton) v.findViewById(R.id.evento_agregar_favoritos);
                eventoDB=new EventoDB(v.getContext());

                if (evento.isFavorito())
                {
                    //  evento.setFavorito(false);

                   fav.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.icons));
                    //fav.setColorFilter(R.color.icons,  PorterDuff.Mode.SRC_IN);
                        Log.e(TAG,"FavBD, eliminar : " + evento.toString());
                }
                else {

                    fav.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.colorAccent));
                   // fav.setColorFilter(R.color.colorAccent,  PorterDuff.Mode.SRC_IN);
                        Log.e(TAG,"FavBD, agregar : " + evento.toString());

                }

                eventoDB.setEventoFavorito(evento);
            }
        });

    }


    public void setFavorito()
    {
            ImageButton fav= getAgregarFavoritos();
            fav.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.colorAccent));
    }

}
