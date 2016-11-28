package edu.udem.feriaint.Activities;

        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;

        import java.util.ArrayList;
        import java.util.StringTokenizer;
        import java.util.logging.Handler;

        import edu.udem.feriaint.Modelos.ContenidoCultural;
        import edu.udem.feriaint.Modelos.Pregunta;
        import edu.udem.feriaint.R;

public class TriviaActivity extends AppCompatActivity {

    //Question currentQ;

    private String TAG;

    ArrayList<Pregunta> trivia;
    int idPregunta;
    Pregunta preguntaActual;

    //Vista de la pregunta actual
    ImageView imgPregunta;
    TextView txtPregunta;
    TextView puntuacion;
    int puntosTotal;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;


    private ContenidoCultural contCult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia4);

        TAG=getClass().getSimpleName();

        //inicializar variables
        puntosTotal=0;
        idPregunta=0;
        trivia=new ArrayList<Pregunta>();

        //Obtener Preguntas de JSON
        Bundle b = getIntent().getExtras();
        contCult = new ContenidoCultural(b.getString("titulo"), b.getString("tema"));

        contCult.setFormato(b.getStringArrayList("formato"));
        contCult.setContenido(b.getStringArrayList("contenido"));
        Log.d(TAG, contCult.toString());

        imgPregunta=(ImageView) findViewById(R.id.imgPregunta);
        txtPregunta=(TextView) findViewById(R.id.txtPreguntaActual);
        puntuacion=(TextView) findViewById(R.id.puntuacion);
        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn3=(Button) findViewById(R.id.btn3);
        btn4=(Button) findViewById(R.id.btn4);



        //Al darle click a los botones se pasa su id para checar si es correcta la respuesta

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                seleccionarRespuesta( btn1.getId());
                //runThread();


            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                seleccionarRespuesta(btn2.getId());

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarRespuesta(btn3.getId());
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarRespuesta(btn4.getId());
            }
        });

        //Obtener la lista de preguntas de la trivia
        getPreguntasTrivia();
        mostrarPregunta();
    }

    public void getPreguntasTrivia()
    {
        String img="";

        for (int i = 0; i < contCult.getFormato().size(); i++) {

            String f=contCult.getFormato().get(i);
            String contenido=contCult.getContenido().get(i);

            if(f.equalsIgnoreCase("trivia2")|| f.equalsIgnoreCase("trivia4"))

            {

                StringTokenizer tokens = new StringTokenizer(contenido, "//");

                String pregunta = tokens.nextToken();
                ArrayList<String> opcionRespuesta = new ArrayList<>();
                Log.d(TAG, "total " + tokens.countTokens());


                while (tokens.hasMoreTokens()) {
                    if (tokens.countTokens() != 1) {
                        opcionRespuesta.add(tokens.nextToken());
                        Log.d(TAG, tokens.countTokens() + " Respuesta Opción " + opcionRespuesta.get(opcionRespuesta.size() - 1));
                    } else {
                        break;
                    }


                }

                int respuesta = Integer.parseInt(tokens.nextToken());
                Log.d(TAG, tokens.countTokens() + " RESPESTA " + respuesta);

                Pregunta nuevaPregunta = new Pregunta(pregunta, opcionRespuesta, respuesta);

                if(!img.equals(""))
                {
                    nuevaPregunta.setImgPregunta(img);
                    img="";
                }
                trivia.add(nuevaPregunta);
            }
            else
            if(f.equalsIgnoreCase("imagen"))
            {

                img=contenido;
            }



        }
    }

    private void mostrarPregunta() {

        // the method which will put all things together
        inicializarColorBoton();

        preguntaActual=trivia.get(idPregunta);
        txtPregunta.setText(preguntaActual.getPregunta());


        if (preguntaActual.getImgPregunta()!=null)
        {

            Log.e(TAG,preguntaActual.getImgPregunta() );
            Glide.with(getApplicationContext())
                    .load(preguntaActual.getImgPregunta())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPregunta);
            imgPregunta.setVisibility(View.VISIBLE);
        }
        else
        {
            imgPregunta.setVisibility(View.GONE);
        }

        switch(preguntaActual.getOpciones().size())
        {
            case 2:
                btn1.setText(preguntaActual.getOpciones().get(0));
                btn2.setText(preguntaActual.getOpciones().get(1));
                btn3.setVisibility(View.GONE);
                btn4.setVisibility(View.GONE);
                break;
            case 4:
                btn1.setText(preguntaActual.getOpciones().get(0));
                btn2.setText(preguntaActual.getOpciones().get(1));
                btn3.setText(preguntaActual.getOpciones().get(2));
                btn4.setText(preguntaActual.getOpciones().get(3));
                btn3.setVisibility(View.VISIBLE);
                btn4.setVisibility(View.VISIBLE);
                break;
        }
        idPregunta++;
    }


    private void seleccionarRespuesta(final int id)  {

        new Thread() {
            public void run() {

                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Button btnSeleccionado = (Button) findViewById(id);

                            int colorNormal = btnSeleccionado.getDrawingCacheBackgroundColor();
                            // btnSeleccionado.setBackgroundColor(getResources().getColor(R.color.tw__light_gray));
                            btnSeleccionado.hasFocus();

                            int indexRespuesta = preguntaActual.getRespuesta() - 1;


                            if (btnSeleccionado.getText().equals(preguntaActual.getOpciones().get(indexRespuesta))) {
                                btnSeleccionado.setBackgroundColor(getResources().getColor(R.color.colorCorrecto));
                                // Toast toast = Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT);
                                //toast.show();

                                puntosTotal += 10;
                                puntuacion.setText("Puntos: " + puntosTotal);
                                Log.d(TAG, "CORRECTO");
                            } else {
                                btnSeleccionado.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                Toast toast = Toast.makeText(getApplicationContext(), "La correcta era " + preguntaActual.getOpciones().get(indexRespuesta), Toast.LENGTH_SHORT);
                                toast.show();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Log.d(TAG, "INCORRECTO");
                            }

                        }
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        siguientePregunta();
                    }
                });


            }
        }.start();


    }

    public void siguientePregunta()
    {
        if (idPregunta < trivia.size()) {

            // todavia hay preguntas
            preguntaActual = trivia.get(idPregunta);
            mostrarPregunta();

        } else {

            //Termino la trivia
            // if over do this

            if (puntosTotal != 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Ganaste " + puntosTotal + " Puntos", Toast.LENGTH_LONG);
                toast.show();
            }


            MainActivity.currentUsuario.agregarPuntos(puntosTotal);
            //TODO: Llevar cuenta de trivias hechas para no agregar puntos

            onBackPressed();

            /*Intent intent = new Intent(this,
                    onBackPressed());
            Bundle b = new Bundle();
            b.putInt("puntos", puntosTotal); //puntos obtenidos
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);*/
            finish();
        }
    }
    public void inicializarColorBoton()
    {
        btn1.setBackgroundColor(getResources().getColor(android.R.color.white));
        btn2.setBackgroundColor(getResources().getColor(android.R.color.white));
        btn3.setBackgroundColor(getResources().getColor(android.R.color.white));
        btn4.setBackgroundColor(getResources().getColor(android.R.color.white));
    }



}
