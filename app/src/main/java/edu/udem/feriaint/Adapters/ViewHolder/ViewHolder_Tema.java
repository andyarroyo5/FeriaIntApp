package edu.udem.feriaint.Adapters.ViewHolder;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Card;

import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class ViewHolder_Tema extends RecyclerView.ViewHolder {


    private CardView boxTema;
    private TextView tema;



    public ViewHolder_Tema(View v) {
        super(v);

        tema=(TextView) v.findViewById(R.id.txtTema);
        boxTema=(CardView) v.findViewById(R.id.boxTema);

    }


    public CardView getBoxTema() {
        return boxTema;
    }

    public void setBoxTema(CardView boxTema,Paint paint) {
        boxTema.setBackgroundColor(paint.getColor());
        this.boxTema = boxTema;
    }

    public TextView getTema() {
        return tema;
    }

    public void setTema(TextView tema) {
        this.tema = tema;
    }
}
