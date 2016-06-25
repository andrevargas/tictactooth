package com.programacao.sisnet.tictactooth.activities;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.programacao.sisnet.tictactooth.R;
import com.programacao.sisnet.tictactooth.domain.Game;
import com.programacao.sisnet.tictactooth.domain.Square;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    Button[] buttons;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);

        buttons = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        for (Button button : buttons)
        {
            button.setOnClickListener(this);
        }

        game = new Game(buttons);
        game.randomizeTurn();
    }

    @Override
    public void onClick(View view)
    {
        Button button = (Button) view;
        Square square = new Square(button);

        boolean result = square.clickAction(game.getTurn());

        if (result) {
            toast("Vitória de \"" + game.getTurn() + "\"");
            game.disableAll();
        }

        game.switchTurns();
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
