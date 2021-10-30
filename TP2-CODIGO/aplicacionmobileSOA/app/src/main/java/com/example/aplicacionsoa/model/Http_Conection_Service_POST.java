package com.example.aplicacionsoa.model;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.aplicacionsoa.ClasesUtilitarias.Utilitarias;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Http_Conection_Service_POST extends IntentService {

    private HttpURLConnection connection;
    private URL url;
    private int respuestaServidor;
    private String Pathbroadcast;

    public Http_Conection_Service_POST() {
        super("Http_Conection_Service_POST");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String uri = intent.getExtras().getString("URI");
        Pathbroadcast = intent.getExtras().getString("pathBroadcast");
        try {
            JSONObject obj = new JSONObject(intent.getExtras().getString("JSON"));
            String result = realizarPOST(obj,uri);
            ejecutarPOST(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String realizarPOST(JSONObject obj,String uri)
    {
        connection = null;
        String result = null;
        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json ; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            DataOutputStream dots = new DataOutputStream(connection.getOutputStream());
            dots.write(obj.toString().getBytes(StandardCharsets.UTF_8));
            dots.flush();
            connection.connect();
            respuestaServidor = connection.getResponseCode();
            if(respuestaServidor == HttpURLConnection.HTTP_OK || respuestaServidor == HttpURLConnection.HTTP_CREATED)
            {
                InputStreamReader iSr = new InputStreamReader(connection.getInputStream());
                result= Utilitarias.convertInputStreamToString(iSr).toString();
            }
            else if(respuestaServidor == HttpURLConnection.HTTP_BAD_REQUEST)
            {
                InputStreamReader iSr = new InputStreamReader(connection.getErrorStream());
                result=Utilitarias.convertInputStreamToString(iSr).toString();
            }

            dots.close();
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
            i.putExtra("token_refresh",obj.getString("token_refresh"));
            i.putExtra("token",obj.getString("token"));
        }
        else if(respuestaServidor == HttpURLConnection.HTTP_BAD_REQUEST)
        {
            i.putExtra("msjError",obj.getString("msg"));
        }
        sendBroadcast(i);
    }


}