package edu.umich.mbowyer.arduinobluetoothcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class TankDriveActivity extends AppCompatActivity {
    public String deviceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_drive);

        //Print Device Name in Toast
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        deviceName = bundle.getString("deviceName");
        Toast.makeText(getApplicationContext(),"Connected to "+deviceName,Toast.LENGTH_LONG).show();// Set your own toast  message
    }
}
