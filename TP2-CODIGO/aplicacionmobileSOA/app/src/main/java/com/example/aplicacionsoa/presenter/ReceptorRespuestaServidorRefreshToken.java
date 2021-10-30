package com.example.aplicacionsoa.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceptorRespuestaServidorRefreshToken extends BroadcastReceiver {
    private PresenterGestionIngresos presenter;

    public ReceptorRespuestaServidorRefreshToken(PresenterGestionIngresos presenter) {
        this.presenter = presenter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getExtras().getBoolean("success");
        if(success)
        {
            String token_refresh = intent.getExtras().getString("token_refresh");
            String token = intent.getExtras().getString("token");
            PresenterRegistro.token_refresh = token_refresh;
            PresenterRegistro.token = token;
            PresenterLogin.token = token;
            PresenterLogin.token = token_refresh;
            presenter.actualizarTokens(token,token_refresh);
            presenter.desregistrarBroadcastRefresh();
            presenter.reenviarRegistroEvento();
        }
        /*
        else
        {
            String msjError = intent.getExtras().getString("msjError");

        }
        */

    }
}
