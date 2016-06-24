package com.programacao.sisnet.tictactooth.domain;


import android.widget.Button;

public class GameValidation {

    public static boolean checkForWinner(Button[] buttons) {

        boolean win = false;
        String[] buttonTexts = new String[buttons.length];

        for (int i = 0; i < buttons.length; i++) {
            buttonTexts[i] = (String) buttons[i].getText();
        }

        //Line 1
        if (buttonTexts[0].equals(buttonTexts[1]) && buttonTexts[1].equals(buttonTexts[2])) {
            win = true;
        }
        //Line 2
        else if (buttonTexts[3].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[5])) {
            win = true;
        }
        //Line 3
        else if (buttonTexts[6].equals(buttonTexts[7]) && buttonTexts[7].equals(buttonTexts[8])) {
            win = true;
        }
        //Column 1
        else if (buttonTexts[0].equals(buttonTexts[3]) && buttonTexts[3].equals(buttonTexts[6])) {
            win = true;
        }
        //Column 2
        else if (buttonTexts[1].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[7])) {
            win = true;
        }
        //Column 3
        else if (buttonTexts[2].equals(buttonTexts[5]) && buttonTexts[5].equals(buttonTexts[8])) {
            win = true;
        }
        //Top left to bottom right diagonal
        else if (buttonTexts[0].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[8])) {
            win = true;
        }
        //Top right to bottom left diagonal
        else if (buttonTexts[2].equals(buttonTexts[4]) && buttonTexts[4].equals(buttonTexts[6])) {
            win = true;
        }

        return win;
    }

}
