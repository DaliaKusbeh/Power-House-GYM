package controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sample.DBConn;
import sample.DBInitializing;

public class DashBoardController implements Initializable {

	@FXML
	private Label coachesLabel;
	@FXML
	private Text dateTextField;
	@FXML
	private Text dayTextField;
	@FXML
	private Text resText;
	@FXML
	private Text resText1;
	@FXML
	private Label traineesLabek;
	Connection con;
	@FXML
	private Label EmployeeresLabel;
	@FXML
	private Label goldresLabel;
	@FXML
	private Label silverResLabel;
	@FXML
	private Label studentReslabel;
	@FXML
	private Label monthsResulLabel;
	@FXML
	private Label yearResulLabel;
	@FXML
	private Label dayResultLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Calendar calendar = Calendar.getInstance();
		String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

		dateTextField.setText(java.time.LocalDate.now() + "");
		dayTextField.setText(days[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		try {
			con = a.connectDB();
			Statement statement = con.createStatement();
			String sql = "select count(coachId) from coach;";
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				BigDecimal val = rs.getBigDecimal(1);
				System.out.println(val);
				coachesLabel.setText(val.toString());

			}
			String sql3 = "select count(*) from trainee;";
			ResultSet rs3 = statement.executeQuery(sql3);
			if (rs3.next()) {
				BigDecimal val = rs3.getBigDecimal(1);
				System.out.println(val);
				traineesLabek.setText(val.toString());

			}
//			select count(*) from traineeJoinPayment where membershipId =1;   ;
//			select count(*) from traineeJoinPayment where membershipId =2;   ;
//			select count(*) from traineeJoinPayment where membershipId =3;   ;
//			select count(*) from traineeJoinPayment where membershipId =4;   ;
//

			String sql4 = "select count(*) from traineeJoinPayment where membershipId =1 ; ";
			ResultSet rs4 = statement.executeQuery(sql4);
			if (rs4.next()) {
				BigDecimal val = rs4.getBigDecimal(1);
				System.out.println(val);
				studentReslabel.setText(val.toString());
			}
			String sql5 = "select count(*) from traineeJoinPayment where membershipId =2 ; ";
			ResultSet rs5 = statement.executeQuery(sql5);
			if (rs5.next()) {
				BigDecimal val = rs5.getBigDecimal(1);
				System.out.println(val);
				EmployeeresLabel.setText(val.toString());
			}
			String sql6 = "select count(*) from traineeJoinPayment where membershipId =3; ";
			ResultSet rs6 = statement.executeQuery(sql6);
			if (rs6.next()) {
				BigDecimal val = rs6.getBigDecimal(1);
				System.out.println(val);
				silverResLabel.setText(val.toString());
			}
			String sql7 = "select count(*) from traineeJoinPayment where membershipId =4; ";
			ResultSet rs7 = statement.executeQuery(sql7);
			if (rs7.next()) {
				BigDecimal val = rs7.getBigDecimal(1);
				System.out.println(val);
				goldresLabel.setText(val.toString());
			}
//			java.sql.Date date;
			String sql8 = "select paidDate from traineeJoinPayment; ";
			ResultSet rs8 = statement.executeQuery(sql8);
			Date date1 = new Date();
			java.sql.Date date2 = new java.sql.Date(date1.getTime());
			System.out.println(date1 + " ," + date2);
			int monthCount = 0;
			int yearCount = 0;
			int dayCount = 0;
			while (rs8.next()) {

				java.sql.Date date = java.sql.Date.valueOf(rs8.getString(1).trim());
				if ((date.getYear() == date2.getYear()) && (date.getMonth() == date2.getMonth()))
					monthCount++;
				if (date.getYear() == date2.getYear())
					yearCount++;
				if ((date2.getDate() == (date.getDate()) && date2.getMonth() == date.getMonth()
						&& date2.getDay() == date.getDay()))
					dayCount++;

				System.out.println(date);
//				System.out.println(date2);
//				System.out.println(date2.equals(date));

			}
			dayResultLabel.setText(dayCount + "");
			yearResulLabel.setText(yearCount + "");
			monthsResulLabel.setText(monthCount + "");
			System.out.println("Conter Of This Day :" + dayCount + "\nCounter OF Month :" + monthCount
					+ "\nCounter Of Year : " + yearCount);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
