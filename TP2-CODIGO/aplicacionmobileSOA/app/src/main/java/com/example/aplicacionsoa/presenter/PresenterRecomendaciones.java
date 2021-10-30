package com.example.aplicacionsoa.presenter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.aplicacionsoa.ClasesUtilitarias.Local;
import com.example.aplicacionsoa.view.activity_consultar_recomendaciones;

import java.util.ArrayList;

public class PresenterRecomendaciones implements MvpConsultarRecomendaciones.Presenter, SensorEventListener {
    private Local tipoLocal;
    private Integer indice =0;
    private SensorManager sensorManager;
    private activity_consultar_recomendaciones view;
    private ArrayList<String> recomendaciones;

    public PresenterRecomendaciones(activity_consultar_recomendaciones view,SensorManager sensorManager)
    {
        this.view = view;
        this.sensorManager = sensorManager;
    }

    @Override
    public void obtenerInformacionLocal() {
        Bundle bund = view.getIntent().getExtras().getBundle("eleccion");
        tipoLocal =(Local) bund.getSerializable("eleccion");
        view.setearTipoDeLocal(tipoLocal.getTipoDeLocal());
        recomendaciones = tipoLocal.getRecomendaciones();
        view.setearRecomendacion(recomendaciones.get(indice));
        indice++;
    }

    @Override
    public void escucharSensor() {
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void dejarDeSensarSensores() {
        sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((event.values[0] > 16) || (event.values[1] > 25) || (event.values[2] > 16))
        {
            if(indice < recomendaciones.size()) {
                view.setearRecomendacion(recomendaciones.get(indice));
                indice++;
            }
            else
                indice=0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
