package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import Utilties.*;

public class AddMemberShipType implements Initializable {

	@FXML
	private TextField DurationText;
	@FXML
	private TextField PriceText;
	@FXML
	private Label WMemberShipType;
	@FXML
	private Label Wduration;
	@FXML
	private Label Wprice;
	@FXML
	private ComboBox<String> ComBox;
	@FXML
	private TextField MembershipTypeText;
	Connection con;

	@FXML
	void handelAddMemberShipType(ActionEvent event) throws ClassNotFoundException, SQLException {
		String embershipType = MembershipTypeText.getText().trim();
		String duration = DurationText.getText().trim();
		String price = PriceText.getText().trim();
		if (embershipType.isEmpty())
			WMemberShipType.setVisible(true);
		if (duration.isEmpty())
			Wduration.setVisible(true);
		if (price.isEmpty())
			Wprice.setVisible(true);
		if (Wprice.isVisible() || Wduration.isVisible() || WMemberShipType.isVisible()) {
			Massege.displayMassage("Add MemberShip Type", "Can Not ADD A new MemberShip Type , please Fill ALL Text");

			return;
		} else {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			String selected = ComBox.getSelectionModel().getSelectedItem();
			duration += " " + selected;
			price += " nis";
			String sql = "insert into membership (membershipType,Duration,price) values ( '" + embershipType + "' ,'"
					+ duration + "' , '" + price + "' );";
			System.out.println(sql);
			DB.ExecuteUpdateStatement(sql);
			con.close();
			clear();
		}
	}

	@FXML
	void handelBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent firstSceneView = FXMLLoader.load((getClass().getResource("../FXML/Design.fxml")));
		Scene scene = new Scene(firstSceneView);
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Wduration.setVisible(false);
		WMemberShipType.setVisible(false);
		Wprice.setVisible(false);
		ComBox.getItems().addAll("Days", "Months", "Years");

	}

	private void clear() {
		DurationText.clear();
		PriceText.clear();
		ComBox.getSelectionModel().clearSelection();
		MembershipTypeText.clear();

	}

	@FXML
	void handleClear(ActionEvent event) {
		clear();
	}

}