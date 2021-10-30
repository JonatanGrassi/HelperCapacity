package com.example.aplicacionsoa.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.aplicacionsoa.ClasesUtilitarias.Discoteca;
import com.example.aplicacionsoa.ClasesUtilitarias.Estadios;
import com.example.aplicacionsoa.ClasesUtilitarias.Local;
import com.example.aplicacionsoa.ClasesUtilitarias.Restaurant;
import com.example.aplicacionsoa.view.Activity_inicio_app;
import com.example.aplicacionsoa.view.activity_opciones_app;

import java.io.Serializable;

public class PresenterActivityInicioApp implements MvpInicioApp.Presenter{
    private Activity_inicio_app view;
    private Local localElegido;
    private String nombreLocal;
    private Double metrosCuadrados;


    public PresenterActivityInicioApp(Activity_inicio_app view)
    {
        this.view = view;
    }


    @Override
    public Boolean validarEntradas(boolean checkRestaurant, boolean checkEstadios, boolean checkDiscoteca, String nombreLocal,String metrosCuadrados) {
        boolean resultado=true;
        this.nombreLocal = nombreLocal;
        if(checkRestaurant)
            localElegido = new Restaurant();
        else if(checkEstadios)
            localElegido = new Estadios();
        else if(checkDiscoteca)
            localElegido = new Discoteca();

        if(localElegido == null)
        {
            view.errorSeleccionDeLocal("debe seleccionar un tipo de local");
            resultado = false;
        }
        if(nombreLocal.isEmpty())
        {
            view.errorNombreLocal("debe ingresar un nombre del local");
            resultado = false;
        }
        if(metrosCuadrados.equals("") || metrosCuadrados.equals("Ingrese Metros cuadrados de su local") )
        {
            view.errorMetrosCuadrados("debe ingresar los metros cuadrados de su local");
            resultado = false;
        }
        else
            this.metrosCuadrados = Double.parseDouble(metrosCuadrados);
        return  resultado;
    }

    @Override
    public void configurarIntent(Intent intent) {
        intent.putExtra("nombreLocal",this.nombreLocal);
        Bundle bundle= new Bundle();
        bundle.putSerializable("eleccion", this.localElegido);
        intent.putExtra("eleccion",bundle);
        intent.putExtra("metrosCuadrados",this.metrosCuadrados);
    }
}
