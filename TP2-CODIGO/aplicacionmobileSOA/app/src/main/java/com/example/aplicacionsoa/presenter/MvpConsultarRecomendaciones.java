package com.example.aplicacionsoa.presenter;

public interface MvpConsultarRecomendaciones {
    interface View {
        public void setearTipoDeLocal(String tipoLocal);
        public void setearRecomendacion(String recomendacion);
    }
    interface Presenter
    {
        public void obtenerInformacionLocal();
        public void escucharSensor();
        public void dejarDeSensarSensores();
    }
}
