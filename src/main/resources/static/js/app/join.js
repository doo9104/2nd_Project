var join = {
    init : function () {
        var _this = this;

        $('#btn-next').on('click',function (e) {
            e.preventDefault();
            const step1Form =  document.querySelector('#step1');
            step1Form.classList.add('animated', 'fadeOut');
            step1Form.addEventListener('animationend', function() {
                $('#step1').hide();
                $('#step2').show();
                step1Form.classList.remove('animated', 'fadeOut');
            });

        });

        $('#btn-prev').on('click',function (e) {
            e.preventDefault();
            const step2Form =  document.querySelector('#step2');
            step2Form.classList.add('animated', 'fadeOut');
            step2Form.addEventListener('animationend', function() {
                $('#step2').hide();
                $('#step1').show();
                step2Form.classList.remove('animated', 'fadeOut');
            });
        });

        $('#btn-join').on('click',function (e) {
            e.preventDefault();
            _this.join();
        });


    },
    join : function () {
        var data = {
            userid : $('#userid').val(),
            email : $('#email').val(),
            nickname : $('#username').val(),
            password : $('#password').val()
        };

        $.ajax({
            type : 'POST',
            url : '/join',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data),
            beforeSend:function(){
                $('body').loading({
                    stoppable: false
                });},
            complete:function(){
                $('body').loading('stop');
            }
        }).done(function () {
            alert('인증을 위한 이메일이 발송되었습니다. 메일을 확인해주세요.');
            //window.location.href = '/login';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    }




};

join.init();