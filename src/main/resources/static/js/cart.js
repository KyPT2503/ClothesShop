function addToCart(clothesId, amount, isAdd) {
    $.ajax({
        type: "PUT",
        url: "/cart/add/" + clothesId + "/" + amount + "/" + isAdd,
        success: function (messages) {
            let code = messages[0];
            let message = messages[1];
            // handle message
            let alert = `<div class="alert alert-dark alert-dismissible fade show">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Thông báo!</strong> ${message}
                        </div>`;
            let div = $('#for-message');
            div.append(alert);
            // handle code
            if (code === '3') {
                let divToShowCartSize = $('#for-cart-size');
                console.log("Size of cart:")
                let cartSize = divToShowCartSize.text().toString();
                cartSize = cartSize.substr(1, cartSize.length - 2);
                cartSize = (+cartSize) + 1;
                divToShowCartSize.text('(' + cartSize.toString() + ')');
            }
        }
    })
}