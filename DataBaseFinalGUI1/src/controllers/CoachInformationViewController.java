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

public class CoachInformationViewController implements Initializable {

	@FXML
	private RadioButton FemaleRB;

	@FXML
	private RadioButton MaleRB;

	@FXML
	private Label emailLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label phoneNumberLabel;

	@FXML
	private TextField searchTextField;

	@FXML
	private Label trainingLabel;
	@FXML
	private ComboBox<String> phoneNumbCB;
	Connection con;

	@FXML
	void backHandel(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void handelOk(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void handelSearch(ActionEvent event) throws ClassNotFoundException, SQLException {
		emailLabel.setVisible(true);
		nameLabel.setVisible(true);
		trainingLabel.setVisible(true);
		phoneNumbCB.getItems().removeAll(phoneNumbCB.getItems());
		int id = -1;
//		if (searchTextField.getText() != null) {
		try {
			id = Integer.valueOf(searchTextField.getText().trim());
		} catch (NumberFormatException e) {
			System.out.println(e);
			Massege.displayMassage("Erorr in Id", "Please Enter Id To Search\nTry Again");

		}
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
//			if (id != -1)
		String sql1 = "Select coachId from coach where coachId = " + id + " ;";
		System.out.println(sql1);
		ResultSet rs = con.createStatement().executeQuery(sql1);
		if (rs.next() != false) {
			String sql = "Select concat(fname , \" \",lname) as name , gender , trainingExperiences , email from coach where coachid = +"
					+ id + " ; ";
			ResultSet rs1 = con.createStatement().executeQuery(sql);
			if (rs1.next()) {
				nameLabel.setText(rs1.getString(1));
				FemaleRB.setSelected(rs1.getString(2).equalsIgnoreCase("Female"));
				MaleRB.setSelected(rs1.getString(2).equalsIgnoreCase("Male"));
				trainingLabel.setText(rs1.getString(3));
				emailLabel.setText(rs1.getString(4));

			}
			fillPhoneNumber();
			con.close();

		} else {
			Massege.displayMassage("Erorr in Id", "ID was Entered is not Corretct\nTry Again");

		}
//		} else {
//			Massege.displayMassage("Erorr in Id", "Please Enter Id To Search\nTry Again");

	}

	private void fillPhoneNumber() throws ClassNotFoundException, SQLException {
		int id = Integer.valueOf(searchTextField.getText().trim());
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql1 = "Select coachId from coach where coachId = " + id + " ;";
		System.out.println(sql1);
		ResultSet rs = con.createStatement().executeQuery(sql1);
		if (rs.next() != false) {

			String sql = "Select phoneNumber from phoneNumberCoach where coachId =" + id + " ;";
			ResultSet rs1 = con.createStatement().executeQuery(sql);
			while (rs1.next()) {
				phoneNumbCB.getItems().addAll(rs1.getString(1));

			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emailLabel.setVisible(false);
		nameLabel.setVisible(false);
		trainingLabel.setVisible(false);
		phoneNumbCB.getSelectionModel().clearSelection();

	}

}