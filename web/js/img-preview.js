function previewFile(input) {
    var file = $("input[type=file]").get(0).files[0];

    if (file) {
        var reader = new FileReader();

        reader.onload = function () {
            $("#previewImg").attr("src", reader.result);
        }
        $("#input-img-update").prop("checked", true);
        reader.readAsDataURL(file);
    }
}