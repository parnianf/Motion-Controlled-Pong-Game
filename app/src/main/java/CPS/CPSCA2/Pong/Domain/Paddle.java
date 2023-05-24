package CPS.CPSCA2.Pong.Domain;


import android.util.Pair;

public class Paddle {
    private Coordinate startPosition;
    private Coordinate stopPosition;
    private Coordinate velocity;
    private Coordinate acceleration;
    private Coordinate theta;
    private int displayWidth;
    private int displayHeight;

    private float width, height;

    public Paddle(Coordinate startPosition, Coordinate stopPosition, Coordinate v, Coordinate a,
                  Pair<Integer, Integer> displaySize, int width, int height) {
        this.startPosition = startPosition;
        this.stopPosition = stopPosition;
        this.velocity = v;
        this.acceleration = a;
        this.theta = new Coordinate(0, 0, 0);
        this.displayWidth = displaySize.first;
        this.displayHeight = displaySize.second;
        this.width = width;
        this.height = height;
    }

//    public void setNextPosition(double deltaT) {
//        position = getNextPosition(deltaT);
//    }

    public Coordinate getStartPosition() {
        return startPosition;
    }

    public Coordinate getStopPosition() {
        return stopPosition;
    }

    public void updatePosition(double deltaT) {
        float newStartX = (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT) + startPosition.x);
        float newStartY = (float) ((0.5 * acceleration.y * Math.pow(deltaT, 2)) + (velocity.y * deltaT) + startPosition.y);

        float newStopX = (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT) + stopPosition.x);
        float newStopY = (float) ((0.5 * acceleration.y * Math.pow(deltaT, 2)) + (velocity.y * deltaT) + stopPosition.y);

        startPosition = new Coordinate(newStartX, newStartY, startPosition.getZ());
        stopPosition = new Coordinate(newStopX, newStopY, stopPosition.getZ());

    }

    public void setAcceleration(float ax) {



        acceleration.x = ax;
    }

//    private void updateVelocity(double deltaT) {
//        Coordinate amountToAdd = acceleration.multiplyVectorByNum(deltaT);
//        velocity.vectorAddition(amountToAdd);
//    }

}
