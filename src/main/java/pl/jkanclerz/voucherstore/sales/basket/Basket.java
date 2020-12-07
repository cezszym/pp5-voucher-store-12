package pl.jkanclerz.voucherstore.sales.basket;

import pl.jkanclerz.voucherstore.productcatalog.Product;
import pl.jkanclerz.voucherstore.sales.Inventory;
import pl.jkanclerz.voucherstore.sales.exceptions.NotEnoughQuantityException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {

    private final HashMap<String, Product> products;
    private final HashMap<String, Integer> productsCount;

    public Basket() {
        products = new HashMap<>();
        productsCount = new HashMap<>();
    }

    public static Basket empty() {
        return new Basket();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void add(Product product, Inventory inventory) {
        if(!isAvailable(product.getId(), inventory)){
            throw new NotEnoughQuantityException();
        }
        if(!isInBasket(product)){
            putToBasket(product);
        } else {
            increaseProductQuantity(product);
        }
    }

    private boolean isAvailable(String productId, Inventory inventory) {
        return inventory.isAvailable(productId);
    }

    private void putToBasket(Product product) {
        products.put(product.getId(), product);
        productsCount.put(product.getId(), 1);
    }

    private void increaseProductQuantity(Product product) {
        productsCount.put(product.getId(), productsCount.get(product.getId()) + 1);
    }

    private boolean isInBasket(Product product) {
        return productsCount.containsKey(product.getId());
    }

    public int getProductCount() {
        return products.size();
    }

    public List<BasketItem> getBasketItems() {
        return productsCount.entrySet()
                .stream()
                .map(es -> new BasketItem(es.getKey(), es.getValue()))
                .collect(Collectors.toList());
    }
}
