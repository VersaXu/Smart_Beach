package com.example.myhelloworldapplication;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
public class runmap1 extends AppCompatActivity {

    Button btn_print1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runmap1);


        btn_print1= (Button) findViewById(R.id.getmap2);

        btn_print1.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                Intent intent = new Intent(runmap1.this, runmap2.class);
                startActivity(intent);

            }
        });
    }
}