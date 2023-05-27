//package CPS.CPSCA2.Pong.Logic.AdvancedCopy;
//
//
//import android.util.Pair;
//
//import CPS.CPSCA2.Pong.Coordinate.Coordinate;
//
//public class PaddleAdvanced {
//    private Coordinate startPosition;
//    private Coordinate stopPosition;
//    private Coordinate velocity;
//    private Coordinate acceleration;
//    private int displayWidth;
//    private int displayHeight;
//    private float width, height;
//    private Coordinate theta;
//    private float length;
//    private float centerX, centerY;
//
//    public PaddleAdvanced(Coordinate startPosition, Coordinate stopPosition, Coordinate v, Coordinate a,
//                          Pair<Integer, Integer> displaySize, int width, int height) {
//        this.startPosition = startPosition;
//        this.stopPosition = stopPosition;
//        this.velocity = v;
//        this.acceleration = a;
//        this.displayWidth = displaySize.first;
//        this.displayHeight = displaySize.second;
//        this.width = width;
//        this.height = height;
//        this.length = Math.abs(startPosition.x - stopPosition.x);
//        this.centerX = startPosition.x + this.length / 2;
//        this.centerY = startPosition.y;
//        this.theta = new Coordinate(0, 0, 0);
//    }
//
//    private void handleWallCollision() {
//        if (centerX > displayWidth) {
//            centerX = displayWidth;
//            velocity.x = 0;
//        } else if (centerX < 0) {
//            centerX = 0;
//            velocity.x = 0;
//        }
//    }
//
//    public Coordinate getStartPosition() {
//        return startPosition;
//    }
//
//    public Coordinate getStopPosition() {
//        return stopPosition;
//    }
//
//    public void setPaddleCenter(double deltaT) {
//        float acc = (float) (acceleration.x * Math.cos(theta.y) * Math.cos(theta.z) + acceleration.y * Math.sin(theta.y) * Math.sin(theta.z) + acceleration.z * Math.cos(theta.y) * Math.sin(theta.z));
////        float acc = acceleration.x;
//        if (acc * velocity.x < 0){
//            acc *= 0.1;
//        }
//        centerX += (float) -((0.5 * acc * Math.pow(deltaT, 2)) + (velocity.x * deltaT));
//        updateVelocity(deltaT);
//    }
//
//    public void updatePosition() {
//        float delta_x = (float) ((length / 2) * Math.cos(theta.z));
//        float delta_y = (float) ((length / 2) * Math.sin(theta.z));
//
//        float newStartY = centerY + delta_y;
//        float newStopY = centerY - delta_y;
//
//        float newStartX = centerX - delta_x;
//        float newStopX = centerX + delta_x;
//        setPosition(newStartX, newStartY, newStopX, newStopY);
//        handleWallCollision();
//    }
//
//    private void updateVelocity(double deltaT) {
//        velocity.x += acceleration.x * deltaT;
//        float velocityAsb = Math.abs(velocity.x);
//        int sign = velocity.x > 0 ? 1 : -1;
//        if ((velocityAsb - 100 * deltaT <= 0)) {
//            velocity.x = 0;
//        } else {
//            velocity.x = (float) ((velocityAsb - (100 * deltaT)) * sign);
//        }
//    }
//
//    public void setPosition(float newStartX, float newStartY, float newStopX, float newStopY) {
//        startPosition = new Coordinate(newStartX, newStartY, startPosition.getZ());
//        stopPosition = new Coordinate(newStopX, newStopY, stopPosition.getZ());
//    }
//
//    public void setAcceleration(Coordinate a) {
//        if(Math.abs(a.x) < 20){
//            acceleration.x = 0;
//        }
//        else {
//            acceleration.x = (float) (acceleration.x * 0.2 + a.x * 0.8);
//        }
//        acceleration.y = (float) (acceleration.y * 0.2 + a.y * 0.8);
//        acceleration.z = (float) (acceleration.z * 0.2 + a.z * 0.8);
//    }
//
//    public void setTheta(Coordinate angularVelocity, double deltaT) {
//        theta.x += angularVelocity.x * deltaT;
//        theta.y += angularVelocity.y * deltaT;
//        theta.z += angularVelocity.z * deltaT;
//
//    }
//
//    public Coordinate getTheta() {
//        return theta;
//    }
//
//}
