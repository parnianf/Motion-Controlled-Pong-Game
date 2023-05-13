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
    int paddleX, paddleY, paddleWidth, paddleHeight;



    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("purple"));
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
        canvas.drawRect(paddleX-Integer.parseInt(String.valueOf(paddleWidth/2)), paddleY-Integer.parseInt(String.valueOf(paddleHeight/2)), paddleX+Integer.parseInt(String.valueOf(paddleWidth/2)), paddleY+Integer.parseInt(String.valueOf(paddleHeight/2)), mPaint);
//        canvas.drawRect(50,50,100,100, mPaint);
    }

    public void updateBallPosition(int x, int y) {
        this.ballX = x;
        this.ballY = y;
        invalidate();
    }

    public void updatePaddlePosition(int paddleX, int paddleY, int paddleWidth, int paddleHeight) {
        this.paddleX = paddleX;
        this.paddleY = paddleY;
        this.paddleWidth = paddleWidth;
        this.paddleHeight  =paddleHeight;
        invalidate();
    }
}
