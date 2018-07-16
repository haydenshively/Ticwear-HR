package info.haydenshively.sleepwear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by h_shively on 7/13/2018.
 */

final class SensorListener implements SensorEventListener {
    private final Context context;
    private final Class parent;
    private final SensorManager sensorManager;
    private final Sensor ppg;

    SensorListener(final Context context, final Class parent) {
        this.context = context;
        this.parent = parent;
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        ppg = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        Filer filer = new Filer(context);

        final int sensorValue = Math.round(sensorEvent.values[0]);
        final int[] newData = Filer.combine(filer.readData(), new int[] {sensorValue});
        filer.write(newData);

        //SHUT IT DOWN AFTER A SINGLE RESULT
        stop();
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int i) {}

    boolean start() {
        if (ppg != null) {
            sensorManager.registerListener(this, ppg, SensorManager.SENSOR_DELAY_NORMAL);
            return true;
        }else return false;
    }

    void unregister() {sensorManager.unregisterListener(this);}

    private void stop() {
        unregister();
        context.stopService(new Intent(context, parent));
    }
}
