package controllers;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import Utilties.*;

public class firstSceneController {
	@FXML
	private Button backButton;

	@FXML
	private Button aboutUsButton;

	@FXML
	private Button signInButton;
	@FXML
	private Button exitButton;

	@FXML
	void setAboutUsView(ActionEvent event) throws IOException {
		System.out.println("About Us");
		if (event.getSource() == aboutUsButton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
			Parent AboutUsStage = FXMLLoader.load((getClass().getResource("../FXML/AboutUs.fxml")));
			Scene scene = new Scene(AboutUsStage);
			stage.setScene(scene);
			stage.show();
			stage.setTitle("About Us Window");
		}

	}

	@FXML
	void setSignInView(ActionEvent event) throws IOException {
		System.out.println("Sign in ");
		if (event.getSource() == signInButton) {

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			Parent SignInStage = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));

			Scene scene = new Scene(SignInStage);
			stage.setScene(scene);
			stage.show();
			stage.setTitle("SignIn View");
		}

	}

	@FXML
	void setBackToMainView(ActionEvent event) throws IOException {
		if (event.getSource() == backButton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/FirstScene.fxml")));
			Scene scene = new Scene(firstSceneView);
			stage.setScene(scene);
			stage.show();

		}

	}

	@FXML
	void handelExitButton(ActionEvent event) throws InterruptedException {
		if (event.getSource() == exitButton) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Power House Gym");
			alert.setContentText("Press Ok To Exit From The Program");
			alert.setHeaderText("Welcom In Power House Gym");
			Optional<ButtonType> firstStage = alert.showAndWait();

			if (firstStage.get() == ButtonType.OK) {
				System.exit(1);
			}

		}

	}

}
