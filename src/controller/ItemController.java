package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Item;
import view.SellerPage;

public class ItemController {
	
	Item item;
	SellerPage hp;
//	ArrayList<Item> items = new ArrayList<Item>();
	ObservableList<Item> data = FXCollections.observableArrayList();
	
	public ItemController(Stage stage) {
		this.hp = new SellerPage(stage);
		this.item = new Item();
		this.data = item.getAllReviewItems();
		hp.getItemsList().setItems(data);
		hp.getUploadItemBtn().setOnAction(e -> uploadItem());
		hp.getItemsList().setOnMouseClicked(e -> editOrDeleteItem());
	}
	
	
	public void uploadItem() {
		hp.clearTextField();
		hp.getDeleteBtn().setDisable(true);
		hp.getEditBtn().setDisable(true);
		hp.getUploadBtn().setDisable(false);
		hp.getUploadBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String itemName = hp.getItemNameTF().getText();
				String itemSize = hp.getItemSizeTF().getText();
				int itemPrice = Integer.parseInt(hp.getItemPriceTF().getText());
				String itemCategory = hp.getItemCategoryTF().getText();
				item.uploadItem(itemName, itemSize, itemPrice, itemCategory);
				data.setAll(item.getAllReviewItems());
				hp.clearTextField();
				
			}
		});
	}
	
	public void editOrDeleteItem() {
		hp.getEditBtn().setVisible(true);
		hp.getDeleteBtn().setVisible(true);
		hp.getUploadBtn().setDisable(true);
		TableSelectionModel<Item> tableSelectionModel = hp.getItemsList().getSelectionModel();
		tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
		Item selectedItem = tableSelectionModel.getSelectedItem();
		
		if(selectedItem != null) {
			hp.getItemNameTF().setText(selectedItem.getItemName());
			hp.getItemSizeTF().setText(selectedItem.getItemSize());
			hp.getItemCategoryTF().setText(selectedItem.getItemCategory());
			hp.getItemPriceTF().setText(Integer.toString(selectedItem.getItemPrice()));
			hp.getEditBtn().setOnAction(e -> editItem(selectedItem.getItemId()));
			hp.getDeleteBtn().setOnAction(e -> deleteItem(selectedItem.getItemId()));
		}
		else {
			System.out.println("Please choose a filled row");
		}
		
	}
	
	private void editItem(String itemId) {
		String itemName = hp.getItemNameTF().getText();
		String itemSize = hp.getItemSizeTF().getText();
		int itemPrice = Integer.parseInt(hp.getItemPriceTF().getText());
		String itemCategory = hp.getItemCategoryTF().getText();
		item.editItem(itemId, itemName, itemSize, itemPrice, itemCategory);
		data.setAll(item.getAllReviewItems());
		hp.clearTextField();
	}
	
	public void deleteItem(String itemId) {
		String itemName = hp.getItemNameTF().getText();
		String itemSize = hp.getItemSizeTF().getText();
		int itemPrice = Integer.parseInt(hp.getItemPriceTF().getText());
		String itemCategory = hp.getItemCategoryTF().getText();
		item.deleteItem(itemId);
		data.setAll(item.getAllReviewItems());
		hp.clearTextField();
	}
	

}
