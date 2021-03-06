package com.programacao.sisnet.tictactooth.domain;


import android.graphics.Color;
import android.widget.Button;

import com.programacao.sisnet.tictactooth.library.Utils;
import com.programacao.sisnet.tictactooth.marks.Mark;
import com.programacao.sisnet.tictactooth.marks.MarkFactory;

public class Square
{
    private Button button;

    public Square(Button button) {
        this.button = button;
    }

    public Utils.typeFinalGame clickAction(String turn) {
        Mark mark = MarkFactory.createMark(turn);
        mark.draw(button);
        return Game.checkForWinner();
    }

    public void clearAction() {
        button.setBackgroundColor(Color.parseColor("#eeeeee"));
        button.setText(null);
        button.setClickable(true);
    }

    public Button getButton() {
        return button;
    }

}
