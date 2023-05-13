package CPS.CPSCA2.Pong.Domain;

public class Coordinate {
    public double x, y, z;

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void vectorAddition(Coordinate newVec) {
        this.x += newVec.x;
        this.y += newVec.y;
        this.z += newVec.z;
    }

    public Coordinate multiplyVectorByNum(double number) {
        return new Coordinate(this.x*number, this.y*number, this.z*number);
    }

    public double getSize() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}