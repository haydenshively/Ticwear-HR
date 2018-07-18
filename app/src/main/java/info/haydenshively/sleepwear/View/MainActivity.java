package info.haydenshively.sleepwear.View;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import info.haydenshively.sleepwear.Model.F_Measurements;
import info.haydenshively.sleepwear.R;
import info.haydenshively.sleepwear.Controller.Schedule;

public final class MainActivity extends Activity {

    private TextView averageHR;
    private SeekBar interval;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        averageHR = (TextView)findViewById(R.id.averageHR);
        interval = (SeekBar)findViewById(R.id.seekBar);
        button = (Button)findViewById(R.id.button);

        Schedule.enableStartOnBoot(this);

        final int[] measurements = (new F_Measurements(this)).mread();
        if (measurements.length > 0) {
            double sum = 0.0;
            for (int measurement : measurements) sum += (double)measurement;
            averageHR.setText("---" + Double.toString(sum/(double)measurements.length).substring(0, 2) + "---");
        }else averageHR.setText("no data");

        interval.setProgress(4);
        button.setText("Start");
    }

    public void openDataActivity(final View view) {startActivity(new Intent(this, DataActivity.class));}

    public void onButtonClick(final View view) {
        if (button.getText() == "Start") {
            if (PermissionActivity.grantedAll(this)) {
                final long interval = getInterval();
                Schedule.setInterval(getInterval());
                Schedule.update(this);

                final String message = "Measurements will be taken every " + Double.toString(interval/60000).substring(0, 2) + " minutes";
                (Toast.makeText(this, message, Toast.LENGTH_SHORT)).show();
                button.setText("Stop");
            }else startActivity(new Intent(this, PermissionActivity.class));
        }else {
            Schedule.cancel(this);
            button.setText("Start");
        }
    }

    private long getInterval() {
        switch(interval.getProgress()) {
            case 0: return 60000L;
            case 1: return 120000L;
            case 2: return 300000L;
            case 3: return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
            case 4: return AlarmManager.INTERVAL_HALF_HOUR;
            default: return AlarmManager.INTERVAL_HOUR;
        }
    }
}
