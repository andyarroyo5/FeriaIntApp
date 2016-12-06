package edu.udem.feriaint.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.text.ParseException;
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
import edu.udem.feriaint.Parser.EdicionJSON;
import edu.udem.feriaint.Parser.GetJSON;
import edu.udem.feriaint.R;
import edu.udem.feriaint.SessionRecorder;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {


    private TabPagerAdapter mTabPagerAdapter;
    public static ViewPager mViewPager;
    private BDHandler bdHandler;

    //clase JSON
    private EdicionJSON edicionJSON;
    private ProgressDialog mProgressDialog;
//listas que obtienen info de JSON
    public static Usuario currentUsuario;
    public static ArrayList<Evento> listaEventos;
    public static ArrayList<ContenidoCultural> listaContCult;
    public static ArrayList<Tema> listaTemasEventos;
    public static ArrayList<Tema> listaTemasContCult;

    TwitterAuthConfig authConfig = new TwitterAuthConfig(String.valueOf(R.string.TWITTER_KEY), String.valueOf(R.string.TWITTER_SECRET));


    private FirebaseUser firebaseUser;

    public static GetJSON repositorioJSON;
    public static Edicion edicion;


    public static TextView toolbarTitulo;

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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitulo=(TextView) findViewById(R.id.appTitulo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setLayoutMain();


        repositorioJSON=new GetJSON(this);
        try {

            edicion= MainActivity.repositorioJSON.getEdicionJSON();
           // MainActivity.repositorioJSON.getTemasEventos(false);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setCurrentUser();
        // upgradeBD();


    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "+ ON START +");



    }

    protected void setCurrentUser()
    {
        currentUsuario=new Usuario();
        Intent tIntent=getIntent();
        Bundle bUsuario=tIntent.getExtras();
        String msg ="";
        currentUsuario.setPuntos(0);

        Fabric.with(this, new Twitter(authConfig));
        final Session activeSession = SessionRecorder.recordInitialSessionState(
                Twitter.getSessionManager().getActiveSession()
        );

        if(activeSession!=null)
        {
            TwitterSession session= Twitter.getSessionManager().getSession(activeSession.getId());
            Log.e(TAG, "TWITTER CONNECTED" + session.getUserName());
            currentUsuario.setTwitter(session.getUserName());
        }
        else
        {
            Log.e(TAG, "TWITTER NOT CONNECTED");

            //TwitterSession session=result.data;
            currentUsuario.setTwitter(null);
        }

      // currentUsuario.setId(Long.parseLong(firebaseUser.getUid()));

        if(bUsuario.getString("tipo").equals("firebase"))
        {

             msg = "@" + bUsuario.getString("user") + " fb!";
          //  currentUsuario.setTwitter(bUsuario.getString("user"));


            currentUsuario.setNombre(firebaseUser.getDisplayName()!=null? firebaseUser.getDisplayName(): bUsuario.getString("user"));
            currentUsuario.setCorreo(firebaseUser.getEmail());
            currentUsuario.setImgPerfil(firebaseUser.getPhotoUrl());

            //getPuntosUsuario


        }
        else {
             msg = "@" + bUsuario.getString("user")+ " iniciaste sesión por google!";

            //currentUsuario.setNombre(bUsuario.getString("user"));
            //currentUsuario.setCorreo(bUsuario.getString("correo"));
            //currentUsuario.setImgPerfil(Uri.parse(bUsuario.getString("img")));
        }

        //Add User BD
        UsuarioDB usuarioDB=new UsuarioDB(this);
        usuarioDB.insertar(currentUsuario);


       Toast t= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
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
        mTabPagerAdapter.setupTabIcons(tabLayout,getApplicationContext());
        mTabPagerAdapter.setColorTabs(tabLayout.getSelectedTabPosition());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        //hideFab
                        toolbarTitulo.setText("Feria Internacional");
                        fab.hide();

                        break;
                    case 1:
                        //get temas eventos
                        toolbarTitulo.setText("Eventos");
                        fab.show();
                        //fab.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        //get temas de edicion
                        //fab.setVisibility(View.VISIBLE);
                        toolbarTitulo.setText("Cultura");

                        fab.show();
                        break;
                    case 3:
                        //hideFab
                        fab.hide();
                        toolbarTitulo.setText("Perfil");
                        break;
                }

                mTabPagerAdapter.setColorTabs(position);



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        //Mostrar fab por temas


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int tab= tabLayout.getSelectedTabPosition();

                Intent filtrar=new Intent(getApplicationContext(), Filtrar_Activity.class);
                Bundle b=new Bundle();
                if(tab==1)
                {

                    b.putString("tipo",EVENTO);


                }
                else
                {
                    if (tab==2)
                    {
                        b.putString("tipo",CONTCULT);

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

               // mTabPagerAdapter.getItem(mViewPager.getCurrentItem()).;

                Log.e(TAG, "Regreso intent filtrar");//+REQUEST_CODE_FILTRAR+ "data "+data.toString());

                break;

            default:

                if(resultCode == RESULT_OK)
                {
                    Log.e(TAG, "Regreso intent"+requestCode);

                    Twitter.logIn(this, new Callback<TwitterSession>() {
                        @Override
                        public void success(Result<TwitterSession> result) {
                            String output = "Status: " +
                                    "Your login was successful " +
                                    result.data.getUserName() +
                                    "\nAuth Token Received: " +
                                    result.data.getAuthToken().token;

                            Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "TWIITER CONECTADOO"+result);
                            MainActivity.currentUsuario.setTwitter(result.data.getUserName());
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            Toast.makeText(getApplicationContext(), "error twitter" +exception, Toast.LENGTH_SHORT).show();

                        }
                    });

                }


                //+" result"+resultCode+ "data "+data.toString()).
                Fragment f=mTabPagerAdapter.getItem(mViewPager.getCurrentItem());

               // f.onActivityResult(requestCode,resultCode,data);



        break;

    }
    }

    public void upgradeBD() throws ParseException {

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


                    Intent acercaDe = new Intent(this, AcercaDe.class);
                    acercaDe.putExtra("pais", edicion.getPais());
                    acercaDe.putExtra("fechaInicio", edicion.getFechaInicio());
                    acercaDe.putExtra("fechaFinal", edicion.getFechaFinal());
                    startActivity(acercaDe);

                break;
        }

            return super.onOptionsItemSelected(item);

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
