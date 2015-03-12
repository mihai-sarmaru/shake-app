package com.sarmaru.mihai.shakeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

    private static final String QUAKEID = "QUAKEID";
    private static List<QuakeObject> quakeList;
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
        viewHolder.dateTV.setText(Utils.formatDateShort(quakeList.get(i).getTime()));
        viewHolder.timeTV.setText(Utils.formatTimeShort(quakeList.get(i).getTime()));

        ShakeAppPreferences prefs = new ShakeAppPreferences(context);
        if (prefs.getLatestDatabaseId() > 0 && quakeList.get(i).getId() > prefs.getLatestDatabaseId()) {
            viewHolder.newTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return quakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView regionTV, dateTV, timeTV, newTV, iconDateR, iconTimeR;
        public CircleColorView magnitudeView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            regionTV = (TextView) itemView.findViewById(R.id.regionTV);
            dateTV = (TextView) itemView.findViewById(R.id.dateTV);
            timeTV = (TextView) itemView.findViewById(R.id.timeTV);
            newTV = (TextView) itemView.findViewById(R.id.newTV);
            magnitudeView = (CircleColorView) itemView.findViewById(R.id.magnitude_circle_color_view);

            Typeface iconFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fontawesome-webfont.ttf");
            iconDateR = (TextView) itemView.findViewById(R.id.iconDateR);
            iconTimeR = (TextView) itemView.findViewById(R.id.iconTimeR);
            iconDateR.setTypeface(iconFont);
            iconTimeR.setTypeface(iconFont);
        }

        @Override
        public void onClick(View v) {
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra(QUAKEID, quakeList.get(getPosition()).getId());
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);
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
