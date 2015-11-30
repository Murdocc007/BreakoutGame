package com.example.aditya.breakoutgame.models;

import java.util.Random;

public class Ball {
    public float xVelocity;
    public float yVelocity;
    public float centerX;
    public float centerY;
    public float radius;

    public Ball(int screenX, int screenY){

        xVelocity = 100;
        yVelocity = -200;

        radius=screenX/12;
    }


    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //update is called to update the ball position, velocity and direction
    public void update(long fps){
        centerX=centerX+(xVelocity/fps);
        centerY=centerY+(yVelocity/fps);
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //get x coordinate of ball
    public float getX(){
        return centerX;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //get y coordinate of ball
    public float getY(){
        return centerY;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //get radius of ball
    public float getR(){
        return radius;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //get reverse Y velocity of the ball
    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //get reverse X velocity of the ball
    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }


    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //set random X velocity of the ball
    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = 7-generator.nextInt(8);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //get reverse velocity of the ball
    public void clearObstacleY(float y){
        centerY = y;
    }

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/18/2015
    //clearObsticles of the ball (place the ball at passed x argument)
    public void clearObstacleX(float x){
        centerX=x;
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/18/2015
    //reset ball position
    public void reset(int x, int y){
        centerX = x / 2;
        centerY= y - 350;
    }

}

