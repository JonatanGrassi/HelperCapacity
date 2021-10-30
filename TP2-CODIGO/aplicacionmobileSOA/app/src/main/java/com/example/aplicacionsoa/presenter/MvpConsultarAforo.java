package com.example.aplicacionsoa.presenter;

public interface MvpConsultarAforo {

    interface View {
        public void setearTipoDeLocal(String tipoLocal);
        public void setearAforo(Integer aforo);
    }
    interface Presenter
    {
        public void obtenerInformacionLocal();
        public void obtenerInformacionAforo();
        public Integer calcularCantidadPersoansPorMCuadrado();
    }
}
