
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import Utilties.*;

public class AddScheduleViewController implements Initializable {

	@FXML
	private Label Wactivity;
	@FXML
	private TextArea activityTextArea;
	@FXML
	private ComboBox<String> selectCoachComBox;
	@FXML
	private TextField SCNameTextField;

	@FXML
	private Label WSchaduleName;

	@FXML
	private Label WnameOfCoach;

	@FXML
	private Label WStartTime;

	@FXML
	private Label Wday;

	@FXML
	private Label WendTime;

	@FXML
	private Button addButton;

	@FXML
	private Button backButton;

	@FXML
	private ComboBox<String> selTimeComBox1;

	@FXML
	private ComboBox<String> selTimeComBox2;

	@FXML
	private ComboBox<String> timeComBox1;

	@FXML
	private ComboBox<String> timeComBox2;

	@FXML
	private ComboBox<String> dayComBox;
	Connection con;

	private String[] time = { "AM", "PM" };
	private String[] timeNum = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	private String[] day = { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };

	@FXML
	void handelAdd(ActionEvent event) throws ClassNotFoundException, SQLException {
//		insert into schedulee (scheduleName,coachId,datee,startTime,endTime)

		String nameOfCoach = selectCoachComBox.getSelectionModel().getSelectedItem();
		String scheduleName = SCNameTextField.getText().trim();
//		String day = dayTextField.getText().trim();
		String day = dayComBox.getSelectionModel().getSelectedItem();
		String sTime = selTimeComBox1.getSelectionModel().getSelectedItem();
		String eTime = selTimeComBox2.getSelectionModel().getSelectedItem();
		String time1 = timeComBox1.getSelectionModel().getSelectedItem();
		String time2 = timeComBox2.getSelectionModel().getSelectedItem();
		String StartTime = sTime + " " + time1;
		String endTime = eTime + " " + time2;
		int coachId = getCoachId();

		if (scheduleName == null) {
			WSchaduleName.setVisible(true);
//			return;
		}
		if (sTime == null || time1 == null) {
			WStartTime.setVisible(true);
//			return;
		}
		if (endTime == null || time2 == null) {
			WendTime.setVisible(true);
//			return;
		}
		if (nameOfCoach == null) {

			WnameOfCoach.setVisible(true);
//			return;
		}

		if (!WSchaduleName.isVisible() && !WStartTime.isVisible() && !WendTime.isVisible()
				&& !WnameOfCoach.isVisible()) {
			if (coachId != -1) {
				DBInitializing DB = new DBInitializing(); // connecting to database
				DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(),
						DB.getDbPassword());
				con = a.connectDB();
				String sql = "insert into schedulee (scheduleName,coachId,theDay,startTime,endTime )values ('"
						+ scheduleName + "'," + coachId + ",'" + day + "','" + StartTime + "','" + endTime + "');";
				System.out.println(sql);
				ExecuteStatement(sql);
				int scheduleId = getSchadualeeId();
				if (scheduleId != -1) {
					String activity = activityTextArea.getText().trim();
					String sql1 = "insert into ActivitySchedualee(scheduleId,activity) values (" + scheduleId + ",'"
							+ activity + "' );";
					System.out.println(sql);
					ExecuteStatement(sql1);
				}
				con.close();
				clear();

			} else
				System.out.println("Coach Id =" + coachId);
		} else {
			Massege.displayMassage("The Field must be Fill ", "Please Fill All The Field");
		}
	}

	private int getSchadualeeId() throws ClassNotFoundException, SQLException {
		System.out.println("Inside getSchadualeeId ");
		int SchadualeeId = -1;
		String sql = "select scheduleid from schedulee order by scheduleid desc limit 1 ; ";
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs.next())
			SchadualeeId = rs.getInt(1);

		System.out.println("SchadualeeId : " + SchadualeeId);

		return SchadualeeId;
	}

	private int getCoachId() throws SQLException, ClassNotFoundException {
		System.out.println("Inside getCoachId ");

		int id = -1;
		String nameOfCoach = selectCoachComBox.getSelectionModel().getSelectedItem();
		if (nameOfCoach != null) {
			String[] tkn = nameOfCoach.split(" ");
			String fname = tkn[0].trim();
			String lname = tkn[1].trim();
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());

			System.out.println(fname + " " + lname + " The Name ");
			String sql = "SELECT coachid FROM coach where fname ='" + fname + "' and lname ='" + lname + "';";
			con = a.connectDB();
			System.out.println(sql);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next())
				id = rs.getInt(1);
			System.out.println("ID : " + id);

			con.close();
			rs.close();
			statement.close();
		}
		return id;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WSchaduleName.setVisible(false);
		WStartTime.setVisible(false);
		WendTime.setVisible(false);
		WnameOfCoach.setVisible(false);
		Wactivity.setVisible(false);

		try {
			fillTheCoachCB();
			fiilAllComBox();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private void fiilAllComBox() {
		timeComBox1.getItems().removeAll(timeComBox1.getItems());
		timeComBox1.getItems().addAll(time);
		timeComBox2.getItems().removeAll(timeComBox2.getItems());
		timeComBox2.getItems().addAll(time);
		selTimeComBox2.getItems().removeAll(selTimeComBox2.getItems());
		selTimeComBox2.getItems().addAll(timeNum);
		selTimeComBox1.getItems().removeAll(selTimeComBox1.getItems());
		selTimeComBox1.getItems().addAll(timeNum);
		dayComBox.getItems().removeAll(dayComBox.getItems());
		dayComBox.getItems().addAll(day);

	}

	private void fillTheCoachCB() throws ClassNotFoundException, SQLException {

		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();

		String sql = "select concat(c.fname , \" \", c.lname) as coachName from coach c where not exists (select * from schedulee s where c.coachid = s.coachid );";

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ObservableList<String> list = FXCollections.observableArrayList();
		while (rs.next()) {
			list.add(rs.getString(1));

		}
		if (!list.isEmpty())
			selectCoachComBox.setItems(list);

	}

	@FXML
	void handelBack(ActionEvent event) throws IOException {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

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

	public void clear() {
		SCNameTextField.clear();
		activityTextArea.clear();
		timeComBox1.getSelectionModel().clearSelection();
		selTimeComBox1.getSelectionModel().clearSelection();
		timeComBox2.getSelectionModel().clearSelection();
		selectCoachComBox.getSelectionModel().clearSelection();
		dayComBox.getSelectionModel().clearSelection();
		selTimeComBox2.getSelectionModel().clearSelection();

	}

	@FXML
	void handleClear(ActionEvent event) {
		clear();
	}

}