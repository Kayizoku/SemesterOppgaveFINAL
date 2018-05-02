package sample;

public abstract class AliveObject {
    public int x;
    public int y;
    public int width;
    public int height;

    public AliveObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public BBox bbox() {
        return new BBox(x, x + width, y, y + height);

    }

}

    /*public abstract void playerGravity();
    public  double getX(){
        return x;
    }
    public  double getY(){
        return y;
    }
    public  void setX(int x){
        this.x = x;
    }
    public  void setY(int y){
        this.y = y;
    }
    public  double getVelX(){
        return velX;
    }
    public  double getVelY(){
        return velY;
    }
    public  void setVelX(double velX){
        this.velX = velX;
    }
    public  void setVelY(double velY){
        this.velY = velY;
    }
    public boolean isFalling() {
        return falling;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public boolean isJumping() {
        return jumping;
    }
    public void setJumping(boolean jumping) {
        this.jumping = jumping;

}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
*/