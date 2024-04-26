
package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RaceController implements Initializable {

    @FXML
    private ImageView horse1;
    @FXML
    private ImageView horse2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TranslateTransition for horse1
        TranslateTransition translateHorse1 = new TranslateTransition();
        translateHorse1.setNode(horse1);
        translateHorse1.setDuration(Duration.millis(1000));
        translateHorse1.setByX(600);
        translateHorse1.play();

        // TranslateTransition for horse2
        TranslateTransition translateHorse2 = new TranslateTransition();
        translateHorse2.setNode(horse2);
        translateHorse2.setDuration(Duration.millis(1000));
        translateHorse2.setByX(700);
        translateHorse2.play();
    }
}