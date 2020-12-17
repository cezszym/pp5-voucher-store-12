package pl.jkanclerz.voucherstore.sales;

import org.junit.Before;
import org.junit.Test;
import pl.jkanclerz.voucherstore.productcatalog.ProductCatalogConfiguration;
import pl.jkanclerz.voucherstore.productcatalog.ProductCatalogFacade;
import pl.jkanclerz.voucherstore.sales.basket.Basket;

import java.math.BigDecimal;
import java.util.UUID;

import static  org.assertj.core.api.Assertions.*;

public class SalesTest {

    private ProductCatalogFacade productCatalog;
    private InMemoryBasketStorage basketStorage;
    private Inventory alwaysExistsInventory;
    private CurrentCustomerContext currentCustomerContext;
    private String customerId;

    @Before
    public void setUp() {
        productCatalog = new ProductCatalogConfiguration().productCatalogFacade();
        basketStorage = new InMemoryBasketStorage();
        alwaysExistsInventory = (productId -> true);
        currentCustomerContext = () -> customerId;

    }

    @Test
    public void itAllowProductToBasket(){

        //Arrange
        SalesFacade sales = thereIsSalesModule();
        String productId1 = thereIsProductAvailable();
        String productId2 = thereIsProductAvailable();
        customerId = thereIsCustomerWhoIsDoingSomeShoping();

        sales.addToBasket(productId1);
        sales.addToBasket(productId2);

        //Assert
        thereIsXproductsInCustomersBasket(2, customerId);
    }

    @Test
    public void itAllowProductToBasketByMultipleCustomers(){

        //Arrange
        SalesFacade sales = thereIsSalesModule();
        String productId1 = thereIsProductAvailable();
        String productId2 = thereIsProductAvailable();

        customerId = thereIsCustomerWhoIsDoingSomeShoping();
        var customer1 = new String(customerId);

        sales.addToBasket(productId1);
        sales.addToBasket(productId2);

        customerId = thereIsCustomerWhoIsDoingSomeShoping();
        var customer2 = new String(customerId);

        sales.addToBasket(productId2);

        //Assert
        thereIsXproductsInCustomersBasket(2, customer1);
        thereIsXproductsInCustomersBasket(1, customer2);
    }

    private void thereIsXproductsInCustomersBasket(int productsCount, String customerId) {
        Basket basket = basketStorage.loadForCustomer(customerId)
                .orElse(Basket.empty());
        assertThat(basket.getProductQuantities()).isEqualTo(productsCount);
    }

    private String thereIsCustomerWhoIsDoingSomeShoping() {
        return UUID.randomUUID().toString();
    }

    private String thereIsProductAvailable() {
        var id = productCatalog.createProduct();
        productCatalog.applyPrice(id, BigDecimal.valueOf(10));
        productCatalog.updateDetails(id, "lego", "http://picture.img");
        return id;
    }

    private SalesFacade thereIsSalesModule() {
        return new SalesFacade(basketStorage, productCatalog, currentCustomerContext, alwaysExistsInventory);
    }
}
