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
    private float centerX, centerY;

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
        this.centerX = (float) (startPosition.x + this.length/2);
        this.centerY = (float) startPosition.y;
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

    public void handlePositionByAccelerometer(double deltaT){
        centerX += (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT));
        float newStartX = centerX - length/2;
        float newStopX = centerX + length/2;
        setPosition(newStartX, (float) startPosition.y, newStopX, (float) stopPosition.y);
    }

    public void handlePositionByGyroscope() {
        float delta_x = (float) ((length / 2) * Math.cos(theta));
        float delta_y = (float) ((length / 2) * Math.sin(theta));

        float newStartY = centerY + delta_y;
        float newStopY = centerY - delta_y;

        float newStartX = centerX - delta_x;
        float newStopX = centerX + delta_x;
        setPosition(newStartX, newStartY, newStopX, newStopY);
    }


    public void setPosition(float newStartX, float newStartY, float newStopX, float newStopY) {
        startPosition = new Coordinate(newStartX, newStartY, startPosition.getZ());
        stopPosition = new Coordinate(newStopX, newStopY, stopPosition.getZ());
    }

    public void setAcceleration(float ax) {
        acceleration.x = ax;
    }

    public void setTheta(float angularVelocityZ, float deltaT){
        theta += angularVelocityZ * deltaT;
    }



//    private void updateVelocity(double deltaT) {
//        Coordinate amountToAdd = acceleration.multiplyVectorByNum(deltaT);
//        velocity.vectorAddition(amountToAdd);
//    }

}
