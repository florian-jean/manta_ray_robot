package florianjean.ray_manta_accelerometer;

import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private float x,y,z,delay,ratio;

    private TextView textAxisX,textAxisY,textAxisZ,textRatio,textDelay;
    private CheckBox checkManually;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAxisX = (TextView) findViewById(R.id.textViewAxisX);
        textAxisY = (TextView) findViewById(R.id.textViewAxisY);
        textAxisZ = (TextView) findViewById(R.id.textViewAxisZ);
        textRatio = (TextView) findViewById(R.id.textViewRatio);
        textDelay = (TextView) findViewById(R.id.textViewDelay);
        checkManually = (CheckBox) findViewById(R.id.checkBoxManually);

        textAxisX.setText("Ready");
        textAxisY.setText("Ready");
        textAxisZ.setText("Ready");

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        onSensorChange(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChange(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            textAxisX.setText(""+String.valueOf(x));
            textAxisY.setText(""+String.valueOf(y));
            textAxisZ.setText(""+String.valueOf(z));

            ratio=(x+10)/20;
            textRatio.setText(""+String.valueOf(ratio));

            delay = (300+20*y);
            textDelay.setText(""+String.valueOf(delay));

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
            }
        }
    }




}
