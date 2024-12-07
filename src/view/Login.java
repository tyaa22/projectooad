package view;

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

public class Login implements UI {
	
	Scene scene;
	BorderPane bp;
	GridPane gp;
	VBox container;
	Label titleLbl, usernameLbl, passwordLbl, errorLbl;
	TextField usernameTF;
	PasswordField passwordTF;
	Button loginBtn, goToRegisterBtn;
	HBox btnBox;

	public Login(Stage primaryStage) {
		initialize();
		layout();
		addEvent(primaryStage);
		typography();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login Form");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	@Override
	public void initialize() {
		bp = new BorderPane();
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
		
		scene = new Scene(bp, 500, 250);
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
	public void layout() {
		
		addElement();
		
		bp.setTop(titleLbl);
		bp.setCenter(container);
		bp.setBottom(btnBox);
		
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(btnBox, Pos.CENTER);
		bp.setPadding(new Insets(10));
		btnBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		BorderPane.setMargin(titleLbl, new Insets(0, 0, 10, 0));
		btnBox.setSpacing(10);
		btnBox.setAlignment(Pos.CENTER);
		
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
	}
	
	public void typography() {
		titleLbl.setFont(new Font(20));
	}
	
	public void addEvent(Stage stage) {
		goToRegisterBtn.setOnAction(e -> goToRegister(stage));
	}
	
	public void goToRegister(Stage stage) {
		new Register(stage);
	}

	public Button getLoginBtn() {
		return loginBtn;
	}

	public Label getTitleLbl() {
		return titleLbl;
	}
	
	public TextField getUsernameTF() {
		return usernameTF;
	}

	public PasswordField getPasswordTF() {
		return passwordTF;
	}

	public Label getErrorLbl() {
		return errorLbl;
	}

}
