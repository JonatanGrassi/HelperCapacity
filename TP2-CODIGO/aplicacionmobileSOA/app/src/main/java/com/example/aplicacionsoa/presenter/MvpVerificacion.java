package com.example.aplicacionsoa.presenter;

public interface MvpVerificacion {
    interface View {
        void mostrarNivelBateria(Integer porcentajeBateria);
        void habilitarIngresoCodigoSeguridad();
        void mostrarMensaje(String msj);
        void habilitarLogin();
    }
    interface Presenter {
        void calcularNivelBateria();
        void enviarSMS(String telefono);
        void verificarCodigoSeguridad(String codigo);
        void pedirPermisoSMS();
    }
}
