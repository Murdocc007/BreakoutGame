package com.example.aditya.breakoutgame;

/**
 * Created by aditya on 11/17/15.
 */
//import android.graphics.RectF;

import java.util.Random;

public class Ball {
//    RectF rect;
    float xVelocity;
    float yVelocity;
//    float ballWidth = 10;
//    float ballHeight = 10;
    float centerX;
    float centerY;
    float radius;

    public Ball(int screenX, int screenY){

        // Start the ball travelling straight up at 100 pixels per second
        xVelocity = 100;
        yVelocity = -200;

        // Place the ball in the centre of the screen at the bottom
        // Make it a 10 pixel x 10 pixel square
//        rect = new RectF();

        radius=screenX/12;
    }

//    public RectF getRect(){
//        return rect;
//    }

    public void update(long fps){
//        rect.left = rect.left + (xVelocity / fps);
//        rect.bottom = rect.bottom + (yVelocity / fps);
//        rect.right = rect.left + ballWidth;
//        rect.top = rect.bottom - ballHeight;
        centerX=centerX+(xVelocity/fps);
        centerY=centerY+(yVelocity/fps);
    }

    public float getX(){
        return centerX;
    }

    public float getY(){
        return centerY;
    }

    public float getR(){
        return radius;
    }

    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = 7-generator.nextInt(8);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    public void clearObstacleY(float y){
        centerY = y;
    }

    public void clearObstacleX(float x){
        centerX=x;
    }

    public void reset(int x, int y){
        centerX = x / 2;
        centerY= y - 350;
    }

}

