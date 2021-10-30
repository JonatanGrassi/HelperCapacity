package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpConsultarAforo;
import com.example.aplicacionsoa.presenter.PresenterConsultarAforo;

import org.w3c.dom.Text;

public class activity_consultar_aforo extends AppCompatActivity implements MvpConsultarAforo.View {

    private MvpConsultarAforo.Presenter presenter;
    private TextView tipoDeLocal;
    private TextView capacidadTotal;
    private TextView aforo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_aforo);
        tipoDeLocal = (TextView) findViewById(R.id.textViewTipoEstablecimiento);
        capacidadTotal = (TextView) findViewById(R.id.textViewCapacidadTotal);
        capacidadTotal.setEnabled(false);
        aforo = (TextView) findViewById(R.id.textViewAforo);
        presenter = new PresenterConsultarAforo(this);
        presenter.obtenerInformacionLocal();
        presenter.obtenerInformacionAforo();
    }

    @Override
    public void setearTipoDeLocal(String tipoLocal) {
        tipoDeLocal.setText(tipoLocal);
    }

    @Override
    public void setearAforo(Integer aforo) {
        this.aforo.setText(aforo.toString() + "%");
        capacidadTotal.setEnabled(true);
        if(aforo != 100)
        {
           capacidadTotal.setText(presenter.calcularCantidadPersoansPorMCuadrado().toString() +" Personas");
        }

    }
}