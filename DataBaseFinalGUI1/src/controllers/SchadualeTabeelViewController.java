package controllers;

import java.io.IOException;

import sample.DBConn;
import sample.DBInitializing;
import sample.Schaduale;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.crypto.AEADBadTagException;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Schaduale;

public class SchadualeTabeelViewController implements Initializable {

	@FXML
	private TableView<Schaduale> TableUser;

	@FXML
	private TableColumn<Schaduale, String> datee;

	@FXML
	private TableColumn<Schaduale, String> traineeId;

	@FXML
	private TableColumn<Schaduale, String> endTime;

	@FXML
	private TableColumn<Schaduale, Integer> scheduleId;

	@FXML
	private TableColumn<Schaduale, String> scheduleName;

	@FXML
	private TableColumn<Schaduale, String> startTime;

	@FXML
	private Button InsertButton;

	@FXML
	private Button UpdateButton;

	@FXML
	private Button SearchButton;

	@FXML
	private Button DeleteButton;

	@FXML
	private Button ExitButton;

	@FXML
	private TextField scheduleNameTextField;

	@FXML
	private TextField scheduleIdTextField;

	@FXML
	private TextField traineeIdTextField;

	@FXML
	private TextField DateTextField;

	@FXML
	private TextField startTimeTextField;

	@FXML
	private TextField endTimeTextField;
	@FXML
	private TextField serachTextField;

	private ObservableList<Schaduale> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	Schaduale schedualee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();
			DeleteButton.disableProperty().bind(Bindings.isEmpty(TableUser.getSelectionModel().getSelectedItems()));
			UpdateButton.disableProperty().bind(Bindings.isEmpty(TableUser.getSelectionModel().getSelectedItems()));

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableUser.setEditable(true);
	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		Schaduale sch = getSchaduale();
		if (sch != null) {
			dataList.add(sch);
			updateName();
			updateDate();
			updateStartTime();
			updateEndTime();

		}

	}

	private void updateName() throws SQLException, ClassNotFoundException {
		String newName = scheduleNameTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Name");

		String sql = "update powerhouse_gym.schedulee set scheduleName = '" + newName + "' where scheduleId = "
				+ (scheduleIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updateDate() throws ClassNotFoundException, SQLException {
		String newDate = DateTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Date");

		String sql = "update powerhouse_gym.schedulee set datee = ' " + newDate + "' where scheduleId = "
				+ (scheduleIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateStartTime() throws ClassNotFoundException, SQLException {
		String newsTime = startTimeTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Start Time");

		String sql = "update powerhouse_gym.schedulee set startTime = '" + newsTime + "' where scheduleId = "
				+ (scheduleIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateEndTime() throws ClassNotFoundException, SQLException {
		String neweTime = endTimeTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside End Time");
		String sql = "update powerhouse_gym.schedulee set endTime = '" + neweTime + "' where scheduleId = "
				+ (scheduleIdTextField.getText()) + ";"; // this
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
		scheduleName.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
		scheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
		traineeId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
		datee.setCellValueFactory(new PropertyValueFactory<>("datee"));
		startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

	}

	@FXML
	private void refreshTable() {
		dataList.clear();
		try {
			rs = con.createStatement().executeQuery("select * from schedulee");
			try {
				while (rs.next()) {
					dataList.add(new Schaduale(rs.getString(1), Integer.parseInt(rs.getString(2)),
							Integer.parseInt(rs.getString(3)), rs.getString(4), rs.getString(5), rs.getString(6)));
					
				}
			} catch (NumberFormatException e) {
				System.out.println("Num Format EXP " + e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		TableUser.setItems(dataList);
	}

	@FXML
	void handleAdd(ActionEvent event) {
		Schaduale tra = getSchaduale();
		if (tra != null) {
			dataList.add(tra);
			insertData(tra);

		}

	}

	private Schaduale getSchaduale() {
		Schaduale s = null;
		try {
			String sname = scheduleNameTextField.getText().trim();
			int schedualeeId = Integer.valueOf(scheduleIdTextField.getText().trim());
			int traineeId = Integer.valueOf(traineeIdTextField.getText().trim());
			String sdate = DateTextField.getText().trim();
			String stime = startTimeTextField.getText().trim();
			String etime = endTimeTextField.getText();
			s = new Schaduale(sname, schedualeeId, traineeId, sdate, stime, etime);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return s;
	}

	private void insertData(Schaduale rc) {
		try {
			System.out.println("Insert into schedulee (scheduleName,scheduleId,datee,startTime,endTime) values" + "('"
					+ rc.getScheduleName() + "'," + rc.getScheduleId() + rc.getTraineeId() + ",'" + rc.getDatee()
					+ "','" + rc.getStartTime() + "','" + rc.getEndTime() + "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("Insert into schedulee (scheduleName,scheduleId,datee,startTime,endTime) values" + "('"
					+ rc.getScheduleName() + "'," + rc.getScheduleId() + rc.getTraineeId() + ",'" + rc.getDatee()
					+ "','" + rc.getStartTime() + "','" + rc.getEndTime() + "');");

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
		scheduleNameTextField.clear();
		scheduleIdTextField.clear();
		traineeIdTextField.clear();
		DateTextField.clear();
		startTimeTextField.clear();
		endTimeTextField.clear();
	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<Schaduale> selectedRows = TableUser.getSelectionModel().getSelectedItems();
		ArrayList<Schaduale> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			TableUser.getItems().remove(row);
			deleteRow(row);
			TableUser.refresh();
		});

	}

	private void deleteRow(Schaduale row) {
		try {
			System.out.println("delete from schedulee  where ScheduleId =" + row.getScheduleId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  schedulee where ScheduleId =" + row.getScheduleId() + ";");
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
		updateName();
		updateDate();
		updateStartTime();
		updateEndTime();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {
		Schaduale traninee = TableUser.getSelectionModel().getSelectedItem();

		scheduleNameTextField.setText(traninee.getScheduleName());
		scheduleIdTextField.setText(String.format("%d", traninee.getScheduleId()));
		traineeIdTextField.setText(String.format("%d", traninee.getTraineeId()));
		DateTextField.setText(traninee.getDatee());
		startTimeTextField.setText(traninee.getStartTime());
		endTimeTextField.setText(traninee.getEndTime());

	}

	@FXML
	void search(ActionEvent event) {
		if (event.getSource() == SearchButton) {
			FilteredList<Schaduale> filteredData = new FilteredList<>(dataList, b -> true);
			serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(trainee -> {
					// If filter text is empty, display all persons.

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					// Compare first name and last name of every person with filter text.
					String lowerCaseFilter = newValue.toLowerCase();
					if (String.valueOf(trainee.getScheduleId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(trainee.getTraineeId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (trainee.getScheduleName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else
						return false;

				});

			});

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Schaduale> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(TableUser.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			TableUser.setItems(sortedData);

		}

	}

}
