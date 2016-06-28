package com.programacao.sisnet.tictactooth.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import com.programacao.sisnet.tictactooth.activities.GameActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {

    private static final String NAME_SECURE = "BluetoothGameSecure";
    private static final String NAME_INSECURE = "BluetoothGameInsecure";

    private static final UUID UUID_SECURE = UUID.fromString("b4a2bde0-3be6-11e6-bdf4-0800200c9a66");
    private static final UUID UUID_INSECURE = UUID.fromString("c21d3630-3be6-11e6-bdf4-0800200c9a66");

    private final BluetoothAdapter bluetoothAdapter;
    private final Handler handler;
    private AcceptThread secureAcceptThread;
    private AcceptThread insecureAcceptThread;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private int state;

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;

    public BluetoothService(Context context, Handler handler) {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.state = STATE_NONE;
        this.handler = handler;
    }

    public synchronized void setState(int state)
    {
        this.state = state;
    }

    public synchronized int getState()
    {
        return state;
    }

    public synchronized void start()
    {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        setState(STATE_LISTEN);

        if (secureAcceptThread == null) {
            secureAcceptThread = new AcceptThread(true);
            secureAcceptThread.start();
        }

        if (insecureAcceptThread == null) {
            insecureAcceptThread = new AcceptThread(false);
            insecureAcceptThread.start();
        }
    }

    public synchronized void connect(BluetoothDevice device, boolean secure)
    {
        if (state == STATE_CONNECTING) {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
            }
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        connectThread = new ConnectThread(device, secure);
        connectThread.start();
        setState(STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType)
    {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (secureAcceptThread != null) {
            secureAcceptThread.cancel();
            secureAcceptThread = null;
        }
        if (insecureAcceptThread != null) {
            insecureAcceptThread.cancel();
            insecureAcceptThread = null;
        }

        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

        setState(STATE_CONNECTED);
    }

    public synchronized void stop()
    {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (secureAcceptThread != null) {
            secureAcceptThread.cancel();
            secureAcceptThread = null;
        }

        if (insecureAcceptThread != null) {
            insecureAcceptThread.cancel();
            insecureAcceptThread = null;
        }

        setState(STATE_NONE);
    }

    public void write(byte[] out)
    {
        ConnectedThread thread = null;
        synchronized (this) {
            if (state != STATE_CONNECTED) {
                thread = connectedThread;
            }
        }
        thread.write(out);
    }

    public void connectionFailed() {
        BluetoothService.this.start();
    }

    public void connectionLost() {
        BluetoothService.this.start();
    }

    private class AcceptThread extends Thread {

        private final BluetoothServerSocket serverSocket;
        private String socketType;

        public AcceptThread(boolean secure)
        {
            BluetoothServerSocket temp = null;
            socketType = secure ? "Secure" : "Insecure";

            try {
                if (secure) {
                    temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, UUID_SECURE);
                }
                else {
                    temp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, UUID_INSECURE);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            serverSocket = temp;
        }

        public void run()
        {
            setName("AcceptThread" + socketType);
            BluetoothSocket socket = null;

            while (state != STATE_CONNECTED) {

                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }

                if (socket != null) {

                    synchronized (BluetoothService.this) {

                        switch (state) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                connected(socket, socket.getRemoteDevice(), socketType);
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                }
            }
        }

        public void cancel()
        {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.getMessage();
                e.printStackTrace();
            }
        }
    }

    private class ConnectThread extends Thread {

        private final BluetoothSocket socket;
        private final BluetoothDevice device;
        private String socketType;

        public ConnectThread(BluetoothDevice device, boolean secure)
        {
            this.device = device;
            BluetoothSocket temp = null;

            try {
                if (secure) {
                    temp = device.createRfcommSocketToServiceRecord(UUID_SECURE);
                }
                else {
                    temp = device.createInsecureRfcommSocketToServiceRecord(UUID_INSECURE);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            socket = temp;
        }

        public void run()
        {
            setName("ConnectThread" + socketType);
            bluetoothAdapter.cancelDiscovery();

            try {
                socket.connect();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException e2) {
                    System.out.println(e2.getMessage());
                    e2.printStackTrace();
                }
                connectionFailed();
                return;
            }

            synchronized (BluetoothService.this) {
                connectedThread = null;
            }

            connected(socket, device, socketType);
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.printf(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private class ConnectedThread extends Thread {

        private final BluetoothSocket socket;
        private final InputStream input;
        private final OutputStream output;

        public ConnectedThread(BluetoothSocket socket)
        {
            this.socket = socket;
            InputStream tempInput = null;
            OutputStream tempOutput = null;

            try {
                tempInput = socket.getInputStream();
                tempOutput = socket.getOutputStream();
            } catch (IOException e) {
                System.out.printf(e.getMessage());
                e.printStackTrace();
            }

            input = tempInput;
            output = tempOutput;
        }

        public void run()
        {
            byte[] buffer = new byte[1024];
            int bytes;

            while (state == STATE_CONNECTED) {
                try {
                    bytes = input.read(buffer);
                    handler.obtainMessage(1, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    connectionLost();
                    BluetoothService.this.start();
                    break;
                }
            }
        }

        public void write(byte[] buffer)
        {
            try {
                output.write(buffer);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        public void cancel()
        {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}


