package com.programacao.sisnet.tictactooth.activities;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.programacao.sisnet.tictactooth.R;
import com.programacao.sisnet.tictactooth.Biblioteca.Utils.eTipoFinalGame;
import com.programacao.sisnet.tictactooth.bluetooth.BluetoothService;
import com.programacao.sisnet.tictactooth.domain.Game;
import com.programacao.sisnet.tictactooth.domain.Square;

public class GameActivity extends Activity implements View.OnClickListener {

    private Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    private Button[] buttons;
    private Game game;
    private String gameParameters;

    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothService bluetoothService;
    private String connectedDeviceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            toast("O Bluetooth não está disponível!");
            finish();
        }

        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);

        buttons = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        for (Button button : buttons)
        {
            button.setOnClickListener(this);
        }

        game = new Game(buttons);
        game.randomizeTurn();
    }

    @Override
    public void onClick(View view)
    {
        Button button = (Button) view;
        Square square = new Square(button);

        eTipoFinalGame result = square.clickAction(game.getTurn());

        if (result == eTipoFinalGame.vencedor) {
            toast("Vitória de \"" + game.getTurn() + "\"");
            game.disableAll();
        }
        else if (result == eTipoFinalGame.empate)
        {
            toast("Empate");
            game.disableAll();
        }

        game.switchTurns();
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        else if (bluetoothService == null) {
            setupService();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setupService();
                    toast("enois");
                }
            break;
        }
    }

    public void setupService() {
        bluetoothService = new BluetoothService(this);
    }

    @Override
    public void onBackPressed()
    {
        toast("Você não pode voltar!");
    }

}
