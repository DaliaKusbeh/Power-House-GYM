package controllers;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import sample.ActivityInformation;
import sample.DBConn;
import sample.DBInitializing;
import sample.Trainee;
import sample.TraineeTV;

public class TraineeTVController implements Initializable {

	@FXML
	private Button contactInformation;
	@FXML
	private ComboBox<String> coachNameCol;
	@FXML
	private ComboBox<String> MembershipTypeCoBox;
	@FXML
	private TableColumn<TraineeTV, String> CoachNameCol;
	@FXML
	private TableColumn<TraineeTV, String> GenderCol;
	@FXML
	private TableColumn<TraineeTV, String> PhoneNumberCol;
	@FXML
	private ComboBox<String> SortComBox;
	@FXML
	private ToggleGroup genderTolgelGroup;
	@FXML
	private TableColumn<TraineeTV, String> TraineeIdCol;
	@FXML
	private TableView<TraineeTV> TtableView;
	@FXML
	private Label Wsearch;
	@FXML
	private Label Wsort;
	@FXML
	private TableColumn<TraineeTV, String> emailCol;
	@FXML
	private TableColumn<TraineeTV, String> firstNameCol;
	@FXML
	private TextField firstNameText;
	@FXML
	private TableColumn<TraineeTV, String> lastNameCol;
	@FXML
	private TextField lastNameText;
	@FXML
	private TableColumn<TraineeTV, String> membershipTypeCol;
	@FXML
	private Button searchButton;
	@FXML
	private Button sortButton;
	@FXML
	private TextField MembershipTypeTextField;
	@FXML
	private TextField coachNameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField lastnameTextField;
	@FXML
	private TextField phoneNumberTextField;
	@FXML
	private RadioButton FEMAILERB;
	@FXML
	private RadioButton maleRB;

	Connection con;
	ObservableList<TraineeTV> dataList = FXCollections.observableArrayList();
	ResultSet rs;
	String query = null;
	private int traineeId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Wsearch.setVisible(false);
		Wsort.setVisible(false);

		SortComBox.getItems().addAll("traineeId", "Fname", "Lname", "coachName", "gender", "email", "phoneNumber",
				"membershipType");
		try {
			loadDate();
			updateValue();
			fillCoachNameCombBox();
			fillMembershipTypeCoBox();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fillMembershipTypeCoBox() throws ClassNotFoundException, SQLException {

		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql = "select membershipType from membership;";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		ObservableList<String> list = FXCollections.observableArrayList();
		while (rs.next())
			list.add(rs.getString(1));

		MembershipTypeCoBox.getItems().addAll(list);

	}

	private void fillCoachNameCombBox() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql = "select concat (Fname ,\" \" ,lname) as name from coach ;";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		ObservableList<String> list = FXCollections.observableArrayList();
		while (rs.next())
			list.add(rs.getString(1));

		coachNameCol.getItems().addAll(list);
	}

	private void updateValue() {

	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		TraineeIdCol.setCellValueFactory(new PropertyValueFactory<>("traineeid"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
		CoachNameCol.setCellValueFactory(new PropertyValueFactory<>("coachName"));
		GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		PhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		membershipTypeCol.setCellValueFactory(new PropertyValueFactory<>("membershipType"));

	}

	@FXML
	private void refreshTable() throws ClassNotFoundException {
		dataList.clear();
		try {

			String sql = "select t.traineeId ,t.Fname  ,t.lname  ,concat(c.Fname ,\" \", c.lname )  as coachName ,t.gender ,t.email ,phoneNumber ,m.membershipType from trainee t  , coach c  ,  phoneNumberTrainee pt , traineeJoinPayment tp , membership m "
					+ " where  t.coachId = c.coachId  and pt.traineeId = t.traineeId and  tp.traineeId = t.traineeId  and m.membershipId = tp.membershipId  ;";
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();

			rs = con.createStatement().executeQuery(sql);

			while (rs.next()) {
				TraineeTV trainee = new TraineeTV();
				trainee.setTraineeid(rs.getInt(1));
				trainee.setFname(rs.getString(2));
				trainee.setLname(rs.getString(3));
				trainee.setCoachName(rs.getString(4));
				trainee.setGender(rs.getString(5));
				trainee.setEmail(rs.getString(6));
				trainee.setPhoneNumber(rs.getString(7));
				trainee.setMembershipType(rs.getString(8));
				dataList.add(trainee);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TtableView.setItems(dataList);
	}

	@FXML
	void handelSearch(ActionEvent event) throws ClassNotFoundException, SQLException {
		System.out.println("Inside Search");
		if (event.getSource() == searchButton) {
			String fn = firstNameTextField.getText().trim();
			String ln = lastnameTextField.getText().trim();
			System.out.println("Name : " + fn + " , " + ln);
			if (fn.isEmpty() && ln.isEmpty()) {
				Wsearch.setVisible(true);
				TtableView.refresh();
				return;
			}

			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			String sql = "select t.traineeId ,t.Fname , t.lname  ,concat(c.Fname ,\" \", c.lname )  as coachName ,t.gender ,t.email ,phoneNumber ,m.membershipType "
					+ "from trainee t  , coach c  ,  phoneNumberTrainee pt , traineeJoinPayment tp , membership m where t.coachId = c.coachId and pt.traineeId = t.traineeId and  tp.traineeId = t.traineeId  and m.membershipId = tp.membershipId and t.Fname like '%"
					+ fn + "%' and t.lname like '%" + ln + "%' ;";
			ResultSet rs = con.createStatement().executeQuery(sql);
			System.out.println(sql);
			ObservableList<TraineeTV> list = FXCollections.observableArrayList();
			while (rs.next()) {
				list.add(new TraineeTV(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8)));

			}
			TtableView.setItems(list);

		}

	}

	@FXML
	void handelSort(ActionEvent event) throws ClassNotFoundException, SQLException {

		if (event.getSource() == sortButton) {
			String sortSelection = SortComBox.getSelectionModel().getSelectedItem();
			if (sortSelection != null) {
				DBInitializing DB = new DBInitializing(); // connecting to database
				DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(),
						DB.getDbPassword());
				con = a.connectDB();
				String sql = "select t.traineeId ,t.Fname , t.lname  ,concat(c.Fname ,\" \", c.lname )  as coachName ,t.gender ,t.email ,phoneNumber ,m.membershipType from trainee t  , coach c  ,  phoneNumberTrainee pt , traineeJoinPayment tp , membership m where "
						+ " t.coachId = c.coachId and pt.traineeId = t.traineeId and  tp.traineeId = t.traineeId  and m.membershipId = tp.membershipId order by "
						+ sortSelection + ";";
				ResultSet rs = con.createStatement().executeQuery(sql);
				ObservableList<TraineeTV> list = FXCollections.observableArrayList();

				while (rs.next()) {
					list.add(new TraineeTV(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));

				}
				TtableView.setItems(list);
				con.close();

			} else {
				Wsort.setVisible(true);
			}

		}

	}

	@FXML
	void handelRefresh(ActionEvent event) throws ClassNotFoundException {
		refreshTable();

	}

	@FXML
	void updateView(MouseEvent event) {
		System.out.println("Update View");
		TraineeTV traninee = TtableView.getSelectionModel().getSelectedItem();
		if (traninee != null) {
			firstNameText.setText(traninee.getFname());
			lastNameText.setText(traninee.getLname());
			emailTextField.setText(traninee.getEmail());
			coachNameTextField.setText(traninee.getCoachName());
			phoneNumberTextField.setText(traninee.getPhoneNumber());
			MembershipTypeTextField.setText(traninee.getMembershipType());
			FEMAILERB.setSelected(traninee.getGender().equalsIgnoreCase("Female"));
			maleRB.setSelected(traninee.getGender().equalsIgnoreCase("Male"));

		}

	}

	@FXML
	void handelClear(ActionEvent event) {
		firstNameText.clear();
		lastNameText.clear();
		emailTextField.clear();
		coachNameTextField.clear();
		phoneNumberTextField.clear();
		MembershipTypeTextField.clear();
		FEMAILERB.setSelected(false);
		maleRB.setSelected(false);

	}

	@FXML
	void handelUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {

		System.out.println("Inside Update ");
//		getTraineeId();
//		if (!firstNameText.getText().isEmpty())
		updateFirstName();
//		if (!lastNameText.getText().isEmpty())
		updateLastName();
//		if (!coachNameTextField.getText().isEmpty())
		updateCoachName();
//		if (maleRB.isSelected() || FEMAILERB.isSelected())
		updateGender();
//		if (!emailTextField.getText().isEmpty())
		updateEmail();
//		if (!phoneNumberTextField.getText().isEmpty())
		updatePhoneNumber();
//		if (MembershipTypeTextField.getText().isEmpty())
		updateMemberShipType();

	}

	private void updateMemberShipType() throws ClassNotFoundException, SQLException {
		System.out.println("Inside MemberShip Type");
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		int MemberShipId = getMemberShipId();
		System.out.println("traineeId == " + traineeId);
		if (MemberShipId != -1) {
			if (traineeId != -1) {
				Statement statement = con.createStatement();
				String sql = "update powerhouse_gym.traineeJoinPayment set membershipId =" + MemberShipId
						+ " where traineeId = " + traineeId + ";";
				System.out.println(sql);
				DB.ExecuteUpdateStatement(sql);
				statement.close();

			} else
				System.out.println("traineeId Not Valid" + traineeId);

		} else {
			System.out.println("MemberShipId Not Valid" + MemberShipId);
		}
		con.close();

	}

	private int getMemberShipId() throws ClassNotFoundException, SQLException {
		String memberShip = MembershipTypeCoBox.getSelectionModel().getSelectedItem();
		System.out.println("memberShip : " + memberShip);
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		String sql = "select membershipid from membership where membershipType = '" + memberShip + "'; ";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		int memberShipId = -1;
		if (rs.next())
			memberShipId = rs.getInt(1);

		System.out.println("member ShipId ===> " + memberShipId);

		return memberShipId;
	}

	private void updatePhoneNumber() throws ClassNotFoundException, SQLException {
		System.out.println("Inside Phone");

		String newPhone = phoneNumberTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
//		int traineeId = getTraineeId();
		System.out.println(traineeId + " <===");
		if (traineeId != -1) {
			String sql = "update powerhouse_gym.phoneNumberTrainee set phoneNumber = " + newPhone
					+ " where traineeId = " + traineeId + " ;";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
			rs.close();
			con.close();
			refreshTable();
		} else {
			System.out.println("ID Not Founded .!!" + traineeId);
		}

	}

	private void updateEmail() throws ClassNotFoundException, SQLException {
		System.out.println("Inside Email");

		String newEmail = emailTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
//		int traineeId = getTraineeId();
		System.out.println("Trainee Id " + traineeId);
		if (traineeId != -1) {
			String sql = "update powerhouse_gym.trainee set email = '" + newEmail + "' where traineeId = " + traineeId
					+ ";";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
//			refreshTable();

			con = a.connectDB();
			Statement stmt = con.createStatement();
			DB.ExecuteUpdateStatement(sql);
			stmt.close();
			con.close();

		} else {
			System.out.println("ID Not Founded .!!" + traineeId);
		}

	}

	private void updateGender() throws ClassNotFoundException, SQLException {
		System.out.println("Inside Gender");

		String newGender = maleRB.isSelected() ? "Male" : "Female";
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
//		int traineeId = getTraineeId();
		System.out.println("traineeId == > " + traineeId);
		if (traineeId != -1) {
			String sql = "update powerhouse_gym.trainee set gender = '" + newGender + "' where traineeId = " + traineeId
					+ ";";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
			rs.close();
			con.close();
			refreshTable();
		} else {
			System.out.println("ID Not Founded .!!" + traineeId);
		}

//		String sql = "select t.traineeId ,t.Fname , t.lname  ,concat(c.Fname ,\" \", c.lname )  as coachName ,t.gender ,t.email ,phoneNumber ,m.membershipType from trainee t  , coach c  ,  phoneNumberTrainee pt , traineeJoinPayment tp , membership m where "
//				+ " t.coachId = c.coachId and pt.traineeId = t.traineeId and  tp.traineeId = t.traineeId  and m.membershipId = tp.membershipId order by "
//				+ sortSelection + ";";

	}

	private int getTraineeId() {
		int id = -1;
		TraineeTV trainee = TtableView.getSelectionModel().getSelectedItem();
		if (trainee != null)
			id = trainee.getTraineeid();
		System.out.println(trainee.getTraineeid() + " =============");
		System.out.println("Trainee ID : " + id);
		return id;
	}

	private void updateCoachName() throws ClassNotFoundException, SQLException {
		System.out.println("Update In Coach Name\n\n\n");
		String newCoach = coachNameTextField.getText().trim();
		String[] tkn = newCoach.split(" ");
		String fn = tkn[0];
		String ln = tkn[1];
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
//		int traineeId = getTraineeId();
		System.out.println("Orginal Name :" + newCoach);
		System.out.println("Name :" + fn + " " + ln);
		System.out.println("traineeId" + traineeId);
		int coachid = getCoachId();
//		int coachid = 2;
		if (coachid != -1) {
			if (traineeId != -1) {
				String sql = "update powerhouse_gym.trainee set coachid =" + coachid + " where traineeId = " + traineeId
						+ ";";
				System.out.println(sql);
				DB.ExecuteUpdateStatement(sql);
				rs.close();
				con.close();
				refreshTable();
			} else {
				System.out.println("ID Not Founded .!!" + traineeId);
			}
			System.out.println("Coach ID Not Founded .!!" + coachid);

		}

	}

	private int getCoachId() throws ClassNotFoundException, SQLException {

		System.out.println("Inside getCoachId");

		String newCoach = coachNameCol.getSelectionModel().getSelectedItem();
		if (newCoach != null) {
			String[] tkn = newCoach.split(" ");
			String fn = tkn[0];
			String ln = tkn[1];
			String sql = "select coachid  from coach where fname ='" + fn + "' and lname ='" + ln + "' ;";
			System.out.println(sql);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			int id = -1;
			if (rs.next())
				id = rs.getInt(1);

			System.out.println("Cocach ID :" + id);

			return id;
		}
		return -1;
	}

	private void updateLastName() throws ClassNotFoundException, SQLException {
		System.out.println("Inside Last Name");
		String newLastName = lastNameText.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		System.out.println("traineeId bEFORE " + traineeId);
//		traineeId = getTraineeId();
		System.out.println("traineeId aFTER " + traineeId);

		if (traineeId != -1) {
			String sql = "update powerhouse_gym.trainee set Lname = '" + newLastName + "' where traineeId = "
					+ traineeId + ";";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
			rs.close();
			con.close();
			refreshTable();
		} else {
			System.out.println("ID Not Founded .!!" + traineeId);
		}
	}

	private void updateFirstName() throws ClassNotFoundException, SQLException {
		System.out.println("Inside First Name");

		String newFirstName = firstNameText.getText().trim();
		System.out.println(newFirstName + " <====== F.N");
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		System.out.println(traineeId + " iD ");

		traineeId = getTraineeId();
		System.out.println(traineeId + " iD ");

		if (traineeId != -1) {
			String sql = "update powerhouse_gym.trainee set FName = '" + newFirstName + "' where traineeId = "
					+ traineeId + ";";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
			rs.close();
			con.close();
			refreshTable();
		} else {
			System.out.println("ID Not Founded .!!" + traineeId);
		}
	}

	/*
	 * 
	 * 
	 * Needed To Modify ...
	 */
	@FXML
	void handelDelete(ActionEvent event) {

		ObservableList<TraineeTV> selectedRows = TtableView.getSelectionModel().getSelectedItems();
		ArrayList<TraineeTV> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			TtableView.getItems().remove(row);
			deleteRow(row);
			TtableView.refresh();
		});

	}

	private void deleteRow(TraineeTV row) {
		try {
			System.out.println("Delete Row");
			String sql = "delete from  trainee where traineeid =" + row.getTraineeid() + " ;";
			System.out.println("delete from  trainee where traineeid =" + row.getTraineeid() + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement(sql);
//			String sql1 = "delete From phoneNumberTrainee where traineeid = "+ row.getTraineeid() + " ; ";
//			ExecuteStatement(sql1);
//			ExecuteStatement("delete from  ActivitySchedualee where traineeid =" + row.getTraineeid() + ";");
//			ExecuteStatement("delete from  schedulee where traineeid =" + row.getTraineeid() + ";");
//			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			System.out.println((e.getMessage()));
		} catch (ClassNotFoundException e) {
			System.out.println((e.getMessage()));
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

	public void handelDelete1(ActionEvent event) throws ClassNotFoundException, SQLException {
		int traineeid = getTraineeId();
		if (traineeid != -1) {
			String sql = "delete from  trainee where traineeid =" + traineeid + " ;";
			System.out.println("delete from  trainee where traineeid =" + traineeid + ";");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement(sql);
		} else {
			System.out.println("traineeid mot Founded");
		}

	}

	@FXML
	void handelContactInformation(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/TrianeeInformation.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

}
