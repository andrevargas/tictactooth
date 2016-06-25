package com.programacao.sisnet.tictactooth.domain;


import android.graphics.Color;
import android.widget.Button;

import java.util.Random;

public class Game
{
    private static Button[] buttons;
    private String turn;
    private String pressedButton;
    private String gameParameters;

    public Game(Button[] buttons) {
        Game.buttons = buttons;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getPressedButton() {
        return pressedButton;
    }

    public void setPressedButton(String pressedButton) {
        this.pressedButton = pressedButton;
    }

    public String getGameParameters() {
        return gameParameters;
    }

    public void setGameParameters(String gameParameters) {
        this.gameParameters = gameParameters;
    }

    public static boolean checkForWinner() {

        boolean win = false;
        String[] buttonTexts = new String[buttons.length];

        for (int i = 0; i < buttons.length; i++) {
            buttonTexts[i] = (String) buttons[i].getText();
        }

        //Line 1
        if (buttonTexts[0].equals(buttonTexts[1]) && buttonTexts[1].equals(buttonTexts[2]) && !buttons[0].isClickable()) {
            win = true;
        }
        //Line 2
        else if (buttonTexts[3].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[5]) && !buttons[3].isClickable()) {
            win = true;
        }
        //Line 3
        else if (buttonTexts[6].equals(buttonTexts[7]) && buttonTexts[7].equals(buttonTexts[8]) && !buttons[6].isClickable()) {
            win = true;
        }
        //Column 1
        else if (buttonTexts[0].equals(buttonTexts[3]) && buttonTexts[3].equals(buttonTexts[6]) && !buttons[0].isClickable()) {
            win = true;
        }
        //Column 2
        else if (buttonTexts[1].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[7]) && !buttons[1].isClickable()) {
            win = true;
        }
        //Column 3
        else if (buttonTexts[2].equals(buttonTexts[5]) && buttonTexts[5].equals(buttonTexts[8]) && !buttons[2].isClickable()) {
            win = true;
        }
        //Top left to bottom right diagonal
        else if (buttonTexts[0].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[8]) && !buttons[0].isClickable()) {
            win = true;
        }
        //Top right to bottom left diagonal
        else if (buttonTexts[2].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[6]) && !buttons[2].isClickable()) {
            win = true;
        }

        return win;
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

}
