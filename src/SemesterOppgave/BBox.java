package sample;

public class BBox {

    private int minX, maxX, minY, maxY;

    public BBox(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }


    public boolean isColliding(BBox other) {
        return maxX >= other.minX && minX <= other.maxX
                && maxY >= other.minY && minY <= other.maxY;
    }

    public boolean fallingColliding(BBox other){
        return maxY >= other.minY && minY <= other.maxY;
    }

    public boolean movingColliding(BBox other){
        return maxX >= other.minX && minX <= other.maxX;
    }



    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

}