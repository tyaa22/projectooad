package model;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Connect;

public class Transaction {
	
	private String transactionId;
	private String userId;
	private String itemId;
	private int finalPrice;
	
	private static Connect connect = Connect.getInstance();
	
	public Transaction(String transactionId, String userId, String itemId, int finalPrice) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.itemId = itemId;
		this.finalPrice	= finalPrice;
	}
	
	public Transaction() {
		
	}
	
	private static String generateID() {
		String txt = "TR";
		String query = "SELECT transaction_id FROM transaction ORDER BY transaction_id DESC LIMIT 1";
		connect.rs = connect.execQuery(query);
		String newId = null;
		try {
			if(connect.rs.next()) {
				String recentId = connect.rs.getString("transaction_id");
				int length = recentId.length();
				int num = Integer.parseInt(recentId.substring(2, length));
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
	
	
	public static ObservableList<Transaction> viewHistory(String currUserId) {
		String query = "SELECT * FROM transaction WHERE user_id = '"+currUserId+"'";
		connect.rs = connect.execQuery(query);
		try {
			ObservableList<Transaction> transactions = FXCollections.observableArrayList();
			while(connect.rs.next()) {
				String transacionId = connect.rs.getString("transaction_id");
				String userId = connect.rs.getString("user_id");
				String itemId = connect.rs.getString("item_id");
				int finalPrice = connect.rs.getInt("final_price");
				transactions.add(new Transaction(transacionId, userId, itemId, finalPrice));
			}
			return transactions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
	
	public static void createTransaction(String userId, String itemId, int finalPrice) {
		String newId = generateID();
		String query = "INSERT INTO transaction VALUES('"+ newId +"', '"+ userId +"',"
				+ " '"+ itemId +"', '"+finalPrice+"')";
		connect.execUpdate(query);
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


}
