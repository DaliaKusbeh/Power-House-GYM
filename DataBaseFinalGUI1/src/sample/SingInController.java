package sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SingInController implements Initializable {
	private Connection con;

	
	@FXML
	private Label resLabel;
	@FXML
	private PasswordField passWordText;
	@FXML
	private TextField userText;
	@FXML
	private Button signInButton;

	private String userName = "Root";
	private String passWord = "Root";

	@FXML
	public void sigin(ActionEvent event) throws SQLException {
		resLabel.setVisible(false);
		if (userText.getText().equals("") || passWordText.getText().equals("")) {
			System.out.print("Should not print\n\n");
			resLabel.setVisible(true);
		} else if (userText.getText().equals(userName) && passWordText.getText().equals(passWord)) {
			System.out.println("Correct..!");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;
			try {
				System.out.print("Should not print3\n\n");
				String admin_SSN = userText.getText().trim(); // We are getting the username
				String admin_Paswword = passWordText.getText().trim(); // getting the entered password
				// -----//
				String sql = "select * from trainee;"; // this is the // statement to b // executed
				con = a.connectDB();
				System.out.println(con.toString() + "---");
				stmt = con.createStatement();
				System.out.print("Should not print333333333333333\n\n");
//				rs = stmt.executeQuery(sql);
				System.out.print("Should not print3\n\n");
//				System.out.println(rs.);
				rs = stmt.executeQuery(sql);
//				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//				int columnsNumber = rsmd.getColumnCount();
//				while (rs.next()) {
//					for (int i = 1; i <= columnsNumber; i++) {
//						if (i > 1)
//							System.out.print(",  ");
//						String columnValue = rs.getString(i);
////						System.out.print(columnValue + " " + rsmd.getColumnName(i));
//						System.out.print(columnValue + " " );
//
//					}
//					System.out.println("");
//				}

			} catch (Exception e) {
				System.out.println("from Catch");
				resLabel.setVisible(true);
			} finally {
				if (stmt != null) {
					stmt.close();
					con.close();
					rs.close();
					System.out.println("from Final1");

				}
				System.out.println("from Final2");

			}

			// -----//

		} else
			resLabel.setVisible(true);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
