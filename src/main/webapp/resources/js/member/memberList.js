/**
 * Created by Administrator on 3/30/2018.
 * 会员列表JS
 */
;
$(document).ready(function () {

    window.search = function () {
        var data = $("#searchForm").serialize();
       // console.log(data);
        //加载列表
        loadUrl(ctx+"/member/listAjax.do",$("#dataTable"),data);
    };
    //加载列表
    loadUrl(ctx+"/member/listAjax.do",$("#dataTable"));
});
