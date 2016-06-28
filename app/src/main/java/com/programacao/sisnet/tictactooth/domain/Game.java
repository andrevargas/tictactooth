package com.programacao.sisnet.tictactooth.domain;


import android.graphics.Color;
import android.widget.Button;
import com.programacao.sisnet.tictactooth.Biblioteca.Utils;
import com.programacao.sisnet.tictactooth.R;

import java.util.ArrayList;
import java.util.Random;

public class Game
{
    private static Button[] buttons;
    private String turn;
    private Button pressedButton = null;
    private String gameParameters = "000000000";
    private ArrayList<Square> squares = new ArrayList<>();

    public Game(Button[] buttons) {
        Game.buttons = buttons;
        for (Button button : buttons) {
            squares.add(new Square(button));
        }
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public Button getPressedButton() {
        return pressedButton;
    }

    public void setPressedButton(Button pressedButton) {
        this.pressedButton = pressedButton;
    }

    public static Utils.eTipoFinalGame checkForWinner() {

        Utils.eTipoFinalGame tipoFinalGame = Utils.eTipoFinalGame.continua;
        boolean end = true;
        String[] buttonTexts = new String[buttons.length];

        for (int i = 0; i < buttons.length; i++) {
            buttonTexts[i] = (String) buttons[i].getText();

            if(buttons[i].isClickable())
                end = false;
        }

        //Line 1
        if (buttonTexts[0].equals(buttonTexts[1]) && buttonTexts[1].equals(buttonTexts[2]) && !buttons[0].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Line 2
        else if (buttonTexts[3].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[5]) && !buttons[3].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Line 3
        else if (buttonTexts[6].equals(buttonTexts[7]) && buttonTexts[7].equals(buttonTexts[8]) && !buttons[6].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Column 1
        else if (buttonTexts[0].equals(buttonTexts[3]) && buttonTexts[3].equals(buttonTexts[6]) && !buttons[0].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Column 2
        else if (buttonTexts[1].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[7]) && !buttons[1].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Column 3
        else if (buttonTexts[2].equals(buttonTexts[5]) && buttonTexts[5].equals(buttonTexts[8]) && !buttons[2].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Top left to bottom right diagonal
        else if (buttonTexts[0].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[8]) && !buttons[0].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        //Top right to bottom left diagonal
        else if (buttonTexts[2].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[6]) && !buttons[2].isClickable()) {
            tipoFinalGame = Utils.eTipoFinalGame.vencedor;
        }
        else if(end)
            tipoFinalGame = Utils.eTipoFinalGame.empate;


        return tipoFinalGame;
    }

    public void randomizeTurn()
    {
        Random random = new Random();
        int value = random.nextInt(1);
        turn = (value == 1) ? "X" : "O";
    }

    public void switchTurns() {
        turn = (turn.equals("X")) ? "O" : "X";
    }

    public void disableAll()
    {
        for (Button button : buttons) {
            button.setBackgroundColor(Color.LTGRAY);
            button.setClickable(false);
        }
    }

    public String getGameParameters()
    {
        return gameParameters;
    }

    public void setGameParameters(String gameParameters)
    {
        this.gameParameters = gameParameters;
    }

    public void mountGameParameters()
    {
        String a1 = gameParameters.substring(0, 1);
        String a2 = gameParameters.substring(1, 2);
        String a3 = gameParameters.substring(2, 3);
        String b1 = gameParameters.substring(3, 4);
        String b2 = gameParameters.substring(4, 5);
        String b3 = gameParameters.substring(6, 7);
        String c1 = gameParameters.substring(7, 8);
        String c2 = gameParameters.substring(8, 9);
        String c3 = gameParameters.substring(9);

        String value = (turn.equals("X")) ? "1" : "2";

        switch (pressedButton.getId()) {

            case R.id.A1:
                a1 = value;
                break;
            case R.id.A2:
                a2 = value;
                break;
            case R.id.A3:
                a3 = value;
                break;
            case R.id.B1:
                b1 = value;
                break;
            case R.id.B2:
                b2 = value;
                break;
            case R.id.B3:
                b3 = value;
                break;
            case R.id.C1:
                c1 = value;
                break;
            case R.id.C2:
                c2 = value;
                break;
            case R.id.C3:
                c3 = value;
                break;
        }

        gameParameters = a1 + a2 + a3 + b1 + b2 + b3 + c1 + c2 + c3;

    }

    public Square findSquareByButtonId(int id)
    {
        for (Square square : squares) {
            if (square.getButton().getId() == id) {
                return square;
            }
        }
        return null;
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

}
