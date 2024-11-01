package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;

import static controllers.SingInController.LoginAdminId;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Utilties.Massege;

public class ProfileController implements Initializable {
	@FXML
	private Button changePasswordButton;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private ComboBox<String> phoneNubmerCBox;
	@FXML
	private TextField userNameTextField;
	@FXML
	private ToggleGroup gendrTogelG;

	@FXML
	private RadioButton FemaleRB;

	@FXML
	private TextField newPhoneTextField;

	@FXML
	private RadioButton MaleRB;
	Connection con;

	@FXML
	private Button updateInfoButton;
	private String fn, ln;
	private int idAdmin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameTextField.setEditable(false);
		refershInfo();
//		DBInitializing DB = new DBInitializing(); // connecting to database
//		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
//		try {
//			con = a.connectDB();
//			Statement statement = con.createStatement();
//			String sql = "select * from administrator where administratorId = " + LoginAdminId + ";";
//			ResultSet rs = statement.executeQuery(sql);
//			if (rs.next()) {
//				userNameTextField.setText(rs.getString(1));
//				firstNameTextField.setText(rs.getString(2));
//				lastNameTextField.setText(rs.getString(3));
//
//				emailTextField.setText(rs.getString(5));
//				FemaleRB.setSelected(rs.getString(4).equalsIgnoreCase("Female"));
//				MaleRB.setSelected(rs.getString(4).equalsIgnoreCase("Male"));
//				fn = firstNameTextField.getText().trim();
//				ln = lastNameTextField.getText().trim();
//
//			}
//
//			rs.close();
//			statement.close();
//			Statement statement1 = con.createStatement();
//			String sql1 = "select phoneNumber from phoneNumberAdministrator where administratorId = " + LoginAdminId
//					+ ";";
//			ResultSet rs1 = statement1.executeQuery(sql1);
////			ResultSet rs1 = con.createStatement().executeQuery(sql1);
//			ObservableList<String> item = FXCollections.observableArrayList(); // List of String
//			while (rs1.next())
//				item.add(rs1.getString("phoneNumber"));
//
//			phoneNubmerCBox.setItems(item);
//			rs1.close();
//			statement1.close();
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
	}

	@FXML
	void handelChangePassword(ActionEvent event) throws IOException {
		System.out.println("Inside Change Password");
		if (event.getSource() == changePasswordButton) {
//			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/changePaswordAdminView.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/changePaswordAdminView.fxml")));
			Scene scene = new Scene(firstSceneView);
			stage.setScene(scene);
			stage.show();

		}

	}

	@FXML
	void handelUpdateInfroation(ActionEvent event) throws ClassNotFoundException, SQLException {
		getAdminId();
		updateAdimnInfo();
		updatePhoneNubmer();
		refershInfo();

	}

	private void updateAdimnInfo() throws ClassNotFoundException, SQLException {
		System.out.println("Inside Update Admin Info");
		String fn = firstNameTextField.getText().trim();
		String ln = lastNameTextField.getText().trim();
		String gender = MaleRB.isSelected() ? "Male" : "Female";
		String email = emailTextField.getText().trim();

		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();

//		String sql = "select * from administrator where administratorId = " + LoginAdminId + ";";
		String sql = "update powerhouse_gym.administrator set Fname = '" + fn + "' , lname ='" + ln + "' ,  gender = '"
				+ gender + "' , email = '" + email + "' where administratorId =" + idAdmin + " ;";
		System.out.println(sql);
		DB.ExecuteUpdateStatement(sql);

	}

	private void updatePhoneNubmer() throws ClassNotFoundException, SQLException {
		System.out.println("Insid Update PhoneNumber Info");
		String phoneNum = newPhoneTextField.getText().trim();
		String oldPhone = phoneNubmerCBox.getSelectionModel().getSelectedItem();
		oldPhone = (oldPhone == null) ? null : oldPhone;

		if (oldPhone == null) {
			System.out.println("Not Item Need To Change");
			Massege.displayMassage("update Phone Nubmer ", "Can Not Update Phone Number , there is Empty Field");
			return;
		}
		System.out.println("New Phone Num ==> " + phoneNum);
		System.out.println("Old Phone Num ==> " + oldPhone);
		if (!phoneNum.isEmpty()) {
//			oldPhone = phoneNum;

			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();

			String sql = "update powerhouse_gym.phoneNumberAdministrator set phoneNumber = '" + phoneNum
					+ "' where administratorId =" + idAdmin + " and phoneNumber ='" + oldPhone + "' ; ";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
			phoneNubmerCBox.getSelectionModel().clearSelection();
			newPhoneTextField.clear();

		} else {
			System.out.println("Not Add New Phone ");
		}

	}

	private int getAdminId() throws ClassNotFoundException, SQLException {
		int id = -1;
		String sql = "Select administratorId from administrator where fname = '" + fn + "' and lname = '" + ln + "' ;";
		System.out.println(sql);
		if (!fn.isEmpty() || !ln.isEmpty()) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				id = rs.getInt(1);
			}
			idAdmin = id;

		}

		System.out.println("Id of Admin " + id);

		return id;

	}

	private void refershInfo() {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		try {
			con = a.connectDB();
			Statement statement = con.createStatement();
			String sql = "select * from administrator where administratorId = " + LoginAdminId + ";";
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				userNameTextField.setText(rs.getString(1));
				firstNameTextField.setText(rs.getString(2));
				lastNameTextField.setText(rs.getString(3));

				emailTextField.setText(rs.getString(5));
				FemaleRB.setSelected(rs.getString(4).equalsIgnoreCase("Female"));
				MaleRB.setSelected(rs.getString(4).equalsIgnoreCase("Male"));
				fn = firstNameTextField.getText().trim();
				ln = lastNameTextField.getText().trim();

			}

			rs.close();
			statement.close();
			Statement statement1 = con.createStatement();
			String sql1 = "select phoneNumber from phoneNumberAdministrator where administratorId = " + LoginAdminId
					+ ";";
			ResultSet rs1 = statement1.executeQuery(sql1);
//			ResultSet rs1 = con.createStatement().executeQuery(sql1);
			ObservableList<String> item = FXCollections.observableArrayList(); // List of String
			while (rs1.next())
				item.add(rs1.getString("phoneNumber"));

			phoneNubmerCBox.setItems(item);
			rs1.close();
			statement1.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
