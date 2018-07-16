package info.haydenshively.sleepwear;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;

/**
 * Created by h_shively on 7/13/2018.
 */

final class IntentSwitchboard extends BroadcastReceiver {
    public static final String MEASURE_INTENT = "MEASURE";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        switch (intent.getAction()) {
            case MEASURE_INTENT:
                if (!isCharging(context)) context.startService(new Intent(context, MeasurementService.class));
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                Schedule.update(context.getApplicationContext());
                break;
            default:
                break;
        }
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

    public static boolean isCharging(final Context context) {
        final IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        final Intent batteryStatus = context.registerReceiver(null, ifilter);
        final int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    }
}
