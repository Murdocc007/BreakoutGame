package com.example.aditya.breakoutgame.models;

import java.util.Comparator;

public class ScoreDataModel implements Comparable {

    private String name;
    private String score;
    private String time;
    private String id;

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //getter for ID
    public String getId() {
        return id;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //setter for ID
    public void setId(String id) {
        this.id = id;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //getter for name
    public String getName() {
        return name;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //getter for score
    public String getScore() {
        return score;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //setter for name
    public void setName(String name) {
        this.name = name;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //setter for score
    public void setScore(String score) {
        this.score = score;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //setter for time
    public void setTime(String time) {
        this.time = time;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //getter for time
    public String getTime() {
        return time;
    }


    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //Comparator for score comparison
    public static Comparator<ScoreDataModel> firstNameComparator = new Comparator<ScoreDataModel>() {

        public int compare(ScoreDataModel c1, ScoreDataModel c2) {

            //scores are to be compared in descending order
            int value1=c1.getScore().compareTo(c2.getScore());

            if(value1==0){
                //timings are to given in ascending order
                return c1.getTime().compareTo(c2.getTime());
            }
            return value1;
        }};

    @Override
    public int compareTo(Object another) {
        return 0;
    }


}
