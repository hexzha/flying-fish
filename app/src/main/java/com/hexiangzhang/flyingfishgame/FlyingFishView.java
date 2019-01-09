package com.hexiangzhang.flyingfishgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class FlyingFishView extends View {
    private Bitmap fish;
    private Bitmap backgroundImage;
    private Bitmap life[] = new Bitmap[2];
    private Paint scorePaint = new Paint();

    public FlyingFishView(Context context) {
        super(context);

        fish = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(backgroundImage, 0, 0, null);
        canvas.drawBitmap(fish, 0, 0, null);
        canvas.drawText("Score: ", 20, 60, scorePaint);

        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 680, 10, null);
        canvas.drawBitmap(life[0], 780, 10, null);

    }
}
