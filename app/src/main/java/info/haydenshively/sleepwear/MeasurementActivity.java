package info.haydenshively.sleepwear;

import android.os.Bundle;
import android.app.Activity;
import android.view.WindowManager;

public class MeasurementActivity extends Activity {
    private SensorListener sensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        sensorListener = new SensorListener(this);
        sensorListener.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorListener.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, 0|0);//TODO probably wrong
        sensorListener.stop();
    }
}
