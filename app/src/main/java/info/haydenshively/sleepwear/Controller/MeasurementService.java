package info.haydenshively.sleepwear.Controller;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import info.haydenshively.sleepwear.R;
import info.haydenshively.sleepwear.View.MainActivity;

/**
 * Created by h_shively on 7/13/2018.
 */

public final class MeasurementService extends Service {
    private static final int sensorRetrievalAttempts = 3;
    private static final long timeoutMS = 60000;
    private final Handler handler = new Handler();
    private final Runnable stop = new Runnable () {@Override public void run() {stopSelf();}};
    private SensorListener sensorListener;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startID) {
        startForeground(0, createNotification().build());

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
        stopForeground(true);
        sensorListener.unregister();
    }

    private NotificationCompat.Builder createNotification() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new NotificationCompat.Builder(this, "ID")
                .setSmallIcon(R.drawable.go_to_phone_00172)
                .setContentTitle("Measuring HR")
                .setContentText("Try to hold still")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
    }
}
