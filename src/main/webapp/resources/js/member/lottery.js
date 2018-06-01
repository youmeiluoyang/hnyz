
;
$(document).ready(function () {

    window.search = function () {
        var data = $("#searchForm").serialize();
        //加载列表
        loadUrl(ctx+"/member/lotteryListAjax.do",$("#dataTable"),data);
    };
    //加载列表
    loadUrl(ctx+"/member/lotteryListAjax.do",$("#dataTable"),$("#searchForm").serialize());


    $("#dataTable").delegate(".updateState","click",function () {
       var that = $(this);
       var ids = that.attr("ids");
       $.post(
           ctx+"/member/updateReward.do",
           {ids:ids},
           function (res) {
               if(res.status=='success'){
                   that.parent().prev().prev().text("已发放");
                   that.remove();
               }else{
                   alert("系统异常,请联系开发人员");
               }
           },
           'json'
       )
    });


    $("#export").click(function () {
        location.href = ctx+"/member/export.do?" + $("#searchForm").serialize();
    });
});
