package com.martynhaigh.checkout;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void CheckApplePrice() throws Exception {
        assertThat(Product.APPLE.getPriceInPence(), is(equalTo(60)));
    }

    @Test
    public void CheckOrangePrice() throws Exception {
        assertThat(Product.ORANGE.getPriceInPence(), is(equalTo(25)));
    }
}
