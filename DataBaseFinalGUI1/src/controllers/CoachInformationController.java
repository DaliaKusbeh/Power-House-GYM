package controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.*;

public class CoachInformationController implements Initializable {

	@FXML
	private TableView<CoachInformation> ATableView;

	@FXML
	private TableColumn<CoachInformation, String> Fname;

	@FXML
	private TableColumn<CoachInformation, String> Lname;

	@FXML
	private TableColumn<CoachInformation, String> gender;

	@FXML
	private TableColumn<CoachInformation, String> trainingExperiences;

	@FXML
	private TableColumn<CoachInformation, String> email;

	@FXML
	private TableColumn<CoachInformation, Integer> NoOfTrainees;

	@FXML
	private TableColumn<CoachInformation, Integer> coachId;

	@FXML
	private Button searchButton;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField totalNumberOfCoach;

	@FXML
	private Button totalNumberOfCoachButton;

	@FXML
	private TextField totalNumberOfTrainee;

	@FXML
	private Button totalNumberOfTraineeButton;

	@FXML
	private TextField coachIdTextField;

	@FXML
	private TextField fnameTextField;

//	@FXML
//	private TextField genderTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private TextField trainingExperiencesTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;

	@FXML
	private RadioButton FemaleRB;
	@FXML
	private RadioButton maleRB;
	@FXML
	private ToggleGroup genderTG;
	private ObservableList<CoachInformation> dataList = FXCollections.observableArrayList();
	private ObservableList<CoachClass> dataList1 = FXCollections.observableArrayList();
	private ObservableList<CoachInformation> dataList2 = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	ResultSet rs1;
	ResultSet rs2;
	PreparedStatement pst;
	String query = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();
			sortComBox.getItems().addAll("Fname", "Lname", "gender", "trainingExperiences", "email");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		coachId.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		Fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		Lname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		trainingExperiences.setCellValueFactory(new PropertyValueFactory<>("trainingExperiences"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		NoOfTrainees.setCellValueFactory(new PropertyValueFactory<>("NoOfTrainees"));

	}

	@FXML
	void getTotalNumberCoach(ActionEvent event) throws SQLException {
		// dataList.clear();

		if (event.getSource() == totalNumberOfCoachButton) {
			String query = "select count(*) as total from coach ;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfCoach.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void getTotalNumberTrainee(ActionEvent event) throws SQLException {
		// dataList.clear();

		if (event.getSource() == totalNumberOfTraineeButton) {
			String query = "select count(*) as sum from trainee ;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfTrainee.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void refreshTable() {
		dataList.clear();
		try {

			String sql = " select coach.coachId,coach.Fname,coach.Lname,coach.gender,coach.trainingExperiences,"
					+ " coach.email, count(trainee.traineeId)" + " from coach,trainee "
					+ " where coach.coachId = trainee.coachId" + " group by coach.coachId;";

			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			rs1 = con.createStatement().executeQuery("select * from coach");
			while (rs.next())
				dataList.addAll(
						new CoachInformation(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getString(5), rs.getString(6), Integer.parseInt(rs.getString(7))));

			while (rs1.next())
				dataList1.addAll(new CoachClass(Integer.parseInt(rs1.getString(1)), rs1.getString(2), rs1.getString(3),
						rs1.getString(4), rs1.getString(5), rs1.getString(6)));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		ATableView.setItems(dataList);
		// ATableView.setItems(dataList1);

	}

	@FXML
	void handelSearch(ActionEvent event) {
		WrongSearchLabel.setVisible(false);
		System.out.println("Inside Search ");
		if (event.getSource() == searchButton) {
			String fName = firstNameTextField.getText().trim();
			String lName = lastNameTextField.getText().trim();
			System.out.println(fName + " " + lName);

			if (fName.isEmpty() || lName.isEmpty()) {
				System.out.print("Can Not Search");
				WrongSearchLabel.setVisible(true);
			} else {
				dataList.clear();
				try {
					String sql = "select coach.coachId,coach.Fname,coach.Lname,coach.gender,coach.trainingExperiences,"
							+ " coach.email, count(trainee.traineeId) from coach,trainee "
							+ " where coach.coachId = trainee.coachId and  " + " coach.Fname like '%" + fName
							+ "%' and coach.Lname  like '%" + lName + "%'" + " group by coach.coachId ";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.add(new CoachInformation(Integer.parseInt(rs.getString(1)), rs.getString(2),
								rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
								Integer.parseInt(rs.getString(7))));
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ATableView.setItems(dataList);

			}
		}

	}

	@FXML
	void handelSort(ActionEvent event) {
		WrongSrotLabel.setVisible(false);
		System.out.println("Inside Sort ");
		if (event.getSource() == sortByButton) {

			String SortText = sortComBox.getSelectionModel().getSelectedItem();
			if (SortText == null) {
				WrongSrotLabel.setVisible(true);
				return;
			}
			System.out.println("sort Text " + SortText);
			if (SortText.isEmpty())
				System.out.println("Empty Field");
			dataList.clear();
			try {

				String sql = " select coach.coachId,coach.Fname,coach.Lname,coach.gender,coach.trainingExperiences,"
						+ " coach.email, count(trainee.traineeId)" + " from coach,trainee "
						+ " where coach.coachId = trainee.coachId" + " group by coach.coachId order by " + SortText
						+ " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.addAll(new CoachInformation(Integer.parseInt(rs.getString(1)), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
							Integer.parseInt(rs.getString(7))));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		CoachClass sch = getCoach();
		if (sch != null) {
			dataList1.add(sch);
			updateFname();
			updateLname();
			updateGender();
			updateEmail();
			updateTrainingExperiences();

		}

	}

	private void updateFname() throws ClassNotFoundException, SQLException {
		String newName = fnameTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.coach set Fname = '" + newName + "' where coachId = "
				+ (coachIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateLname() throws ClassNotFoundException, SQLException {
		String newName = lnameTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.coach set Lname = '" + newName + "' where coachId = "
				+ (coachIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private boolean updateGender() throws ClassNotFoundException, SQLException {
		String newGender = FemaleRB.isSelected() ? "Female" : "Male";
		if (newGender.equalsIgnoreCase("Female") || newGender.equalsIgnoreCase("Male")
				|| newGender.equalsIgnoreCase("Other")) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "update powerhouse_gym.coach set gender = '" + newGender + "' where coachId = "
					+ (coachIdTextField.getText()) + ";"; // this
			System.out.println(sql);

			con = a.connectDB();
			stmt = con.createStatement();
			DB.ExecuteUpdateStatement(sql);
			stmt.close();
			con.close();
			return true;
		} else {
			(new Alert(Alert.AlertType.ERROR, "Wrong Gender")).show();
			return false;
		}
	}

	private void updateEmail() throws SQLException, ClassNotFoundException {
		String newEmail = emailTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.coach set email = '" + newEmail + "' where coachId = "
				+ (coachIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updateTrainingExperiences() throws ClassNotFoundException, SQLException {
		String neweTime = trainingExperiencesTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside End Time");
		String sql = "update powerhouse_gym.coach set trainingExperiences = '" + neweTime + "' where coachId = "
				+ (coachIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

//	@FXML
//	private void refreshTable() {
//		dataList.clear();
//		try {
//			rs = con.createStatement().executeQuery("select * from schedulee");
//			while (rs.next())
//				dataList.add(new Schaduale(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
//						rs.getString(4), rs.getString(5), rs.getString(6)));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		TableUser.setItems(dataList);
//	}

	private CoachClass getCoach() {
		CoachClass s = null;
		try {

			int coachId = Integer.valueOf(coachIdTextField.getText().trim());
			String fname = fnameTextField.getText().trim();
			String lname = lnameTextField.getText().trim();
			String gender = FemaleRB.isSelected() ? "Female" : "Male";
			String exp = trainingExperiencesTextField.getText();
			String email = emailTextField.getText();

			s = new CoachClass(coachId, fname, lname, gender, exp, email);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return s;
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

	@FXML
	void handleClear(ActionEvent event) {
		clear();
	}

	public void clear() {
		coachIdTextField.clear();
		fnameTextField.clear();
		lnameTextField.clear();
//		genderTextField.clear();
		trainingExperiencesTextField.clear();
		emailTextField.clear();
		totalNumberOfCoach.clear();
		totalNumberOfTrainee.clear();
		FemaleRB.setSelected(false);
		maleRB.setSelected(false);
	}

//	@FXML
//	void handleDelete(ActionEvent event) {
//		ObservableList<CoachInformation> selectedRows = ATableView.getSelectionModel().getSelectedItems();
//		ArrayList<CoachInformation> rows = new ArrayList<>(selectedRows);
//		rows.forEach(row -> {
//			ATableView.getItems().remove(row);
//			deleteRow(row);
//			ATableView.refresh();
//		});
//
//	}
//
//	private void deleteRow(CoachInformation row) {
//		try {
//			System.out.println("delete from  coach where coachId =" + row.getCoachId() + ";");
//			DBInitializing DB = new DBInitializing(); // connecting to database
//			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
//			con = a.connectDB();
//			ExecuteStatement("delete from  coach where coachId =" + row.getCoachId() + ";");
//			con.close();
//			System.out.println("Connection closed");
//
//		} catch (SQLException e) {
//			System.out.println((e.getMessage()));
//		} catch (ClassNotFoundException e) {
//			System.out.println((e.getMessage()));
//		}
//	}

	@FXML
	void handleUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {
		System.out.println("Hndleee");
		updateFname();
		updateLname();
		updateGender();
		updateTrainingExperiences();
		updateEmail();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {
		CoachInformation traninee = ATableView.getSelectionModel().getSelectedItem();
		if (traninee != null) {
			coachIdTextField.setText(String.format("%d", traninee.getCoachId()));
			fnameTextField.setText(traninee.getFname());
			lnameTextField.setText(traninee.getLname());
//		genderTextField.setText(traninee.getGender());
			trainingExperiencesTextField.setText(traninee.getTrainingExperiences());
			emailTextField.setText(traninee.getEmail());
			FemaleRB.setSelected((traninee.getGender().equalsIgnoreCase("Female")));
			maleRB.setSelected(traninee.getGender().equalsIgnoreCase("Male"));
		}
		System.out.println("No Data In this Row");

	}

	@FXML
	void handelRefersh(ActionEvent event) {
		refreshTable();
	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<CoachInformation> selectedRows = ATableView.getSelectionModel().getSelectedItems();
		ArrayList<CoachInformation> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			ATableView.getItems().remove(row);
			deleteRow(row);
			ATableView.refresh();
		});

	}

	private void deleteRow(CoachInformation row) {
		try {
			System.out.println("delete from  coach where coachId =" + row.getCoachId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  coach where coachId =" + row.getCoachId() + ";");
//			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			System.out.println((e.getMessage()));
		} catch (ClassNotFoundException e) {
			System.out.println((e.getMessage()));
		}
	}

	@FXML
	void handelContactInformation(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/CoachInformationView.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

}
