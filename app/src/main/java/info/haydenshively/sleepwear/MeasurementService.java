package info.haydenshively.sleepwear;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * Created by h_shively on 7/13/2018.
 */

public final class MeasurementService extends Service {
    private static final int sensorRetrievalAttempts = 3;
    private static final long timeoutMS = 30000;
    private final Handler handler = new Handler();
    private final Runnable stop = new Runnable () {@Override public void run() {stopSelf();}};
    private SensorListener sensorListener;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startID) {

        for (int i = 0; i < sensorRetrievalAttempts; i++) {
            sensorListener = new SensorListener(this, MeasurementService.class);
            if (sensorListener.start()) break;
        }

        handler.postDelayed(stop, timeoutMS);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {return null;}//ignore this since not linked to activity

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorListener.unregister();
    }
}
