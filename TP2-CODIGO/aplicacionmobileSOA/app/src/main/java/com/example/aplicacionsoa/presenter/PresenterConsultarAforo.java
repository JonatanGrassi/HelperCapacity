package com.example.aplicacionsoa.presenter;

import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.aplicacionsoa.ClasesUtilitarias.Local;
import com.example.aplicacionsoa.view.Activity_inicio_app;
import com.example.aplicacionsoa.view.activity_consultar_aforo;
import com.example.aplicacionsoa.view.activity_opciones_app;

public class PresenterConsultarAforo implements MvpConsultarAforo.Presenter {
    private activity_consultar_aforo view;
    private Local tipoLocal;
    private Double metrosCuadrados;
    private String nombreLocal;

    public PresenterConsultarAforo(activity_consultar_aforo view)
    {
        this.view = view;
    }


    @Override
    public void obtenerInformacionLocal() {
        metrosCuadrados = view.getIntent().getExtras().getDouble("metrosCuadrados");
        Bundle bund = view.getIntent().getExtras().getBundle("eleccion");
        tipoLocal =(Local) bund.getSerializable("eleccion");
        nombreLocal = view.getIntent().getExtras().getString("nombreLocal");
        view.setearTipoDeLocal(tipoLocal.getTipoDeLocal());
    }

    @Override
    public void obtenerInformacionAforo() {
        view.setearAforo(tipoLocal.obtenerPorcetajeAforo());
    }

    @Override
    public Integer calcularCantidadPersoansPorMCuadrado() {
       return tipoLocal.calcularAforo(metrosCuadrados);
    }
}
