var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click',function () {
            _this.save();
        });

        $('#btn-modify').on('click',function () {
            _this.update();
        });

        $('#btn-delete').on('click',function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title : $('#title').val(),
            writer : $('#writer').val(),
            content : $('#content').val(),
            thumbnailsrc : img
        };

        console.log(data.content);
        $.ajax({
            type : 'POST',
            url : '/dog/post',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('등록되었습니다.');
            window.location.href = '/dog';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

    update : function () {
        var data = {
            title : $('#title').val(),
            writer : $('#writer').val(),
            content : $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type : 'PUT',
            url : '/dog/post/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('수정 되었습니다.');
            window.location.href = '/dog';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type : 'DELETE',
            url : '/dog/post/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function () {
            alert('삭제되었습니다.');
            window.location.href = '/dog';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }


};

main.init();