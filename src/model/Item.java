package model;

import java.sql.ResultSet;

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
	
//	public String generateID() {
//		ResultSet rs = connect.execQuery(query);
//		return "IT";
//	}
	
	public void uploadItem(String itemName, String itemSize, int itemPrice, String itemCategory) {
//		String itemId = generateID();
		String query = "INSERT INTO user " +
						"VALUES('"+ itemId +"', '"+ itemName +"', '"+ itemSize +"', '"
						+ itemPrice +"', '"+ itemCategory +"', 'Under Review', 'null', 'null')";
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
