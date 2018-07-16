package info.haydenshively.sleepwear.View;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.view.WearableListView;

import info.haydenshively.sleepwear.Model.F_Measurements;
import info.haydenshively.sleepwear.R;
import info.haydenshively.sleepwear.Controller.Schedule;

public final class MainActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PermissionActivity.grantedAll(this)) {
            Schedule.enableStartOnBoot(this);
            Schedule.update(this);
        }else startActivity(new Intent(this, PermissionActivity.class));


        final int[] measurements = (new F_Measurements(this)).mread();

        list = findViewById(R.id.list);
        if (measurements.length > 0) list.setAdapter(new ListAdapter(this, measurements));
        else list.setAdapter(new ListAdapter(this, new String[] {"No data yet!"}));
        list.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
//        int[] currentNumbers = filer.readData();
//
//        Integer tag = (Integer)v.itemView.getTag();
//        switch(tag) {//TODO do stuff
//            default:filer.write(Filer.combine(currentNumbers, new int[] {tag}));break;
//        }
    }

    @Override
    public void onTopEmptyRegionClick() {}
}
