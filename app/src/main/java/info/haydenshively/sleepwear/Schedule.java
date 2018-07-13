package info.haydenshively.sleepwear;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by h_shively on 7/13/2018.
 */

public final class Schedule {
    private Schedule() {}

    public static final String MEASURE_INTENT = "MEASURE";
    private static long interval = AlarmManager.INTERVAL_HALF_HOUR;

    public static void setInterval(final long interval) {Schedule.interval = interval;}
    public static long getInterval() {return interval;}

    public static void update(final Context context) {
        final Intent alarmIntent = new Intent(context, IntentSwitchboard.class);
        alarmIntent.setAction(MEASURE_INTENT);
        final PendingIntent alarmIntent_compatible = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval, interval, alarmIntent_compatible);
    }
}
