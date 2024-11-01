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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.*;

public class ClassOfPaymentInfoController implements Initializable {

	@FXML
	private TableView<ClassOfPaymentInfo> ATableView;

	@FXML
	private TableColumn<ClassOfPaymentInfo, Integer> traineeId;

	@FXML
	private TableColumn<ClassOfPaymentInfo, String> membershipType;

	@FXML
	private TableColumn<ClassOfPaymentInfo, String> amountRequired;

	@FXML
	private TableColumn<ClassOfPaymentInfo, String> amountPaid;

	@FXML
	private TableColumn<ClassOfPaymentInfo, String> remainingAmount;

	@FXML
	private TableColumn<ClassOfPaymentInfo, String> paidDate;

	@FXML
	private Button searchButton;

	@FXML
	private TextField studentTextField;
	@FXML
	private TextField employeeTextField;
	@FXML
	private TextField silverTextField;
	@FXML
	private TextField goldTextField;
	@FXML
	private TextField totalTextField;

	@FXML
	private TextField reqTextField;

	@FXML
	private TextField paidTextField;
	@FXML
	private TextField remainTextField;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField traineeIdTextField;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> typeComBox;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;

	private ObservableList<ClassOfPaymentInfo> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	// ClassOfPaymentInfo trainee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			sortComBox.getItems().addAll("membershipType", "amountRequired", "amountPaid", "remainingAmount",
					"paidDate");
//			fillComboBox();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		ATableView.setEditable(true);
	}

//	private void fillComboBox() {
//		String colName[] = { "Fname", "Lname", "membershipType", "amountRequired", "amountPaid", "remainingAmount",
//				"paidDate" };
//
//		sortComBox = new ComboBox<>(FXCollections.observableArrayList(colName));
//	}

	@FXML
	void getTotal(ActionEvent event) throws SQLException, ClassNotFoundException {
		ResultSet rs;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String query = "select count(*)  from traineeJoinPayment;";
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next())
				count = rs.getInt(1);
			System.out.println(count);
			totalTextField.setText(String.format("%d", (count)));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

	}

	@FXML
	void getTotalStudent(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		ResultSet rs;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		// if (event.getSource() == totalNumberOfSchedualeeButton) {
		String query = "select count(*) as totalStudent from traineeJoinPayment where membershipId = \"1\"";
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next())
				count = rs.getInt(1);
			System.out.println(count);
			studentTextField.setText(String.format("%d", (count)));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		// }

	}

	@FXML
	void getTotalEmployee(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		ResultSet rs;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		// if (event.getSource() == totalNumberOfSchedualeeButton) {
		String query = "select count(*) as totalStudent from traineeJoinPayment where membershipId = \"2\"";
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next())
				count = rs.getInt(1);
			System.out.println(count);
			employeeTextField.setText(String.format("%d", (count)));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		// }

	}

	@FXML
	void getTotalSilver(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		ResultSet rs;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		// if (event.getSource() == totalNumberOfSchedualeeButton) {
		String query = "select count(*) as totalStudent from traineeJoinPayment where membershipId = \"3\"";
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next())
				count = rs.getInt(1);
			System.out.println(count);
			silverTextField.setText(String.format("%d", (count)));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		// }

	}

	@FXML
	void getTotalGold(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		ResultSet rs;
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String query = "select count(*) as totalStudent from traineeJoinPayment where membershipId = \"4\";";
		int count = 0;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next())
				count = rs.getInt(1);
			System.out.println(count);
			goldTextField.setText(String.format("%d", (count)));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		traineeId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
		membershipType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
		amountRequired.setCellValueFactory(new PropertyValueFactory<>("amountRequired"));
		amountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
		remainingAmount.setCellValueFactory(new PropertyValueFactory<>("remainingAmount"));
		paidDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));

	}

	private void refreshTable() {
		dataList.clear();
		try {

			String sql = "select trainee.traineeId,membership.membershipType,"
					+ " traineeJoinPayment.amountRequired,traineeJoinPayment.amountPaid,traineeJoinPayment.remainingAmount, traineeJoinPayment.paidDate"
					+ " from trainee,traineeJoinPayment,membership"
					+ " where trainee.traineeId = traineeJoinPayment.traineeId and "
					+ " membership.membershipId = traineeJoinPayment.membershipId ;";

			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			while (rs.next())
				dataList.addAll(new ClassOfPaymentInfo(((rs.getInt(1))), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));

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
			} else {
				dataList.clear();
				try {

					String sql = "select trainee.traineeId ,membership.membershipType,traineeJoinPayment.amountRequired,"
							+ "traineeJoinPayment.amountPaid,traineeJoinPayment.remainingAmount, traineeJoinPayment.paidDate"
							+ " from trainee,traineeJoinPayment,membership"
							+ " where trainee.traineeId = traineeJoinPayment.traineeId and membership.membershipId = traineeJoinPayment.membershipId "
							+ " and membershipType like '%" + fName + "%' ;";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.addAll(new ClassOfPaymentInfo((Integer.parseInt(rs.getString(1))), rs.getString(2),
								rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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

				String sql = "select trainee.traineeId,membership.membershipType,traineeJoinPayment.amountRequired,"
						+ " traineeJoinPayment.amountPaid,traineeJoinPayment.remainingAmount, traineeJoinPayment.paidDate  "
						+ " from trainee,traineeJoinPayment,membership"
						+ " where trainee.traineeId = traineeJoinPayment.traineeId and "
						+ " membership.membershipId = traineeJoinPayment.membershipId order by " + SortText + " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.addAll(new ClassOfPaymentInfo((Integer.parseInt(rs.getString(1))), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		ClassOfPaymentInfo sch = getCoach();
		if (sch != null) {
			dataList.add(sch);
			updateType();
			updateRequaier();
			updatePaid();
			updateRemain();
			updateDate();

		}

	}

	private void updateType() throws SQLException, ClassNotFoundException {

		typeComBox.getItems().addAll("Student membership", "Employee membership", "Silver membership",
				"gold membership");
		String com = typeComBox.getPromptText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
//		ResultSet rs = null;
		System.out.println("Inside Name");
		String sql = "update powerhouse_gym.traineeJoinPayment set membershipType = '" + com + "' where membershipId = "
				+ (traineeIdTextField.getText()) + ";";
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updateRequaier() throws SQLException, ClassNotFoundException {
		String newName = reqTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
//		ResultSet rs = null;
		System.out.println("Inside Name");

		String sql = "update powerhouse_gym.traineeJoinPayment set amountRequired = '" + newName
				+ "' where membershipId = " + (traineeIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updatePaid() throws ClassNotFoundException, SQLException {
		String newDate = paidTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
//		ResultSet rs = null;
		System.out.println("Inside Date");

		String sql = "update powerhouse_gym.traineeJoinPayment set amountPaid = ' " + newDate
				+ "' where membershipId = " + (traineeIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateRemain() throws ClassNotFoundException, SQLException {
		String newsTime = remainTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
//		ResultSet rs = null;
		System.out.println("Inside Start Time");

		String sql = "update powerhouse_gym.traineeJoinPayment set remainingAmount = '" + newsTime
				+ "' where membershipId = " + (traineeIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateDate() throws ClassNotFoundException, SQLException {
		String neweTime = dateTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
//		ResultSet rs = null;
		System.out.println("Inside End Time");
		String sql = "update powerhouse_gym.traineeJoinPayment set paidDate = '" + neweTime + "' where membershipId = "
				+ (traineeIdTextField.getText()) + ";"; // this
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

	private ClassOfPaymentInfo getCoach() {
		ClassOfPaymentInfo s = null;
		try {

			int coachId = Integer.valueOf(traineeIdTextField.getText().trim());
			String fname = typeComBox.getPromptText().trim();
			String lname = reqTextField.getText().trim();
			String gender = paidTextField.getText().trim();
			String exp = remainTextField.getText();
			String email = dateTextField.getText();

			s = new ClassOfPaymentInfo(coachId, fname, lname, gender, exp, email);

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
		traineeIdTextField.clear();
		reqTextField.clear();
		remainTextField.clear();
		paidTextField.clear();
		dateTextField.clear();
	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<ClassOfPaymentInfo> selectedRows = ATableView.getSelectionModel().getSelectedItems();
		ArrayList<ClassOfPaymentInfo> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			ATableView.getItems().remove(row);
			deleteRow(row);
			ATableView.refresh();
		});

	}

	private void deleteRow(ClassOfPaymentInfo row) {
		try {
			System.out.println("delete from  traineeJoinPayment where traineeId =" + row.getTraineeId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  traineeJoinPayment where traineeId =" + row.getTraineeId() + ";");
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
		updateType();
		updateRequaier();
		updatePaid();
		updateRemain();
		updateDate();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {
		ClassOfPaymentInfo traninee = ATableView.getSelectionModel().getSelectedItem();

		traineeIdTextField.setText(String.format("%d", traninee.getTraineeId()));
		typeComBox.setPromptText(traninee.getMembershipType());
		reqTextField.setText(traninee.getAmountRequired());
		paidTextField.setText(traninee.getAmountPaid());
		remainTextField.setText(traninee.getRemainingAmount());
		dateTextField.setText(traninee.getPaidDate());

	}

	@FXML
	void handelRefersh(ActionEvent event) {
		refreshTable();

	}
}
