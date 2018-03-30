<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>邮美洛阳公众号管理系统<decorator:title></decorator:title>   </title>
<link rel="stylesheet" href="${ctx }/resources/css/base.css"/>
<script type="text/javascript" src="${basepath}/resources/js/jquery.md5.js"></script>
<decorator:head></decorator:head>
</head>
<body>
	<!--头部header/s -->
    <div class="header">
        <div class="min_box">
        	<span class="m_hello">
        		欢迎您，${login_admin.loginName}
	            &nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="showChangePwd">修改密码</a>
	            &nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="loginOut">退出</a>
        	</span>
        </div>
    </div>
    <!--头部header/e -->
    
    <!--左侧菜单menu/s -->
    <div class="menu">
		<a href="${ctx}/common/index.do"><h1><img src="${ctx}/resources/images/avatar.png" alt="邮美洛阳">邮美洛阳</h1></a>
        <div class="category">
			<div class="cate">
				<c:forEach items="${menu}" var="mainMenu">
					<div class="cate_hd"><i class="i_cate i_business"></i>${mainMenu.key}</div>
					<div class="cate_bd">
						<c:forEach items="${mainMenu.value}" var="subMenu" varStatus="status">
							<a href="${ctx}/${subMenu.url}" class="cate_item" sort_num="${status.index}">${subMenu.names}</a>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
    </div>
    <!--左侧菜单menu/e -->
    
    <!--右侧主体内容main/s -->
    <div class="main">
        <decorator:body></decorator:body>
		<!-- 浮动提示/S -->
		<div class="float hide" id="floatTip">
			<img class="tip" src="${ctx}/resources/images/loading.gif">
		</div>
		<!-- 浮动提示/E -->
    </div>
    <!--右侧主体内容main/e -->

	<div class="popMain changePwd">
	  	  <div class="popBox">
			  <div class="boxInner">
				  <form id="adminRoleForm">
					  <input type="hidden" name="adminId" Id="adminId">
					  <table>
					      <tr>
						      <td class="label"><span>原密码</span></td>
						      <td>
						          <input type="password" id="oldPwd" />
						      </td>
						  </tr>
						  <tr>
						      <td class="label"><span>新密码</span></td>
						      <td>
						          <input type="password" id="newPwd" />
						      </td>
						  </tr>
						  <tr>
						      <td class="label"><span>确认密码</span></td>
						      <td>
						          <input type="password" id="rePwd" />
						      </td>
						  </tr>
					  </table>
				  </form>
				  <div style="">
				      <span id="errorMsg" style="color: red;margin-top: 5px;padding-left:120px;"></span>
				  </div>
		          <div class="btnGroup">
	                  <a href="javascript:void(0);" class="btnGreen" onclick="changePwd();">确定</a>
	                  <a href="javascript:void(0);" class="btnOrange">取消</a>
	          	  </div>
			  </div>
	      </div>
	  </div>








	<script type="text/javascript">
        $(function(){
            $("#loginOut").click(function() {
                if(confirm("确定要退出吗？")) {
                    location.href = "${ctx}/system/logout.do";
                }
            });

            $("#showChangePwd").click(function() {
                $(".changePwd").show();
            });

            $(".btnOrange").click(function(){
                $(".popMain").hide(function(){
                    $("#oldPwd").val("");
                    $("#newPwd").val("");
                    $("#rePwd").val("");
                });
            });
        });

        function changePwd() {
            var oldPwd = $("#oldPwd").val();
            var newPwd = $("#newPwd").val();
            var rePwd = $("#rePwd").val();

            if(oldPwd == "") {
                $("#errorMsg").html("原密码不能为空");
                return false;
            }
            if(newPwd == "") {
                $("#errorMsg").html("新密码不能为空");
                return false;
            }
            if(newPwd.length<8){
                $("#errorMsg").html("密码至少需要8位");
                return false;
            }
            var re=/^[a-z|A-Z]+[0-9][a-z|A-Z|0-9]*$/g;
            if(!re.test(newPwd)){
                $("#errorMsg").html("密码必须同时包含字母和数字，且第一位必须为字母");
                return false;
            }
            if(rePwd != newPwd) {
                $("#errorMsg").html("密码不一致");
                return false;
            }

            $.ajax({
                type: "post",
                async: false, //同步执行
                url: "${ctx}/system/changePwd.do",
                data:{
                    oldPwd:$.md5(oldPwd),
                    newPwd:$.md5(newPwd)
                },
                dataType: "json", //返回数据形式为json
                success: function (data) {
                    if (data.status == "success") {
                        confirm("修改密码成功");
                        $(".popMain").hide(function(){
                            $("#oldPwd").val("");
                            $("#newPwd").val("");
                            $("#rePwd").val("");
                        });
                    } else {
                        if(data.code == -1){
                            alert("原密码错误");
                        }else{

                            alert("密码修改错误,请重试");
                        }
                    }
                },
                error: function (errorMsg) {
                    alert("请求数据失败");
                }
            });
        }

	</script>

</body>
</html>
