
let $cartTemp = $("#cart-template");

function getOrder(value) {
    if (value == null || value == 0)
        return;

//    $("#addr-id").val(value);
    $("tr").removeClass("selected-row");
    $("#order-row-" + value).addClass("selected-row");


    $.get("getOrder?txtOrderID=" + value, function (response) {
        let requestError = true;
        if (response != null) {
            let rep = response.split('&');
            let result = JSON.parse(rep[0]);

            if (result.result != null &&
                    result.result.includes("success")) {
                requestError = false;
                let items = JSON.parse(rep[1]);
                let cart = JSON.parse(rep[2]);
                let address = JSON.parse(rep[3]);
                updateCartView(items, cart, address);
            } else if (result.result == null ||
                    result.result.includes("error")) {
                requestError = false;
                alert(result.msg);
                $("#cart-template").removeClass("to-display");
                $("#cart-template").addClass("to-hide");
            }
        }

        if (requestError) {
            $("#cart-template").removeClass("to-display");
            $("#cart-template").addClass("to-hide");
            alert("error sending request");
        }
    });
}

function updateCartView(itemList, cartInfo, addressInfo) {
    updateCartInfo(cartInfo);
    updateCartAddress(addressInfo);
    updateCartItem(itemList);
    $("#cart-template").addClass("to-display");
    $("#cart-template").removeClass("to-hide");
}

function updateCartInfo(cartInfo) {
    $("#cart-count").text(cartInfo.cart_amount);
    $("#cart-total").text('$' + cartInfo.cart_total);
}

function updateCartAddress(addressInfo) {
    $("#cart-recipient").text(addressInfo.name);
    $("#cart-address").text(addressInfo.address);
    $("#cart-phone").text(addressInfo.phone);
}

function updateCartItem(itemList) {
    let $list = $("#cart-product-list");
    $('.li-item').remove();

    if (itemList != null) {
        console.log('list len: ' + itemList.length);
        let $item = $list.children("#item-template").clone();
        console.log('item: ' + $item);
        $item.removeAttr('id');
        $item.removeClass("to-fullyhide");
        $item.addClass("li-item");
        for (let i = 0; i < itemList.length; i++) {
            let $itemToPrepend = $item.clone();
            console.log("i:" + i);
            let cake = itemList[i];
            console.log(cake);
            $itemToPrepend.find("#cart-quantity").text(cake.quantity).removeAttr('id');
            $itemToPrepend.find("#cart-name").text(cake.name);
            $itemToPrepend.find("#cart-price").text('$' + cake.price).removeAttr('id');

            $list.prepend($itemToPrepend);
        }
    } else {
        $list.prepend("<li>no item found</li>")
    }
}