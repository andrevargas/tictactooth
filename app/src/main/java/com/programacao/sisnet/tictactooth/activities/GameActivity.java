package com.programacao.sisnet.tictactooth.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.programacao.sisnet.tictactooth.R;
import com.programacao.sisnet.tictactooth.library.Utils.typeFinalGame;
import com.programacao.sisnet.tictactooth.domain.Game;
import com.programacao.sisnet.tictactooth.domain.Square;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    private Button[] buttons;
    private Game game;

    private Button restartButton;
    private Button exitButton;

    private TextView xWins;
    private TextView circleWins;
    private TextView draws;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        xWins = (TextView) findViewById(R.id.xWins);
        circleWins = (TextView) findViewById(R.id.circleWins);
        draws = (TextView) findViewById(R.id.draws);

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

        restartButton = (Button) findViewById(R.id.restartButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        restartButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        game = new Game(buttons);
        game.randomizeTurn();
    }

    @Override
    public void onClick(View view)
    {
        Button button = (Button) view;

        if (button.getId() == R.id.restartButton) {
            game.clearAll();
        }
        else if (button.getId() == R.id.exitButton) {
            finish();
        }
        else {

            Square square = game.findSquareByButtonId(button.getId());

            typeFinalGame result = square.clickAction(game.getTurn());

            if (result == typeFinalGame.win) {
                toast("Vitória de \"" + game.getTurn() + "\"");
                game.incrementScore(false);
                updateScoreText();
                game.disableAll();
            }
            else if (result == typeFinalGame.draw)
            {
                toast("Empate");
                game.incrementScore(true);
                updateScoreText();
                game.disableAll();
            }

            game.switchTurns();
        }
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart () {
        super.onStart();
        updateScoreText();
    }

    @Override
    public void onBackPressed()
    {
        toast("Você não pode voltar!");
    }

    private void updateScoreText()
    {
        xWins.setText("X: " + game.getNumberXVictories());
        draws.setText("Empates: " + game.getNumberDraws());
        circleWins.setText("O: " + game.getNumberCircleVictories());
    }
}
