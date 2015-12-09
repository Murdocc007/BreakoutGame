package com.example.aditya.breakoutgame.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;



import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.aditya.breakoutgame.models.Ball;
import com.example.aditya.breakoutgame.models.Brick;
import com.example.aditya.breakoutgame.models.ScoreDataModel;
import com.example.aditya.breakoutgame.utilities.FileHandler;
import com.example.aditya.breakoutgame.models.Paddle;
import com.example.aditya.breakoutgame.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class BreakoutGame extends AppCompatActivity {

    // breakoutView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    BreakoutView breakoutView;
    LayoutParams params;
    Button playButton, pauseButton;
    ImageView imageView;
    SeekBar ballSpeedSeekbar;
    public static long fps = 50;

    //This variable becomes 1 as soon as we start the game
    int firstTimeRun=0;

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/1/2015
    //onCreate Function of the Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        breakoutView = new BreakoutView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_toolbar, layout, false);
        layout.addView(v);
        layout.addView(breakoutView);


        params = layout.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        // Initialize gameView and set it as the view

        setContentView(layout);

        ballSpeedSeekbar=(SeekBar)findViewById(R.id.seekBar);
        imageView = (ImageView)findViewById(R.id.imageView);
        playButton=(Button)findViewById(R.id.playButton);
        pauseButton=(Button)findViewById(R.id.pauseButton);
        playButton.setVisibility(View.INVISIBLE);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakoutView.pause();
                startActivity(new Intent(getApplicationContext(), HallOfFame.class));
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                breakoutView.resume();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);
                breakoutView.pause();
            }
        });

        ballSpeedSeekbar.setProgress(50);
        ballSpeedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                fps = (99 - progressChanged);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                fps = (99 - progressChanged);
                Toast.makeText(getApplicationContext(), "Speed set to:" + progressChanged,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

     // Here is our implementation of BreakoutView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside the BreakoutGame class
    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class BreakoutView extends SurfaceView implements Runnable,SensorEventListener {

        FileHandler fileHandler = new FileHandler(getApplicationContext());
        ScoreDataModel scoreDataModel = new ScoreDataModel();
        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // Game is paused at the start
        boolean paused = true;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
       // public static fps;

        //The pause which helps us retain the state of the game
        private Object pauseLock;
        private boolean mPause;



        // The size of the screen in pixels
        int screenX;
        int screenY;

        //Sensor Manager for the Accelerometer
        private SensorManager senSensorManager;
        private Sensor senAccelerometer;
        private long lastUpdate = -1;
        private float last_x, last_y, last_z;
        private static final int SHAKE_THRESHOLD = 800;

        //The maximum score of the game
        int maxScore=0;

        // The players paddle
        Paddle paddle;

        // A ball
        Ball ball;

        //Used for storing the start time of the game
        long startTime;
        long timer;
        int firstTime=0;

        //This stores the the x coordinate of the initial touch
        float initialTouch=0;

        // Up to 200 bricks
        Brick[] bricks = new Brick[200];
        int numBricks = 0;

        // For sound FX
        SoundPool soundPool;
        int beep1ID = -1;
        int beep2ID = -1;
        int beep3ID = -1;
        int loseLifeID = -1;
        int explodeID = -1;

        // The score
        int score = 0;


         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
         //Constructor of the BreakoutView class
        // When the we initialize (call new()) on gameView
        // This special constructor method is called
        public BreakoutView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            // Get a Display object to access screen details
            Display display = getWindowManager().getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            paddle = new Paddle(screenX, screenY);

            // Create a ball
            ball = new Ball(screenX, screenY);

            //Initialize the pause lock
            pauseLock=new Object();
            mPause=false;

            // Load the sounds

            // This SoundPool is deprecated but don't worry
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

            try{
                // Create objects of the 2 required classes
                AssetManager assetManager = context.getAssets();
                AssetFileDescriptor descriptor;

                // Load our fx in memory ready for use
                descriptor = assetManager.openFd("beep1.ogg");
                beep1ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("beep2.ogg");
                beep2ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("beep3.ogg");
                beep3ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("loseLife.ogg");
                loseLifeID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("explode.ogg");
                explodeID = soundPool.load(descriptor, 0);

            }catch(IOException e){
                // Print an error message to the console
                Log.e("error", "failed to load sound files");
            }


            senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);


            createBricksAndRestart();

        }

         //Name: Aditya Mahajan
         //NetId:axm156630@utdallas.edu
         //Date:11/1/2015
         //function to return the score, which is required while passing score to the next activity
        public int getScore(){
            return score;
        }


         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
         //function to return the Time taken, which is required while passing score to the next activity
        public int getTime(){
            return (int)timer;
        }


         //Name: Aditya Mahajan
         //NetId:axm156630@utdallas.edu
         //Date:11/1/2015
         //this function is called when rhe game gets over so as to reset the position of all
         //elements (Ball, Bricks, Paddle) as it was at the start of the game
         public void createBricksAndRestart(){



             // Put the ball back to the start
             ball.reset(screenX, screenY);
             paddle.reset(screenX,screenY);
             int brickWidth = screenX ;
             int brickHeight = screenX/12 ;

             //Initializing the timer variables
             startTime=0;
             timer=0;
             firstTime=0;
             maxScore=0;


             Random r=new Random();

             // Build a wall of bricks
             numBricks = 0;
             for(int column = 0; column < 8; column ++ ){
                 for(int row = 2; row < 5; row ++ ){
                     bricks[numBricks] = new Brick(row, column, brickWidth/(9-row), brickHeight);
                     if(bricks[numBricks].getRect().left>0 && bricks[numBricks].getRect().right<screenX)
                     {
                         int temp=0;

                     while(temp==0)
                     {
                         temp=r.nextInt(7-1);
                     }
                     while(numBricks>=3 && bricks[numBricks-3].type==temp)
                     {
                         temp=r.nextInt(7-1);
                     }
                     maxScore+=temp;
                     bricks[numBricks].type=temp;
                     bricks[numBricks].hits=bricks[numBricks].type;
                     numBricks ++;
                     }
                 }

             }

             score=0;
             paused=true;

         }

        @Override
        public void run() {
            while (playing) {
                synchronized (pauseLock) {
                    while (mPause) {
                        try {
                            pauseLock.wait();
                            } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                // Update the frame
                if(!paused){

                    if(firstTime==0)
                    {
                        startTime=System.currentTimeMillis();
                        firstTime=1;
                    }
                    timer=System.currentTimeMillis()-startTime;
                    update();
                }

                // Draw the frame
                draw();

                //Used to control the speed
                //fps=50;

            }

        }

         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
        // Everything that needs to be updated goes in here
        // Movement, collision detection etc.
        public void update() {

            // Move the paddle if required
            paddle.update(fps);

            ball.update(fps);

            // Check for ball colliding with a brick
            for(int i = 0; i < numBricks; i++){

                if (bricks[i].getVisibility()){

                    if(intersects(bricks[i].getRect(), ball)) {
                        bricks[i].hits--;
                        if(bricks[i].hits==0) {
                            bricks[i].setInvisible();
                        }
                        ball.reverseYVelocity();
                        score = score + 10;

//                        soundPool.play(explodeID, 1, 1, 0, 0, 1);
                    }
                }
            }

            // Check for ball colliding with paddle
            if(intersects(paddle.getRect(), ball)) {
                ball.setRandomXVelocity();
                ball.reverseYVelocity();
                ball.clearObstacleY(paddle.getRect().top - ball.getR() - 2);

//                soundPool.play(beep1ID, 1, 1, 0, 0, 1);
            }

            // Bounce the ball back when it hits the bottom of screen
            if(ball.getY()+ball.getR() > screenY){
                ball.reverseYVelocity();
                ball.clearObstacleY(screenY - ball.getR() - 2);

//                soundPool.play(loseLifeID, 1, 1, 0, 0, 1);

                scoreDataModel.setScore(Integer.toString(getScore()));
                scoreDataModel.setTime(Integer.toString(getTime()));
                if(fileHandler.isInTopTen(scoreDataModel) == 1) {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(getScore());
                    arrayList.add(getTime());
                    createBricksAndRestart();
                    startActivity(new Intent(getApplicationContext(), ScoreEntry.class).putIntegerArrayListExtra("caller", arrayList));
                }else {
                    createBricksAndRestart();
                }

            }

            // Bounce the ball back when it hits the top of screen
            if(ball.getY()-ball.getR() < 0){
                ball.reverseYVelocity();
                ball.clearObstacleY(ball.getR()+12);

//                soundPool.play(beep2ID, 1, 1, 0, 0, 1);
            }

            // If the ball hits left wall bounce
            if(ball.getX()-ball.getR() < 0){
                ball.reverseXVelocity();
                ball.clearObstacleX(ball.getR() + 2);

//                soundPool.play(beep3ID, 1, 1, 0, 0, 1);
            }

            // If the ball hits right wall bounce
            if(ball.getX()+ball.getR() > screenX - 10){
                ball.reverseXVelocity();
                ball.clearObstacleX(screenX - ball.getR() - 22);

//                soundPool.play(beep3ID, 1, 1, 0, 0, 1);
            }

            // Pause if cleared screen
            if(score==maxScore*10){
                paused = true;
                //Akash---

                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(getScore());
                arrayList.add(getTime());
                createBricksAndRestart();

                startActivity(new Intent(getApplicationContext(), ScoreEntry.class).putIntegerArrayListExtra("caller", arrayList));

            }

        }

         //Name: Aditya Mahajan
         //NetId:axm156630@utdallas.edu
         //Date:11/1/2015
        // Function to draw the newly updated scene (Ball, paddle, bricks etc.)
        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.rgb( 190, 190, 190));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(204, 101, 37, 1));

                // Draw the paddle
                canvas.drawRect(paddle.getRect(), paint);


                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 255, 255, 255));

                // Draw the ball
                canvas.drawCircle(ball.centerX, ball.centerY, ball.radius, paint);

                // Change the brush color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));
                paint.setUnderlineText(true);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                // Draw the bricks if visible
                for(int i = 0; i < numBricks; i++){
                    if(bricks[i].getVisibility()) {
                        if(bricks[i].type==5)
                            paint.setColor(Color.argb(150,000, 000, 000));//black color
                        else if(bricks[i].type==4)
                            paint.setColor(Color.argb(255, 255, 000, 000));//Red color
                        else if(bricks[i].type==3)
                            paint.setColor(Color.argb(255, 000, 255, 000));//Green color
                        else if(bricks[i].type==2)
                            paint.setColor(Color.argb(255, 000, 000, 255));//Blue color
                        else
                            paint.setColor(Color.argb(255,255,255,255));//white color
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  255, 255, 255));

                // Draw the score
                paint.setTextSize(40);
                canvas.drawText("Score: " + score +
                        "  Timer :"+timer, 150,50, paint);




                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
         //function that decides whether the ball stricks the brick
        public boolean intersects(RectF rect,Ball ball){

            if((abs(ball.getX() -rect.left)< ball.getR() || abs(ball.getX() -rect.right)< ball.getR() ))
            {
                if (abs(ball.getY()-rect.top)<=ball.getR())//Top right or Top left
                    return true;
                else if(abs(ball.getY()-rect.bottom)<=ball.getR())//Bottom right or bottom left
                    return true;
                else if((ball.getX()-rect.top)<=0 && (ball.getX()-rect.bottom)>=0)//Ball has hit the middle of the brick on the right or on the left
                    return true;
                else return false;
            }


            return false;
        }


         //Name: Aditya Mahajan
         //NetId:axm156630@utdallas.edu
         //Date:11/1/2015
        // when game is paused/stopped by the user
        // shutdown our thread.
        public void pause() {
            //Unregistering the sensor manager
            senSensorManager.unregisterListener(this);
//            playing = false;
            mPause=true;

        }

         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
        // when game is started theb
        // start our thread.
        public void resume() {
            //Registering the sensor manager
            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            playing = true;
            if(firstTimeRun==0)
            {
                firstTimeRun=1;
                gameThread = new Thread(this);
                gameThread.start();
            }
            else{
                synchronized (pauseLock)
                {
                    mPause=false;

                    pauseLock.notifyAll();
                }
            }

        }

         //Name: Aditya Mahajan
         //NetId:axm156630@utdallas.edu
         //Date:11/1/2015
        //The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {


            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:
                    paused = false;
                    initialTouch=motionEvent.getX();
                    break;

                //Player is moving the finger on the screen
                case MotionEvent.ACTION_MOVE:
                    float x=motionEvent.getX();
                    if(x-initialTouch>0)
                        paddle.setMovementState(paddle.RIGHT);
                    else if(x-initialTouch<0)
                        paddle.setMovementState(paddle.LEFT);
                    else
                    {
                        if(x-paddle.getRect().left<=0)
                            paddle.setMovementState(paddle.LEFT);
                        if(x-paddle.getRect().right>=0)
                            paddle.setMovementState(paddle.RIGHT);
                    }
                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    paddle.setMovementState(paddle.STOPPED);
                    break;
            }
//            update();
            return true;
        }


         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
         //to make the ball round
        public  float Round(float Rval, int Rpl) {
            float p = (float)Math.pow(10,Rpl);
            Rval = Rval * p;
            float tmp = Math.round(Rval);
            return (float)tmp/p;
        }

         //Name: Aditya Mahajan
         //NetId:axm156630@utdallas.edu
         //Date:11/1/2015
         //event driven callback which is invoked on device rotation/ orientation
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long curTime = System.currentTimeMillis();
                // only allow one update every 100ms.
                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;


                    float x = event.values[SensorManager.DATA_X];
                    float y = event.values[SensorManager.DATA_Y];
                    float z = event.values[SensorManager.DATA_Z];

                    float prevVelocity=ball.xVelocity;

                    //Left Shake
                    if(Round(x,4)>5.0000){
                        ball.xVelocity-=150;
                    }//Right shake
                    else if(Round(x,4)<-5.0000){
                        ball.xVelocity+=150;
                    }
                    else
                    {
                        ball.xVelocity=prevVelocity;
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }

         //Name: Akash Chaturvedi
         //NetId:axc144430@utdallas.edu
         //Date:11/1/2015
         //auto generated callback
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    // This is the end of our BreakoutView inner class

    //Name: Aditya Mahajan
    //NetId:axm156630@utdallas.edu
    //Date:11/1/2015
    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        breakoutView.resume();
    }

    //Name: Akash Chaturvedi
    //NetId:axc144430@utdallas.edu
    //Date:11/1/2015
    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        breakoutView.pause();
    }

}