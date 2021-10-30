package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpVerificacion;
import com.example.aplicacionsoa.presenter.PresenterVerificacion;

public class Activity_Verificacion extends AppCompatActivity implements MvpVerificacion.View {
    private EditText numeroText;
    private EditText codSeguridad;
    private Button confirmarCod;
    private PresenterVerificacion presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        numeroText = (EditText) findViewById(R.id.PlainTextIngresoNum);
        codSeguridad = (EditText) findViewById(R.id.textPlainIngresCod);
        codSeguridad.setEnabled(false);
        confirmarCod = (Button) findViewById(R.id.confirmarCod);
        confirmarCod.setEnabled(false);
        Button enviarConfirmarCod = (Button) findViewById(R.id.enviarCodigoSeg);
        presenter = new PresenterVerificacion(this);
        presenter.calcularNivelBateria();
        presenter.pedirPermisoSMS();

        enviarConfirmarCod.setOnClickListener((V) -> {
            String telefono = numeroText.getText().toString();
            presenter.enviarSMS(telefono);
        });

        confirmarCod.setOnClickListener((V) -> {
            String codigo = codSeguridad.getText().toString();
            presenter.verificarCodigoSeguridad(codigo);
        });
}

    @Override
    public void mostrarNivelBateria(Integer porcentajeBateria) {
        Toast.makeText(getApplicationContext(), "Bateria: "+porcentajeBateria.toString()+"%", Toast.LENGTH_LONG).show();
    }

    @Override
    public void habilitarIngresoCodigoSeguridad() {
        codSeguridad.setEnabled(true);
        confirmarCod.setEnabled(true);
    }

    @Override
    public void mostrarMensaje(String msj) {
        Toast.makeText(this,msj,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void habilitarLogin() {
        Intent newIntent = new Intent(this, Activity_Login.class);
        startActivity(newIntent);
    }
}