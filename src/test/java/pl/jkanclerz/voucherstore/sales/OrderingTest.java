package pl.jkanclerz.voucherstore.sales;

import org.junit.Test;

public class OrderingTest extends SalesTestCase {


    @Test
    public void itCreateOrderBasedOnCurrentOffer() {
        SalesFacade sales = thereIsSalesModule();
        String productId1 = thereIsProductAvailable();
        String productId2 = thereIsProductAvailable();
        customerId = thereIsCustomerWhoIsDoingSomeShoping();

        sales.addToBasket(productId1);
        sales.addToBasket(productId2);
        Offer seenOffer = sales.getCurrentOffer();
        String reservationId = sales.acceptOffer(seenOffer);

        thereIsPendingOrderWithId(reservationId);
    }

    private void thereIsPendingOrderWithId(String reservationId) {
    }
}
