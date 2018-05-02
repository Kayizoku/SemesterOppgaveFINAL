import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    Path rp = Paths.get("");
    String ap = rp.toAbsolutePath().toString();

    File file = new File(ap + "/src/SemesterOppgave/saves/gameSave.txt");


    String content = "";

    private Scene firstScene;

    static boolean pauseImage = false;
    public static Image pause = new Image("/SemesterOppgave/paused.png");

    public static Image getPause(){
        return pause;
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        Platform.runLater(this::later);
    }

    public void later() {

    }

}