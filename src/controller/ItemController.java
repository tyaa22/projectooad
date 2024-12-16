package controller;

import javafx.collections.ObservableList;
import model.Item;

public class ItemController {
	
//	private static Item item;
	
	public ItemController() {
//		ItemController.item = new Item();
	}

	
	public static ObservableList<Item> getAllItems() {
		return Item.getAllItems();
	}
	
	public static ObservableList<Item> getAllItems(String status) {
		return Item.getAllItems(status);
	}
	
	private static boolean isNumber(String price) {
		try {
			if(Integer.parseInt(price) <= 0) return false;
			
		} catch (Exception e) {
			return false; 
		}
		return true;
	}
	
	public static String checkItemValidation(String itemName, String itemPrice, String itemSize, String itemCategory) {
		if(itemName.isEmpty() || itemPrice.isEmpty() || itemSize.isEmpty() || itemCategory.isEmpty()) return "All fields must be filled";
		if(itemName.length() < 3) return "Item name cannot be less than 3 characters long";
		if(!isNumber(itemPrice)) return "Item price cannot be equals to 0 or contains non-numeric values";
		if(itemCategory.length() < 3) return "Item category cannot be less than 3 characters long";
		return "Success";
	}
	
	//Seller Use Case
	public static String uploadItem(String itemName, String itemPrice, String itemSize, String itemCategory) {
		if(checkItemValidation(itemName, itemPrice, itemSize, itemCategory).equals("Success")) {
			Item.uploadItem(itemName, itemSize, Integer.parseInt(itemPrice), itemCategory);
			return "Item successfully uploaded";
		}
		return checkItemValidation(itemName, itemPrice, itemSize, itemCategory);
	}
	
	public static String editItem(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory) {
		if(checkItemValidation(itemName, itemPrice, itemSize, itemCategory).equals("Success")) {
			Item.editItem(itemId, itemName, itemSize, Integer.parseInt(itemPrice), itemCategory);
			return "Item successfully edited";
		}
		return checkItemValidation(itemName, itemPrice, itemSize, itemCategory);
	}
	
	public static void deleteItem(String itemId) {
		Item.deleteItem(itemId);
	}
	
	
	//Admin Use Case
	public static void approveItem(String itemId) {
		Item.approveItem(itemId);
	}
	
	//Buyer Use Case
	
	public static ObservableList<Item> viewOfferItem() {
		return Item.viewOfferItem();
	}
	
	public static String offerPrice(String userId, String itemId, String offerPrice) {
		if(offerPrice.isEmpty()) return "Offer price cannot be empty";
		if(!isNumber(offerPrice)) return "Price must contains numbers and bigger than zero";
		int offerPriceInt = Integer.parseInt(offerPrice);
		if(offerPriceInt <= Item.getHighestOffer(itemId)) return "Offer price must be bigger than " + Item.getHighestOffer(itemId);
		
		Item.offerPrice(itemId, userId, offerPriceInt);
		return "Success";
	}
	
	public static void acceptOffer(String itemId, String userId) {
		Item.deleteOffer(itemId, userId);
	}
	
	public static void deleteOffer(String itemId, String userId) {
		Item.deleteOffer(itemId, userId);
	}
	
}
