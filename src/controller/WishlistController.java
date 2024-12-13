package controller;

import model.Wishlist;

public class WishlistController {
	
	private Wishlist wishlist;

	public WishlistController() {
		this.wishlist = new Wishlist();
	}
	
	public void addWishlist(String itemId, String userId) {
		wishlist.addWishlist(itemId, userId);
	}

}
