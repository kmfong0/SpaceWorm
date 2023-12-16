package com.example.spaceworm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

//WormHoles will spawn every x amount of stars eaten
//Each wormhole will have a partner location that will
// be where the wormhole will transport the SpaceWorm
public class WormHole extends GameObject implements IDrawable {
    private static WormHole instance = null;
    private Point partnerLocation = new Point();
    private Bitmap mBitmapWormHole;

    private int passthrough = 0;
    private WormHole(Context c, Point sr, int size) {
        super(sr, size);
        mBitmapWormHole = BitmapFactory.decodeResource(c.getResources(), R.drawable.worm_hole);
        // Resize the bitmap
        mBitmapWormHole = Bitmap.createScaledBitmap(mBitmapWormHole, size, size, false);
        isActive = true;
    }

    public static WormHole getWormHoleInstance(Context c, Point sr, int size)
    {
        if(instance == null)
            instance = new WormHole(c, sr, size);
        return instance;
    }
    public static void resetWormHoleInstance(Context c, Point sr, int size)
    {

    }

    public Point getPartnerLocation()
    {
        return partnerLocation;
    }
    void setLocation(int mx, int my, int px, int py) {
        super.setLocation(mx, my);
        partnerLocation.x = px;
        partnerLocation.y = py;
    }

    public int getPassthrough() {
        return passthrough;
    }

    public void updatePassthrough() {
        if(passthrough < 3)
            passthrough += 1;
        else
            passthrough = 0;
    }

    @Override
    public void spawn() {
        SoundManager.playPortal1();
        Random random = new Random();
        int mx = random.nextInt(mSpawnRange.x - 1) + 1;
        int my = random.nextInt(mSpawnRange.y - 1) + 1;
        int px = random.nextInt(mSpawnRange.x - 1) + 1;
        int py = random.nextInt(mSpawnRange.y - 1) + 1;
        //Code here to spawn 2 portal instances
        setLocation(mx, my, px, py);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapWormHole,
                location.x * mSize, location.y * mSize, paint);
        canvas.drawBitmap(mBitmapWormHole,
                partnerLocation.x * mSize, partnerLocation.y * mSize, paint);
    }
}
