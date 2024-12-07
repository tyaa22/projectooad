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
import view.HomePage;

public class ItemController {
	
	Item item;
	HomePage hp;
//	ArrayList<Item> items = new ArrayList<Item>();
	ObservableList<Item> data = FXCollections.observableArrayList();
	
	public ItemController(Stage stage) {
		this.hp = new HomePage(stage);
		this.item = new Item();
		this.data = item.getAllReviewItems();
		hp.getItemsList().setItems(data);
		hp.getUploadBtn().setOnAction(e -> uploadItem());
		hp.getItemsList().setOnMouseClicked(startEditItem());
	}
	
	
	public void uploadItem() {
		hp.clearTextField();
		hp.getActionBtn().setText("Upload");
		hp.getActionBtn().setOnAction(new EventHandler<ActionEvent>() {
			
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
	
	
	private EventHandler<MouseEvent> startEditItem() {
		return new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Item> tableSelectionModel = hp.getItemsList().getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				Item selectedItem = tableSelectionModel.getSelectedItem();
				
				if(selectedItem != null) {
					hp.getItemNameTF().setText(selectedItem.getItemName());
					hp.getItemSizeTF().setText(selectedItem.getItemSize());
					hp.getItemCategoryTF().setText(selectedItem.getItemCategory());
					hp.getItemPriceTF().setText(Integer.toString(selectedItem.getItemPrice()));
					hp.getActionBtn().setText("Edit");
					hp.getActionBtn().setOnAction(e -> editItem(selectedItem.getItemId()));
				}
				else {
					System.out.println("Please choose a filled row");
				}
				
			}
		};
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
	

}
