$(document).ready(function () {

    var itemList = {

        wheyList: [{ name: "[水解乳清] 茉香奶綠風味", imgsrc: "IMG/GreenMilkTea.webp", href: "/product?name=GreenMilkTea", price: "2500" },
        { name: "[水解乳清] 黑可可風味", imgsrc: "IMG/DarkCacao.webp", href: "/product?name=DarkCacao", price: "2600" },
        { name: "[水解乳清] 奶茶風味", imgsrc: "IMG/MilkTea.webp", href: "/product?name=MilkTea", price: "2400" },
        { name: "[水解乳清] 烏龍奶茶風味", imgsrc: "IMG/OolongMilkTea.webp", href: "/product?name=OolongMilkTea", "price": "2200" },
        { name: "[水解乳清] 茗金萱奶茶無甜風味", imgsrc: "IMG/JinXuanMilkTea.webp", href: "/product?name=JinXuanMilkTea", "price": "2700" },
        ]

        , bcaaList: [
        ]

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
    
    function loadItemList(infor) {
        $Whey = $("#Whey");
        $Whey.empty();
        $BCAA = $("#BCAA");
        $BCAA.empty();


        itemList.wheyList.forEach(function (Whey) {
            $div = $('<div>');
            $a = $('<a>').attr("href", Whey.href);
            $img = $('<img>').attr('src', Whey.imgsrc).addClass("img");
            $p = $('<p>').addClass('name').text(Whey.name);

            $a.append($img);
            $div.append($a);
            $div.append($p);
            $Whey.append($div);
        });


        itemList.bcaaList.forEach(function (BCAA) {
            $div = $('<div>');
            $a = $('<a>').attr('href', BCAA.href);
            $img = $('<img>').attr('src', BCAA.imgsrc).addClass("img");
            $p = $('<p>').addClass("name").text(BCAA.name);


            $a.append($img);
            $div.append($a);
            $div.append($p);
            $BCAA.append($div);

        });
    }

    function getCookie(name) {

        const nameEQ = name + "=";
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) {
                return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    }




    const API_URL = `api/ItemList/`;

    function loadProduct() {
        return $.ajax({
            url: API_URL,
            type: "GET",
            dataType: "json"
        });
    }

    loadProduct().done(
        function (p) {
            console.log(p);
            itemList = p;
            loadItemList(p);
        }
    )
        .fail(
            function () {
                console.log("fail");

                loadItemList(itemList);
            }
        )



});