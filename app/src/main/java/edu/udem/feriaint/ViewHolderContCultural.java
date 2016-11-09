package edu.udem.feriaint;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import edu.udem.feriaint.Activities.Detalle_EventoActivity;
import edu.udem.feriaint.Modelos.ContenidoCultural;

/**
 * Created by Andrea Arroyo on 24/10/2016.
 */

public class ViewHolderContCultural extends RecyclerView.ViewHolder {


    private TextView mtitulo;
    private ImageView imgContenido;
    private CardView cv_cont_cult;
    private ContenidoCultural contenidoCultural;

    public ViewHolderContCultural(View v) {
            super(v);


            mtitulo = (TextView) v.findViewById(R.id.contenido_titulo);
            imgContenido= (ImageView) v.findViewById(R.id.contenido_imagen);
            cv_cont_cult=(CardView) v.findViewById(R.id.cv_cont_cult);
    }

    public TextView getTitulo() {
        return mtitulo;
    }

    public void seTitulo(TextView mtitulo) {
        this.mtitulo = mtitulo;
    }

    public ImageView getImgContenido() {
        return imgContenido;
    }

    public void setImgContenido(ImageView imgContenido) {
        this.imgContenido = imgContenido;
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

    public void verDetalleContCult()
    {
        getCv_cont_cult().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast t= Toast.makeText(v.getContext(),
                        "EVENTO DETALLE", Toast.LENGTH_SHORT);

                t.show();

                Intent intent=new Intent(v.getContext(), Detalle_ContCultural.class);
                intent.putExtra("tituloContCult", contenidoCultural.getTitulo().toString());
                intent.putExtra("img", contenidoCultural.getImg());


                v.getContext().startActivity(intent);
            }
        });

    }

}
