package com.example.aplicacionsoa.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.net.HttpURLConnection;

public class ReceptorRespuestaServidorEventos extends BroadcastReceiver {

    private MvpGestionarIngresos.Presenter presenter;

    public ReceptorRespuestaServidorEventos(MvpGestionarIngresos.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getExtras().getBoolean("success");
        int codError = intent.getExtras().getInt("codigoError");
        presenter.desregistrarBroadcastEventos();
        if(!success)
        {
            String msjError = intent.getExtras().getString("msjError");
            if(codError==HttpURLConnection.HTTP_UNAUTHORIZED)
                presenter.falloTokenExpirado();
        }
        else
            presenter.registracionExitosa();
    }
}
