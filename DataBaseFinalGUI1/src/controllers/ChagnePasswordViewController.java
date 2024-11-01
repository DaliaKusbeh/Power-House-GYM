package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;

import static controllers.SingInController.LoginAdminId;
import Utilties.*;

import java.io.IOException;
import java.lang.runtime.ObjectMethods;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class ChagnePasswordViewController {

	@FXML
	private PasswordField CPasswordPasswordField;

	@FXML
	private Label WrongLabel;

	@FXML
	private PasswordField passwordPasswordField;

	@FXML
	private Button restMyPasswordButton;

	@FXML
	private TextField userNameTextField;

	Connection con;

	@FXML
	void handelChangePassword(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		WrongLabel.setVisible(false);
		String pass = passwordPasswordField.getText().trim();
		String CPass = CPasswordPasswordField.getText().trim();

		if (pass.isEmpty() || CPass.isEmpty()) {
			WrongLabel.setVisible(true);
			Massege.displayMassage("Password", "Password Filed can not Be Empty");
			return;
		}
		int adminId = -1;
		try {

			adminId = Integer.parseInt(userNameTextField.getText().trim());
		} catch (Exception e) {
			System.out.println("Can not be A String" + e.getMessage());
		}
		System.out.println(Validation.isValidID(adminId));
		if (Validation.isValidID(adminId))
			LoginAdminId = adminId;
		else {
			Massege.displayMassage("Erorr : not Correct ! ", "InCorrect Id Number");
			WrongLabel.setVisible(true);
			return;
		}
		if (!WrongLabel.isVisible()) {
			DBInitializing DB1 = new DBInitializing(); // connecting to database
			DBConn a1 = new DBConn(DB1.getURL(), DB1.getPort(), DB1.getDbName(), DB1.getDbUsername(),
					DB1.getDbPassword());
			con = a1.connectDB();
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(
					"select administratorId from administrator where administratorId =" + LoginAdminId + " ;");

			if (!rs.next()) {
				Massege.displayMassage("Admin ID", "In Correct Admin ID ");
				return;
			} else
				System.out.println(rs.getString("administratorId"));

			if (!pass.equals(CPass)) {
				WrongLabel.setVisible(true);
				return;
			} else {
				DBInitializing DB = new DBInitializing(); // connecting to database
				DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(),
						DB.getDbPassword());
				con = a.connectDB();
				Statement statement = con.createStatement();
				String sql = "update powerhouse_gym.administrator set passworde = '" + pass
						+ "' where administratorId = " + LoginAdminId + ";"; // this
				System.out.println(sql);
				statement.executeUpdate(sql);
				statement.close();

				if (!WrongLabel.isVisible()) {
					// ***
					Massege.displayMassage("Change Password", "Change PassWord Was Done Succefully");

//				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
//				Parent AboutUsStage = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));
//				Scene scene = new Scene(AboutUsStage);
//				stage.setScene(scene);
//				stage.show();
//				stage.setTitle("Main Window");

				}

			}
		}

	}

	@FXML
	void handelBack(MouseEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // to access main stage
		Parent AboutUsStage = FXMLLoader.load((getClass().getResource("../FXML/SignIn.fxml")));
		Scene scene = new Scene(AboutUsStage);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Main Window");

	}
}