function updateValidation(formName, isValid, msg) {
    let $inputField = $("input[name='" + formName + "'");
    if (isValid) {
        $inputField.addClass("is-valid");
        $inputField.siblings(".valid-feedback").text(msg);
        $inputField.removeClass("is-invalid");
    } else {
        $inputField.addClass("is-invalid");
        $inputField.siblings(".invalid-feedback").text(msg);
        $inputField.removeClass("is-valid");
    }
}