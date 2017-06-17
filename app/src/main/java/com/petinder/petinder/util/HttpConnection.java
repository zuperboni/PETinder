package com.petinder.petinder.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Mônica on 15/06/2017.
 */

public class HttpConnection {

    public static String getSetDataWeb(String url, String method, String data) {
        String answer = "";

        try {
            URL endereco = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) endereco.openConnection();

            conn.setRequestMethod("POST");
            conn.setReadTimeout(10 * 1000);
            conn.setConnectTimeout(10 * 1000);
            //was 30*1000 - changed 01/04/2016
            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();


            //Enviar
            String urlParameters =
                    "method=" + URLEncoder.encode(method, "UTF-8") +
                            "&json=" + URLEncoder.encode(data, "UTF-8");

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Receber
            int resposta = conn.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                answer = bytesToString(is);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return answer;
    }

    public static String bytesToString(InputStream is) throws IOException {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bigBuffer = new ByteArrayOutputStream();
        int bytesLidos;
        while ((bytesLidos = is.read(buffer)) != -1) {
            bigBuffer.write(buffer, 0, bytesLidos);
        }
        return new String(bigBuffer.toByteArray(), "UTF-8");
    }


    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
