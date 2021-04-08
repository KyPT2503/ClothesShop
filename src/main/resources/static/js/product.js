$(document).ready(function () {
    let imageCount = 0;
    $(document).on("click", "#more-image-button", function () {
        let img = ``
        $('#for-input-image').append(`<div>
                                    <img style="margin-bottom: 5px;" id="${imageCount}" src="" width="100%">
                                    <input class="input-image" type="file" name="clothesDetail.imageFiles" accept="image/*"/>
                                </div>`);
        let imageInputs = $('input[type="file"]')[imageCount];
        imageCount += 1;
    })
    $(document).on("change", ".input-image", function (e) {
        console.log(e.target);
        let input = $(e.target);
        input.css("display", "none");
        /*--------------*/
        let reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById((imageCount - 1) + '').src = e.target.result;
        };
        reader.readAsDataURL(this.files[0]);
    })
});