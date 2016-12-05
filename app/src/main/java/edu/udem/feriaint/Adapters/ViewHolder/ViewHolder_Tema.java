package edu.udem.feriaint.Adapters.ViewHolder;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Card;

import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class ViewHolder_Tema extends RecyclerView.ViewHolder {


    private String TAG=getClass().getSimpleName();
    private CardView boxTema;
    private TextView nombre;
    private Tema tema;



    public ViewHolder_Tema(View v) {
        super(v);

        nombre=(TextView) v.findViewById(R.id.txtTema);
        boxTema=(CardView) v.findViewById(R.id.boxTema);

    }


    public CardView getBoxTema() {
        return boxTema;
    }

    public void setBoxTema(CardView boxTema,Paint paint) {
        boxTema.setBackgroundColor(paint.getColor());
        this.boxTema = boxTema;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public void seleccionarTema(final int position, final String tipo)
    {
        getBoxTema().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CardView boxSelec=(CardView) view.findViewById(R.id.boxTema);
                if (tema.isSeleccionado())
                {
                    tema.setSeleccionado(false);
                    //boxSelec.setBackgroundColor(Color.WHITE);
                     boxSelec.setBackgroundColor(tema.getColor());
                     boxSelec.setAlpha((float) 0.4);
                    //boxSelec.setCardBackgroundColor(tema.getColor());


                }
                else
                {
                    tema.setSeleccionado(true);
                    boxSelec.setAlpha((float) 1);
                    boxSelec.setBackgroundColor(tema.getColor());
                    //boxSelec.setCardBackgroundColor(Color.blue(tema.getColor()));

                }

               /* if (tipo.equals("evento"))
                {
                    try {
                        MainActivity.repositorioJSON.getTemasEventos(false).get(position).setSeleccionado(tema.isSeleccionado());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                   // MainActivity.listaTemasContCult.get(position).setSeleccionado(tema.isSeleccionado());

                }*/

                Log.e(TAG,"Tema"+ tema.getNombre()+" "+tema.isSeleccionado());


            }
        });
    }
}
