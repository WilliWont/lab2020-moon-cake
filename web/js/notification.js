
function notify(notification) {
    let result = notification.result;
    let msg = notification.msg;
    $("#div-notif").removeClass("toast-hide");
    $("#notification-body").text("error sending request");
    if (msg !== null) {
        $("#notification-body").text(msg);

        if (result === null || result.includes("error")) {
            $("#notification-body").addClass("toast-invalid");
            $("#notification-body").removeClass("toast-valid");
            $("#notification-header").addClass("toast-invalid");
            $("#notification-header").removeClass("toast-valid");
        } else if (result.includes("success")) {
            $("#notification-body").addClass("toast-valid");
            $("#notification-body").removeClass("toast-invalid");
            $("#notification-header").addClass("toast-valid");
            $("#notification-header").removeClass("toast-invalid");
        }

    }
    setTimeout(() => {
        $("#div-notif").addClass("toast-hide");
    },
            1250);
}

function notifyDanger(msg) {
    $("#div-notif").removeClass("toast-hide");
    $("#notification-body").text(msg);
    $("#notification-body").addClass("toast-invalid");
    $("#notification-body").removeClass("toast-valid");
    $("#notification-header").addClass("toast-invalid");
    $("#notification-header").removeClass("toast-valid");

    setTimeout(() => {
        $("#div-notif").addClass("toast-hide");
    },
            2000);
}