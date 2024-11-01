package sample;

import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DriverDB extends Application {
	public Parent root;
	double x, y;

	@Override
	public void start(Stage stage) throws Exception {
//		root = FXMLLoader.load((getClass().getResource("../FXML/DashBoardView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/SignUp.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/TraineeTableView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/CoachTableView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/MainView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/MembershipTableView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/TraineeJoinPayment.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/AdminTableView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/ChangePasswordView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/FirstScene.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/AddTrainee.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/AddCoachView.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/AddCoachToSchaduale.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/CoachInformation.fxml")));
		root = FXMLLoader.load((getClass().getResource("../FXML/FirstScene.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/GroupTrainee.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/TrianeeInformation.fxml")));
//		root = FXMLLoader.load((getClass().getResource("../FXML/EndMembership.fxml")));

		Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("../CSS/fullPackUpStyle.css").toExternalForm());

		stage.setScene(scene);
//		stage.initStyle(StageStyle.UNDECORATED);
		root.setOnMousePressed(evt -> {
			x = evt.getSceneX();
			y = evt.getSceneY();
//			System.out.println("SDs1");
		});
		root.setOnMouseDragged(evt -> {
			stage.setX(evt.getScreenX() - x);
			stage.setY(evt.getScreenY() - y);
//			System.out.println("SD2s");

		});
		stage.setTitle("First JavaFX Application");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
