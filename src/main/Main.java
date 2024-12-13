package main;

import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import view.RegisterView;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		UserController controller =  new UserController(primaryStage);
		new RegisterView(primaryStage, controller);
	}
}
