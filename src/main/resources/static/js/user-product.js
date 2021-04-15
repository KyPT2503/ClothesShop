function search() {
    let name = $('#name').val();
    let colorId = $('#colorId').val();
    let sizeId = $('#sizeId').val();
    let brandId = $('#brandId').val();
    let categoryId = $('#categoryId').val();
    let priceFrom = $('#priceFrom').val();
    let priceTo = $('#priceTo').val();
    let searchData = {
        "name": name,
        "colorId": colorId,
        "sizeId": sizeId,
        "brandId": brandId,
        "categoryId": categoryId,
        "priceFrom": priceFrom,
        "priceTo": priceTo
    }
    console.log('Data for search:');
    console.log(searchData);
    $.ajax({
        dataType: 'json',
        contentType: 'application/json',
        type: "POST",
        data: JSON.stringify(searchData),
        url: "/product/search",
        success: function (clothes) {
            console.log(clothes);
            $('#for-show-products').html(``);
            jQuery.each(clothes, function (index, cloth) {
                insertProduct(cloth.id, cloth["clothesDetail"]["name"], cloth["clothesDetail"]["sources"][0], cloth["clothesDetail"]["price"]);
            })
        }
    })
}

function insertProduct(id, name, source, price) {
    let div = `<div class="col-md-4">
                        <div class="product-item">
                            <div class="product-title">
                                <a href="#">${name}</a>
                                <div class="ratting">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </div>
                            </div>
                            <div class="product-image">
                                <a href="">
                                    <img src="/${source}"
                                         alt="Product Image">
                                </a>
                                <div class="product-action">
                                    <a class="button-add-to-cart" id="${id}" href=""><i class="fa fa-cart-plus"></i></a>
                                    <a href="#"><i class="fa fa-heart"></i></a>
                                    <a href="/product/detail/${id}"><i class="fa fa-search"></i></a>
                                </div>
                            </div>
                            <div class="product-price">
                                <h3>${price} Đồng</h3>
                                <a class="btn" href=""><i class="fa fa-shopping-cart"></i>Mua hàng</a>
                            </div>
                        </div>
                    </div>`;
    $('#for-show-products').append(div);
}

$(document).ready(function () {
    $('#search-form').submit(function (e) {
        search();

        e.preventDefault();
    });
    $(document).on("click", ".button-add-to-cart", function (e) {
        let button = $(e.target);
        if (button.attr("id") === undefined) {
            button = button.parent();
        }
        console.log(button.attr("id"));
        addToCart(button.attr("id"), 1, 1);
        e.preventDefault();
    })
})