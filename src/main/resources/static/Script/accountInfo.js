$(document).ready(function () {

    var order = {
        orderList: [{ number: "20251216041813703", date: "2025-12-16", price: "2645", href: "/orders/6940dd8587cdfaab78ab4842" },
        { number: "20241216041813703", date: "2025-11-16", price: "2700", href: "/orders/6940dd8587cdfaab78ab4843" },
        ]
    }

    var account = {
        lastName: "",
        firstName: "",
        email: "",
        passwordCheck: "",
        password: "",
        userID: "",
    };

    var userID = getCookie("userID");
    var password = getCookie("password");

    account.userID = userID;
    account.password = password;

    if (userID) {
        $('#login').text(userID);
    }
    else {
        window.location.href = "/login";
        console.log("Username Notfound");
    }


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


    function SetAccountInfo() {

        var lastName = $('#lastName').val();
        var firstName = $('#firstName').val();
        var email = $('#email').val();
        var password = $('#password').val();
        var passwordCheck = $('#passwordCheck').val();


        account.email = email;
        account.firstName = firstName;
        account.lastName = lastName;
        account.password = password;
        account.passwordCheck = passwordCheck;
        account.userID = getCookie("userID");

        return account;
    }


    function loadDescription($ele) {

        $info = $("#info");
        $accountInfo = $("#accountInfo");
        $order = $("#order");
        $info.empty();
        $info.removeClass();



        if ($ele.is($accountInfo)) {



            $form_personalInfo = $('<form>')
                .addClass("form-container")
                .attr("id", "form_personalInfo");

            $form_passwordManage = $('<form>')
                .addClass("form-container")
                .attr("id", "form_passwordManage");

            $label_LastName = $('<label>').text("姓氏").attr('for', 'lastName');
            $input_LastName = $('<input>').attr({
                type: 'text',
                id: 'lastName',
                value: account.lastName,
                class: 'lastName',
                required: 'required'
            });


            $label_firstName = $('<label>').text("名字").attr('for', 'firstName');
            $input_firstName = $('<input>').attr({
                type: 'text',
                id: 'firstName',
                value: account.firstName,
                class: 'firstName',
                required: 'required'
            });

            $label_email = $('<label>').text("信箱").attr('for', 'email');
            $input_email = $('<input>').attr({
                type: 'text',
                id: 'email',
                value: account.email,
                class: 'email',
                required: 'required'
            });


            $button_Save = $('<button>')
                .text("儲存")
                .addClass('modify')
                .attr('id', 'modify')
                .attr('type', 'submit');

            $form_personalInfo.append($label_LastName);
            $form_personalInfo.append($input_LastName);
            $form_personalInfo.append($label_firstName);
            $form_personalInfo.append($input_firstName);
            $form_personalInfo.append($label_email);
            $form_personalInfo.append($input_email);
            $form_personalInfo.append($button_Save);


            $label_password = $('<label>').text("密碼修改").attr('for', 'password');
            $input_password = $('<input>').attr({
                type: 'password',
                id: 'password',
                class: 'password',
                required: 'required'
            });


            $label_passwordCheck = $('<label>').text("密碼確認").attr('for', 'passwordCheck');
            $input_passwordCheck = $('<input>').attr({
                type: 'password',
                id: 'passwordCheck',
                class: 'password',
                required: 'required'
            });
            $button_passwordSave = $('<button>')
                .text("修改密碼")
                .addClass('modify')
                .attr('id', 'modifyPassword')
                .attr('type', 'submit');

            $form_passwordManage.append($label_password);
            $form_passwordManage.append($input_password);
            $form_passwordManage.append($label_passwordCheck);
            $form_passwordManage.append($input_passwordCheck);
            $form_passwordManage.append($button_passwordSave);




            $info.append($form_personalInfo);
            $info.append($form_passwordManage);
            $info.addClass("Info");
        }
        else if ($ele.is($order)) {



            $table = $('<table>');
            $thead = $('<thead>');
            $tbody = $('<tbody>');
            $tr = $('<tr>');
            $th_number = $('<th>').text("訂單號碼").addClass("w-50");
            $th_date = $('<th>').text("訂單日期").addClass("w-20");
            $th_price = $('<th>').text("合計").addClass("w-20");
            $th_refer = $('<th>').addClass("w-10");

            $tr.append($th_number);
            $tr.append($th_date);
            $tr.append($th_price);
            $tr.append($th_refer);


            $thead.append($tr);
            $table.append($thead);

            order.orderList.forEach(function (ele) {


                $tr = $('<tr>');
                $td_number = $('<td>').text(ele.number).addClass("w-50");
                $td_date = $('<td>').text(ele.date).addClass("w-20");
                $td_price = $('<td>').text(ele.price).addClass("w-20");
                $td_refer = $('<td>').addClass("w-10");

                $a = $('<a>').text("查閱").addClass("btnOrder").attr("href", ele.href);
                $td_refer.append($a);

                $tr.append($td_number);
                $tr.append($td_date);
                $tr.append($td_price);
                $tr.append($td_refer);
                $tbody.append($tr);
            });


            $table.append($tbody);
            $info.append($table);


        };




    }
    $.ajax({
        url: '/getAccountInfo', // The URL to send the request to
        type: 'POST',                // The type of request
        // dataType: 'text',            // The expected data type of the response
        contentType: 'application/json; charset=utf-8', // Data encoding type
        data: JSON.stringify(account), // The data to send (stringify for JSON payload)
        success: function (response) {
            account = JSON.parse(response);

            loadDescription($("#accountInfo"));
        },
        error: function (xhr, status, error) {
            var errorResponse = JSON.parse(xhr.responseText).message;
            console.log(errorResponse);
            alert(errorResponse); // 顯示 "發生錯誤: 資料不存在"
        }
    });
    $("#accountInfo, #order").on("click", function () {

        $accountInfo = $("#accountInfo");
        $order = $("#order");

        $accountInfo.removeClass();
        $order.removeClass();


        if ($(this).is($accountInfo)) {
            $accountInfo.addClass("selected");
            $order.addClass("unselected");
        }
        else if ($(this).is($order)) {

            $order.addClass("selected");
            $accountInfo.addClass("unselected");
        }

        loadDescription($(this));

    });


    $(document).ready(function () {
        $(document).on('submit', '#form_personalInfo', function (e) {
            e.preventDefault(); // 阻止頁面重整

            SetAccountInfo();

            $.ajax({
                url: '/putAccountInfo', // The URL to send the request to
                type: 'PUT',                // The type of request
                // dataType: 'text',            // The expected data type of the response
                contentType: 'application/json; charset=utf-8', // Data encoding type
                data: JSON.stringify(account), // The data to send (stringify for JSON payload)
                success: function (response) {
                    alert(response);

                },
                error: function (xhr, status, error) {
                    var errorResponse = JSON.parse(xhr.responseText).message;
                    console.log(errorResponse);
                    alert(errorResponse); // 顯示 "發生錯誤: 資料不存在"
                }
            });
            // 在此處處理資料
        });
    });

    $(document).ready(function () {
        $(document).on('submit', '#form_passwordManage', function (e) {
            e.preventDefault();
            SetAccountInfo();

            $.ajax({
                url: '/putPassword', // The URL to send the request to
                type: 'PUT',                // The type of request
                // dataType: 'text',            // The expected data type of the response
                contentType: 'application/json; charset=utf-8', // Data encoding type
                data: JSON.stringify(account), // The data to send (stringify for JSON payload)
                success: function (response) {
                    alert(response);

                },
                error: function (xhr, status, error) {
                    var errorResponse = JSON.parse(xhr.responseText).message;
                    console.log(errorResponse);
                    alert(errorResponse); // 顯示 "發生錯誤: 資料不存在"
                }
            });
            // 在此處處理資料
        });
    });


    $('#logout').on('click', function () {

        setCookie("userID", null, 0);
        setCookie("password", null, 0);

        alert("登出成功!!");

        setTimeout(function () { 
            window.location.href = "/login"; }, 1000);


    })





});