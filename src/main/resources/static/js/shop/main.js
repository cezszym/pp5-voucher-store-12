
const getProducts = () => {
    return fetch('/api/products')
      .then(response => response.json())
      .catch((error) => console.log(error))
}

const createHtmlElementFromString = (template) => {
    let parent = document.createElement("div");
    parent.innerHTML = template.trim();

    return parent.firstChild
}

const createProductComponent = (product) => {
    const template = `
        <li class="product">
            <div class="productName">
                <h3>${product.description}</h3>
            </div>
            <div class="product__image-container">
                <img
                        class="product__image"
                        src="${product.picture}"
                />
            </div>
            <p class="product__price">$ ${product.price}</p>
            <button class="product__button" data-product-id="${product.productID}">
                Add to basket
            </button>
        </li>
    `

    return createHtmlElementFromString(template)
}

const handleAddToBasket = (productId) => {
    return fetch(`/api/add-product/${productId}`, {
        method: 'POST',
    })

}

const refreshBasket = (offer) => {
    document.querySelector('.basket__count').innerText = offer.productCount

//    document.querySelector('.basket__value').innerText = offer.total + ' PLN'
}

const refreshCurrentOffer = () => {
    return fetch(`/api/current-offer`)
        .then(response => response.json())
        .then(offer => refreshBasket(offer))
}

const initializeAddToBasketHandler = (element) => {
    const btn = element.querySelector('button.product__button')

    btn.addEventListener('click', (event) => {
        const productId = event.target.getAttribute('data-product-id')
        handleAddToBasket(productId)
            .then( () => refreshCurrentOffer() )
    })
}

const productList = document.querySelector(".products")
getProducts().then(products => {
    products
        .map( product => createProductComponent(product) )
        .forEach( li => {
            productList.appendChild(li)
            initializeAddToBasketHandler(li)
        })
} )