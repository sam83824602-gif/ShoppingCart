$(document).ready(function () {



    var userID = getCookie("userID");

    if (userID) {
        window.location.href = "/";
    }
    else {
        console.log("Username Notfound");
    }

    // $("#login").on("click", function () {
    //     if (userID) {
    //         window.location.href = "/accountInfo";
    //     }
    //     else {
    //         window.location.href = "/login";
    //     }

    // });


    function getCookie(name) {

        const nameEQ = name + "=";
        const ca = document.cookie.split(';');


        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];

            console.log(c);

            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) {
                return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    }

    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }
    
    function setCookie(name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        // Set the cookie with name, value, expiration, and path
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    $('#login_btn').on('click', function () {

        var password = $('#password').val();
        var userID = $('#userID').val();

        account = {
            password: password,
            userID: userID,
        };

        console.log(account);
        $.ajax({
            url: '/accountLogin', // The URL to send the request to
            type: 'POST',                // The type of request
            // dataType: 'text',            // The expected data type of the response
            contentType: 'application/json; charset=utf-8', // Data encoding type
            data: JSON.stringify(account), // The data to send (stringify for JSON payload)
            success: function (response) {
                alert(response); // 顯示 "發生錯誤: 資料不存在
                setCookie("userID", userID);
                setCookie("password", password);


                var username = getCookie("userID");
                var password = getCookie("password");


                if (username) {
                    console.log("Username found:", username, password);
                }
                else {
                    console.log("Username Notfound");
                }

                setTimeout(function () {
                    window.location.href = "/";
                }, 1000);
            },
            error: function (xhr, status, error) {
                var errorResponse = JSON.parse(xhr.responseText).message;
                console.log(errorResponse);
                alert(errorResponse);
            }
        });

        return false;
    });

    $("#Register_btn").on("click", function () {
        window.location.href = "Register";
    });




});