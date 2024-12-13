package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.Item;
import view.AdminView;
import view.BuyerView;
import view.SellerView;
import view.UI;

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
	
	//Seller Use Case
	
	public void uploadItem(String itemName, String itemSize, int itemPrice, String itemCategory) {
		item.uploadItem(itemName, itemSize, itemPrice, itemCategory);
	}

	
//	public void editOrDeleteItem() {
//		sellerView.getEditBtn().setVisible(true);
//		sellerView.getDeleteBtn().setVisible(true);
//		sellerView.getUploadBtn().setDisable(true);
//		TableSelectionModel<Item> tableSelectionModel = sellerView.getItemsList().getSelectionModel();
//		tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
//		Item selectedItem = tableSelectionModel.getSelectedItem();
//		
//		if(selectedItem != null) {
//			sellerView.getItemNameTF().setText(selectedItem.getItemName());
//			sellerView.getItemSizeTF().setText(selectedItem.getItemSize());
//			sellerView.getItemCategoryTF().setText(selectedItem.getItemCategory());
//			sellerView.getItemPriceTF().setText(Integer.toString(selectedItem.getItemPrice()));
//			sellerView.getEditBtn().setOnAction(e -> editItem(selectedItem.getItemId()));
//			sellerView.getDeleteBtn().setOnAction(e -> deleteItem(selectedItem.getItemId()));
//		}
//		else {
//			System.out.println("Please choose a filled row");
//		}		
//	}
	
	public void editItem(String itemId, String itemName, String itemSize, int itemPrice, String itemCategory) {
		item.editItem(itemId, itemName, itemSize, itemPrice, itemCategory);
	}
	
	public void deleteItem(String itemId) {
		item.deleteItem(itemId);
	}
	
	
	//Admin Use Case
	
	
//	private void approveOrDeclineItem() {
//		TableSelectionModel<Item> tableSelectionModel = adminView.getItemsList().getSelectionModel();
//		tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
//		Item selectedItem = tableSelectionModel.getSelectedItem();
//		
//		if(selectedItem != null) {
//			adminView.setItemNameTxt(selectedItem.getItemName());
//			adminView.setItemSizeTxt(selectedItem.getItemSize());
//			adminView.setItemPriceTxt(Integer.toString(selectedItem.getItemPrice()));
//			adminView.setItemCategoryTxt(selectedItem.getItemCategory());
//			adminView.getApproveBtn().setOnAction(e -> approveItem(selectedItem.getItemId()));
//			adminView.getDeclineBtn().setOnAction(e -> declineItem(selectedItem.getItemId()));
//		}
//		else {
//			System.out.println("Please choose a filled row");
//		}		
//	}
	
	public void approveItem(String itemId) {
		item.approveItem(itemId);
	}
	
}
