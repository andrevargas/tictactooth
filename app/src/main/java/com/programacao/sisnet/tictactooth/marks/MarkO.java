package com.programacao.sisnet.tictactooth.marks;


import android.graphics.Color;
import android.widget.Button;

public class MarkO implements Mark {

    @Override
    public Button draw(Button button)
    {
        button.setTextColor(Color.rgb(22, 151, 215));
        button.setTextSize(60);
        button.setText("O");
        button.setBackgroundColor(Color.LTGRAY);
        button.setClickable(false);
        return button;
    }
}
