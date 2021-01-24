package pl.jkanclerz.voucherstore.sales;

import org.junit.Before;
import org.junit.Test;
import pl.jkanclerz.voucherstore.sales.basket.Basket;

import java.math.BigDecimal;
import java.util.UUID;

import static  org.assertj.core.api.Assertions.*;

public class CollectingProductsTest extends SalesTestCase {

    @Before
    public void setUp() {
        productCatalog = thereIsProductCatalog();
        basketStorage = thereIsBasketStore();
        alwaysExistsInventory = thereIsInventory();
        currentCustomerContext = thereIsCurrentCustomerContext();

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

    @Test
    public void itGenerateOfferBasedOnCurrentBasket() {
        SalesFacade sales = thereIsSalesModule();
        String productId1 = thereIsProductAvailable();
        String productId2 = thereIsProductAvailable();

        customerId = thereIsCustomerWhoIsDoingSomeShoping();
        var customer1 = new String(customerId);

        sales.addToBasket(productId1);
        sales.addToBasket(productId1);
        sales.addToBasket(productId2);

        Offer offer = sales.getCurrentOffer();

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(30));
    }

    @Test
    public void itGenerateOfferBasedOnCurrentBasketWithSingleProduct() {
        SalesFacade sales = thereIsSalesModule();
        String productId1 = thereIsProductAvailable();

        customerId = thereIsCustomerWhoIsDoingSomeShoping();
        var customer1 = new String(customerId);

        sales.addToBasket(productId1);

        Offer offer = sales.getCurrentOffer();

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.valueOf(10));
    }

    private void thereIsXproductsInCustomersBasket(int productsCount, String customerId) {
        Basket basket = basketStorage.loadForCustomer(customerId)
                .orElse(Basket.empty());
        assertThat(basket.getProductQuantities()).isEqualTo(productsCount);
    }


}
