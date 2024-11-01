package controllers;

import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
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

public class MembershipInformationControll implements Initializable {

	@FXML
	private TableView<MembershipInformation> ATableView;

	@FXML
	private TableColumn<MembershipInformation, Integer> membershipId;

	@FXML
	private TableColumn<MembershipInformation, String> membershipType;

	@FXML
	private TableColumn<MembershipInformation, String> Duration;

	@FXML
	private TableColumn<MembershipInformation, String> price;

	@FXML
	private TableColumn<MembershipInformation, Integer> NoOfMempership;

	@FXML
	private Button searchButton;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField idMemper;

	@FXML
	private TextField totalNumberOfType;

	@FXML
	private Button totalNumberOfTypeButton;

	@FXML
	private TextField totalNumberOfParticipants;

	@FXML
	private Button totalNumberOfParticipantsButton;

	@FXML
	private TextField typeTextField;

	@FXML
	private TextField durationTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField ParticipantsTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;

	private ObservableList<MembershipInformation> dataList = FXCollections.observableArrayList();

	Connection con;
	ResultSet rs;

	PreparedStatement pst;
	String query = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ParticipantsTextField.setEditable(false);
		try {
			loadDate();
			updateValue();
			sortComBox.getItems().addAll("membershipId", "membershipType", "Duration", "price", "TotalMember");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		membershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
		membershipType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
		Duration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		NoOfMempership.setCellValueFactory(new PropertyValueFactory<>("NoOfMempership"));

	}

	@FXML
	void getTotalNumberParticipants(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		ResultSet rs;
		if (event.getSource() == totalNumberOfParticipantsButton) {
			String query = " select count(*) from traineeJoinPayment;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfParticipants.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void getTotalNumberType(ActionEvent event) throws SQLException, ClassNotFoundException {
		// dataList.clear();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		ResultSet rs;
		if (event.getSource() == totalNumberOfTypeButton) {
			String query = "select count(*) as sum from membership ;";
			int count = 0;
			try {
				rs = con.createStatement().executeQuery(query);
				while (rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				totalNumberOfType.setText(String.format("%d", (count)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void refreshTable() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		ResultSet rs;
		ResultSet rs1;
		dataList.clear();
		try {

			String sql = " select membership.membershipId , membership.membershipType, membership.Duration, membership.price, "
					+ " count(membership.membershipId)" + " from membership,traineeJoinPayment "
					+ " where membership.membershipId = traineeJoinPayment.membershipId " + " group by membershipId;";

			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			// rs1 = con.createStatement().executeQuery("select * from coach");
			while (rs.next())
				dataList.addAll(new MembershipInformation(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5)));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		ATableView.setItems(dataList);
		// ATableView.setItems(dataList1);

	}

	@FXML
	void handelSearch(ActionEvent event) throws ClassNotFoundException, SQLException {

		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		ResultSet rs;
		WrongSearchLabel.setVisible(false);
		System.out.println("Inside Search ");
		if (event.getSource() == searchButton) {
			String fName = firstNameTextField.getText().trim();
			// String lName = lastNameTextField.getText().trim();
			System.out.println(fName);

			if (fName.isEmpty()) {
				System.out.print("Can Not Search");
				WrongSearchLabel.setVisible(true);
			} else {
				dataList.clear();
				try {
					String sql = "select membership.membershipId , membership.membershipType, membership.Duration, membership.price, "
							+ " count(membership.membershipId) " + " from membership,traineeJoinPayment "
							+ " where membership.membershipId = traineeJoinPayment.membershipId and  "
							+ " membership.membershipType like '%" + fName + "%' " + " group by membershipId; ";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.add(new MembershipInformation(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getInt(5)));
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ATableView.setItems(dataList);

			}
		}

	}

	@FXML
	void handelSort(ActionEvent event) throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		ResultSet rs;
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

				String sql = " select membership.membershipId , membership.membershipType, membership.Duration, membership.price,"
						+ " count(membership.membershipId) as TotalMember " + " from membership,traineeJoinPayment "
						+ " where membership.membershipId = traineeJoinPayment.membershipId "
						+ " group by membershipId order by " + SortText + " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.addAll(new MembershipInformation(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getInt(5)));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		MembershipInformation sch = getCoach();
		if (sch != null) {
			dataList.add(sch);
			updateType();
			updateDuration();
			updatePrice();

		}

	}

	private boolean updateType() throws ClassNotFoundException, SQLException {
		String newName = typeTextField.getText().trim();
		if (newName.equalsIgnoreCase("Student membership") || newName.equalsIgnoreCase("Employee membership")
				|| newName.equalsIgnoreCase("Silver membership") || newName.equalsIgnoreCase("gold membership")) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "update powerhouse_gym.membership set membershipType = '" + newName + "' where membershipId = "
					+ (idMemper.getText()) + ";"; // this
			System.out.println(sql);

			con = a.connectDB();
			stmt = con.createStatement();
			DB.ExecuteUpdateStatement(sql);
			stmt.close();
			con.close();
			return true;
		} else {
			(new Alert(Alert.AlertType.ERROR, "Wrong Type")).show();
			return false;
		}
	}

	private void updateDuration() throws ClassNotFoundException, SQLException {
		String newName = durationTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "update powerhouse_gym.membership set Duration = '" + newName + "' where membershipId = "
				+ (idMemper.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	private boolean updatePrice() throws ClassNotFoundException, SQLException {

		String newGender = priceTextField.getText().trim();
		if (newGender.equalsIgnoreCase("50 nis") || newGender.equalsIgnoreCase("100 nis")
				|| newGender.equalsIgnoreCase("250 nis") || newGender.equalsIgnoreCase("900 nis")) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "update powerhouse_gym.membership set price = '" + newGender + "' where membershipId = "
					+ (idMemper.getText()) + ";"; // this
			System.out.println(sql);

			con = a.connectDB();
			stmt = con.createStatement();
			DB.ExecuteUpdateStatement(sql);
			stmt.close();
			con.close();
			return true;
		} else {
			(new Alert(Alert.AlertType.ERROR, "Wrong Price")).show();
			return false;
		}
	}

	private MembershipInformation getCoach() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		// ResultSet rs;
		MembershipInformation s = null;
		try {

			int coachId = Integer.valueOf(idMemper.getText().trim());
			String fname = typeTextField.getText().trim();
			String lname = durationTextField.getText().trim();
			String gender = priceTextField.getText().trim();
			int exp = Integer.valueOf(ParticipantsTextField.getText().trim());

			s = new MembershipInformation(coachId, fname, lname, gender, exp);

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
		idMemper.clear();
		typeTextField.clear();
		durationTextField.clear();
		priceTextField.clear();
		ParticipantsTextField.clear();
		totalNumberOfParticipants.clear();
		totalNumberOfType.clear();
	}

	@FXML
	void handleDelete(ActionEvent event) throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		ResultSet rs;
		ObservableList<MembershipInformation> selectedRows = ATableView.getSelectionModel().getSelectedItems();
		ArrayList<MembershipInformation> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			ATableView.getItems().remove(row);
			deleteRow(row);
			ATableView.refresh();
		});

	}

	private void deleteRow(MembershipInformation row) {

		try {
			System.out.println("delete from  membership where membershipId =" + row.getMembershipId() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("delete from  membership where membershipId =" + row.getMembershipId() + ";");
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
		updateDuration();
		updatePrice();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {
		MembershipInformation traninee = ATableView.getSelectionModel().getSelectedItem();
		if (traninee != null) {
			idMemper.setText(String.format("%d", traninee.getMembershipId()));
			typeTextField.setText(traninee.getMembershipType());
			durationTextField.setText(traninee.getDuration());
			priceTextField.setText(traninee.getPrice());
			ParticipantsTextField.setText(String.format("%d", traninee.getNoOfMempership()));
		}

	}

}
