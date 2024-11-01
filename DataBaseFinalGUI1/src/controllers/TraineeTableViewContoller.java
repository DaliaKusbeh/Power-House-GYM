package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.naming.Binding;

import sample.Coach;
import sample.DBConn;
import sample.DBInitializing;
import sample.Trainee;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TraineeTableViewContoller implements Initializable {

	@FXML
	private Button searchButton;
	@FXML
	private TextField serachTextField;

	@FXML
	private TableColumn<Trainee, String> FnameCol;
	@FXML
	private TableColumn<Trainee, String> LnameCol;
	@FXML
	private TableColumn<Trainee, Integer> coachIdCol;
	@FXML
	private TableColumn<Trainee, String> emailCol;
	@FXML
	private TableColumn<Trainee, String> genderCol;
	@FXML
	private TableColumn<Trainee, Integer> traineeIdCol;
	@FXML
	private TableView<Trainee> TTableView;
	@FXML
	private Button addButton;
	@FXML
	private Button clearButton;
	@FXML
	private TextField coachIdTextField;
	@FXML
	private Button deleteButton;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField fnameTextField;
	@FXML
	private TextField genderTextField;
	@FXML
	private TextField lnameTextField;
	@FXML
	private TextField traineeTextField;
	@FXML
	private Button updateButton;
	
	
	

	private ObservableList<Trainee> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	Trainee trainee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();
			deleteButton.disableProperty().bind(Bindings.isEmpty(TTableView.getSelectionModel().getSelectedItems()));
			updateButton.disableProperty().bind(Bindings.isEmpty(TTableView.getSelectionModel().getSelectedItems()));

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TTableView.setEditable(true);
	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		Trainee trainee = getTrainee();
		if (trainee != null) {
			dataList.add(trainee);
			updateFname();
			updateLname();
			updateEmail();
			updateGender();

		}

	}

	private boolean updateGender() throws ClassNotFoundException, SQLException {
		String newGender = genderTextField.getText().trim();
		if (newGender.equalsIgnoreCase("Female") || newGender.equalsIgnoreCase("Male")
				|| newGender.equalsIgnoreCase("Other")) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "update powerhouse_gym.trainee set gender = '" + newGender + "' where traineeId = "
					+ (traineeTextField.getText()) + ";"; // this
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
		String sql = "update powerhouse_gym.trainee set email = '" + newEmail + "' where traineeId = "
				+ (traineeTextField.getText()) + ";"; // this
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
		String sql = "update powerhouse_gym.trainee set Lname = '" + newName + "' where traineeId = "
				+ (traineeTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateFname() throws ClassNotFoundException, SQLException {
		String newName = fnameTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.trainee set Fname = '" + newName + "' where traineeId = "
				+ (traineeTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	@FXML
	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		traineeIdCol.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
		coachIdCol.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		FnameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		LnameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

	}

	@FXML
	private void refreshTable() {
		dataList.clear();
		try {
			rs = con.createStatement().executeQuery("select * from trainee");
			while (rs.next())
				dataList.add(new Trainee(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		TTableView.setItems(dataList);
	}

	@FXML
	void handleAdd(ActionEvent event) {
		Trainee tra = getTrainee();
		if (tra != null) {
			dataList.add(tra);
			insertData(tra);

		}

	}

	private Trainee getTrainee() {
		Trainee tra = null;
		try {
			int trId = Integer.valueOf(traineeTextField.getText().trim());
			int coachId = Integer.valueOf(coachIdTextField.getText().trim());
			String fname = fnameTextField.getText().trim();
			String lname = lnameTextField.getText().trim();
			String gender = genderTextField.getText().trim();
			String email = emailTextField.getText();
			tra = new Trainee(trId, coachId, fname, lname, gender, email);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return tra;
	}

	private void insertData(Trainee rc) {
		try {
			System.out
					.println("Insert into trainee (traineeId,coach_id, schedule_id,Fname, Lname, gender,email) values("
							+ rc.getTraineeId() + "," + rc.getCoachId() + ",'" + rc.getFname() + "','" + rc.getLname()
							+ "' , '" + rc.getGender() + "','" + rc.getEmail() + "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("Insert into trainee (traineeId,coachid,Fname, Lname, gender,email) values("
					+ rc.getTraineeId() + "," + rc.getCoachId() + ",'" + rc.getFname() + "','" + rc.getLname() + "' , '"
					+ rc.getGender() + "','" + rc.getEmail() + "');");

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
		traineeTextField.clear();
		coachIdTextField.clear();
		emailTextField.clear();
		lnameTextField.clear();
		genderTextField.clear();
		fnameTextField.clear();
	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<Trainee> selectedRows = TTableView.getSelectionModel().getSelectedItems();
		ArrayList<Trainee> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			TTableView.getItems().remove(row);
			deleteRow(row);
			TTableView.refresh();
		});

	}

	private void deleteRow(Trainee row) {
		try {
			System.out.println("delete from trainee  where traineeId =" + row.getTraineeId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  trainee where traineeId =" + row.getTraineeId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			System.out.println((e.getMessage()));
		} catch (ClassNotFoundException e) {
			System.out.println((e.getMessage()));
		}
	}

	@FXML
	void handleUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {
		System.out.println("Hndleee");
		updateGender();
		updateEmail();
		updateFname();
		updateLname();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {

		Trainee traninee = TTableView.getSelectionModel().getSelectedItem();
		if (traninee != null) {

			traineeTextField.setText(String.format("%d", traninee.getTraineeId()));
			coachIdTextField.setText(String.format("%d", traninee.getCoachId()));
			fnameTextField.setText(traninee.getFname());
			lnameTextField.setText(traninee.getLname());
			genderTextField.setText(traninee.getGender());
			emailTextField.setText(traninee.getEmail());
		}

	}

	@FXML
	void search(ActionEvent event) {
		if (event.getSource() == searchButton) {
			FilteredList<Trainee> filteredData = new FilteredList<>(dataList, b -> true);
			serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(trainee -> {
					// If filter text is empty, display all persons.

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					// Compare first name and last name of every person with filter text.
					String lowerCaseFilter = newValue.toLowerCase();
					if (String.valueOf(trainee.getTraineeId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (trainee.getFname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (trainee.getLname().toLowerCase().indexOf(lowerCaseFilter) != -1)
						return true;
					else
						return false;

				});
			});

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Trainee> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(TTableView.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			TTableView.setItems(sortedData);

		}

	}

}
