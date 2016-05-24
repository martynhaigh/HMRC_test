package com.martynhaigh.checkout;

public enum Product {
    APPLE(60), ORANGE(25);

    // Operate in pence for memory size considerations and ease
    private final int priceInPence;

    Product(final int priceInPence) {
        this.priceInPence = priceInPence;
    }

    /**
     * Get the product cost
     * @return The product cost in pence
     */
    public int getPriceInPence() {
        return priceInPence;
    }
}
