package com.programacao.sisnet.tictactooth.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.programacao.sisnet.tictactooth.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    Button[] bArray;
    boolean turn = false;
    TextView labelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        labelName = (TextView) findViewById(R.id.labelName);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        labelName.setText(name);

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
    public void onClick(View v) {

        Button b = (Button) v;

        buttonClicked(b);
    }

    public void buttonClicked(Button b) {

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

        Typeface typeface = Typeface.createFromAsset(getAssets(), "CODE Bold.otf");
        b.setTypeface(typeface);

        //Desabilita o botão após ser clicado
        b.setClickable(false);

        turn = !turn;

        b.setBackgroundColor(Color.rgb(222, 222, 222));

        CheckForWinner();

    }

    private void CheckForWinner() {

        boolean bVencedor = false;

        if(a1.getText().equals(a2.getText()) && a2.getText().equals(a3.getText()) && !a1.isClickable())
            bVencedor = true;
        else if(b1.getText().equals(b2.getText())&& b2.getText().equals(c3.getText())&& !b1.isClickable())
            bVencedor = true;
        else if(c1.getText().equals(c2.getText())&& c2.getText().equals(c3.getText())&& !c1.isClickable())
            bVencedor = true;
        else if(a1.getText().equals(b1.getText()) && b1.getText().equals(c1.getText())&& !a1.isClickable())
            bVencedor = true;
        else if(a2.getText().equals(b2.getText()) && b2.getText().equals(c2.getText()) && !a2.isClickable())
            bVencedor = true;
        else if(a3.getText().equals(b3.getText()) && b3.getText().equals(c3.getText()) && !a3.isClickable())
            bVencedor = true;
        else if(a1.getText().equals(b2.getText()) && b2.getText().equals(c3.getText()) && !a1.isClickable())
            bVencedor = true;
        else if(a3.getText().equals(b2.getText()) && b2.getText().equals(c1.getText()) && !a3.isClickable())
            bVencedor = true;

        if(bVencedor){
            if(turn){
                toast("O Wins");
            }else{
                toast("X Wins");
            }

            enableDisableAllButtons(false);
        }
    }

    private void enableDisableAllButtons(boolean enable) {
        for (Button b: bArray){
            b.setClickable(enable);

            if(enable)
                b.setBackgroundColor(Color.parseColor("#33b5e5"));
            else {
                b.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        toast("Você não pode voltar!");
    }
}
