package pl.jkanclerz.voucherstore.sales.productd;

import pl.jkanclerz.voucherstore.productcatalog.ProductCatalogFacade;

public class ProductCatalogDetailsProvider implements ProductDetailsProvider {

    private final ProductCatalogFacade productCatalogFacade;

    public ProductCatalogDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        this.productCatalogFacade = productCatalogFacade;
    }


    @Override
    public ProductDetails getByProductId(String productId) {
        var product = productCatalogFacade.getById(productId);

        return new ProductDetails(product.getId(), product.getDescription(), product.getPrice());
    }
}
