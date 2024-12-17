package controller;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import model.Item;
import model.Transaction;

public class TransactionController {
	
	public static String purchaseItem(String userId, String itemId, int finalPrice) {
		// return hasil insert transaksi ke db
		return Transaction.createTransaction(userId, itemId, finalPrice);
	}
	
	public static ObservableList<Item> viewHistory(String userId) {
		// return purchase history yang pernah dibuat user
		return Transaction.viewHistory(userId);
	
	}

}
