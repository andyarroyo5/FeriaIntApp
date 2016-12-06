package edu.udem.feriaint.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.udem.feriaint.Activities.AdminPerfil_Activity;
import edu.udem.feriaint.Activities.InicioSesionEjemplo;
import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Adapters.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Data.ContCultBD;
import edu.udem.feriaint.Data.EventoDB;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.R;
import edu.udem.feriaint.TwitterInicioSesion;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Andrea Arroyo on 09/10/2016.
 */

public class Fragment_Perfil extends Fragment {

    private String TAG;

    public final int REQUEST_CODE=555;

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




    ImageView img_perfil;


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
    LinearLayout layoutCarrera;
    LinearLayout layoutTwitter;

    public Fragment_Perfil() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=getClass().getSimpleName();
        //TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.TWITTER_KEY,getString(R.string.TWITTER_SECRET);
        //Fabric.with(this, new Twitter(authConfig));

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
        img_perfil=(ImageView) rootView.findViewById(R.id.img_perfil);

        layoutCarrera=(LinearLayout)rootView.findViewById(R.id.layout_UsuarioCarrera);
        layoutTwitter=(LinearLayout)rootView.findViewById(R.id.layout_UsuarioTwitter);


        setUsuarioInfo();

        btnFavoritoPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnFavoritoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.accent));
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
                btnFavoritoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.primary_text));
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
                btnFavoritoPerfil.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.primary_text));
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

                Intent cerrarS=new Intent(getContext(), InicioSesionEjemplo.class);
                cerrarS.putExtra("cerrarS",true);
                startActivity(cerrarS);
                getActivity().finish();

            }
        });


       if ( MainActivity.currentUsuario.getImgPerfil()!=null && !MainActivity.currentUsuario.getImgPerfil().toString().isEmpty())
        {
            if(MainActivity.currentUsuario.getImgPerfil().toString().equals(""))
            {
                img_perfil.setVisibility(View.GONE);
            }
        }


        return rootView;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainActivity.RESULT_OK) {

            switch (requestCode)
            {
                case REQUEST_CODE:
                    Log.e(TAG, "ON REQUEST CODE");
                    setUsuarioInfo();
                    break;
            }
            Log.e(TAG, "FRAGMENT -- ON REQ CODE");

         // signInResult(requestCode,resultCode,data);


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
        usuarioCorreo.setText(MainActivity.currentUsuario.getCorreo());


        if(MainActivity.currentUsuario.getCarrera()==null || MainActivity.currentUsuario.getCarrera().isEmpty())
        {

            layoutCarrera.setVisibility(View.GONE);
        }
        else
        {
            usuarioCarrera.setText(MainActivity.currentUsuario.getCarrera());
            layoutCarrera.setVisibility(View.VISIBLE);
        }

        if(MainActivity.currentUsuario.getTwitter()==null || MainActivity.currentUsuario.getTwitter().isEmpty())
        {

            layoutTwitter.setVisibility(View.GONE);
        }
        else
        {
            usuarioTwitter.setText(MainActivity.currentUsuario.getTwitter());

            layoutTwitter.setVisibility(View.VISIBLE);
        }

        usuarioPuntos.setText(String.valueOf(MainActivity.currentUsuario.getPuntos()));

        if (MainActivity.currentUsuario.getImgPerfil()!=null)
        {

            if(!MainActivity.currentUsuario.getImgPerfil().toString().equals(""))
                img_perfil.setVisibility(View.VISIBLE);

            Glide.with(getContext()).load(MainActivity.currentUsuario.getImgPerfil()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(new BitmapImageViewTarget(img_perfil) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img_perfil.setImageDrawable(circularBitmapDrawable);
                }
            });

        }
        else
        {
            img_perfil.setVisibility(View.GONE);
        }

        Log.e(TAG,"SET USUARIO UPDATE");

    }


    private ArrayList<Object> getEventosFavoritos() throws ParseException {

        ArrayList<Object> items = new ArrayList<>();

        ArrayList<Evento> eventos=MainActivity.currentUsuario.getListaEventosFavoritos();
        items.addAll(eventos);

        return items;
    }

    private ArrayList<Object> getContenidoFavoritos() {
       ArrayList<Object> items = new ArrayList<>();

        ContCultBD contCultBD=new ContCultBD(getContext());
        ArrayList<ContenidoCultural> contCult=MainActivity.currentUsuario.getListaContCultFavoritos();
      //  ArrayList<ContenidoCultural> contCult=
        items.addAll(contCult);

        return items;

    }

}


