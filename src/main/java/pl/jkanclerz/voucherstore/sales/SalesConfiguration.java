package pl.jkanclerz.voucherstore.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkanclerz.voucherstore.productcatalog.ProductCatalogFacade;
import pl.jkanclerz.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.jkanclerz.voucherstore.sales.offer.OfferMaker;
import pl.jkanclerz.voucherstore.sales.productd.ProductCatalogDetailsProvider;
import pl.jkanclerz.voucherstore.sales.productd.ProductDetails;
import pl.jkanclerz.voucherstore.sales.productd.ProductDetailsProvider;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker) {
        return new SalesFacade(
                new InMemoryBasketStorage(),
                productCatalogFacade,
                () -> "customer",
                (productId) -> true,
                offerMaker
        );
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean
    ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogDetailsProvider(productCatalogFacade);
    }
}
