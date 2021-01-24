package pl.jkanclerz.voucherstore.sales.offer;

import pl.jkanclerz.voucherstore.sales.basket.BasketItem;

import java.math.BigDecimal;
import java.util.List;

public class OfferMaker {
    public BigDecimal getTotal() {
        return BigDecimal.valueOf(30);
    }

    public Offer calculateOffer(List<BasketItem> basketItems) {
    }
}
