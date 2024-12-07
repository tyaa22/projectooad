package main;

import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Register;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new UserController(primaryStage);
	}
}
