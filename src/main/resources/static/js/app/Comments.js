var commentsmanager = (function () {
    var getAll = function (obj, callback) {
        console.log("getting all...");

        $.getJSON('/comments/dog/'+obj, callback);
    };

    // 댓글 추가
    var add = function (obj, callback) {
        console.log("adding comment...");

        $.ajax({
            type : 'post',
            url : '/comments/dog/' + obj.id,
            data : JSON.stringify(obj),
            dataType : 'json',
            contentType: "application/json",
            success:callback
        });
    };

    //댓글 삭제
    var remove = function (obj, callback) {
        console.log("deleting comment...");

        $.ajax({
            type:'delete',
            url: '/comments/dog/'+ obj.bid+"/" + obj.cid,
            dataType:'json',
            contentType: "application/json",
            success:callback
        });
    };

    // 댓글 수정
    var update = function (obj, callback) {
        console.log("updating comment....");

        $.ajax({
            type:'put',
            url: '/comments/dog/'+ obj.bid,
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
