package com.hexiangzhang.flyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private Bitmap backgroundImage;
    private Bitmap life[] = new Bitmap[2];
    private Paint scorePaint = new Paint();

    private int fishX = 10, fishY, fishSpeed;
    private int canvasWidth, canvasHeight;

    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 24;
    private Paint redPaint = new Paint();

    private boolean touch = false;
    private int score, lifeCounterOfFish;

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        int minFishY = fish[0].getHeight();
        int maxFishY = canvas.getHeight() - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if (fishY < minFishY) {
            fishY = minFishY;
        } else if (fishY > maxFishY) {
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY)) {
            score += 10;
            yellowX = -1;
        }

        if (yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }

        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY)) {
            score += 15;
            greenX = -1;
        }

        if (greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }

        canvas.drawCircle(greenX, greenY, 25, greenPaint);

        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY)) {
            lifeCounterOfFish--;
            redX = -1;

            if (lifeCounterOfFish == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameOverIntent);

            }
        }

        if (redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }

        canvas.drawCircle(redX, redY, 30, redPaint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (550 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounterOfFish) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);

            }
        }

        canvas.drawText("Score: " + score, 20, 60, scorePaint);

    }

    public boolean hitBallChecker(int x, int y) {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;

            fishSpeed = -22;
        }
        return true;
    }
}
