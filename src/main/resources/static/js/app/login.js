var login = {
    init : function () {
        var _this = this;

        $('#btn-login').on('click',function (e) {
            e.preventDefault();
            _this.login();
        });


    },
    login : function () {
        var data = {
            userid : $('#userid').val(),
            password : $('#password').val(),
            prevpage : prevPage
        };

        $.ajax({
            type : 'POST',
            url : '/loginprocess',
            /*dataType : 'json',*/
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (data) {
            if(data == "NotAuth") {
                alert("인증되지 않은 계정입니다."); return;
            } else if(data == "WrongInfo") {
                alert("입력하신 아이디&비밀번호가 맞지 않습니다."); return;
            } else {
                window.location.href=data;
            }
        }).fail(function (error) {
            alert("error : " + JSON.stringify(error));
            console.log(JSON.stringify(error));
        });

    }




};

login.init();