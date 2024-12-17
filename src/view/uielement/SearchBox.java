package view.uielement;

import controller.ItemController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Item;

public class SearchBox extends HBox {

	TextField searchTF;
	Button searchBtn, cancelBtn;
	ObservableList<Item> data;
	
	public SearchBox(TableView<Item> itemsList) {
		searchBtn = new Button("Search");
		cancelBtn = new Button("Cancel");
		searchTF = new TextField();
		searchTF.setPromptText("Search");
		
		this.getChildren().addAll(searchTF, searchBtn, cancelBtn);
		this.setSpacing(10);
	}
	
	public EventHandler<ActionEvent> addEvent(TableView<Item> itemsList, ObservableList<Item> data) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(event.getSource() == searchBtn) {
					if(searchTF.getText().isEmpty()) searchTF.requestFocus();
					else {
						ObservableList<Item> result = (ObservableList<Item>) ItemController.browseItem(searchTF.getText(), data);
						itemsList.setItems(result);
						
					}
				}
				else if(event.getSource() == cancelBtn) {
					itemsList.setItems(data);
					searchTF.clear();
				}	
			}
		};
	}
	
	public void setEvent(TableView<Item> itemsList, ObservableList<Item> data) {
		searchBtn.setOnAction(addEvent(itemsList, data));
		cancelBtn.setOnAction(addEvent(itemsList, data));
	}
}
