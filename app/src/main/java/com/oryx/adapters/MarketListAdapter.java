package com.oryx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oryx.R;

public class MarketListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MarketListAdapter(Context context, String[] values) {
        super(context, R.layout.activity_list_market, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_list_market, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        TextView _label = (TextView) rowView.findViewById(R.id.label);
        TextView _description = (TextView) rowView.findViewById(R.id.description);

        _label.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        if (s.equals("WindowsMobile")) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else if (s.equals("iOS")) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else if (s.equals("Blackberry")) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher);
        }

        return rowView;
    }
}
