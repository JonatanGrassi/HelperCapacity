package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aplicacionsoa.presenter.PresenterRegistro;
import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpLogin_Registro;

import org.json.JSONObject;

public class Activity_Register extends AppCompatActivity implements MvpLogin_Registro.View {
    private EditText nombre;
    private EditText apellido;
    private EditText dni;
    private EditText mail;
    private EditText ambiente;
    private EditText comision;
    private EditText grupo;
    private EditText contraseña;
    private Button enviarInformacionDeRegistro;
    private ProgressBar progressRegistrar;
    private PresenterRegistro presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nombre = (EditText) findViewById(R.id.editTextNombre);
        apellido = (EditText) findViewById(R.id.editTextApellido);
        mail = (EditText) findViewById(R.id.editTextEmail);
        dni = (EditText) findViewById(R.id.editTextDNI);
        ambiente = (EditText) findViewById(R.id.editTextAmbiente);
        comision = (EditText) findViewById(R.id.editTextComision);
        grupo = (EditText) findViewById(R.id.editTextGrupo);
        contraseña = (EditText) findViewById(R.id.editTextPassword);
        enviarInformacionDeRegistro = (Button) findViewById(R.id.buttonRegistrar);
        progressRegistrar = (ProgressBar)findViewById(R.id.progressRegistrar);
        enviarInformacionDeRegistro.setOnClickListener(HandlerRegistro);
        presenter = new PresenterRegistro(this);

    }

    @Override
    protected void onDestroy() {
        presenter.liberarRecursos();
        super.onDestroy();
    }


    private View.OnClickListener HandlerRegistro = (V) ->
    {
        presenter.configurarBroadCastReciever();
        if(presenter.comprobarConexion())
        {

        JSONObject obj = presenter.getJsonObject(ambiente.getText().toString(),nombre.getText().toString(),apellido.getText().toString()
                ,mail.getText().toString(),dni.getText().toString(),contraseña.getText().toString(),comision.getText().toString()
                ,grupo.getText().toString());

            /*
            JSONObject obj = presenter.getJsonObject("TEST","jonatan","grassi"
                    ,"jonatangrassi22@gmail.com","40077893","12342131234","2900"
                    ,"1");
    */
        presenter.iniciarServicio(obj);
        }
        else
        {
            Toast.makeText(this,"sin conexion a internet.Su cuenta no se registrara",Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void mostrarResultadoConexion(String msj) {
        enviarInformacionDeRegistro.setEnabled(true);
        progressRegistrar.setVisibility(View.INVISIBLE);
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show();
    }

    @Override
    public void iniciarAplicacion() {
        Intent i = new Intent(this,Activity_inicio_app.class);
        startActivity(i);
    }

    @Override
    public void comunicarRequestEnProceso() {
        enviarInformacionDeRegistro.setEnabled(false);
        progressRegistrar.setVisibility(View.VISIBLE);
    }
}