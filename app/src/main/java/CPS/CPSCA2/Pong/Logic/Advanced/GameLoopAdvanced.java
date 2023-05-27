package CPS.CPSCA2.Pong.Logic.Advanced;


import android.util.Pair;

import CPS.CPSCA2.Pong.Activity.GameView;
import CPS.CPSCA2.Pong.Coordinate.Coordinate;

public class GameLoopAdvanced extends Thread {
    private final boolean running;
    private final float deltaT;
    BallAdvanced ball;
    PaddleAdvanced paddleAdvanced;
    GameView gameView;
    boolean isRunning;
    Pair<Integer, Integer> screen;


    public GameLoopAdvanced(GameView view, float dt, Pair<Integer, Integer> screen) {
        this.gameView = view;
        this.running = true;
        this.deltaT = dt;
        isRunning = true;
        this.screen = screen;
        initiateGame();

    }

    public void initiateGame() {
        ball = new BallAdvanced(
                new Coordinate(Integer.parseInt(String.valueOf(screen.first / 2)), 10, 0),
                new Coordinate(0, 0, 0),
                new Coordinate(0, 2000, 0), //TODO: why 1000?
                screen, 10
        );

        int paddleStartPositionX = Integer.parseInt(String.valueOf(screen.first / 3));
        int paddleStopPositionX = Integer.parseInt(String.valueOf(2 * screen.first / 3));
        int paddlePositionY = Integer.parseInt(String.valueOf(3 * screen.second / 4));

        paddleAdvanced = new PaddleAdvanced(
                new Coordinate(paddleStartPositionX, paddlePositionY, 0),
                new Coordinate(paddleStopPositionX, paddlePositionY, 0),
                new Coordinate(0, 0, 0),
                new Coordinate((float) 0.1, 0, 0), screen, 400, 200);
    }

    public void endLoop() {
        isRunning = false;
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y2 - y1, 2));
    }

    public float calculateDistanceFromCircleToLineSegment(float lineStartX, float lineStartY, float lineEndX,
                                                          float lineEndY, float circleX, float circleY, float circleRadius) {
        float lineLength = distance(lineStartX, lineStartY, lineEndX, lineEndY);
        if (lineLength == 0f) {
            // The line segment is just a point, so return the distance between the circle center and that point
            return distance(circleX, circleY, lineStartX, lineStartY) - circleRadius;
        }

        // Calculate the projection of the circle center onto the line segment
        float t = ((circleX - lineStartX) * (lineEndX - lineStartX) +
                (circleY - lineStartY) * (lineEndY - lineStartY)) / (lineLength * lineLength);
        t = Math.max(0f, Math.min(1f, t)); // Clamp t to ensure it's within the line segment range

        float projectionX = lineStartX + t * (lineEndX - lineStartX);
        float projectionY = lineStartY + t * (lineEndY - lineStartY);

        // Calculate the distance between the circle center and the projection point
        float distanceToProjection = distance(circleX, circleY, projectionX, projectionY);

        // Calculate the distance between the circle center and the line segment
        float distanceToLineSegment = distanceToProjection - circleRadius;

        return distanceToLineSegment;
    }

    public boolean isCollision(Coordinate ballPos, Coordinate paddleStartPos, Coordinate paddleStopPos, float ballRadius) {
        float ballX = ballPos.getX();
        float ballY = ballPos.getY();
        float paddleX1 = paddleStartPos.getX();
        float paddleY1 = paddleStartPos.getY();
        float paddleX2 = paddleStopPos.getX();
        float paddleY2 = paddleStopPos.getY();

        // Find the equation of the line that represents the paddle
        float m = (paddleY2 - paddleY1) / (paddleX2 - paddleX1);
        float b = paddleY1 - m * paddleX1;

        // Find the y-coordinate of the line at the x-coordinate of the ball
        float paddleY = m * ballX + b;

        // Check if the ball's y-coordinate falls within the range of y-coordinates that count as a hit
        float t = 50; // thickness of the paddle
        float yMin = paddleY - ballRadius - t;
        float yMax = paddleY + ballRadius + t;
        return ballY > yMin && ballY < yMax &&
                ballX > (paddleX1 - ballRadius - t) && ballX < (paddleX2 + ballRadius + t);
    }

    public void updatePaddleXAcceleration(Coordinate a, double deltaTime) {
        if(Math.abs(a.x) < 20){
            a.x = 0;
        }
        paddleAdvanced.setAcceleration(a);
//        this.deltaT = deltaTime;
        paddleAdvanced.setPaddleCenter(deltaTime);
    }

    public void updatePaddleAngularVelocity(Coordinate angularVelocity, double deltaTime) {
        paddleAdvanced.setTheta(angularVelocity, deltaTime);
    }

    public void updateBallAcceleration(float az){
        ball.setZAcceleration(az);
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
                Coordinate paddleStartPos = paddleAdvanced.getStartPosition();
                Coordinate paddleStopPos = paddleAdvanced.getStopPosition();


                if (calculateDistanceFromCircleToLineSegment(paddleStartPos.x, paddleStartPos.y, //collision darim
                        paddleStopPos.x, paddleStopPos.y,
                        ballPos.x, ballPos.y, ball.getRadius()) < ball.getRadius() + 20) {  // TODO: 30 or 0??
//                    ball.reverseBallVelocity();
                    if (!isCollision) {
                        isCollision = true;
                        ball.handlePaddleCollisions(paddleAdvanced.getTheta(), deltaT);
                    }
                } else {
                    isCollision = false;
                    gameView.updateBallPosition((int) ballPos.getX(), (int) ballPos.getY());
                    gameView.updatePaddlePosition((int) paddleStartPos.getX(), (int) paddleStartPos.getY(), (int) paddleStopPos.getX(), (int) paddleStopPos.getY());

                }
                ball.updatePosition(deltaT);
                paddleAdvanced.updatePosition();
                Thread.sleep((long) (deltaT * 1000)); //1ms
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
