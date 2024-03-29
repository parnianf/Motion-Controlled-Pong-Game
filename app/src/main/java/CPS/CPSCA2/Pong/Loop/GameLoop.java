package CPS.CPSCA2.Pong.Loop;


import android.util.Pair;

import CPS.CPSCA2.Pong.Activity.GameView;
import CPS.CPSCA2.Pong.Domain.Ball;
import CPS.CPSCA2.Pong.Domain.Coordinate;
import CPS.CPSCA2.Pong.Domain.Paddle;

public class GameLoop extends Thread {
    private final float deltaT;
    Ball ball;
    Paddle paddle;
    GameView gameView;
    boolean isRunning;
    Pair<Integer, Integer> screen;
    private final String mode;


    public GameLoop(GameView view, float dt, Pair<Integer, Integer> screen, String mode) {
        this.gameView = view;
        this.deltaT = dt;
        isRunning = true;
        this.screen = screen;
        initiateGame();
        this.mode = mode;
    }

    public void updateBallAcceleration(float az) {
        ball.setZAcceleration(az);
    }

    public void initiateGame() {
        ball = new Ball(new Coordinate(Integer.parseInt(String.valueOf(screen.first / 2)), 10, 0), new Coordinate(0, 0, 0), new Coordinate(0, 2000, 0), //TODO: why 1000?
                screen.first, screen.second, 10, mode);

        int paddleStartPositionX = Integer.parseInt(String.valueOf(screen.first / 3));
        int paddleStopPositionX = Integer.parseInt(String.valueOf(2 * screen.first / 3));
        int paddlePositionY = Integer.parseInt(String.valueOf(3 * screen.second / 4));

        paddle = new Paddle(new Coordinate(paddleStartPositionX, paddlePositionY, 0), new Coordinate(paddleStopPositionX, paddlePositionY, 0), new Coordinate(0, 0, 0), new Coordinate((float) 0.1, 0, 0), screen);
    }

    public void endLoop() {
        isRunning = false;
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y2 - y1, 2));
    }

    public float calculateDistanceFromCircleToLineSegment(float lineStartX, float lineStartY, float lineEndX, float lineEndY, float circleX, float circleY, float circleRadius) {
        float lineLength = distance(lineStartX, lineStartY, lineEndX, lineEndY);
        if (lineLength == 0f) {
            // The line segment is just a point, so return the distance between the circle center and that point
            return distance(circleX, circleY, lineStartX, lineStartY) - circleRadius;
        }

        // Calculate the projection of the circle center onto the line segment
        float t = ((circleX - lineStartX) * (lineEndX - lineStartX) + (circleY - lineStartY) * (lineEndY - lineStartY)) / (lineLength * lineLength);
        t = Math.max(0f, Math.min(1f, t)); // Clamp t to ensure it's within the line segment range

        float projectionX = lineStartX + t * (lineEndX - lineStartX);
        float projectionY = lineStartY + t * (lineEndY - lineStartY);

        // Calculate the distance between the circle center and the projection point
        float distanceToProjection = distance(circleX, circleY, projectionX, projectionY);

        // Calculate the distance between the circle center and the line segment

        return distanceToProjection - circleRadius;
    }

    public void updatePaddleXAcceleration(Coordinate a, double deltaTime) {
        if (Math.abs(a.x) < 20) {
            a.x = 0;
        }
        paddle.setAcceleration(a);
//        this.deltaT = deltaTime;
        paddle.setPaddleCenter(deltaTime);
    }

    public void updatePaddleAngularVelocity(Coordinate angularVelocity, double deltaTime) {
        paddle.setTheta(angularVelocity, deltaTime);
    }

    @Override
    public void run() {
        boolean isCollision = false;
        super.run();
        while (true) {
            try {
                if (gameView.getRestart()) {
                    initiateGame();
                    gameView.setRestart(false);
                }

                Coordinate ballPos = ball.getPosition();
                Coordinate paddleStartPos = paddle.getStartPosition();
                Coordinate paddleStopPos = paddle.getStopPosition();


                if (calculateDistanceFromCircleToLineSegment(paddleStartPos.x, paddleStartPos.y, //collision darim
                        paddleStopPos.x, paddleStopPos.y, ballPos.x, ballPos.y, ball.getRadius()) < ball.getRadius() + 20) {  // TODO: 30 or 0??
//                    ball.reverseBallVelocity();
                    if (!isCollision) {
                        isCollision = true;
                        ball.handlePaddleCollisions(paddle.getTheta(), deltaT);
                    }
                } else {
                    isCollision = false;
                    gameView.updateBallPosition((int) ballPos.getX(), (int) ballPos.getY());
                    gameView.updatePaddlePosition((int) paddleStartPos.getX(), (int) paddleStartPos.getY(), (int) paddleStopPos.getX(), (int) paddleStopPos.getY());

                }
                ball.updatePosition(deltaT);
                paddle.updatePosition();
                Thread.sleep((long) (deltaT * 1000)); //1ms
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
