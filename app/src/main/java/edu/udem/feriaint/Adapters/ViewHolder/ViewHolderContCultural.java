package edu.udem.feriaint.Adapters.ViewHolder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import edu.udem.feriaint.Activities.Detalle_ContCultural;
import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Activities.TriviaActivity;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 24/10/2016.
 */

public class ViewHolderContCultural extends RecyclerView.ViewHolder {


    private String TAG=getClass().getSimpleName();

    private CardView cv_cont_cult;


    private ContenidoCultural contenidoCultural;

    private TextView titulo;
    private ImageView imgPortada;
    private RelativeLayout contenidoPortada;
    private TextView tema;
    private ImageButton agregarFavoritos;
    private ImageButton compartir;

    public ViewHolderContCultural(View v) {
            super(v);

            contenidoPortada=(RelativeLayout)v.findViewById(R.id.contenido_portada);
            cv_cont_cult=(CardView) v.findViewById(R.id.cv_cont_cult);
            imgPortada = (ImageView) v.findViewById(R.id.contenido_img_portada);
            titulo = (TextView) v.findViewById(R.id.contenido_titulo);
            tema = (TextView) v.findViewById(R.id.txtTemaNombre);
            agregarFavoritos =(ImageButton) v.findViewById(R.id.contenido_agregar_favoritos);
            compartir=(ImageButton) v.findViewById(R.id.contenido_compartir);

    }

    public RelativeLayout getContenidoPortada() {
        return contenidoPortada;
    }

    public void setContenidoPortada(RelativeLayout contenidoPortada) {
        this.contenidoPortada = contenidoPortada;
    }

    public TextView getTitulo() {
        return titulo;
    }

    public void seTitulo(TextView mtitulo) {
        this.titulo = mtitulo;
    }

    public ImageView getImgPortada() {
        return imgPortada;
    }

    public void setImgPortada(ImageView imgPortada) {
        this.imgPortada = imgPortada;
    }

    public CardView getCv_cont_cult() {
        return cv_cont_cult;
    }

    public void setCv_cont_cult(CardView cv_cont_cult) {
        this.cv_cont_cult = cv_cont_cult;
    }

    public ContenidoCultural getContenidoCultural() {
        return contenidoCultural;
    }

    public void setContenidoCultural(ContenidoCultural contenidoCultural) {
        this.contenidoCultural = contenidoCultural;
    }


    public TextView getTema() {
        return tema;
    }

    public void setTema(TextView tema) {
        this.tema = tema;
    }


    public ImageView getAgregarFavoritos() {
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

    public void verDetalleContCult()
    {
        getCv_cont_cult().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();

                b.putString("titulo", contenidoCultural.getTitulo());
                b.putString("tema", contenidoCultural.getTema().getNombre());
                b.putStringArrayList("formato", contenidoCultural.getFormato());
                b.putStringArrayList("contenido", contenidoCultural.getContenido());

                Intent detalle;

                if(contenidoCultural.getTipo().equalsIgnoreCase("trivia"))
                {
                     detalle = new Intent(v.getContext(), TriviaActivity.class);
                }
                else {


                    for (int i = 0; i < contenidoCultural.getFormato().size(); i++) {
                        Log.d("ON CLICK DETALLE", contenidoCultural.getContenido().get(i).toString());
                    }

                    detalle = new Intent(v.getContext(), Detalle_ContCultural.class);
                    //intent.putExtra("imgPortada", contenidoCultural);
                    // detalle.putExtra("tema", contenidoCultural.getTema().toString());

                    //  b.putParcelable("contCult", contenidoCultural);



                }

                detalle.putExtras(b);
                v.getContext().startActivity(detalle);
            }
        });



    }


    public void compartir()
    {
        getCompartir().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String[] TO = {""};
                String[] CC = {""};
                Intent compartir = new Intent(Intent.ACTION_SEND);


                String mensaje= contenidoCultural.getTitulo();

                compartir.setData(Uri.parse("mailto:"));
                compartir.setType("text/plain");
                compartir.putExtra(Intent.EXTRA_EMAIL, TO);
                compartir.putExtra(Intent.EXTRA_CC, CC);
                compartir.putExtra(Intent.EXTRA_SUBJECT, contenidoCultural.getTema().getNombre());
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

    public void setPortada(View view)
    {
        if(contenidoCultural.getImgPortada()!=null)
        {

            Glide.with(view.getContext())
                    .load(contenidoCultural.getImgPortada())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getImgPortada());

            getImgPortada().setVisibility(View.VISIBLE);

        }
        else
        {
            getContenidoPortada().setBackgroundColor(contenidoCultural.getTema().getColor());
            getImgPortada().setVisibility(View.GONE);


            // holder.getBoxTema().setBackgroundColor(Color.WHITE);
          //  holder.getBoxTema().setBackgroundColor(tema.getColor());
           // holder.getBoxTema().setAlpha((float) 0.4);
            // holder.getBoxTema().setBackgroundColor(holder.itemView.getResources().getColor(R.color.accent));
        }

    }

    public void agregarFavoritos() {
        getAgregarFavoritos().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ImageButton fav= (ImageButton) v.findViewById(R.id.contenido_agregar_favoritos);


                if (contenidoCultural.isFavorito())
                {
                    //  evento.setFavorito(false);

                    fav.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.icons));
                    //fav.setColorFilter(R.color.icons,  PorterDuff.Mode.SRC_IN);
                    MainActivity.currentUsuario.getListaContCultFavoritos().remove(contenidoCultural);
                    contenidoCultural.setFavorito(false);

                }
                else {

                    fav.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.colorAccent));
                    // fav.setColorFilter(R.color.colorAccent,  PorterDuff.Mode.SRC_IN);
                    MainActivity.currentUsuario.getListaContCultFavoritos().add(contenidoCultural);
                    contenidoCultural.setFavorito(true);
                }


            }
        });

    }

}
