package info.haydenshively.sleepwear.Controller;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
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
    private PowerManager.WakeLock wakeLock;
    private SensorListener sensorListener;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startID) {
        keepAlive(true);
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
        sensorListener.unregister();
        stopForeground(true);
        keepAlive(false);
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

    private void keepAlive(final boolean on) {
        if (wakeLock == null) {
            PowerManager powerManager = (PowerManager)getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Measuring");
        }
        if (on) wakeLock.acquire();
        else wakeLock.release();
    }
}
