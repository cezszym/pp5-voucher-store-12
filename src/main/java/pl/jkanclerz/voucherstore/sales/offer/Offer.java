package pl.jkanclerz.voucherstore.sales.offer;

import java.math.BigDecimal;
import java.util.List;

public class Offer {
    private final List<OrderLine> orderItems;
    private final BigDecimal total;


    private final int productCount;

    public Offer(List<OrderLine> orderItems, BigDecimal total) {

        this.orderItems = orderItems;
        this.total = total;
        this.productCount = orderItems.size();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<OrderLine> getOrderItems() {
        return orderItems;
    }
    public int getProductCount() {
        return productCount;
    }
}
