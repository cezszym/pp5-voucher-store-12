package pl.jkanclerz.voucherstore.productcatalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Id
    private String productID;
    private String description;
    private String picture;
    private BigDecimal price;

    Product() {}

    public Product(UUID productId) {
        this.productID = productId.toString();
    }

    public String getId() {
        return productID;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Product setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
