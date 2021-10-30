package com.example.aplicacionsoa.presenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacionsoa.ClasesUtilitarias.Local;
import com.example.aplicacionsoa.view.Activity_inicio_app;
import com.example.aplicacionsoa.view.activity_opciones_app;

public class PresenterActivityOpcionesApp implements MvpOpcionesApp.Presenter{
    private Bundle tipoLocal;
    private Double metrosCuadrados;
    private String nombreLocal;
    private activity_opciones_app view;

    public PresenterActivityOpcionesApp(activity_opciones_app view) {
        this.view = view;
    }

    @Override
    public void configurarIntent(Intent intent) {
        intent.putExtra("metrosCuadrados",metrosCuadrados);
        intent.putExtra("eleccion",tipoLocal);
        intent.putExtra("nombreLocal",nombreLocal);
    }

    @Override
    public void obtenerNombreLocal() {
        metrosCuadrados = view.getIntent().getExtras().getDouble("metrosCuadrados");
        tipoLocal = view.getIntent().getExtras().getBundle("eleccion");
        nombreLocal = view.getIntent().getExtras().getString("nombreLocal");
        view.setearNombreDeLocal(nombreLocal);
    }

}
