package model;

import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class User {
	
	private String userId;
	private String username;
	private String password;
	private String phoneNumber;
	private String address;
	private String role;
	
	static Connect connect = Connect.getInstance();
	
	
	public User(String userId, String username, String password, String phoneNumber, String address, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}
	
	public User() {
		
	}
	
	private String generateID(String userRole) {
		String txt;
		if(userRole.equals("Buyer")) txt = "BY";
		else if(userRole.equals("Seller")) txt = "SL";
		else txt = "AD";
		String query = "SELECT user_id FROM user WHERE user_id = '"+txt+"' ORDER BY user_id DESC LIMIT 1";
		connect.rs = connect.execQuery(query);
		String newId = null;
		try {
			if(connect.rs.next()) {
				String recentUserId = connect.rs.getString("user_id");
				int length = recentUserId.length();
				int num = Integer.parseInt(recentUserId.substring(2, length));
				num++;
				newId = txt + Integer.toString(num);
			}
			else {
				newId = txt + "1";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newId;
	}
	
	public void addUser(String username, String password, String phonenumber, String address, String role) {
		String userId = generateID(role);
		String query = "INSERT INTO user " +
						"VALUES('"+ userId +"', '"+ username +"', '"+ password +"', '"
						+ phonenumber +"', '"+ address +"', '"+ role +"')";
		connect.execUpdate(query);
	}
	
	public void addUser(User user) {
		String userId = generateID(role);
		String query = "INSERT INTO user " +
				"VALUES('"+ userId +"', '"+ user.getUsername() +"', '"+ user.getPassword() +"', '"
				+ user.getPhoneNumber() +"', '"+ user.getAddress() +"', '"+ user.getRole() +"')";
		connect.execUpdate(query);
	}
	
	
	public User searchUser(String searchUsername) {
		User searchUser = null;
		String query = "SELECT * FROM user WHERE username='"+searchUsername+"'";
		connect.rs = connect.execQuery(query);
		try {
			if(connect.rs.next()) {
				String userId = connect.rs.getString("user_id");
				String username = connect.rs.getString("username");
				String password = connect.rs.getString("password");
				String phonenumber = connect.rs.getString("phonenumber");
				String address = connect.rs.getString("address");
				String role = connect.rs.getString("role");
				searchUser = new User(userId, username, password, phonenumber, address, role);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchUser;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	

}
