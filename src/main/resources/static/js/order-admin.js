$(document).ready(function () {
    $(document).on("click", ".button-for-show-order-detail", function (e) {
        let button = $(e.target);
        $.ajax({
            data: 'application/json',
            contentType: 'application.json',
            type: "GET",
            url: "/admin/order/detail/" + button.attr("id"),
            success: function (data) {
                let content = `<tr>
                                    <th>Sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Giá tiền</th>
                               </tr>`;
                jQuery.each(data, function (index, detail) {
                    content += `<tr>
                                <td>${detail["clothes"]["clothesDetail"]["name"]}</td>
                                <td>${detail["amount"]}</td>
                                <td>${detail["clothes"]["clothesDetail"]["price"]}</td>
                              </tr>`;
                })
                $('#for-show-order-detail').html(content);
            }
        })
    })
    $(document).on("click", ".for-change-order-state", function (e) {
        let target = $(e.target);
        let stateId = target.attr("value");
        let orderId = target.attr("name");
        console.log(stateId);
        console.log(orderId);
        $.ajax({
            url: "/admin/order/change-state/" + orderId + "/" + stateId,
            type: "PUT",
            success: function () {
                console.log('state changed.');
            }
        })
    })
    // remove order for user
    $(document).on("click", ".button-for-remove-order", function (e) {
        let button = $(e.target);
        $.ajax({
            type: "DELETE",
            url: "/order/remove/" + button.attr("id"),
            success: function (message) {
                console.log(message);
                let alert = `<div class="alert alert-primary alert-dismissible fade show">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Thông báo!</strong> ${message}
                        </div>`;
                let div = $('#for-show-message');
                div.append(alert);
                if (message === "Hủy đơn hàng thành công.") {
                    button.parent().parent().remove();
                }
            }
        })
    })
    $(document).on("submit", "#search-by-date-range", function (e) {
        console.log('Search run');
        let startDay = $('#start_date').val();
        let endDay = $('#end_date').val();
        let searchDate = {
            "startDay": startDay,
            "endDay": endDay
        }
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/admin/order/search",
            data: JSON.stringify(searchDate),
            success: function (orders) {
                console.log(orders);
                let trs = ``;
                jQuery.each(orders, function (index, order) {
                    trs += `<tr>
                <th scope="row"><b>#${order["id"]}</b></th>
                <td>
                    <b>${order["date"]}</b>
                </td>
                <td>
                    <b>${order["orderContact"]["customerName"]}</b>
                </td>
                <td>
                    <b>${order["orderContact"]["address"]}</b>
                </td>
                <td>
                    <b>${order["orderContact"]["phoneNumber"]}</b>
                </td>
                <td>
                    <b>${order["payment"]["name"]}</b>
                </td>
                <td>
                    <label>
                        <input class="form-control-sm for-change-order-state" type="radio" value="1"
                               name="${order['id']}" ${(order['orderState']['id'] == 1) ? 'checked' : ''}>Xác
                        nhận |
                    </label>
                    <label>
                        <input class="form-control-sm for-change-order-state" type="radio" value="2"
                               name="${order['id']}" ${(order['orderState']['id'] == 2) ? 'checked' : ''}>Đang
                        gửi |
                    </label>
                    <label>
                        <input class="form-control-sm for-change-order-state" type="radio" value="3"
                               name="${order['id']}" ${(order['orderState']['id'] == 3) ? 'checked' : ''}>Đã
                        gửi
                    </label>
                </td>
                <td>
                    <button type="button" class="btn btn-primary button-for-show-order-detail" data-toggle="modal"
                            data-target="#modal" id="${order['id']}">
                        Xem
                    </button>

                </td>
            </tr>`
                })
                $('#for-show-when-search').html(trs);
            }
        })
        e.preventDefault();
    })
})