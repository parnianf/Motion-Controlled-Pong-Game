package CPS.CPSCA2.Ball;

public class Coordinates {
    public double x, y, z;

    public Coordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void vectorAddition(Coordinates newVec) {
        this.x += newVec.x;
        this.y += newVec.y;
        this.z += newVec.z;
    }

    public Coordinates multiplyVectorByNum(double number) {
        return new Coordinates(this.x*number, this.y*number, this.z*number);
    }

    public double getSize() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }
}