package com.sarmaru.mihai.shakeapp;

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

    public QuakeListAdapter (List<QuakeObject> quakeObjectList) {
        this.quakeList = quakeObjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.quake_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // TODO - Add Circle color
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
}
