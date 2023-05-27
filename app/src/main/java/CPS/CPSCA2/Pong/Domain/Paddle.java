package CPS.CPSCA2.Pong.Domain;


import android.util.Pair;

public class Paddle {
    private Coordinate startPosition;
    private Coordinate stopPosition;
    private Coordinate velocity;
    private Coordinate acceleration;
    private int displayWidth;
    private int displayHeight;
    private float width, height;
    private Coordinate theta;
    private float length;
    private float centerX, centerY;

    public Paddle(Coordinate startPosition, Coordinate stopPosition, Coordinate v, Coordinate a,
                  Pair<Integer, Integer> displaySize, int width, int height) {
        this.startPosition = startPosition;
        this.stopPosition = stopPosition;
        this.velocity = v;
        this.acceleration = a;
        this.displayWidth = displaySize.first;
        this.displayHeight = displaySize.second;
        this.width = width;
        this.height = height;
        this.length = Math.abs(startPosition.x - stopPosition.x);
        this.centerX = startPosition.x + this.length / 2;
        this.centerY = startPosition.y;
        this.theta = new Coordinate(0, 0, 0);
    }

    private void handleWallCollision() {
        if (centerX > displayWidth) {
            centerX = displayWidth;
            velocity.x = 0;
        } else if (centerX < 0) {
            centerX = 0;
            velocity.x = 0;
        }
    }

    public Coordinate getStartPosition() {
        return startPosition;
    }

    public Coordinate getStopPosition() {
        return stopPosition;
    }

    public void setPaddleCenter(float deltaT) {
        centerX += (float) ((0.5 * acceleration.x * Math.pow(deltaT, 2)) + (velocity.x * deltaT));
        updateVelocity(deltaT);
    }

    public void updatePosition() {
        float delta_x = (float) ((length / 2) * Math.cos(theta.z));
        float delta_y = (float) ((length / 2) * Math.sin(theta.z));

        float newStartY = centerY + delta_y;
        float newStopY = centerY - delta_y;

        float newStartX = centerX - delta_x;
        float newStopX = centerX + delta_x;
        setPosition(newStartX, newStartY, newStopX, newStopY);
        handleWallCollision();
    }

    private void updateVelocity(double deltaT) {
        velocity.x += acceleration.x * deltaT;
        float velocityAsb = (float) Math.abs(velocity.x);
        int sign = velocity.x > 0 ? 1 : -1;
        if ((velocityAsb - 2 * deltaT <= 0) || Math.abs(acceleration.x) < 100) {
            velocity.x = 0;
        } else {
            velocity.x = (float) ((velocityAsb - (2 * deltaT)) * sign);
        }
//        Log.i("velocity", String.valueOf(velocity.x));
//        Log.i("acceleration", String.valueOf(acceleration.x));
    }

    public void setPosition(float newStartX, float newStartY, float newStopX, float newStopY) {
        startPosition = new Coordinate(newStartX, newStartY, startPosition.getZ());
        stopPosition = new Coordinate(newStopX, newStopY, stopPosition.getZ());
    }

    public void setAcceleration(float ax) {
        acceleration.x = ax;
    }

    public void setTheta(Coordinate angularVelocity, float deltaT) {
        theta.x += angularVelocity.x * deltaT;
        theta.y += angularVelocity.y * deltaT;
        theta.z += angularVelocity.z * deltaT;

    }

    public Coordinate getTheta() {
        return theta;
    }

}
