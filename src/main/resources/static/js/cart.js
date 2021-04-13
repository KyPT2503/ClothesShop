function addToCart(clothesId, amount, isAdd) {
    $.ajax({
        type: "PUT",
        url: "/cart/add/" + clothesId + "/" + amount + "/" + isAdd,
        success: function (messages) {
            let code = messages[0];
            let message = messages[1];
            // handle message
            let alert = `<div class="alert alert-primary alert-dismissible fade show">
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

$(document).ready(function () {
    $(document).on("click", ".button-change-amount", function (e) {
        let div;
        if (e.target.tagName === 'I') {
            div = $(e.target).parent().parent();
        } else {
            div = $(e.target).parent();
        }
        let input = $(div.children()[1]);
        console.log(input.attr("id"));
        console.log(input.val());
        addToCart(input.attr("id"), input.val(), 0);
    })
    $(document).on("click", ".button-remove-from-cart", function (e) {
        let button;
        if (e.target.tagName === 'I') {
            button = $(e.target).parent();
        } else {
            button = $(e.target);
        }
        console.log(button);
        $.ajax({
            type: "DELETE",
            url: "/cart/remove/" + button.attr("id"),
            success: function () {
                // giam so luong tren cai gio hang
                let showSize = $('#for-cart-size');
                let cartSize = showSize.text().toString();
                cartSize = cartSize.substr(1, cartSize.length - 2);
                console.log('Cart size:');
                console.log(cartSize);
                showSize.text('(' + (+cartSize - 1) + ')');
                // xoa dong day di
                button.parent().parent().remove();
                // hien thong bao
                let alert = `<div class="alert alert-dark alert-dismissible fade show">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Thông báo!</strong> Đã xóa 1 sản phẩm khỏi giỏ hàng của bạn.
                        </div>`;
                let div = $('#for-message');
                div.append(alert);
            }
        })
    })
    $(document).on("click", ".button-add-to-cart", function (e) {
        let clothesId = $('.button-add-to-cart').attr("id");
        let amount = $('#amount-in-detail').val();
        addToCart(clothesId, amount, 0);
        e.preventDefault();
    })
})