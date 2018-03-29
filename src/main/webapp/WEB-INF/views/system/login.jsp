<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title>邮美洛阳公众号后台管理系统</title>
	<script type="text/javascript" src="${basepath}/resources/js/jquery.md5.js"></script>
	<link rel="stylesheet" href="${ctx }/resources/css/base.css" />
</head>

<style>
.error {
	color: red;
	margin-top: 5px;
}
</style>

<body class="login_wrapper">
	<div class="login">
		<div class="login_main">
			<div class="login_title">邮美洛阳公众号后台管理系统</div>
			<div class="login_bd">
				<form>
					<div class="login_item">
						<div class="item_name">
							<label>用户名</label>
						</div>
						<div class="item_ipt">
							<input type="text" id="loginName" class="i_txt" value=""/>
						</div>
					</div>
					<div class="login_item">
						<div class="item_name">
							<label>密码</label>
						</div>
						<div class="item_ipt">
							<input id="loginPwd" type="password" class="i_txt" value=""/>
						</div>
					</div>
					<div class="login_submit">
						<button id="login" type="button" class="btn_submit">登录</button>
						<span class="error"></span>
						<div class="login_others">
							<a href="javascript:onclick=identify();" class="forget">忘记密码</a>
							<a href="javascript:onclick=reset();" class="reset" >重置</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		$(function() {

			//禁用后退
			$(function() {
				$(window).load(function(){
					window.history.forward(1); 
				});
			});

			// 登录操作
			$("#login").click(function() {
				if ($("#loginName").val() == "") {
					$("#loginName").focus();
					$(".error").html("请输入账号");
				} else if ($("#loginPwd").val() == "") {
					$("#loginPwd").focus();
					$(".error").html("请输入密码");
				}else {
                    $("#login").attr("disabled", "disabled");
                    $("#login").html("登录中，请稍候...");
					$.ajax({
						type : "post",
						url : "${ctx}/system/toLogin.do",
						data : {
							loginName : $("#loginName").val(),
							loginPwd : $.md5($("#loginPwd").val())
						},
						dataType : "json",
						success : function(data) {
							if (data.status == "success") { // 登录成功
								window.location.href = "${ctx}/common/index.do";
							} else {
								$("#code_src").click();
								$("#verifyCode").val("");
								if (data.code == "-1") { // 用户不存在
                                    $("#loginName").val("");
                                    $("#loginName").focus();
                                    $(".error").html("用户不存在");
								}
								else if(data.code=="-2"){
                                    $("#loginPwd").val("");
                                	$("#loginPwd").focus();
                                	$(".error").html("密码错误");
                                }else{
                                    $(".error").html("登录失败,请重新登录");
                                }
                                $("#login").removeAttr("disabled");
                                $("#login").html("登录");
							}

						}
					});
				}
			});

			//监听回车提交
			$(function() {
				document.onkeydown = function(e) {
					var ev = document.all ? window.event : e;
					if (ev.keyCode == 13 && $("#login").attr("disabled") != "disabled") {
						$("#login").click();
					}
					;
				};
			});
		});

		//忘记密码
		function identify(){
			alert("请联系管理员");
		};
		//重置
		function reset() {
            $("#loginPwd").val("");
            $("#loginName").val("");
        }
	</script>
</body>
</html>
