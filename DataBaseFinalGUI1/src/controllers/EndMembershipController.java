package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.input.MouseEvent;
import sample.DBConn;
import sample.DBInitializing;
import sample.EndMembership;

public class EndMembershipController implements Initializable {

	@FXML
	private TableView<EndMembership> ATableView;

	@FXML
	private TableColumn<EndMembership, Integer> traineeId;

	@FXML
	private TableColumn<EndMembership, String> Fname;

	@FXML
	private TableColumn<EndMembership, String> Lname;

	@FXML
	private TableColumn<EndMembership, String> membershipType;

	@FXML
	private TableColumn<EndMembership, Date> startDate;

	@FXML
	private TableColumn<EndMembership, Date> endDate;

	@FXML
	private Button searchButton;
	@FXML
	private TextField startDateTextField;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField endDateTextField;

//
	@FXML
	private TextField traineeIdTestField;
//
//	@FXML
//	private Button totalNumberOfCoachButton;

	@FXML
	private TextField totalNumberOfTrainee;

	@FXML
	private Button totalNumberOfTraineeButton;

	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField membershipTypeTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;

	private ObservableList<EndMembership> dataList = FXCollections.observableArrayList();

	Connection con;
	ResultSet rs;

	PreparedStatement pst;
	String query = null;
	private int traineeid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			traineeIdTestField.setEditable(false);
			loadDate();
			// updateValue();
			sortComBox.getItems().addAll("traineeId", "Fname", "Lname", "membershipType", "startDate", "endDate");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		traineeId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
		Fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		Lname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		membershipType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
		startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

	}

	@FXML
	void getTotalNumberTrainee(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		if (event.getSource() == totalNumberOfTraineeButton) {
			String query = "select count(*) from traineeJoinPayment;;";
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

//			String sql = " select trainee.traineeId, trainee.Fname, trainee.Lname, membership.membershipType ,trainee.startDate "
//					+ "    (  CASE "
//					+ "        WHEN traineeJoinPayment.membershipId = 1 THEN DATE_ADD(startDate, INTERVAL 1 MONTH) "
//					+ "        WHEN traineeJoinPayment.membershipId = 2 THEN DATE_ADD(startDate, INTERVAL 3 MONTH) "
//					+ "        WHEN traineeJoinPayment.membershipId = 3 THEN DATE_ADD(startDate, INTERVAL 6 MONTH) "
//					+ "        WHEN traineeJoinPayment.membershipId = 4 THEN DATE_ADD(startDate, INTERVAL 1 YEAR) "
//					+ "        ELSE 1 " + "    END) AS endDate " + " from trainee , traineeJoinPayment, membership "
//					+ " where trainee.traineeId = traineeJoinPayment.traineeId and "
//					+ "       membership.membershipId = traineeJoinPayment.membershipId  ;";

			String sql = "select trainee.traineeId, trainee.Fname, trainee.Lname, membership.membershipType ,trainee.startDate,"
					+ "    (   CASE "
					+ "        WHEN traineeJoinPayment.membershipId = 1 THEN DATE_ADD(startDate, INTERVAL 1 MONTH) "
					+ "        WHEN traineeJoinPayment.membershipId = 2 THEN DATE_ADD(startDate, INTERVAL 3 MONTH) "
					+ "        WHEN traineeJoinPayment.membershipId = 3 THEN DATE_ADD(startDate, INTERVAL 6 MONTH) "
					+ "        WHEN traineeJoinPayment.membershipId = 4 THEN DATE_ADD(startDate, INTERVAL 1 YEAR) "
					+ "        ELSE 1 " + "    END) AS endDate "
					+ " from trainee , traineeJoinPayment, membership where trainee.traineeId = traineeJoinPayment.traineeId and   membership.membershipId = traineeJoinPayment.membershipId \r\n"
					+ "       order by Lname ;";

			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);

			while (rs.next())

				dataList.addAll(new EndMembership((rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDate(5), rs.getDate(6)));

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
					String sql = " select trainee.traineeId, trainee.Fname, trainee.Lname, membership.membershipType ,trainee.startDate, "
							+ "	   ( CASE "
							+ "	 WHEN traineeJoinPayment.membershipId = 1 THEN DATE_ADD(startDate, INTERVAL 1 MONTH) "
							+ "	 WHEN traineeJoinPayment.membershipId = 2 THEN DATE_ADD(startDate, INTERVAL 3 MONTH) "
							+ "	 WHEN traineeJoinPayment.membershipId = 3 THEN DATE_ADD(startDate, INTERVAL 6 MONTH) "
							+ "	 WHEN traineeJoinPayment.membershipId = 4 THEN DATE_ADD(startDate, INTERVAL 1 YEAR) "
							+ "	 ELSE 1   END) AS endDate 	 from trainee , traineeJoinPayment, membership "
							+ "	 where trainee.traineeId = traineeJoinPayment.traineeId and "
							+ "	 membership.membershipId = traineeJoinPayment.membershipId  "
							+ " and  trainee.Fname like '%" + fName + "%' and trainee.Lname  like '%" + lName + "%'"
							+ " ; ";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.addAll(new EndMembership((rs.getInt(1)), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getDate(5), rs.getDate(6)));

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

//				String sql = " select trainee.traineeId, trainee.Fname, trainee.Lname, membership.membershipType ,trainee.startDate, \"\r\n"
//						+ "	   ( "
//						+ "	 CASE "
//						+ "	 WHEN traineeJoinPayment.membershipId = 1 THEN DATE_ADD(startDate, INTERVAL 1 MONTH) "
//						+ "	 WHEN traineeJoinPayment.membershipId = 2 THEN DATE_ADD(startDate, INTERVAL 3 MONTH) "
//						+ "	 WHEN traineeJoinPayment.membershipId = 3 THEN DATE_ADD(startDate, INTERVAL 6 MONTH) "
//						+ "	 WHEN traineeJoinPayment.membershipId = 4 THEN DATE_ADD(startDate, INTERVAL 1 YEAR) "
//						+ "	 ELSE 1 "
//						+ "	 END) AS endDate "
//						+ "	 from trainee , traineeJoinPayment, membership "
//						+ "	 where trainee.traineeId = traineeJoinPayment.traineeId and "
//						+ "	 membership.membershipId = traineeJoinPayment.membershipId  "
//						+ "  order by " + SortText + " ;";
				String sql = "select trainee.traineeId, trainee.Fname, trainee.Lname, membership.membershipType ,trainee.startDate, "
						+ "    ( " + "    CASE "
						+ "        WHEN traineeJoinPayment.membershipId = 1 THEN DATE_ADD(startDate, INTERVAL 1 MONTH) "
						+ "        WHEN traineeJoinPayment.membershipId = 2 THEN DATE_ADD(startDate, INTERVAL 3 MONTH) "
						+ "        WHEN traineeJoinPayment.membershipId = 3 THEN DATE_ADD(startDate, INTERVAL 6 MONTH) "
						+ "        WHEN traineeJoinPayment.membershipId = 4 THEN DATE_ADD(startDate, INTERVAL 1 YEAR) "
						+ "        ELSE 1 " + "    END) AS endDate " + " from trainee , traineeJoinPayment, membership "
						+ "where trainee.traineeId = traineeJoinPayment.traineeId and "
						+ "       membership.membershipId = traineeJoinPayment.membershipId  " + "       order by "
						+ SortText + " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.addAll(new EndMembership((rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getDate(5), rs.getDate(6)));

			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

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
		traineeIdTestField.clear();
		fnameTextField.clear();
		lnameTextField.clear();
		membershipTypeTextField.clear();
		startDateTextField.clear();
		endDateTextField.clear();
	}

	@FXML
	void updateView(MouseEvent event) {
		EndMembership traninee = ATableView.getSelectionModel().getSelectedItem();
		if (traninee == null) {
			System.out.println("No Data In This Row !!");
		} else {
			traineeIdTestField.setText(traninee.getTraineeId() + "");
//		traineeIdTestField.setText(String.format("%d", traninee.getTraineeId()));
			fnameTextField.setText(traninee.getFname());
			lnameTextField.setText(traninee.getLname());
			membershipTypeTextField.setText(traninee.getMembershipType());
			startDateTextField.setText(traninee.getStartDate() + "");
			endDateTextField.setText(traninee.getEndDate() + "");
		}
//		startDateTextField.setUserData((traninee.getStartDate()));
//		endDateTextField.setUserData((traninee.getEndDate()));
//		

	}

}
