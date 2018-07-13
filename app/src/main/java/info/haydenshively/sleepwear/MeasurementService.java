package info.haydenshively.sleepwear;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Timer;

/**
 * Created by h_shively on 7/13/2018.
 */

public final class MeasurementService extends Service {
    private SensorListener sensorListener;
    private long startTime = SystemClock.elapsedRealtime();

    public MeasurementService() {}

    //FOR ALL
    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startID) {
//        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "HR");
//        wakeLock.acquire();
        enterForeground();

        sensorListener = new SensorListener(this);
        sensorListener.start();

        startTime = SystemClock.elapsedRealtime();
//        wakeLock.release();
        return START_STICKY;
    }


    //FOR ALL
    @Override
    public IBinder onBind(final Intent intent) {return null;}//ignore this since not linked to activity
    //FOR ALL
    @Override
    public void onCreate() {super.onCreate();}

    private void enterForeground() {
        final Intent onNotiClick = new Intent(getApplicationContext(), MainActivity.class);
        onNotiClick.setAction(Intent.ACTION_MAIN);
        onNotiClick.addCategory(Intent.CATEGORY_LAUNCHER);
        onNotiClick.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final PendingIntent onNotiClick_compatible = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                onNotiClick,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Automatic HR measurement enabled")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(onNotiClick_compatible)
                .build();

        startForeground(1, notification);
    }

    /**
     * Stops listening to sensor events, removes notification, and ends service
     */
    private void stop() {
        sensorListener.stop();
        stopForeground(true);
        stopSelf();
    }
}
