
$(document).ready(function () {
    $("#btn-checkout").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        // Get form
        var form = $('#form-checkout')[0];

        // Create an FormData object
        var data = new FormData(form);

        // disabled the submit button
        $("#btn-checkout").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "confirmCheckout",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                let rep = JSON.parse(data);
                let toDisable = false;
                if (rep.result.includes("success")) {
                    window.location.replace(rep.addr);
                } else if (rep.result.includes("error")) {
                    if (rep.msg != null) {
                        updateCheckoutCart(rep.item_error, rep.cart_price);
                        notifyDanger(rep.msg);

                        if (rep.cart_price == 0) {
                            toDisable = true;
                            alert("empty cart, checkout unavailable");
                            $("#form-checkout :input").prop("disabled", toDisable);
                        }
                    }

                    try {
                        updateValidation("txtAddrLine", rep.txtAddrLine.length == 0,
                                rep.txtAddrLine);

                        updateValidation("txtAddrCity", rep.txtAddrCity.length == 0,
                                rep.txtAddrCity);

                        updateValidation("txtAddrDistrict", rep.txtAddrDistrict.length == 0,
                                rep.txtAddrDistrict);

                        updateValidation("txtAddrWard", rep.txtAddrWard.length == 0,
                                rep.txtAddrWard);

                        updateValidation("txtNameF", rep.txtNameF.length == 0,
                                rep.txtNameF);

                        updateValidation("txtNameL", rep.txtNameL.length == 0,
                                rep.txtNameL);

                        updateValidation("txtAddrPhone", rep.txtAddrPhone.length == 0,
                                rep.txtAddrPhone);
                    } catch (err) {
                        alert("something went wrong, please try again later");
                        console.log(err);
                    }

                } else {
                    alert("something went wrong, please try again later");
                }

                $("#btn-checkout").prop("disabled", toDisable);
            },
            error: function (e) {

                console.log("ERROR : ", e);
                $("#btn-checkout").prop("disabled", false);

            }
        });

    });
});

function updateCheckoutCart(cart, total) {
    let items = cart.split(";");
    for (let i = 0; i < items.length; i++) {
        let item = items[i].split(',');
        let newQuantity = item[1];
        console.log("id:" + item[0] + "q: " + item[1]);
        let $itemEle = $("#product-id-" + item[0]);
        let $itemEleQuantity = $itemEle.children("strong");
        if (newQuantity > 0) {
            $itemEle.addClass("text-warning");
            $itemEle.removeClass("text-danger");
        } else {
            $itemEle.addClass("text-danger");
            $itemEle.removeClass("text-warning");
        }
        $itemEleQuantity.text(newQuantity);
    }
    $("#cart-total-view").addClass("text-warning");
    $("#cart-total-view").text("$" + total);
}

