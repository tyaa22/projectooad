package view;

import controller.ItemController;
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

public class AdminView extends BorderPane implements UI {
	
	Scene scene;
	BorderPane bottomContainer;
	TableView<Item> itemsList;
	VBox container;
	HBox btnBox;
	MenuBar menuBar;
	Menu menu;
	MenuItem viewAllItems, viewPendingItems;
	GridPane gp;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl;
	Text itemNameTxt, itemPriceTxt, itemCategoryTxt, itemSizeTxt;
	TextField reasonTF;
	Button approveBtn, declineBtn;
	ItemController controller;
	ObservableList<Item> data;

	public AdminView(Stage stage, ItemController controller) {
		this.controller = controller;
		this.data = FXCollections.observableArrayList();
		initialize();
		setLayout();
		addEvents(stage);
		stage.setScene(scene);
		stage.setTitle("CaLouselF Admin");
		stage.setResizable(false);
		stage.show();
		
	}

	@Override
	public void initialize() {
		container = new VBox();
		gp = new GridPane();
		
		itemsList = new TableView<Item>();
		
		menuBar = new MenuBar();
		menu = new Menu();
		viewAllItems = new MenuItem("View All Items");
		viewPendingItems = new MenuItem("View Pending Items");
	
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
		reasonTF = new TextField();
		approveBtn = new Button("Approve");
		declineBtn = new Button("Decline");
		btnBox = new HBox();
		
		scene = new Scene(this, 500, 500);
	}

	@Override
	public void addElement() {
		
		setUpTable();
		this.data = controller.getAllItems("Under Review");
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
//		gp.add(approveBtn, 0, 0);
		
		menu.getItems().addAll(viewAllItems, viewPendingItems);
		menuBar.getMenus().add(menu);
		btnBox.getChildren().addAll(approveBtn, declineBtn);
		reasonTF.setPromptText("Please input reasons to decline the item");
	}

	@Override
	public void setLayout() {
		
		addElement();
		
		bottomContainer.setCenter(gp);
		bottomContainer.setBottom(btnBox);
		bottomContainer.setRight(reasonTF);
		
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
				itemNameTxt.setText(selectedItem.getItemName());
				itemSizeTxt.setText(selectedItem.getItemSize());
				itemPriceTxt.setText(Integer.toString(selectedItem.getItemPrice()));
				itemCategoryTxt.setText(selectedItem.getItemCategory());
				
				//action untuk approve item
				approveBtn.setOnAction(event -> {
					controller.approveItem(selectedItem.getItemId());
					this.data.setAll(controller.getAllItems("Under Review"));
				});
				
				//action untuk decline item
				declineBtn.setOnAction(event -> {
					//jika terdapat reason maka hapus item dari db
					if(!reasonTF.getText().isEmpty()) {
						try {
							controller.deleteItem(selectedItem.getItemId());
							this.data.setAll(controller.getAllItems("Under Review"));
							reasonTF.clear();
						}	
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else {
						//request focus ke reason textfield jika reason kosong
						reasonTF.requestFocus();
					}
				});
			}
			else {
				System.out.println("Please choose a filled row");
			}	
		});
		
	}
	
	private void setUpTable() {
		TableColumn<Item, String> idColumn = new TableColumn<Item, String>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemId"));
		
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameColumn.setPrefWidth(200);
		
		TableColumn<Item, Integer> priceColumn = new TableColumn<Item, Integer>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemPrice"));
		
		TableColumn<Item, String> categoryColumn = new TableColumn<Item, String>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		
		TableColumn<Item, String> sizeColumn = new TableColumn<Item, String>("Size");
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		
		itemsList.getColumns().addAll(idColumn, nameColumn, priceColumn, categoryColumn, sizeColumn);
	}	

}
