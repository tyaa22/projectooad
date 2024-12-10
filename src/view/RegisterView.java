package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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

public class RegisterView implements UI {	
	Scene scene;
	BorderPane bp;
	GridPane gp;
	VBox container;
	HBox radioBox, btnBox;
	Label titleLbl, usernameLbl, passwordLbl, phoneLbl, addressLbl, roleLbl, errorLbl;
	TextField usernameTF, phoneTF, addressTF;
	ToggleGroup roleGroup;
	RadioButton sellerRB, buyerRB, adminRB;
	PasswordField passwordTF;
	Button registerBtn;
	Button goToLoginBtn;

	public RegisterView(Stage stage) {
		initialize();
		layout();
		typography();
//		stage.setScene(scene);
		stage.setTitle("Register or Login");
		stage.setResizable(false);
//		stage.show();
		
	}
	@Override
	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		container = new VBox();
		
		titleLbl = new Label("Register Page");
		usernameLbl = new Label("Username:");
		passwordLbl = new Label("Password:");
		phoneLbl = new Label("Phone Number:");
		addressLbl = new Label("Address:");
		roleLbl = new Label("Role:");
		errorLbl = new Label();
		
		usernameTF = new TextField();
		phoneTF = new TextField("+62");
		addressTF = new TextField();
		passwordTF = new PasswordField();
		roleGroup = new ToggleGroup();
		sellerRB = new RadioButton("Seller");
		buyerRB = new RadioButton("Buyer");
		adminRB = new RadioButton("Admin");
		sellerRB.setToggleGroup(roleGroup);
		buyerRB.setToggleGroup(roleGroup);
		adminRB.setToggleGroup(roleGroup);
		radioBox = new HBox();
		btnBox = new HBox();
		
		registerBtn = new Button("Register");
		goToLoginBtn = new Button("Login Here");
		
		scene = new Scene(bp, 500, 260);
	}
	
	@Override
	public void addElement() {
		gp.add(usernameLbl, 0, 0);
		gp.add(passwordLbl, 0, 1);
		gp.add(phoneLbl, 0, 2);
		gp.add(addressLbl, 0, 3);
		gp.add(roleLbl, 0, 4);
		
		gp.add(usernameTF, 1, 0);
		gp.add(passwordTF, 1, 1);
		gp.add(phoneTF, 1, 2);
		gp.add(addressTF, 1, 3);
		gp.add(radioBox, 1, 4);
		
		container.getChildren().addAll(gp, errorLbl);
		
		btnBox.getChildren().addAll(registerBtn, goToLoginBtn);
		radioBox.getChildren().addAll(sellerRB, buyerRB, adminRB);
	}
	
	@Override
	public void layout() {	
		addElement();
		
		bp.setTop(titleLbl);
		bp.setCenter(container);
		bp.setBottom(btnBox);
		
		btnBox.setSpacing(10);
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(btnBox, Pos.CENTER);
		bp.setPadding(new Insets(10));
//		gp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		BorderPane.setMargin(titleLbl, new Insets(0, 0, 10, 0));
		btnBox.setAlignment(Pos.CENTER);
	
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
		
		usernameLbl.setMinWidth(100);
//		usernameLbl.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		passwordLbl.setMinWidth(100);
		phoneLbl.setMinWidth(100);
		addressLbl.setMinWidth(100);
		roleLbl.setMinWidth(100);
		
		radioBox.setSpacing(10);
		
		usernameTF.setMaxWidth(200);
		usernameTF.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		passwordTF.setMaxWidth(300);
		phoneTF.setMaxWidth(300);
		addressTF.setMaxWidth(300);

	}
	
	public void typography() {
		titleLbl.setFont(new Font(20));
	}

	public TextField getUsernameTF() {
		return usernameTF;
	}

	public TextField getPhoneTF() {
		return phoneTF;
	}

	public TextField getAddressTF() {
		return addressTF;
	}

	public ToggleGroup getRoleGroup() {
		return roleGroup;
	}

	public PasswordField getPasswordTF() {
		return passwordTF;
	}

	public Button getRegisterBtn() {
		return registerBtn;
	}

	public Button getGoToLoginBtn() {
		return goToLoginBtn;
	}
	public Scene getScene() {
		return scene;
	}
	public Label getErrorLbl() {
		return errorLbl;
	}
	
}
