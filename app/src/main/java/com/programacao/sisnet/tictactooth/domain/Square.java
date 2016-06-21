package com.programacao.sisnet.tictactooth.domain;


import android.graphics.Typeface;
import android.widget.Button;

public class Square
{
    private Button button;

    public Square(Button button) {
        this.button = button;
    }

    public void clickAction(String turn) {



    }

    private Button drawButton() {

        button.setTextSize(60);
        button.setTextColor();

        return this.button;
    }
}
