package info.haydenshively.sleepwear.View;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.haydenshively.sleepwear.R;

public class WearableListItemLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {
    private ImageView circle;
    private TextView text;

    public WearableListItemLayout(final Context context) {this(context, null);}
    public WearableListItemLayout(final Context context, final AttributeSet attrs) {this(context, attrs, 0);}
    public WearableListItemLayout(final Context context, final AttributeSet attrs, final int defStyle) {super(context, attrs, defStyle);}

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
    public void onCenterPosition(final boolean animate) {}

    @Override
    public void onNonCenterPosition(final boolean animate) {}
}