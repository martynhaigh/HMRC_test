package com.martynhaigh.checkout;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class OfferTest {

    /**
     * Ensure that no offer is applied with an invalid cart
     */
    @Test
    public void CheckBogofOfferWithAnInvalidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.APPLE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        assertThat(appliedOffers.getTotalSavings(), is(equalTo(0)));
    }

    /**
     * Ensure that an offer is applied with a valid cart
     */
    @Test
    public void CheckBogofOfferWithAValidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.APPLE);
            add(Product.APPLE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        assertThat(appliedOffers.getTotalSavings(), is(equalTo(60)));
    }

    /**
     * Ensure that multiple offers of the same type applied with a valid cart
     */
    @Test
    public void CheckMultipleBogofOfferWithAValidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.APPLE);
            add(Product.APPLE);
            add(Product.APPLE);
            add(Product.APPLE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        assertThat(appliedOffers.getTotalSavings(), is(equalTo(120)));
    }

    /**
     * Ensure that no offer is applied with an invalid cart
     */
    @Test
    public void CheckThreeForTwoOfferWithAnInvalidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.ORANGE);
            add(Product.ORANGE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        assertThat(appliedOffers.getTotalSavings(), is(equalTo(0)));
    }

    /**
     * Ensure that an offer is applied with a valid cart
     */
    @Test
    public void CheckThreeForTwoOfferWithAValidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        assertThat(appliedOffers.getTotalSavings(), is(equalTo(25)));
    }

    /**
     * Ensure that multiple offers of the same type applied with a valid cart
     */
    @Test
    public void CheckMultipleThreeForTwoOfferWithAValidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        assertThat(appliedOffers.getTotalSavings(), is(equalTo(50)));
    }

    /**
     * Ensure that multiple offers of different types are applied with a valid cart
     */
    @Test
    public void CheckMultipleOffersWithAValidCart() {
        List<Product> cart = new ArrayList<Product>() {{
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.ORANGE);
            add(Product.APPLE);
            add(Product.APPLE);
            add(Product.APPLE);
            add(Product.APPLE);
        }};
        Offer.AppliedOffers appliedOffers = Offer.applyAllOffers(cart);

        // Savings of 2 * 25 + 2 * 60
        assertThat(appliedOffers.getTotalSavings(), is(equalTo(170)));
    }
}
