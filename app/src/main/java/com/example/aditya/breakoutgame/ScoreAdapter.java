package com.example.aditya.breakoutgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akash on 11/28/2015.
 */
public class ScoreAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<DataModel> dataModelArrayList;
    TextView name, timeTaken, score;

    public ScoreAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {
        super(context, R.layout.hall_of_fame_elements, dataModelArrayList);
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hall_of_fame_elements, parent, false);
        }

        name = (TextView)convertView.findViewById(R.id.name);
        name.setText(dataModelArrayList.get(position).getName());

        timeTaken = (TextView)convertView.findViewById(R.id.timeTaken);
        timeTaken.setText(dataModelArrayList.get(position).getTime());

        score = (TextView)convertView.findViewById(R.id.score);
        score.setText(dataModelArrayList.get(position).getScore());
        return convertView;
    }
}
