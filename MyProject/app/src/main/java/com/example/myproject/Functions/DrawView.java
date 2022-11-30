package com.example.myproject.Functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();
    View startView;
    View endView;

    public DrawView(Context context,View startView,View endView) {
        super(context);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        this.startView = startView;
        this.endView = endView;

    }

    @SuppressLint("NewApi")
    public void onDraw(Canvas canvas) {
        canvas.drawLine(startView.getX()+20, startView.getY()+20, endView.getX()+20, endView.getY()+20, paint);
    }

}