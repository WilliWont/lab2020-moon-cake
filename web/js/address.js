function setAddress(value) {
    $("#addr-id").val(value);
    $("tr").removeClass("selected-row");
    $("#address-row-" + value).addClass("selected-row");
}