package com.sarmaru.mihai.shakeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Mihai Sarmaru on 05.02.2015.
 */
public class QuakeListAdapter extends RecyclerView.Adapter<QuakeListAdapter.ViewHolder> {

    private List<QuakeObject> quakeList;
    private static Context context;

    public QuakeListAdapter (List<QuakeObject> quakeObjectList, Context appContext) {
        this.quakeList = quakeObjectList;
        context = appContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.quake_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.magnitudeView.setCircleColor(getMagnitudeColor(quakeList.get(i).getMagnitude()));
        viewHolder.magnitudeView.setCircleText(String.valueOf(quakeList.get(i).getMagnitude()));

        viewHolder.regionTV.setText(quakeList.get(i).getRegion());
        viewHolder.dateTV.setText(Utils.formatDate(quakeList.get(i).getTime()));
        viewHolder.timeTV.setText(Utils.formatTime(quakeList.get(i).getTime()));

        // TODO - Check visibility
        viewHolder.newTV.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return quakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView regionTV, dateTV, timeTV, newTV;
        public CircleColorView magnitudeView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            regionTV = (TextView) itemView.findViewById(R.id.regionTV);
            dateTV = (TextView) itemView.findViewById(R.id.dateTV);
            timeTV = (TextView) itemView.findViewById(R.id.timeTV);
            newTV = (TextView) itemView.findViewById(R.id.newTV);
            magnitudeView = (CircleColorView) itemView.findViewById(R.id.magnitude_circle_color_view);
        }

        @Override
        public void onClick(View v) {

            // TODO: OnCLick Intent action
            Toast.makeText(v.getContext(), "Position: " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    private static int getMagnitudeColor (double magnitude) {
        int magnitudeColor = context.getResources().getColor(R.color.magnitude_default);
        if (magnitude >= 2 && magnitude < 3) {
            magnitudeColor = context.getResources().getColor(R.color.magnitude_2);
        } else if (magnitude >= 3 && magnitude < 4) {
            magnitudeColor = context.getResources().getColor(R.color.magnitude_3);
        } else if (magnitude >= 4 && magnitude < 5) {
            magnitudeColor = context.getResources().getColor(R.color.magnitude_4);
        } else if (magnitude >= 5 && magnitude < 6) {
            magnitudeColor = context.getResources().getColor(R.color.magnitude_5);
        } else if (magnitude >= 6 && magnitude < 7) {
            magnitudeColor = context.getResources().getColor(R.color.magnitude_6);
        } else {
            magnitudeColor = context.getResources().getColor(R.color.magnitude_7);
        }
        return magnitudeColor;
    }
}
