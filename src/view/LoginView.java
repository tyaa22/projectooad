package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView extends BorderPane implements UI {
	
	Scene scene;
//	BorderPane bp;
	GridPane gp;
	VBox container;
	Label titleLbl, usernameLbl, passwordLbl, errorLbl;
	TextField usernameTF;
	PasswordField passwordTF;
	Button loginBtn, goToRegisterBtn;
	HBox btnBox;
	UserController controller;
//	Stage stage;

	public LoginView(Stage stage, UserController controller) {
		this.controller = controller;
//		this.stage = stage;
		initialize();
		setLayout();
		addEvents(stage);
		typography();
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.setResizable(false);
		stage.show();
		
	}
	
	@Override
	public void initialize() {
//		bp = new BorderPane();
		gp = new GridPane();
		container = new VBox();
		
		titleLbl = new Label("Login");
		usernameLbl = new Label("Username:");
		passwordLbl = new Label("Password:");
		errorLbl = new Label();
		usernameTF = new TextField();
		passwordTF = new PasswordField();
		
		loginBtn = new Button("Login");
		goToRegisterBtn = new Button("Register Here");
		
		btnBox = new HBox();
		
		scene = new Scene(this, 500, 250);
	}
	
	@Override
	public void addElement() {
		
		gp.add(usernameLbl, 0, 0);
		gp.add(passwordLbl, 0, 1);
		gp.add(errorLbl, 0, 2);
		
		gp.add(usernameTF, 1, 0);
		gp.add(passwordTF, 1, 1);
		
		container.getChildren().addAll(gp, errorLbl);
		
		btnBox.getChildren().addAll(loginBtn, goToRegisterBtn);
		
	}

	@Override
	public void setLayout() {
		
		addElement();
		
		this.setTop(titleLbl);
		this.setCenter(container);
		this.setBottom(btnBox);
		
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(btnBox, Pos.CENTER);
		this.setPadding(new Insets(10));
//		btnBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		BorderPane.setMargin(titleLbl, new Insets(0, 0, 10, 0));
		btnBox.setSpacing(10);
		btnBox.setAlignment(Pos.CENTER);
		
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
	}
	
	@Override
	public void addEvents(Stage stage) {
		loginBtn.setOnAction(e -> {
			String username = usernameTF.getText();
			String password = passwordTF.getText();
			try {
				String msg = controller.login(username, password);
				errorLbl.setText(msg);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		});
		goToRegisterBtn.setOnAction(e -> {
			new RegisterView(stage, controller);
		});
	}
	
	public void typography() {
		titleLbl.setFont(new Font(20));
	}

}
