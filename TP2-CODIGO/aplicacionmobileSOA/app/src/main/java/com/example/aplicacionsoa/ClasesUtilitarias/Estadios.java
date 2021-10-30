package com.example.aplicacionsoa.ClasesUtilitarias;

import java.util.ArrayList;
import java.util.Arrays;

public class Estadios extends Local{


    public Estadios()
    {
        super("Estadios",50);
        ArrayList<String> recom = new ArrayList<>();
        recom.add("Los mayores de 18 años deben tener al menos una dosis de cualquier vacuna contra el coronavirus");
        recom.add("Se dispondrá de anillos de ingreso con corredores establecidos que permitan su ordenamiento");
        recom.add("En los diferentes puntos de acceso estarán dispuestos voluntarios con dispositivos de soluciones sanitizantes");
        recom.add("Las personas o grupos deberán respetar el distanciamiento físico");
        super.setRecomendaciones(recom);
    }

    @Override
    public Integer calcularAforo(Double metrosCuadrados) {
        Double aforo = (double)super.porcetajeAforo;
        return (int)(metrosCuadrados*(aforo/100)/2.25);
    }
}
