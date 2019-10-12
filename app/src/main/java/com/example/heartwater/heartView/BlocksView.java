package com.example.heartwater.heartView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BlocksView extends View {

    public BlocksView(Context context) {
        super(context);
    }

    public BlocksView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BlockView.getBlock(500,500).drawBlock(canvas);
    }
}
