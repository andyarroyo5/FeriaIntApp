package edu.udem.feriaint.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.vision.text.Text;
import com.twitter.sdk.android.Twitter;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Activities.ActivityInicial;
import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Data.UsuarioDB;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Usuario;
import edu.udem.feriaint.R;
import edu.udem.feriaint.SessionRecorder;
import edu.udem.feriaint.TwitterInicioSesion;

/**
 * Created by Andrea Arroyo on 09/10/2016.
 */

public class Fragment_Perfil extends Fragment{

    private String TAG;

    ImageButton btnFavoritoPerfil;
    ImageButton btnEventosPerfil;
    ImageButton btnInfoPerfil;
    LinearLayout lytFav;
    LinearLayout lytCurrent;
    Button cerrarSesion;

    Usuario currentUsuario;

    String usuarioNombre;
    boolean twitter=false;
    Bundle bUsuario;

    public Fragment_Perfil() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=getClass().getSimpleName();

        currentUsuario= ((MainActivity)getActivity()).getCurrentUsuario();

        bUsuario =getActivity().getIntent().getExtras();
        System.out.println(bUsuario.getString("tipo"));
        Object array[]= bUsuario.keySet().toArray();


        for (int i=0;i<bUsuario.keySet().size();i++)
        {

            System.out.println(array[i].toString());
        }
       //Intent intent=getContext().getIntent();
      Bundle b = getArguments();
        if(b!=null) {
            Object array2[] = b.keySet().toArray();
            for (int i = 0; i < b.keySet().size(); i++) {

                System.out.println(array2[i].toString());
            }
            // if ((b.containsKey("tipo")))
            //   Log.d("BUNDLE", b.getString("tipo"));
        }
        getBundleInfo();

        //Log.e("PERFIL", String.valueOf(bTwitter)+bTwitter.getString("user"));

        try {
            getEventosTodos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void getBundleInfo() {


        bUsuario =getActivity().getIntent().getExtras();
       if ((bUsuario.containsKey("tipo")))
        switch(bUsuario.getString("tipo"))
        {
            case "evento":
                Log.d("BUNDLE",bUsuario.getString("tipo") );
                break;
            case "twitter":
                Log.d("BUNDLE",bUsuario.getString("tipo") );
                usuarioNombre=bUsuario.getString("user");
                //  agregarUsuario(bUsuario);

                break;

            case "ejemplo":
                Bundle b = getArguments();
                String s = b.getString("tipo");
                Log.d("BUNDLE", s);
                break;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        lytFav=(LinearLayout) rootView.findViewById(R.id.layout_Info);
        lytCurrent=(LinearLayout) rootView.findViewById(R.id.layout_Info2);
        btnFavoritoPerfil= (ImageButton) rootView.findViewById(R.id.btnFavoritoPerfil);
        btnEventosPerfil = (ImageButton) rootView.findViewById(R.id.btnEventos);
        btnInfoPerfil=(ImageButton) rootView.findViewById(R.id.btnInfoPerfil);
        cerrarSesion = (Button) rootView.findViewById(R.id.btnCerrarSesion);

        TextView usuarioTwitter=(TextView) rootView.findViewById(R.id.txtUsuarioTwitter);
        usuarioTwitter.setText(usuarioNombre);


        btnFavoritoPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnFavoritoPerfil.setColorFilter(getContext().getResources().getColor(R.color.accent));
                btnInfoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.primary_text));
                btnEventosPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.primary_text));
                lytFav.setVisibility(LinearLayout.VISIBLE);
                lytCurrent.setVisibility(LinearLayout.GONE);
            }
        });


        btnEventosPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnEventosPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.accent));
                btnFavoritoPerfil.setColorFilter(getContext().getResources().getColor(R.color.primary_text));
                btnInfoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.primary_text));

            }
        });

        btnInfoPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnInfoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.accent));
                btnEventosPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.primary_text));
                btnFavoritoPerfil.setColorFilter(getContext().getResources().getColor(R.color.primary_text));
                lytFav.setVisibility(LinearLayout.GONE);
                lytCurrent.setVisibility(LinearLayout.VISIBLE);


            }
        });



        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(twitter) {
                    Twitter.getSessionManager().clearActiveSession();

                    SessionRecorder.recordSessionInactive("About: accounts deactivated");
                    Answers.getInstance().logLogin(new LoginEvent().putMethod("Twitter").putSuccess(false));


                    Toast.makeText(getContext(), "Haz cerrado sesión",
                            Toast.LENGTH_SHORT).show();
                }*/


                Intent cerrarS=new Intent(getContext(), TwitterInicioSesion.class);
                cerrarS.putExtra("cerrarS",true);
                startActivity(cerrarS);
            }
        });




        return rootView;
    }


    public void agregarUsuario(Bundle bTwitter) throws ParseException {

        twitter=true;
        currentUsuario= ((MainActivity)getActivity()).getCurrentUsuario();
        System.out.println(currentUsuario.getTwitter());
        usuarioNombre = bTwitter.getString("user","error");


        /*UsuarioDB usuarioDB= new UsuarioDB(getContext());

        usuarioDB.open();
        ArrayList<Usuario> lista=usuarioDB.getTodosLosUsuarios();
        if (lista.size()==0)
        {
            currentUsuario=new Usuario( bTwitter.getString("user"));
            currentUsuario.setNombre("Andrea");
            System.out.println(currentUsuario);
            usuarioDB.insert(currentUsuario);

        }
        else
        {
            for (int i=0; i<lista.size(); i++)
            {
                System.out.println(lista.get(i));
            }
        }*/


     //   usuarioDB.close();
        //  bTwitter.putParcelable("usuario", (Parcelable) usuarioNuevo);


    }



    public void getEventosTodos() throws ParseException {

        EventoDB eventoDB= new EventoDB(getContext());

        currentUsuario.setListaEventosFavoritos(eventoDB.getTodosLosEventos());

        Log.d(TAG, "entrada TODOS"+ currentUsuario.getListaEventosFavoritos().size());

        for  (int i =0; i<currentUsuario.getListaEventosFavoritos().size(); i++)
        {
            Log.d(TAG, currentUsuario.getListaEventosFavoritos().get(i).toString());
        }
    }

}


