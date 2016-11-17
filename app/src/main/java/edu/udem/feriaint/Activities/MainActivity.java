package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import edu.udem.feriaint.Adapters.TabPagerAdapter;
import edu.udem.feriaint.Data.BDHandler;
import edu.udem.feriaint.Data.UsuarioDB;
import edu.udem.feriaint.Modelos.Usuario;
import edu.udem.feriaint.R;

public class MainActivity extends AppCompatActivity {


    private TabPagerAdapter mTabPagerAdapter;
    private ViewPager mViewPager;
    private BDHandler bdHandler;
    public Usuario currentUsuario;

    private String TAG ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=this.getClass().getSimpleName();


        setContentView(R.layout.activity_main);

        upgradeBD();
        setLayoutMain();
        setCurrentUser();
    }


    protected void setCurrentUser()
    {
        currentUsuario=new Usuario();

        Intent tIntent=getIntent();
        Bundle bUsuario=tIntent.getExtras();
        bUsuario.putString("tipo","twitter");

        //if correo else twitter
        currentUsuario.setTwitter(bUsuario.getString("user"));
        currentUsuario.setNombre("Andrea");
       // currentUsuario.setCarrera("ITC");

        //Add User BD
        UsuarioDB usuarioDB=new UsuarioDB(this);
        usuarioDB.insertar(currentUsuario);

        String msg = "@" + bUsuario.getString("user") + " logged in!";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

  /* public Usuario getCurrentUsuario()
    {
        return currentUsuario;
    }*/

    protected void setLayoutMain()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Get Edicion país

        //toolbar.setTitleTextColor(getResources().getColor(R.color.primary_text));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Crear el adaptador
        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        // Configurar ViewPager con TabPagerAdapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTabPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);
        //Configuar los íconos en las pestañas
        mTabPagerAdapter.setupTabIcons(tabLayout);
        mTabPagerAdapter.setColorTabs(tabLayout,getApplicationContext());


        //Filtro por temas
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Filtrar por temas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent filtrar=new Intent(getApplicationContext(), Filtrar_Activity.class);
                startActivityForResult(filtrar, 55);
            }
        });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent sigin = new Intent(this, AcercaDe.class);
                startActivity(sigin);
                break;
            case R.id.app_bar_search:
                Intent acercaDe = new Intent(this, AcercaDe.class);
                startActivity(acercaDe);
        }
        return super.onOptionsItemSelected(item);
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
