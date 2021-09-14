package com.example.helloworld;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lmn.learning.CarClient;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.helloworld.MESSAGE";

    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public void sendMessage(){
      //  Intent intent = new Intent(this, DisplayMessageActivity.class);

//         comes from external library / jar
        String msg = CarClient.testCar();

        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_LONG).show();

//        intent.putExtra(EXTRA_MESSAGE, message);
//        intent.putExtra(msg, msg);
//        startActivity(intent);
    }
}