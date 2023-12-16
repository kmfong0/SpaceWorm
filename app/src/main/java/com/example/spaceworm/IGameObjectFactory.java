package com.example.spaceworm;

import android.content.Context;


abstract class IGameObjectFactory {
    protected final Context context;
    protected final int NUM_BLOCKS_WIDE;
    protected final int mNumBlocksHigh;
    protected final int blockSize;
    //protected final GameObjectCollection gameObjects;
    protected Difficulty difficulty = Difficulty.Easy;

    public IGameObjectFactory(Context context, int NUM_BLOCKS_WIDE, int mNumBlocksHigh, int blockSize) {
        this.context = context;
        this.NUM_BLOCKS_WIDE = NUM_BLOCKS_WIDE;
        this.mNumBlocksHigh = mNumBlocksHigh;
        this.blockSize = blockSize;
        //this.gameObjects = gameObjects;
    }


    abstract GameObject createObject();

    protected void setDifficulty(Difficulty d){
        difficulty = d;
    }
}
