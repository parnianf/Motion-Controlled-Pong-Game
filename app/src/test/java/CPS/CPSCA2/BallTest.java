package CPS.CPSCA2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import CPS.CPSCA2.Pong.Domain.Ball;
import CPS.CPSCA2.Pong.Domain.Coordinate;


public class BallTest {
    @Test
    public void testHandlePaddleCollisions() {
        Ball gameLogic = new Ball(
                new Coordinate(Integer.parseInt(String.valueOf(1024 / 2)), 10, 0),
                new Coordinate(1, 1, 0),
                new Coordinate(0, 0, 30),
                1024, 1024,
                10, "normal");
        Coordinate theta = new Coordinate(0, 0, 0);
        float deltaT = (float) 0.01;

        gameLogic.handlePaddleCollisions(theta, deltaT);

        assertEquals(1, gameLogic.getVelocity().getX(), 0.01);
        assertEquals(-1, gameLogic.getVelocity().getY(), 0.01);
    }

    @Test
    public void testHandlePaddleCollisionsWithTheta() {
        Ball gameLogic = new Ball(
                new Coordinate(Integer.parseInt(String.valueOf(1024 / 2)), 10, 0),
                new Coordinate(1, 1, 0),
                new Coordinate(0, 0, 30),
                1024, 1024,
                10, "normal");

        Coordinate theta = new Coordinate(0, 0, (float) Math.toRadians(30.0));
        float deltaT = (float) 0.01;

        gameLogic.handlePaddleCollisions(theta, deltaT);

        assertEquals(-0.36, gameLogic.getVelocity().getX(), 0.01);
        assertEquals(-1.36, gameLogic.getVelocity().getY(), 0.01);
    }
}

