package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import sample.*;

public class GroupTraineeController implements Initializable {

	@FXML
	private TableView<GroupTrainee> ATableView;

	@FXML
	private TableColumn<GroupTrainee, Integer> traineeId;

	@FXML
	private TableColumn<GroupTrainee, String> Fname;

	@FXML
	private TableColumn<GroupTrainee, String> Lname;

	@FXML
	private TextField totalNumberOfTraineeText;

	@FXML
	private Button searchButton;

	@FXML
	private Button totalNumberOfTrainee;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> scheduleNameComBox;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;

	@FXML
	private TextField nameOfCoach;

	private ObservableList<GroupTrainee> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	// ClassOfPaymentInfo trainee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			nameOfCoach.setEditable(false);
			loadDate();
			fillScheduleNameComBox();
			sortComBox.getItems().addAll("traineeId", "Fname", "Lname");
//			scheduleNameComBox.getItems().addAll("Spinning", "Tep Aerobics", "Zumba Toning", "Body Shaping",
//					"Functional Training & TRX", "Cardio &Abs", "Yoga for Diabetes");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillScheduleNameComBox() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql = "SELECT scheduleName FROM schedulee;";
		ResultSet rs = con.createStatement().executeQuery(sql);
		ObservableList<String> list = FXCollections.observableArrayList();
		while (rs.next())
			list.add(rs.getString(1));

		scheduleNameComBox.setItems(list);

	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();

		traineeId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
		Fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		Lname.setCellValueFactory(new PropertyValueFactory<>("Lname"));

	}

	private void refreshTable() {
		dataList.clear();
		String schedualeNameText = scheduleNameComBox.getSelectionModel().getSelectedItem();
		try {

			String sql = " select trainee.traineeId ,trainee.Fname , trainee.Lname "
					+ " from trainee , play , schedulee " + " where trainee.traineeId = play.traineeId and "
					+ " schedulee.scheduleId = play.scheduleId and " + " schedulee.scheduleName = " + schedualeNameText
					+ ";";

			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			while (rs.next())
				dataList.add(new GroupTrainee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ATableView.setItems(dataList);

	}

	@FXML
	void handelSearch(ActionEvent event) {
		String schedualeNameText = scheduleNameComBox.getSelectionModel().getSelectedItem();
		String SelectSort = sortComBox.getSelectionModel().getSelectedItem();
		WrongSearchLabel.setVisible(false);
		System.out.println("Inside Search ");
		if (event.getSource() == searchButton) {
			String fName = firstNameTextField.getText().trim();
			String lName = lastNameTextField.getText().trim();
			System.out.println(fName + " " + lName);

			if (fName.isEmpty() && lName.isEmpty()) {
				System.out.print("Can Not Search");
				WrongSearchLabel.setVisible(true);
			} else {
				dataList.clear();
				try {

					if (!WrongSearchLabel.isVisible()) {
						String sql = "select t.traineeId ,t.Fname , t.Lname "
								+ "	from trainee t , play p  , schedulee s where t.traineeId = p.traineeId and "
								+ "	s.scheduleId = p.scheduleId and  s.scheduleName = '" + schedualeNameText
								+ "' and fname like '%" + fName + "%' and lname like '%" + lName + "%'  order by "
								+ SelectSort + ";";
						System.out.println(sql);
						rs = con.createStatement().executeQuery(sql);
						while (rs.next()) {
							GroupTrainee gt = new GroupTrainee();
							gt.setTraineeId(rs.getInt(1));
							gt.setFname(rs.getString(2));
							gt.setLname(rs.getString(3));
							dataList.add(gt);

						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ATableView.setItems(dataList);
			}
		}

	}

	@FXML
	void handelSort(ActionEvent event) {
		String schedualeNameText = scheduleNameComBox.getSelectionModel().getSelectedItem();
		String selectionSort = sortComBox.getSelectionModel().getSelectedItem();
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

				String sql = "  select t.traineeId ,t.Fname , t.Lname from trainee t , play p  , schedulee s where t.traineeId = p.traineeId and s.scheduleId = p.scheduleId and  s.scheduleName = '"
						+ schedualeNameText + "' order by " + selectionSort + ";";

				System.out.println(sql);

				rs = con.createStatement().executeQuery(sql);
				while (rs.next()) {
					GroupTrainee gt = new GroupTrainee();
					gt.setTraineeId(rs.getInt(1));
					gt.setFname(rs.getString(2));
					gt.setLname(rs.getString(3));
					dataList.add(gt);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

	@FXML
	void getTotalNumberTrainee(ActionEvent event) throws SQLException {
		// dataList.clear();
		String schedualeNameText = scheduleNameComBox.getSelectionModel().getSelectedItem();

		String sql = " select count(*) " + " from trainee , play , schedulee "
				+ " where trainee.traineeId = play.traineeId and " + " schedulee.scheduleId = play.scheduleId and "
				+ " schedulee.scheduleName = '" + schedualeNameText + "';";
		System.out.print(sql);
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(sql);
			while (rs.next())
				count = rs.getInt(1);
			System.out.println(count);
			totalNumberOfTraineeText.setText(String.format("%d", (count)));
		} catch (SQLException e) {
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
	void handelDisplay(ActionEvent event) throws ClassNotFoundException, SQLException {
		dataList.clear();

		String schedualeNameText = scheduleNameComBox.getSelectionModel().getSelectedItem();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql = "select t.traineeId ,t.Fname , t.Lname "
				+ "	from trainee  t, play p, schedulee s where t.traineeId = p.traineeId and s.scheduleId = p.scheduleId and  s.scheduleName =  '"
				+ schedualeNameText + "' ;";
		System.out.println(sql);
		ResultSet rs = con.createStatement().executeQuery(sql);
		while (rs.next()) {
			GroupTrainee gt = new GroupTrainee();

			gt.setTraineeId(rs.getInt(1));
			gt.setFname(rs.getString(2));
			gt.setLname(rs.getString(3));
			dataList.add(gt);

		}
		ATableView.setItems(dataList);

		// nameOfCoach
		String sql1 = "select concat(c.fname ,\" \", c.lname )as coachName " + "from coach c ,schedulee s "
				+ "where c.coachid = s.coachid and s.scheduleName = '" + schedualeNameText + "' ;";
		System.out.println(sql1);
		ResultSet rs1 = con.createStatement().executeQuery(sql1);
		if (rs1.next())
			nameOfCoach.setText(rs1.getString(1));

	}
}
