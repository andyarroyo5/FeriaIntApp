package edu.udem.feriaint.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;


import edu.udem.feriaint.R;

public class Detalle_EventoActivity extends  ListActivity{

    private ViewPager mViewPager;
    private LayoutInflater detalle;

    private static final String TAG = "DetalleEventoActivity";
    private static final String SEARCH_QUERY = "(from:FeriaIntUDEM OR from:UDEM) AND #feriaintudem";//OR #CoreaenlaUDEM OR @FeriaIntUDEM";
            //"(#KPOP OR #nature OR #romance OR #mystery)";;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
     //   setActionBar(toolbar);


        Bundle b=getIntent().getExtras();

        TextView tituloE=(TextView) findViewById(R.id.txtTituloEvento);
        TextView fecha=(TextView) findViewById(R.id.txtFechas);
        TextView dia=(TextView) findViewById(R.id.txtFechaDia);
        TextView mes=(TextView) findViewById(R.id.txtMes);
        TextView lugar=(TextView) findViewById(R.id.txtLugar);
        TextView descripcion=(TextView) findViewById(R.id.txtDescripcion);


        tituloE.setText(b.getString("titulo"));
        fecha.setText(b.getString("fecha"));
        lugar.setText(b.getString("lugar"));
        dia.setText(b.getString("dia"));
        mes.setText(b.getString("mes"));
        descripcion.setText(b.getString("descripcion"));


        setListaTweets();

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
                Intent intent = new Intent(this, AcercaDe.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setListaTweets() {

    //RecyclerView rvTweet=(RecyclerView) findViewById(R.id.rvTweetEvento);

        SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query(SEARCH_QUERY)
                .build();


        final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(searchTimeline)
                .build();


//       LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
      // rvTweet.setLayoutManager(mLayoutManager);
      //  eventoAdapter = new EventoAdapter(this.listaEventos);
        //Especificar Adapter

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("FeriaIntUDEM")
                .build();

        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);
      //  setListAdapter(adapter);


        // getListView().setEmptyView(findViewById(android.R.id.list));


    }




}
