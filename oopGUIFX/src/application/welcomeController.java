package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class welcomeController {
	@FXML
	TextField nameTextField;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//back to Welcome Page
	public void displayWelcomePage(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		scene=new Scene (root);
		stage.setScene(scene);
	}
	//After a name has been entered,greet user with userName
	public void displayManualPage(ActionEvent e) throws IOException {
		String userName= nameTextField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Manual.fxml"));
		root = loader.load();
		
		manualController manualController = loader.getController();
		manualController.displayUserName(userName);
		
		stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		scene=new Scene (root);
		stage.setScene(scene);
	}
	
}
