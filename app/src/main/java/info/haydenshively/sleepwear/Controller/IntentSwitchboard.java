package info.haydenshively.sleepwear.Controller;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import info.haydenshively.sleepwear.Model.F_Permissions;
/**
 * Created by h_shively on 7/13/2018.
 */

public final class IntentSwitchboard extends BroadcastReceiver {
    public static final String MEASURE_INTENT = "MEASURE";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        switch (intent.getAction()) {
            case MEASURE_INTENT:
                if (!isCharging(context) && (new F_Permissions(context)).contains(Manifest.permission.BODY_SENSORS)) context.startService(new Intent(context, MeasurementService.class));
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                Schedule.update(context.getApplicationContext());
                break;
            default:
                break;
        }
    }

    public static boolean isCharging(final Context context) {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        final Intent batteryStatus = context.registerReceiver(null, filter);
        final int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    }
}
