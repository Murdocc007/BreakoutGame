package com.example.aditya.breakoutgame;

import java.util.Comparator;

/**
 * Created by aditya on 11/25/15.
 */
public class DataModel implements Comparable {

    private String name;
    private String score;
    private String time;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public static Comparator<DataModel> firstNameComparator = new Comparator<DataModel>() {

        public int compare(DataModel c1, DataModel c2) {

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
