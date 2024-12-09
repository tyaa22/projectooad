package controller;

import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.User;
import view.SellerView;
import view.UI;
import view.AdminView;
import view.LoginView;
import view.RegisterView;

public class UserController {
	
	User user;
	Stage stage;
	RegisterView rp;
	LoginView lp;
	
	public UserController(User user, Stage stage) {
		this.user = user;
		this.stage = stage;
		rp = new RegisterView(stage);
		rp.getRegisterBtn().setOnAction(e -> register());
		rp.getGoToLoginBtn().setOnAction(e -> goToLogin());	
	}

	private void goToLogin() {
		lp = new LoginView(stage);
		lp.getLoginBtn().setOnAction(e -> login());
		lp.getGoToRegisterBtn().setOnAction(e -> new UserController(user, stage));
	}

	public void login() {
		String username = lp.getUsernameTF().getText();
		String password = lp.getPasswordTF().getText();
		User searchUser = user.searchUser(username);
		if(searchUser != null) {
			if(searchUser.getPassword().equals(password)) {
				redirectToView(searchUser.getRole());
			}
			else {
				lp.getErrorLbl().setText("Username and password do not match");
			}
		}
		else {
			lp.getErrorLbl().setText("User not found");
		}
	}
	
	
	public void register() {
		String username = rp.getUsernameTF().getText();
		String password = rp.getPasswordTF().getText();
		String phonenumber = rp.getPhoneTF().getText();
		String address = rp.getAddressTF().getText();
		RadioButton rb = (RadioButton) rp.getRoleGroup().getSelectedToggle();
		String role = null;
		if(rb != null) {
			role = rb.getText();
		}
		user.addUser(username, password, phonenumber, address, role);
		redirectToView(role);
//		new ItemController(stage);
	}
	
	public void redirectToView(String role) {
		if(role.equals("Seller")) {
			new ItemController(new SellerView(stage));
		}
		else if(role.equals("Buyer")) {
			System.out.println("Go to BuyerView");
		}
		else {
			new ItemController(new AdminView(stage));
		}
	}

}
