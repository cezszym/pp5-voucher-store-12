package pl.jkanclerz.voucherstore.sales.basket;

import org.junit.Test;
import pl.jkanclerz.voucherstore.productcatalog.Product;
import pl.jkanclerz.voucherstore.sales.Inventory;
import pl.jkanclerz.voucherstore.sales.basket.Basket;

import java.util.UUID;

import static  org.assertj.core.api.Assertions.*;

public class BasketTest {

    @Test
    public void newlyCreatedBasketIsEmpty(){
        Basket basket = Basket.empty();

        assertThat(basket.isEmpty()).isTrue();
    }
    @Test
    public void itAllowToAddProduct() {

        //Arrange
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct("lego-2314");


        //Act
        basket.add(product1, thereIsAlwaysExistingProductInventory());

        //Assert
        assertThat(basket.isEmpty()).isFalse();
    }

    @Test
    public void itShowsProductCount() {
        //Arrange
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct("lego-2314");
        Product product2 = thereIsProduct("lego-5213");


        //Act
        basket.add(product1, thereIsAlwaysExistingProductInventory());
        basket.add(product2, thereIsAlwaysExistingProductInventory());

        //Assert
        assertThat(basket.getProductQuantities()).isEqualTo(2);
    }

    @Test
    public void itShowsSingleLineWhenSameProductIsAddedTwice() {
        //Arrange
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct("lego-2314");

        //Act
        basket.add(product1, thereIsAlwaysExistingProductInventory());
        basket.add(product1, thereIsAlwaysExistingProductInventory());

        //Assert
        assertThat(basket.getProductQuantities()).isEqualTo(1);

        basketContainsProductWithQuantity(basket, product1, 2);
    }

    @Test
    public void itStoreQuantityOfMultipleProducts() {
        //Arrange
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct("lego-2314");
        Product product2 = thereIsProduct("lego-2316");

        //Act
        basket.add(product1, thereIsAlwaysExistingProductInventory());
        basket.add(product1, thereIsAlwaysExistingProductInventory());
        basket.add(product2, thereIsAlwaysExistingProductInventory());

        //Assert
        assertThat(basket.getProductQuantities()).isEqualTo(2);

        basketContainsProductWithQuantity(basket, product1, 2);
        basketContainsProductWithQuantity(basket, product2, 1);
    }

    @Test
    public void itDennyToAddProductWithInventoryStateOf0() {
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct("lego-2314");

        assertThatThrownBy(() -> basket.add(product1, (productId) -> false))
                .hasMessage("Not enough products");
    }


    private Inventory thereIsAlwaysExistingProductInventory() {
      return (productId -> true);
    }

    private void basketContainsProductWithQuantity(Basket basket, Product product1, int expectedQuantity) {
        assertThat(basket.getBasketItems())
                .filteredOn(basketItem -> basketItem.getProductId().equals(product1.getId()))
                .extracting(bi -> bi.getQuantity())
                .first()
                .isEqualTo(expectedQuantity);
    }

    private Product thereIsProduct(String description) {
        var product = new Product(UUID.randomUUID());

        return product;
    }
}
