package CPS.CPSCA2.Pong.Domain;

import android.util.Pair;

import java.lang.Math;

public class Ball {
    private Coordinate position;
    private Coordinate velocity;
    private Coordinate acceleration;
    private Coordinate theta;
    private int displayWidth;
    private int displayHeight;
    private float radius;

    public Ball(Coordinate x, Coordinate v, Coordinate a,
                Pair<Integer, Integer> displaySize, float radius) {
        this.position = x;
        this.velocity = v;
        this.acceleration = a;
        this.theta = new Coordinate(0, 0, 0);
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

    private void handleBoundaryCollisions() {
        if ((position.x + radius) >= displayWidth || (position.x - radius) <= 0) {
            velocity.x = -velocity.x;
        }
        if ((position.y + radius) >= displayHeight || (position.y - radius) <= 0) {
            velocity.y = -velocity.y;
        }
    }

    private void updateVelocity(double deltaT) {
        velocity.y += acceleration.y * deltaT;
    }

    public void reverseBallVelocity() {
        velocity.y = -velocity.y;
    }

    public float getRadius() {
        return radius;
    }

    //    private void updateAcceleration(Coordinate F) {
//        acceleration.x = (F.x / GameConfig.BALL_WEIGHT) * GameConfig.ACCELERATION_FACTOR;
//        acceleration.y = (F.y / GameConfig.BALL_WEIGHT) * GameConfig.ACCELERATION_FACTOR;
//    }

//    public void generateRandomVelocity() {
//        double randomFloat = Math.random();
//        int randomSign = (randomFloat > 0.5) ? 1 : -1;
//        velocity = RandomGenerator.random3dVector(GameConfig.RANDOM_VELOCITY_LOW,
//                GameConfig.RANDOM_VELOCITY_HIGH,
//                GameConfig.RANDOM_VELOCITY_LOW,
//                GameConfig.RANDOM_VELOCITY_HIGH, 0, 0);
//        velocity.y *= randomSign;
//    }

//    public boolean checkWallCollision(Coordinate position) {
//        boolean xCollided = (position.x <= 0) ||
//                (position.x >= displayWidth);
//        boolean yCollided = (position.y <= 0) ||
//                (position.y >= displayHeight);
//        return xCollided || yCollided;
//    }
//
//    private boolean handleWallCollision(Coordinate position) {
//        boolean collided = false;
//        if (checkWallCollision(new Coordinate(position.x + radius, position.y, 0))) {
//            velocity.y = Math.abs(velocity.y);
//            collided = true;
//        }
//        if (checkWallCollision(new Coordinate(position.x, position.y + radius, 0))) {
//            velocity.x = Math.abs(velocity.x);
//            collided = true;
//        }
//        if (checkWallCollision(new Coordinate(position.x + radius,
//                position.y + radius * 2, 0))) {
//            velocity.y = -Math.abs(velocity.y);
//            collided = true;
//        }
//        if (checkWallCollision(new Coordinate(position.x + radius * 2,
//                position.y + radius, 0))) {
//            velocity.x = -Math.abs(velocity.x);
//            collided = true;
//        }
//        if (collided) {
//            velocity = velocity.multiplyVectorByNum(GamePhysicsConfig.kineticEnergyReductionFactor);
//        }
//        return collided;
//    }
//
//
//    public void handleSensorEvent(Coordinate vec, GameConfig.sensor sensor, double deltaT) {
//        if (sensor == GameConfig.sensor.GYROSCOPE) {
//            handleGyroscopeSensorEvent(vec, deltaT);
//        } else {
//            handleGravitySensorEvent(vec, deltaT);
//        }
//        handlePhysics();
//        updateVelocity(deltaT);
//        Coordinate nextPosition = getNextPosition(deltaT);
//        boolean collided = handleWallCollision(nextPosition);
//        if (collided) {
//            handlePhysics();
//            updateVelocity(deltaT);
//        }
//        position = getNextPosition(deltaT);
//    }
//
//    private void handlePhysics() {
//        Coordinate F = getForces();
//        double N = getN();
//        F = handleFriction(F, N);
//        updateAcceleration(F);
//    }
//
//    private Coordinate getForces() {
//        double fX = GamePhysicsConfig.earthGravity * GameConfig.BALL_WEIGHT * Math.sin(theta.y);
//        double fY = GamePhysicsConfig.earthGravity * GameConfig.BALL_WEIGHT * Math.sin(theta.x);
//        return new Coordinate(fX, fY, 0);
//    }
//
//    private double getN() {
//        Coordinate sinTheta = new Coordinate(Math.sin(theta.x), Math.sin(theta.y), 0);
//        double N = GamePhysicsConfig.earthGravity * GameConfig.BALL_WEIGHT *
//                Math.cos(Math.atan(sinTheta.getSize() / (Math.cos(theta.x) + Math.cos(theta.y))));
//        return N;
//    }
//
//    private Coordinate handleFriction(Coordinate F, double N) {
//        if (((position.x == 0) ||
//                (position.x == displayWidth) && Math.abs(velocity.y) < GameConfig.BALL_STOP_SPEED) ||
//                ((position.y == 0) || (position.y == displayHeight) &&
//                        Math.abs(velocity.x) < GameConfig.BALL_STOP_SPEED)) {
//            if (canMove(F, N)) {
//                double frictionSize = N * GamePhysicsConfig.Uk;
//                double velocitySize = velocity.getSize();
//                double frictionX = 0;
//                double frictionY = 0;
//                if (velocitySize > 0) {
//                    frictionX = frictionSize * velocity.x / velocitySize;
//                    frictionY = frictionSize * velocity.y / velocitySize;
//                }
//                if (velocity.x != 0)
//                    F.x += velocity.x > 0 ? -Math.abs(frictionX) : Math.abs(frictionX);
//                if (velocity.y != 0)
//                    F.y += velocity.y > 0 ? -Math.abs(frictionY) : Math.abs(frictionY);
//            } else {
//                F = new Coordinate(0, 0, 0);
//            }
//        }
//        return F;
//    }
//
//    private void handleGyroscopeSensorEvent(Coordinate vec, double deltaT) {
//        theta = new Coordinate(vec.x * deltaT + theta.x,
//                vec.y * deltaT + theta.y,
//                vec.z * deltaT + theta.z);
//    }
//
//    private void handleGravitySensorEvent(Coordinate vec, double deltaT) {
//        theta = new Coordinate(Math.asin(vec.y / GamePhysicsConfig.earthGravity),
//                Math.asin(-vec.x / GamePhysicsConfig.earthGravity),
//                Math.asin(vec.z / GamePhysicsConfig.earthGravity));
//    }
//
//    private boolean canMove(Coordinate f, double N) {
//        double frictionSize = N * GamePhysicsConfig.Us;
//        return f.getSize() > frictionSize;
//    }
}