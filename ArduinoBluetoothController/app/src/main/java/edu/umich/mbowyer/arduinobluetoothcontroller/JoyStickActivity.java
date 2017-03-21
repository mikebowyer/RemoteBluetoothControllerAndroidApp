package edu.umich.mbowyer.arduinobluetoothcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JoyStickActivity extends AppCompatActivity {

    //LAYOUT COMPONENTS
    RelativeLayout layout_joystick;// how to layout the joystick
    ImageView image_joystick, image_border;//images are just circles
    TextView XTextView, YTextView, AngleTextView, DistanceTextView, DirectionTextView;//textviews to print out the joystick data

    JoyStick js;//joystick class to figure out where the joystick is pressed.
    public String deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_stick);

        //Print Device Name in Toast
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        deviceName = bundle.getString("deviceName");
        Toast.makeText(getApplicationContext(),"Connected to "+deviceName,Toast.LENGTH_LONG).show();// Set your own toast  message

        //Assigning all Layout COmponents
        XTextView = (TextView)findViewById(R.id.XTextView);
        YTextView = (TextView)findViewById(R.id.YTextView);
        AngleTextView = (TextView)findViewById(R.id.AngleTextView);
        DistanceTextView = (TextView)findViewById(R.id.DistanceTextView);
        DirectionTextView = (TextView)findViewById(R.id.DirectionTextView);
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        //Setting up Joystick Class
        js = new JoyStick(getApplicationContext(), layout_joystick, R.drawable.image_button);

        //setting Joystick Size
        js.setStickSize(100, 100);//set size of inner joystick image when layout is pressed.
        js.setLayoutSize(700, 700);//sets size of joystick area.
        js.setLayoutAlpha(150);//changes shade of the background image
        js.setStickAlpha(100);//changes shade of the joystick image when layout is pressed.
        js.setOffset(10);//??
        js.setMinimumDistance(50);//sets minimum distance which the joystick must be moved in order to be drawn.

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);//draw the joystick with a new motion event

                //if the motion event is the layout is pressed, or is already pressed and is moved
                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE)
                {
                    double x;
                    if(js.getX()>js.getLayoutWidth()/2){
                        x= 1;
                    }else if(js.getX() < - (js.getLayoutWidth()/2)){
                        x=-1;
                    }else{
                        x = (js.getX() / ( js.getLayoutWidth()/2 ) );
                    }

                    double y;
                    if(js.getY()> ( js.getLayoutHeight()/2) ){
                        y= 1;
                    }else if(js.getY() < - ( js.getLayoutHeight() / 2 )){
                        y=-1;
                    }else{
                        y = (js.getY() / (js.getLayoutHeight()/2) );
                    }

                    //udapte the text fields
                    XTextView.setText("X : " + String.format("%.2f",x) );//js.getX()));
                    YTextView.setText("Y : " + String.format("%.2f",y) );
                    //textView2.setText("Y : " + String.valueOf(js.getY()));
                    AngleTextView.setText("Angle : " + String.format("%.2f",js.getAngle()));
                    DistanceTextView.setText("Distance : " + String.format("%.2f",js.getDistance()));

                    //update the joystick direction
                    int direction = js.get8Direction();
                    if(direction == JoyStick.STICK_UP) {
                        DirectionTextView.setText("Direction : ^");
                    } else if(direction == JoyStick.STICK_UPRIGHT) {
                        DirectionTextView.setText("Direction : ^>");
                    } else if(direction == JoyStick.STICK_RIGHT) {
                        DirectionTextView.setText("Direction : >");
                    } else if(direction == JoyStick.STICK_DOWNRIGHT) {
                        DirectionTextView.setText("Direction : v>");
                    } else if(direction == JoyStick.STICK_DOWN) {
                        DirectionTextView.setText("Direction : v");
                    } else if(direction == JoyStick.STICK_DOWNLEFT) {
                        DirectionTextView.setText("Direction : <v");
                    } else if(direction == JoyStick.STICK_LEFT) {
                        DirectionTextView.setText("Direction : <");
                    } else if(direction == JoyStick.STICK_UPLEFT) {
                        DirectionTextView.setText("Direction : <^");
                    } else if(direction == JoyStick.STICK_NONE) {
                        DirectionTextView.setText("Direction : -");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    XTextView.setText("X :");
                    YTextView.setText("Y :");
                    AngleTextView.setText("Angle :");
                    DistanceTextView.setText("Distance :");
                    DirectionTextView.setText("Direction :");
                }
                return true;
            }
        });

    }
}
