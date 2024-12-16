package view;

import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Item;

public class BuyerView extends BorderPane implements UI {
	
//	ItemController itemController;
//	WishlistController wishlistController;
	String signedInUserId;
	Scene scene;
	BorderPane bottomContainer;
	TableView<Item> itemsList;
	VBox container;
	HBox btnBox;
	MenuBar menuBar;
	Menu home, wishList, history;
	MenuItem viewAllItems, viewWishlist;
	GridPane gp;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl;
	Text itemIdTXt, itemNameTxt, itemPriceTxt, itemCategoryTxt, itemSizeTxt;
	TextField offerPriceTF;
	Button addWishlistBtn, offerBtn, removeBtn, purchaseBtn;
	ObservableList<Item> data;
	String wishlistId, selectedItemId = null;
	int selectedItemPrice = 0;

	public BuyerView(Stage stage, String signedInUserId,  ItemController itemController, WishlistController wishlistController) {
		this.signedInUserId = signedInUserId;
//		this.itemController = itemController;
//		this.wishlistController = wishlistController;
		this.data = FXCollections.observableArrayList();
		initialize();
		setLayout();
		addEvents(stage);
		stage.setScene(scene);
		stage.setTitle("CaLouselF");
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void initialize() {
		container = new VBox();
		gp = new GridPane();
		
		itemsList = new TableView<Item>();
		
		menuBar = new MenuBar();
		home = new Menu("Home");
		wishList = new Menu("Wishlist");
		history = new Menu("Purchase History");
		viewAllItems = new MenuItem("View All Items");
		viewWishlist = new MenuItem("View Wishlist");
	
		titleLbl = new Label("View All Items");
		itemNameLbl = new Label("Item Name:");
		itemPriceLbl = new Label("Item Price");
		itemCategoryLbl = new Label("Item Category");
		itemSizeLbl = new Label("Item Size");
		
		itemNameTxt = new Text();
		itemPriceTxt = new Text();
		itemCategoryTxt = new Text();
		itemSizeTxt = new Text();
		
		bottomContainer = new BorderPane();
		offerPriceTF = new TextField();
		addWishlistBtn = new Button("Add to Wishlist");
		offerBtn = new Button("Make Offer");
		removeBtn = new Button("Remove");
		purchaseBtn = new Button("Purchase");
		btnBox = new HBox();
		
		scene = new Scene(this, 500, 500);
		
	}

	@Override
	public void addElement() {
		setUpTable();
		this.data = ItemController.getAllItems("Approved");
		itemsList.setItems(data);
		
		container.getChildren().addAll(titleLbl, itemsList);
		
		gp.add(itemNameLbl, 0, 0);
		gp.add(itemPriceLbl, 0, 1);
		gp.add(itemCategoryLbl, 0, 2);
		gp.add(itemSizeLbl, 0, 3);
		
		gp.add(itemNameTxt, 1, 0);
		gp.add(itemPriceTxt, 1, 1);
		gp.add(itemCategoryTxt, 1, 2);
		gp.add(itemSizeTxt, 1, 3);
//		gp.add(addWishlistBtn, 0, 0);
		
		home.getItems().add(viewAllItems);
		wishList.getItems().add(viewWishlist);
		menuBar.getMenus().addAll(home, wishList, history);
		btnBox.getChildren().addAll(addWishlistBtn, offerBtn, purchaseBtn);
		addWishlistBtn.setDisable(true);
		offerBtn.setDisable(true);
		purchaseBtn.setDisable(true);
		removeBtn.setDisable(true);

		
	}

	@Override
	public void setLayout() {
		addElement();
		
		bottomContainer.setCenter(gp);
		bottomContainer.setBottom(btnBox);
		bottomContainer.setRight(offerPriceTF);
		
		this.setTop(menuBar);
		this.setCenter(container);
		this.setBottom(bottomContainer);
		
		btnBox.setSpacing(10);
		
		gp.setVgap(10);
		gp.setHgap(10);
	}

	@Override
	public void addEvents(Stage stage) {
		itemsList.setOnMouseClicked(e -> {
			TableSelectionModel<Item> tableSelectionModel = itemsList.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Item selectedItem = tableSelectionModel.getSelectedItem();
			
			if(selectedItem != null) {
				selectedItemId = selectedItem.getItemId();
				itemNameTxt.setText(selectedItem.getItemName());
				itemSizeTxt.setText(selectedItem.getItemSize());
				itemPriceTxt.setText(Integer.toString(selectedItem.getItemPrice()));
				selectedItemPrice = selectedItem.getItemPrice();
				itemCategoryTxt.setText(selectedItem.getItemCategory());
				wishlistId = selectedItem.getItemWishlist();
			}
			addWishlistBtn.setDisable(false);
			offerBtn.setDisable(false);
			purchaseBtn.setDisable(false);
			removeBtn.setDisable(false);
		});
		
		addWishlistBtn.setOnAction(event -> {
			WishlistController.addWishlist(selectedItemId, signedInUserId);
			clearText();
			disableButtons();
		});
		
		removeBtn.setOnAction(e -> {
			WishlistController.removeItemFromWishlist(wishlistId);
			try {
				this.data.setAll(WishlistController.viewWishlist(signedInUserId));				
			} catch (Exception e2) {
				this.data.clear();
			}
			clearText();
			disableButtons();
		});
		
		offerBtn.setOnAction(e -> {
			String offerPrice = offerPriceTF.getText();
			String msg = ItemController.offerPrice(signedInUserId, selectedItemId, offerPrice);
			offerPriceTF.clear();
			System.out.println(msg);
		});
		
		purchaseBtn.setOnAction(e -> {
			try {				
				TransactionController.purchaseItem(signedInUserId, selectedItemId, selectedItemPrice);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		viewAllItems.setOnAction(e -> {
			this.data.setAll(ItemController.getAllItems("Approved"));
			btnBox.getChildren().add(addWishlistBtn);
			btnBox.getChildren().remove(removeBtn);
			clearText();
			disableButtons();
		});
		
		viewWishlist.setOnAction(e -> {
			try {
				this.data.setAll(WishlistController.viewWishlist(signedInUserId));				
			} catch (Exception e2) {
				this.data.clear();
			}
			btnBox.getChildren().add(removeBtn);
			btnBox.getChildren().remove(addWishlistBtn);
			clearText();
			disableButtons();
		});
		
	}
	
	private void setUpTable() {		
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameColumn.setPrefWidth(200);
		
		TableColumn<Item, Integer> priceColumn = new TableColumn<Item, Integer>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemPrice"));
		
		TableColumn<Item, String> categoryColumn = new TableColumn<Item, String>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		
		TableColumn<Item, String> sizeColumn = new TableColumn<Item, String>("Size");
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		
		itemsList.getColumns().addAll(nameColumn, priceColumn, categoryColumn, sizeColumn);
	}
	
	private void clearText() {
		selectedItemId = null;
		itemNameTxt.setText("");
		itemPriceTxt.setText("");
		itemCategoryTxt.setText("");
		itemSizeTxt.setText("");
	}
	
	private void disableButtons() {
		removeBtn.setDisable(true);
		addWishlistBtn.setDisable(true);
		offerBtn.setDisable(true);
	}

}
