package com.example.aplicacionsoa.presenter;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public interface MvpOpcionesApp {

    interface View {
        public void setearNombreDeLocal(String nombre);
    }
    interface Presenter
    {
        public void configurarIntent(Intent intent);
        public void obtenerNombreLocal();
    }
}
