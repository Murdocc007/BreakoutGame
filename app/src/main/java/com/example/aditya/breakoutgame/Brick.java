package com.example.aditya.breakoutgame;

/**
 * Created by aditya on 11/17/15.
 */
import android.graphics.RectF;

public class Brick {

    /* type is used to indicate the color of the brick*/
    public int type;
    /*how many times a brick has to be hit inorder to disappear*/
    public int hit;


    private RectF rect;

    private boolean isVisible;

    public Brick(int row, int column, int width, int height){

        isVisible = true;

        int padding = 1;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}