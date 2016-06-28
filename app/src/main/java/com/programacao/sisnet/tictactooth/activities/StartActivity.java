package com.programacao.sisnet.tictactooth.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.programacao.sisnet.tictactooth.R;

import java.util.Set;

public class StartActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter bluetoothAdapter;

    public static String EXTRA_DEVICE_ADDRESS = "device_name";

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "O Bluetooth não está disponível!", Toast.LENGTH_SHORT).show();
            finish();
        }

        ArrayAdapter<String> pairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        ListView devicesList = (ListView) findViewById(R.id.paired_devices);

        devicesList.setAdapter(pairedDevicesArrayAdapter);
        devicesList.setOnItemClickListener(deviceClickListener);

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        else {
            pairedDevicesArrayAdapter.add("Nenhum dispositivo pareado");
        }

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);

        if (!gameActivity.getStringExtra(EXTRA_DEVICE_ADDRESS).isEmpty()) {
            startActivity(gameActivity);
        }
        else {
            Toast.makeText(getApplicationContext(), "Selecione um dispositivo!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Você precisa do Bluetooth para jogar!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private AdapterView.OnItemClickListener deviceClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length(), -17);

            Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
            gameActivity.putExtra(EXTRA_DEVICE_ADDRESS, address);

        }
    };
}
