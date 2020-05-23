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
            boardtype : boardtype,
            title : $('#title').val(),
            writer : $('#writer').val(),
            content : $('#content').val(),
            thumbnailsrc : img
        };

        $.ajax({
            type : 'POST',
            url : '/'+boardtype+'/post',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            bootbox.alert("등록되었습니다.", function(){
                window.location.href = '/'+boardtype;
            });
        }).fail(function (error) {
            bootbox.alert(JSON.stringify(error));
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
            url : '/'+boardtype+'/post/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            bootbox.alert("수정되었습니다.", function(){
                window.location.href = '/'+boardtype;
            });
        }).fail(function (error) {
            bootbox.alert(JSON.stringify(error));
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type : 'DELETE',
            url : '/'+boardtype+'/post/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function () {
            bootbox.alert("삭제되었습니다.", function(){
                window.location.href = '/'+boardtype;
            });
        }).fail(function (error) {
            bootbox.alert(JSON.stringify(error));
        });
    }


};

main.init();