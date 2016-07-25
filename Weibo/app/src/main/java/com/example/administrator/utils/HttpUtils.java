package com.example.administrator.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/25.
 */
public class HttpUtils {
    public static final int TIMEOUT=3000;
    public static final int READOUT=5000;
    public static String setUrl(String baseUrl,String function){
        return baseUrl+function;
    }


    public static String get(String url,String key,String value){
        String http=url+"?"+key+"="+value;
        Log.i("http", http);
        StringBuilder result=new StringBuilder();
        try {
            URL mUrl=new URL(http);

            HttpURLConnection httpURLConnection=(HttpURLConnection)mUrl.openConnection();
            httpURLConnection.setConnectTimeout(TIMEOUT);
            httpURLConnection.setReadTimeout(READOUT);
            httpURLConnection.setDoInput(true);

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.connect();
            Log.i("response", httpURLConnection.getResponseCode()+"");
            if(httpURLConnection.getResponseCode()==200){
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String line=bufferedReader.readLine();
                while (line!=null&&line.length()>0){
                    result.append(line);
                    line=bufferedReader.readLine();
                }

                inputStream.close();
                bufferedReader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }

        return result.toString();
    }
}
