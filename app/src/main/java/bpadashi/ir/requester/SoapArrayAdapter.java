package bpadashi.ir.requester;

/**
 * Created by BabakPadashi on 30/03/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SoapArrayAdapter extends ArrayAdapter<Entity> {
    private final Context context;
    private final List<Entity> values;

    public SoapArrayAdapter(Context context, List<Entity> values) {
        super(context, R.layout.list_row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView textViewName = (TextView) rowView.findViewById(R.id.name);
        TextView textViewId = (TextView) rowView.findViewById(R.id.id);

        textViewName.setText(values.get(position).getName());
        textViewId.setText("ID:"+values.get(position).getId());


        return rowView;
    }
}