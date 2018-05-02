package  sample;

import javafx.scene.canvas.GraphicsContext;



    public class Enemy extends AliveObject{
        public boolean left = true;
        public boolean right = true;
        int changeDirectionCounter = 0;
        int maxVerdi = 100;
        boolean moveRight = true;



        public Enemy(int x, int y, int w, int h) {
            super(x, y, w, h);
        }

        // BRUKER DENNE?

//        public void enemyMovement(){
//            if(right){
//                x+=5;
//            } else {
//                x-=5;
//            }
//        }


        public void enemyMovement(int speed){
            changeDirectionCounter++;
            if(moveRight){
                x+=speed;

                if(changeDirectionCounter == maxVerdi){
                    moveRight = false;
                    changeDirectionCounter = 0;
                }
            }  else {
                x-= speed;
                if(changeDirectionCounter == maxVerdi){
                    moveRight = true;
                    changeDirectionCounter = 0;
                }
            }

        }
    public void drawEnemy(GraphicsContext g){
        g.strokeRect(x,y,width,height);




    }


}
