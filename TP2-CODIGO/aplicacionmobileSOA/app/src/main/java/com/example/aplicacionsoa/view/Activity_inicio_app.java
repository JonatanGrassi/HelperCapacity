package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpInicioApp;
import com.example.aplicacionsoa.presenter.PresenterActivityInicioApp;

public class Activity_inicio_app extends AppCompatActivity implements MvpInicioApp.View {

    private RadioButton radRestaurante;
    private EditText nombreLocal;
    private EditText metrosCuadrado;
    private RadioButton radEstadios;
    private RadioButton radDiscoteca;
    private Button botonAceptar;
    private MvpInicioApp.Presenter presenter ;
    private ImageView ayudaMetrosCuadrados;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        nombreLocal = (EditText) findViewById(R.id.editTextNombreLocal);
        metrosCuadrado = (EditText) findViewById(R.id.editTextMetrosCuadrados);
        ayudaMetrosCuadrados = (ImageView) findViewById(R.id.imageViewAyuda);
        radRestaurante = (RadioButton) findViewById(R.id.radButtonRestaurante);
        radDiscoteca = (RadioButton) findViewById(R.id.radButtonDiscoteca);
        radEstadios = (RadioButton) findViewById(R.id.radButtonEstadios);
        botonAceptar = (Button) findViewById(R.id.buttonAceptar);
        botonAceptar.setOnClickListener(HandlerAceptar);
        ayudaMetrosCuadrados.setOnClickListener(handlerToolTip);
        presenter = new PresenterActivityInicioApp(this);
    }

    private View.OnClickListener HandlerAceptar = (V) ->
    {
       if(presenter.validarEntradas(radRestaurante.isChecked(), radEstadios.isChecked(),radDiscoteca.isChecked(),nombreLocal.getText().toString(),metrosCuadrado.getText().toString()))
       {
           Intent intentOpcionesApp = new Intent(this,activity_opciones_app.class);
           presenter.configurarIntent(intentOpcionesApp);
           startActivity(intentOpcionesApp);
       }
    };

    private View.OnClickListener handlerToolTip = (V) ->
    {
       Toast.makeText(this,"Es la superficie que se considera transitable en su establecimiento",Toast.LENGTH_LONG).show();

    };

    @Override
    public void errorSeleccionDeLocal(String msj) {
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorNombreLocal(String msj) {
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorMetrosCuadrados(String msj) {
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show();
    }
}