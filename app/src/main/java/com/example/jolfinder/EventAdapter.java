package com.example.jolfinder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public EventAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Events object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        EventHolder eventHolder;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            eventHolder = new EventHolder();
            eventHolder.tx_eventN = (TextView) row.findViewById(R.id.tx_eventN);
            eventHolder.tx_eventDistance = (TextView) row.findViewById(R.id.tx_eventDistance);


            row.setTag(eventHolder);

        }else{
            eventHolder = (EventHolder) row.getTag();
        }

        Events events = (Events) this.getItem(position);
        eventHolder.tx_eventN.setText(events.getEventN());
        eventHolder.tx_eventDistance.setText(events.getTotalDistance());
        eventHolder.tx_eventDistance.setBackground(Drawable.createFromPath(events.getImage()));


        return row;
    }

    static class EventHolder{
        TextView tx_eventN, tx_eventDistance, tx_eventLong;
        ImageView e_img;
    }

}
