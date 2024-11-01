package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;

public class AdministratorController implements Initializable {

	@FXML
	private TableView<Administrator> ATableView;

	@FXML
	private TableColumn<Administrator, String> EmailCol;

	@FXML
	private TableColumn<Administrator, String> FNameCol;

	@FXML
	private TableColumn<Administrator, String> GenderCol;

	@FXML
	private TableColumn<Administrator, String> LNameCol;

	@FXML
	private TableColumn<Administrator, String> PhoneCol;

	@FXML
	private Button searchButton;

	@FXML
	private TextField searchTextField;

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;

	@FXML
	private Button sortByButton;

	@FXML
	private ComboBox<String> sortComBox;
	@FXML
	private Label WrongSearchLabel;
	@FXML
	private Label WrongSrotLabel;
	private ObservableList<Administrator> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	Trainee trainee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			sortComBox.getItems().addAll("FName", "LName", "GENDER", "phoneNumber");
//			fillComboBox();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		ATableView.setEditable(true);
	}

	private void fillComboBox() {
		String colName[] = { "FName", "LName", "GENDER", "phoneNumber" };

		sortComBox = new ComboBox<>(FXCollections.observableArrayList(colName));
	}

	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();

		PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		FNameCol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		LNameCol.setCellValueFactory(new PropertyValueFactory<>("Lname"));
		GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
//		EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

	}

	private void refreshTable() {
		dataList.clear();
		try {

			String sql = "SELECT FNAME , LNAME , GENDER  , phoneNumber "
					+ "from administrator a  , phoneNumberAdministrator p "
					+ "where a.administratorId = p.administratorId;";
			System.out.println(sql);
			rs = con.createStatement().executeQuery(sql);
			while (rs.next())
				dataList.add(new Administrator(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
//				dataList.add(new Administrator(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
//						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ATableView.setItems(dataList);

	}

	@FXML
	void handelSearch(ActionEvent event) {
		WrongSearchLabel.setVisible(false);
		System.out.println("Inside Search ");
		if (event.getSource() == searchButton) {
			String fName = firstNameTextField.getText().trim();
			String lName = lastNameTextField.getText().trim();
			System.out.println(fName + " " + lName);

			if (fName.isEmpty() || lName.isEmpty()) {
				System.out.print("Can Not Search");
				WrongSearchLabel.setVisible(true);
			} else {
				dataList.clear();
				try {

//					String sql = "SELECT FNAME , LNAME , GENDER  , phoneNumber "
//							+ "from administrator a  , phoneNumberAdministrator p "
//							+ "where a.administratorId = p.administratorId and " + "fName = '" + fName
//							+ "' and lName = '" + lName + "' ;";
					String sql = "SELECT FNAME , LNAME , GENDER  , phoneNumber from administrator a  , phoneNumberAdministrator p"
							+ " where a.administratorId = p.administratorId " + " and Fname like '%" + fName + "%' and"
							+ " Lname  like '%" + lName + "%' ;";
					System.out.println(sql);
					rs = con.createStatement().executeQuery(sql);
					while (rs.next())
						dataList.add(
								new Administrator(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				} catch (SQLException e) {
					e.printStackTrace();
				}

				ATableView.setItems(dataList);

			}
		}

	}

	@FXML
	void handelSort(ActionEvent event) {
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

				String sql = "SELECT FNAME , LNAME , GENDER  , phoneNumber "
						+ "from administrator a  , phoneNumberAdministrator p "
						+ "where a.administratorId = p.administratorId order by " + SortText + " ;";
				System.out.println(sql);
				System.out.println(sql);
				rs = con.createStatement().executeQuery(sql);
				while (rs.next())
					dataList.add(new Administrator(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ATableView.setItems(dataList);

		}

	}

}
