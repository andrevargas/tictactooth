package com.programacao.sisnet.tictactooth.activities;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.programacao.sisnet.tictactooth.Biblioteca.Utils;
import com.programacao.sisnet.tictactooth.R;
import com.programacao.sisnet.tictactooth.Biblioteca.Utils.eTipoFinalGame;
import com.programacao.sisnet.tictactooth.bluetooth.BluetoothService;
import com.programacao.sisnet.tictactooth.domain.Game;
import com.programacao.sisnet.tictactooth.domain.Square;

import java.util.ArrayList;

public class GameActivity extends Activity implements View.OnClickListener {

    private Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    private Button[] buttons;
    private Button sendButton;
    private Button cancelButton;

    private Game game;

    private BluetoothService bluetoothService;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);

        sendButton = (Button) findViewById(R.id.sendButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        buttons = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        for (Button button : buttons)
        {
            button.setOnClickListener(this);
        }

        sendButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        game = new Game(buttons);
        game.randomizeTurn();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        connectDevice(getIntent());
        setupService();
    }

    @Override
    public void onClick(View view)
    {
        Button button = (Button) view;

        if (button.getId() == R.id.sendButton) {
            game.mountGameParameters();
            sendData(game.getGameParameters());
            announceWinner(Game.checkForWinner());
        }
        else if (button.getId() == R.id.cancelButton) {
            game.setPressedButton(null);
            game.findSquareByButtonId(game.getPressedButton().getId()).clearAction();
        }
        else {

            Square square = game.findSquareByButtonId(button.getId());
            square.clickAction(game.getTurn());

            game.setPressedButton(button);
            game.switchTurns();
        }
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setupService() {
        bluetoothService = new BluetoothService(this, handler);
    }

    @Override
    public void onBackPressed()
    {
        toast("Você não pode voltar!");
    }

    public void connectDevice(Intent data)
    {
        String address = data.getExtras().getString(StartActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        bluetoothService.connect(device, true);
    }

    public void sendData(String gameParameters)
    {
        if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            toast("Conexão perdida :(");
        }

        byte[] send = gameParameters.getBytes();
        bluetoothService.write(send);

    }

    public void reloadScreen() {

        String gameParameters = game.getGameParameters();

        String pA1 = gameParameters.substring(0, 1);
        String pA2 = gameParameters.substring(1, 2);
        String pA3 = gameParameters.substring(2, 3);
        String pB1 = gameParameters.substring(3, 4);
        String pB2 = gameParameters.substring(4, 5);
        String pB3 = gameParameters.substring(6, 7);
        String pC1 = gameParameters.substring(7, 8);
        String pC2 = gameParameters.substring(8, 9);
        String pC3 = gameParameters.substring(9);

        String[] params = new String[]{pA1, pA2, pA3, pB1, pB2, pB3, pC1, pC2, pC3};

        ArrayList<Square> squares = game.getSquares();

        for (int i = 0; i < params.length; i++) {
            if (params[i].equals("1")) {
                squares.get(i).clickAction("X");
            }
            else if (params[i].equals("2")) {
                squares.get(i).clickAction("O");
            }
        }

    }

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message message)
        {
            switch (message.what) {
                case 1:
                    byte[] readBuf = (byte[]) message.obj;
                    String params = new String(readBuf);
                    game.setGameParameters(params);
                    reloadScreen();
                    announceWinner(Game.checkForWinner());
                    break;
            }
        }
    };

    public void announceWinner(Utils.eTipoFinalGame result)
    {
        if(result == eTipoFinalGame.vencedor) {
            toast("Vitória de \"" + game.getTurn() + "\"!");
            finish();
        }
        else if (result == eTipoFinalGame.empate) {
            toast("Empatou!");
            finish();
        }
    }

}
