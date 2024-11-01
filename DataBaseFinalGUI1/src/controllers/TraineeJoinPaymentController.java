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
import sample.*;

public class TraineeJoinPaymentController implements Initializable {

	@FXML
	private TableView<TraineeJoinPayment> TableJoin;

	@FXML
	private TableColumn<TraineeJoinPayment, Integer> membershipId;

	@FXML
	private TableColumn<TraineeJoinPayment, Integer> paymentId;

	@FXML
	private TableColumn<TraineeJoinPayment, String> amountRequired;

	@FXML
	private TableColumn<TraineeJoinPayment, String> amountPaid;

	@FXML
	private TableColumn<TraineeJoinPayment, Integer> remainingAmount;

	@FXML
	private TableColumn<TraineeJoinPayment, Integer> paidDate;

	@FXML
	private Button DeleteButton;

	@FXML
	private Button InsertButton;

	@FXML
	private Button UpdateButton;

	@FXML
	private Button ExitButton;

	@FXML
	private Button searchButton;

	@FXML
	private TextField serachTextField;

	@FXML
	private TextField membershipIdTextField;

	@FXML
	private TextField paymentIdTextField;

	@FXML
	private TextField amountRequiredTextField;

	@FXML
	private TextField amountPaidTextField;

	@FXML
	private TextField remainingAmountTextField;

	@FXML
	private TextField paidDateTextField;

	private ObservableList<TraineeJoinPayment> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	TraineeJoinPayment join = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();
			DeleteButton.disableProperty().bind(Bindings.isEmpty(TableJoin.getSelectionModel().getSelectedItems()));
			UpdateButton.disableProperty().bind(Bindings.isEmpty(TableJoin.getSelectionModel().getSelectedItems()));

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableJoin.setEditable(true);
	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		TraineeJoinPayment sch = getJoin();
		if (sch != null) {
			dataList.add(sch);
			updateRequaier();
			updatePaid();
			updateRemain();
			updateDate();

		}

	}

	private void updateRequaier() throws SQLException, ClassNotFoundException {
		String newName = amountRequiredTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Name");

		String sql = "update powerhouse_gym.traineeJoinPayment set amountRequired = '" + newName
				+ "' where membershipId = " + (membershipIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();

	}

	private void updatePaid() throws ClassNotFoundException, SQLException {
		String newDate = amountPaidTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Date");

		String sql = "update powerhouse_gym.traineeJoinPayment set amountPaid = ' " + newDate
				+ "' where membershipId = " + (membershipIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateRemain() throws ClassNotFoundException, SQLException {
		String newsTime = remainingAmountTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Start Time");

		String sql = "update powerhouse_gym.traineeJoinPayment set remainingAmount = '" + newsTime
				+ "' where membershipId = " + (membershipIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private void updateDate() throws ClassNotFoundException, SQLException {
		String neweTime = paidDateTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside End Time");
		String sql = "update powerhouse_gym.traineeJoinPayment set paidDate = '" + neweTime + "' where membershipId = "
				+ (membershipIdTextField.getText()) + ";"; // this
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
		paymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
		membershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
		amountRequired.setCellValueFactory(new PropertyValueFactory<>("amountRequired"));
		amountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
		remainingAmount.setCellValueFactory(new PropertyValueFactory<>("remainingAmount"));
		paidDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));

	}

	@FXML
	private void refreshTable() {
		dataList.clear();
		try {
			rs = con.createStatement().executeQuery("select * from traineeJoinPayment");
			while (rs.next())
				dataList.add(
						new TraineeJoinPayment(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
								rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		TableJoin.setItems(dataList);
	}

	@FXML
	void handleAdd(ActionEvent event) {
		TraineeJoinPayment tra = getJoin();
		if (tra != null) {
			dataList.add(tra);
			insertData(tra);

		}

	}

	private TraineeJoinPayment getJoin() {
		TraineeJoinPayment s = null;
		try {

			int paymentId = Integer.valueOf(paymentIdTextField.getText().trim());
			int memperId = Integer.valueOf(membershipIdTextField.getText().trim());
			String reqAmount = amountRequiredTextField.getText().trim();
			String paiAmount = amountPaidTextField.getText().trim();
			String remAmount = remainingAmountTextField.getText().trim();
			String dateApaid = paidDateTextField.getText();
			s = new TraineeJoinPayment(paymentId, memperId, reqAmount, paiAmount, remAmount, dateApaid);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return s;
	}

	private void insertData(TraineeJoinPayment rc) {
		try {
			System.out.println(
					"Insert into traineeJoinPayment (paymentId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) values"
							+ "(" + rc.getPaymentId() + "," + rc.getMembershipId() + ",'" + rc.getRemainingAmount()
							+ "','" + rc.getAmountPaid() + "','" + rc.getRemainingAmount() + "','" + rc.getPaidDate()
							+ "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement(
					"Insert into traineeJoinPayment (paymentId,membershipId,amountRequired,amountPaid,remainingAmount,paidDate) values"
							+ "(" + rc.getPaymentId() + "," + rc.getMembershipId() + ",'" + rc.getRemainingAmount()
							+ "','" + rc.getAmountPaid() + "','" + rc.getRemainingAmount() + "','" + rc.getPaidDate()
							+ "');");

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
		paymentIdTextField.clear();
		membershipIdTextField.clear();
		amountRequiredTextField.clear();
		amountPaidTextField.clear();
		remainingAmountTextField.clear();
		paidDateTextField.clear();
	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<TraineeJoinPayment> selectedRows = TableJoin.getSelectionModel().getSelectedItems();
		ArrayList<TraineeJoinPayment> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			TableJoin.getItems().remove(row);
			deleteRow(row);
			TableJoin.refresh();
		});

	}

	private void deleteRow(TraineeJoinPayment row) {
		try {
			System.out.println("delete from traineeJoinPayment  where membershipId =" + row.getMembershipId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  traineeJoinPayment where membershipId =" + row.getMembershipId() + ";");
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
		updateRequaier();
		updatePaid();
		updateRemain();
		updateDate();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {
		TraineeJoinPayment join = TableJoin.getSelectionModel().getSelectedItem();

		paymentIdTextField.setText(String.format("%d", join.getPaymentId()));
		membershipIdTextField.setText(String.format("%d", join.getMembershipId()));
		amountRequiredTextField.setText(join.getAmountRequired());
		amountPaidTextField.setText(join.getAmountPaid());
		remainingAmountTextField.setText(join.getRemainingAmount());
		paidDateTextField.setText(join.getPaidDate());

	}

	@FXML
	void search(ActionEvent event) {
		if (event.getSource() == searchButton) {
			FilteredList<TraineeJoinPayment> filteredData = new FilteredList<>(dataList, b -> true);
			serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(join -> {
					// If filter text is empty, display all persons.

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					// Compare first name and last name of every person with filter text.
					String lowerCaseFilter = newValue.toLowerCase();
					if (String.valueOf(join.getMembershipId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(join.getPaymentId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else
						return false;

				});
			});

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<TraineeJoinPayment> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(TableJoin.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			TableJoin.setItems(sortedData);

		}

	}

}
