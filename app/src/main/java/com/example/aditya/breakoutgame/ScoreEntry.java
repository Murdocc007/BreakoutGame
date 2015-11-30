package com.example.aditya.breakoutgame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreEntry extends AppCompatActivity {

    TextView entryTime, entryScore;
    EditText entryName;
    Button submitButton, cancelButton;
    DataModel receivedDataModel;
    FileHandler fileHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_entry);

        entryName = (EditText)findViewById(R.id.entryName);
        entryScore = (TextView)findViewById(R.id.entryScore);
        entryTime = (TextView)findViewById(R.id.entryTime);

        submitButton = (Button)findViewById(R.id.buttonSubmit);
        cancelButton = (Button)findViewById(R.id.buttonCancel);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Score Entry");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toolbar.setTitleTextColor(Color.WHITE);


        receivedDataModel = new DataModel();
        Bundle bundle = getIntent().getExtras();
        ArrayList<Integer> arrayList = bundle.getIntegerArrayList("caller");
        //set content in TextViews (entryScore and entryTime) here based on the actual values passed from the game Activity

        if(arrayList.size() != 0) {
            receivedDataModel.setScore(arrayList.get(0).toString());
            receivedDataModel.setTime(arrayList.get(1).toString());

            entryTime.setText(receivedDataModel.getTime().toString());
            entryScore.setText(receivedDataModel.getScore().toString());
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first get the Max ID from the File Handler class and set maxid + 1 as new entry
                fileHandler = new FileHandler(getApplicationContext());

                ArrayList<DataModel> arrayList = new ArrayList<>();
                arrayList = fileHandler.getDataObject();

                //initialize data model based on the score values entered/passed from Game Activity
                receivedDataModel.setId(fileHandler.getMaxId() + 1);
                receivedDataModel.setName(entryName.getText().toString());

                //add entry to arrayList and then write new arrayLIst to the file
                arrayList.add(receivedDataModel);

                //sort the returned arrayList and remove all after 10 entries
                Collections.sort(arrayList, new CustomComparator());

                if(arrayList.size() > 10){
                    for(int i=10;i<arrayList.size()-1;i++){
                        arrayList.remove(i);
                    }
                }

                fileHandler.setDataObject(arrayList);

                finish();
                startActivity(new Intent(getApplicationContext(), HallOfFame.class));
            }
        });


    }


    public class CustomComparator implements Comparator<DataModel> {

        @Override
        public int compare(DataModel lhs, DataModel rhs) {
            if (Integer.parseInt(lhs.getScore()) > Integer.parseInt(rhs.getScore())){
                return -1;
            }else if (Integer.parseInt(lhs.getScore()) < Integer.parseInt(rhs.getScore())){
                return 1;
            }else {
                return 0;
            }
        }
    }



}
