package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import Utilties.*;

public class TrianeeIformationContoller implements Initializable {

	@FXML
	private RadioButton FEMALERB;

	@FXML
	private RadioButton MALERB;

	@FXML
	private Label emailLabel;

	@FXML
	private Label memberShipLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label paidDateLabel;

	@FXML
	private ComboBox<String> phoneNumberCB;
	@FXML
	private Label remainLabel;

	@FXML
	private TextField searchTextField;

	Connection con;

	@FXML
	void handelBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void handelSearch(ActionEvent event) throws ClassNotFoundException, SQLException {
		System.out.println("Inside Search Method");
//		phoneNumberCB.getSelectionModel().clearSelection();
		phoneNumberCB.getItems().removeAll(phoneNumberCB.getItems());
		emailLabel.setVisible(true);
		memberShipLabel.setVisible(true);
		nameLabel.setVisible(true);
		paidDateLabel.setVisible(true);
		remainLabel.setVisible(true);

		int searchId = Integer.valueOf(searchTextField.getText());
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql1 = "Select traineeId from trainee where traineeId = " + searchId;
		System.out.println(sql1);
		ResultSet rs = con.createStatement().executeQuery(sql1);
//		System.out.println(rs.next() != false);
		if (rs.next() != false) {
			String sql = "Select concat(t.fname , \" \",t.lname) as name , t.gender ,t.email  , ms.membershipType , tp.paidDate from trainee t , phoneNumberTrainee pt , traineeJoinPayment tp , membership ms where t.traineeId = pt.traineeId and tp.traineeId =t.traineeId and ms.membershipid =tp. membershipid and t.traineeId ="
					+ searchId + " ;";
			ResultSet rs1 = con.createStatement().executeQuery(sql);
			if (rs1.next()) {
				nameLabel.setText(rs1.getString(1));
				MALERB.setSelected(rs1.getString(2).equalsIgnoreCase("Male"));
				FEMALERB.setSelected(rs1.getString(2).equalsIgnoreCase("Female"));
				emailLabel.setText(rs1.getString(3));
				memberShipLabel.setText(rs1.getString(4));
				paidDateLabel.setText(rs1.getString(5));

			}
			fillPhoneNumber();
			con.close();

		} else {
			Massege.displayMassage("ID OF TRIANEE", "The ID was Not Correct , Try Again");

		}

	}

	private void fillPhoneNumber() throws ClassNotFoundException, SQLException {
		int searchId = Integer.valueOf(searchTextField.getText());
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql1 = "Select traineeId from trainee where traineeId =" + searchId;
		System.out.println(sql1);
		ResultSet rs = con.createStatement().executeQuery(sql1);
		if (rs.next()) {

			if (searchId != -1) {
//				DBInitializing DB = new DBInitializing(); // connecting to database
//				DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(),
//						DB.getDbPassword());
				con = a.connectDB();
				String sql = " select phoneNumber from phoneNumberTrainee where traineeId =" + searchId + ";";
				ResultSet rs1 = con.createStatement().executeQuery(sql);
				while (rs1.next()) {
					phoneNumberCB.getItems().addAll(rs1.getString(1));

				}

			} else {
				System.out.println("Not Exsist ID");
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emailLabel.setVisible(false);
		memberShipLabel.setVisible(false);
		nameLabel.setVisible(false);
		paidDateLabel.setVisible(false);
		remainLabel.setVisible(false);
		phoneNumberCB.getSelectionModel().clearSelection();

	}

	@FXML
	void handelOk(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}
}
