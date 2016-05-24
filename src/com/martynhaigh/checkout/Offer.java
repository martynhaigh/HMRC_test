package com.martynhaigh.checkout;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Offer {
    //buy one, get one free on Apples
    BOGOF_APPLE {
        @Override
        OfferDetails applyOffer(List<Product> shoppingCart) {
            int count = (int) shoppingCart.stream().filter(product -> product == Product.APPLE).count();

            return new OfferDetails(count / 2, Product.APPLE.getPriceInPence());
        }
    },
    //3 for the price of 2 on Oranges
    THREE_FOR_TWO_ORANGES {
        @Override
        OfferDetails applyOffer(List<Product> shoppingCart) {
            int count = (int) shoppingCart.stream().filter(product -> product == Product.ORANGE).count();

            return new OfferDetails(count / 3, Product.ORANGE.getPriceInPence());
        }
    };

    /**
     * Iterates over all available offers and generates an {@link AppliedOffers} object with details
     * @param shoppingCart The cart to process for offers
     * @return Details of offers available
     */
    public static AppliedOffers applyAllOffers(List<Product> shoppingCart) {
        List<OfferDetails> offerDetails = Arrays.stream(Offer.values()).map(offer -> offer.applyOffer(shoppingCart)).collect(Collectors.toList());
        return new AppliedOffers(offerDetails);
    }

    abstract OfferDetails applyOffer(List<Product> shoppingCart);

    /**
     * Immutable class describing offers
     */
    static public class AppliedOffers {
        final List<OfferDetails> offers;
        final int totalSavings;

        AppliedOffers(List<OfferDetails> offers) {
            this.offers = offers;
            totalSavings = calculateSavings();
        }

        private int calculateSavings() {
            return offers.stream().mapToInt(OfferDetails::getTotalSaving).sum();
        }

        public int getTotalSavings() {
            return totalSavings;
        }
    }

    /**
     * Immutable class describing an offer
     */
    static public class OfferDetails {

        final int timesApplied;
        final int moneySavedForEachOffer;

        OfferDetails(int timesApplied, int moneySavedForEachOffer) {
            this.timesApplied = timesApplied;
            this.moneySavedForEachOffer = moneySavedForEachOffer;
        }

        public int getTotalSaving() {
            return timesApplied * moneySavedForEachOffer;
        }
    }
}
