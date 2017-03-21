package edu.umich.mbowyer.arduinobluetoothcontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectBluetoothDevice extends AppCompatActivity {

    ListView deviceListView;
    Bluetooth myBT;
    ArrayList<String> deviceList;
    ArrayAdapter<String> deviceListAdapter;
    public final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /*LAYOUT COMPONENT VARIABLES*/
        deviceListView = (ListView)findViewById(R.id.BTPairedDevicesListView);

        //ENABLE BLUETOOTH
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//grab bluetooth adapter of the device(this is BT radio of the device)
        boolean requestBT=false;
        while(!bluetoothAdapter.isEnabled())
         {
             if(!requestBT) {
                 Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //create an intent to request to turn on bluetooth
                 startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);// start bluetooth if application enableBTIntent returns true
                 Log.w("myDebug", "Bluetooth is being requested to be enabled. ");
             }
             requestBT=true;
         }Log.v("myDebug", "Btservice is Enabled on this device");

        //initalize Bluetooth handler and get device list
        myBT = new Bluetooth(this,mHandler);// create instance of bluetooth object
        deviceList=myBT.getDeviceList();//save list of paired devices.

        //get bluetooth device list to display to user
        deviceListAdapter = new ArrayAdapter<String>(this,R.layout.bt_device_list_element,deviceList);
        deviceListView.setAdapter(deviceListAdapter);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDeviceName = deviceList.get(position);//get device name

                Intent popUpIntent = new Intent(SelectBluetoothDevice.this,PopUp.class);
                Bundle myBundle = new Bundle();
                myBundle.putString("deviceName",selectedDeviceName);
                popUpIntent.putExtras(myBundle);
                startActivity(popUpIntent);
                //connectService(selectedDeviceName);//start connectino with device
            }
        });
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE://when state is changed IE connected, COnnected, disconnected.
                    Log.d("myDebug", "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:// This device attempted to send a message to the remote device
                    Log.d("myDebug", "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ://this dewice recieved a message, and saved it in the myBTRecievedString
                    Log.d("myDebug", "MESSAGE_READ =" + myBT.recievedString);
                    //updateRecievedDataTextView(myBT.recievedString);
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME://the device name when the device has become connected
                    Log.d("myDebug", "MESSAGE_DEVICE_NAME "+myBT.connectedDeviceName);
                    break;
                case Bluetooth.MESSAGE_TOAST://when a message failed to send.
                    Log.d("myDebug", "MESSAGE_TOAST "+msg);
                    break;
            }
        }
    };
}
