package com.fotg.keepingcool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DavidsTips4Activity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button5;
    Button button6;
    Button button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davids_tips4);


        button1 = findViewById(R.id.circleButton1);
        button2 = findViewById(R.id.circleButton2);
        button3 = findViewById(R.id.circleButton3);
        button5 = findViewById(R.id.circleButton5);
        button6 = findViewById(R.id.circleButton6);
        button7 = findViewById(R.id.circleButton7);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips2();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips3();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips5();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips6();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips7();
            }
        });
    }

    public void openDavidsTips1() {
        Intent intent = new Intent(this, DavidsTipsActivity.class);
        startActivity(intent);
    }

    public void openDavidsTips2() {
        Intent intent = new Intent(this, DavidsTips2Activity.class);
        startActivity(intent);
    }

    public void openDavidsTips3() {
        Intent intent = new Intent(this, DavidsTips3Activity.class);
        startActivity(intent);
    }

    public void openDavidsTips5() {
        Intent intent = new Intent(this, DavidsTips5Activity.class);
        startActivity(intent);
    }

    public void openDavidsTips6() {
        Intent intent = new Intent(this, DavidsTips6Activity.class);
        startActivity(intent);
    }

    public void openDavidsTips7() {
        Intent intent = new Intent(this, DavidsTips7Activity.class);
        startActivity(intent);
    }
}
