package com.example.aplicacionsoa.model;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;



public class PreferenciasCompartidas {
    private SharedPreferences preferences;

    public PreferenciasCompartidas(Activity activity, String key) {
        preferences = activity.getApplicationContext().getSharedPreferences(key,activity.MODE_PRIVATE);
    }

    public void guardarSesionFallida() {
        Calendar calendario = new GregorianCalendar();
        SharedPreferences.Editor editor = preferences.edit();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        String key = null;
        if(hora<6)
            key = "Login fallidos 0hs a 6hs";
        if(hora>=6 && hora<12)
            key = "Login fallidos 6hs a 12hs";
        if(hora>=12 && hora<18)
            key = "Login fallidos 12hs a 18hs";
        if(hora>=18)
            key = "Login fallidos 18hs a 24hs";
        int cantidadIniciosFallidos = preferences.getInt(key,0);
        cantidadIniciosFallidos++;
        editor.putInt(key,cantidadIniciosFallidos);
        editor.apply();
    }

    public void guardarRegistro(){
        Calendar calendario = new GregorianCalendar();
        SharedPreferences.Editor editor = preferences.edit();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        String key = hora < 12 ? "Registros exitosos 0hs a 12hs" : "Registros exitosos 12hs a 24hs";
        int cantRegistros = preferences.getInt(key,0);
        cantRegistros++;
        editor.putInt(key,cantRegistros);
        editor.apply();
    }

    public Map<String,?> obtenerMetricas(){
        Map<String, ?> entradas = preferences.getAll();
        return entradas;
    }
}


