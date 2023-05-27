package CPS.CPSCA2.Pong.Logic.Normal;

import android.util.Pair;

import CPS.CPSCA2.Pong.Coordinate.Coordinate;

public class Ball {
    private Coordinate position;
    private Coordinate velocity;
    private Coordinate acceleration;
    private int displayWidth;
    private int displayHeight;
    private float radius;

    public Ball(Coordinate x, Coordinate v, Coordinate a, Pair<Integer, Integer> displaySize, float radius) {
        this.position = x;
        this.velocity = v;
        this.acceleration = a;
        this.displayWidth = displaySize.first;
        this.displayHeight = displaySize.second;
        this.radius = radius;
    }

    public Coordinate getPosition() {
        return position;
    }


    public void updatePosition(double deltaT) {
        float new_x = (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT) + position.x);
        float new_y = (float) ((0.5 * acceleration.y * Math.pow(deltaT, 2)) + (velocity.y * deltaT) + position.y);

        position = new Coordinate(new_x, new_y, position.z);
        updateVelocity(deltaT);
        handleBoundaryCollisions();
    }

    private void updateVelocity(double deltaT) {
        velocity.y += acceleration.y * deltaT;
        velocity.x += acceleration.x * deltaT;
    }

    public void handlePaddleCollisions(Coordinate theta) {
        float vx = (float) (velocity.x * Math.cos(2 * theta.z) - velocity.y * Math.sin(2 * theta.z));
        float vy = (float) (-velocity.x * Math.sin(2 * theta.z) - velocity.y * Math.cos(2 * theta.z));
        velocity.x = vx;
        velocity.y = vy;
    }

    private void handleBoundaryCollisions() {
        if ((position.x + radius) >= displayWidth || (position.x - radius) <= 0) {
            velocity.x = -velocity.x;
        }
        if ((position.y + radius) >= displayHeight || (position.y - radius) <= 0) {
            velocity.y = -velocity.y;
        }
    }

    public float getRadius() {
        return radius;
    }
}