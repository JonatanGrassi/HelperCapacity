package com.example.aplicacionsoa.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpGestionarIngresos;
import com.example.aplicacionsoa.presenter.PresenterGestionIngresos;

public class activity_gestionarIngresos extends AppCompatActivity implements MvpGestionarIngresos.View {

    private ImageButton agregar;
    private ImageButton limpiar;
    private TextView cantidadPersonas;
    private ProgressBar capacidad;
    private TextView tipoLocal;
    private ImageButton sacar;
    private MvpGestionarIngresos.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ingresos);
        agregar = (ImageButton) findViewById(R.id.imageButtonAgregar);
        limpiar = (ImageButton) findViewById(R.id.imageButtonLimpiar);
        sacar = (ImageButton) findViewById(R.id.imageButtonSacar);
        capacidad = (ProgressBar) findViewById(R.id.progressBarAforo);
        tipoLocal = (TextView) findViewById(R.id.textViewTipoEstableci);
        cantidadPersonas = (TextView) findViewById(R.id.textViewContadorPersonas);
        agregar.setOnClickListener(HandlerAgregar);
        limpiar.setOnClickListener(HandlerLimpiar);
        sacar.setOnClickListener(HandlerSacar);
        presenter = new PresenterGestionIngresos(this,(SensorManager) getSystemService(SENSOR_SERVICE));
        presenter.obtenerInformacionLocal();
    }

    private View.OnClickListener HandlerAgregar = (V) ->
    {
        presenter.aumentarContador();
    };

    private View.OnClickListener HandlerLimpiar = (V) ->
    {
        presenter.resetearContador();
    };

    private View.OnClickListener HandlerSacar = (V) ->
    {
        presenter.restarContador();
    };

    @Override
    protected void onResume() {
        presenter.escucharSensor();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        presenter.escucharSensor();
        super.onRestart();
    }


    @Override
    protected void onPause() {
        presenter.dejarDeSensarSensores();
        super.onPause();
    }

    @Override
    public void setearTipoDeLocal(String tipoLocal) {
        this.tipoLocal.setText(tipoLocal);
    }

    @Override
    public void actualizarTextViewContador(String cantidad) {
        cantidadPersonas.setText(cantidad);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void actualizarProgressBar(Integer cantidad) {
        capacidad.setProgress(cantidad,true);

    }

    @Override
    public void notificarCapacidadTotal() {
        Toast.makeText(this,"Capacidad Maxima Alcanzada",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setearProgressBar(Integer maxValue) {
        capacidad.setMax(maxValue);
    }

    @Override
    public void inhabilitarBotones() {
        agregar.setEnabled(false);
        limpiar.setEnabled(false);
        sacar.setEnabled(false);
    }

    @Override
    public void habilitarBotones() {
        agregar.setEnabled(true);
        limpiar.setEnabled(true);
        sacar.setEnabled(true);
    }

    @Override
    public void mostrarRegistracionEventoExitosa() {
        Toast.makeText(this,"Evento [Detecto Sensor] se ha registrado en el servidor",Toast.LENGTH_LONG).show();
    }
}