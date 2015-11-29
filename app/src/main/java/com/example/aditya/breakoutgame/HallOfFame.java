package com.example.aditya.breakoutgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class HallOfFame extends AppCompatActivity {

    FileHandler fileHandler = new FileHandler(this);
    ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
    ScoreAdapter scoreAdapter;
    ListView scoreList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Hall of Fame");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toolbar.setTitleTextColor(Color.WHITE);


        scoreList = (ListView)findViewById(R.id.scoreList);

        //initialize arrayList dataModelArrayList with the data returned from the File Handler
        dataModelArrayList = fileHandler.getDataObject();

        //feed this attaylist to Adapter
        scoreAdapter = new ScoreAdapter(getApplicationContext(), dataModelArrayList);

        //link this adapter with the layout ListView
        scoreList.setAdapter(scoreAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        //
        super.onBackPressed();
    }
}
