package com.example.aplicacionsoa.presenter;

import java.util.Map;

public interface MvpMetricas {
    interface View {
        void dibujarFilas(Map<String, ?> entradas);
        void mostrarMensaje(String msj);
    }
    interface Presenter {
        void cargarPreferencias();
    }
}
