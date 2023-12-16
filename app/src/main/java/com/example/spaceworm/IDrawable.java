package com.example.spaceworm;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface IDrawable {
    void draw(Canvas canvas, Paint paint);
    /*
    default void update(List<IGameObject> gameObjects) {
        // no implementation required (e.g. for apples)
    }
     */
}
