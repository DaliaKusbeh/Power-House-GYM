package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController {

	@FXML
	private Button classScheduleButton;
	@FXML
	private Button coachesButton;
	@FXML
	private Button dashBoardButton;
	@FXML
	private Label directoryLabel;
	@FXML
	private Button groupButton;
	@FXML
	private Button logoutbutton;
	@FXML
	private Button memberShipButton;
	@FXML
	private Button paymentButton;
	@FXML
	private Button profileButton;
	@FXML
	private Button repotButton;
	@FXML
	private Button staffMemberButton;
	@FXML
	private Button traineeButton;
	@FXML
	private Pane titlePane;
	@FXML
	protected Pane paneScene;

//	@FXML
//	public void handleClick(ActionEvent event) {
//		if (event.getSource() == dashBoardButton) {
//			titleLabel.setText("DashBoard");
//			directoryLabel.setText("/home/DashBoard");
//			titlePane.setStyle("-fx-background-color: #E63E6D");
//		} else if (event.getSource() == classScheduleButton) {
//			titleLabel.setText("class Schedule");
//			directoryLabel.setText("/home/class Schedule");
//			titlePane.setStyle("-fx-background-color: #7D1935");
//		} else if (event.getSource() == coachesButton) {
//			titleLabel.setText("Coach");
//			directoryLabel.setText("/home/Coach");
//			titlePane.setStyle("-fx-background-color: #B42B51");
//		} else if (event.getSource() == groupButton) {
//			titleLabel.setText("Group");
//			directoryLabel.setText("/home/Group");
//			titlePane.setStyle("-fx-background-color: #7D1935");
//		} else if (event.getSource() == logoutbutton) {
//			titleLabel.setText("Log Out");
//			directoryLabel.setText("/home/DashBoard");
//			titlePane.setStyle("-fx-background-color: #B42B51");
//		} else if (event.getSource() == memberShipButton) {
//			titleLabel.setText("MemberShip");
//			directoryLabel.setText("/home/MemberShip");
//			titlePane.setStyle("-fx-background-color: #B42B51");
//		} else if (event.getSource() == paymentButton) {
//			titleLabel.setText("Payment");
//			directoryLabel.setText("/home/Payment");
//			titlePane.setStyle("-fx-background-color: #E63E6D");
//		} else if (event.getSource() == profileButton) {
//			titleLabel.setText("Profile");
//			directoryLabel.setText("/home/Profile");
//			titlePane.setStyle("-fx-background-color: #7D1935");
//		} else if (event.getSource() == repotButton) {
//			titleLabel.setText("Report");
//			directoryLabel.setText("/home/Report");
//			titlePane.setStyle("-fx-background-color: #E63E6D");
//		} else if (event.getSource() == staffMemberButton) {
//			titleLabel.setText("Staff Member");
//			directoryLabel.setText("/home/DashBoard");
//			titlePane.setStyle("-fx-background-color: #7D1935");
//		}
//	}

	@FXML
	public void openCoachStage(ActionEvent event) throws IOException {
		System.out.println("YAY\n\n");

		if (event.getSource() == coachesButton) {

			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/CoachestableView.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);

//			Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXML/AboutUs.fxml"));
//			final Stage AboutUsStage = new Stage();
//			AboutUsStage.initModality(Modality.NONE);
//			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
//			AboutUsStage.initOwner(app_stage);
//			System.out.println("YAY\n\n");
//			System.out.println("WTF");
//			Scene scene11 = new Scene(nextSceneParent);
//			AboutUsStage.setScene(scene11);
//			AboutUsStage.show();
		}
	}

	@FXML
	void MemberShipTypeView(ActionEvent event) throws IOException {

		if (event.getSource() == memberShipButton) {
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/MembershipTableView.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);

		}

	}

	@FXML
	void classSchedualeView(ActionEvent event) throws IOException {
		System.out.println("Click In Scheduale");
		if (event.getSource() == classScheduleButton) {
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/SchadualeTableView.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);
		}

	}

	@FXML
	void coachesView(ActionEvent event) throws IOException {
		System.out.println("Click In Coach");
		if (event.getSource() == coachesButton) {
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/CoachestableView.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);
//		

		}
	}

	@FXML
	void dashBoardView(ActionEvent event) throws IOException {
		if (event.getSource() == dashBoardButton) {
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/DashBoardView.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);
		}

	}

	@FXML
	void groupView(ActionEvent event) {

	}

	@FXML
	void paymentView(ActionEvent event) throws IOException {
		System.out.println("Click on Payment");
		if (event.getSource() == paymentButton) {
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/TraineeJoinPayment.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);

		}

	}

	@FXML
	void profileView(ActionEvent event) throws IOException {
		if (event.getSource() == profileButton) {
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/AdminProfile.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);

		}

	}

	@FXML
	void reportView(ActionEvent event) {

	}

	@FXML
	void staffMemberView(ActionEvent event) {

	}

	@FXML
	void traineeView(ActionEvent event) throws IOException {
		if (event.getSource() == traineeButton) {

			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/TraineeTableView.fxml"));
			paneScene.getChildren().removeAll();
			paneScene.getChildren().setAll(fxml);

		}

	}

	public void handelLogOut(ActionEvent event) throws IOException {

		if (event.getSource() == logoutbutton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			Parent SignInStage = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));

			Scene scene = new Scene(SignInStage);
			stage.setScene(scene);
			stage.show();
			stage.setTitle("SignIn View");

		}
	}

	public Pane getPaneScene() {
		return paneScene;
	}

	public void setPaneScene(Pane paneScene) {
		this.paneScene = paneScene;
	}

}