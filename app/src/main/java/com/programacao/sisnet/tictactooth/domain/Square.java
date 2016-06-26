package com.programacao.sisnet.tictactooth.domain;


import android.widget.Button;

import com.programacao.sisnet.tictactooth.Biblioteca.Utils;
import com.programacao.sisnet.tictactooth.marks.Mark;
import com.programacao.sisnet.tictactooth.marks.MarkFactory;

public class Square
{
    private Button button;

    public Square(Button button) {
        this.button = button;
    }

    public Utils.eTipoFinalGame clickAction(String turn) {
        Mark mark = MarkFactory.createMark(turn);
        mark.draw(button);
        return Game.checkForWinner();
    }

}
