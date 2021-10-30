package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpMetricas;
import com.example.aplicacionsoa.presenter.PresenterMetricas;
import java.util.Map;

public class Activity_Metricas extends AppCompatActivity implements MvpMetricas.View {

    private TableLayout tablaMetricas;
    private PresenterMetricas presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metricas);
        tablaMetricas = findViewById(R.id.tablaMetricas);
        presenter = new PresenterMetricas(this);
        presenter.cargarPreferencias();
    }

    private TableRow crearFila(Map.Entry<String, ?> entrada){
        TableRow filaTabla = new TableRow(this);
        TextView celdaNombre = crearCelda(entrada.getKey(),filaTabla);
        TextView celdaValor = crearCelda(entrada.getValue().toString(),filaTabla);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        filaTabla.addView(celdaNombre,params);
        filaTabla.addView(celdaValor,params);
        return filaTabla;
    }

    private TextView crearCelda(String texto, TableRow filaTabla){
        TextView celda = new TextView(this);
        celda.setWidth(filaTabla.getWidth());
        celda.setText(texto);
        celda.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        celda.setPadding(0,5,0,5);
        return celda;
    }

    public void dibujarFilas(Map<String, ?> entradas){
        for (Map.Entry<String, ?> entrada: entradas.entrySet()) {
            TableRow filaTabla = crearFila(entrada);
            tablaMetricas.addView(filaTabla);
        }
    }

    @Override
    public void mostrarMensaje(String msj) {
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show();
    }
}