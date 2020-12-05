$(document).ready(function () {

    $("#btn-create-cake").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        // Get form
        var form = $('#form-create-cake')[0];

        // Create an FormData object
        var data = new FormData(form);

        // disabled the submit button
        $("#btn-create-cake").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "create",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                alert(data);
                if (!data.includes("error")) {
                    $(function () {
                        $('#createModal').modal('toggle');
                    });
                    $("#form-create-cake").trigger('reset');
                    $("#previewImg").attr("src", "img/placeholder.png");
                }
                $("#btn-create-cake").prop("disabled", false);
            },
            error: function (e) {

                console.log("ERROR : ", e);
                $("#btn-create-cake").prop("disabled", false);

            }
        });

    });

    $("#btn-create-modal").click(function () {
        $("#previewImg").attr("src", "img/placeholder.png");
        $("#form-create-cake").trigger('reset');
        toggleConfBtn(0);
    });

    $(".btn-get-item").click(function () {
        toggleConfBtn(1);
    });
});

function toggleConfBtn(val) {
    if (val === 0) {
        $("#btn-update-cake").addClass("d-none");
        $("#btn-create-cake").removeClass("d-none");
    } else if (val === 1) {
        $("#btn-update-cake").removeClass("d-none");
        $("#btn-create-cake").addClass("d-none");
    }
}

function setModalField(product) {
    let productToDisplay = JSON.parse(product);
    $("#input-cake-id").val(productToDisplay.id);
    $("#input-cake-name").val(productToDisplay.name);
    $("#input-cake-desc").val(productToDisplay.desc);
    $("#input-cake-cate").val(productToDisplay.cateID);
    $("#input-cake-price").val(productToDisplay.price);
    $("#input-cake-quantity").val(productToDisplay.quantity);
    $("#input-cake-dateCreate").val(productToDisplay.createDate);
    $("#input-cake-dateExpire").val(productToDisplay.expireDate);
    $("#previewImg").attr("src", productToDisplay.img);
}
