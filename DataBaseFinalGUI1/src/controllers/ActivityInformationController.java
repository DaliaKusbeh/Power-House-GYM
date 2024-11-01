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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import sample.*;

public class ActivityInformationController implements Initializable {

	@FXML
	private TableView<ActivityInformation> ATableView;

	@FXML
	private TableColumn<ActivityInformation, String> scheduleName;

	@FXML
	private TableColumn<ActivityInformation, String> activity;

	@FXML
	private TableColumn<ActivityInformation, Integer> schedualeId;

	@FXML
	private Button searchButton;

	@FXML
	private TextField searchTextField;

	@FXML
	private TextField idTextField;

	@FXML
	private TextField firstNameTextField;

//	@FXML
//	private TextField lastNameTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private Button totalNumberOfSchedualeeButton;

	@FXML
	private TextField totalNumberOfScheduale;

	@FXML
	private TextField name;

	@FXML
	private TextArea Activity;

	@FXML
	private ComboBox<String> sortComBox;

	@FXML
	private Label WrongSearchLabel;

	@FXML
	private Label WrongSrotLabel;

	private ObservableList<ActivityInformation> dataList = FXCollections.observableArrayList();
	private ObservableList<Schaduale> dataList1 = FXCollections.observableArrayList();
	private ObservableList<Activities> dataList2 = FXCollections.observableArrayList();

	Connection con;
	ResultSet rs;
	ResultSet rs1;
	ResultSet rs2;
	PreparedStatement pst;
	String query = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idTextField.setEditable(false);
		try {
			loadDate();
			sortComBox.getItems().addAll("scheduleName", "activity");
//			fillComboBox();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		ATableView.setEditable(true);
	}

	@FXML
	void getTotalNumberCoach(ActionEvent event) throws SQLException {
		// dataList.clear();

		if (event.getSource() == totalNumberOfSchedualeeButton) {
			String query = "select count(*) from schedulee;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfScheduale.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		schedualeId.setCellValueFactory(new PropertyValueFactory<>("schedualeId"));
		scheduleName.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
		activity.setCellValueFactory(new PropertyValueFactory<>("activity"));

	}

	private void refreshTable() {
		dataList.clear();
		try {

			String sql = " select schedulee.scheduleId,schedulee.scheduleName,ActivitySchedualee.activity "
					+ " from ActivitySchedualee , schedulee"
					+ " where ActivitySchedualee.scheduleId = schedulee.scheduleId;";
			rs = con.createStatement().executeQuery("select * from ActivitySchedualee");
			rs1 = con.createStatement().executeQuery("select * from schedulee");
			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			while (rs.next())
				dataList.add(
						new ActivityInformation((Integer.parseInt(rs.getString(1))), rs.getString(2), rs.getString(3)));

//			while (rs1.next())
//				dataList1.add(new Schaduale(rs.getString(1)));
//			
//			while (rs2.next())
//				dataList2.add(new Activities(rs.getString(1)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ATableView.setItems(dataList);

	}

	@FXML
	void handelSearch(ActionEvent event) {
		WrongSearchLabel.setVisible(false);
		System.out.println("Inside Search ");
		if (event.getSource() == searchButton) {
			String fName = firstNameTextField.getText().trim();
//			String lName = lastNameTextField.getText().trim();
			System.out.println(fName);

			if (fName.isEmpty()) {
				System.out.print("Can Not Search");
				WrongSearchLabel.setVisible(true);
//				refreshTable();
			} else {
				dataList.clear();
				try {

					String sql = "select schedulee.scheduleId,schedulee.scheduleName,ActivitySchedualee.activity from ActivitySchedualee , schedulee"
							+ " where ActivitySchedualee.scheduleId = schedulee.scheduleId"
							+ " and scheduleName like '%" + fName + "%' ;";

//					String sql = "select schedulee.scheduleId,schedulee.scheduleName,ActivitySchedualee.activity\r\n"
//							+ " from ActivitySchedualee , schedulee"
//							+ " where ActivitySchedualee.scheduleId = schedulee.scheduleId"
//							+ " and scheduleName like '%" + fName + "%' and" + " activity  like '%" + lName + "%' ;";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.add(new ActivityInformation((Integer.parseInt(rs.getString(1))), rs.getString(2),
								rs.getString(3)));
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

				String sql = " select schedulee.scheduleId,schedulee.scheduleName,ActivitySchedualee.activity "
						+ " from ActivitySchedualee , schedulee "
						+ " where ActivitySchedualee.scheduleId = schedulee.scheduleId order by " + SortText + " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.add(new ActivityInformation((Integer.parseInt(rs.getString(1))), rs.getString(2),
							rs.getString(3)));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		ActivityInformation sch = getName();

		if (sch != null) {
			dataList.add(sch);
			updateName();
			updateActivity();
		}

	}

	private void updateName() throws ClassNotFoundException, SQLException {
		String newName = name.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.schedulee set scheduleName = '" + newName + "' where scheduleId = "
				+ (idTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateActivity() throws ClassNotFoundException, SQLException {
		String newName = Activity.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.ActivitySchedualee set activity = '" + newName + "' where scheduleId = "
				+ (idTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private ActivityInformation getName() {
		ActivityInformation s = null;
		try {
			int id = Integer.valueOf(idTextField.getText().trim());
			String Name = name.getText().trim();
			String activity = Activity.getText().trim();

			s = new ActivityInformation(id, Name, activity);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}

//	private Schaduale getName() {
//		Schaduale s = null;
//		try {	
//			String Name = name.getText().trim();
//		
//			s = new Schaduale(Name);
//
//		} catch (NumberFormatException e) {
//			System.out.println(e.getMessage());
//		}
//		return s;
//	}
//	
//	
//	private Activities getActivity() {
//		Activities s = null;
//		try {	
//			String Name = Activity.getText().trim();
//		
//			s = new Activities(Name);
//
//		} catch (NumberFormatException e) {
//			System.out.println(e.getMessage());
//		}
//		return s;
//	}

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
		name.clear();
		Activity.clear();
		idTextField.clear();
		totalNumberOfScheduale.clear();
	}

	@FXML
	void handleUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {
		System.out.println("Hndleee");
		updateName();
		updateActivity();
		loadDate();
		clear();
	}

	@FXML
	void updateView(MouseEvent event) {
		ActivityInformation traninee = ATableView.getSelectionModel().getSelectedItem();
		if (traninee != null) {
			idTextField.setText(String.format("%d", traninee.getSchedualeId()));
			name.setText(traninee.getScheduleName());
			Activity.setText(traninee.getActivity());
		}

	}

	@FXML
	void handelRefersh(ActionEvent event) {
		refreshTable();

	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<ActivityInformation> selectedRows = ATableView.getSelectionModel().getSelectedItems();
		ArrayList<ActivityInformation> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			ATableView.getItems().remove(row);
			deleteRow(row);
			ATableView.refresh();
		});

	}

	private void deleteRow(ActivityInformation row) {
		try {
			System.out.println("delete from  ActivitySchedualee where scheduleId =" + row.getSchedualeId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  ActivitySchedualee where scheduleId =" + row.getSchedualeId() + ";");
			ExecuteStatement("delete from  schedulee where scheduleId =" + row.getSchedualeId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			System.out.println((e.getMessage()));
		} catch (ClassNotFoundException e) {
			System.out.println((e.getMessage()));
		}
	}
}
