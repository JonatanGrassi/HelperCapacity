package com.example.aplicacionsoa.ClasesUtilitarias;

import java.util.ArrayList;

public class Restaurant extends Local {

    public Restaurant() {
        super("restaurant", 100);
        ArrayList<String> recom = new ArrayList<>();
        recom.add("Puede tener 6 personas en una mesa en el interior y 8 en el exterior");
        recom.add("Se deberá ingresar con barbijo y en caso de levantarse de la mesa deberá seguir colocárselo.");
        recom.add("Se debe proveer alcohol en gel");
        recom.add("Es opcional tomar la temperatura");
        recom.add("En los diferentes puntos de acceso estarán dispuestos voluntarios con dispositivos de soluciones sanitizantes que permitan la higiene de manos");
        super.setRecomendaciones(recom);
    }

    @Override
    public Integer calcularAforo(Double metrosCuadrados) {
        Double aforo = (double)super.porcetajeAforo;
        return (int)(metrosCuadrados*(aforo/100)/2.25);
    }
}
