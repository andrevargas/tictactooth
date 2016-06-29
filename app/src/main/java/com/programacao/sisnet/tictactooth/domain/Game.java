package com.programacao.sisnet.tictactooth.domain;


import android.graphics.Color;
import android.widget.Button;
import com.programacao.sisnet.tictactooth.library.Utils;

import java.util.ArrayList;
import java.util.Random;

public class Game
{
    private static Button[] buttons;
    private String turn;
    private ArrayList<Square> squares = new ArrayList<>();
    private int numberXVictories;
    private int numberCircleVictories;
    private int numberDraws;

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

    public int getNumberXVictories() {
        return numberXVictories;
    }

    public void setNumberXVictories(int numberXVictories) {
        this.numberXVictories = numberXVictories;
    }

    public int getNumberCircleVictories() {
        return numberCircleVictories;
    }

    public void setNumberCircleVictories(int numberCircleVictories) {
        this.numberCircleVictories = numberCircleVictories;
    }

    public int getNumberDraws() {
        return numberDraws;
    }

    public void setNumberDraws(int numberDraws) {
        this.numberDraws = numberDraws;
    }

    public static Utils.typeFinalGame checkForWinner() {

        Utils.typeFinalGame tipoFinalGame = Utils.typeFinalGame.resume;
        boolean end = true;
        String[] buttonTexts = new String[buttons.length];

        for (int i = 0; i < buttons.length; i++) {
            buttonTexts[i] = (String) buttons[i].getText();

            if(buttons[i].isClickable())
                end = false;
        }

        //Line 1
        if (buttonTexts[0].equals(buttonTexts[1]) && buttonTexts[1].equals(buttonTexts[2]) && !buttons[0].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Line 2
        else if (buttonTexts[3].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[5]) && !buttons[3].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Line 3
        else if (buttonTexts[6].equals(buttonTexts[7]) && buttonTexts[7].equals(buttonTexts[8]) && !buttons[6].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Column 1
        else if (buttonTexts[0].equals(buttonTexts[3]) && buttonTexts[3].equals(buttonTexts[6]) && !buttons[0].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Column 2
        else if (buttonTexts[1].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[7]) && !buttons[1].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Column 3
        else if (buttonTexts[2].equals(buttonTexts[5]) && buttonTexts[5].equals(buttonTexts[8]) && !buttons[2].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Top left to bottom right diagonal
        else if (buttonTexts[0].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[8]) && !buttons[0].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        //Top right to bottom left diagonal
        else if (buttonTexts[2].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[6]) && !buttons[2].isClickable()) {
            tipoFinalGame = Utils.typeFinalGame.win;
        }
        else if(end)
            tipoFinalGame = Utils.typeFinalGame.draw;


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

    public Square findSquareByButtonId(int id)
    {
        for (Square square : squares) {
            if (square.getButton().getId() == id) {
                return square;
            }
        }
        return null;
    }

    public void incrementScore(boolean draw)
    {
        if (draw) {
            numberDraws++;
        } else {
            if (turn.equals("X")) {
                numberXVictories++;
            }
            else if (turn.equals("O")) {
                numberCircleVictories++;
            }
        }
    }

    public void clearAll()
    {
        for (Square square : squares) {
            square.clearAction();
        }
    }

}
