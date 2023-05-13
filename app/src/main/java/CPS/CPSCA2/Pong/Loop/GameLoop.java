package CPS.CPSCA2.Pong.Loop;


import android.util.Pair;

import CPS.CPSCA2.Pong.Activity.GameView;
import CPS.CPSCA2.Pong.Domain.Ball;
import CPS.CPSCA2.Pong.Domain.Coordinate;

public class GameLoop extends Thread {
    private final boolean running;
    private final int deltaT;
    Ball ball;
    GameView gameView;
    boolean isRunning;

    public GameLoop(GameView view, int dt, Pair<Integer, Integer> screen) {
        this.gameView = view;
        this.running = true;
        this.deltaT = dt;
        isRunning = true;

        ball = new Ball(new Coordinate(10, 10, 0), new Coordinate(0, 0, 0),
                new Coordinate(0.1, 0.1, 0), screen, 10);

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
                gameView.updateBallPosition((int) ballPos.getX(), (int) ballPos.getY());
                ball.setNextPosition(deltaT);
                Thread.sleep(deltaT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
