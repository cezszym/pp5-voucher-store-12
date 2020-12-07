package pl.jkanclerz.voucherstore.productcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Configuration
public class ProductCatalogConfiguration {

    public ProductCatalogFacade productCatalogFacade() {
        return new ProductCatalogFacade(new HashMapProductsStorage());
    }

    @Bean
    public ProductCatalogFacade fixturesAwareProductCatalogFacade(ProductsStorage productsStorage) {
        ProductCatalogFacade catalogFacade = new ProductCatalogFacade(productsStorage);

//        String pId1 = catalogFacade.createProduct();
//        catalogFacade.applyPrice(pId1, BigDecimal.TEN);
//        catalogFacade.updateDetails(pId1, "My Fancy Product 1","My Fancy Product Url");
//
//        String pId2 = catalogFacade.createProduct();
//        catalogFacade.applyPrice(pId2, BigDecimal.TEN);
//        catalogFacade.updateDetails(pId2, "My Fancy Product 2","My Fancy Product Url");
//
//        String pId3 = catalogFacade.createProduct();
//        catalogFacade.applyPrice(pId3, BigDecimal.TEN);
//        catalogFacade.updateDetails(pId3, "My Fancy Product 3","My Fancy Product Url");
        return catalogFacade;
    }
}
