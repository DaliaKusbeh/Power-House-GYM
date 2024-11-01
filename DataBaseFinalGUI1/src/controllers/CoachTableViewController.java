package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Coach;
import sample.DBConn;
import sample.DBInitializing;

public class CoachTableViewController implements Initializable {

	@FXML
	private TableColumn<Coach, String> FnameCol;

	@FXML
	private TableColumn<Coach, String> LnameCol;

	@FXML
	private TextField ScheduleIDTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private TableView<Coach> TTableView;

	@FXML
	private Button clearButton;

	@FXML
	private TableColumn<Coach, Integer> coachIdCol;

	@FXML
	private TextField coachIdTextField;

	@FXML
	private Button deleteButton;

	@FXML
	private TableColumn<Coach, String> emailCol;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TableColumn<Coach, String> genderCol;

	@FXML
	private TextField genderTextField;

	@FXML
	private Button insertButton;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TableColumn<Coach, Integer> scheduleIdCol;

	@FXML
	private TextField serachTextField;

	@FXML
	private TextField tExperiencesTextField;

	@FXML
	private TableColumn<Coach, String> trainingExperiencesCol;

	@FXML
	private Button updateButton;

	private ObservableList<Coach> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	Coach coach = null;

	@FXML
	void updateView(MouseEvent event) {
		Coach coach = TTableView.getSelectionModel().getSelectedItem();
		if (coach != null) {
			coachIdTextField.setText(String.format("%d", coach.getCoachId()));
			ScheduleIDTextField.setText(String.format("%d", coach.getScheduleId()));
			firstNameTextField.setText(coach.getFname());
			lastNameTextField.setText(coach.getLname());
			genderTextField.setText(coach.getGender());
			tExperiencesTextField.setText(coach.getTrainingExperiences());
			emailTextField.setText(coach.getEmail());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		deleteButton.disableProperty().bind(Bindings.isEmpty(TTableView.getSelectionModel().getSelectedItems()));
		updateButton.disableProperty().bind(Bindings.isEmpty(TTableView.getSelectionModel().getSelectedItems()));
		TTableView.setEditable(true);
	}

	private void updateValue() throws ClassNotFoundException, SQLException {
		Coach coach = getCoach();
		if (coach != null) {
			dataList.add(coach);
			updateFname();
			updateLname();
			updateEmail();
			updateGender();
			updateScheduleId();
			updateTrainingExperiences();

		}

	}

	private void updateTrainingExperiences() throws ClassNotFoundException, SQLException {
		String newTExperiences = tExperiencesTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.coach set trainingExperiences = '" + newTExperiences + "' where coachId = "
				+ (coachIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updateScheduleId() throws SQLException, ClassNotFoundException {
		String newSchedual = ScheduleIDTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.coach set scheduleId = '" + newSchedual + "' where coachId = "
				+ (coachIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updateGender() throws ClassNotFoundException, SQLException {
		String newGender = genderTextField.getText().trim();
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
		} else {
			(new Alert(Alert.AlertType.ERROR, "Wrong Gender")).show();
		}

	}

	private void updateEmail() throws ClassNotFoundException, SQLException {
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

	private void updateLname() throws ClassNotFoundException, SQLException {
		String newName = lastNameTextField.getText().trim();
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

	private void updateFname() throws ClassNotFoundException, SQLException {
		String newName = firstNameTextField.getText().trim();
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

	private Coach getCoach() {
		Coach coach = null;
		try {
			int coachId = Integer.valueOf(coachIdTextField.getText().trim());
			int scheduleID = Integer.valueOf(ScheduleIDTextField.getText().trim());
			String fname = firstNameTextField.getText().trim();
			String lname = lastNameTextField.getText().trim();
			String gender = genderTextField.getText().trim();
			String TExperiences = tExperiencesTextField.getText().trim();
			String email = emailTextField.getText();
			coach = new Coach(coachId, scheduleID, fname, lname, gender, TExperiences, email);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return coach;
	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();

		coachIdCol.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		scheduleIdCol.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
		FnameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		LnameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		trainingExperiencesCol.setCellValueFactory(new PropertyValueFactory<>("trainingExperiences"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

	}

	private void refreshTable() {
		dataList.clear();
		try {
			rs = con.createStatement().executeQuery("select * from coach");
			while (rs.next())
				dataList.add(new Coach(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		TTableView.setItems(dataList);

	}

	@FXML
	void search(ActionEvent event) {

		if (event.getSource() == SearchButton) {
			FilteredList<Coach> filteredData = new FilteredList<>(dataList, b -> true);
			serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(coach -> {
					// If filter text is empty, display all persons.

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					// Compare first name and last name of every person with filter text.
					String lowerCaseFilter = newValue.toLowerCase();
					if (String.valueOf(coach.getCoachId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (coach.getFname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (coach.getLname().toLowerCase().indexOf(lowerCaseFilter) != -1)
						return true;
					else
						return false;

				});
			});

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Coach> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(TTableView.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			TTableView.setItems(sortedData);

		}

	}

	@FXML
	public void clear(ActionEvent event) {
		if (event.getSource() == clearButton)
			clear();

	}

	private void clear() {
		tExperiencesTextField.clear();
		firstNameTextField.clear();
		lastNameTextField.clear();
		genderTextField.clear();
		emailTextField.clear();
		coachIdTextField.clear();
		ScheduleIDTextField.clear();

	}

	@FXML
	void handleAdd(ActionEvent event) {
		System.out.println("insude add");
		Coach coach = getCoach();
		if (coach != null) {
			dataList.add(coach);
			insertData(coach);

		}

	}

	private void insertData(Coach rc) {
		try {
			System.out.println(
					"Insert into coach (coach_id, schedule_id,Fname, Lname, gender,trainingExperiences,email) values("
							+ rc.getCoachId() + "," + rc.getScheduleId() + ",'" + rc.getFname() + "','" + rc.getLname()
							+ "' , '" + rc.getGender() + "', '" + rc.getTrainingExperiences() + "','" + rc.getEmail()
							+ "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();

			ExecuteStatement(
					"Insert into coach (coachid, scheduleid,Fname, Lname, gender,trainingExperiences,email) values("
							+ rc.getCoachId() + "," + rc.getScheduleId() + ",'" + rc.getFname() + "','" + rc.getLname()
							+ "' , '" + rc.getGender() + "', '" + rc.getTrainingExperiences() + "','" + rc.getEmail()
							+ "');");

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<Coach> selectedRows = TTableView.getSelectionModel().getSelectedItems();
		ArrayList<Coach> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			TTableView.getItems().remove(row);
			deleteRow(row);
			TTableView.refresh();
		});

	}

	private void deleteRow(Coach row) {
		try {
			System.out.println("delete from coach where coachId =" + row.getCoachId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from coach where coachId =" + row.getCoachId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			System.out.println((e.getMessage()));
		} catch (ClassNotFoundException e) {
			System.out.println((e.getMessage()));
		}

	}

	private void ExecuteStatement(String sql) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}

	}

	@FXML
	void handleUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {
		System.out.println("Hndleee");
		updateGender();
		updateEmail();
		updateScheduleId();
		updateFname();
		updateLname();
		updateTrainingExperiences();
		loadDate();
		clear();

	}

}
