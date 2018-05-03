package sample;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Player extends AliveObject {
    public int hp = 3;
    private boolean powerIsActive = false;

    Canvas canvas;

    public int oldX;
    public int oldY;

    final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
    final BooleanBinding spaceAndLeftPressed = spacePressed.and(leftPressed);

    //public Player player;
    Player block2;
    boolean leftColliding = false;
    boolean rightColliding = false;
    boolean canJump = false;
    boolean movingLeft = false;
    boolean movingRight = false;

    private int jumpHeight = this.y - 40;


    private double gravity = 0.1;
    private final double T_VELY = 10;
    private final double T_VELX = 5;

    public void setX(int i) {


        this.x = i;
    }

    public void setY(int i) {

        this.y = i;
    }

    //Gravity
    public void playerGravity() {
        x += velX;
        y += velY;

        if (falling || jumping) {
            velY += gravity;

            if (velY > T_VELY) {
                velY = T_VELY;
            }
        }
        if (movingLeft || movingRight) {

            if (velX > T_VELX) {
                velX = T_VELX;
            }
        }
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }


    /*public void draw(GraphicsContext g) {
        g.strokeRect(x, y, width, height);
    }*/
    public BBox bbox() {
        return new BBox(x, x + width, y, y + height);
    }

    public boolean fallingColliding(Player other) {
        return bbox().fallingColliding(other.bbox());
    }

    public boolean movingColliding(Player other) {
        return bbox().movingColliding(other.bbox());

    }

    /*public boolean isColliding(Player other) {
        return bbox().isColliding(other.bbox());
    }*/

    public void jump() {
        velY -= 3;

    }


    public void update(Player block, int[][] map) {

     /*   canvas.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            } else if (ke.getCode() == KeyCode.RIGHT) {
                rightPressed.set(true);
            } else if (ke.getCode() == KeyCode.LEFT && !leftColliding) {
                leftPressed.set(true);
            }
        });
        canvas.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            } else if (ke.getCode() == KeyCode.RIGHT) {
                rightPressed.set(false);
            } else if (ke.getCode() == KeyCode.LEFT) {
                leftPressed.set(false);
                if (!player.isColliding(block)) {
                    leftColliding = false;
                    rightColliding = false;
                }
            }
        }); */

        oldX = x;
        oldY = y;

        if (leftPressed.get()) {
            this.x -= 2;
            movingLeft = true;
        }
        if (leftPressed.get() && movingLeftColliding(map)) {
            x = oldX;
            leftColliding = true;
        }

        if (rightPressed.get()) {
            this.x += 2;
            movingRight = true;
        }

        if (rightPressed.get() && movingRightColliding(map)) {
            //if (rightPressed.get() && isColliding(map)/* Men sjekker bare x-akse */ ) {

            x = oldX;
        }

        if (spacePressed.get() && canJump) {
            jump();
            canJump = false;

        }

        // if (falling && fallingColliding(map)) {
        //if (falling && fallingColliding(map)/* Men sjekker bare y-akse */) {
        //  velY = 0;
        //}

    }

    private boolean fallingColliding(int[][] map) {
        int playerXStart = (int) ((oldX) / (canvas.getWidth() / map.length));
        int playerXStop = (int) ((oldX + width) / (canvas.getWidth() / map.length));
        int playerY = (int) ((oldY + height) / (canvas.getHeight() / map[0].length));

        if (playerY > 0 && (map[playerXStart][playerY] == 1 || map[playerXStop][playerY] == 1)) {
            while (map[playerXStart][playerY] == 1 || map[playerXStop][playerY] == 1) playerY--;
            y = (int) ((playerY * (canvas.getHeight() / map.length) - height / 3));

            oldY = y;
            canJump = true;
            return true;
        } else {
            return false;
        }
    }


    private boolean movingRightColliding(int[][] map) {
        double playerXStop = ((x + width) / (canvas.getWidth() / map.length));
        double playerY = ((oldY + height) / (canvas.getHeight() / map[0].length));

        int playerXInt = (int) Math.round(playerXStop);
        int playerYInt = (int) Math.round(playerY);
        if (playerXStop < map.length && playerY > 0 && map[playerXInt][playerYInt - 1] == 1) {
            System.out.println("true");
            if (map[playerXInt][playerYInt - 1] == 1) playerXInt--;
            oldX = (int) (playerXInt * (canvas.getWidth() / map.length) - width / 3);
            return true;
        } else {
            return false;
        }
    }

    private boolean movingLeftColliding(int[][] map) {
        double playerXStart = ((x) / (canvas.getWidth() / map.length));
        double playerY = ((oldY + height) / (canvas.getHeight() / map[0].length));

        int playerXLeftInt = (int) Math.round(playerXStart);
        int playerYInt = (int) Math.round(playerY);


        if (playerXStart > 0 && playerY > 0 && map[playerXLeftInt][playerYInt - 1] == 1) {
            System.out.println("true");
            if (map[playerXLeftInt][playerYInt - 1] == 1) playerXLeftInt++;
            oldX = (int) ((playerXLeftInt * (canvas.getWidth() / map.length) + width / 3));
            return true;
        } else {
            return false;

        }
    }


    public void drawPlayer(GraphicsContext g) {
        g.strokeRect(x, y, width, height);
    }


    //public int x, y, w, h;

    public Player(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /*public void draw(GraphicsContext g) {
        g.strokeRect(x, y, width, height);
    }*/

    public boolean isColliding(BBox box) {
        return bbox().isColliding(box);
    }

    public void setPowerIsActive(boolean powerIsActive) {
        this.powerIsActive = powerIsActive;
    }

    public boolean isColliding(AliveObject other) {
        return bbox().isColliding(other.bbox());
    }


   /* public void drawPlayer(GraphicsContext g) {
        g.strokeRect(x, y, width, height);
    }*/

        public void death() {
            if (hp <= 0) {
                System.out.println("Spillet har stoppet");
            }

        }


        public boolean isPowerIsActive(){
            return powerIsActive;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }

    }

