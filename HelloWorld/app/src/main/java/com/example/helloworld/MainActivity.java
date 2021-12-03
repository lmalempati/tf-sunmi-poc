package com.example.helloworld;


import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import fdrc.base.Request;
import fdrc.base.Response;
import fdrc.client.Client;
import fdrc.utils.JsonBuilder;


//import fdrc.client.Client;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.helloworld.MESSAGE";

    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        System.setProperty("java.awt.headless", "true");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSend = findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
//         comes from external library / jar
//        String result = BookMain.JacksonSample();
//        System.out.println(result);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Client client = new Client();
                    Request request = JsonBuilder.getRequestFromJsonString(getAssetJsonData(getApplicationContext()));
                    Response response = client.processRequest(request);
                    System.out.println();
                    response = client.processRequest(request);
                    System.out.println(response.respCode + response.errorMsg);
                    response = client.processRequest(request);
                    System.out.println(response.respCode + response.errorMsg);
                    response = client.processRequest(request);
                    System.out.println(response.respCode + response.errorMsg);
                    Looper.prepare();
                    Context context = getApplicationContext();
                    CharSequence text = response.respCode + response.addtlRespData;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
//        BookMain.jaxbSample();
//        BookMain.XmlEncoderSample();
//        BookMain.Xstream();
//        SimpleSerializer.toXml();


//        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_LONG).show();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        intent.putExtra(msg, msg);
//        startActivity(intent);
    }

    public static String getAssetJsonData(Context context) {
        String json = null;
        try {
            InputStream is = null;
            try {
                is = context.getAssets().open("payload.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }
}