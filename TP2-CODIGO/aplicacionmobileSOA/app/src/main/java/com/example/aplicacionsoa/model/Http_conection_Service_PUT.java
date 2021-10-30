package com.example.aplicacionsoa.model;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.aplicacionsoa.ClasesUtilitarias.Utilitarias;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http_conection_Service_PUT extends IntentService {

    public Http_conection_Service_PUT() {
        super("Http_conection_Service_PUT");
    }

    private HttpURLConnection connection;
    private URL url;
    private int respuestaServidor;
    private String Pathbroadcast;


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String uri = intent.getExtras().getString("URI");
        String token_refresh = intent.getExtras().getString("token_refresh");
        Pathbroadcast = intent.getExtras().getString("pathBroadcast");
        try {
            String result = realizarPOST(uri,token_refresh);
            ejecutarPOST(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String realizarPOST(String uri,String token_refresh)
    {
        connection = null;
        String result = null;
        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type","application/json ; charset=UTF-8");
            connection.setRequestProperty("Authorization","Bearer " + token_refresh);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            connection.connect();
            respuestaServidor = connection.getResponseCode();
            if(respuestaServidor == HttpURLConnection.HTTP_OK || respuestaServidor == HttpURLConnection.HTTP_CREATED)
            {
                InputStreamReader iSr = new InputStreamReader(connection.getInputStream());
                result= Utilitarias.convertInputStreamToString(iSr).toString();
            }
            else if(respuestaServidor == HttpURLConnection.HTTP_BAD_REQUEST )
            {
                InputStreamReader iSr = new InputStreamReader(connection.getErrorStream());
                result=Utilitarias.convertInputStreamToString(iSr).toString();
            }

            connection.disconnect();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public void ejecutarPOST(String result) throws JSONException {
        Intent i = new Intent(Pathbroadcast);
        //Deberia preguntar por result == null para evitar problemas cuando no responde el server
        JSONObject obj = new JSONObject(result);
        boolean success = obj.getBoolean("success");
        i.putExtra("success",success);
        if(respuestaServidor == HttpURLConnection.HTTP_OK || respuestaServidor == HttpURLConnection.HTTP_CREATED)
        {
            i.putExtra("token",obj.getString("token"));
            i.putExtra("token_refresh",obj.getString("token_refresh"));
        }
        else if(respuestaServidor == HttpURLConnection.HTTP_BAD_REQUEST)
        {
            i.putExtra("msjError",obj.getString("msg"));
        }
        sendBroadcast(i);
    }
}
