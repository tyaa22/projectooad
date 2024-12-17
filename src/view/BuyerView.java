package view;

import java.util.ArrayList;

import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import model.Transaction;
import view.uielement.CustomAlert;
import view.uielement.ItemTableView;
import view.uielement.SearchBox;

public class BuyerView extends BorderPane implements UI {
	
	Scene scene;
	ItemTableView itemsList;
	VBox container, bottomContainer;
	HBox btnBox;
	SearchBox searchBox;
	MenuBar menuBar;
	Menu home, wishList, history;
	MenuItem viewAllItems, viewWishlist, viewPurchaseHistory;
	GridPane gp;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl;
	Text itemIdTxt, itemNameTxt, itemPriceTxt, itemCategoryTxt, itemSizeTxt;
	TextField offerPriceTF, searchTF;
	Button addWishlistBtn, offerBtn, removeBtn, purchaseBtn, searchBtn, cancelBtn;
	ObservableList<Item> data;
	String signedInUserId, wishlistId, selectedItemId = null;
	int selectedItemPrice = 0;

	public BuyerView(Stage stage, String signedInUserId) {
		this.signedInUserId = signedInUserId;
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
		
		itemsList = new ItemTableView(this);
		
		menuBar = new MenuBar();
		home = new Menu("Home");
		wishList = new Menu("Wishlist");
		history = new Menu("Purchase History");
		viewAllItems = new MenuItem("View All Items");
		viewWishlist = new MenuItem("View Wishlist");
		viewPurchaseHistory = new MenuItem("View Purchase History");
		searchBox = new SearchBox(itemsList);
	
		titleLbl = new Label("View All Items");
		itemNameLbl = new Label("Item Name:");
		itemPriceLbl = new Label("Item Price");
		itemCategoryLbl = new Label("Item Category");
		itemSizeLbl = new Label("Item Size");
		
		itemNameTxt = new Text();
		itemPriceTxt = new Text();
		itemCategoryTxt = new Text();
		itemSizeTxt = new Text();
		
		bottomContainer = new VBox();
		offerPriceTF = new TextField();
		offerPriceTF.setPromptText("Enter offer price");
		searchTF = new TextField();
		addWishlistBtn = new Button("Add to Wishlist");
		offerBtn = new Button("Make Offer");
		removeBtn = new Button("Remove");
		purchaseBtn = new Button("Purchase");
		searchBtn = new Button("Search");
		cancelBtn = new Button("Cancel");
		btnBox = new HBox();
		
		scene = new Scene(this, 500, 500);
		
	}

	@Override
	public void addElement() {
		this.data = ItemController.getAllItems("Approved");
		itemsList.setItems(data);
		
		container.getChildren().addAll(titleLbl, searchBox, itemsList);
		
		gp.add(itemNameLbl, 0, 0);
		gp.add(itemPriceLbl, 0, 1);
		gp.add(itemCategoryLbl, 0, 2);
		gp.add(itemSizeLbl, 0, 3);
		
		gp.add(itemNameTxt, 1, 0);
		gp.add(itemPriceTxt, 1, 1);
		gp.add(itemCategoryTxt, 1, 2);
		gp.add(itemSizeTxt, 1, 3);
		
		home.getItems().add(viewAllItems);
		wishList.getItems().add(viewWishlist);
		history.getItems().add(viewPurchaseHistory);
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
		
		bottomContainer.setSpacing(10);
		bottomContainer.getChildren().addAll(gp, offerPriceTF, btnBox);
		
		offerPriceTF.setMaxWidth(100);
		
		this.setTop(menuBar);
		this.setCenter(container);
		this.setBottom(bottomContainer);
		
		btnBox.setSpacing(10);
		
		gp.setVgap(10);
		gp.setHgap(10);
	}

	@Override
	public void addEvents(Stage stage) {
		
		searchBox.setEvent(itemsList, data);
		
		//mengambil data item yang diklik oleh user
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
				
				//jika item tidak null maka button dapat digunakan
				addWishlistBtn.setDisable(false);
				offerBtn.setDisable(false);
				purchaseBtn.setDisable(false);
				removeBtn.setDisable(false);
			}
		});
		
		
		addWishlistBtn.setOnAction(event -> {
			try {
				String msg = WishlistController.addWishlist(selectedItemId, signedInUserId);
				//memunculkan alert untuk memberitahu user apakah mereka berhasil menambahkan item ke wishlist
				if (msg.equals("Item added to Wishlist")) {					
					new CustomAlert(AlertType.INFORMATION, msg);
				}
				else {
					new CustomAlert(AlertType.ERROR, msg);					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			clearText();
			disableButtons();
		});
		
		removeBtn.setOnAction(e -> {
			try {
				WishlistController.removeItemFromWishlist(wishlistId);
				this.data.setAll(WishlistController.viewWishlist(signedInUserId));				
			} catch (Exception e2) {
				this.data.clear();
			}
			clearText();
			disableButtons();
		});
		
		offerBtn.setOnAction(e -> {
			offerPriceTF.setDisable(false);
			String offerPrice = offerPriceTF.getText();
			String msg = ItemController.offerPrice(signedInUserId, selectedItemId, offerPrice);
			//memunculkan alert untuk memberitahu user apakah mereka berhasil membuat offer atau tidak
			if(msg.equals("Success")) {
				new CustomAlert(AlertType.INFORMATION, msg);
			}
			else {
				new CustomAlert(AlertType.WARNING, msg);
			}
			offerPriceTF.clear();
			offerPriceTF.setVisible(false);
		});
		
		purchaseBtn.setOnAction(e -> {
			this.data.clear();
			try {				
				String msg = TransactionController.purchaseItem(signedInUserId, selectedItemId, selectedItemPrice);
				WishlistController.removeItemFromWishlist(wishlistId);
				//memunculkan alert untuk memberitahu user apakah mereka berhasil membeli barang atau tidak
				if(msg.equals("Item purchased")) {
					new CustomAlert(AlertType.INFORMATION, msg);
				}
				else {
					new CustomAlert(AlertType.WARNING, msg);
				}
				this.data.addAll(WishlistController.viewWishlist(signedInUserId));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		//mengubah tampilan untuk menunjukkan item apa saja yang dapat dibeli
		viewAllItems.setOnAction(e -> {
			try {
				this.setBottom(bottomContainer);
				
				//menyembunyikan kolom transactionId
				itemsList.getColumns().get(0).setVisible(false);
				this.data.setAll(ItemController.getAllItems("Approved"));
				
				// menambahkan kembali tombol addWishlist dan menghapus remove from wishlist
				if(!btnBox.getChildren().contains(addWishlistBtn)) {					
					btnBox.getChildren().add(addWishlistBtn);
				}
				btnBox.getChildren().remove(removeBtn);
				clearText();
				disableButtons();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			
		});
		
		//mengubah tampilan layar untuk menunjukkan wishlist user
		viewWishlist.setOnAction(e -> {
			this.data.clear();
			try {
				//menyembunyikan kolom transactionID
				itemsList.getColumns().get(0).setVisible(false);
				this.data.addAll(WishlistController.viewWishlist(signedInUserId));	
				//menambahkan tombol remove untuk menghapus wishlist
				if(!btnBox.getChildren().contains(removeBtn)) {					
					btnBox.getChildren().add(removeBtn);
				}
				this.setBottom(bottomContainer);
				//ketika melihat wishlist, button addWishlist di-remove
				btnBox.getChildren().remove(addWishlistBtn);
				clearText();
				disableButtons();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		//mengubah tampilan layar untuk menunjukkan purchase history user
		viewPurchaseHistory.setOnAction(e -> {
			titleLbl.setText("Purchase History");
			this.setBottom(null);
			this.data.setAll(TransactionController.viewHistory(signedInUserId));
			itemsList.getColumns().get(0).setVisible(true);
		});
		
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
