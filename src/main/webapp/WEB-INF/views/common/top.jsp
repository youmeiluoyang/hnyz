<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div class="bw-header">
    <div class="bw-title" onclick="javascript:window.location.href='${basepath}/common/index.do'"><i class="bw-icon"></i>中国邮政微信${login_admin.pubnumTypeName}管理系统
    </div>
    <div class="bw-flex-main"></div>
    <div class="bw-user">
        <img src="${basepath}/resources/images/sample/icon_userpic.png" alt="" class="bw-head">
        <span class="bw-welcome">${login_admin.loginName}</span>
    </div>
    <div class="bw-mail">
        <a href="${basepath}/common/index.do" class="bw-icon"><span class="count"></span></a>
    </div>
    <div class="bw-modifypwd">
        <span class="bw-modify"><a href="javascript:">修改密码</a></span>
    </div>
    <div class="bw-exit"><a href="${basepath}/common/logout.do">退出</a></div>
</div>
<form id="modifyPwdForm">
<%--修改密码--%>
<div class="bw-mask bw-hide">
    <div class="change-password bw-mask-con">
        <div class="password-form">
            <div class="password-item">
                <label>原密码</label>
                <input type="password" name="prePwd" />
            </div>
            <div class="password-item">
                <label>新密码</label>
                <input type="password" name="newPwd" />
            </div>
            <div class="password-item">
                <label>确认新密码</label>
                <input type="password" name="confirmPwd" />
            </div>
        </div>
        <div class="bw-form-btns">
            <a href="javascript:" class="bw-btn bw-btn-form-confirm">确定</a>
            <a href="javascript:" class="bw-btn bw-btn-form-cancle">取消</a>
        </div>
    </div>
</div>
</form>
<script type="text/javascript">
    $(function(){
        $(".bw-modify").click(function(){
            $("input[name=prePwd]").val("");
            $("input[name=newPwd]").val("");
            $("input[name=confirmPwd]").val("");
            $(".bw-mask").removeClass("bw-hide");
        });

        $(".bw-btn-form-cancle").click(function(){
            $(".bw-mask").addClass("bw-hide");
        });

        // 修改密码
        $(".bw-btn-form-confirm").click(function(){
            var prePwd = $("input[name=prePwd]").val();
            var newPwd = $("input[name=newPwd]").val();
            var confirmPwd = $("input[name=confirmPwd]").val();
            if(prePwd == ""){
                createAlertMsgMainWin("原密码不能为空.", "red");
                $("input[name=prePwd]").focus();
                $("input[name=prePwd]").select();
                return false;
            }
            if(newPwd == ""){
                createAlertMsgMainWin("新密码不能为空.", "red");
                $("input[name=newPwd]").focus();
                $("input[name=newPwd]").select();
                return false;
            }
            if(confirmPwd == ""){
                createAlertMsgMainWin("确认新密码不能为空.", "red");
                $("input[name=confirmPwd]").focus();
                $("input[name=confirmPwd]").select();
                return false;
            }
            if(newPwd != confirmPwd){
                createAlertMsgMainWin("新密码与确认密码不一致.", "red");
                $("input[name=confirmPwd]").focus();
                $("input[name=confirmPwd]").select();
                return false;
            }
            $.ajax({
                type: "post",
                async: true,
                url: "${basepath}/admin/modifyPwd.do",
                data: $('#modifyPwdForm').serialize(),
                dataType: "json",
                success: function (result) {
                    if (result.status == "success") {
                        createAlertMsgMainWin("修改密码操作成功，请重新登录系统.", "green", function(){
                            window.location.href = "${basepath}/common/logout.do";
                        });
                        $(".bw-mask").addClass("bw-hide");
                    } else {
                        createAlertMsgMainWin(result.message, "red");
                    }
                }
            });
        });
    });
</script>