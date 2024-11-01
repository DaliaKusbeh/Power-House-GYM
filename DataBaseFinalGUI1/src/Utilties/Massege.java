package Utilties;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Massege {
	public static void displayMassage(String title, String massage) {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);

		String styleBt = "-fx-background-color: #ffffff;"
				+ "-fx-border-width: 1; -fx-border-color: #000000;-fx-font-size:18;"
				+ "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; ";

		String styleHoverBt = "-fx-background-color: #000000; " + "-fx-font-size:18;"
				+ "-fx-border-width: 1; -fx-border-color: #000000;-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman'; ";

		Label lbl = new Label(massage);
		lbl.setStyle("-fx-text-fill:#000000; -fx-background-color:#ffffff; -fx-font-size:17;"
				+ " -fx-font-family: 'Arial';");
		lbl.setAlignment(Pos.CENTER);

		ImageView imgWarning = new ImageView(new Image("icon/warning1.png"));
		imgWarning.setFitWidth(32);
		imgWarning.setFitHeight(32);

		HBox hBox = new HBox(8);
		hBox.setPadding(new Insets(5, 5, 5, 5));
		hBox.setAlignment(Pos.CENTER);
		hBox.setStyle("-fx-background-color: #ffffff;");
		hBox.getChildren().addAll(imgWarning, lbl);

		Button closeButton = new Button("OK");
		closeButton.setMinWidth(80);
		closeButton.setStyle(styleBt);

		closeButton.setOnMouseEntered(e -> {
			closeButton.setStyle(styleHoverBt);
		});
		closeButton.setOnMouseExited(e -> {
			closeButton.setStyle(styleBt);
		});
		closeButton.setOnAction(e -> window.close());

		VBox vBox = new VBox(12);
		vBox.getChildren().addAll(hBox, closeButton);
		vBox.setAlignment(Pos.CENTER);
		vBox.setStyle("-fx-background-color: #ffffff;");
		vBox.setMinWidth(420);
		vBox.setMinHeight(120);

		Scene scene = new Scene(vBox);

		window.setScene(scene);
		window.setResizable(false);
		window.show();
	}

}
