package com.example.aditya.breakoutgame;

/**
 * Created by aditya on 11/17/15.
 */
import android.graphics.RectF;

public class Brick {

    private RectF rect;

    private boolean isVisible;

    /*gives the type of brick*/
    public int type;

    /*gives the number of hits required inorder to become invisible*/
    public int hits;

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