package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
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

public class AdminView implements UI {
	
	Scene scene;
	BorderPane mainContainer, bottomContainer;
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

	public AdminView(Stage stage) {
		initialize();
		layout();
		stage.setScene(scene);
		stage.setTitle("CaLouselF Admin");
		stage.setResizable(false);
		stage.show();
		
	}

	@Override
	public void initialize() {
		
		mainContainer = new BorderPane();
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
		
		scene = new Scene(mainContainer, 500, 500);
	}

	@Override
	public void addElement() {
		
		setUpTable();
		
		container.getChildren().addAll(titleLbl, itemsList);
		
		gp.add(itemNameLbl, 0, 0);
		gp.add(itemPriceLbl, 0, 1);
		gp.add(itemCategoryLbl, 0, 2);
		gp.add(itemSizeLbl, 0, 3);
		
		gp.add(itemNameTxt, 1, 0);
		gp.add(itemPriceTxt, 1, 1);
		gp.add(itemCategoryTxt, 1, 2);
		gp.add(itemSizeTxt, 1, 3);
		gp.add(approveBtn, 0, 0);
		
		menu.getItems().addAll(viewAllItems, viewPendingItems);
		menuBar.getMenus().add(menu);
		btnBox.getChildren().addAll(approveBtn, declineBtn);
	}

	@Override
	public void layout() {
		
		addElement();
		
		bottomContainer.setCenter(gp);
		bottomContainer.setBottom(btnBox);
		bottomContainer.setRight(reasonTF);
		
		mainContainer.setTop(menuBar);
		mainContainer.setCenter(container);
		mainContainer.setBottom(bottomContainer);
		
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

	public TableView<Item> getItemsList() {
		return itemsList;
	}

	public TextField getReasonTF() {
		return reasonTF;
	}

	public Button getApproveBtn() {
		return approveBtn;
	}

	public Button getDeclineBtn() {
		return declineBtn;
	}

	public void setItemNameTxt(String txt) {
		itemNameTxt.setText(txt);
	}

	public void setItemPriceTxt(String txt) {
		itemPriceTxt.setText(txt);
	}

	public void setItemCategoryTxt(String txt) {
		itemCategoryTxt.setText(txt);
	}

	public void setItemSizeTxt(String txt) {
		itemSizeTxt.setText(txt);
	}
	
	

}
