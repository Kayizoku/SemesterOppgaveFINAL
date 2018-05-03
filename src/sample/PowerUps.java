package sample;

public class PowerUps extends AliveObject{


    public PowerUps(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    public boolean update(){
        y-=0.5;
        if(x>width){
            return true;
        }
        return false;
    }
}