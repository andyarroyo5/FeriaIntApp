package edu.udem.feriaint.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.udem.feriaint.Activities.AdminPerfil_Activity;
import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Data.UsuarioDB;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Usuario;
import edu.udem.feriaint.R;
import edu.udem.feriaint.TwitterInicioSesion;

/**
 * Created by Andrea Arroyo on 09/10/2016.
 */

public class Fragment_Perfil extends Fragment{

    private String TAG;

    int REQUEST_CODE=555;

    ImageButton btnFavoritoPerfil;
    ImageButton btnEventosPerfil;
    ImageButton btnInfoPerfil;
    Button btnAdminPerfil;

    LinearLayout lytFav;
    LinearLayout lytInfo;
    LinearLayout lytEventos;
    Button cerrarSesion;

    TextView usuarioNombre;
    TextView usuarioCorreo;
    TextView usuarioCarrera;
    TextView usuarioTwitter;

    RecyclerView rvEventos;
    RecyclerView rvContCultural;

    Usuario currentUsuario;

   // String usuarioNombre;
    boolean twitter=false;
    Bundle bUsuario;

    RecyclerView mRecyclerViewEventos;
    RecyclerView mRecyclerViewFavoritos;
    RecyclerView.LayoutManager mLayoutManager;
    ComplexRecyclerViewAdapter compAdapter;

    public Fragment_Perfil() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=getClass().getSimpleName();

        //currentUsuario= ((MainActivity)getActivity()).getCurrentUsuario();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        lytInfo=(LinearLayout) rootView.findViewById(R.id.layout_Info);
        lytFav=(LinearLayout) rootView.findViewById(R.id.layout_Favoritos);
        lytEventos=(LinearLayout) rootView.findViewById(R.id.layout_eventos);

        mRecyclerViewEventos = (RecyclerView) rootView.findViewById(R.id.rv_eventos);
        mRecyclerViewFavoritos = (RecyclerView) rootView.findViewById(R.id.rv_favoritos);

        btnFavoritoPerfil= (ImageButton) rootView.findViewById(R.id.btnFavoritoPerfil);
        btnEventosPerfil = (ImageButton) rootView.findViewById(R.id.btnEventos);
        btnInfoPerfil=(ImageButton) rootView.findViewById(R.id.btnInfoPerfil);
        btnAdminPerfil=(Button) rootView.findViewById(R.id.btnAdministrarPerfil);

        cerrarSesion = (Button) rootView.findViewById(R.id.btnCerrarSesion);

        usuarioCorreo=(TextView) rootView.findViewById(R.id.txtUsuarioCorreo);
        usuarioNombre=(TextView) rootView.findViewById(R.id.txtUsuarioNombre);
        usuarioCarrera=(TextView) rootView.findViewById(R.id.txtUsuarioCarrera);
        usuarioTwitter=(TextView) rootView.findViewById(R.id.txtUsuarioTwitter);


        try {
            getUsuario();
            getBundleInfo();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setUsuarioInfo();

        btnFavoritoPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnFavoritoPerfil.setColorFilter(getContext().getResources().getColor(R.color.accent));
                btnInfoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.primary_text));
                btnEventosPerfil.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.primary_text));
                lytFav.setVisibility(LinearLayout.VISIBLE);
                lytInfo.setVisibility(LinearLayout.GONE);
                lytEventos.setVisibility(LinearLayout.GONE);


                //Recycler Viewer

                if (mRecyclerViewFavoritos != null) {
                    mRecyclerViewFavoritos.setHasFixedSize(true);
                }

                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerViewFavoritos.setLayoutManager(mLayoutManager);


                ArrayList<Object> feed= getFavoritos();

                compAdapter=new ComplexRecyclerViewAdapter(feed);
                mRecyclerViewFavoritos.setAdapter(compAdapter);
                compAdapter.notifyDataSetChanged();
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

                lytEventos.setVisibility(LinearLayout.VISIBLE);
                lytInfo.setVisibility(LinearLayout.GONE);
                lytFav.setVisibility(LinearLayout.GONE);

                //Recycler Viewer

                if (mRecyclerViewEventos != null) {
                    mRecyclerViewEventos.setHasFixedSize(true);
                }

                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerViewEventos.setLayoutManager(mLayoutManager);


                ArrayList<Object> feed= null;
                try {
                    feed = getEventos();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                compAdapter=new ComplexRecyclerViewAdapter(feed);
                mRecyclerViewEventos.setAdapter(compAdapter);
                compAdapter.notifyDataSetChanged();

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
                lytEventos.setVisibility(LinearLayout.GONE);
                lytInfo.setVisibility(LinearLayout.VISIBLE);


            }
        });


        btnAdminPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent adminPerfilIntent=new Intent(getContext(), AdminPerfil_Activity.class);
                adminPerfilIntent.putExtra("nombre",usuarioNombre.getText());
                adminPerfilIntent.putExtra("correo",usuarioCorreo.getText());
                adminPerfilIntent.putExtra("carrera",usuarioCarrera.getText());
                adminPerfilIntent.putExtra("twitter",usuarioTwitter.getText());
                adminPerfilIntent.putExtra("usuario", (Parcelable) currentUsuario);
                startActivity(adminPerfilIntent);
              //  startActivityForResult(adminPerfilIntent,REQUEST_CODE);


            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent cerrarS=new Intent(getContext(), TwitterInicioSesion.class);
                cerrarS.putExtra("cerrarS",true);
                startActivity(cerrarS);
            }
        });

        return rootView;
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
              currentUsuario.setTwitter(bUsuario.getString("user"));

                Log.d("BUNDLE",currentUsuario.toString());

                //  agregarUsuario(bUsuario);

                break;

            case "ejemplo":
                Bundle b = getArguments();
                String s = b.getString("tipo");
                Log.d("BUNDLE", s);
                break;
        }
        else
       {

           Log.d(TAG, "bundle without tipo");

       }


    }


    public void getUsuario() throws ParseException {
        UsuarioDB usuario=new UsuarioDB(getContext());

        currentUsuario=usuario.getTodosLosUsuarios();

        Log.e(TAG,"GET IN PERFIL"+currentUsuario.toString());

    }


    public void setUsuarioInfo()
    {
        usuarioNombre.setText(currentUsuario.getNombre());
        usuarioCorreo.setText(currentUsuario.getCorreo()!=""? currentUsuario.getCarrera():"No tienes agregado tu mail ");
        usuarioCarrera.setText(currentUsuario.getCarrera()!="" ? currentUsuario.getCarrera():"No tienes agregada tu carrera" );
        usuarioTwitter.setText(currentUsuario.getTwitter());

        Log.e(TAG,currentUsuario.toString());

    }



     private ArrayList<Object> getEventos() throws ParseException {


        ArrayList<Evento> eventos = new ArrayList<Evento>();
        ArrayList<Object> listaEventos = new ArrayList<>();

        EventoDB eventoDB= new EventoDB(getContext());

         try {
             eventos=eventoDB.getTodosLosEventos();
             if(eventos!=null)
             for  (int i =0; i<eventos.size(); i++)
             {
                 if(eventos.get(i).isFavorito())
                 {
                     currentUsuario.getListaEventosFavoritos().add(eventos.get(i));
                     listaEventos.add(eventos.get(i));
                 }
             }


         } catch (ParseException e) {
             e.printStackTrace();
         }



        return listaEventos;
    }

    private ArrayList<Object> getFavoritos() {
        ArrayList<Object> items = new ArrayList<>();

        Date p=new Date();
        Calendar c= new GregorianCalendar();
        c.getTime();

        items.add("image");
        items.add(new ContenidoCultural("Corea y su gastronomía",R.drawable.evento));
        items.add(new ContenidoCultural("Fun Facts",R.drawable.evento2));
        items.add(new ContenidoCultural("Historia de Corea",R.drawable.corea_logo));

        return items;
    }




}


