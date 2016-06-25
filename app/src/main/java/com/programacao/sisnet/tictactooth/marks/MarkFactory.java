package com.programacao.sisnet.tictactooth.marks;


public class MarkFactory {

    public static Mark mark;

    public static Mark createMark(String turn) {

        if (turn.equals("X")) {
            mark = new MarkX();
        }
        else if (turn.equals("O")) {
            mark = new MarkO();
        }

        return mark;

    }

}
