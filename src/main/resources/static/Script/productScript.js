$(document).ready(function () {

    var information = {
        title: "戰神MARS 茉香奶綠風味水解乳清 | 雙倍濃郁茶味、清甜奶香 | 高品質蛋白補給"
        , productImg: "GreenMilkTea.webp"
        , productName: "水解乳清 墨香奶綠"
        , introductionList: [
            "✓ 全台首創100%完全水解乳清蛋白"
            , "✓ 蛋白質分最小、吸收效率最快"
            , "✓ 極低乳糖含量，較不易引起腸胃不適、長痘痘"
            , "✓ 每包隨手包提供26g優質蛋白質"
            , "抹茶與綠茶的雙倍濃郁茶味衝擊"
            , "香澀茶味入喉擴散，尾韻綻放茉莉花香"
            , "茶香與花香中隱隱透著清甜奶感，甜蜜無負擔"
        ]

        , price: 2500
        , description: ["品名｜戰神MARS水解乳清蛋白（茉香奶綠風味）"
            , "規格｜每袋20入or每盒60入，每份35g含26g蛋白質"
            , "產地｜台灣"
            , "保存方式｜避免陽光直射及高溫潮濕處，產品拆封後請立即食用"
            , "建議食用方式｜建議食用方式｜每包建議350-400ml水量，可依照個人口感調整"
            , "其他｜奶素可食、含咖啡因"
            , "本產品已投保產品責任險2000萬元，非實際賠償金額"
        ]

        , deliver: ["新竹物流-常溫"
            , "全家 取貨不付款 (B2C)"
            , "7-11 取貨不付款 (B2C)"
            , "7-11 取貨付款 (B2C)"
            , "7-11 跨境門市取貨不付款"
            , "7-11 跨境宅配"
            , "健身房自取（GYM MARS,核心肌地）"
        ]
        , pay: ["街口支付", "全家取貨付款", "Apple Pay", "信用卡付款", "LINE Pay", "7-11 取貨付款"]

    }


    var userID = getCookie("userID");

    if (userID) {
        $('#login').text(userID);
    }
    else {
        console.log("Username Notfound");
    }

    $("#login").on("click", function () {
        if (userID) {
            window.location.href = "/accountInfo";
        }
        else {
            window.location.href = "/login";
        }

    });


    function loadInformation(infor) {
        $title = $("#title");
        $title.text(infor.title);

        $productImg = $("#productImg");
        $productImg.attr('src', infor.productImg);

        $productName = $("#productName");
        $productName.text(infor.productName);

        $price = $("#price");
        $price.text(infor.price);



        $introductionList = $("#introductionList");

        $introductionList.empty();



        infor.introductionList.forEach(function (ele) { // 每個元素會被賦予 'fruit'


            if (ele == "") {
                $introductionList.append($('<br>'));
            }
            else {
                $introductionList.append($('<li>').text(ele));
            }





        });

        loadDescription($("#description"));


    }


    function loadDescription($ele) {

        $info = $("#info");

        $description = $("#description");
        $deliver_pay = $("#deliver_pay");
        $info.empty();

        if ($ele.is($description)) {
            information.description.forEach(function (ele) { // 每個元素會被賦予 'fruit'
                $span = $('<span>').text(ele);
                $div = $('<div>');
                $div.append($span);
                $info.append($div);
            });
        }
        else if ($ele.is($deliver_pay)) {

            $deliver_pay = $('<div>').addClass("deliver_pay");
            $deliver = $('<div>').append($('<div>').addClass("deliver").text("送貨方式"));
            $pay = $('<div>').append($('<div>').addClass("pay").text("付款方式"));
            information.deliver.forEach(function (ele) {
                $deliver.append($('<li>').text(ele));
            });

            information.pay.forEach(function (ele) {
                $pay.append($('<li>').text(ele));
            });
            $deliver_pay.append($deliver);
            $deliver_pay.append($pay);

            $info.append($deliver_pay);

        };




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


    var username = getCookie("username");
    var rememberMe = getCookie("rememberMe");

    console.log("Username found:", username, rememberMe);


    $("#minus, #plus").on("click", function () {

        $var = $(this);
        $qty = $("#quantity");
        qty = parseInt($qty.val());

        if ($var.is($("#minus"))) {
            qty = Math.max(qty - 1, 1);
            $qty.val(qty);
        }
        else if ($var.is($("#plus"))) {
            $qty.val(qty + 1);
        }

    });

    $("#description, #deliver_pay").on("click", function () {

        $description = $("#description");
        $deliver_pay = $("#deliver_pay");

        $description.removeClass();
        $deliver_pay.removeClass();


        if ($(this).is($description)) {
            $description.addClass("selected");
            $deliver_pay.addClass("unselected");
        }
        else if ($(this).is($deliver_pay)) {

            $deliver_pay.addClass("selected");
            $description.addClass("unselected   ");
        }

        loadDescription($(this));

    });


    const PRODUCT_ID = new URLSearchParams(location.search).get("name") || 2;
    const API_URL = `api/product/${encodeURIComponent(PRODUCT_ID)}`;
    function loadProduct() {
        return $.ajax({
            url: API_URL,
            type: "GET",
            dataType: "json"
        });
    }

    loadProduct().done(
        function (p) {
            information = p;
            loadInformation(p);

            console.log("success");
            console.log(p);
        }
    )
        .fail(
            function () {
                console.log("fail");
            }
        )






});