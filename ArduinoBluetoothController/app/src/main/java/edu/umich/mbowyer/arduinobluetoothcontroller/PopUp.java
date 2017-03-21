package edu.umich.mbowyer.arduinobluetoothcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mbowyer on 3/20/2017.
 */
public class PopUp extends Activity {

    public String deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.5));

        //Get selected Device Name
        Intent intent = this.getIntent();
        Bundle recieved_bundle = intent.getExtras();
        deviceName= recieved_bundle.getString("deviceName");
    }

    public void JoyStickControllerLaunch(View view)
    {
        Intent JoyStickControlIntent = new Intent(PopUp.this,JoyStickActivity.class);
        Bundle sendBundle = new Bundle();
        sendBundle.putString("deviceName",deviceName);
        JoyStickControlIntent.putExtras(sendBundle);
        startActivity(JoyStickControlIntent);
    }
    public void GyroscopeControllerLaunch(View view)
    {
        Intent GyroIntent = new Intent(PopUp.this,GyroscopeActivity.class);
        Bundle sendBundle = new Bundle();
        sendBundle.putString("deviceName",deviceName);
        GyroIntent.putExtras(sendBundle);
        startActivity(GyroIntent);
    }
    public void TankDriveControllerLaunch(View view)
    {
        Intent TankDriveIntent = new Intent(PopUp.this,TankDriveActivity.class);
        Bundle sendBundle = new Bundle();
        sendBundle.putString("deviceName",deviceName);
        TankDriveIntent.putExtras(sendBundle);
        startActivity(TankDriveIntent);
    }
}
