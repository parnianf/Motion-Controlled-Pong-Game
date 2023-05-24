package CPS.CPSCA2.Pong.Loop;


import android.util.Pair;

import CPS.CPSCA2.Pong.Activity.GameView;
import CPS.CPSCA2.Pong.Domain.Ball;
import CPS.CPSCA2.Pong.Domain.Coordinate;
import CPS.CPSCA2.Pong.Domain.Paddle;

public class GameLoop extends Thread {
    private final boolean running;
    private final float deltaT;
    Ball ball;
    Paddle paddle;
    GameView gameView;
    boolean isRunning;


    public GameLoop(GameView view, float dt, Pair<Integer, Integer> screen) {
        this.gameView = view;
        this.running = true;
        this.deltaT = dt;
        isRunning = true;

        ball = new Ball(
                new Coordinate(Integer.parseInt(String.valueOf(screen.first / 2)), 10, 0),
                new Coordinate(0, 0, 0),
                new Coordinate(0, 1000, 0), screen, 10
        );

        int paddleStartPositionX = Integer.parseInt(String.valueOf(screen.first / 3));
        int paddleStopPositionX = Integer.parseInt(String.valueOf(2 * screen.first / 3));
        int paddlePositionY = Integer.parseInt(String.valueOf(3 * screen.second / 4));

        paddle = new Paddle(
                new Coordinate(paddleStartPositionX, paddlePositionY, 0),
                new Coordinate(paddleStopPositionX, paddlePositionY, 0),
                new Coordinate(0, 0, 0),
                new Coordinate(0.1, 0, 0), screen, 400, 200);

    }

    public void endLoop() {
        isRunning = false;
    }

    public boolean isCollision(Coordinate ballPos, Coordinate paddleStartPos, Coordinate paddleStopPos, float ballRadius) {
        double ballX = ballPos.getX();
        double ballY = ballPos.getY();
        double paddleX1 = paddleStartPos.getX();
        double paddleY1 = paddleStartPos.getY();
        double paddleX2 = paddleStopPos.getX();
        double paddleY2 = paddleStopPos.getY();

        // Find the equation of the line that represents the paddle
        double m = (paddleY2 - paddleY1) / (paddleX2 - paddleX1);
        double b = paddleY1 - m * paddleX1;

        // Find the y-coordinate of the line at the x-coordinate of the ball
        double paddleY = m * ballX + b;

        // Check if the ball's y-coordinate falls within the range of y-coordinates that count as a hit
        double t = 10; // thickness of the paddle
        double yMin = paddleY - (double) ballRadius;
        double yMax = paddleY + (double) ballRadius + t;
        return ballY >= yMin && ballY <= yMax;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Coordinate ballPos = ball.getPosition();
                Coordinate paddleStartPos = paddle.getStartPosition();
                Coordinate paddleStopPos = paddle.getStopPosition();

                if (isCollision(ballPos, paddleStartPos, paddleStopPos, ball.getRadius())) {
                    ball.reverseBallVelocity();
                }

                gameView.updateBallPosition((int) ballPos.getX(), (int) ballPos.getY());
                gameView.updatePaddlePosition((int) paddleStartPos.getX(), (int) paddleStopPos.getY(), (int) paddleStopPos.getX(), (int) paddleStopPos.getY());

                ball.updatePosition(deltaT);
                paddle.updatePosition(deltaT);


                Thread.sleep((long) (deltaT * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
