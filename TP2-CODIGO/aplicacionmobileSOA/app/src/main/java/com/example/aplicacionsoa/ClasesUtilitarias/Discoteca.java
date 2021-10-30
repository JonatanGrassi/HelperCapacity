package com.example.aplicacionsoa.ClasesUtilitarias;

import java.util.ArrayList;

public class Discoteca extends Local{

    public Discoteca() {
        super("discoteca", 50);
        ArrayList<String> recom = new ArrayList<>();
        recom.add("Abiertos hasta las 3 de la mañana");
        recom.add("Esquema de vacunación completo al menos 14 días previos al evento");
        recom.add("Test o diagnóstico negativo realizado con una antelación no mayor a 48 horas");
        recom.add("Asegurece de proveer alcohol en gel");
        recom.add("Entregue vasos junto con las bebidas que se vendan");
        recom.add("Controle el aglomeramiento en los accesos en los baños");
        recom.add("Mantener el distanciamiento social de 1,5 metros");
        recom.add("Mantener el distanciamiento social de 1,5 metros");
        recom.add("Es opcional tomar la temperatura");
        recom.add("Los que se realicen en espacios al aire libre podrán utilizar, los jóvenes podrán ingresar con una sola dosis");
        super.setRecomendaciones(recom);
    }
    //1,5 metros de distancia entre personas 1,5 x 1,5 m2 = 2.25
    @Override
    public Integer calcularAforo(Double metrosCuadrados) {
        Double aforo = (double)super.porcetajeAforo;
        return (int)(metrosCuadrados*(aforo/100)/2.25);
    }
}
