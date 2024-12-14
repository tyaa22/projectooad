package controller;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import model.Item;
import model.Wishlist;

public class WishlistController {
	
	private Wishlist wishlist;

	public WishlistController() {
		this.wishlist = new Wishlist();
	}
	
	public void addWishlist(String itemId, String userId) {
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
	
	public ObservableList<Item> viewWishlist(String userId) {
		return wishlist.viewWishlist(userId);
	}
	
	public void removeItemFromWishlist(String wishlistId) {
		wishlist.deleteWishlist(wishlistId);
	}

}
