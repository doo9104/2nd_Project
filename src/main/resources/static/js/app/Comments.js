var commentsmanager = (function () {

    var getAll = function (obj, callback) {

        $.getJSON('/comments/'+boardtype+'/'+obj, callback);
    };

    // 댓글 추가
    var add = function (obj, callback) {

        $.ajax({
            type : 'post',
            url : '/comments/'+boardtype+'/' + obj.id,
            data : JSON.stringify(obj),
            dataType : 'json',
            contentType: "application/json",
            success:callback
        });
    };

    //댓글 삭제
    var remove = function (obj, callback) {

        $.ajax({
            type:'delete',
            url: '/comments/'+boardtype+'/'+ obj.bid+"/" + obj.cid,
            dataType:'json',
            contentType: "application/json",
            success:callback
        });
    };

    // 댓글 수정
    var update = function (obj, callback) {

        $.ajax({
            type:'put',
            url: '/comments/'+boardtype+'/'+ obj.bid,
            dataType:'json',
            data: JSON.stringify(obj),
            contentType: "application/json",
            success:callback
        });
    };


   return {
        getAll : getAll,
        add : add,
        remove : remove,
        update : update
    }
})();
