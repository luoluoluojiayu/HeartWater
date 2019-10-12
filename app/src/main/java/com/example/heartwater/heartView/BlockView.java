package com.example.heartwater.heartView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class BlockView {
    int centerX;
    int centerY;
    int blockWidth = 100;     //半径
    Paint paint = new Paint();

    public BlockView(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        paint.setColor(Color.parseColor("#FCE5DF"));
        paint.setStyle(Paint.Style.FILL);
    }

    public void drawBlock(Canvas canvas) {
        Path path = new Path();
        path.moveTo(centerX - blockWidth, centerY - blockWidth);
        path.quadTo(centerX, centerY - blockWidth, centerX + blockWidth, centerY - blockWidth);
        path.quadTo(centerX + blockWidth, centerY, centerX + blockWidth, centerY + blockWidth);
        path.quadTo(centerX, centerY + blockWidth, centerX - blockWidth, centerY + blockWidth);
        path.quadTo(centerX - blockWidth, centerY, centerX - blockWidth, centerY - blockWidth);
        path.close();
        canvas.drawPath(path, paint);
    }

    public void drawChoiceBlock(Canvas canvas) {

    }

    public void changeTop(Path path, int touchX, int touchY) {
//        if(Math.abs(touchX - centerX) <= blockWidth&&)
    }

    public boolean hasChoice(int touchX, int touchY) {
        if (Math.abs(touchX - centerX) <= blockWidth && Math.abs(touchY - centerY) <= blockWidth)
            return true;
        else return false;
    }


    public static BlockView getBlock(int centerX, int centerY) {
        return new BlockView(centerX, centerY);
    }
}
