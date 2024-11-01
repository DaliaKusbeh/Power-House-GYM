package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import Utilties.*;

public class AddCoachContoller {

	@FXML
	private TextField EmailText;

	@FXML
	private RadioButton FemaleRB;

	@FXML
	private TextField FirstNameText;

	@FXML
	private TextField LastNameText;

	@FXML
	private TextField TraningExp;

	@FXML
	private Label WTrExp;

	@FXML
	private Label Wemail;

	@FXML
	private Label WfirstName;

	@FXML
	private Label Wgender;

	@FXML
	private Label WlastName;

	@FXML
	private Label WphoneNumber;

	@FXML
	private Button addNewCoachButton;

	@FXML
	private RadioButton maleRB;

	@FXML
	private TextField phoneText;

	@FXML
	private Button addToTheExeButton;

	public static int coachId = -1;
	String fname, lname;
	@FXML
	private ToggleGroup genderTogelGroup;
	Connection con;

	@FXML
	void handelAddCoach(ActionEvent event) throws ClassNotFoundException, SQLException {
//

		insertAtCoactInformation();
		insertPhone();
		clear();
	}

	private void insertPhone() throws ClassNotFoundException, SQLException {
		try {
			System.out.println("Phone Before " + phoneText.getText());
			if (Validation.isPhoneNumber(phoneText.getText())) {

				long phone = Long.valueOf(phoneText.getText().trim());

				System.out.println("Phone After " + phone);
				if (coachId != -1) {
					DBInitializing DB = new DBInitializing(); // connecting to database
					DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(),
							DB.getDbPassword());
					con = a.connectDB();
//			Statement stmt = con.createStatement();

					System.out.println("insert into phoneNumberCoach (coachId,phoneNumber) values (" + coachId + ", '"
							+ phone + "');");
					ExecuteStatement("insert into phoneNumberCoach (coachId,phoneNumber) values (" + coachId + ", '"
							+ phone + "');");

				} else {
					System.out.println("Can Not ADD phoneNumberCoach Becuase the coachId" + coachId);
				}
			}
		} catch (Exception e) {
			System.out.println(e + " In Phone Number");
		}

	}

	private void insertAtCoactInformation() throws ClassNotFoundException, SQLException {
		String fname = FirstNameText.getText().trim();
		String lname = LastNameText.getText().trim();
		String gender = (maleRB.isSelected() == true) ? "Male" : "Female";
		String trainingExperiences = TraningExp.getText().trim();
		String email = EmailText.getText().trim();
		String phone = phoneText.getText().trim();
		this.fname = fname;
		this.lname = lname;
		System.out.println(Validation.isName(fname));
		System.out.println(Validation.isName(lname));
		System.out.println(Validation.isPhoneNumber(phone));

		if (Validation.isName(fname) && Validation.isName(lname) && Validation.isPhoneNumber(phone)) {
//			insert into coach (coachId,Fname,Lname,gender,trainingExperiences,email) 
			System.out.println(
					"Insert into coach (Fname,Lname,gender,trainingExperiences,email) values( '" + fname + "','" + lname
							+ "' , '" + trainingExperiences + "', '" + trainingExperiences + "','" + email + "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();

			ExecuteStatement("Insert into coach (Fname,Lname,gender,trainingExperiences,email) values( '" + fname
					+ "','" + lname + "' , '" + gender + "', '" + trainingExperiences + "','" + email + "');");
			getCoachID();

			con.close();
		} else {
			Massege.displayMassage("Erorr : Add Coach ", "Your Information is not Valid");
		}

	}

	private int getCoachID() throws ClassNotFoundException, SQLException {
		System.out.println("INSID Get Traineee Id");
		int id = -1;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		System.out.println(fname + " " + lname + " The Name ");
//		String sql = "select coachid from coach where fname ='" + fname + "' and lname = '" + lname + "' ;";
		String sql = "SELECT coachid FROM coach ORDER BY coachid DESC limit 1";

		System.out.println(sql);
		con = a.connectDB();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next()) {

			System.out.println(rs.getString("coachid"));
			id = rs.getInt("coachid");

		} else
			System.out.println("Not Found .. 1");

		System.out.println(id + " Coach ID\n\n");
		coachId = id;

		con.close();

		return -1;

	}

	private void ExecuteStatement(String sql) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		} finally {
			con.close();

		}

	}

	public void clear() {
		phoneText.clear();
		FirstNameText.clear();
		LastNameText.clear();
		EmailText.clear();
		TraningExp.clear();
		maleRB.setSelected(false);
		FemaleRB.setSelected(false);

	}

	@FXML
	void handelAddToExerSc(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

//		insertAtCoactInformation();
//		insertPhone();
		clear();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/AddCoachToSchaduale.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	public boolean isEmpty() {

		String fname = FirstNameText.getText().trim();
		String lname = LastNameText.getText().trim();
		String gender = (maleRB.isSelected() == true) ? "Male" : "Female";
		String trainingExperiences = TraningExp.getText().trim();
		String email = EmailText.getText().trim();
		String phone = phoneText.getText().trim();
		if (fname.isEmpty() && lname.isEmpty() && gender.isEmpty() && trainingExperiences.isEmpty() && email.isEmpty()
				&& phone.isEmpty()) {

		}

		return false;
	}

	@FXML
	void handelBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void handleClear(ActionEvent event) {
		FirstNameText.clear();
		LastNameText.clear();
		maleRB.setSelected(false);
		FemaleRB.setSelected(false);
		EmailText.clear();
		phoneText.clear();
		TraningExp.clear();

	}
	

}