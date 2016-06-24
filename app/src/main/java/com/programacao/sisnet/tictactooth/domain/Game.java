package com.programacao.sisnet.tictactooth.domain;


import android.widget.Button;

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

    public static Button[] getButtons() {
        return Game.buttons;
    }
}
