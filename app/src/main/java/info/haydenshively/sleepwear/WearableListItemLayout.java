package info.haydenshively.sleepwear;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListItemLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {
    private ImageView circle;
    private TextView text;

    public WearableListItemLayout(Context context) {this(context, null);}
    public WearableListItemLayout(Context context, AttributeSet attrs) {this(context, attrs, 0);}
    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}

    // Get references to the icon and text in the item layout definition
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // These are defined in the layout file for list items
        // (see next section)
        circle = (ImageView)findViewById(R.id.circle);
        text = (TextView)findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean animate) {}

    @Override
    public void onNonCenterPosition(boolean animate) {}
}