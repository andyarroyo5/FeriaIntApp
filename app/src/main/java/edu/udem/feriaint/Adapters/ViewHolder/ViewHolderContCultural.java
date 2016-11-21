package edu.udem.feriaint.Adapters.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.udem.feriaint.Activities.Detalle_ContCultural;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 24/10/2016.
 */

public class ViewHolderContCultural extends RecyclerView.ViewHolder {

    private CardView cv_cont_cult;


    private ContenidoCultural contenidoCultural;

    private TextView titulo;
    private ImageView imgPortada;
    private TextView tema;

    public ViewHolderContCultural(View v) {
            super(v);

            cv_cont_cult=(CardView) v.findViewById(R.id.cv_cont_cult);
            imgPortada = (ImageView) v.findViewById(R.id.contenido_img_portada);
            titulo = (TextView) v.findViewById(R.id.contenido_titulo);
            tema = (TextView) v.findViewById(R.id.txtTemaNombre);


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

    public void verDetalleContCult()
    {
        getCv_cont_cult().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



                for (int i=0; i<contenidoCultural.getFormato().size();i++)
                {
                    Log.d("ON CLICK DETALLE",contenidoCultural.getFormato().get(i).toString());
                }

                Intent detalle=new Intent(v.getContext(), Detalle_ContCultural.class);

                //intent.putExtra("imgPortada", contenidoCultural);
               // detalle.putExtra("tema", contenidoCultural.getTema().toString());
                Bundle b = new Bundle();
              //  b.putParcelable("contCult", contenidoCultural);
                b.putString("titulo", contenidoCultural.getTitulo() );
                b.putString("tema", contenidoCultural.getTema().getNombre() );
                b.putStringArrayList("formato", contenidoCultural.getFormato());
                b.putStringArrayList("contenido", contenidoCultural.getContenido());
                detalle.putExtras(b);
               // detalle.putExtra("contenidoCultural", contenidoCultural);
                v.getContext().startActivity(detalle);
            }
        });

    }

}
