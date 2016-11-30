package edu.udem.feriaint.Parser;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import edu.udem.feriaint.Adapters.ContenidoCulturalAdapter;
import edu.udem.feriaint.Data.TemaContCulturalBD;
import edu.udem.feriaint.Modelos.ContenidoCultural;
import edu.udem.feriaint.Modelos.Tema;

/**
 * Created by Andrea Arroyo on 09/11/2016.
 */

public class ContCulturalJSON extends AsyncTask<Object, Object, ArrayList<ContenidoCultural>> {

    private Context context;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeContainerCultura;
    private String url;
    private ArrayList<ContenidoCultural> listaContenidos;
    private ContenidoCulturalAdapter contenidoAdapter;
    TemaContCulturalBD temaBD;

    private String TAG;


    public ContCulturalJSON(Context context) {

        this.context = context;
        url = "http://feriaint.herokuapp.com/app/contenido";
        listaContenidos = new ArrayList<ContenidoCultural>();
        TAG=getClass().getSimpleName();
        temaBD=new TemaContCulturalBD(context);

    }

    public void setRecyclerViewer( RecyclerView mRecyclerViewer)
    {
        this.mRecyclerView=mRecyclerViewer;
    }

    public void setSwipeContainer (SwipeRefreshLayout swipeContainerCultura)
    {
        this.swipeContainerCultura=swipeContainerCultura;

    }

    public ArrayList<ContenidoCultural> getListaContCultural() { return listaContenidos;  }

     @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected ArrayList<ContenidoCultural> doInBackground(Object... voids) {

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try{

                ArrayList<Tema> listaTemas=new ArrayList<>();

                JSONArray contCulturalArray = new JSONArray(jsonStr);

                for (int i = 0; i < contCulturalArray.length(); i++) {
                    JSONObject contCult = contCulturalArray.getJSONObject(i);

                    Long id =contCult.getLong("id");
                    String titulo=contCult.getString("titulo");

                    //información relacionada al TEMA
                    Long temaId=contCult.getLong("tema_id");
                    String temaNombre=contCult.getString("tema_nombre");
                    Tema tema=new Tema(temaId, temaNombre);

                    String tipo=contCult.getString("tipo");

                  ContenidoCultural contCultural=new ContenidoCultural(titulo,tema);

                    contCultural.setId(id);
                    contCultural.setTipo(tipo);

                   JSONArray contenidoArray = contCult.getJSONArray("contenido");
                    for (int j = 0; j < contenidoArray.length(); j++) {
                        JSONObject contenido=contenidoArray.getJSONObject(j);

                        String formato=contenido.getString("formato");
                        String cont=contenido.getString("contenido");

                        if (formato.equals("imagen") && contCultural.getImgPortada()==null)
                        {
                            contCultural.setImgPortada(cont);
                            Log.e(TAG,"Portada "+cont);
                        }

                        Log.e(TAG,formato+" "+cont);

                        contCultural.getFormato().add(formato);
                        contCultural.getContenido().add(cont);

                    }

                    listaContenidos.add(contCultural);

                    Log.e(TAG,contCultural.toString());
                }


                for (int i=0; i< listaTemas.size(); i++)
                {
                    Log.e(TAG, listaTemas.get(i).toString());
                }

                    Log.d(TAG,"LISTA QUEDO"+listaContenidos.size());


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
        } else {
                Log.e(TAG, "Couldn't get json from server.");

            }


            return listaContenidos;
    }

    @Override
    protected void onPostExecute( ArrayList<ContenidoCultural> result) {
        super.onPostExecute(result);
        if (swipeContainerCultura!=null)
        swipeContainerCultura.setRefreshing(false);

        try {
            if (mRecyclerView!=null)
                layoutAdapter();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void layoutAdapter() throws ParseException {

        contenidoAdapter = new ContenidoCulturalAdapter(listaContenidos);
        //Especificar Adapter
        mRecyclerView.setAdapter(contenidoAdapter);
        contenidoAdapter.notifyDataSetChanged();
    }

}
