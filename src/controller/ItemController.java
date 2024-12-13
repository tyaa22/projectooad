package controller;

import javafx.collections.ObservableList;
import model.Item;

public class ItemController {
	
	Item item;
	
	public ItemController() {
		this.item = new Item();
	}

	
	public ObservableList<Item> getAllItems() {
		return item.getAllItems();
	}
	
	public ObservableList<Item> getAllItems(String status) {
		return item.getAllItems(status);
	}
	
	private boolean isNumber(String price) {
		try {
			if(Integer.parseInt(price) <= 0) return false;
			
		} catch (Exception e) {
			System.out.println(price);
			System.out.println("NumberException");
			return false; 
		}
		return true;
	}
	
	public String checkItemValidation(String itemName, String itemPrice, String itemSize, String itemCategory) {
		if(itemName.isEmpty() || itemPrice.isEmpty() || itemSize.isEmpty() || itemCategory.isEmpty()) return "All fields must be filled";
		if(itemName.length() < 3) return "Item name cannot be less than 3 characters long";
		if(!isNumber(itemPrice)) return "Item price cannot be equals to 0 or contains non-numeric values";
		if(itemCategory.length() < 3) return "Item category cannot be less than 3 characters long";
		return "Success";
	}
	
	//Seller Use Case
	public String uploadItem(String itemName, String itemPrice, String itemSize, String itemCategory) {
		if(checkItemValidation(itemName, itemPrice, itemSize, itemCategory).equals("Success")) {
			item.uploadItem(itemName, itemSize, Integer.parseInt(itemPrice), itemCategory);
			return "Item successfully uploaded";
		}
		return checkItemValidation(itemName, itemPrice, itemSize, itemCategory);
	}
	
	public String editItem(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory) {
		if(checkItemValidation(itemName, itemPrice, itemSize, itemCategory).equals("Success")) {
			item.editItem(itemId, itemName, itemSize, Integer.parseInt(itemPrice), itemCategory);
			return "Item successfully edited";
		}
		return checkItemValidation(itemName, itemPrice, itemSize, itemCategory);
	}
	
	public void deleteItem(String itemId) {
		item.deleteItem(itemId);
	}
	
	
	//Admin Use Case
	public void approveItem(String itemId) {
		item.approveItem(itemId);
	}
	
}
