package edu.udem.feriaint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Detalle_ContCultural extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__cont_cultural);


        Bundle b=getIntent().getExtras();

        TextView tituloContCult=(TextView) findViewById(R.id.txtTituloContCult);
        ImageView img=(ImageView) findViewById(R.id.imgContCultural);

        tituloContCult.setText(b.getString("tituloContCult"));
        img.setImageResource(b.getInt("img"));

    }
}
