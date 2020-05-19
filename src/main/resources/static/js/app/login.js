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
            password : $('#password').val()
        };

        $.ajax({
            type : 'POST',
            url : '/loginprocess',
            /*dataType : 'json',*/
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (data) {
            console.log("success : " + JSON.stringify(data));
            window.location.href = '/';
        }).fail(function (error) {
            alert("error : " + JSON.stringify(error));
            console.log(JSON.stringify(error));
        });

    }




};

login.init();