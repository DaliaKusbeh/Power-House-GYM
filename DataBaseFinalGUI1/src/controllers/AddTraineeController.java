package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import sample.Trainee;
import Utilties.*;

public class AddTraineeController implements Initializable {

	@FXML
	private TextField EmailTextField;

	@FXML
	private RadioButton EmpRB;

	@FXML
	private RadioButton FemaleRB;

	@FXML
	private TextField FirstNameTextField;

	@FXML
	private RadioButton GoldRB;

	@FXML
	private TextField LastNameTextField;

	@FXML
	private RadioButton SilverRB;

	@FXML
	private Label WEmail;

	@FXML
	private Label WFirstName;

	@FXML
	private Label WGender;

	@FXML
	private Label WLastName;

	@FXML
	private Label WSelectCoach;

	@FXML
	private Label WphoneNum;

	@FXML
	private Label WselectEx;

	@FXML
	private Button addButton;

	@FXML
	private ComboBox<String> coachCoBox;

	@FXML
	private ToggleGroup genderToggleGroup;

	@FXML
	private RadioButton maleRB;

	@FXML
	private ToggleGroup memberShipToggleGroup;

	@FXML
	private TextField paidText;

	@FXML
	private Label WpaidLabel;

	@FXML
	private Label WMemberShip;

	@FXML
	private TextField phoneTextField;

	@FXML
	private ComboBox<String> selectSch1;

	@FXML
	private ComboBox<String> selectSch2;

	@FXML
	private RadioButton studentRB;

	Connection con;
	String fn, ln;
	private int traineeId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());

		try {
			fillCobBox();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void fillCobBox() throws SQLException, ClassNotFoundException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		String sql = "select  concat( Fname,\" \",lname)as Coachname from coach;";
		con = a.connectDB();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ObservableList<String> item = FXCollections.observableArrayList(); // List of String
		while (rs.next())
			item.add(rs.getString("Coachname"));
		coachCoBox.setItems(item);

		String sql1 = "select scheduleName from schedulee ;";
		Statement stmt1 = con.createStatement();
		ResultSet rs1 = stmt1.executeQuery(sql1);
		ObservableList<String> item1 = FXCollections.observableArrayList(); // List of String
		while (rs1.next())
			item1.add(rs1.getString("scheduleName"));
		selectSch1.setItems(item1);
		selectSch2.setItems(item1);

	}

	@FXML
	void handelNewTrainee(ActionEvent event) throws ClassNotFoundException, SQLException {
		WEmail.setVisible(false);
		WFirstName.setVisible(false);
		WGender.setVisible(false);
		WLastName.setVisible(false);
		WSelectCoach.setVisible(false);
		WphoneNum.setVisible(false);
		WselectEx.setVisible(false);
		String fname = "", lname = "", email = "", phone = "", paid = "";

//			traineeId = Integer.valueOf(traineeIDTextField.getText().trim());
		fname = FirstNameTextField.getText().trim();
		lname = LastNameTextField.getText().trim();
		email = EmailTextField.getText().trim();
		phone = phoneTextField.getText().trim();
		paid = paidText.getText().trim();

		if (fname.isEmpty())
			WFirstName.setVisible(true);
		if (lname.isEmpty())
			WLastName.setVisible(true);
		if (email.isEmpty())
			WEmail.setVisible(true);
		if (phone.isEmpty())
			WphoneNum.setVisible(true);
		if (paid.isEmpty())
			WpaidLabel.setVisible(true);
		if (!maleRB.isSelected() && !FemaleRB.isSelected())
			WGender.setVisible(true);
		if (!studentRB.isSelected() && !EmpRB.isSelected() && !GoldRB.isSelected() && !SilverRB.isSelected())
			WMemberShip.setVisible(true);
		if (coachCoBox.getSelectionModel().getSelectedItem() == null)
			WSelectCoach.setVisible(true);
		if (selectSch1.getSelectionModel().getSelectedItem() == null)
			WselectEx.setVisible(true);

		if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty() || paid.isEmpty()
				|| !maleRB.isSelected() || !FemaleRB.isSelected() || !studentRB.isSelected() || !EmpRB.isSelected()
				|| !GoldRB.isSelected() || !SilverRB.isSelected()
				|| coachCoBox.getSelectionModel().getSelectedItem() == null
				|| selectSch1.getSelectionModel().getSelectedItem() == null)
			;

		if (WEmail.isDisable() || WFirstName.isDisable() || WLastName.isDisable() || WGender.isDisable()
				|| WMemberShip.isDisable() || WpaidLabel.isDisable() || WphoneNum.isDisable()
				|| WSelectCoach.isDisable() || WselectEx.isDisable())
			Massege.displayMassage("Field must Be Fill", "All Field Must be Fill Please");
		else {
			System.out.println("Doest Not Shown the Massege");
			insertTraineInformation();
			insertToExerciseSchedule();
			insertToMemberShip();
			clear();

		}

	}

	private void insertToMemberShip() throws ClassNotFoundException, SQLException {
		System.out.println("inside insertToMemberShip  To INSERT IN trainee Join Payment ===><><><><>================");
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		String sql;

		con = a.connectDB();
		int membershipId, amountRequired, amountPaid, remainingAmount;
		String paidDate;
		int amountPaid2;
		String paid = paidText.getText();
		System.out.println(paid + " yahooooooooooooooo");
		try {
			amountPaid2 = Integer.valueOf(paidText.getText());
		} catch (Exception e) {

			System.out.println(" Ammount Paid Not Correct " + e);
		}

		if (traineeId != -1) {
			String MSType = (EmpRB.isSelected()) ? "Employee"
					: (SilverRB.isSelected()) ? "Silver membership"
							: (studentRB.isSelected()) ? "Student membership"
									: (GoldRB.isSelected()) ? "gold membership" : "Not";
			System.out.println("Inside first Block for if Statment");
			System.out.println(MSType + " IS Selected ");
			System.out.println("Employee Is Selected ?");
			if (MSType.equalsIgnoreCase("Employee")) {
				membershipId = 2;
				amountRequired = 100;
				amountPaid = Integer.valueOf(paidText.getText());
				remainingAmount = amountRequired - amountPaid;
				paidDate = String.valueOf(java.time.LocalDate.now());

				con = a.connectDB();
				System.out.println(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value "
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' );");

				ExecuteStatement(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value"
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "') ;");

			} else if (MSType.equalsIgnoreCase("Silver membership")) {
				membershipId = 4;
				amountRequired = 250;
				amountPaid = Integer.valueOf(paidText.getText());
				remainingAmount = amountRequired - amountPaid;
				paidDate = String.valueOf(java.time.LocalDate.now());

				con = a.connectDB();
				System.out.println(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value "
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' );");

				ExecuteStatement(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value"
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' );");
			} else if (MSType.equalsIgnoreCase("Student membership")) {
				membershipId = 1;
				amountRequired = 50;
				amountPaid = Integer.valueOf(paidText.getText());
				remainingAmount = amountRequired - amountPaid;
				paidDate = String.valueOf(java.time.LocalDate.now());
//				insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate)

				con = a.connectDB();
				System.out.println(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value "
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' );");

				ExecuteStatement(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value"
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' );");

			} else if (MSType.equalsIgnoreCase("gold membership")) {
				membershipId = 3;
				amountRequired = 900;
				amountPaid = Integer.valueOf(paidText.getText());
				remainingAmount = amountRequired - amountPaid;
				paidDate = String.valueOf(java.time.LocalDate.now());

				con = a.connectDB();
				System.out.println("Heerererererer");
				System.out.println(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value "
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' );");

				ExecuteStatement(
						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value"
								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '"
								+ +amountPaid + "','" + remainingAmount + "', '" + paidDate + "' ) ;");
//				ExecuteStatement(
//						"insert into traineeJoinPayment (traineeId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) value"
//								+ " (" + traineeId + " , " + membershipId + " ,'" + amountRequired + "' , '" + paidDate
//								+ "' ; '" + amountPaid + "','" + remainingAmount + "',");

			} else {
				System.out.println("No Button is selected ");
			}
		} else {
			System.out.println("Trainee id Not Exsists");
		}

//		} catch (Exception e) {
//			System.out.println(e + " Erorr ");
//		}

	}

	private void insertToExerciseSchedule() throws ClassNotFoundException, SQLException {
		System.out.println("Inside insertToExerciseSchedule ");

//		insert into play(traineeId ,scheduleId) values (1,3);
//		int traineeId = getTraineeId();
		String nameOfSchedule1 = selectSch1.getSelectionModel().getSelectedItem();
		String nameOfSchedule2 = selectSch2.getSelectionModel().getSelectedItem();

		int scheduleId1 = getScheduleId(nameOfSchedule1);
		int scheduleId2 = getScheduleId(nameOfSchedule2);

		if (scheduleId1 != -1) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
//			insert into play(traineeId ,scheduleId) values (1,3);
			System.out.println();
			System.out.println(
					"insert into play (traineeId,scheduleId) values( " + traineeId + " , " + scheduleId1 + " );");

			ExecuteStatement(
					"insert into play (traineeId,scheduleId) values( " + traineeId + " , " + scheduleId1 + " );");

		}
		if (scheduleId2 != -1) {
			DBInitializing DB1 = new DBInitializing(); // connecting to database
			DBConn aa = new DBConn(DB1.getURL(), DB1.getPort(), DB1.getDbName(), DB1.getDbUsername(),
					DB1.getDbPassword());
			con = aa.connectDB();
//			insert into play(traineeId ,scheduleId) values (1,3);
			System.out.println();
			System.out.println(
					"insert into play (traineeId,scheduleId) values( " + traineeId + " , " + scheduleId2 + " );");

			ExecuteStatement(
					"insert into play (traineeId,scheduleId) values( " + traineeId + " , " + scheduleId2 + " );");

		}

	}

	private int getScheduleId(String nameOfSchedule) throws ClassNotFoundException, SQLException {
		DBInitializing DB1 = new DBInitializing(); // connecting to database
		DBConn aa = new DBConn(DB1.getURL(), DB1.getPort(), DB1.getDbName(), DB1.getDbUsername(), DB1.getDbPassword());
		con = aa.connectDB();

		String sql = "select scheduleId from schedulee where scheduleName ='" + nameOfSchedule + "' ;";

		ResultSet rs = con.createStatement().executeQuery(sql);
		int scId = -1;
		if (rs.next())
			scId = rs.getInt(1);
		System.out.println(" Name Of Schedule " + nameOfSchedule + " Id :" + scId);

		return scId;
	}

	private void insertTraineInformation() throws ClassNotFoundException, SQLException {
		System.out.println("Inside insertTraineInformation ");
		try {
			String fname = FirstNameTextField.getText().trim();
			String lname = LastNameTextField.getText().trim();
			String email = EmailTextField.getText().trim();
			String gender = (maleRB.isSelected() == true ? "Male" : "Female");
			int coachId = getCoachId();
			int phone = Integer.valueOf(phoneTextField.getText().trim());
			fn = fname;
			ln = lname;
			LocalDate date = java.time.LocalDate.now();

			System.out.println();
//			trainee (traineeId,coachId,Fname,Lname,gender,email
//			insert into trainee (coachId,Fname,Lname,gender,email)
//			insert into trainee (coachId,Fname,Lname,gender,email, startDate)
//			values
			String sql = " insert into trainee (coachId,Fname,Lname,gender,email, startDate)	values " + "(" + coachId
					+ ",'" + fname + "','" + lname + "' , '" + gender + "','" + email + "' , '" + date + "' );";
			System.out.println(sql);
//			System.out.println(" insert into trainee (coachId,Fname,Lname,gender,email, startDate)	values " + "("
//					+ coachId + ",'" + fname + "','" + lname + "' , '" + gender + "','" + email + "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement(sql);
//			ExecuteStatement("Insert into trainee (coachid,Fname, Lname, gender,email) values(" + +coachId + ",'"
//					+ fname + "','" + lname + "' , '" + gender + "','" + email + "');");

			con.close();

			getTraineeId();

			System.out.println("Trainee Id =========> " + traineeId);
			if (traineeId != -1) {
				System.out.println("insert into phoneNumberTrainee (traineeId,phoneNumber) values (" + traineeId + " , "
						+ phone + " );");
				ExecuteStatement("insert into phoneNumberTrainee (traineeId,phoneNumber) values (" + traineeId + " , "
						+ phone + " );");
			} else {
				System.out.println("Not Add phoneNumberTrainee becaues Trainee Id is :" + traineeId);
			}
//			clear();
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

	}

	private int getTraineeId() throws ClassNotFoundException, SQLException {
		System.out.println("INSID Get Traineee Id");
		int id = -1;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
//		String sql = "select coachId from coach where fname like '%" + fn + "%' ;";
//		String fn = FirstNameTextField.getText().trim();
//		String ln = LastNameTextField.getText().trim();
		System.out.println(fn + " " + ln + "The Name ");

		String sql = "select traineeId from trainee where fname = '" + fn + "' and lname = '" + ln + "' ;";
		System.out.println(sql);
		con = a.connectDB();
		Statement stmt = con.createStatement();
//		ResultSet rs = stmt.executeQuery(sql);
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next())
			id = rs.getInt(1);

		System.out.println("========================");
		System.out.println("========================");
		System.out.println("========================");
		System.out.println(id + " Trainee ID\n\n");
		System.out.println("========================");
		System.out.println("========================");
		System.out.println("========================");
		traineeId = id;
		return id;
	}

	private int getCoachId() throws ClassNotFoundException, SQLException {
		System.out.println("Inside Get Coach Id");
		String select = coachCoBox.getSelectionModel().getSelectedItem();
		String[] tkn = select.split(" ");
		String fn = tkn[0];
		String ln = tkn[1];
		System.out.println(fn + " " + ln);
		int id = 0;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
//		String sql = "select coachId from coach where fname like '%" + fn + "%' ;";
		String sql = "select coachId from coach where fname = '" + fn + "' and lname = '" + ln + "' ;";
		con = a.connectDB();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next())
//			id = rs.getInt(getCoachId());
			id = rs.getInt(1);

		System.out.println(id + " Coach Id");
		return id;
	}

	public void ExecuteStatement(String SQL) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}

	}

	private void clear() {
		FirstNameTextField.clear();
		LastNameTextField.clear();
		maleRB.setSelected(false);
		FemaleRB.setSelected(false);
		EmailTextField.clear();
		phoneTextField.clear();
		coachCoBox.getSelectionModel().clearSelection();
		selectSch1.getSelectionModel().clearSelection();
		selectSch2.getSelectionModel().clearSelection();
		studentRB.setSelected(false);
		SilverRB.setSelected(false);
		GoldRB.setSelected(false);
		EmpRB.setSelected(false);
		paidText.clear();

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
	void HandelpaymentAmount(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/PayemntInformationOnly.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void handleClear(ActionEvent event) {
//		FirstNameTextField.clear();
//		LastNameTextField.clear();
//		maleRB.setSelected(false);
//		FemaleRB.setSelected(false);
//		EmailTextField.clear();
//		phoneTextField.clear();
//		coachCoBox.getSelectionModel().clearSelection();
//		selectSch1.getSelectionModel().clearSelection();
//		selectSch2.getSelectionModel().clearSelection();
//		studentRB.setSelected(false);
//		SilverRB.setSelected(false);
//		GoldRB.setSelected(false);
//		EmpRB.setSelected(false);
//		paidText.clear();
		clear();

	}
}