package info.haydenshively.sleepwear;

import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.view.WearableListView;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    private Filer filer;
    private WearableListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //enableStartOnBoot alarm that triggers sensor reading
        IntentSwitchboard.enableStartOnBoot(this);//TODO only need to run once in APP's lifetime
        Schedule.setInterval(15000L);
        Schedule.update(this);

        filer = new Filer(this);
        int[] demoNumbers = filer.readData();
        if (demoNumbers.length == 0) demoNumbers = new int[] {1000, 2000, 3000};

        list = (WearableListView)findViewById(R.id.list);
        list.setAdapter(new ListAdapter(this, demoNumbers));
        list.setClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        int[] currentNumbers = filer.readData();

        Integer tag = (Integer)v.itemView.getTag();
        switch(tag) {//TODO do stuff
            default:filer.write(Filer.combine(currentNumbers, new int[] {tag}));break;
        }
    }

    @Override
    public void onTopEmptyRegionClick() {}

}
