package com.programacao.sisnet.tictactooth;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    Button[] bArray;
    boolean turn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);

        bArray = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        for (Button b : bArray)
        {
            b.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v)
    {
        Button b = (Button) v;

        Typeface typeface = Typeface.createFromAsset(getAssets(), "CODE Bold.otf");

        if (turn) {
            b.setTextSize(60);
            b.setTextColor(Color.rgb(255, 51, 94));
            b.setText("X");
        }
        else {
            b.setTextSize(60);
            b.setTextColor(Color.rgb(22, 151, 215));
            b.setText("O");
        }

        b.setTypeface(typeface);

        turn = !turn;

        b.setClickable(false);
        b.setBackgroundColor(Color.rgb(222, 222, 222));
        toast("Clicked");

    }

    private  void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
