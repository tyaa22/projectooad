package view;

import controller.ItemController;
import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import view.uielement.ItemTableView;
import view.uielement.SearchBox;

public class SellerView extends BorderPane implements UI {
	
	Scene scene;
	TableView<Item> itemsList;
	ObservableList<Item> data;
	SearchBox searchBox;
	VBox container, offerContainer;
	BorderPane formBP;
	HBox itemBtnBox, offerBtnBox;
	MenuBar menuBar;
	Menu home, offer;
	MenuItem uploadItem, viewAllItems, viewOffers;
	GridPane itemGP, offerGP;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl, errorLbl;
	TextField itemNameTF, itemPriceTF, itemCategoryTF, itemSizeTF, reasonTF, searchTF;
	Button uploadItemBtn, editBtn, deleteBtn, uploadBtn, acceptBtn, declineBtn, searchBtn, cancelBtn;
	ItemController controller;
	String signedInUserId, selectedItemId, offeringUserId;
	int selectedItemPrice = 0;

	public SellerView(Stage stage, String signedInUserId) {
		this.signedInUserId = signedInUserId;
		this.data = FXCollections.observableArrayList();
		initialize();
		setLayout();
		addEvents(stage);
		stage.setScene(scene);
		stage.setTitle("CaLouselF Seller");
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void initialize() {		
//		homeContainer = new BorderPane();
		itemsList = new ItemTableView(this);
		
		titleLbl = new Label("View All Items");
		container = new VBox();
		offerContainer = new VBox();
		formBP = new BorderPane();
		itemBtnBox = new HBox();
		offerBtnBox = new HBox();
		menuBar = new MenuBar();
		home = new Menu("Home");
		offer = new Menu("Offers");
		viewAllItems = new MenuItem("View All Items");
		viewOffers = new MenuItem("View Offers");
		uploadItem = new MenuItem("Upload Item");
		
		
		itemGP = new GridPane();	
		itemNameLbl = new Label("Item Name:");
		itemPriceLbl = new Label("Item Price");
		itemCategoryLbl = new Label("Item Category");
		itemSizeLbl = new Label("Item Size");
		errorLbl = new Label();
		
		itemNameTF = new TextField();
		itemPriceTF = new TextField();
		itemCategoryTF = new TextField();
		itemSizeTF = new TextField();
		reasonTF = new TextField();
		searchBox = new SearchBox(itemsList);
		
		uploadItemBtn = new Button("Upload Item");
		uploadBtn = new Button("Upload");
		editBtn = new Button("Edit");
		deleteBtn = new Button("Delete");
		acceptBtn = new Button("Accept");
		declineBtn = new Button("Decline");
		editBtn.setDisable(true);
		deleteBtn.setDisable(true);
		acceptBtn.setDisable(true);
		declineBtn.setDisable(true);
		
		scene = new Scene(this, 500, 500);
		
	}

	@Override
	public void addElement() {
		menuBar.getMenus().addAll(home, offer);
		home.getItems().addAll(viewAllItems, uploadItem);
		offer.getItems().add(viewOffers);

		this.data = ItemController.getAllItems();
		itemsList.setItems(data);
		
		container.getChildren().addAll(titleLbl, searchBox, itemsList);
//		offerContainer.getChildren().addAll(titleLbl, itemsList);
//		this.data = controller.getAllItems("Approved");
		
		itemGP.add(itemNameLbl, 0, 0);
		itemGP.add(itemPriceLbl, 0, 1);
		itemGP.add(itemCategoryLbl, 0, 2);
		itemGP.add(itemSizeLbl, 0, 3);
		
		itemGP.add(itemNameTF, 1, 0);
		itemGP.add(itemPriceTF, 1, 1);
		itemGP.add(itemCategoryTF, 1, 2);
		itemGP.add(itemSizeTF, 1, 3);
		
		itemBtnBox.getChildren().addAll(uploadBtn, editBtn, deleteBtn);
		offerBtnBox.getChildren().addAll(acceptBtn, declineBtn);
//		searchBox.getChildren().addAll(searchTF, searchBtn, cancelBtn);
	}

	@Override
	public void setLayout() {
		
		addElement();
		
		this.setTop(menuBar);
		this.setCenter(container);
		this.setBottom(formBP);
		
		itemsList.setMaxHeight(250);
		
		itemGP.setVgap(10);
		itemGP.setHgap(10);
		itemGP.setAlignment(Pos.TOP_CENTER);
		
		formBP.setTop(itemGP);
		formBP.setCenter(itemBtnBox);
		formBP.setBottom(errorLbl);
		
		itemBtnBox.setSpacing(10);
		itemBtnBox.setAlignment(Pos.CENTER);
		offerBtnBox.setSpacing(10);
		offerBtnBox.setAlignment(Pos.CENTER);
		
		container.setSpacing(10);
	}
	
	@Override
	public void addEvents(Stage stage) {
		
		searchBox.setEvent(itemsList, data);
		
		//action untuk memasukkan item ke db
		uploadBtn.setOnAction(e -> {
			String itemName = itemNameTF.getText();
			String itemPrice = itemPriceTF.getText();
			String itemSize = itemSizeTF.getText();
			String itemCategory = itemCategoryTF.getText();
			try {				
				String msg = ItemController.uploadItem(itemName, itemPrice, itemSize, itemCategory, signedInUserId);
				if(msg.equals("Item successfully uploaded")) {
					itemsList.setItems(ItemController.getAllItems());
					clearSelectedItem();
				}
				errorLbl.setText(msg);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		//mengambil data item yang ingin diedit atau delete ketika table row di-click
		itemsList.setOnMouseClicked(e -> {
			editBtn.setVisible(true);
			deleteBtn.setVisible(true);
			uploadBtn.setDisable(true);
			TableSelectionModel<Item> tableSelectionModel = itemsList.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Item selectedItem = tableSelectionModel.getSelectedItem();
			
			if(selectedItem != null) {
				selectedItemId = selectedItem.getItemId();
				offeringUserId = selectedItem.getItemOfferingUser();
				selectedItemPrice = selectedItem.getOfferPrice();
				itemNameTF.setText(selectedItem.getItemName());
				itemSizeTF.setText(selectedItem.getItemSize());
				itemCategoryTF.setText(selectedItem.getItemCategory());
				itemPriceTF.setText(Integer.toString(selectedItem.getItemPrice()));
				enableTextField();
				editBtn.setDisable(false);
				deleteBtn.setDisable(false);
				acceptBtn.setDisable(false);
				declineBtn.setDisable(false);
			}
			else {
				System.out.println("Please choose a filled row");
			}
		}); 
		
		//action untuk edit item ke db
		editBtn.setOnAction(event -> {
			String itemName = itemNameTF.getText();
			String itemSize = itemSizeTF.getText();
			String itemPrice = itemPriceTF.getText();
			String itemCategory = itemCategoryTF.getText();
			String msg = ItemController.editItem(selectedItemId, itemName, itemSize, itemPrice, itemCategory);
			if(msg.equals("Item successfully edited")) {
				this.data.setAll(ItemController.getAllItems());
				clearSelectedItem();
				deleteBtn.setDisable(true);
				editBtn.setDisable(true);
			}
			errorLbl.setText(msg);
		});
		//action untuk delete item dari db
		deleteBtn.setOnAction(event -> {
			ItemController.deleteItem(selectedItemId);
			errorLbl.setText("Item successfully deleted");
			this.data.setAll(ItemController.getAllItems());
			clearSelectedItem();
			deleteBtn.setDisable(true);
			editBtn.setDisable(true);
		});
		
		viewAllItems.setOnAction(e -> {
//			this.setCenter(container);
//			itemsList.getColumns().remove(itemsList.getColumns().size() - 1);
			try {
				itemsList.getColumns().get(4).setVisible(false);
				itemsList.getColumns().get(5).setVisible(false);
				itemsList.getColumns().get(0);
				formBP.setCenter(itemBtnBox);
				this.data.setAll(ItemController.getAllItems());		
			} catch (Exception e2) {
				this.data.clear();
				e2.printStackTrace();
			}
		});
		
		uploadItem.setOnAction(e -> {
			try {
				itemsList.getColumns().get(4).setVisible(false);
				itemsList.getColumns().get(5).setVisible(false);
				this.data.setAll(ItemController.getAllItems());
				clearSelectedItem();
				formBP.setCenter(itemBtnBox);
				formBP.setRight(null);
				deleteBtn.setDisable(true);
				editBtn.setDisable(true);
				uploadBtn.setDisable(false);
				enableTextField();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		viewOffers.setOnAction(e -> {
			itemsList.getColumns().get(4).setVisible(true);
			itemsList.getColumns().get(5).setVisible(true);
			try {
				this.data.setAll(ItemController.viewOfferItem(signedInUserId));				
				itemNameTF.setDisable(true);
				itemPriceTF.setDisable(true);
				itemSizeTF.setDisable(true);
				itemCategoryTF.setDisable(true);
				formBP.setCenter(offerBtnBox);
				formBP.setRight(reasonTF);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		acceptBtn.setOnAction(e -> {
			TransactionController.purchaseItem(offeringUserId, selectedItemId, selectedItemPrice);
			ItemController.deleteOffer(selectedItemId, offeringUserId);
			this.data.setAll(ItemController.viewOfferItem(signedInUserId));
		});
		
		declineBtn.setOnAction(e -> {
			if(reasonTF.getText().isEmpty()) {
				reasonTF.requestFocus();
			}
			else {
				ItemController.deleteOffer(selectedItemId, offeringUserId);
				this.data.setAll(ItemController.viewOfferItem(signedInUserId));
			}
		});
		
	}
		
	public void clearSelectedItem() {
		selectedItemId = "";
		offeringUserId = "";
		selectedItemPrice = 0;
		itemNameTF.clear();
		itemPriceTF.clear();
		itemCategoryTF.clear();
		itemSizeTF.clear();
	}
	
	private void enableTextField() {
		itemNameTF.setDisable(false);
		itemPriceTF.setDisable(false);
		itemCategoryTF.setDisable(false);
		itemSizeTF.setDisable(false);
	}

}
