package controller;

import javafx.collections.ObservableList;
import model.Item;
import model.Wishlist;

public class WishlistController {
	
	private static Wishlist wishlist;

	public WishlistController() {
		WishlistController.wishlist = new Wishlist();
	}
	
	public static void addWishlist(String itemId, String userId) {
		if(wishlist.itemAlreadyInWishlist(userId, itemId)) {
			System.out.println("Item already in Wishlist");
		}
		else {			
			wishlist.addWishlist(itemId, userId);
		}
	}
	
//	public ArrayList<String> getUserWishlist(String userId) {
//		return wishlist.getUserWishlist(userId);
//	}
	
	public static ObservableList<Item> viewWishlist(String userId) {
		return wishlist.viewWishlist(userId);
	}
	
	public static void removeItemFromWishlist(String wishlistId) {
		wishlist.deleteWishlist(wishlistId);
	}

}
