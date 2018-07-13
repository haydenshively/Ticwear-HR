package info.haydenshively.sleepwear;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by h_shively on 7/13/2018.
 */

public class IntentSwitchboard extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        switch (intent.getAction()) {
            case Schedule.MEASURE_INTENT:
                final Intent measure = new Intent(context, MeasurementActivity.class);
                measure.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(measure);
                break;

            case Intent.ACTION_BOOT_COMPLETED: Schedule.update(context.getApplicationContext()); break;
            default: break;
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
}
