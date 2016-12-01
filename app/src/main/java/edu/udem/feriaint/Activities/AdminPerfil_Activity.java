package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.udem.feriaint.Adapters.ComplexRecyclerViewAdapter;
import edu.udem.feriaint.Data.UsuarioDB;
import edu.udem.feriaint.Fragment.Fragment_Perfil;
import edu.udem.feriaint.Modelos.Usuario;
import edu.udem.feriaint.R;

public class AdminPerfil_Activity extends AppCompatActivity {

    EditText nombre;
    EditText correo;
    EditText carrera;
    EditText twitter;
    ImageButton img_perfil;
    Button btn_img;
    Uri imgPath;
    boolean img=false;

    final int RESULT_LOAD_IMAGE=1010;
   // Usuario currentUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_perfil);

        Bundle bUsuario = getIntent().getExtras();

        nombre=(EditText) findViewById(R.id.usuario_nombre);
        correo=(EditText) findViewById(R.id.usuario_correo);
        carrera=(EditText) findViewById(R.id.usuario_carrera);
        twitter=(EditText) findViewById(R.id.usuario_twitter);
        img_perfil=(ImageButton) findViewById(R.id.img_perfil_detalle);
        btn_img=(Button) findViewById(R.id.btn_img_perfil);


        nombre.setText(MainActivity.currentUsuario.getNombre());
        correo.setText(MainActivity.currentUsuario.getCorreo());
        carrera.setText(MainActivity.currentUsuario.getCarrera());
        twitter.setText(MainActivity.currentUsuario.getTwitter());

        if ( MainActivity.currentUsuario.getImgPerfil()!=null && !MainActivity.currentUsuario.getImgPerfil().toString().isEmpty())
        {
            img=true;
            imgPath=MainActivity.currentUsuario.getImgPerfil();
        }

        esconderMostrarImg();

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
        MainActivity.currentUsuario.setCorreo(correo.getText().toString());
        MainActivity. currentUsuario.setCarrera(carrera.getText().toString());
        MainActivity.currentUsuario.setTwitter(twitter.getText().toString());

        MainActivity.currentUsuario.setImgPerfil(imgPath);

        usuarioDB.actualizar(MainActivity.currentUsuario);

        //startActivity(resultInfo);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {



          /*  Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();

*/
            imgPath = data.getData();


            Glide.with(this)
                    .load(imgPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_perfil);
            img_perfil.setVisibility(View.VISIBLE);
            img=true;

            esconderMostrarImg();

        }

    }

    public void esconderMostrarImg() {
        if (img) {
            btn_img.setText("Quitar foto de perfil");
            Glide.with(this)
                    .load(imgPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_perfil);


            btn_img.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));

        } else {
            btn_img.setText("Agregar Foto Perfil");
            img_perfil.setVisibility(View.GONE);
            btn_img.setBackgroundColor(getResources().getColor(R.color.accent));
        }

    }


    public void getGaleriaQuitarFoto(View View)
    {
        if(img)
        {
          imgPath=Uri.parse("");
          esconderMostrarImg();
        }
        else
        {
            getGaleria();
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

}
