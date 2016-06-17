package com.programacao.sisnet.tictactooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity implements View.OnClickListener {

    Button startButton;
    EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        inputName = (EditText) findViewById(R.id.inputName);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);

        gameActivity.putExtra("name", inputName.getText().toString());
        Log.e("n", inputName.getText().toString());

        startActivity(gameActivity);
    }
}
