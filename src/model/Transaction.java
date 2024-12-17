package model;

import java.awt.ItemSelectable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Connect;

public class Transaction {
	
	private String transactionId;
	private String userId;
	private String itemId;
	private Item item;
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
	
	// membuat ID baru untuk Transaction
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
	
	
	// mengambil data transaksi yang pernah dilakukan oleh user
	public static ObservableList<Item> viewHistory(String currUserId) {
		String query = "SELECT * FROM transaction JOIN item ON transaction.item_id = item.item_id WHERE transaction.user_id = '"+currUserId+"'";
		connect.rs = connect.execQuery(query);
		try {
			ObservableList<Item> transactions = FXCollections.observableArrayList();
			while(connect.rs.next()) {
				String transacionId = connect.rs.getString("transaction_id");
				String userId = connect.rs.getString("user_id");
				String itemId = connect.rs.getString("item_id");
				String itemName = connect.rs.getString("item_name");
				int itemPrice = connect.rs.getInt("item_price");
				String itemCategory = connect.rs.getString("item_category");
				String itemSize = connect.rs.getString("item_size");
				String sellerId = connect.rs.getString("seller_id");
				int finalPrice = connect.rs.getInt("final_price");;
				Item newTransaction = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, sellerId);
				newTransaction.setTransactionId(transacionId);
				transactions.add(newTransaction);
			}
			return transactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	//memasukkan data transaksi ke db
	public static String createTransaction(String userId, String itemId, int finalPrice) {
		try {
			String newId = generateID();
			String query = "INSERT INTO transaction VALUES('"+ newId +"', '"+ userId +"',"
					+ " '"+ itemId +"', '"+finalPrice+"')";
			connect.execUpdate(query);
			//jika berhasil maka akan return success message
			return "Item purchased";
		} catch (Exception e) {
			//jika tidak akan return failure message
			return "Cannot create transaction";
		}
		
	}

	public String getUserId() {
		return userId;
	}

	public String getItemId() {
		return itemId;
	}


	public int getFinalPrice() {
		return finalPrice;
	}


	public String getTransactionId() {
		return transactionId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


}
