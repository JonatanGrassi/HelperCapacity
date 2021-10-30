package com.example.aplicacionsoa.presenter;

import android.content.Intent;

import com.example.aplicacionsoa.view.Activity_Register;
import com.example.aplicacionsoa.view.Activity_inicio_app;

import org.json.JSONObject;

public interface MvpInicioApp {

    interface View {
        public void errorSeleccionDeLocal(String msj);
        public void errorNombreLocal(String msj);
        public void errorMetrosCuadrados(String msj);
    }
    interface Presenter
    {
        public Boolean validarEntradas(boolean checkRestaurant, boolean checkBar, boolean checkDiscoteca, String toString,String metrosCuadrados);
        public void configurarIntent(Intent intent);
    }
}
