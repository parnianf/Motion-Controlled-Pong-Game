package CPS.CPSCA2.Pong.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    Context context;
    private final Paint mPaint;
    int ballX = 0, ballY = 0;
    int paddleStartX, paddleStartY, paddleStopX, paddleStopY;
    boolean restart = false;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("white"));
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(ballX, ballY, 50, mPaint);
        canvas.drawLine(paddleStartX, paddleStartY, paddleStopX, paddleStopY, mPaint);
    }

    public void updateBallPosition(int x, int y) {
        this.ballX = x;
        this.ballY = y;
        invalidate();
    }

    public void updatePaddlePosition(int paddleStartX, int paddleStartY, int paddleStopX, int paddleStopY) {
        this.paddleStartX = paddleStartX;
        this.paddleStartY = paddleStartY;
        this.paddleStopX = paddleStopX;
        this.paddleStopY = paddleStopY;
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {// Check if touch point is on the line
            if (isTouchOnLine(touchX, touchY)) {
                restart = true;
            }
        }

        return true; // Return true to indicate that we've handled the event
    }

    public boolean getRestart() {
        return restart;
    }

    public void setRestart(boolean restart_) {
        restart = restart_;
    }

    private boolean isTouchOnLine(float touchX, float touchY) {
        // Calculate the distance from the touch point to the line using line equation
        float distance = Math.abs((paddleStopY - paddleStartY) * touchX - (paddleStopX - paddleStartX) * touchY + paddleStopX * paddleStartY - paddleStopY * paddleStartX) / (float) Math.sqrt(Math.pow(paddleStopY - paddleStartY, 2) + Math.pow(paddleStopX - paddleStartX, 2));

        // Define a threshold to determine if the touch point is close enough to the line
        float threshold = 30f;

        return distance <= threshold;
    }
}
