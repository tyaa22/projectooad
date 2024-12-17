package view.uielement;

import java.awt.event.ItemListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import view.BuyerView;
import view.SellerView;
import view.UI;

public class ItemTableView extends TableView<Item> {
	
	TableSelectionModel<Item> tableSelectionModel;

	public ItemTableView(UI view) {
		setUpTable(view);
	}
	
	private void setUpTable(UI view) {
			
		TableColumn<Item, String> nameColumn = new TableColumn<Item, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameColumn.setPrefWidth(200);
		
		TableColumn<Item, Integer> priceColumn = new TableColumn<Item, Integer>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemPrice"));
		
		TableColumn<Item, String> categoryColumn = new TableColumn<Item, String>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		
		TableColumn<Item, String> sizeColumn = new TableColumn<Item, String>("Size");
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		
		this.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		if(view instanceof SellerView) {
			TableColumn<Item, Integer> offerPriceColumn = new TableColumn<Item, Integer>("Offer Price");
			offerPriceColumn.setId("offerPriceColumn");
			offerPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("offerPrice"));
			
			TableColumn<Item, Integer> offeringUserColumn = new TableColumn<Item, Integer>("User ID");
			offeringUserColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemOfferingUser"));
			offeringUserColumn.setId("offeringUserColumn");
			offerPriceColumn.setVisible(false);
			offeringUserColumn.setVisible(false);
			
			this.getColumns().add(offerPriceColumn);
			this.getColumns().add(offeringUserColumn);
		}
		
		if(view instanceof BuyerView) {
			TableColumn<Item, String> transactionIdColumn = new TableColumn<Item, String>("ID");
			transactionIdColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("transactionId"));
			transactionIdColumn.setId("transactionIdColumn");
			this.getColumns().add(0, transactionIdColumn);
			transactionIdColumn.setVisible(false);
		}
	
	}
}
