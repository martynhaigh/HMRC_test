package com.martynhaigh.checkout;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CheckoutTest {

    private Checkout checkout;

    @org.junit.Before
    public void setUp() throws Exception {
        checkout = new Checkout();
    }

    /**
     * Ensure that we parse a string representation of a product to a valid product correctly
     */
    @Test
    public void parseSingleValidItems() {
        // Test various strings to ensure parsing is case insensitive
        assertThat(checkout.parseItemName("Orange"), is(allOf(notNullValue(), equalTo(Product.ORANGE))));
        assertThat(checkout.parseItemName("ORANGE"), is(allOf(notNullValue(), equalTo(Product.ORANGE))));
        assertThat(checkout.parseItemName("orANge"), is(allOf(notNullValue(), equalTo(Product.ORANGE))));
        assertThat(checkout.parseItemName("orange"), is(allOf(notNullValue(), equalTo(Product.ORANGE))));

        // check spaces
        assertThat(checkout.parseItemName(" orange"), is(allOf(notNullValue(), equalTo(Product.ORANGE))));
        assertThat(checkout.parseItemName("orange "), is(allOf(notNullValue(), equalTo(Product.ORANGE))));
        assertThat(checkout.parseItemName(" orange "), is(allOf(notNullValue(), equalTo(Product.ORANGE))));

        // check extreme spaces
        assertThat(checkout.parseItemName("             apple                                                                  "), is(allOf(notNullValue(), equalTo(Product.APPLE))));

        assertThat(checkout.parseItemName("Apple"), is(allOf(notNullValue(), equalTo(Product.APPLE))));
        assertThat(checkout.parseItemName("APPLE"), is(allOf(notNullValue(), equalTo(Product.APPLE))));
        assertThat(checkout.parseItemName("aPpLe"), is(allOf(notNullValue(), equalTo(Product.APPLE))));
        assertThat(checkout.parseItemName("apple"), is(allOf(notNullValue(), equalTo(Product.APPLE))));

        // check spaces
        assertThat(checkout.parseItemName(" apple"), is(allOf(notNullValue(), equalTo(Product.APPLE))));
        assertThat(checkout.parseItemName("apple "), is(allOf(notNullValue(), equalTo(Product.APPLE))));
        assertThat(checkout.parseItemName(" apple "), is(allOf(notNullValue(), equalTo(Product.APPLE))));

        // check extreme spaces
        assertThat(checkout.parseItemName("                        apple                                 "), is(allOf(notNullValue(), equalTo(Product.APPLE))));
    }

    /**
     * Make sure we don't parse any invalid objects
     */
    @Test
    public void parseSingleInvalidItems() {
        assertThat(checkout.parseItemName("Pineapple"), is(nullValue()));
        assertThat(checkout.parseItemName("apples"), is(nullValue()));
        assertThat(checkout.parseItemName("appl e"), is(nullValue()));
        assertThat(checkout.parseItemName("ooranges"), is(nullValue()));
        assertThat(checkout.parseItemName("o range"), is(nullValue()));
        assertThat(checkout.parseItemName("             apple                                                                  !"), is(nullValue()));

        assertThat(checkout.parseItemName("ja;dfajhsdf ;iadf;asdfi abdflgihqpfo;gnqn;3i4ogp895gq;o24j5;o245Y \"$U^\"%(^Q($%Y45 Y($U %Y15y"), is(nullValue()));

    }

    /**
     * Ensure that we can parse multiple valid string representations correctly
     */
    @Test
    public void parseMultipleValidItems() {
        // Ensure we can parse a list of valid objects correctly
        List<Product> items = checkout.parseShoppingList(new String[] {"Apple", "Orange"});
        assertThatCartContentIsCorrect(items, 1, 1);
    }

    /**
     * Ensure that we can parse multiple valid string representations correctly
     */
    @Test
    public void parseLotsOfValidItems() {
        // Ensure we can parse a list of valid objects correctly
        List<Product> items = checkout.parseShoppingList(new String[] {"applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE",
                "applE", "orangE", " orangE", "orange", "oranGE", "orange ", "orangE"});
        assertThatCartContentIsCorrect(items, 10, 60);
    }

    /**
     * Ensure that we can parse multiple invalid string representations correctly
     */
    @Test
    public void parseMultipleInvalidItems() {
        // Ensure we can parse a list of invalid objects correctly
        List<Product> items = checkout.parseShoppingList(new String[] {"Orangese", "Appasdle", "Orafwecwnge"});
        assertThatCartContentIsCorrect(items, 0, 0);
    }

    /**
     * Make sure we parse valid objects and don't parse invalid objects when mixed together
     */
    @Test
    public void parseValidAndInvalidItems() {
        List<Product> items = checkout.parseShoppingList(new String[] {"APPLES", "APPLE", "Appasdle", "Orafwecwnge", "oRAngE"});
        assertThatCartContentIsCorrect(items, 1, 1);
    }

    /**
     * Ensure that the cart operates correctly when adding a single item and check the cart size
     */
    @Test
    public void addValidSingleItemsToCartAndCheckCartSize() {
        addItemToCart(checkout, Product.APPLE);
        assertThat(checkout.getCartItems().size(), is(equalTo(1)));
    }

    /**
     * Ensure that the cart operates correctly when adding a single item and check the cart cost
     */
    @Test
    public void addValidSingleItemsToCartAndCheckCost() {
        addItemToCart(checkout, Product.APPLE);
        assertThat(checkout.getCartTotalCost(), is(equalTo(60)));
    }

    /**
     * Ensure that we don't add invalid or null single objects to the cart and check cart size
     */
    @Test
    public void addInvalidSingleItemToCartAndCheckCartSize() {
        addItemToCart(checkout, null);
        assertThat(checkout.getCartItems().size(), is(equalTo(0)));
    }

    /**
     * Ensure that we don't add invalid or null single objects to the cart and check cart cost
     */
    @Test
    public void addInvalidSingleItemToCartAndCheckCartCost() {
        addItemToCart(checkout, null);
        assertThat(checkout.getCartTotalCost(), is(equalTo(0)));
    }

    /**
     * Make sure we can add multiple valid items to cart and check cart size
     */
    @Test
    public void addListOfValidItemsCheckItemCount() {
        addMultipleItemsToCart(checkout);
        assertThat(checkout.getCartItems().size(), is(equalTo(3)));
    }
    /**
     * Make sure we can add multiple valid items to cart and check price
     */
    @Test
    public void addListOfValidItemsCheckPrice() {
        addMultipleItemsToCart(checkout);
        assertThat(checkout.getCartTotalCost(), is(equalTo(110)));
    }
    /**
     * Make sure we can add multiple valid items to cart and check cart contents
     */
    @Test
    public void addListOfValidItemsCheckCartContents() {
        addMultipleItemsToCart(checkout);
        assertThatCartContentIsCorrect(checkout.getCartItems(), 2, 1);
    }

    /**
     * Convenience method to add multiple valid objects to the checkout
     * @param checkout
     */
    private void addMultipleItemsToCart(final Checkout checkout) {
        checkout.addItemsToCart(new ArrayList<Product>() {{
            add(Product.APPLE);
            add(Product.ORANGE);
            add(Product.ORANGE);
        }});
    }

    /**
     * Make sure we can add multiple invalid items and they aren't added to the cart and check price
     */
    @Test
    public void ensureCorrectPriceWithListOfInvalidItems() {
        checkout.addItemsToCart(new ArrayList<Product>() {{
            add(null);
            add(null);
        }});

        assertThat(checkout.getCartTotalCost(), is(equalTo(0)));
    }

    /**
     * Make sure we can add multiple invalid items and they aren't added to the cart and check item count
     */
    @Test
    public void ensureCorrectItemCountWithListOfInvalidItems() {
        checkout.addItemsToCart(new ArrayList<Product>() {{
            add(null);
            add(null);
        }});

        assertThat(checkout.getCartItems().size(), is(equalTo(0)));
    }

    /**
     * Check item count when adding multiple valid and invalid items
     */
    @Test
    public void ensureCorrectItemCountWithListOfValidItemsWithAnInvalidItem() {
        checkout.addItemsToCart(new ArrayList<Product>() {{
            add(null);
            add(Product.APPLE);
            add(null);
            add(Product.ORANGE);
            add(Product.ORANGE);
        }});

        assertThat(checkout.getCartItems().size(), is(equalTo(3)));
    }

    /**
     * Check price when adding multiple valid and invalid items
     */
    @Test
    public void ensureCorrectPriceWithListOfValidItemsWithAnInvalidItem() {
        checkout.addItemsToCart(new ArrayList<Product>() {{
            add(null);
            add(Product.APPLE);
            add(null);
            add(Product.ORANGE);
            add(Product.ORANGE);
        }});

        assertThat(checkout.getCartTotalCost(), is(equalTo(110)));
    }

    /**
     * Check that the printout is as we would expect
     */
    @Test
    public void checkPrintout() {
        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.ORANGE);

        assertThat(checkout.getPrintout(), is(equalTo("2 items in cart coming to a total of £0.85")));
    }

    /**
     * Check that we can apply a single apple offer
     */
    @Test
    public void checkSingleAppleOffer() {
        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.APPLE);
        assertThat(checkout.getCartTotalCost(), is(equalTo(60)));
    }

    /**
     * Check that we can apply a single apple offer with additional items
     */
    @Test
    public void checkSingleAppleOfferWithAdditionalItems() {
        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.ORANGE);

        assertThat(checkout.getCartTotalCost(), is(equalTo(145)));
    }

    /**
     * Check that we can apply a single orange offer
     */
    @Test
    public void checkSingleOrangeOffer() {
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        assertThat(checkout.getCartTotalCost(), is(equalTo(50)));
    }

    /**
     * Check that we can apply a single orange offer with additional items
     */
    @Test
    public void checkSingleOrangeOfferWithAdditionalItems() {
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.APPLE);

        assertThat(checkout.getCartTotalCost(), is(equalTo(135)));
    }

    /**
     * Check that we can apply multiple apple offers
     */
    @Test
    public void checkMultipleAppleOffer() {
        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.APPLE);

        addItemToCart(checkout, Product.APPLE);
        addItemToCart(checkout, Product.APPLE);
        assertThat(checkout.getCartTotalCost(), is(equalTo(120)));
    }

    /**
     * Check that we can apply multiple orange offers
     */
    @Test
    public void checkMultipleOrangeOffer() {
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);

        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        addItemToCart(checkout, Product.ORANGE);
        assertThat(checkout.getCartTotalCost(), is(equalTo(100)));
    }


    /**
     * Convenience method to add a single item to the cart
     * @param product The items to add
     */
    private void addItemToCart(final Checkout checkout, Product product) {
        checkout.addItemsToCart(new ArrayList<Product>(){{
            add(product);
        }});
    }

    /**
     * Convenience method to check cart contents is as expected
     * @param items The items in the cart
     * @param expectedNumberOfApples The expected number of apples
     * @param expectedNumberOfOranges The expected number of oranges
     */
    private void assertThatCartContentIsCorrect(final List<Product> items, int expectedNumberOfApples, int expectedNumberOfOranges) {
        if (items != null && items.size() > 0) {
            for (Product item : items) {
                if (item == Product.APPLE) {
                    expectedNumberOfApples--;
                } else if (item == Product.ORANGE) {
                    expectedNumberOfOranges--;
                }
            }
        }
        assertThat(expectedNumberOfApples + expectedNumberOfOranges, is(equalTo(0)));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        checkout = null;
    }
}
