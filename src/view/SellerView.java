package view;

import controller.ItemController;
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

public class SellerView extends BorderPane implements UI {
	
	Scene scene;
	TableView<Item> itemsList;
	VBox container, offerContainer;
	BorderPane formBP;
	HBox itemBtnBox, offerBtnBox;
	MenuBar menuBar;
	Menu home, offer;
	MenuItem uploadItem, viewAllItems, viewOffers;
	GridPane itemGP, offerGP;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl, errorLbl;
	TextField itemNameTF, itemPriceTF, itemCategoryTF, itemSizeTF, reasonTF;
	Button uploadItemBtn, editBtn, deleteBtn, uploadBtn, acceptBtn, declineBtn;
//	ObservableList<Item> data;
	ItemController controller;
	String selectedItemId, offeringUserId;

	public SellerView(Stage stage, ItemController controller) {
//		this.controller = controller;
//		this.data = FXCollections.observableArrayList();
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
		itemsList = new TableView<Item>();
		
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
		
//		offerGP = new GridPane();
		
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

		setUpTable();
		itemsList.setItems(ItemController.getAllItems());
		
		container.getChildren().addAll(titleLbl, itemsList);
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
		//action untuk upload item button di kiri atas screen
//		uploadItemBtn.setOnAction(e -> {
//			clearTextField();
//			deleteBtn.setDisable(true);
//			editBtn.setDisable(true);
//			uploadBtn.setDisable(false);
//		});
		
		//action untuk memasukkan item ke db
		uploadBtn.setOnAction(e -> {
			String itemName = itemNameTF.getText();
			String itemPrice = itemPriceTF.getText();
			String itemSize = itemSizeTF.getText();
			String itemCategory = itemCategoryTF.getText();
			try {				
				String msg = ItemController.uploadItem(itemName, itemPrice, itemSize, itemCategory);
				if(msg.equals("Item successfully uploaded")) {
					itemsList.setItems(ItemController.getAllItems());
					clearTextField();
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
				itemsList.setItems(ItemController.getAllItems());
				clearTextField();
				deleteBtn.setDisable(true);
				editBtn.setDisable(true);
			}
			errorLbl.setText(msg);
		});
		//action untuk delete item dari db
		deleteBtn.setOnAction(event -> {
			ItemController.deleteItem(selectedItemId);
			errorLbl.setText("Item successfully deleted");
			itemsList.setItems(ItemController.getAllItems());
			clearTextField();
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
				itemsList.setItems(ItemController.getAllItems());
//				container.getChildren().clear();
//				container.getChildren().addAll(uploadItemBtn, titleLbl, itemsList);
//				this.data.setAll(controller.getAllItems("Approved"));				
			} catch (Exception e2) {
//				this.data.clear();
				e2.printStackTrace();
			}
		});
		
		uploadItem.setOnAction(e -> {
			try {
				itemsList.getColumns().get(4).setVisible(false);
				itemsList.getColumns().get(5).setVisible(false);
				itemsList.setItems(ItemController.getAllItems());
				for (Item item : ItemController.getAllItems()) {
					System.out.println(item.getItemId());
				}
				clearTextField();
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
//			this.setCenter(offerContainer);
			itemsList.getColumns().get(4).setVisible(true);
			itemsList.getColumns().get(5).setVisible(true);
//			System.out.println(itemsList.getColumns().get(4).getCellData(0));
			try {
				itemsList.setItems(ItemController.viewOfferItem());				
				itemNameTF.setDisable(true);
				itemPriceTF.setDisable(true);
				itemSizeTF.setDisable(true);
				itemCategoryTF.setDisable(true);
				formBP.setCenter(offerBtnBox);
				formBP.setRight(reasonTF);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
//			container.getChildren().clear();
//			container.getChildren().addAll(titleLbl, itemsList);
		});
		
		acceptBtn.setOnAction(e -> {
			ItemController.deleteOffer(selectedItemId, offeringUserId);
			itemsList.setItems(ItemController.viewOfferItem());
		});
		
		declineBtn.setOnAction(e -> {
			if(reasonTF.getText().isEmpty()) {
				reasonTF.requestFocus();
			}
			else {
				ItemController.deleteOffer(selectedItemId, offeringUserId);
				itemsList.setItems(ItemController.viewOfferItem());
			}
		});
		
	}
	
	private void setUpTable() {
		
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameColumn.setPrefWidth(130);
		
		TableColumn<Item, Integer> priceColumn = new TableColumn<Item, Integer>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemPrice"));
		
		TableColumn<Item, String> categoryColumn = new TableColumn<Item, String>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		
		TableColumn<Item, String> sizeColumn = new TableColumn<Item, String>("Size");
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		
		TableColumn<Item, Integer> offerPriceColumn = new TableColumn<Item, Integer>("Offer Price");
		offerPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("offerPrice"));
		
		TableColumn<Item, Integer> offeringUserColumn = new TableColumn<Item, Integer>("User ID");
		offeringUserColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemOfferStatus"));
		offerPriceColumn.setVisible(false);
		offeringUserColumn.setVisible(false);
		
		itemsList.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn, offerPriceColumn, offeringUserColumn);
	}
	
	public void clearTextField() {
		selectedItemId = "";
		offeringUserId = "";
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
