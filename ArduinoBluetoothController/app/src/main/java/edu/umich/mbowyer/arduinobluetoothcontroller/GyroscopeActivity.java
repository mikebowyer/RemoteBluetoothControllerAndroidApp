package edu.umich.mbowyer.arduinobluetoothcontroller;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GyroscopeActivity extends AppCompatActivity implements SensorEventListener {

    //Layout COmponoents
    TextView GyroScopeTextView;

    public String deviceName;

    //SENSOR RELATED VARIABLES
    private SensorManager sManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        //Grab LAYOUT COmponents
        GyroScopeTextView=(TextView)findViewById(R.id.GyroScopeTextView);

        //Print Device Name in Toast
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        deviceName = bundle.getString("deviceName");
        Toast.makeText(getApplicationContext(),"Connected to "+deviceName,Toast.LENGTH_LONG).show();// Set your own toast  message

        //Initialize Sensor Manager
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);


    }



    ///////////*SENSOR FUNCTIONS*////////////
    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        //else it will output the Roll, Pitch and Yawn values
        GyroScopeTextView.setText("Orientation X (Roll) :"+ Float.toString(event.values[2]) +"\n"+
                "Orientation Y (Pitch) :"+ Float.toString(event.values[1]) +"\n"+
                "Orientation Z (Yaw) :"+ Float.toString(event.values[0]));
    }
}