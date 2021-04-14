$(document).ready(function () {
    $(document).on("submit", "#search-by-date-range", function (e) {
        let startDay = $('#start_date').val();
        let endDay = $('#end_date').val();
        let searchData = {
            "startDay": startDay, "endDay": endDay
        }
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/admin/statistic/search",
            data: JSON.stringify(searchData),
            success: function (statistics) {
                console.log(statistics);
                let trs = ``;
                jQuery.each(statistics, function (index, statistic) {
                    trs += `<tr>
                                <th scope="row">
                                    <b class="each_day">${statistic["day"]}</b>
                                </th>
                            <td>
                                <b>${statistic["orderAmount"]}</b>
                            </td>
                            <td>
                                 <b>${statistic["productAmount"]}</b>
                            </td>
                            <td>
                                <b>${statistic["total"]} VNĐ</b>
                            </td>
                        </tr>`;
                })
                $('#for-show-when-search').html(trs);
            }
        })
        e.preventDefault();
    })
    $(document).on("click", "#button-export-excel-file", function (e) {
        let dayTags = $('.each_day');
        let startDay = dayTags[0].innerHTML;
        let endDay = dayTags[dayTags.length - 1].innerHTML;
        $('#start-date-in-form').val(startDay);
        $('#end-date-in-form').val(endDay);
        $('#form-download').submit();
    })
})