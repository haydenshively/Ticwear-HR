package info.haydenshively.sleepwear;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * Created by h_shively on 7/13/2018.
 */

final class MeasurementService extends Service {
    private static final int sensorRetrievalAttempts = 3;
    private static final long timeoutMS = 30000;
    private final Handler handler = new Handler();
    private final Runnable stop = new Runnable () {@Override public void run() {stopSelf();}};
    private SensorListener sensorListener;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startID) {

//        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "HR");
//        wakeLock.acquire();
//        enterForeground();
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
//        stopForeground(true);
//        wakeLock.release();
    }




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



}
