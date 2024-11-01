package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;

public class ClassOfScheduleController implements Initializable {

	@FXML
	private TableView<ClassOfSchedule> ATableView;

	@FXML
	private TableColumn<ClassOfSchedule, String> days;

	@FXML
	private TableColumn<ClassOfSchedule, String> stime;
	@FXML
	private TableColumn<ClassOfSchedule, String> etime;
	@FXML
	private TableColumn<ClassOfSchedule, String> Fname;

	@FXML
	private TableColumn<ClassOfSchedule, String> Lname;

	@FXML
	private TableColumn<ClassOfSchedule, String> scheduleName;

	@FXML
	private TableColumn<ClassOfSchedule, Integer> NoOfTrainee;

	@FXML
	private TextField totalNumberOfCoachText;
	@FXML
	private TextField totalNumberOfTraineeText;
	@FXML
	private TextField totalNumberOfSchedualeText;
	@FXML
	private Button searchButton;

	@FXML
	private Button totalNumberOfTrainee;

	@FXML
	private Button totalNumberOfCoach;

	@FXML
	private Button totalNumberOfScheduale;

//	@FXML
//	private TextField searchTextField;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;

	private ObservableList<ClassOfSchedule> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	// ClassOfPaymentInfo trainee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			sortComBox.getItems().addAll("Fname", "Lname", "scheduleName");
//			fillComboBox();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		ATableView.setEditable(true);
	}

	private void fillComboBox() {
		String colName[] = { "Fname", "Lname", "scheduleName" };

		sortComBox = new ComboBox<>(FXCollections.observableArrayList(colName));
	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		Fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		Lname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		scheduleName.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
		days.setCellValueFactory(new PropertyValueFactory<>("days"));
		stime.setCellValueFactory(new PropertyValueFactory<>("stime"));
		etime.setCellValueFactory(new PropertyValueFactory<>("etime"));
		NoOfTrainee.setCellValueFactory(new PropertyValueFactory<>("NoOfTrainee"));

	}

	private void refreshTable() {
		dataList.clear();
		try {
			/// change the day
			String sql = "select coach.Fname, coach.Lname ,scheduleName,theDay,startTime,endTime,count(trainee.traineeId)"
					+ " from trainee, schedulee , play ,coach " + " where trainee.traineeId = play.traineeId and "
					+ " play.scheduleId = schedulee.scheduleId and " + " schedulee.coachId = coach.coachId "
					+ " group by coach.coachId ;";

			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			while (rs.next())
				dataList.add(new ClassOfSchedule(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7)));
//				dataList.add(new Administrator(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
//						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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
			String lName = lastNameTextField.getText().trim();
			System.out.println(fName + " " + lName);

			if (fName.isEmpty() || lName.isEmpty()) {
				System.out.print("Can Not Search");
				WrongSearchLabel.setVisible(true);
			} else {
				dataList.clear();
				try {

//					String sql = "SELECT FNAME , LNAME , GENDER  , phoneNumber "
//							+ "from administrator a  , phoneNumberAdministrator p "
//							+ "where a.administratorId = p.administratorId and " + "fName = '" + fName
//							+ "' and lName = '" + lName + "' ;";
					String sql = "select coach.Fname, coach.Lname ,scheduleName,theDay,startTime,endTime,"
							+ " count(trainee.traineeId) " + " from trainee, schedulee , play ,coach "
							+ " where trainee.traineeId = play.traineeId and "
							+ " play.scheduleId = schedulee.scheduleId and " + " schedulee.coachId = coach.coachId "
							+ " and coach.Fname like  '%" + fName + "%'and  coach.Lname  like '%" + lName + "%'"
							+ " group by coach.coachId";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.add(
								new ClassOfSchedule(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
										rs.getString(5), rs.getString(6), (Integer.parseInt(rs.getString(7)))));
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

				String sql = "select coach.Fname, coach.Lname ,scheduleName,theDay,startTime,endTime,"
						+ " count(trainee.traineeId)" + " from trainee, schedulee , play ,coach "
						+ " where trainee.traineeId = play.traineeId and "
						+ " play.scheduleId = schedulee.scheduleId and " + " schedulee.coachId = coach.coachId"
						+ " group by coach.coachId order by " + SortText + " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.add(new ClassOfSchedule(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), (Integer.parseInt(rs.getString(7)))));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

	@FXML
	void getTotalNumberCoach(ActionEvent event) throws SQLException {
		// dataList.clear();

		if (event.getSource() == totalNumberOfCoach) {
			String query = "select count(*) as total from coach ;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfCoachText.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void getTotalNumberTrainee(ActionEvent event) throws SQLException {
		// dataList.clear();

		if (event.getSource() == totalNumberOfTrainee) {
			String query = "select count(*)  from trainee ;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfTraineeText.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void getTotalNumberSchedule(ActionEvent event) throws SQLException {
		// dataList.clear();

		if (event.getSource() == totalNumberOfScheduale) {
			String query = "select count(*)  from schedulee ;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfSchedualeText.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
