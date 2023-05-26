package CPS.CPSCA2.Pong.Domain;


import android.util.Pair;

public class Paddle {
    private Coordinate startPosition;
    private Coordinate stopPosition;
    private Coordinate velocity;
    private Coordinate acceleration;
//    private Coordinate theta;
    private int displayWidth;
    private int displayHeight;

    private float width, height;
    private float theta;

    private float length;

    public Paddle(Coordinate startPosition, Coordinate stopPosition, Coordinate v, Coordinate a,
                  Pair<Integer, Integer> displaySize, int width, int height) {
        this.startPosition = startPosition;
        this.stopPosition = stopPosition;
        this.velocity = v;
        this.acceleration = a;
//        this.theta = new Coordinate(0, 0, 0);
        this.displayWidth = displaySize.first;
        this.displayHeight = displaySize.second;
        this.width = width;
        this.height = height;
        this.length = (float) Math.abs(startPosition.x - stopPosition.x);
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
        float delta_x = (float) Math.abs((length / 2) * Math.cos(theta));
        float delta_y = (float) Math.abs((length / 2) * Math.sin(theta));

        float len2 = (float) (Math.abs(stopPosition.x - startPosition.x) / 2);
        float centerX = (float) (startPosition.x + len2);
        float centerY = (float) ((float)displayHeight * 3.0 / 4.0);

        float newCenterX = (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT) + centerX);
        float newCenterY = centerY;

        float newStartY = newCenterY + delta_y;
        float newStopY = newCenterY - delta_y;

        float newStartX = newCenterX - delta_x;
        float newStopX = newCenterX + delta_x;

        System.out.println("theta: " + theta + " centerX: " + centerX + " newCenterY: " + newCenterY + " newStartY: " + newStartY + " newStopY: " + newStopY + " newStartX: " + newStartX + " newStopX: " + newStopX);


//
//        float newStartX = (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT) + startPosition.x) - length/2 + delta_x;
//        float newStartY = (float) ((0.5 * acceleration.y * Math.pow(deltaT, 2)) + (velocity.y * deltaT) + startPosition.y) - delta_y;
//
//        float newStopX = (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT) + stopPosition.x) + length/2 - delta_x;
//        float newStopY = (float) ((0.5 * acceleration.y * Math.pow(deltaT, 2)) + (velocity.y * deltaT) + stopPosition.y) + delta_y;

        startPosition = new Coordinate(newStartX, newStartY, startPosition.getZ());
        stopPosition = new Coordinate(newStopX, newStopY, stopPosition.getZ());

//        System.out.println(startPosition);

    }

    public void setAcceleration(float ax) {
        acceleration.x = ax;
    }

    public void setTheta(float angularVelocityZ, float deltaT){
        theta = angularVelocityZ * deltaT;
    }



//    private void updateVelocity(double deltaT) {
//        Coordinate amountToAdd = acceleration.multiplyVectorByNum(deltaT);
//        velocity.vectorAddition(amountToAdd);
//    }

}
