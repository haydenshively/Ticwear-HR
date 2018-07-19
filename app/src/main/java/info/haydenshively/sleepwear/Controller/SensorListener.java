package info.haydenshively.sleepwear.Controller;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import info.haydenshively.sleepwear.Model.F_Measurements;

/**
 * Created by h_shively on 7/13/2018.
 */

final class SensorListener implements SensorEventListener {
    private static final int histLength = 24;
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
        F_Measurements filer = new F_Measurements(context);

        final int sensorValue = Math.round(sensorEvent.values[0]);
        final int[] storedData = filer.mread();
        if (storedData.length > 0) {
            final int[] newData = F_Measurements.combine(new int[] {sensorValue}, storedData);
            if (newData.length > histLength) {
                int[] toBeStored = new int[histLength];
                for (int i = 0; i < histLength; i++) toBeStored[i] = newData[i];
                filer.write(toBeStored);
            }else filer.write(newData);
        }else filer.write(new int[] {sensorValue});


        //SHUT IT DOWN AFTER A SINGLE RESULT
        stop();
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int i) {}

    boolean start() {
        if (ppg != null) {
            sensorManager.registerListener(this, ppg, SensorManager.SENSOR_DELAY_NORMAL);
//            incrementAttempts();
            return true;
        }else return false;
    }

    void unregister() {sensorManager.unregisterListener(this);}

    private void stop() {
        unregister();
        context.stopService(new Intent(context, parent));
    }

    private void incrementAttempts() {
        F_Measurements filer = new F_Measurements(context);
        int[] oldData = filer.mread();
        if (oldData.length > 0) oldData[0]++;
        else oldData = new int[] {1};
        filer.write(oldData);
    }
}
