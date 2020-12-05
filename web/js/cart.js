jQuery(function ($) {
    $('.product-quantity').on('input', function () {
        let $input = $(this);

        let $form = $(this).parent(".form-cart-item");
        $.get("quantity", $form.serialize(), function (response) {
            let rep = JSON.parse(response);
            if (rep.result.includes("success"))
                $("#cart-total").text('$' + rep.total);
            else if (rep.result.includes("error")) {
//                notify(rep);
            }
            $input.val(rep.quantity);
        });
    });
});