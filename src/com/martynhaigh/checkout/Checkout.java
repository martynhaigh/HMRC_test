package com.martynhaigh.checkout;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Checkout {

    static final String CART_PRINTOUT_TEMPLATE = "%d items in cart coming to a total of £%.2f";
    private static final String[] DEFAULT_SHOPPING_LIST = new String[]{"Apple", "Apple", "Orange", "Apple", "Apple"};
    private List<Product> itemsInCart = new ArrayList<>();

    public static void main(String[] args) {
        Checkout checkout = new Checkout();

        // Allows for passing through of items from the CLI
        List<Product> itemsToAddToCart = checkout.parseShoppingList(args.length > 0 ? args : DEFAULT_SHOPPING_LIST);
        checkout.addItemsToCart(itemsToAddToCart);

        System.out.print(checkout.getPrintout());
    }

    /**
     * Takes an array of item names and attempts to parse them to valid list of {@link Product}
     *
     * @param itemsToAddToCart An array of case insensitive product names to add to the cart.
     * @return A valid list of {@link Product}
     */
    List<Product> parseShoppingList(final String[] itemsToAddToCart) {
        return Arrays.stream(itemsToAddToCart).map(this::parseItemName).collect(Collectors.toList());
    }

    /**
     * Takes an array of item names, attempts to parse them and add them to the cart.
     *
     * @param itemsToAddToCart An array of case insensitive product names to add to the cart.
     */
    void addItemsToCart(final List<Product> itemsToAddToCart) {
        // Note: Don't use itemsInCart.addAll as we don't want null values to be added

        if (itemsToAddToCart == null || itemsToAddToCart.size() == 0) {
            return;
        }

        itemsToAddToCart.stream().filter(product -> product != null).forEach(itemsInCart::add);
    }

    /**
     * Attempts to parse a string to a valid {@link Product}
     *
     * @param itemName The name of the item to parse, this is case insensitive and ignores any leading and trailing whitespace.
     * @return A valid {@link Product} or null
     */
    @Nullable
    Product parseItemName(String itemName) {
        itemName = itemName.toUpperCase().trim();
        try {
            return Product.valueOf(itemName);
        } catch (final IllegalArgumentException e) {
            // Expected error if the passed in name doesn't match any Product value
            return null;
        }
    }

    /**
     * Return all itemsInCart added to cart
     *
     * @return
     */
    List<Product> getCartItems() {
        return itemsInCart;
    }

    /**
     * Gets the cart total price
     *
     * @return total price in pennies
     */
    int getCartTotalCost() {
        int cartTotal = itemsInCart.stream().mapToInt(Product::getPriceInPence).sum();
        int offerSavings = Offer.applyAllOffers(itemsInCart).getTotalSavings();
        return cartTotal - offerSavings;
    }

    /**
     * Generates the shopping cart printout
     *
     * @return A String representation of the total number of items in the cart and total cost in GBP
     */
    String getPrintout() {
        return String.format(CART_PRINTOUT_TEMPLATE, getCartItems().size(), (float) getCartTotalCost() / 100);
    }
}
