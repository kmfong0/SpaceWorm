package com.example.spaceworm;

import static java.lang.Math.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

//Supernovas will spawn every x amount of stars eaten
//Supernovas will harm the Spaceworm if it tries to eat one (lose a point and segment)
// Supernovas are worth 3 points. They jump around after a certain amount of time.
// if the worm didn't eat the supernova after 2 jumps, the supernova
// is replaced with a normal star.
public class Supernova extends Star implements IDrawable {
    private Bitmap mBitmapSupernova;
    private int timeCounter = 0;
    private int jumpCounter=0;
    private final StarType type = StarType.supernova;

    public Supernova(Context c, Point sr, int size) {
        super(sr, size);
        isActive = true;
        points = randPoints();
        segmentAmount = -points;
        mBitmapSupernova = BitmapFactory.decodeResource(c.getResources(), R.drawable.supernova);
        // Resize the bitmap
        mBitmapSupernova = Bitmap.createScaledBitmap(mBitmapSupernova, size*2, size*2, false);
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        int xcoord = (int)round((location.x-0.5)*mSize);
        int ycoord = (int)round((location.y-0.5)*mSize);
        if (isActive){
            canvas.drawBitmap(mBitmapSupernova,
                    xcoord, ycoord, paint);
        }
    }

    @Override
    public void updateStar(){
        if (timeCounter == 30){    // if number of frames has been reached
            SoundManager.playNovaTele();
            timeCounter = 0;
            this.spawn();                // supernova should jump
            timeCounter++;
            jumpCounter++;          // increase jump counter
            if(jumpCounter == 3 ){
                SoundManager.playNovaGone();
                isActive = false;   // remove from map at next update
                jumpCounter = 0;    // reset jump counter
            }
        }
        else {
            timeCounter++;
        }
    }
    public StarType getType(){
        return type;
    }
    boolean checkActive(){ return isActive; }

    @Override
    public int points(){
        points = this.randPoints()+3;
        segmentAmount = -points;
        return points;
    }
}
