package controller;

import javafx.collections.ObservableList;
import model.Item;
import model.Wishlist;

public class WishlistController {
	

	public WishlistController() {
		
	}
	
	public static void addWishlist(String itemId, String userId) {
		if(Wishlist.itemAlreadyInWishlist(userId, itemId)) {
			System.out.println("Item already in Wishlist");
		}
		else {			
			Wishlist.addWishlist(itemId, userId);
		}
	}
	
//	public ArrayList<String> getUserWishlist(String userId) {
//		return wishlist.getUserWishlist(userId);
//	}
	
	public static ObservableList<Item> viewWishlist(String userId) {
		return Wishlist.viewWishlist(userId);
	}
	
	public static void removeItemFromWishlist(String wishlistId) {
		Wishlist.deleteWishlist(wishlistId);
	}

}
