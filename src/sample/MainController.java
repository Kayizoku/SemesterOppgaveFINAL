package  sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public void exitGame(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE) {

            Parent menuPane = null;

            try {

                menuPane = FXMLLoader.load(getClass().getResource("Menu.fxml"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            Stage stage = new Stage();
            Scene scene2 = new Scene(menuPane, 600, 400);
            stage.setScene(scene2);
            stage.show();
            scene2 = ((Node) event.getSource()).getScene();
            scene2.getWindow().hide();

        }
    }

    Path rp = Paths.get("");
    String ap = rp.toAbsolutePath().toString();

    File file = new File(ap + "/src/SemesterOppgave/saves/gameSave.txt");
    String content = "";


    @FXML
    BorderPane border;

    @FXML
    Canvas canvas;

    public Enemy enemy;
    public Player player;
    private Player block;
    public int[][] map;
    public double cellSize;
    private boolean canJump = true;
    // double jumpOrigin;
    // private boolean jumping = false;
    // private boolean falling = true;
    Timeline timeline;
    // private int jumpHeight = 100;
    GraphicsContext gc;
    Image uganda;
    private Image Goomba;

   /* final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
    final BooleanBinding spaceAndLeftPressed = spacePressed.and(leftPressed);
    boolean leftColliding = false; */


    List<Enemy> enemies = new LinkedList<Enemy>();
    List<Bullet> bullets = new LinkedList<>();
    List<Star> stars = new LinkedList<>();
    List<Coin> coins = new LinkedList<>();


    Media sound;
    Image ugandaImage;
    Enemy goomba;
    Image goombaImage;
    int pointCounter = 0;
    int counter = 0;
    int maxVerdi = 30;
    boolean bliSkadet = true;
    Star star;
    Coin coin;
    double jumpOrigin;
    private boolean jumping = false;
    private boolean falling = false;
    private long powerUpStart;
    boolean paused = false;
    Image starImg;
    private int jumpHeight = 100;
    final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
    final BooleanBinding spaceAndLeftPressed = spacePressed.and(leftPressed);
    public static int HEALTH = 100 * 2;


    @Override
    public void initialize(URL url, ResourceBundle resource) {

        //        Background myBI = new Background(new BackgroundImage(new Image("SemesterOppgave/Background.png"),
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
//          border.setBackground(myBI);


        block = new Player(100, 100, 50, 70);
        player = new Player(160, 130, 10, 10);
        enemy = new Enemy(160, 330, 20, 30);
        map = new int[50][50];
        cellSize = canvas.getHeight() / map.length;
        createMap();
        uganda = new Image(getClass().getResourceAsStream("/images/images.png"));
        Goomba = new Image(getClass().getResourceAsStream("/images/GoombaNSMB.png"));
        //jumpOrigin = 300;

        gc = canvas.getGraphicsContext2D();
        createMap();
        draw();


        preparedGame();

        //enemies.add(new Enemy())


        border.setFocusTraversable(true);
        canvas.setFocusTraversable(true);
        music();

    }

    // ANIMATION

    Duration duration = Duration.millis(1000 / 60);
    KeyFrame keyframe = new KeyFrame(duration, (javafx.event.ActionEvent e) -> {
          /*  if (jumping) {

                // Jumping
                if (player.y > jumpOrigin - jumpHeight && !falling) {
                    player.y -= 5;
                }

                // Falling
                else if (player.y < jumpOrigin) {
                    falling = true;
                    player.y += 5;
                }

                // On ground
                else {
                    jumping = false;
                    falling = true;
                }
            }*/

        //  playerMovement();
        player.playerGravity();

        // SKRIVE NOE HER
        //enemy.enemyMovement();

          /*  if (mapCoordinates()){
                System.out.println("True");
                player.x = player.oldX;
                player.y = player.oldY;
            } else {
            } */

        player.update(block, map);
        draw();

        player.setCanvas(canvas);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        //timeline.getKeyFrames().add(keyframe);
        timeline.play();

      /*  rightPressed.addListener((observable, oldValue, newValue) -> System.out.println("Right pressed"));
        leftPressed.addListener((observable, oldValue, newValue) -> System.out.println("Left pressed"));
        spacePressed.addListener((observable, oldValue, newValue) -> System.out.println("Space pressed"));
        spaceAndRightPressed.addListener((observable, oldValue, newValue) -> System.out.println("Space and right pressed together"));
        spaceAndLeftPressed.addListener((observable, oldValue, newValue) -> System.out.println("Space and left pressed together"));
        leftPressed.set(false); */

        canvas.setOnKeyPressed(ke -> {


            String[] verdi = player.toString().split(",");
            int x = player.x;
            int y = player.y;

            System.out.println("x nå er: " + x + " y er nå : " + y);
            if (ke.getCode() == KeyCode.SPACE) {
                player.spacePressed.set(true); //Jump
            } else if (ke.getCode() == KeyCode.RIGHT) {
                player.rightPressed.set(true);
            } else if (ke.getCode() == KeyCode.LEFT) {
                player.leftPressed.set(true);

            }







    /* public void playerMovement() {
        if (rightPressed.get()){
            player.x +=5;
        } if (leftPressed.get()) {
            player.x -= 5;
        } if (spacePressed.get()){
            jump();
        } if(playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        } if(leftPressed.get() && player.isColliding(block)){
            leftColliding = true;
            leftPressed.set(false);
        } else if (leftPressed.get()) {
            leftColliding = false;
        }
        if(rightPressed.get() && player.isColliding(block)){
            rightPressed.set(false);
        }/* if(jumping && player.isColliding(block)){
            //Set velY = 0;
        }
        // Drawing methods
    } */

            //gc.strokeText(player.isColliding(block) ? "Collision" : "NO Collision", 300, 300);
        });
    });

    public void createMap() {
        map = Levels.getLevel2();


    /*public boolean mapCoordinates() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 1) {
                    /*System.out.println();
                    System.out.println(i*cellSize);
                    System.out.println(j*cellSize);
                    System.out.println(player.x);
                    System.out.println(player.y);*/

                    /*int x1 = player.x;
                    int y2 = player.y;
                    int x2 = player.x + player.width;
                    int y1 = player.y + player.height;
                    int x3 = (int)(i*cellSize);
                    int y3 = (int)(j*cellSize) + (int)cellSize;
                    int x4 = (int)(x3 + cellSize);
                    int y4 = (int)(j*cellSize);
                    if(!(x3 > x2 || y3 < y2 || x1 > x4 || y1 > y4)){
                        System.out.println("colliding");
                    }*/
                    /*if ((player.x/cellSize)  > i  && (player.x/cellSize) < (i + 1)
                            && (player.y/cellSize) > j && (player.y/cellSize) < (j + 1)) {
                        //player.x  > i * cellSize && player.x < i* cellSize + cellSize
                        //                            && player.y > j * cellSize && player.y < j * cellSize + cellSize
                        return true;
                    }
                }
            }
        } return false;
    } */


        // NEED THIS?
    /*
        public class thread implements Runnable{
            @Override
            public void run() {
                try{
                    jumping = true;
                    Thread.sleep((long) jumpingTime);
                } catch (Exception e){
                    e.printStackTrace();
                new Thread(this).start();
                    System.exit(0);
                }
            }
        }
*/

        //** KAMERA BEVEGELSE **//
        if (rightPressed.get() == true) {

            canvas.setTranslateX(-(player.x - 200));
        } else if (leftPressed.get() == true) {

            canvas.setTranslateX((-player.x + 200));

            //** SLUTT **/

            player.update(block, map);
            if (bliSkadet == false)

                counter++;
            if (counter == maxVerdi) {
                bliSkadet = true;
                counter = 0;
            }


            for (Enemy ee : enemies) {

                ee.enemyMovement(3);
                if (player.isColliding(ee) && bliSkadet) {
                    bliSkadet = false;
                    System.out.println("Kommer du elr ikke");
                    player.hp -= 1;
                }
            }

        /*    if(coin != null) {
                if (player.isColliding(coin)) {
                    //System.out.println("Kjøres is colliding coin");
                    player.setPowerIsActive(true);
                    gc.clearRect(coin.getX(), coin.getY(), coin.width, coin.height);
                    coin = null;
                    powerUpStart = System.currentTimeMillis();
                    //shooting();
                }
            }*/

            if (System.currentTimeMillis() - powerUpStart > 20000) {
                player.setPowerIsActive(false);
            }
            /*
            for(Bullet bb:bullets){
                bb.bulletMove();
                //TODO: sjekk kollisjon med enemy
                for(Enemy en: enemies) {
                    if (bb.isColliding(en.bbox())) {
                        System.out.println("Collision");
                        enemies.remove(en);
                        bullets.remove(bb);
                    }
                }*/

            for (int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.bulletMove();
                for (int j = 0; j < enemies.size(); j++) {
                    if (b.isColliding(enemies.get(j).bbox())) {
                        pointCounter++;
                        bullets.remove(b);
                        i++;
                        enemies.remove(enemies.get(j));
                        j++;
                    }
                }

                //gc.fillRect(bb.getMinX(),bb.getMinY(),bb.getMaxX()-bb.getMinX(),bb.getMaxY()-bb.getMinY());

                for (int k = 0; k < stars.size(); k++) {
                    if (player.isColliding(stars.get(i))) {
                        pointCounter++;
                        stars.remove(stars.get(k));
                        k++;
                    }

               /* if(player.x==coin.x){
                    System.out.println("Heihei");
                    gc.strokeText("PowerUps",player.x,player.y);
                }*/
                    for (int a = 0; a < coins.size(); a++) {
                        if (player.isColliding(coins.get(a))) {
                            player.setPowerIsActive(true);
                            powerUpStart = System.currentTimeMillis();
                            pointCounter++;
                            coins.remove(coins.get(a));
                            a++;
                            gc.setFill(Color.BLACK);
                            gc.setStroke(Color.BLACK);
                            gc.strokeText("PowerUps ", player.getX() + 1, player.getY() + 1);

                        }
                    }
                }
                /*if(player.isColliding()){
                pointCounter++;
                } if(player.isColliding(star)&& takenCoin){
                    takenCoin = false;
            }*/

                //  goomba.enemyMovement(3);
                player.death();
                draw();
                if (player.hp == 0) {
                    timeline.stop();
                    //popup();

                    timeline = new Timeline();
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.getKeyFrames().add(keyframe);
                    timeline.play();


                    rightPressed.addListener((observable, oldValue, newValue) -> System.out.println("Right pressed"));
                    leftPressed.addListener((observable, oldValue, newValue) -> System.out.println("Left pressed"));
                    spacePressed.addListener((observable, oldValue, newValue) -> System.out.println("Space pressed"));
                    spaceAndRightPressed.addListener((observable, oldValue, newValue) -> System.out.println("Space and right pressed together"));
                    spaceAndLeftPressed.addListener((observable, oldValue, newValue) -> System.out.println("Space and left pressed together"));


                    canvas.setOnKeyPressed(ke -> {
                        if (ke.getCode() == KeyCode.SPACE) {
                            spacePressed.set(true);
                        } else if (ke.getCode() == KeyCode.RIGHT) {
                            rightPressed.set(true);
                        } else if (ke.getCode() == KeyCode.LEFT) {
                            leftPressed.set(true);
                        } else if (ke.getCode() == KeyCode.L) {
                            popup();
                        }
                    });

                    canvas.setOnKeyReleased(ke -> {
                        if (ke.getCode() == KeyCode.SPACE) {
                            spacePressed.set(false);
                        } else if (ke.getCode() == KeyCode.RIGHT) {
                            rightPressed.set(false);
                        } else if (ke.getCode() == KeyCode.LEFT) {
                            leftPressed.set(false);
                        } else if (ke.getCode() == KeyCode.P) {
                            if (!paused) {
                                paused = true;
                                gc.drawImage(Menu.getPause(), 50, 50, 300, 300);
                                timeline.pause();
                            } else {
                                paused = false;
                                timeline.play();
                            }
                        } else if (ke.getCode() == KeyCode.J) {
                            player.hp -= 1;
                            if (player.hp <= 0) {
                                popup();

                            }
                        } else if (ke.getCode() == KeyCode.S) {
                            shooting();

                        }
                    });
                }
            }
        }
    }
    // Controller commands for the game


    public void popup() {
        Alert gameOver = new Alert(Alert.AlertType.CONFIRMATION);
        gameOver.setTitle("Game Over");
        gameOver.setContentText("Do you want to try again?");

        //timeline.stop();
        Optional<ButtonType> result = gameOver.showAndWait();
        //timeline.play();

        if (result.get() == ButtonType.OK) {
            System.out.println("Try again!");
            player.hp = 3;
            preparedGame();
            timeline.play();
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("Close");
            System.exit(0);

        }

    }

    public void playerMovement() {
        if (rightPressed.get()) {
            player.x += 5;
        }
        if (leftPressed.get()) {
            player.x -= 5;
        }
        if (spacePressed.get() && !jumping) {
            jumping = true;
            falling = false;
            jumpOrigin = player.y;
        }

    }

    // Drawing methods
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /* gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); */

        drawMap(gc);


        //gc.clearRect(player.x, player.y, player.width, player.height);

        gc.setFill(Color.BLACK);
        gc.setFill(Color.BLUE);
        goombaImage = new Image("SemesterOppgave/Goomba.png");
        for (Enemy e : enemies) {
            gc.drawImage(goombaImage, e.x, e.y, 30, 30);
        }
        // gc.fillRect(goomba.x, goomba.y, goomba.width, goomba.height);
        gc.setFill(Color.RED);
        ugandaImage = new Image("SemesterOppgave/uganda.png");
        gc.drawImage(ugandaImage, player.x, player.y, 30, 30);
        //gc.fillRect(player.x, player.y, player.width, player.height);
        gc.setFill(Color.GREY);
        // gc.fillRect(5, 5, 200, 50);
        gc.setFill(Color.BLUE);
        gc.setFill(Color.BROWN);
        starImg = new Image("SemesterOppgave/Star1.png");
        for (Star s : stars) {
            gc.drawImage(starImg, s.x, s.y, s.width, s.height);
        }
        Image coinImg = new Image("SemesterOppgave/coin.png");
        for (Coin c : coins) {
            gc.drawImage(coinImg, c.x, c.y, c.width, c.height);
        }

        gc.setFill(Color.BROWN);
        gc.fillRect(5, 5, 200, 50);
        gc.setFill(Color.GREY);
        gc.fillRect(5, 5, 200, 50);

        Image heart = new Image("SemesterOppgave/Heart.png");
        for (int i = 0; i < player.hp; i++) {
            int x = 15 + i * 65;
            int y = 6;
            gc.drawImage(heart, x, y, 50, 50);
        }

        //player.drawPlayer(gc);
       /* for (int i = 0; i <enemies.size() ; i++) {
            enemies.get(i).drawEnemy(gc);
        }*/
        //må sjekkes
        //goomba.drawEnemy(gc);

        for (int i = 0; i < bullets.size(); i++) {


            bullets.get(i).drawBullet(gc);

            gc.strokeText(paused ? "Paused" : "Live", 200, 50);
            gc.strokeText(player.isColliding(star) ? "Points: " + pointCounter : "Points: " + pointCounter, 5, 300);

        }

    }

    // Makes the map
    public void shooting() {
        if (player.isPowerIsActive()) {
            int x = player.getX();
            int y = player.getY();
            Bullet bullet = new Bullet(x, x + 10, y, y + 10, 5);
            bullets.add(bullet);


            // NEED THIS?
    /*
        public class thread implements Runnable{
            @Override
            public void run() {
                try{
                    jumping = true;
                    Thread.sleep((long) jumpingTime);
                } catch (Exception e){
                    e.printStackTrace();
                new Thread(this).start();
                    System.exit(0);
                }
            }
        }
        */

        }
    }


    public void preparedGame() {
        stars.clear();
        enemies.clear();
        bullets.clear();

        player = new Player(0, 330, 30, 30);
        coin = new Coin(200, 330, 30, 30);
        star = new Star(250, 330, 30, 30);
        Star star1 = new Star(110, 330, 30, 30);
        stars.add(star);
        Star star2 = new Star(140, 330, 30, 30);
        Star star3 = new Star(150, 330, 30, 30);
        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        goomba = new Enemy(100, 200, 20, 50);
        Enemy goomba1 = new Enemy(250, 330, 20, 50);
        Enemy goomba2 = new Enemy(100, 200, 20, 50);
        Enemy goomba3 = new Enemy(50, 100, 20, 50);

        enemies.add(goomba);
        enemies.add(goomba1);
        enemies.add(goomba2);
        enemies.add(goomba3);
        Coin coin1 = new Coin(250, 330, 30, 30);
        Coin coin2 = new Coin(300, 330, 30, 30);
        coins.add(coin);
        coins.add(coin1);
        coins.add(coin2);

    }

    public void music() {
        File songfile = new File("src/SemesterOppgave/Mario.mp3");
        Media sound = new Media(songfile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.setOnEndOfMedia((new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);

            }


        }));
        mediaPlayer.play();
    }

}













