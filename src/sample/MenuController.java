package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MenuController  implements Initializable {



    private Scene firstScene;

    public void setFirstScene(Scene scene) {

        firstScene = scene;
    }


    public void startGame(ActionEvent event) {

        Parent mainPane = null;

        try {

            mainPane = FXMLLoader.load(getClass().getResource("Main.fxml"));

        } catch (IOException e) {
            e.printStackTrace();

        }

        Stage stage = new Stage();
        stage.setTitle("Game");
        Scene scene1 = new Scene(mainPane, 600, 400);
        stage.setScene(scene1);
        stage.show();
        scene1 = ((Node) event.getSource()).getScene();
        scene1.getWindow().hide();
    }

    public void loadGame(ActionEvent event) {


        content = "x:" + player.x + ",y:" + player.y;

        Parent mainPane = null;
        FileReader fr;
        try {

            if (!file.exists()) {

                System.out.println("File does not exist");

            }

            fr = new FileReader(file);


            BufferedReader br = new BufferedReader(fr);
            content = br.readLine();
            String str = content.toString();
            String reg = "(,)|(:)";
            String[] regx = str.split(reg);

            System.out.println("Fil innhold : " + fr.toString());
            System.out.println("Content:  " + content.toString());

            int xpos = Integer.parseInt(regx[1]);
            int ypos = Integer.parseInt(regx[3]);

            player.setX(xpos);
            player.setY(ypos);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loading gameSave...successful");

        Stage stage = new Stage();
        stage.setTitle("Game");
        Scene scene2 = new Scene((mainPane), 600, 400);
        stage.setScene(scene2);
        stage.show();
        scene2 = ((Node) event.getSource()).getScene();
        scene2.getWindow().hide();

    }




    public void quitGame(ActionEvent event) throws IOException {

        Parent menuPane = null;

        try {

            menuPane = FXMLLoader.load(getClass().getResource("Menu.fxml"));

            Stage stage = new Stage();
            Scene scene2 = new Scene(menuPane, 600, 400);
            stage.setScene(scene2);
            scene2 = ((Node) event.getSource()).getScene();
            scene2.getWindow().hide();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }


    Player player;

    Path rp = Paths.get("");
    String ap = rp.toAbsolutePath().toString();

    File file = new File(ap + "/src/sample/saves/gameSave.txt");


    String content = "";

    public static Image pause = new Image("sample/paused.png");

    static boolean pauseImage = false;

    public  static Image getPause() {
        return pause;
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        Platform.runLater(this::later);
    }

    public void later() {

    }

}