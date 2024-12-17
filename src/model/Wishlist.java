package model;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Connect;

public class Wishlist {
	
	private String wishlistId;
	private String itemId;
	private String userId;
	
	private static Connect connect = Connect.getInstance();
	
	public Wishlist(String wishlistId, String itemId, String userId) {
		this.wishlistId = wishlistId;
		this.itemId = itemId;
		this.userId = userId;
	}
	
	public Wishlist() {
		
	}
	
	public static boolean itemAlreadyInWishlist(String userId, String itemId) {
		String query = "SELECT * FROM wishlist WHERE user_id = '"+ userId +"' AND item_id = '"+ itemId +"' LIMIT 1";
		connect.rs = connect.execQuery(query);
		try {
			if(connect.rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static String generateID() {
		String txt = "WL";
		String query = "SELECT wishlist_id FROM wishlist ORDER BY wishlist_id DESC LIMIT 1";
		connect.rs = connect.execQuery(query);
		String newId = null;
		try {
			if(connect.rs.next()) {
				String recentId = connect.rs.getString("wishlist_id");
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
	
	public static void addWishlist(String itemId, String userId) {
		String newId = generateID();
		String query = "INSERT INTO wishlist(wishlist_id, item_id, user_id) VALUES("
				+ "'"+newId+"', '"+itemId+"', '"+userId+"')";
		connect.execUpdate(query);
		
	}
	
	public static void deleteWishlist(String wishlistId) {
		String query = "DELETE FROM wishlist WHERE wishlist_id = '"+wishlistId+"'";
		connect.execUpdate(query);
	}
	
//	public ArrayList<String> getUserWishlist(String userId) {
//		String query = "SELECT item_id FROM wishlist WHERE user_id ='"+ userId +"'";
//		connect.rs = connect.execQuery(query);
//		try {
//			ArrayList<String> itemIds = new ArrayList<String>();
//			while(connect.rs.next()) {
//				itemIds.add(connect.rs.getString("item_id"));
//			}
//			return itemIds;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public static ObservableList<Item> viewWishlist(String userId) {
		String query = "SELECT wishlist.wishlist_id, item.item_id, item.item_name, item.item_price, "
				+ "item.item_size, item.item_category, item.seller_id FROM "
				+ "wishlist JOIN item WHERE item.item_id = wishlist.item_id AND wishlist.user_id = '"+ userId +"'";
		connect.rs = connect.execQuery(query);
		try {
			ObservableList<Item> wishlistItems = FXCollections.observableArrayList();
			while(connect.rs.next()) {
				String wishlistId = connect.rs.getString("wishlist_id");
				String itemId = connect.rs.getString("item_id");
				String itemName = connect.rs.getString("item_name");
				int itemPrice = connect.rs.getInt("item_price");
				String itemSize = connect.rs.getString("item_size");
				String itemCategory = connect.rs.getString("item_category");
				String sellerId = connect.rs.getString("seller_id");
				Item item = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, sellerId);
				item.setItemWishlist(wishlistId);
				wishlistItems.add(item);
			}
			return wishlistItems.isEmpty()? null : wishlistItems;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(String wishlistId) {
		this.wishlistId = wishlistId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
