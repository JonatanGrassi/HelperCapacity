package com.example.aplicacionsoa.presenter;

import org.json.JSONObject;

public interface MvpLogin_Registro {

    interface View {
        public void mostrarResultadoConexion(String cod);
        public void comunicarRequestEnProceso();
        public void iniciarAplicacion();
    }
    interface Presenter
    {
        public void iniciarServicio(JSONObject obj);
        public void configurarBroadCastReciever();
        public void liberarRecursos();
        public boolean comprobarConexion();
        public void comunicarRespuestaExitosa();
        public void comunicarRespuestaFallida(String msj);
    }
}
