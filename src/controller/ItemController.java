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
	SellerView sellerView;
	AdminView adminView;
	BuyerView buyerView;
	ObservableList<Item> data;
	
	public ItemController(UI view) {
		this.item = new Item();
		this.data = FXCollections.observableArrayList();
		initView(view);
	}
	
	public void initView(UI view) {
		if(view instanceof SellerView) {
			this.sellerView = (SellerView) view;
			this.data = item.getAllItems();
			sellerView.getItemsList().setItems(data);
			sellerView.getUploadItemBtn().setOnAction(e -> startUploadItem());
			sellerView.getUploadBtn().setOnAction(e -> uploadItem());
			sellerView.getItemsList().setOnMouseClicked(e -> editOrDeleteItem());
		}
		else if(view instanceof AdminView) {
			this.adminView = (AdminView) view;
			this.data = item.getAllItems("Under Review");
			adminView.getItemsList().setItems(data);
			adminView.getItemsList().setOnMouseClicked(e -> approveOrDeclineItem());
		}
		else {
			this.buyerView = (BuyerView) view;
			this.data = item.getAllItems("Approved");
		}
	}
	
	//Seller Use Case
	
	public void uploadItem() {
		String itemName = sellerView.getItemNameTF().getText();
		String itemSize = sellerView.getItemSizeTF().getText();
		int itemPrice = Integer.parseInt(sellerView.getItemPriceTF().getText());
		String itemCategory = sellerView.getItemCategoryTF().getText();
		item.uploadItem(itemName, itemSize, itemPrice, itemCategory);
		data.setAll(item.getAllItems());
		sellerView.clearTextField();
	}
	
	public void startUploadItem() {
		sellerView.clearTextField();
		sellerView.getDeleteBtn().setDisable(true);
		sellerView.getEditBtn().setDisable(true);
		sellerView.getUploadBtn().setDisable(false);
			
	}
	
	private void getItemData() {
		item.setItemName(sellerView.getItemNameTF().getText());
		item.setItemSize(sellerView.getItemSizeTF().getText());
		item.setItemPrice(Integer.parseInt(sellerView.getItemPriceTF().getText()));
		item.setItemCategory(sellerView.getItemCategoryTF().getText());
	}
	
	public void editOrDeleteItem() {
		sellerView.getEditBtn().setVisible(true);
		sellerView.getDeleteBtn().setVisible(true);
		sellerView.getUploadBtn().setDisable(true);
		TableSelectionModel<Item> tableSelectionModel = sellerView.getItemsList().getSelectionModel();
		tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
		Item selectedItem = tableSelectionModel.getSelectedItem();
		
		if(selectedItem != null) {
			sellerView.getItemNameTF().setText(selectedItem.getItemName());
			sellerView.getItemSizeTF().setText(selectedItem.getItemSize());
			sellerView.getItemCategoryTF().setText(selectedItem.getItemCategory());
			sellerView.getItemPriceTF().setText(Integer.toString(selectedItem.getItemPrice()));
			sellerView.getEditBtn().setOnAction(e -> editItem(selectedItem.getItemId()));
			sellerView.getDeleteBtn().setOnAction(e -> deleteItem(selectedItem.getItemId()));
		}
		else {
			System.out.println("Please choose a filled row");
		}		
	}
	
	private void editItem(String itemId) {
//		String itemName = sellerView.getItemNameTF().getText();
//		String itemSize = sellerView.getItemSizeTF().getText();
//		int itemPrice = Integer.parseInt(sellerView.getItemPriceTF().getText());
//		String itemCategory = sellerView.getItemCategoryTF().getText();
		getItemData();
		item.editItem(itemId, item.getItemName(), item.getItemSize(), item.getItemPrice(), item.getItemCategory());
		data.setAll(item.getAllItems());
		sellerView.clearTextField();
	}
	
	private void deleteItem(String itemId) {
		item.deleteItem(itemId);
		data.setAll(item.getAllItems());
		sellerView.clearTextField();
	}
	
	
	//Admin Use Case
	
	
	private void approveOrDeclineItem() {
		TableSelectionModel<Item> tableSelectionModel = adminView.getItemsList().getSelectionModel();
		tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
		Item selectedItem = tableSelectionModel.getSelectedItem();
		
		if(selectedItem != null) {
			adminView.setItemNameTxt(selectedItem.getItemName());
			adminView.setItemSizeTxt(selectedItem.getItemSize());
			adminView.setItemPriceTxt(Integer.toString(selectedItem.getItemPrice()));
			adminView.setItemCategoryTxt(selectedItem.getItemCategory());
			adminView.getApproveBtn().setOnAction(e -> approveItem(selectedItem.getItemId()));
			adminView.getDeclineBtn().setOnAction(e -> declineItem(selectedItem.getItemId()));
		}
		else {
			System.out.println("Please choose a filled row");
		}		
	}
	
	private void approveItem(String itemId) {
		if(itemId != null) {
			item.approveItem(itemId);
			data.setAll(item.getAllItems("Under Review"));
		}
	}
	
	private void declineItem(String itemId) {
		if(adminView.getReasonTF().getText() != null) {
			try {
				item.deleteItem(itemId);
				data.setAll(item.getAllItems("Under Review"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			adminView.getReasonTF().requestFocus();
		}
	}
	

}
