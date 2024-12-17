package controller;

import javafx.stage.Stage;
import model.User;
import view.SellerView;
import view.AdminView;
import view.BuyerView;

public class UserController {
	
//	private static User user;
	private static Stage stage;
	
	public UserController(Stage stage) {
//		UserController.user = new User();
		UserController.stage = stage;
	}
	
	//mengecek apakah password mengandung mininal satu special character
	private static boolean containSpecialChar(String password) {
		if(password.contains("!") || password.contains("@") || password.contains("#") ||
				password.contains("$")|| password.contains("%") || password.contains("^") ||
				password.contains("&") || password.contains("*")) return true;
		return false;
	}
	
	//mengecek apakah phonenumber mengandung angka semua
	private static boolean isNumber(String phonenumber) {
		try {
			Integer.parseInt(phonenumber.substring(3));
			return true;
			
		} catch (Exception e) {
			return false; 
		}
	}
	
	//validasi user data fields
	public static String checkAccountValidation(String username, String password, String phonenumber, String address, String role) {
		User searchUser = User.searchUser(username);
		if(searchUser != null) return "Username already exist";
		if(username.isEmpty() || username.length() < 3) return "Username must contains at least 3 characters";
		if(password.isEmpty() || password.length() < 8 || !containSpecialChar(password)) return "Password must contains at least 8 characters and include special characters ";
		if(phonenumber.isEmpty() || phonenumber.length() != 12 || 
				!phonenumber.substring(0, 3).equals("+62") || !isNumber(phonenumber)) return "Phone number must contains +62 and additional 9 numbers";
		if(role == null) return "Choose one role";
		if(address.isEmpty()) return "Address cannot be empty";
		return "success";
	}

	public static String login(String username, String password) {
		if(username.isEmpty() || password.isEmpty()) {
			return "Both field must be filled";
		}
		User searchUser = User.searchUser(username);
		if(searchUser != null) {
			if(searchUser.getPassword().equals(password)) {
				redirectToView(searchUser.getUserId(), searchUser.getRole());
			}
			else {
				return "Username and password do not match";
			}
		}
		return "User not found";
	}
	
	
	public static String register(String username, String password, String phonenumber, String address, String role) {
		String errorMsg = checkAccountValidation(username, password, phonenumber, address, role);
		if(errorMsg.equals("success")) {
			User newUser = User.addUser(username, password, phonenumber, address, role);
			redirectToView(newUser.getUserId(), role);
		}
		return errorMsg;
		
	}
	
	public static void redirectToView(String userId, String role) {
		if(role.equals("Seller")) {
			new SellerView(stage, userId);
		}
		else if(role.equals("Buyer")) {
			new BuyerView(stage, userId);
		}
		else {
			new AdminView(stage);
		}
	}

}
