package CPS.CPSCA2.Pong.Domain;


import android.util.Pair;

public class Paddle {
    private Coordinate position;
    private Coordinate velocity;
    private Coordinate acceleration;
    private Coordinate theta;
    private int displayWidth;
    private int displayHeight;

    private float width, height;

    public Paddle(Coordinate x, Coordinate v, Coordinate a,
                Pair<Integer, Integer> displaySize, int width, int height) {
        this.position = x;
        this.velocity = v;
        this.acceleration = a;
        this.theta = new Coordinate(0, 0, 0);
        this.displayWidth = displaySize.first;
        this.displayHeight = displaySize.second;
        this.width = width;
        this.height = height;
    }

    public void setNextPosition(double deltaT) {
        position = getNextPosition(deltaT);
    }

    public Coordinate getPosition() {
        return position;
    }

    private Coordinate getNextPosition(double deltaT) {
        Coordinate amountToAdd1 = acceleration.multiplyVectorByNum(0.5 * (Math.pow(deltaT, 2)));
        Coordinate amountToAdd2 = velocity.multiplyVectorByNum(deltaT);
        amountToAdd1.vectorAddition(amountToAdd2);
        return new Coordinate((position.x + amountToAdd1.x) % displayWidth,
                position.y + amountToAdd1.y,
                position.z + amountToAdd1.z);
    }

    private void updateVelocity(double deltaT) {
        Coordinate amountToAdd = acceleration.multiplyVectorByNum(deltaT);
        velocity.vectorAddition(amountToAdd);
    }

}
