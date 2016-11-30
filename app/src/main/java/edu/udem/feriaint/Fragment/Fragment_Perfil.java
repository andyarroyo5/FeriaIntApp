package edu.udem.feriaint.Fragment;

import android.content.Intent;
import android.os.Bundle;
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

import edu.udem.feriaint.Activities.AdminPerfil_Activity;
import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
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
    TextView usuarioPuntos;


    RecyclerView rvEventos;
    RecyclerView rvContCultural;

   // Usuario currentUsuario;

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
        usuarioPuntos=(TextView) rootView.findViewById(R.id.txtUsuarioPuntos);


     /*   try {
            getUsuario();
            getBundleInfo();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
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


                ArrayList<Object> feed= getContenidoFavoritos();

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
                    feed = getEventosFavoritos();
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
               // adminPerfilIntent.putExtra("usuario", (Parcelable) currentUsuario);
              //  startActivity(adminPerfilIntent);
               startActivityForResult(adminPerfilIntent,REQUEST_CODE);


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

    /*
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

    }*/


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
               // mPostsAdapter.notifyDataSetChanged();
                setUsuarioInfo();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setUsuarioInfo();
    }

    public void setUsuarioInfo()
    {
        usuarioNombre.setText(MainActivity.currentUsuario.getNombre());
        usuarioCorreo.setText(MainActivity.currentUsuario.getCorreo()!=""? MainActivity.currentUsuario.getCorreo():"No tienes agregado tu mail ");
        usuarioCarrera.setText(MainActivity.currentUsuario.getCarrera()!="" ? MainActivity.currentUsuario.getCarrera():"No tienes agregada tu carrera" );
        usuarioTwitter.setText(MainActivity.currentUsuario.getTwitter());
        usuarioPuntos.setText(String.valueOf(MainActivity.currentUsuario.getPuntos()));


        Log.e(TAG,"SET USUARIO UPDATE");

    }


     private ArrayList<Object> getEventosFavoritos() throws ParseException {


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
                     MainActivity.currentUsuario.getListaEventosFavoritos().add(eventos.get(i));
                     listaEventos.add(eventos.get(i));
                 }
             }


         } catch (ParseException e) {
             e.printStackTrace();
         }






        return listaEventos;
    }

    private ArrayList<Object> getContenidoFavoritos() {
       ArrayList<Object> items = new ArrayList<>();

        ArrayList<ContenidoCultural> contCult=MainActivity.currentUsuario.getListaContCultFavoritos();
        items.addAll(contCult);

       /* for  (int i =0; i<contCult.size(); i++)
        {
            if(contCult.get(i).isFavorito())
            {
                MainActivity.currentUsuario.getListaContCultFavoritos().add(contCult.get(i));
            }
        }*/


        /*
                Date p=new Date();
                Calendar c= new GregorianCalendar();
                c.getTime();
        */


        return items;

    }




}


