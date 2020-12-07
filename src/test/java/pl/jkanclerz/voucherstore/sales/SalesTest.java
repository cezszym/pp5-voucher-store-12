package pl.jkanclerz.voucherstore.sales;

import org.junit.Test;
import static  org.assertj.core.api.Assertions.*;

public class SalesTest {

    @Test
    public void itAllowProductToBasket(){

        //Arrange
        SalesFacade sales = thereIsSalesModule();
        String productId1 = thereIsProductAvailable();
        String productId2 = thereIsProductAvailable();
        String customerId = thereIsCustomerWhoIsDoingSomeShoping();

        sales.addToBasket(productId1);
        sales.addToBasket(productId2);
//        //Act
//        sales.addToBasket(productId);

        //Assert
        thereIsXproductsInCustomersBasket(1, customerId);
    }

    private void thereIsXproductsInCustomersBasket(int i, String customerId) {
        assertThat(true).isTrue();
    }

    private String thereIsCustomerWhoIsDoingSomeShoping() {
        return null;
    }

    private String thereIsProductAvailable() {
        return null;
    }

    private SalesFacade thereIsSalesModule() {
        return null;
    }
}
