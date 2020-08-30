package com.gucarsoft.becarefulegg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    boolean countDownTimerFinished=false;
    int playerCounts = 0;
    TextView txt;
    ImageView img;
    RelativeLayout rlt;
    Resources res;
    TextView countDownText;
    int count = 1;
    int countDown=0;
    boolean eggStatus = true;
    boolean cover = true;
    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        txt = findViewById(R.id.txt);
        img = findViewById(R.id.img);
        rlt = findViewById(R.id.rlt);
        countDownText=findViewById(R.id.countDown);
        res = getResources();
        img.setImageDrawable(res.getDrawable(R.drawable.eggcover));
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
        playerCounts = Integer.parseInt(getIntent().getStringExtra("playerCounts"));
        txt.setText("Player " + count);

        countDown = Integer.parseInt(getIntent().getStringExtra("countDown"));
        if(countDown==100)
        {
            countDownTimerFinished=true;
            countDownText.setText("∞");
        }
        else{
            countDownText.setText(countDown+"");
        }

        timer();


    }

    public void game(int tap) {

        if (gameOver == false) {

            if (tap == 1) {
                if (cover != true) {
                    gameOver = true;
                }
                else{
                    changePlayer();
                }
            } else if (tap == 2) {
                if (cover == true) {
                    cover = false;
                    changePlayer();
                } else {
                    cover = true;
                    changePlayer();
                }

            }

        }

        images();
    }

    public void images() {
        if (gameOver == false) {

            if (cover == true) {
                img.setImageDrawable(res.getDrawable(R.drawable.eggcover));
            } else if (cover == false) {
                img.setImageDrawable(res.getDrawable(R.drawable.egg));
            }
        } else {
            img.setImageDrawable(res.getDrawable(R.drawable.eggbroken));
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            gameOver = false;
                            eggStatus = true;
                            cover = true;
                            count=1;
                            txt.setText("Player " + count);
                            countDownTimerFinished=false;
                            timer();
                            images();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            Intent intent = new Intent(Game.this, MainActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
            builder.setMessage("Player " + count + " lose!\nDo you want to play again?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }

    public void changePlayer() {
        if (count < playerCounts) {
            count += 1;
        } else {
            count = 1;
        }
        txt.setText("Player " + count);

    }

    public void timer(){
        if (countDownTimerFinished==false) {
            new CountDownTimer(countDown, 1000) {

                public void onTick(long millisUntilFinished) {
                    countDownText.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    countDownText.setText("finished!");
                    countDownTimerFinished = true;
                    gameOver = true;
                    images();
                }
            }.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimerFinished=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimerFinished=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerFinished=true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    boolean doubleTap = true;

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        if (doubleTap == true) {
            game(2);
            doubleTap = false;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTap = true;
            }
        }, 150);
        return true;
    }

    boolean oneTap = true;

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        if (oneTap == true) {
            game(1);
            oneTap = false;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                oneTap = true;
            }
        }, 150);

        return true;
    }


}

