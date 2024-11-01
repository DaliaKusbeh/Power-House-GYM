package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import sample.Coach;
import sample.DBConn;
import sample.DBInitializing;

// Controllers.CoachTableView
public class CoachTableView implements Initializable {

	@FXML
	private ComboBox<String> sortBy;

	@FXML
	private TextField filterField;
	@FXML
	private Button updataButton;
	@FXML
	private Button serachButton;
	@FXML
	private Button addButton;
	@FXML
	private Button coachTableButton;
	@FXML
	private Button DeleteButton;
	@FXML
	private AnchorPane paneAnchorPane;
	@FXML
	private TextField coachIdTextField;
	@FXML
	private TableColumn<Coach, String> FnameCol;

	@FXML
	private TableColumn<Coach, String> LnameCol;

	@FXML
	private TableColumn<Coach, Integer> coachIdCol;

	@FXML
	private TableColumn<Coach, String> emailCol;

	@FXML
	private TableColumn<Coach, String> genderCol;

	@FXML
	private TableColumn<Coach, Integer> scheduleidCol;

	@FXML
	private TableColumn<Coach, String> trainingExperiencesCol;
	@FXML
	protected TableView<Coach> MStableView;
	@FXML
	private Button showDataButton;
	private ArrayList<Coach> data;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField genderTextField;

	@FXML
	private TextField lNameTextField;

	@FXML
	private TextField schedualeIDTextField;

	@FXML
	private TextField traningExperincessTextField;

	private ObservableList<Coach> dataList = FXCollections.observableArrayList();

	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
//    Connection connection = null ;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Coach coach = null;

	public void tableViewOfCoach(ActionEvent event) throws IOException {
		System.out.println("Click In TableView Of Coach");

		if (event.getSource() == coachTableButton) {
//			Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXML/CoachestableView.fxml"));
//			final Stage AboutUsStage = new Stage();
//			AboutUsStage.initModality(Modality.NONE);
//			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
//			AboutUsStage.initOwner(app_stage);
//			System.out.println("YAY\n\n");
//			System.out.println("WTF");
//			Scene scene11 = new Scene(nextSceneParent);
//			AboutUsStage.setScene(scene11);
//			AboutUsStage.show();
			Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/CoachestableView.fxml"));
			paneAnchorPane.getChildren().removeAll();
			paneAnchorPane.getChildren().setAll(fxml);

//				e.printStackTrace();

		}
	}

//		MStableView.setEditable(true);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();
			MStableView.setEditable(true);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		editTable();
		// DBInitializing DB = new DBInitializing(); // connecting to database
//		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
//		try {
//			Connection con = a.connectDB();
//			rs = con.createStatement().executeQuery("select * from coach");
//			while (rs.next())
//				dataList.add(new Coach(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
//						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
////
//
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Connection closed : " + data.size());
//
//		}
//		coachIdCol.setCellValueFactory(new PropertyValueFactory<>("coachId"));
//		scheduleidCol.setCellValueFactory(new PropertyValueFactory<>("scheduleid"));
//		FnameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
//		LnameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));
//		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
//		trainingExperiencesCol.setCellValueFactory(new PropertyValueFactory<>("trainingExperiences"));
//		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//		MStableView.setItems(dataList);
//		showTable();
//		System.out.println("Connection closed : " + data.size());

	}

	@FXML
	public void displayData(ActionEvent event) {
		MStableView.setEditable(true);

		if (event.getSource() == showDataButton) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			try {
				Connection con = a.connectDB();
				rs = con.createStatement().executeQuery("select * from coach");
				while (rs.next())
					dataList.add(new Coach(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
							rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();

			}
			showTable();
//			System.out.println("Connection closed : " + data.size());

		}
	}

	@FXML
	public void editTable() {
		System.out.println("1EDIT");
		coachIdCol.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		System.out.println("2EDIT");
		scheduleidCol.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
		scheduleidCol.setOnEditCommit((CellEditEvent<Coach, Integer> t) -> {
			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setScheduleId(t.getNewValue());
			updateScheduleId(t.getRowValue().getCoachId(), t.getNewValue());
		});

		System.out.println("3EDIT");
		FnameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		FnameCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFname(t.getNewValue());

			updateFname(t.getRowValue().getCoachId(), t.getNewValue());

		});
		System.out.println("4EDIT");
		LnameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));

		LnameCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLname(t.getNewValue());

			updateLname(t.getRowValue().getCoachId(), t.getNewValue());

		});

		System.out.println("5EDIT");

		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		genderCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGender(t.getNewValue());

			updateGender(t.getRowValue().getCoachId(), t.getNewValue());
		});
		System.out.println("6EDIT");

		trainingExperiencesCol.setCellValueFactory(new PropertyValueFactory<>("trainingExperiences"));
		System.out.println("7");
		trainingExperiencesCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setTrainingExperiences(t.getNewValue());

			updateTrainingExperiences(t.getRowValue().getCoachId(), t.getNewValue());
		});

		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		System.out.println("8");
		emailCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());

			updateEmail(t.getRowValue().getCoachId(), t.getNewValue());
		});

//		dataList = FXCollections.observableArrayList(data);
		System.out.println("7");
//		MStableView.setItems(dataList); // table name
//		MStableView.getColumns().addAll(coachIdCol, scheduleidCol, FnameCol, LnameCol, genderCol, trainingExperiencesCol, emailCol);

	}

	public void showTable() {
		MStableView.setEditable(true);

		System.out.println("1");
		coachIdCol.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		System.out.println("2");
		scheduleidCol.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
		scheduleidCol.setOnEditCommit((CellEditEvent<Coach, Integer> t) -> {
			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setScheduleId(t.getNewValue());
			updateScheduleId(t.getRowValue().getCoachId(), t.getNewValue());
		});

		System.out.println("3");
		FnameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		FnameCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFname(t.getNewValue());

			updateFname(t.getRowValue().getCoachId(), t.getNewValue());

		});
		System.out.println("4");
		LnameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));

		LnameCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLname(t.getNewValue());

			updateLname(t.getRowValue().getCoachId(), t.getNewValue());

		});

		System.out.println("5");

		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		genderCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGender(t.getNewValue());

			updateGender(t.getRowValue().getCoachId(), t.getNewValue());

		});
		System.out.println("6");

		trainingExperiencesCol.setCellValueFactory(new PropertyValueFactory<>("trainingExperiences"));
		System.out.println("7");
		trainingExperiencesCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setTrainingExperiences(t.getNewValue());

			updateTrainingExperiences(t.getRowValue().getCoachId(), t.getNewValue());
		});

		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		System.out.println("8");
		emailCol.setOnEditCommit((CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());

			updateEmail(t.getRowValue().getCoachId(), t.getNewValue());
		});

//		dataList = FXCollections.observableArrayList(data);
		System.out.println("7");
		MStableView.setItems(dataList); // table name
//		MStableView.getColumns().addAll(coachIdCol, scheduleidCol, FnameCol, LnameCol, genderCol, trainingExperiencesCol, emailCol);

	}

	@FXML
	public void insertData(ActionEvent event) {
		if (event.getSource() == addButton) {
			int id = Integer.valueOf(coachIdTextField.getText().trim());
			int scID = Integer.valueOf(schedualeIDTextField.getText().trim());
			String fname = fnameTextField.getText().trim();
			String lname = lNameTextField.getText().trim();
			String gender = genderTextField.getText().trim();
			String time = traningExperincessTextField.getText().trim();
			String email = emailTextField.getText().trim();
			Coach rc = new Coach(id, scID, fname, lname, gender, time, email);
//					
//					fnameTF.getText(), 
//					Integer.valueOf(lnameTF.getText()),
//					genderTF.getText(), 
//					emailTF.getText());
//public Coach(int coach_id, int schedule_id, String fname, String lname, String gender, String trainingExperiences,
//				String email) {
			dataList.add(rc);
			insertData(rc);
			coachIdTextField.clear();
			schedualeIDTextField.clear();
			fnameTextField.clear();
			lNameTextField.clear();
			genderTextField.clear();
			traningExperincessTextField.clear();
			emailTextField.clear();

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

	private void insertData(Coach rc) {

//		public Coach(int coach_id, int schedule_id, String fname, String lname, String gender, String trainingExperiences,
//				String email) {
		try {
			System.out.println(
					"Insert into coach (coach_id, schedule_id,Fname, Lname, gender,trainingExperiences,email) values("
							+ rc.getCoachId() + "," + rc.getScheduleId() + ",'" + rc.getFname() + "','" + rc.getLname()
							+ "' , '" + rc.getGender() + "', '" + rc.getTrainingExperiences() + "','" + rc.getEmail()
							+ "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();

//			insert into coach (coach_id,schedule_id,Fname,Lname,gender,trainingExperiences,email)
			ExecuteStatement(
					"Insert into coach (coachid, scheduleid,Fname, Lname, gender,trainingExperiences,email) values("
							+ rc.getCoachId() + "," + rc.getScheduleId() + ",'" + rc.getFname() + "','" + rc.getLname()
							+ "' , '" + rc.getGender() + "', '" + rc.getTrainingExperiences() + "','" + rc.getEmail()
							+ "');");

			con.close();
//			System.out.println("Connection closed : " + data.size());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void deleteData(ActionEvent event) {
		if (event.getSource() == DeleteButton) {
			ObservableList<Coach> selectedRows = MStableView.getSelectionModel().getSelectedItems();
			ArrayList<Coach> rows = new ArrayList<>(selectedRows);
			rows.forEach(row -> {
				MStableView.getItems().remove(row);
				deleteRow(row);
				MStableView.refresh();
			});

		}

	}

	private void deleteRow(Coach row) {

		try {
			System.out.println("delete from  Coach where Coach_Id =" + row.getCoachId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  Coach where CoachId =" + row.getCoachId() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
//	insert into coach (coachId,scheduleId,Fname,Lname,gender,trainingExperiences,email) 

	public void updateScheduleId(int coachId, int scheduleId) {

		try {
			System.out.println("update  coach set scheduleId = '" + scheduleId + "' where coachId = " + coachId);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("update  coach set scheduleId = '" + scheduleId + "' where coachId = " + coachId + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateFname(int coachId, String fname) {

		try {
			System.out.println("update coach set fname = " + fname + " where coachId = " + coachId);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("update coach set fname = " + fname + " where coachId = " + coachId + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateLname(int coachId, String Lname) {

		try {
			System.out.println("update  coach set Lname = " + Lname + " where coachId = " + coachId);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("update  coach set Lname = " + Lname + " where coachId = " + coachId + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateGender(int coachId, String gender) {

		try {
			System.out.println("update  coach set gender = " + gender + " where coachId = " + coachId);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("update  coach set gender = " + gender + " where coachId = " + coachId + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateTrainingExperiences(int coachId, String trainingExperiences) {

		try {
			System.out.println(
					"update  coach set trainingExperiences = " + trainingExperiences + " where coachId = " + coachId);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("update  coach set trainingExperiences = " + trainingExperiences + " where coachId = "
					+ coachId + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateEmail(int coachId, String email) {

		try {
			System.out.println("update  coach set email = " + email + " where coachId = " + coachId);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("update  coach set email = " + email + " where coachId = " + coachId + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void refreshTable() {
		dataList.clear();
//		DBInitializing DB = new DBInitializing(); // connecting to database
//		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		try {
//			Connection con = a.connectDB();
			rs = con.createStatement().executeQuery("select * from coach");
			while (rs.next())
				dataList.add(new Coach(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MStableView.setItems(dataList);
	}

	private void loadDate() throws ClassNotFoundException, SQLException {

		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();

		coachIdCol.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		scheduleidCol.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
		FnameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		LnameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		trainingExperiencesCol.setCellValueFactory(new PropertyValueFactory<>("trainingExperiences"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		MStableView.setItems(dataList);

		// add cell of button edit
//		editCol.setCellFactory(cellFoctory);
//		studentsTable.setItems(StudentList);

	}

	@FXML
	public void search(ActionEvent event) {
		System.out.println("search");
		if (event.getSource() == serachButton) {

			FilteredList<Coach> filteredData = new FilteredList<>(dataList, b -> true);

			// 2. Set the filter Predicate whenever the filter changes.
			filterField.textProperty().addListener((observable, oldValue, newValue) -> {
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
			sortedData.comparatorProperty().bind(MStableView.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			MStableView.setItems(sortedData);
		}
	}

	@FXML
	public void updateValue() {
		coachIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		scheduleidCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		scheduleidCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coach, Integer>>() {

			@Override
			public void handle(CellEditEvent<Coach, Integer> event) {
//				Coach coach = MStableView.getSelectionModel().getSelectedItem();
//				coach.setFname(event.getNewValue().toString());
//
//			}
				((Coach) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setScheduleId(event.getNewValue());
			}

		});
		FnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		FnameCol.setOnEditCommit((TableColumn.CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFname(t.getNewValue());

		});

		LnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		LnameCol.setOnEditCommit((TableColumn.CellEditEvent<Coach, String> t) -> {

			((Coach) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLname(t.getNewValue());

		});
		genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
		trainingExperiencesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	@FXML
	void handleSortedCol(ActionEvent event) {

	}

}
