package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Connect;

public class Item {
	
	private String itemId;
	private String itemName;
	private String itemSize;
	private int itemPrice;
	private String itemCategory;
	private String itemStatus;
	private String itemWishlist;
	private String itemOfferStatus;
	
	static Connect connect = Connect.getInstance();
	
	public Item(String itemId, String itemName, String itemSize, int itemPrice, String itemCategory) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;;
	}
	
	public Item() {
		
	}
	
	private String generateID() {
		String txt = "IT";
		String query = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";
		connect.rs = connect.execQuery(query);
		String newId = null;
		try {
			if(connect.rs.next()) {
				String recentId = connect.rs.getString("item_id");
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
	
	public ObservableList<Item> getAllReviewItems() {
		ObservableList<Item> items = FXCollections.observableArrayList();
		String query = "SELECT * FROM item WHERE item_status = 'Under Review'";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				String itemID = connect.rs.getString("item_id");
				String itemName = connect.rs.getString("item_name");
				String itemSize = connect.rs.getString("item_size");
				int itemPrice = Integer.parseInt(connect.rs.getString("item_price"));
				String itemCategory = connect.rs.getString("item_category");
				String itemStatus = connect.rs.getString("item_status");
				String itemWishList = connect.rs.getString("item_wishlist");
				String itemOfferStatus = connect.rs.getString("item_offer_status");
				Item currItem = new Item(itemID, itemName, itemSize, itemPrice, itemCategory);
				items.add(currItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public void uploadItem(String itemName, String itemSize, int itemPrice, String itemCategory) {
		String itemId = generateID();
		String query = "INSERT INTO item " +
						"VALUES('"+ itemId +"', '"+ itemName +"', '"+ itemSize +"', '"
						+ itemPrice +"', '"+ itemCategory +"', 'Under Review', 'null', 'null')";
		connect.execUpdate(query);
	}
	
	public void editItem(String itemId, String itemName, String itemSize, int itemPrice, String itemCategory) {
		String query = String.format("UPDATE item\n" +
						"SET item_name = '%s', item_size = '%s', item_price = %d, " +
						"item_category = '%s' WHERE item_id = '%s'", itemName, itemSize, itemPrice,
						itemCategory, itemId);
		connect.execUpdate(query);
	}
	
	public void deleteItem(String itemId) {
		String query = String.format("DELETE FROM item\n" +
				 "WHERE item_id = '%s'",  itemId);
		connect.execUpdate(query);
	}
	

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemWishlist() {
		return itemWishlist;
	}

	public void setItemWishlist(String itemWishlist) {
		this.itemWishlist = itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

	public void setItemOfferStatus(String itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}


}
