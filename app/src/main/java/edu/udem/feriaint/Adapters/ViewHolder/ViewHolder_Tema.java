package edu.udem.feriaint.Adapters.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.udem.feriaint.R;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class ViewHolder_Tema extends RecyclerView.ViewHolder {

    private TextView tema;


    public ViewHolder_Tema(View v) {
        super(v);

        tema=(TextView) v.findViewById(R.id.txtTema);

    }

    public TextView getTema() {
        return tema;
    }

    public void setTema(TextView tema) {
        this.tema = tema;
    }
}
