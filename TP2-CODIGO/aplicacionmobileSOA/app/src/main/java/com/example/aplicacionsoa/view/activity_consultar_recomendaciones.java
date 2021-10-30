package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpConsultarRecomendaciones;
import com.example.aplicacionsoa.presenter.PresenterRecomendaciones;

public class activity_consultar_recomendaciones extends AppCompatActivity implements MvpConsultarRecomendaciones.View {
    private MvpConsultarRecomendaciones.Presenter presenter;
    private TextView tipoLocal;
    private TextView recomendaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_recomendaciones);
        tipoLocal = (TextView) findViewById(R.id.textViewTipoLocal);
        recomendaciones = (TextView) findViewById(R.id.textViewRecomendaciones);
        presenter = new PresenterRecomendaciones(this,(SensorManager) getSystemService(SENSOR_SERVICE));
        presenter.obtenerInformacionLocal();
    }

    @Override
    public void setearTipoDeLocal(String tipoLocal) {
        this.tipoLocal.setText(tipoLocal);
    }

    @Override
    public void setearRecomendacion(String recomendacion) {
        recomendaciones.setText(recomendacion);
    }

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
}