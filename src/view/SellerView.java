package view;

import controller.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Item;

public class SellerView extends BorderPane implements UI {
	
	Scene scene;
//	BorderPane homeContainer;
//	GridPane itemsList, uploadItemForm;
	TableView<Item> itemsList;
	VBox container, formContainer;
	HBox btnBox;
	MenuBar menuBar;
	Menu home;
	MenuItem uploadItem;
	GridPane gp;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl;
	TextField itemNameTF, itemPriceTF, itemCategoryTF, itemSizeTF;
	Button uploadItemBtn, editBtn, deleteBtn, uploadBtn;
	ObservableList<Item> data;
	ItemController controller;

	public SellerView(Stage stage, ItemController controller) {
		this.controller = controller;
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
		itemsList = new TableView<Item>();
		
		titleLbl = new Label("View All Items");
		container = new VBox();
		formContainer = new VBox();
		btnBox = new HBox();
		menuBar = new MenuBar();
		home = new Menu("Home");
		uploadItem = new MenuItem("View Items");
		
		gp = new GridPane();	
		itemNameLbl = new Label("Item Name:");
		itemPriceLbl = new Label("Item Price");
		itemCategoryLbl = new Label("Item Category");
		itemSizeLbl = new Label("Item Size");
		
		itemNameTF = new TextField();
		itemPriceTF = new TextField();
		itemCategoryTF = new TextField();
		itemSizeTF = new TextField();
		
		uploadItemBtn = new Button("Upload Item");
		uploadBtn = new Button("Upload");
		editBtn = new Button("Edit");
		deleteBtn = new Button("Delete");
		editBtn.setVisible(false);
		deleteBtn.setVisible(false);
		
		scene = new Scene(this, 500, 500);
		
	}

	@Override
	public void addElement() {
		
		home.getItems().addAll(uploadItem);
		menuBar.getMenus().addAll(home);
		
		setUpTable();
		this.data = controller.getAllItems();
		itemsList.setItems(data);
		
		gp.add(itemNameLbl, 0, 0);
		gp.add(itemPriceLbl, 0, 1);
		gp.add(itemCategoryLbl, 0, 2);
		gp.add(itemSizeLbl, 0, 3);
		
		gp.add(itemNameTF, 1, 0);
		gp.add(itemPriceTF, 1, 1);
		gp.add(itemCategoryTF, 1, 2);
		gp.add(itemSizeTF, 1, 3);
		
		btnBox.getChildren().addAll(uploadBtn, editBtn, deleteBtn);
		
		container.getChildren().addAll(uploadItemBtn, titleLbl,itemsList);
		formContainer.getChildren().addAll(gp, btnBox);
		
	}

	@Override
	public void setLayout() {
		
		addElement();
		
		this.setTop(menuBar);
		this.setCenter(container);
		this.setBottom(formContainer);
		
		itemsList.setMaxHeight(250);
		
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.TOP_CENTER);
		
		btnBox.setSpacing(10);
		btnBox.setAlignment(Pos.CENTER);
		
		container.setSpacing(10);
		formContainer.setAlignment(Pos.CENTER);
		formContainer.setSpacing(10);
		
//		sp.setContent(container);
//		container.setPadding(new Insets(10));
//		container.setSpacing(10);	
	}
	
	@Override
	public void addEvents(Stage stage) {
		//action untuk upload item button di kiri atas screen
		uploadItemBtn.setOnAction(e -> {
			clearTextField();
			deleteBtn.setDisable(true);
			editBtn.setDisable(true);
			uploadBtn.setDisable(false);
		});
		
		//action untuk memasukkan item ke db
		uploadBtn.setOnAction(e -> {
			String itemName = itemNameTF.getText();
			String itemSize = itemSizeTF.getText();
			int itemPrice = Integer.parseInt(itemPriceTF.getText());
			String itemCategory = itemCategoryTF.getText();
			controller.uploadItem(itemName, itemSize, itemPrice, itemCategory);
			this.data.setAll(controller.getAllItems());
			clearTextField();
		});
		
		//mengambil data item yang ingin diedit atau delete
		itemsList.setOnMouseClicked(e -> {
			editBtn.setVisible(true);
			deleteBtn.setVisible(true);
			uploadBtn.setDisable(true);
			TableSelectionModel<Item> tableSelectionModel = itemsList.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Item selectedItem = tableSelectionModel.getSelectedItem();
			
			if(selectedItem != null) {
				String itemId = selectedItem.getItemId();
				itemNameTF.setText(selectedItem.getItemName());
				itemSizeTF.setText(selectedItem.getItemSize());
				itemCategoryTF.setText(selectedItem.getItemCategory());
				itemPriceTF.setText(Integer.toString(selectedItem.getItemPrice()));
				//action untuk edit item ke db
				editBtn.setOnAction(event -> {
					String itemName = itemNameTF.getText();
					String itemSize = itemSizeTF.getText();
					int itemPrice = Integer.parseInt(itemPriceTF.getText());
					String itemCategory = itemCategoryTF.getText();
					controller.editItem(itemId, itemName, itemSize, itemPrice, itemCategory);
					this.data.setAll(controller.getAllItems());
					clearTextField();
				});
				//action untuk delete item dari db
				deleteBtn.setOnAction(event -> {
					controller.deleteItem(itemId);
					data.setAll(controller.getAllItems());
					clearTextField();
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
	
	public void clearTextField() {
		itemNameTF.clear();
		itemPriceTF.clear();
		itemCategoryTF.clear();
		itemSizeTF.clear();
	}

}
