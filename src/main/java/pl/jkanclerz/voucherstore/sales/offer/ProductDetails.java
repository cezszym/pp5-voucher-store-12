package pl.jkanclerz.voucherstore.sales.offer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

public class ProductDetails {
    private String productId;
    private String description;
    private BigDecimal unitPrice;

    public ProductDetails(String productId ,String description, BigDecimal unitPrice) {
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getProductId() {
        return productId;
    }
}
