$(document).on("submit", ".product-item", function (event) {
    var $form = $(this);
    $.get("addItem", $form.serialize(), function (response) {
        notify(JSON.parse(response));
    });

    event.preventDefault(); // Important! Prevents submitting the form.
});


