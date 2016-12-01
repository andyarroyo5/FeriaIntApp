package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.udem.feriaint.Adapters.TabPagerAdapter;
import edu.udem.feriaint.Data.BDHandler;
import edu.udem.feriaint.Data.UsuarioDB;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Edicion;
import edu.udem.feriaint.Modelos.Evento;
import edu.udem.feriaint.Modelos.Tema;
import edu.udem.feriaint.Modelos.Usuario;
import edu.udem.feriaint.Parser.ContCulturalJSON;
import edu.udem.feriaint.Parser.EdicionJSON;
import edu.udem.feriaint.Parser.EventoJSON;
import edu.udem.feriaint.Parser.TemaJSON;
import edu.udem.feriaint.R;

public class MainActivity extends AppCompatActivity {


    private TabPagerAdapter mTabPagerAdapter;
    private ViewPager mViewPager;
    private BDHandler bdHandler;

    //clase JSON
    private EdicionJSON edicionJSON;
//listas que obtienen info de JSON
    public static Usuario currentUsuario;
    public static ArrayList<Evento> listaEventos;
    public static ArrayList<ContenidoCultural> listaContCult;
    public static ArrayList<Tema> listaTemasEventos;
    public static ArrayList<Tema> listaTemasContCult;

    public static Edicion edicion;



    public final String EVENTO="evento";
    public final String CONTCULT="contenidoCultural";
    public final String USUARIO="usuario";
    public static final String API_KEY = String.valueOf(R.string.API_KEY_YT);



    private String TAG ;
    private Toolbar toolbar;

    private final int  REQUEST_CODE_FILTRAR=111;


    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=this.getClass().getSimpleName();

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setLayoutMain();

        upgradeBD();
        try {
            getEdicionJSON();
            getTemasEventos();
            getListaEventosJSON();
            getListaContenidoCultural();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setCurrentUser();


    }


    protected void setCurrentUser()
    {
        currentUsuario=new Usuario();

        Intent tIntent=getIntent();
        Bundle bUsuario=tIntent.getExtras();
        //bUsuario.putString("tipo","twitter");

        //if correo else twitter

       // currentUsuario.setCarrera("ITC");
        String msg ="";

        if(bUsuario.getString("tipo").equals("twitter"))
        {
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

             msg = "@" + bUsuario.getString("user") + " iniciaste sesión por Twitter!";
            currentUsuario.setTwitter(bUsuario.getString("user"));

        }
        else {
             msg = "@" + bUsuario.getString("user") +bUsuario.getString("token")+ " iniciaste sesión por google!";
            currentUsuario.setNombre(bUsuario.getString("user"));
            currentUsuario.setCorreo(bUsuario.getString("correo"));
           // String img=bUsuario.getString("img");
           // currentUsuario.setImgPerfil(Uri.parse(img));

        }

        //Add User BD
        UsuarioDB usuarioDB=new UsuarioDB(this);
        usuarioDB.insertar(currentUsuario);


       Toast t= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
       // Snackbar.make(this.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();



    }

    protected void setLayoutMain()
    {
        toolbar.inflateMenu(R.menu.menu_main);

        // Crear el adaptador
        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        // Configurar ViewPager con TabPagerAdapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTabPagerAdapter);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);
        //Configuar los íconos en las pestañas
        mTabPagerAdapter.setupTabIcons(tabLayout);
        mTabPagerAdapter.setColorTabs(tabLayout,getApplicationContext());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   int position = tab.getPosition();
                                                   switch (position) {
                                                       case 0:
                                                           //hideFab
                                                           Log.e("Main", String.valueOf(position));
                                                           fab.hide();
                                                           break;
                                                       case 1:
                                                           //get temas eventos

                                                           //fab.setVisibility(View.VISIBLE);
                                                           fab.show();

                                                           break;
                                                       case 2:
                                                           //get temas de edicion
                                                           //fab.setVisibility(View.VISIBLE);
                                                           fab.show();


                                                           break;
                                                       case 3:
                                                           //hideFab
                                                           Log.e("Main", String.valueOf(position));

                                                           //fab.setVisibility(View.GONE);
                                                           fab.hide();

                                                           break;
                                                   }

                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {
                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {
                                                   int position = tab.getPosition();
                                                   switch (position) {
                                                       case 0:
                                                           //hideFab
                                                           Log.e("Main", String.valueOf(position));
                                                           fab.hide();
                                                           break;
                                                       case 1:
                                                           //get temas eventos

                                                           //fab.setVisibility(View.VISIBLE);
                                                           fab.show();

                                                           break;
                                                       case 2:
                                                           //get temas de edicion
                                                           //fab.setVisibility(View.VISIBLE);
                                                           fab.show();


                                                           break;
                                                       case 3:
                                                           //hideFab
                                                           Log.e("Main", String.valueOf(position));

                                                           //fab.setVisibility(View.GONE);
                                                           fab.hide();

                                                           break;
                                                   }
                                               }

                                           });


        //Mostrar fab por temas
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int tab= tabLayout.getSelectedTabPosition();

                Intent filtrar=new Intent(getApplicationContext(), Filtrar_Activity.class);
                Bundle b=new Bundle();
                if(tab==1)
                {
                    b.putString("tipo",EVENTO);
                    if(listaTemasEventos==null)
                    {
                        try {
                            getTemasEventos();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG,"no hay temas eventos pedir json otra vez");
                    }

                }
                else
                {
                    b.putString("tipo",CONTCULT);
                    if(listaTemasContCult==null)
                    {
                        getTemasContCult();
                        Log.e(TAG,"no hay temas contCult pedir json otra vez");
                    }
                }

                Log.d(TAG, "GET in FAB"+ tab);


                filtrar.putExtras(b);
                startActivityForResult(filtrar, REQUEST_CODE_FILTRAR);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {

            case REQUEST_CODE_FILTRAR:
                Log.e(TAG, "Regreso intent filtrar");//+REQUEST_CODE_FILTRAR+ "data "+data.toString());

                break;

            default:
                Log.e(TAG, "Regreso intent"+requestCode);//+" result"+resultCode+ "data "+data.toString());
                break;

        }
    }

    public void upgradeBD()
    {
        try {

          bdHandler=new BDHandler(this);
          bdHandler.onUpgrade(bdHandler.getReadableDatabase(),bdHandler.getBDVersion(), bdHandler.getBDVersion()+1);
            Log.e(TAG, "UPGRADE db "+bdHandler.getBDVersion());
        } catch (Exception e) {
            Log.e(TAG, "UPGRADE db "+e.toString());
       }
    }

    //Menu Layout y caso de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.acercade:

                if (edicionJSON.getStatus() == AsyncTask.Status.FINISHED) {

                    Intent acercaDe = new Intent(this, AcercaDe.class);
                    acercaDe.putExtra("pais", edicion.getPais());
                    acercaDe.putExtra("fechaInicio", edicion.getFechaInicio());
                    acercaDe.putExtra("fechaFinal", edicion.getFechaFinal());
                    startActivity(acercaDe);
                }

                break;


            case R.id.ejemplo:


                Intent acercaDe = new Intent(this, EjemploActivity.class);
                startActivity(acercaDe);


                break;
        }

            return super.onOptionsItemSelected(item);

    }


   //TODO getJSONs class? y al hacer refresh llamar otra vez depende de lo que se quiera

    public void getEdicionJSON() throws ExecutionException, InterruptedException {
        edicionJSON=new EdicionJSON(this);
        //edicionJSON.execute();
        edicion= edicionJSON.execute().get();
        //while(!edicionJSON.getStatus().equals(AsyncTask.Status.FINISHED));

    }

    public void getTemasEventos() throws ExecutionException, InterruptedException {
        //TODO variables como evento hacer CONS

        TemaJSON temasEJSON=new TemaJSON(this.getApplicationContext(), EVENTO);
        listaTemasEventos=temasEJSON.execute().get();
        if(temasEJSON.getStatus()== AsyncTask.Status.FINISHED)
        Log.e(TAG, "lista termino"+listaTemasEventos.size());


        // temasEJSON.execute();
       // listaTemasEventos=temasEJSON.getListaContenidos().get();
    }


    public void getTemasContCult()
    {


        TemaJSON temasContCultJSON=new TemaJSON(this.getApplicationContext(), CONTCULT);
        //listaTemasEventos=temasEJSON.execute().get();  esto hace esperar al thread principal
        temasContCultJSON.execute();
        listaTemasContCult=temasContCultJSON.getListaContenidos();

    }

    public void getListaEventosJSON() throws ExecutionException, InterruptedException {
        listaEventos=new ArrayList<>();
        EventoJSON eventosJSON=new EventoJSON(this);
        listaEventos= eventosJSON.execute().get();
      /*  eventosJSON.execute();
        listaEventos=eventosJSON.getListaEventos();*/

    }

    public void getListaContenidoCultural() throws ExecutionException, InterruptedException {
        listaContCult=new ArrayList<>();
        ContCulturalJSON contCultJSON=new ContCulturalJSON(this);
        listaContCult=contCultJSON.execute().get();
        //listaContCult=contCultJSON.getListaContCultural();

    }









    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "+ ON START +");


        //ver si existe en web

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "+ ON RESUME +");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
         Log.v(TAG, "- ON DESTROY -");
    }
}
