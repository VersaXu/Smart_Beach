package com.example.myhelloworldapplication;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;

public class runmap2 extends AppCompatActivity {

    Button btn_print2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runmap2);


        btn_print2 = (Button) findViewById(R.id.getmap1);

        btn_print2.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                Intent intent = new Intent(runmap2.this, runmap1.class);
                startActivity(intent);

            }
        });
    }
}