package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DesignController {

	@FXML
	private Pane pane;

	@FXML
	void handelAllMember(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/AllMemberView.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelClassSchedule(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/ActivityInformation.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelCoach(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/CoachInformation.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelGroup(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/SchedualeClassInfo.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelHome(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/DashBoardView.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelLogOut(MouseEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent SignInStage = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));
		Scene scene = new Scene(SignInStage);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("SignIn View");
	}

	@FXML
	void handelMemberShipType(MouseEvent event) throws IOException {
		// MemberShipInfromation
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/MemberShipInfromation.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelPayment(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/ClassOfPaymentInfo.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelProfile(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/AdminProfile.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelTrainee(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/TraineeTV.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}

	@FXML
	void handelTraineeInSchedule(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/GroupTrainee.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);
	}

	@FXML
	void handelSubscribe(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/EndMembership.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);
	}

}
