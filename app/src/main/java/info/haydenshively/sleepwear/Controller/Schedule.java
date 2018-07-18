package info.haydenshively.sleepwear.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

/**
 * Created by h_shively on 7/13/2018.
 */

public final class Schedule {
    private Schedule() {}

    private static long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    public static void setInterval(final long interval) {Schedule.interval = interval;}
    public static long getInterval() {return interval;}

    public static void update(final Context context) {
        final Intent alarmIntent = new Intent(context, IntentSwitchboard.class);
        alarmIntent.setAction(IntentSwitchboard.MEASURE_INTENT);
        final PendingIntent alarmIntent_compatible = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.uptimeMillis() + interval/2, interval, alarmIntent_compatible);
    }

    public static void cancel(final Context context) {
        final Intent alarmIntent = new Intent(context, IntentSwitchboard.class);
        alarmIntent.setAction(IntentSwitchboard.MEASURE_INTENT);
        final PendingIntent alarmIntent_compatible = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent_compatible);
    }

    public static void enableStartOnBoot(final Context context) {
        final ComponentName receiver = new ComponentName(context, IntentSwitchboard.class);
        final PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public static void disableStartOnBoot(final Context context) {
        final ComponentName receiver = new ComponentName(context, IntentSwitchboard.class);
        final PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
