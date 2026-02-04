$(document).ready(function () {

    var account = {
    };

    // Example usage to get an "account" value

    $('#info').on('submit', function () {

        var lastName = $('#lastName').val();
        var firstName = $('#firstName').val();
        var email = $('#email').val();
        var password = $('#password').val();
        var passwordCheck = $('#passwordCheck').val();
        var userID = $('#userID').val();

        account = {
            lastName: lastName,
            firstName: firstName,
            email: email,
            passwordCheck: passwordCheck,
            password: password,
            userID: userID,
        };

        $.ajax({
            url: '/accountRegister', // The URL to send the request to
            type: 'POST',                // The type of request
            // dataType: 'text',            // The expected data type of the response
            contentType: 'application/json; charset=utf-8', // Data encoding type
            data: JSON.stringify(account), // The data to send (stringify for JSON payload)
            success: function (response) {
                alert(response); 
                setTimeout(function () {
                    window.location.href = "login";
                }, 1000);
            },
            error: function (xhr, status, error) {
                var errorResponse = JSON.parse(xhr.responseText).message;
                console.log(errorResponse);
                alert(errorResponse); // 顯示 "發生錯誤: 資料不存在"
            }
        });

        return false;
    });

  






});