package pl.jkanclerz.voucherstore.sales;

import pl.jkanclerz.voucherstore.productcatalog.Product;
import pl.jkanclerz.voucherstore.productcatalog.ProductCatalogFacade;
import pl.jkanclerz.voucherstore.sales.basket.Basket;
import pl.jkanclerz.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.jkanclerz.voucherstore.sales.offer.Offer;
import pl.jkanclerz.voucherstore.sales.offer.OfferMaker;
import pl.jkanclerz.voucherstore.sales.ordering.Reservation;

public class SalesFacade {
    private final InMemoryBasketStorage basketStorage;
    private final ProductCatalogFacade productCatalogFacade;
    private final CurrentCustomerContext currentCustomerContext;
    private final Inventory inventory;
    private final OfferMaker offerMaker;

    public SalesFacade(InMemoryBasketStorage basketStorage, ProductCatalogFacade productCatalogFacade, CurrentCustomerContext currentCustomerContext, Inventory inventory, OfferMaker offerMaker) {
        this.basketStorage = basketStorage;
        this.productCatalogFacade = productCatalogFacade;
        this.currentCustomerContext = currentCustomerContext;
        this.inventory = inventory;
        this.offerMaker = offerMaker;
    }

    public void addToBasket(String productId1) {
        Basket basket = basketStorage.loadForCustomer(currentCustomerContext.getCustomerId())
                .orElseGet(Basket::empty);

        Product product = productCatalogFacade.getById(productId1);

        basket.add(product, inventory);

        basketStorage.addForCustomer(currentCustomerContext.getCustomerId(), basket);
    }

    public Offer getCurrentOffer() {
        Basket basket = basketStorage.loadForCustomer(currentCustomerContext.getCustomerId())
                .orElseGet(Basket::empty);

        return offerMaker.calculateOffer(basket.getBasketItems());
    }

    public String acceptOffer(ClientDetails clientDetails, Offer seenOffer) {
        Basket basket = basketStorage.loadForCustomer(currentCustomerContext.getCustomerId())
                .orElseGet(Basket::empty);

        Offer currentOffer = offerMaker.calculateOffer(basket.getBasketItems());

        if (!seenOffer.equals(currentOffer)) {
            throw new OfferChangedException();
        }

        Reservation reservation = Reservation.of(currentOffer);

        return reservation.getId();
    }
}
