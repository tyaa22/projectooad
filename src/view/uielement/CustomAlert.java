package view.uielement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CustomAlert extends Alert {

	public CustomAlert(AlertType alertType, String msg) {
		super(alertType);
		this.setContentText(msg);
		this.setHeaderText(null);
		
		this.showAndWait();
	}



}
