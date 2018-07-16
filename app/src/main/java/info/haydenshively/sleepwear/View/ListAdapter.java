package info.haydenshively.sleepwear.View;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.haydenshively.sleepwear.R;

final class ListAdapter extends WearableListView.Adapter {
    private final Context context;
    private final LayoutInflater inflater;
    private String[] items;

    public ListAdapter(final Context context, final String[] items) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(final Context context, final int[] dataset) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new String[dataset.length];
        for (int i = 0; i < dataset.length; i++) items[i] = Integer.toString(dataset[i]);
    }


    // Create new views for list items
    // (invoked by the WearableListView's layout manager)
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Inflate our custom layout for list items
        return new ItemViewHolder(inflater.inflate(R.layout.list_item, null));
    }

    // Replace the contents of a list item
    // Instead of creating new views, the list tries to recycle existing ones
    // (invoked by the WearableListView's layout manager)
    @Override
    public void onBindViewHolder(final WearableListView.ViewHolder holder, final int position) {
        // retrieve the text view
        final TextView view = ((ItemViewHolder)holder).textView;
        // replace text contents
        view.setText(items[position]);
        // replace list item's metadata
        holder.itemView.setTag(position);
    }

    // Return the size of your dataset
    // (invoked by the WearableListView's layout manager)
    @Override
    public int getItemCount() {return items.length;}

    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            textView = itemView.findViewById(R.id.name);
        }
    }
}