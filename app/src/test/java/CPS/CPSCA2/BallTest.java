package CPS.CPSCA2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import android.util.DisplayMetrics;
import android.util.Pair;

import CPS.CPSCA2.Pong.Activity.GameActivity;
import CPS.CPSCA2.Pong.Domain.Ball;
import CPS.CPSCA2.Pong.Domain.Coordinate;
import CPS.CPSCA2.Pong.Domain.Paddle;


public class BallTest {
    @Test
    public void testHandlePaddleCollisions_WithAdvancedMode_ShouldUpdateVelocity() {
        Ball gameLogic = new Ball(
                new Coordinate(Integer.parseInt(String.valueOf(1024 / 2)), 10, 0),
                new Coordinate(1, 1, 0),
                new Coordinate(0, 0, 30),
                1024, 1024,
                10, "normal");
        Coordinate theta = new Coordinate(0, 0, 0);
        float deltaT = (float) 0.01;

        gameLogic.handlePaddleCollisions(theta, deltaT);

        assertEquals(-1.27, gameLogic.getVelocity().getX(), 0.01);
        assertEquals(-0.73, gameLogic.getVelocity().getY(), 0.01);
    }

//    @Test
//    public void testHandlePaddleCollisions_WithBasicMode_ShouldUpdateVelocity() {
//        GameLogic gameLogic = new GameLogic();
//        gameLogic.setMode("basic");
//        Coordinate theta = new Coordinate(0, 0, 0);
//        float deltaT = 0.1f;
//
//        // Set initial values for velocity
//        gameLogic.setVelocity(new Coordinate(1, 1, 0));
//
//        gameLogic.handlePaddleCollisions(theta, deltaT);
//
//        // Assert that the velocity is updated correctly based on the collision handling logic
//        assertEquals(-1.27f, gameLogic.getVelocity().getX(), 0.01);
//        assertEquals(-0.73f, gameLogic.getVelocity().getY(), 0.01);
//    }
}
