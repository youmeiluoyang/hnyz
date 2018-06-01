;

var basePath = "http://ymly888.cn";
var Status = {
    success:"SUCCESS"
};
var Address = {
    isLogin:basePath + "/api/wx/isLogin.do",
    loginUser:basePath + "/api/wx/toLogin.do?scope=snsapi_userinfo",
    loginBase:basePath + "/api/wx/toLogin.do?scope=snsapi_base",
    fansInfo:basePath + "/api/wx/getFansInfo.do?"
}

var Util = {

    showModal:function(title,content,showClose){
        $("#myModalLabel").text(title);
        $("#modalTip").text(content);
        var showClose = showClose?showClose:false;
        if(!showClose){
            $("#closeModal").addClass("hide");
        }else{
            $("#closeModal").removeClass("hide");
        }
        $('#myModal').modal({
            backdrop:'static',
            keyboard:false
        })
    },
    hideModal:function(){
        $('#myModal').modal('hide');
    },
    showLoading:function(){
        $('#loadingModal').modal({
            backdrop:'static',
            keyboard:false
        })
    },
    hideLoading:function(){
        $('#loadingModal').modal('hide');
    }
}