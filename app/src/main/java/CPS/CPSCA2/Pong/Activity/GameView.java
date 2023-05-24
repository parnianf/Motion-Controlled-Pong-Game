package CPS.CPSCA2.Pong.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;

    int ballX = 0, ballY = 0;
    int paddleStartX, paddleStartY, paddleStopX, paddleStopY;


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
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
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
}
