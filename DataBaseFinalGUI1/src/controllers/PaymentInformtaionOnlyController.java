package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaymentInformtaionOnlyController {

	@FXML
	void handelOkButton(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/AddTrainee.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

}
