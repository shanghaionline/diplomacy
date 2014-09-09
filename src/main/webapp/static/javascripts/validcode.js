$(document).ready(function () {
    $("#valid_code_button").on("click", function (evt) {
        var phone = $("#valid_code_target").val();
        $.post(contextPath + "/message/sendvalidcode", {phone: phone}, function (data) {
            if (data.status == "success") {
                alert("验证码发送成功");
            }
        });
    });
});
