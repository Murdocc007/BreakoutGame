package com.example.aditya.breakoutgame.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.aditya.breakoutgame.models.ScoreDataModel;
import com.example.aditya.breakoutgame.utilities.FileHandler;
import com.example.aditya.breakoutgame.R;
import com.example.aditya.breakoutgame.adapters.ScoreAdapter;

import java.util.ArrayList;

public class HallOfFame extends AppCompatActivity {

    FileHandler fileHandler = new FileHandler(this);
    ArrayList<ScoreDataModel> scoreDataModelArrayList = new ArrayList<>();
    ScoreAdapter scoreAdapter;
    ListView scoreList;

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/1/2015
    //onCreate Function of the Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Hall of Fame");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toolbar.setTitleTextColor(Color.WHITE);


        scoreList = (ListView)findViewById(R.id.scoreList);

        //initialize arrayList scoreDataModelArrayList with the data returned from the File Handler
        scoreDataModelArrayList = fileHandler.getDataObject();

        //feed this attaylist to Adapter
        scoreAdapter = new ScoreAdapter(getApplicationContext(), scoreDataModelArrayList);

        //link this adapter with the layout ListView
        scoreList.setAdapter(scoreAdapter);

    }

    //Name: Auto generated
    //Date:11/1/2015
    @Override
    public void onResume() {
        super.onResume();

    }

    //Name: Auto generated
    //Date:11/1/2015
    @Override
    public void onBackPressed() {
        //
        super.onBackPressed();
    }
}
