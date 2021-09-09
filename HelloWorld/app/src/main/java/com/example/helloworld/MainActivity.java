package com.example.helloworld;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.poc_interface.CarClient;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.helloworld.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
//         comes from external library / jar
        String msg = CarClient.testCar();

//        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(msg, message);
        startActivity(intent);
    }
}