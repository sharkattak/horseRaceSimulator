package application;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class manualController {
	@FXML
	private Label manualLabel;
	@FXML
	private Button startRace;
	 @FXML
	    private ImageView myImage;

	    private Stage stage;
	    private Parent root;
	    private Scene scene;
	    
	    
	    //After start race has been entered, display race page
	 	public void horseMovement(ActionEvent e) throws IOException, InvocationTargetException {
	 		 try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Race.fxml"));
		root = loader.load();
		
		RaceController raceController =loader.getController();
		URL location = getClass().getResource("Race.fxml");
		raceController.initialize(location, null);
		
		stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		scene=new Scene (root);
		
		

		stage.setScene(scene);
	}catch (IOException ex) {
        ex.printStackTrace(); // Print the stack trace to identify the cause of the exception
    }
	 		 
	 	}
	 	
		
	public void displayUserName(String userName) {
		String existingText = manualLabel.getText() +  "\r\n" ;
		String newText = "\r\n"
		+"Hey " + userName + "!" 
            + "\r\n"
            + "\r\n"
            + " Objective:\r\n"
            + "Compete in thrilling horse races to determine the fastest horse and emerge as the ultimate champion.\r\n"
            + "\r\n"
            + "Gameplay:\r\n"
            + "1. Start a New Race:\r\n"
            + "   - Choose the 'Start a new race' option from the main menu to begin.\r\n"
            + "   - You'll witness an exciting race with three horses competing against each other in separate lanes.\r\n"
            + "\r\n"
            + "2. Race Progression:\r\n"
            + "   - Each horse moves forward randomly based on its confidence level.\r\n"
            + "   - The probability of a horse moving forward or falling depends on its confidence rating.\r\n"
            + "   - A fallen horse cannot move until the end of the race.\r\n"
            + "\r\n"
            + "3. Race Completion:\r\n"
            + "   - The race continues until one of the horses crosses the finish line.\r\n"
            + "   - The first horse to complete the race is declared the winner.\r\n"
            + "\r\n"
            + "4. View Leaderboard:\r\n"
            + "   - Check the leaderboard history to see past race results and track the performance of different horses.\r\n"
            + "\r\n"
            + "5. Game Manual:\r\n"
            + "   - Refer to the game manual for detailed instructions and tips on how to maximize your chances of winning.\r\n"
            + "\r\n"
            + "Controls:\r\n"
            + "- Use the menu options to navigate through the game.\r\n"
            + "- No direct control over the horses' movements; their progress is simulated based on predefined probabilities.\r\n"
            + "\r\n"
            + "Tips for Success:\r\n"
            + "- Choose horses with higher confidence ratings for better chances of winning.\r\n"
            + "- Keep an eye on the race progress to anticipate the outcome.\r\n"
            + "\r\n"
            + "Have Fun and Good Luck!\r\n"
            + "Press X to return to the main menu or anything else to exit game\r\n";

		    manualLabel.setText(existingText + newText);
		}

}


