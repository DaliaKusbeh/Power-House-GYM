package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;

public class SingInController implements Initializable {

	public static int LoginAdminId = -1;
	Connection con;
	@FXML
	private Label forgetPasswordLabel;
	@FXML
	private PasswordField passWordText;
	@FXML
	private Button signInButton;
	@FXML
	private TextField userText;
	@FXML
	private Label wrongTable;
	@FXML
	private Button backButton;
	@FXML
	private Button signUpButton;

	@FXML
	public void sigin(ActionEvent event) throws SQLException {
		System.out.println("Inside Signin");
		wrongTable.setVisible(false);
		if (userText.getText().equals("") || passWordText.getText().equals("")) {
			System.out.print("Should not print\n\n");
			wrongTable.setVisible(true);
		} else {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;

			try {
				int administratorId = Integer.valueOf(userText.getText().trim());
				String passworde = passWordText.getText().trim();
				System.out.println("Admin Id :" + administratorId + ", " + passworde);

				String sql = "select * from administrator where administratorId=" + administratorId + ";";
				System.out.println(sql);
				con = a.connectDB();
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next() == false) {
					wrongTable.setVisible(true);
				} else {// one tuple at a time
						// rs.getString(6) is col 6 from the therapist table, which is the passwor
					System.out.println("bbbbbb");
					System.out.print(rs.getString(6) + " " + passworde + "\n");
					if (rs.getString(6).equals(passworde)) { // if the entered password same as database, open next
						// stage
						LoginAdminId = administratorId;
						System.out.println("TAY");

						Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
						Parent AboutUsStage = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
						Scene scene = new Scene(AboutUsStage);
						stage.setScene(scene);
						stage.show();
						stage.setTitle("Main Window");

					} else {
						wrongTable.setVisible(true);
					}
				}

			} catch (Exception e) {
				wrongTable.setVisible(true);

			} finally {
				if (stmt != null) {
					stmt.close();
					con.close();
					rs.close();
				}
			}
		}

//		} else if (userText.getText().equals(userName) && passWordText.getText().equals(passWord)) {
//			System.out.println("Correct..!");
//			DBInitializing DB = new DBInitializing(); // connecting to database
//			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
//			Statement stmt = null;
//			ResultSet rs = null;
//		} else
//			wrongTable.setVisible(true);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void setBackToMainView(ActionEvent event) throws IOException {
		if (event.getSource() == backButton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/FirstScene.fxml")));
			Scene scene = new Scene(firstSceneView);
			stage.setScene(scene);
			stage.show();

		}

	}

	@FXML
	void handleSignUpView(ActionEvent event) throws IOException {
		if (event.getSource() == signUpButton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/SignUp.fxml")));
			Scene scene = new Scene(firstSceneView);
			stage.setScene(scene);
			stage.show();
			stage.setTitle("Sign Up Window");
		}

	}

	@FXML
	void handelForgetPassword(MouseEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/ChangePasswordView.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Sign Up Window");

	}

}
