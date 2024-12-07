package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UploadItem implements UI{
	
	HomePage hp;
	GridPane gp;
	Label titleLbl, itemNameLbl, itemPriceLbl, itemCategoryLbl, itemSizeLbl;
	TextField itemNameTF, itemPriceTF, itemCategoryTF, itemSizeTF;
	Button uploadBtn;

	public UploadItem(HomePage hp) {
		this.hp = hp;
		initialize();
		layout();
	}

	@Override
	public void initialize() {
		gp = new GridPane();
		
		titleLbl = new Label("Upload Item");
		itemNameLbl = new Label("Item Name:");
		itemPriceLbl = new Label("Item Price");
		itemCategoryLbl = new Label("Item Category");
		itemSizeLbl = new Label("Item Size");
		itemNameTF = new TextField();
		itemPriceTF = new TextField();
		itemCategoryTF = new TextField();
		itemSizeTF = new TextField();
		
		uploadBtn = new Button("Upload");
		
	}

	@Override
	public void addElement() {
		gp.add(itemNameLbl, 0, 0);
		gp.add(itemPriceLbl, 0, 1);
		gp.add(itemCategoryLbl, 0, 2);
		gp.add(itemSizeLbl, 0, 3);
		
		gp.add(itemNameTF, 1, 0);
		gp.add(itemPriceTF, 1, 1);
		gp.add(itemCategoryTF, 1, 2);
		gp.add(itemSizeTF, 1, 3);
		
		
	}

	@Override
	public void layout() {
		
		hp.getTitleLbl().setText("Upload Item");
		hp.getContentContainer().setCenter(gp);
		hp.getContentContainer().setBottom(uploadBtn);
		
		addElement();
		
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
	}

}
