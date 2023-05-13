package CPS.CPSCA2.Pong.Loop;


import android.util.Pair;

import CPS.CPSCA2.Pong.Activity.GameView;
import CPS.CPSCA2.Pong.Domain.Ball;
import CPS.CPSCA2.Pong.Domain.Coordinate;
import CPS.CPSCA2.Pong.Domain.Paddle;

public class GameLoop extends Thread {
    private final boolean running;
    private final int deltaT;
    Ball ball;
    Paddle paddle;
    GameView gameView;
    boolean isRunning;



    public GameLoop(GameView view, int dt, Pair<Integer, Integer> screen) {
        this.gameView = view;
        this.running = true;
        this.deltaT = dt;
        isRunning = true;

        ball = new Ball(new Coordinate(Integer.parseInt(String.valueOf(screen.first/2)), 0, 0), new Coordinate(0, 0, 0),
                new Coordinate(0, 0.1, 0), screen, 10);

        paddle = new Paddle(new Coordinate(Integer.parseInt(String.valueOf(screen.first/2)), Integer.parseInt(String.valueOf(3*screen.second/4)), 0), new Coordinate(0, 0, 0),
                new Coordinate(0.1, 0, 0), screen, 400, 200);

    }

    public void endLoop() {
        isRunning = false;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Coordinate ballPos = ball.getPosition();
                Coordinate paddlePos = paddle.getPosition();

                gameView.updateBallPosition((int) ballPos.getX(), (int) ballPos.getY());
                gameView.updatePaddlePosition((int) paddlePos.getX(), (int) paddlePos.getY(), 400, 200);
                ball.setNextPosition(deltaT);
                paddle.setNextPosition(deltaT);
                Thread.sleep(deltaT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
