
const getProducts = () => {
    return fetch('/api/products')
      .then(response => response.json())
      .catch((error) => console.log(error))
}

const createHtmlElementFromString = (template, el) => {
    let parent = document.createElement(el);
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

    return createHtmlElementFromString(template, 'div')
}

const handleAddToBasket = (productId) => {
    return fetch(`/api/add-product/${productId}`, {
        method: 'POST',
    })

}



const refreshBasket = (offer) => {
    document.querySelector('.basket__count').innerText = offer.productCount
    document.querySelector('.totalValue').innerText = '$' + offer.total
    refreshItemsList(offer.orderItems)
}

const refreshItemsList = (items) => {
    const cartList = document.querySelector('.itemsList');
    console.log(cartList);
    cartList.innerHTML=''
    items.forEach(item => {
        const template = `
            <li>
                <p class="itemName">${ item.description }</p>
                <p class="itemQuantity">x ${item.quantity}</p>
                <p class="price">$ ${item.unitPrice}</p>
                <p></p>
            </li>
        `
        cartList.appendChild(createHtmlElementFromString(template, 'div'))
    })
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

const initializeShowCartHandler = () => {
    const btn = document.querySelector('.showCart')
    const cartBox = document.querySelector('.cartSummary')
    btn.addEventListener('click', (event) => {
        cartBox.classList.toggle("hidden")
    })
}

const productList = document.querySelector(".products")
initializeShowCartHandler()
getProducts().then(products => {
    products
        .map( product => createProductComponent(product) )
        .forEach( li => {
            productList.appendChild(li)
            initializeAddToBasketHandler(li)
        })
} )