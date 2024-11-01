package controllers;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;

public class MemperShipTableViewController implements Initializable {

	@FXML
	private TableView<Mempership> TableMemper;

	@FXML
	private TableColumn<Mempership, Integer> membershipId;

	@FXML
	private TableColumn<Mempership, String> membershipType;

	@FXML
	private TableColumn<Mempership, String> statuss;

	@FXML
	private TableColumn<Mempership, Integer> traineeId;

	@FXML
	private Button DeleteButton;

	@FXML
	private Button InsertButton;

	@FXML
	private Button UpdateButton;

	@FXML
	private Button ExitButton;

	@FXML
	private Button searchButton;

	@FXML
	private TextField serachTextField;

	@FXML
	private TextField MemperIdTextField;

	@FXML
	private TextField TraineeIdTextField;

	@FXML
	private TextField MemperTypeTextField;

	@FXML
	private TextField MemperStutesTextField;

	private ObservableList<Mempership> dataList = FXCollections.observableArrayList();
	Connection con;
	ResultSet rs;
	PreparedStatement pst;
	String query = null;
	Mempership schedualee = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadDate();
			updateValue();
			DeleteButton.disableProperty().bind(Bindings.isEmpty(TableMemper.getSelectionModel().getSelectedItems()));
			UpdateButton.disableProperty().bind(Bindings.isEmpty(TableMemper.getSelectionModel().getSelectedItems()));

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableMemper.setEditable(true);
	}

	@FXML
	private void updateValue() throws ClassNotFoundException, SQLException {

		Mempership sch = getMemper();
		if (sch != null) {
			dataList.add(sch);
			updateType();
			updateStutes();
		}

	}

	private boolean updateType() throws ClassNotFoundException, SQLException {
		String newType = MemperTypeTextField.getText().trim();
		if (newType.equalsIgnoreCase("Monthly") || newType.equalsIgnoreCase("Annual")) {
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "update powerhouse_gym.membership set membershipType = '" + newType + "' where membershipId = "
					+ (MemperIdTextField.getText()) + ";"; // this
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

	private void updateStutes() throws ClassNotFoundException, SQLException {
		String newDate = MemperStutesTextField.getText().trim();
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Inside Date");

		String sql = "update powerhouse_gym.membership set statuss = ' " + newDate + "' where membershipId = "
				+ (MemperIdTextField.getText()) + ";"; // this
		System.out.println(sql);
		con = a.connectDB();
		stmt = con.createStatement();
		DB.ExecuteUpdateStatement(sql);
		stmt.close();
		con.close();
	}

	@FXML
	private void loadDate() throws ClassNotFoundException, SQLException {
		DBInitializing DB = new DBInitializing(); // connecting to database
		DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
		con = a.connectDB();
		refreshTable();
		membershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
		traineeId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
		membershipType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
		statuss.setCellValueFactory(new PropertyValueFactory<>("statuss"));
	}

	@FXML
	private void refreshTable() {
		dataList.clear();
		try {
			rs = con.createStatement().executeQuery("select * from membership");
			while (rs.next())
				dataList.add(new Mempership(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
						rs.getString(3), rs.getString(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		TableMemper.setItems(dataList);
	}

	@FXML
	void handleAdd(ActionEvent event) {
		Mempership tra = getMemper();
		if (tra != null) {
			dataList.add(tra);
			insertData(tra);

		}

	}

	private Mempership getMemper() {
		Mempership m = null;
		try {
			Integer memperId = Integer.valueOf(MemperIdTextField.getText().trim());
			int traineeId = Integer.valueOf(TraineeIdTextField.getText().trim());
			String memperType = MemperTypeTextField.getText().trim();
			String memperStutes = MemperStutesTextField.getText().trim();
			m = new Mempership(memperId, traineeId, memperType, memperStutes);

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return m;
	}

	private void insertData(Mempership rc) {
		try {
			System.out.println("Insert into membership (membershipId,traineeId,membershipType,statuss) values" + "("
					+ rc.getMembershipId() + "," + rc.getTraineeId() + ",'" + rc.getMembershipType() + "','"
					+ rc.getStatuss() + "');");
			DBInitializing DB = new DBInitializing(); // connecting to database
			DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
			con = a.connectDB();
			ExecuteStatement("Insert into membership (membershipId,traineeId,membershipType,statuss) values" + "("
					+ rc.getMembershipId() + "," + rc.getTraineeId() + ",'" + rc.getMembershipType() + "','"
					+ rc.getStatuss() + "');");

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

	@FXML
	void handleClear(ActionEvent event) {
		clear();
	}

	public void clear() {
		MemperIdTextField.clear();
		TraineeIdTextField.clear();
		MemperTypeTextField.clear();
		MemperStutesTextField.clear();

	}

	@FXML
	void handleDelete(ActionEvent event) {
		ObservableList<Mempership> selectedRows = TableMemper.getSelectionModel().getSelectedItems();
		ArrayList<Mempership> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			TableMemper.getItems().remove(row);
			deleteRow(row);
			TableMemper.refresh();
		});

	}

	private void deleteRow(Mempership row) {
		try {
			System.out.println("delete from membership  where membershipId =" + row.getMembershipId() + ";");
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
		updateStutes();
		loadDate();
		clear();

	}

	@FXML
	void updateView(MouseEvent event) {
		Mempership mem = TableMemper.getSelectionModel().getSelectedItem();

		MemperIdTextField.setText(String.format("%d", mem.getMembershipId()));
		TraineeIdTextField.setText(String.format("%d", mem.getTraineeId()));
		MemperTypeTextField.setText(mem.getMembershipType());
		MemperStutesTextField.setText(mem.getStatuss());

	}

	@FXML
	void search(ActionEvent event) {
		if (event.getSource() == searchButton) {
			FilteredList<Mempership> filteredData = new FilteredList<>(dataList, b -> true);
			serachTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(mem -> {
					// If filter text is empty, display all persons.

					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					// Compare first name and last name of every person with filter text.
					String lowerCaseFilter = newValue.toLowerCase();
					if (String.valueOf(mem.getMembershipId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(mem.getTraineeId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else
						return false;

				});
			});

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Mempership> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(TableMemper.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			TableMemper.setItems(sortedData);

		}

	}

}
