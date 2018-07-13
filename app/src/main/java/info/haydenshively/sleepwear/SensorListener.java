package info.haydenshively.sleepwear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;

/**
 * Created by h_shively on 7/13/2018.
 */

public class SensorListener implements SensorEventListener {
    private final Context context;
    private final SensorManager sensorManager;
    private final Sensor ppg;

    public SensorListener(final Context context) {
        this.context = context;

        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        ppg = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        Filer filer = new Filer(context);

        float sensorValue = sensorEvent.values[0];
        Filer.combine(filer.readData(), new int[] {(int)sensorValue});
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int i) {}

    boolean start() {
        if (ppg != null) {
            sensorManager.registerListener(this, ppg, SensorManager.SENSOR_DELAY_NORMAL);
            return true;
        }else return false;
    }
    void stop() {sensorManager.unregisterListener(this);}
}
