package view;

import javafx.stage.Stage;

public interface UI {
	
	public void initialize();
	public void addElement();
	public void setLayout();
	public void addEvents(Stage stage);

}
