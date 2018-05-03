package sample;

import javafx.scene.canvas.GraphicsContext;

public class Bullet extends BBox{
    private int velocity;


    public Bullet(int minX, int maxX, int minY, int maxY,int velocity) {
        super(minX, maxX, minY, maxY);
        this.velocity = velocity;

    }

    public void bulletMove(){
        setMaxY(getMaxY()-velocity);
        setMinY(getMinY()- velocity);
    }
    public void drawBullet(GraphicsContext g){
        double width = getMaxX() - getMinX();
        double height = getMaxY() - getMinY();
        g.strokeRect(getMinX(),getMinY(),width,height);
    }
}