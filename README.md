# HMRC Technical Test

I wanted to jot down a few thoughts about this process in hindsight - letting you know what went well and what I'd change.

Went well:
* I feel that the use of functional enums is a nice way of abstracting away the functionality for both the Products and Offers.
* The Java 8 streaming API was a really nice way of removing a lot of boilerplate code but still retaining readability.

Would improve:
* Although appropriate for this example, I'd go back and refactor the price and quantities to use longs rather than integers.
* I'd reduce the first few tests of the CheckoutTest which test parsing of the products, splitting them out in to individual tests with appropriate names instead of lumping them all in to one huge function.
