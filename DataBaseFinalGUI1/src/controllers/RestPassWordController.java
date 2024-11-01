package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import static controllers.SingInController.LoginAdminId;
import Utilties.Massege;

public class RestPassWordController {

	@FXML
	private PasswordField ConfirmNewPasswordPField;

	@FXML
	private Label WPassWordLabel;

	@FXML
	private Button changePasswordButton;

	@FXML
	private PasswordField newPasswordPField;
	Connection con;
	@FXML
	private Button backButton;

	@FXML
	void changePassword(ActionEvent event) throws ClassNotFoundException, SQLException {
		WPassWordLabel.setVisible(false);
		System.out.println("inside Change");
//		if (event.getSource() == changePasswordButton) {
		String newPass = newPasswordPField.getText().trim();
		String confPass = ConfirmNewPasswordPField.getText().trim();
		System.out.println(newPass);
		System.out.println(confPass);
		System.out.println(newPass.equals(confPass) + " <==");
		System.out.println(LoginAdminId);

		if (!newPass.equals(confPass)) {
			WPassWordLabel.setVisible(true);
			System.out.println("Not Equal");
			Massege.displayMassage("Erorr in Change Password Stage", "Password that Entered was Not The Same");

		} else if (newPass.equals(confPass)) {
			System.out.println(" Equal");

			WPassWordLabel.setVisible(false);
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			Statement statement = con.createStatement();
			String sql = "update powerhouse_gym.administrator set passworde = '" + newPass
					+ "' where administratorId = " + LoginAdminId + ";"; // this
			System.out.println(sql);
			statement.executeUpdate(sql);
			statement.close();

		}

	}

	@FXML
	void handelBackButton(ActionEvent event) throws IOException {
		if (event.getSource() == backButton) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
			Scene scene = new Scene(firstSceneView);
			stage.setScene(scene);
			stage.show();

		}

	}

}
