package com.example.aditya.breakoutgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aditya.breakoutgame.R;
import com.example.aditya.breakoutgame.models.ScoreDataModel;

import java.util.ArrayList;

public class ScoreAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<ScoreDataModel> scoreDataModelArrayList;
    TextView name, timeTaken, score;

    //Name: Aditya Mahajan
    //NetId: axm156630
    //Date: 11/1/2015
    //class constructor, takes the scoreDataModelArrayList arraylist and the context of activity
    public ScoreAdapter(Context context, ArrayList<ScoreDataModel> scoreDataModelArrayList) {
        super(context, R.layout.hall_of_fame_elements, scoreDataModelArrayList);
        this.context = context;
        this.scoreDataModelArrayList = scoreDataModelArrayList;
    }



    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/1/2015
    //getViewfunction gets called everytime a contact goes out of the scope of the screen and when a new contact data
    //comes in the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hall_of_fame_elements, parent, false);
        }

        name = (TextView)convertView.findViewById(R.id.name);
        name.setText(scoreDataModelArrayList.get(position).getName());

        timeTaken = (TextView)convertView.findViewById(R.id.timeTaken);
        timeTaken.setText(scoreDataModelArrayList.get(position).getTime());

        score = (TextView)convertView.findViewById(R.id.score);
        score.setText(scoreDataModelArrayList.get(position).getScore());
        return convertView;
    }
}
