package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpLogin_Registro;
import com.example.aplicacionsoa.presenter.PresenterLogin;

import org.json.JSONObject;

public class Activity_Login extends AppCompatActivity implements MvpLogin_Registro.View {

    private EditText ingresoMail;
    private EditText ingresoPass;
    private TextView registrate;
    private Button botonIngresar;
    private PresenterLogin presenter;
    private ProgressBar progressBarLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ingresoMail = (EditText) findViewById(R.id.editTextMailLog);
        ingresoPass = (EditText) findViewById(R.id.editTextPassWord);
        botonIngresar = (Button) findViewById(R.id.button);
        registrate = (TextView) findViewById(R.id.textViewRegistrate);
        botonIngresar.setOnClickListener(HandlerLogin);
        progressBarLogin = (ProgressBar)findViewById(R.id.progressBarLogin);
        registrate.setOnClickListener(HandlerRegistrate);
        presenter = new PresenterLogin(this);

    }

    @Override
    protected void onDestroy() {
        presenter.liberarRecursos();
        super.onDestroy();
    }

    private View.OnClickListener HandlerLogin = (V) ->
    {
        presenter.configurarBroadCastReciever();
        if(presenter.comprobarConexion())
        {
            JSONObject obj = presenter.getJsonObject(ingresoMail.getText().toString(),ingresoPass.getText().toString());
            //JSONObject obj = presenter.getJsonObject("jonatangrassi22@gmail.com","hola12345");
            presenter.iniciarServicio(obj);
        }
        else
        {
            Toast.makeText(this,"sin conexion a internet.No se podra Loguear",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener HandlerRegistrate = (V) ->
    {
        Intent newIntent = new Intent(this, Activity_Register.class);
        startActivity(newIntent);
    };

    @Override
    public void mostrarResultadoConexion(String cod) {
        botonIngresar.setEnabled(true);
        progressBarLogin.setVisibility(View.INVISIBLE);
        Toast.makeText(this,cod,Toast.LENGTH_LONG).show();
    }

    @Override
    public void iniciarAplicacion() {
        Intent i = new Intent(this,Activity_inicio_app.class);
        startActivity(i);
    }

    @Override
    public void comunicarRequestEnProceso() {
        botonIngresar.setEnabled(false);
        progressBarLogin.setVisibility(View.VISIBLE);
    }
}