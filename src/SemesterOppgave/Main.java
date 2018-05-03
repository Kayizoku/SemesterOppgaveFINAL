package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loadMenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent menuPane = loadMenu.load();
        Scene scene1 = new Scene(menuPane, 600, 400);
        MenuController menuController = new MenuController();
        menuController.setFirstScene(scene1);
        loadMenu.setController(menuController);

        FXMLLoader mainPaneLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent mainPane = mainPaneLoader.load();
        Scene scene2 = new Scene(mainPane, 600, 400);
        MainController mainController = new MainController();
        menuController.setFirstScene(scene2);
        mainPaneLoader.setController(mainController);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene1);
        primaryStage.show();



    }



    public static void main(String[] args) {
        launch(args);
    }



}