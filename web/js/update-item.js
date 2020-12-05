$(document).on("submit", ".product-item", function (event) {
    var $form = $(this);
    $.get("getItem", $form.serialize(), function (response) {
        setModalField(response);
        $('#createModal').modal('toggle');
    });

    event.preventDefault(); // Important! Prevents submitting the form.
});

$(document).ready(function () {

    $("#btn-update-cake").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        // Get form
        var form = $('#form-create-cake')[0];

        // Create an FormData object
        var data = new FormData(form);

        // disabled the submit button
        $("#btn-update-cake").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "update",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                alert(data);
                $("#btn-update-cake").prop("disabled", false);
            },
            error: function (e) {

                console.log("ERROR : ", e);
                $("#btn-update-cake").prop("disabled", false);

            }
        });

    });
});

