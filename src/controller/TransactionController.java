package controller;

import javafx.collections.ObservableList;
import model.Transaction;

public class TransactionController {
	
//	private static Transaction transaction;

	public TransactionController() {
//		this.transaction = new Transaction();
	}
	
	public static void purchaseItem(String userId, String itemId, int finalPrice) {
		Transaction.createTransaction(userId, itemId, finalPrice);
	}
	
	public static ObservableList<Transaction> viewHistory(String userId) {
		return Transaction.viewHistory(userId);
	
	}
	
	public void createTransaction(String userId, String itemId, int finalPrice) {
		
	}

}
