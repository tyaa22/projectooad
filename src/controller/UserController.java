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
	
	public UserController(Stage stage) {
		this.user = new User();
		this.stage = stage;
//		addBtnEvent();
//		stage.setScene(rp.getScene());
//		stage.show();
	}

//	private void goToLogin() {
//		stage.show();
//	}
	
//	private void addBtnEvent() {
//		rp.getRegisterBtn().setOnAction(e -> register());
//		rp.getGoToLoginBtn().setOnAction(e -> stage.setScene(lp.getScene()));	
//		lp.getLoginBtn().setOnAction(e -> login());
//		lp.getGoToRegisterBtn().setOnAction(e -> stage.setScene(rp.getScene()));
//	}
	
	private boolean containSpecialChar(String password) {
		if(password.contains("!") || password.contains("@") || password.contains("#") ||
				password.contains("$")|| password.contains("%") || password.contains("^") ||
				password.contains("&") || password.contains("*")) return true;
		return false;
	}
	
	private boolean isNumber(String phonenumber) {
		try {
			Integer.parseInt(phonenumber.substring(3));
			return true;
			
		} catch (Exception e) {
			return false; 
		}
	}
	
	public String checkAccountValidation(String username, String password, String phonenumber, String address, String role) {
		User searchUser = user.searchUser(username);
		if(searchUser != null) return "Username already exist";
		if(username.isEmpty() || username.length() < 3) return "Username must contains at least 3 characters";
		if(password.isEmpty() || password.length() < 8 || !containSpecialChar(password)) return "Password must contains at least 8 characters and include special characters ";
		if(phonenumber.isEmpty() || phonenumber.length() != 12 || 
				!phonenumber.substring(0, 3).equals("+62") || !isNumber(phonenumber)) return "Phone number must contains +62 and additional 9 numbers";
		if(role == null) return "Choose one role";
		if(address.isEmpty()) return "Address cannot be empty";
		return "success";
	}

	public String login(String username, String password) {
		if(username.isEmpty() || password.isEmpty()) {
			return "Both field must be filled";
		}
		User searchUser = user.searchUser(username);
		if(searchUser != null) {
			if(searchUser.getPassword().equals(password)) {
				redirectToView(searchUser.getRole());
			}
			else {
				return "Username and password do not match";
			}
		}
		return "User not found";
	}
	
	
	public String register(String username, String password, String phonenumber, String address, String role) {
//		String username = rp.getUsernameTF().getText();
//		String password = rp.getPasswordTF().getText();
//		String phonenumber = rp.getPhoneTF().getText();
//		String address = rp.getAddressTF().getText();
//		RadioButton rb = (RadioButton) rp.getRoleGroup().getSelectedToggle();
//		String role = null;
//		if(rb != null) {
//			role = rb.getText();
//		}
		String errorMsg = checkAccountValidation(username, password, phonenumber, address, role);
		if(errorMsg.equals("success")) {
			user.addUser(username, password, phonenumber, address, role);
			redirectToView(role);
		}
		return errorMsg;
		
	}
	
	public void redirectToView(String role) {
		ItemController controller = new ItemController();
		if(role.equals("Seller")) {
			new SellerView(stage, controller);
		}
		else if(role.equals("Buyer")) {
			System.out.println("Go to BuyerView");
		}
		else {
			new AdminView(stage, controller);
		}
	}

}
