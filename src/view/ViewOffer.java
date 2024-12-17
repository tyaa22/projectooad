package view;

import controller.ItemController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Item;

public class ViewOffer extends VBox implements UI{

	GridPane gp;
	HBox btnBox;
	TableView<Item> offerList;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl;
	Text itemNameTxt, itemPriceTxt, itemCategoryTxt, itemSizeTxt, offerPriceTxt;
	Button acceptBtn, declineBtn;
	ItemController controller;

	public ViewOffer(ItemController controller) {
		this.controller = controller;
		initialize();
		setLayout();
		addEvents(null);
	}

	@Override
	public void initialize() {
		gp = new GridPane();
		
		offerList = new TableView<Item>();
		
		titleLbl = new Label("Active Offers");
		itemNameLbl = new Label("Item Name:");
		itemPriceLbl = new Label("Item Price");
		itemCategoryLbl = new Label("Item Category");
		itemSizeLbl = new Label("Item Size");
		itemNameTxt = new Text();
		itemPriceTxt = new Text();
		itemCategoryTxt = new Text();
		itemSizeTxt = new Text();
		offerPriceTxt = new Text();
		
		btnBox = new HBox();
		acceptBtn = new Button("Accept");
		declineBtn = new Button("Decline");
		
	}

	@Override
	public void addElement() {
		
		setUpTable();
		offerList.setItems(controller.viewOfferItem(""));
		
		gp.add(itemNameLbl, 0, 0);
		gp.add(itemPriceLbl, 0, 1);
		gp.add(itemCategoryLbl, 0, 2);
		gp.add(itemSizeLbl, 0, 3);
		
		gp.add(itemNameTxt, 1, 0);
		gp.add(itemPriceTxt, 1, 1);
		gp.add(itemCategoryTxt, 1, 2);
		gp.add(itemSizeTxt, 1, 3);
		
		btnBox.getChildren().addAll(acceptBtn, declineBtn);
		this.getChildren().addAll(titleLbl, gp, btnBox);
			
	}
	

	@Override
	public void setLayout() {
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
		btnBox.setSpacing(10);
		
	}

	@Override
	public void addEvents(Stage stage) {
		//mengambil data offer ketika table row di-click
				offerList.setOnMouseClicked(e -> {
					acceptBtn.setDisable(false);
					declineBtn.setDisable(false);
					TableSelectionModel<Item> tableSelectionModel = offerList.getSelectionModel();
					tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
					Item selectedItem = tableSelectionModel.getSelectedItem();
					
					if(selectedItem != null) {
//						selectedItemId = selectedItem.getItemId();
						itemNameTxt.setText(selectedItem.getItemName());
						itemSizeTxt.setText(selectedItem.getItemSize());
						itemCategoryTxt.setText(selectedItem.getItemCategory());
						itemPriceTxt.setText(Integer.toString(selectedItem.getItemPrice()));
						offerPriceTxt.setText(Integer.toString(selectedItem.getOfferPrice()));
					}
					else {
						System.out.println("Please choose a filled row");
					}
				}); 
		
	}
	
	private void setUpTable() {
		
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameColumn.setPrefWidth(200);
	
		TableColumn<Item, String> categoryColumn = new TableColumn<Item, String>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		
		TableColumn<Item, String> sizeColumn = new TableColumn<Item, String>("Size");
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		
		TableColumn<Item, Integer> priceColumn = new TableColumn<Item, Integer>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemPrice"));
		
		TableColumn<Item, Integer> offerPriceColumn = new TableColumn<Item, Integer>("Offer Price");
		offerPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("offerPrice"));
		
		offerList.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn, offerPriceColumn);
	}
	
	private void clearText() {
		itemNameTxt.setText("");
		itemPriceTxt.setText("");
		itemCategoryTxt.setText("");
		itemSizeTxt.setText("");
		offerPriceTxt.setText("");
	}

}
