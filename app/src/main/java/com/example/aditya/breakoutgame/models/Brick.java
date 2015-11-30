package com.example.aditya.breakoutgame.models;

import android.graphics.RectF;

public class Brick {

    private RectF rect;

    private boolean isVisible;

    /*gives the type of brick*/
    public int type;

    /*gives the number of hits required inorder to become invisible*/
    public int hits;

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //Constructor of Brick Class
    public Brick(int row, int column, int width, int height){

        isVisible = true;

        int padding = 1;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //returns a RectF object (basically brick dimentions)
    public RectF getRect(){
        return this.rect;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //function to set the visibility of the brick based on the number of hits
    public void setInvisible(){
        isVisible = false;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //function to set the visibility of the brick
    public boolean getVisibility(){
        return isVisible;
    }
}