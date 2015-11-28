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

public class ScoreEntry extends AppCompatActivity {

    TextView entryTime, entryScore;
    EditText entryName;
    Button submitButton, cancelButton;
    DataModel dataModel;
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



        //set content in TextViews (entryScore and entryTime) here based on the actual values passed from the game Activity

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
                dataModel = new DataModel();
                dataModel.setId(fileHandler.getMaxId() + 1);
                dataModel.setName("alsdk");
                dataModel.setScore("123");
                dataModel.setTime("asdasd");

                //add entry to arrayList and then write new arrayLIst to the file
                arrayList.add(dataModel);
                fileHandler.setDataObject(arrayList);

                startActivity(new Intent(getApplicationContext(), HallOfFame.class));
            }
        });


    }



}
