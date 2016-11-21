package edu.udem.feriaint.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;

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

        nombre.setText(MainActivity.currentUsuario.getNombre());
        correo.setText(MainActivity.currentUsuario.getCorreo());
        carrera.setText(MainActivity.currentUsuario.getCarrera());
        twitter.setText(MainActivity.currentUsuario.getTwitter());
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

        usuarioDB.actualizar(MainActivity.currentUsuario);

        /*
        resultInfo.putExtra("nombre",nombre.getText().toString());
        resultInfo.putExtra("correo",correo.getText().toString());
        resultInfo.putExtra("carrera",carrera.getText().toString());
        resultInfo.putExtra("twitter",twitter.getText().toString());

        resultInfo.putString("nombre",nombre.getText().toString());
        resultInfo.putString("correo",correo.getText().toString());
        resultInfo.putString("carrera",carrera.getText().toString());
        resultInfo.putString("twitter",twitter.getText().toString());

        */
        //startActivity(resultInfo);
        finish();
    }

}
