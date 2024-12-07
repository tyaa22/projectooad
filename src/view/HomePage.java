package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;

public class HomePage implements UI {
	
	Scene scene;
	BorderPane homeContainer, contentContainer;
	ScrollPane sp;
//	GridPane itemsList, uploadItemForm;
	TableView<Item> itemsList;
	Label titleLbl;
	VBox container;
	MenuBar menuBar;
	Menu home;
	MenuItem viewItem, uploadItem;

	public HomePage(Stage stage) {
		initialize();
		layout();
		stage.setScene(scene);
		stage.setTitle("Home");
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void initialize() {
		
		homeContainer = new BorderPane();
		contentContainer = new BorderPane();
		sp = new ScrollPane();
		itemsList = new TableView<Item>();
//		uploadItemForm = new GridPane();
		titleLbl = new Label("View All Items");
		container = new VBox();
		menuBar = new MenuBar();
		home = new Menu("Home");
		viewItem = new MenuItem("View All Items");
		uploadItem = new MenuItem("Upload Item");
		
		scene = new Scene(homeContainer, 500, 260);
		
	}

	@Override
	public void addElement() {
		
		home.getItems().addAll(viewItem, uploadItem);
		menuBar.getMenus().addAll(home);
		
		
	}

	@Override
	public void layout() {
		
		addElement();
		
		homeContainer.setTop(menuBar);
		
		sp.setContent(contentContainer);
		contentContainer.setTop(titleLbl);
		
		homeContainer.setCenter(sp);
		
		setUpTable();
		contentContainer.setCenter(itemsList);
		
	}
	
	private void setUpTable() {
		TableColumn<Item, String> idColumn = new TableColumn<Item, String>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("item_id"));
		
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("item_name"));
		
		TableColumn<Item, Integer> priceColumn = new TableColumn<Item, Integer>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("item_price"));
		
		TableColumn<Item, String> categoryColumn = new TableColumn<Item, String>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("item_category"));
		
		TableColumn<Item, String> sizeColumn = new TableColumn<Item, String>("Size");
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("item_size"));
		
		itemsList.getColumns().addAll(idColumn, nameColumn, priceColumn, categoryColumn, sizeColumn);
	}

	public Label getTitleLbl() {
		return titleLbl;
	}

	public BorderPane getContentContainer() {
		return contentContainer;
	}
	
	

}
