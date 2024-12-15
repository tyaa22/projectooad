package model;

import java.sql.SQLException;
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
	private int offerPrice = 0;
	private String itemOfferStatus;
	
	private static Connect connect = Connect.getInstance();
	
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
	
	public ObservableList<Item> getAllItems(String status) {
		ObservableList<Item> items = FXCollections.observableArrayList();
		String query = String.format("SELECT * FROM item WHERE item_status = '%s'", status);
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				String itemID = connect.rs.getString("item_id");
				String itemName = connect.rs.getString("item_name");
				String itemSize = connect.rs.getString("item_size");
				int itemPrice = Integer.parseInt(connect.rs.getString("item_price"));
				String itemCategory = connect.rs.getString("item_category");
				Item currItem = new Item(itemID, itemName, itemSize, itemPrice, itemCategory);
				items.add(currItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public ObservableList<Item> getAllItems() {
		String query = "SELECT * FROM item";
		connect.rs = connect.execQuery(query);
		ObservableList<Item> items = FXCollections.observableArrayList();
		try {
			while(connect.rs.next()) {
				String itemID = connect.rs.getString("item_id");
				String itemName = connect.rs.getString("item_name");
				String itemSize = connect.rs.getString("item_size");
				int itemPrice = connect.rs.getInt("item_price");
				String itemCategory = connect.rs.getString("item_category");
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
						+ itemPrice +"', '"+ itemCategory +"', 'Pending', null, null)";
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
		String query = String.format("DELETE FROM item "+"WHERE item_id = '%s'",  itemId);
		connect.execUpdate(query);
	}
	
	public void approveItem(String itemId) {
		String query = "UPDATE item SET item_status = 'Approved' WHERE item_id = '"+itemId+"'";
		connect.execUpdate(query);
	}
	
	public void offerPrice(String itemId, String userId, int offerPrice) {
		String query = "INSERT INTO offer(user_id, item_id, offer_price) "
				+ "VALUES('"+userId+"', '"+itemId+"', '"+offerPrice+"')";
		connect.execUpdate(query);
	}
	
	public ObservableList<Item> viewOfferItem() {
		String query = "SELECT item.item_id, item.item_name, item.item_price, item.item_size, item.item_category, offer.offer_price"
				+ " FROM offer JOIN item WHERE item.item_id = offer.item_id";
		connect.rs = connect.execQuery(query);
		try {
			ObservableList<Item> items = FXCollections.observableArrayList();
			while(connect.rs.next()) {
				String itemId = connect.rs.getString("item_id");
				String itemName = connect.rs.getString("item_name");
				int itemPrice = connect.rs.getInt("item_price");
				String itemSize = connect.rs.getString("item_size");
				String itemCategory = connect.rs.getString("item_category");
				int offerPrice = connect.rs.getInt("offer_price");
				Item item = new Item(itemId, itemName, itemSize, itemPrice, itemCategory);
				item.setOfferPrice(offerPrice);
				items.add(item);
			}
			return items.isEmpty()? null : items;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getHighestOffer(String itemId) {
		String query = "SELECT offer_price, item_price FROM offer JOIN item "
				+ "ON offer.item_id = item.item_id WHERE item.item_id = '"+itemId+"' ORDER BY offer_price DESC LIMIT 1";
		connect.rs = connect.execQuery(query);
		try {
			if(connect.rs.next()) {
				return connect.rs.getInt("offer_price");
			}
			else return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
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

	public int getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(int offerPrice) {
		this.offerPrice = offerPrice;
	}


}
