package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Optional;

import Utilties.Massege;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.Administrator;
import sample.DBConn;
import sample.DBInitializing;
import Utilties.*;

public class SignUpController {

	@FXML
	private Button backButton;

	@FXML
	private RadioButton femaleRadioButton;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lashNameTextField;

	@FXML
	private RadioButton maleRadioButton;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private TextField phoneNumberTextField;

	@FXML
	private TextField userNameTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private Button signUpButton;
	@FXML
	private Label WrongFNameLabel;

	@FXML
	private Label WrongGenderLabel;

	@FXML
	private Label WrongLNameLabel;

	@FXML
	private Label WrongPasswordLabel;

	@FXML
	private Label WrongPhoneLabel;

	@FXML
	private Label WrongUserLabel;
	@FXML
	private Label wrongUserNameExsistLabel;
	@FXML
	private ToggleGroup genderTG;
	@FXML
	private Label WEmail;
	Connection con;

	@FXML
	void handleSetBack(ActionEvent event) throws IOException {
		if (event.getSource() == backButton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));
			Scene scene = new Scene(firstSceneView);
			stage.setScene(scene);
			stage.show();
		}
	}

	@FXML
	void handleSignUp(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		WrongUserLabel.setVisible(false);
		WrongFNameLabel.setVisible(false);
		WrongLNameLabel.setVisible(false);
		WrongPasswordLabel.setVisible(false);
		WrongPhoneLabel.setVisible(false);
		WrongGenderLabel.setVisible(false);
		WEmail.setVisible(false);

		boolean flag = false;

		String firstName = "";
		try {
			firstName = firstNameTextField.getText().trim();
			if (firstName.length() == 0) {
				throw new Exception("Empty First Name Text Field");
			}
		} catch (Exception e) {
			System.out.println(e + "Wrong First Name");
			WrongFNameLabel.setVisible(true);
			flag = true;
			Massege.displayMassage(e + "", "Wrong First Name");

		}
		String lastName = "";
		try {
			lastName = lashNameTextField.getText().trim();
			if (lastName.length() == 0) {
				throw new Exception("Empty Last Name Text Field");
			}
		} catch (Exception e) {
			System.out.println(e + " wrong Last Name");
			flag = true;
			Massege.displayMassage(e + "", "Wrong Last Name");

			WrongLNameLabel.setVisible(true);
		}
		int userId = -1;
		try {
			userId = Integer.parseInt(userNameTextField.getText().trim());
		} catch (Exception e) {
			System.out.println(e + " Wrong Admin Id ");
			WrongUserLabel.setVisible(true);
			flag = true;
			Massege.displayMassage("SignIn", "Wrong Admin Id");

		}
		String pass = "";
		try {
			pass = passwordTextField.getText().trim();
			System.out.println("The Pass : " + pass);
			if (pass.length() == 0 || !pass.matches("[0-9A-Za-z]+"))
				throw new Exception(" PassWord Filed Not Valid ");
		} catch (Exception e) {
			System.out.println(e + ", Wrong PassWord ");
			WrongPasswordLabel.setVisible(true);
			flag = true;
			Massege.displayMassage(e + "", "Wrong PassWord");

		}

		String PhoneNum = "";
		try {
			PhoneNum = phoneNumberTextField.getText().trim();
			if (!(PhoneNum.matches("[0-9]+")))
				throw new Exception("Not Valid");
		} catch (Exception e) {
			System.out.println(e + " Wrong Phone Number");
			WrongPhoneLabel.setVisible(true);
			flag = true;
		}

		String gender = "";
		if (femaleRadioButton.isSelected())
			gender = "Female";
		else if (maleRadioButton.isSelected())
			gender = "Male";
		else {
			WrongGenderLabel.setVisible(true);
			flag = false;
		}
		System.out.println("Gender" + gender);
		String email = "";
		try {
			email = emailTextField.getText().trim();
			System.out.println("Email : " + email);
			if (email.length() == 0) {
				System.out.println("Email " + email);
				throw new Exception(" Email Filed Not Valid ");
			}
		} catch (Exception e) {
			System.out.println(e + "Fill Email Field");
			WEmail.setVisible(true);
			flag = true;
			Massege.displayMassage(e + "", "Please Fill Email Field");

		}

		if (!flag) {
			System.out.println("in flag");

			DBInitializing DB = new DBInitializing(); // connecting to database
			String sql3 = "SELECT administratorId FROM powerhouse_gym.administrator P where P.administratorId = "
					+ userId + " ;";
			System.out.println(sql3);

			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			Statement stmt3 = con.createStatement();
			ResultSet rs3 = stmt3.executeQuery(sql3);

			if (rs3.next() == false) {
//				public Administrator(int administratorId, String fname, String lname, String gender, String passworde, String email,
//						String phone) {

				Administrator temp = new Administrator(userId, firstName, lastName, gender, pass, email, PhoneNum);
				System.out.println(
						"Insert into powerhouse_gym.administrator (administratorId,Fname,Lname,gender,passworde,email) values( "
								+ temp.getAdministratorId() + "','" + temp.getFname() + "','" + temp.getLname() + "','"
								+ temp.getGender() + "','" + temp.getPassworde() + "' , '" + temp.getEmail() + "' );");

				String sql4 = "Insert into powerhouse_gym.administrator (administratorId,Fname,Lname,gender,passworde,email) values( "
						+ temp.getAdministratorId() + ",'" + temp.getFname() + "','" + temp.getLname() + "','"
						+ temp.getGender() + "','" + temp.getPassworde() + "' , '" + temp.getEmail() + "' );";

//				String sql4 = "Insert into powerhouse_gym.administrator (administratorId, Fname, Lname,gender,passworde) values('"
//						+ temp.getAdministratorId() + "','" + temp.getFname() + "','" + temp.getLname() + "','"
//						+ temp.getGender() + "','" + temp.getPassworde() + "');";

				try {
					DB.ExecuteUpdateStatement(sql4);
					System.out.println("Done");
				} catch (Exception e) {
					System.out.println(e + " Not Excuted ..!");
				}

				String sql5 = "insert into phoneNumberAdministrator (administratorId,phoneNumber) values ("
						+ temp.getAdministratorId() + ",'" + temp.getPhone() + "');";
				System.out.println(sql5);
				DBInitializing DB1 = new DBInitializing(); // connecting to database

				DBConn a1 = new DBConn(DB1.getURL(), DB1.getPort(), DB1.getDbName(), DB1.getDbUsername(),
						DB1.getDbPassword());
				con = a1.connectDB();
//				DB1.ExecuteUpdateStatement(sql5);
				try {
					DB1.ExecuteUpdateStatement(sql5);
					System.out.println("Done Phone");
				} catch (Exception e) {
					System.out.println(e + " Not Excuted Phone ..!");
				}

				Massege.displayMassage("Create New Account", "Create New Account Was Done successfully");
				Optional<Massege>opt ;
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));
				Scene scene = new Scene(firstSceneView);
				stage.setScene(scene);
				stage.show();

//				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
//				Parent AboutUsStage = FXMLLoader.load((getClass().getResource("../FXML/MainView.fxml")));
//				Scene scene = new Scene(AboutUsStage);
//				stage.setScene(scene);
//				stage.show();
//				stage.setTitle("Main Window");
				clear();
			} else {
				System.out.println("Not Exsists");
				wrongUserNameExsistLabel.setVisible(true);
				Massege.displayMassage("SignUp", "Not Exsists Id");
			}
			stmt3.close();
			con.close();
			rs3.close();

		} else {
			clear();
			Massege.displayMassage("SignUp", "Please Fill All Filed");
		}

	}

	private void clear() {
		firstNameTextField.clear();
		lashNameTextField.clear();
		passwordTextField.clear();
		phoneNumberTextField.clear();
		userNameTextField.clear();

	}

}
