package com.example.spaceworm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class AsteroidBelt extends GameObject implements IDrawable{
    private static AsteroidBelt mAsteroidBelt = null;
    // The location of the initial asteroid on the grid
    // Not in pixels
    private final Point location = new Point();
    private Bitmap mBitmapAsteroid;
    private Bitmap mBitmapSmallAsteroid;
    // used for drawing.
    private ArrayList<Point> wallLocations;
    // used for determining which spaces are occupied by asteroids
    private static boolean[][] asteroidMap;
    private Difficulty difficulty;
    private ArrayList<AsteroidCluster> clusters;
    private static int rangeX;
    private static int rangeY;
    private final boolean isActive = true;

    private AsteroidBelt(Point sr, int s, Difficulty d){
        super(sr, s);
        difficulty = d;
        rangeX = sr.x;
        rangeY = sr.y;
        clusters = new ArrayList<>();
        // make asteroidMap the size of the available game blocks
        asteroidMap = new boolean[mSpawnRange.x][mSpawnRange.y];
    }

    static AsteroidBelt getInstance(Point sr, int s, Difficulty d){
        if(mAsteroidBelt == null) {
            mAsteroidBelt = new AsteroidBelt(sr, s, d);
        }
        return mAsteroidBelt;
    }
    public void spawn(){  }

    public void spawn(Context context, Difficulty difficulty){
        int numberOfBelts;
        int maxBeltLength;
        int wallSize;
        wallLocations = new ArrayList<Point>();
        //reset clusters and asteroidMap
        resetAsteroids();

        if (difficulty == Difficulty.Easy){
            numberOfBelts = 3;
            maxBeltLength = 6;
        }
        else if (difficulty == Difficulty.Medium){
            numberOfBelts = 4;
            maxBeltLength = 10;
        }
        else{
            numberOfBelts = 5;
            maxBeltLength = 14;
        }
        makeBelt(context, numberOfBelts, maxBeltLength);
    }

    private void makeBelt(Context context, int numberOfBelts, int beltSize){
        for(int i = 0; i < numberOfBelts; i++){
            AsteroidCluster x = new AsteroidCluster(context, mSpawnRange, mSize);
            clusters.add(x);
            Point p = validCoord();
            while (p.x == mSpawnRange.x || p.x == 0 ||p.y == 0 || p.y == mSpawnRange.y)
                p = validCoord();
            asteroidMap = x.spawn(beltSize, validCoord(), asteroidMap);
        }
        emptyCenterScreen();
    }

    private void emptyCenterScreen(){
        int yClearLow = mSpawnRange.y/2 - mSpawnRange.y/10;
        int yClearHigh = mSpawnRange.y/2 + mSpawnRange.y/10;
        for (int i=19; i < 30; i++){
            for (int j=yClearLow; j<= yClearHigh; j++) {
                asteroidMap[i][j] = false;
                for (AsteroidCluster x: clusters){
                    x.clearAsteroid(new Point(i,j));
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        try {
            for(AsteroidCluster x : clusters){
                if(x != null)
                    x.draw(canvas, paint, mSize);
            }
        } catch (ConcurrentModificationException e) {
            // TODO: I was seeing this error so I put a try catch here. Does this break anything?
        }
    }
    // returns a coordinate that does not contain an asteroid.
    public static Point validCoord(){
        Random random = new Random();
        int x = random.nextInt(rangeX - 2) + 1;
        int y = random.nextInt(rangeY - 2) + 1;
        while(asteroidMap[x][y]) {
            x = random.nextInt(rangeX - 2) + 1;
            y = random.nextInt(rangeY - 2) + 1;
        }
        return new Point(x,y);
    }
    
    static boolean[][] getAsteroidMap() {
        boolean[][] aMap = asteroidMap;
        return aMap;
    }

    public void resetAsteroids(){
        clusters.clear();
        for (boolean[] row : asteroidMap){
            Arrays.fill(row, false);
        }
    }
    public boolean checkActive(){ return isActive; }
}
