package com.galacticCat.chatbleu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button listen_Btn, send_Btn, listDevces_Btn;
    private ListView listView;
    private TextView msg_box, status;
    private EditText msgField;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice[] bluetoothArray;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    private int REQUEST_ENABLE_BLUETOOTH = 1;

    private static final String APP_NAME = "ChatBleu";
    private static final UUID MY_UUID = UUID.fromString("7376f6a4-6525-11e9-a923-1681be663d3e");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIdes();
        bluetoothAdapter = bluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);

        }

        implementListeners();
    }

    private void implementListeners() {
    listDevces_Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            makeToast("Listing devices");
            Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
            String[] strings = new String[bt.size()];
            int index = 0;

            if (bt.size() > 0) {
                for (BluetoothDevice device : bt) {
                    bluetoothArray[index] = device;
                    strings[index] = device.getName();
                    index++;
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, strings);
                listView.setAdapter(arrayAdapter);
            }
        }
    });

    listen_Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            makeToast("Searching");
            ServerClass serverClass = new ServerClass();
            serverClass.start();
        }
    });
    }

    Handler handler = new Handler (new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {

        switch(msg.what){
            case STATE_LISTENING:
                status.setText("Listening");
                break;
            case STATE_CONNECTED:
                status.setText("Connecting");
                break;
            case STATE_CONNECTION_FAILED:
                status.setText("Connection Failed");
                break;
            case STATE_MESSAGE_RECEIVED:
                //status.setText("Message Received");
                break;
        }
            return true;
        }
    });

    private void findViewByIdes() {
        listen_Btn = (Button) findViewById(R.id.listen);
        send_Btn = (Button) findViewById(R.id.send);
        listView = (ListView) findViewById(R.id.listview);
        msg_box = (TextView) findViewById(R.id.msg);
        status = (TextView) findViewById(R.id.status);
        msgField = (EditText) findViewById(R.id.writemsg);
        listDevces_Btn = (Button) findViewById(R.id.listDevices);
    }

    public void makeToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass() {
            try {
                serverSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run() {
            BluetoothSocket socket = null;

            while (socket == null) {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);

                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                }

                if(socket != null) {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);

                    //TODO Codigo para enviar y recibir
                    break;
                }
            }
        }
    }
}
