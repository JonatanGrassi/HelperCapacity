package com.example.aplicacionsoa.presenter;

import com.example.aplicacionsoa.model.PreferenciasCompartidas;
import com.example.aplicacionsoa.view.Activity_Metricas;

import java.util.Map;

public class PresenterMetricas implements MvpMetricas.Presenter{

    private final Activity_Metricas activity_metricas;
    private final PreferenciasCompartidas preferencias;
    private final String MSJ_SIN_PREFERENCIAS = "No hay metricas registradas. Consulte el manual";

    public PresenterMetricas(Activity_Metricas activity_metricas) {
        this.activity_metricas = activity_metricas;
        preferencias = new PreferenciasCompartidas(activity_metricas,"METRICAS");
    }


    @Override
    public void cargarPreferencias() {
        Map<String,?> entradas = preferencias.obtenerMetricas();
        if(entradas.isEmpty()){
            activity_metricas.mostrarMensaje(MSJ_SIN_PREFERENCIAS);
        }
        activity_metricas.dibujarFilas(entradas);
    }
}
