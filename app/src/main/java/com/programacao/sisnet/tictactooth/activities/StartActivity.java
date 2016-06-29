package com.programacao.sisnet.tictactooth.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.programacao.sisnet.tictactooth.R;
import com.programacao.sisnet.tictactooth.activities.GameActivity;

public class StartActivity extends Activity implements View.OnClickListener {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(gameActivity);
    }
}
