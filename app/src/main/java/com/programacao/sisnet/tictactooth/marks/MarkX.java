package com.programacao.sisnet.tictactooth.marks;

import android.graphics.Color;
import android.widget.Button;

public class MarkX implements Mark
{
    @Override
    public Button draw(Button button)
    {
        button.setTextColor(Color.rgb(255, 51, 94));
        button.setTextSize(60);
        button.setText("X");
        button.setBackgroundColor(Color.LTGRAY);
        button.setClickable(false);
        return button;
    }
}
