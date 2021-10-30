package com.example.aplicacionsoa.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

public interface MvpGestionarIngresos {
    interface View {
        public void setearTipoDeLocal(String tipoLocal);
        public void actualizarTextViewContador(String cantidad);
        public void actualizarProgressBar(Integer cantidad);
        public void notificarCapacidadTotal();
        public void setearProgressBar(Integer maxValue);
        public void inhabilitarBotones();
        public void habilitarBotones();
        public void mostrarRegistracionEventoExitosa();
    }
    interface Presenter
    {
        public void obtenerInformacionLocal();
        public void resetearContador();
        public void aumentarContador();
        public void restarContador();
        public void escucharSensor();
        public void dejarDeSensarSensores();
        public void falloTokenExpirado();
        public void desregistrarBroadcastEventos();
        public void desregistrarBroadcastRefresh();
        public void reenviarRegistroEvento();
        public void registracionExitosa();
        public void actualizarTokens(String token,String token_refresh);
    }
}
