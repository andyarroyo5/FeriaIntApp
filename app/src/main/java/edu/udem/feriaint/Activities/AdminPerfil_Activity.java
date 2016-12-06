package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.ArrayList;

import edu.udem.feriaint.Adapters.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Data.UsuarioDB;
import edu.udem.feriaint.Fragment.Fragment_Perfil;
import edu.udem.feriaint.Modelos.Usuario;
import edu.udem.feriaint.R;

public class AdminPerfil_Activity extends AppCompatActivity implements  View.OnClickListener {

    EditText nombre;
    TextView correo;
    EditText carrera;
    EditText twitter;
    ImageButton img_perfil;
    LinearLayout layout_twitter;
    Button btn_img;
    Button btn_quitar_img;
    Uri imgPath;
    boolean img=false;
    Button sign_out;
    private String TAG;


    TwitterLoginButton signInTwitter;



    final int RESULT_LOAD_IMAGE=1010;
   // Usuario currentUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_perfil);

        nombre=(EditText) findViewById(R.id.usuario_nombre);
        correo=(TextView) findViewById(R.id.usuario_correo);
        carrera=(EditText) findViewById(R.id.usuario_carrera);
        twitter=(EditText) findViewById(R.id.usuario_twitter);
        img_perfil=(ImageButton) findViewById(R.id.img_perfil_detalle);
        btn_img=(Button) findViewById(R.id.btn_img_perfil);
        btn_quitar_img=(Button) findViewById(R.id.btn_quitar_img);

        layout_twitter=(LinearLayout) findViewById(R.id.layout_admin_Twitter);

        nombre.setText(MainActivity.currentUsuario.getNombre());
        correo.setText(MainActivity.currentUsuario.getCorreo());
        carrera.setText(MainActivity.currentUsuario.getCarrera());
        twitter.setText(MainActivity.currentUsuario.getTwitter());
        signInTwitter = (TwitterLoginButton) findViewById(R.id.signin_twitter);
        sign_out=(Button) findViewById(R.id.sign_out_twitter);

        signInTwitter.setOnClickListener(this);


        signInTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                TwitterSession session = result.data;
                String output = "Status: " +
                        "Your login was successful " +
                        session.getUserName() +
                        "\nAuth Token Received: " +
                        session.getAuthToken().token;

                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();

                Log.e(TAG, "TWITTER CONECTADOO"+result);
                MainActivity.currentUsuario.setTwitter(result.data.getUserName());
                Log.e(TAG,"TWITTER 2"+result.data.getUserName());
                twitter.setText(result.data.getUserName());
                twitter.setVisibility(View.VISIBLE);
                sign_out.setVisibility(View.VISIBLE);
                signInTwitter.setVisibility(View.GONE);



            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }

        });


        if(MainActivity.currentUsuario.getTwitter()!=null)
        {
            signInTwitter.setVisibility(View.GONE);
            sign_out.setVisibility(View.VISIBLE);
            layout_twitter.setVisibility(View.VISIBLE);
        }
        else
        {
            twitter.setVisibility(View.GONE);
            signInTwitter.setVisibility(View.VISIBLE);
            sign_out.setVisibility(View.GONE);

        }


        if ( MainActivity.currentUsuario.getImgPerfil()!=null && !MainActivity.currentUsuario.getImgPerfil().toString().isEmpty())
        {
            imgPath=MainActivity.currentUsuario.getImgPerfil();
            Glide.with(this)
                    .load(imgPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_perfil);
            img_perfil.setVisibility(View.VISIBLE);

            btn_img.setVisibility(View.GONE);
            btn_quitar_img.setVisibility(View.VISIBLE);
        }
        else
        {
            img_perfil.setVisibility(View.GONE);
            btn_img.setVisibility(View.VISIBLE);
            btn_quitar_img.setVisibility(View.GONE);
        }



        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGaleria();
            }
        });

       // MaicurrentUsuario=bUsuario.getParcelable("usuario");
        
    }


    public void guardarCambios(View view)
    {

        Intent resultInfo=new Intent(this, Fragment_Perfil.class);

        UsuarioDB usuarioDB=new UsuarioDB(this);
        //Get usuario?

        MainActivity.currentUsuario.setNombre(nombre.getText().toString());
        MainActivity.currentUsuario.setCarrera(carrera.getText().toString());
       // MainActivity.currentUsuario.setTwitter(twitter.getText().toString());
        if(imgPath!=null &&!imgPath.equals(""))
        {
            MainActivity.currentUsuario.setImgPerfil(imgPath);

        }
        else
        {
            MainActivity.currentUsuario.setImgPerfil(null);
        }

        usuarioDB.actualizar(MainActivity.currentUsuario);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            imgPath = data.getData();

            Glide.with(this)
                    .load(imgPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_perfil);
            img_perfil.setVisibility(View.VISIBLE);
            btn_quitar_img.setVisibility(View.VISIBLE);
            btn_img.setVisibility(View.GONE);


        }
        else {
            signInTwitter.onActivityResult(requestCode,resultCode,data);
        }

    }

    public void quitarImg(View view )
    {
        img_perfil.setVisibility(View.GONE);
        imgPath=Uri.parse("");
        btn_quitar_img.setVisibility(View.GONE);
        btn_img.setVisibility(View.VISIBLE);
    }

    public void agregarImg(View view)
    {
      getGaleria();

    }


    public void esconderMostrarImg() {
        if (img) {
            btn_img.setText("Quitar foto de perfil");
            Glide.with(this)
                    .load(imgPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_perfil);


            btn_img.setBackgroundColor(ContextCompat.getColor(this,R.color.tw__composer_light_gray));

        } else {
            btn_img.setText("Agregar Foto Perfil");
            img_perfil.setVisibility(View.GONE);
            btn_img.setBackgroundColor(ContextCompat.getColor(this,R.color.accent));
        }

    }


    public void getGaleria()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), RESULT_LOAD_IMAGE);
    }


    public void cerrarSesionTwitter(View view)
    {
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
        MainActivity.currentUsuario.setTwitter(null);
        twitter.setVisibility(View.GONE);
        signInTwitter.setVisibility(View.VISIBLE);
        sign_out.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View view) {

    }
}
