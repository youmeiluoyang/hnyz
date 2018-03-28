<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=9,IE=10,IE=11,IE=edge,chrome=1">
    <meta name="description" content="中国邮政微信${login_admin.pubnumTypeName}管理系统">
    <title>中国邮政微信${login_admin.pubnumTypeName}管理系统</title>
    <link href="${basepath}/resources/css/big-wx.css?t=333" rel="stylesheet"/>
    <link href="${basepath}/resources/css/index.css" rel="stylesheet"/>
    <link href="${basepath}/resources/css/dialog.css" rel="stylesheet"/>
    <script type="text/javascript" src="${basepath}/resources/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${basepath}/resources/js/common.js"></script>
    <script type="text/javascript">
        $(function () {
            var loadedUrl = location.href.toString();
            if (Util.checkUrlLoad(loadedUrl)) {
                $(".bw-main-container").html("");
                return;
            }


            pageJump(1); // 加载公告列表

            /*----更新上级集群推送--BEGIN----*/
            $.ajax({
                url: $basepath + "/clusterPush/updateSuperPush.do",
                dataType: "json",
                success: function (result) {
                    if (result.status == "success")
                        $("#countNotRead").html(result.data.notReadCount);
                },
                error: function (e) {
                    console.info(e);
                }
            });
            /*----更新上级集群推送--END----*/

            /*----下级群发审核提醒--BEGIN----*/
            $.ajax({
                url: $basepath + "/wxMsg/notReviewCount.do",
                dataType: "json",
                success: function (result) {
                    if (result.status == "success")
                        $("#countNotReadWxMsg").html(result.data.count);
                },
                error: function (e) {
                    console.info(e);
                }
            });
            /*----下级群发审核提醒--END----*/

            /*----下级群发审核提醒--菜单审核个数----*/
            $.post(
                    $basepath + "/menuReview/getToReviewCount.do",
                    {
                        state: '00'
                    },
                    function (response) {
                        if (response.status == "success") {
                            $("#reviewMenuCount").html(response.data.cnt);
                        } else {
                            console.log(response.message);
                        }
                    },
                    "json"
            )

        });
    </script>
</head>
<body id="index-body" class="${login_admin.pubnumType}">
<div class="bw-wrap">
    <!--top-->
    <jsp:include page="/common/top.do"/>
    <div class="bw-flex">
        <jsp:include page="/common/left.do"/>
        <div class="bw-main-container">
            <ol class="bw-breadcrumb">
                <li><a href="">系统首页</a></li>
            </ol>
            <div class="bw-tile-container">
                <shiro:hasPermission name="clusterPush/superPush.do">
                    <div class="bw-tile" onclick="navClick(this, $basepath + '/clusterPush/superPush.do',true,'接收上级推送')"
                         style="cursor: pointer;">
                        <div class="bw-tile-left">
                            <i class="bw-icon-tab-message"></i>
                            <span class="bw-tile-txt">上级集群<br/>推送消息</span>
                        </div>
                        <div class="bw-tile-right">
                            <span class="count" id="countNotRead"></span>
                        </div>
                    </div>
                </shiro:hasPermission>
                <shiro:hasPermission name="wxMsg/wxMsg.do">
                    <div class="bw-tile" onclick="navClick(this, $basepath + '/wxMsg/reviewWxMsg.do',true,'群发消息')"
                         style="cursor: pointer;">
                        <div class="bw-tile-left">
                            <i class="bw-icon-tab-message"></i>
                            <span class="bw-tile-txt">下级群发消息<br/>审核</span>
                        </div>
                        <div class="bw-tile-right">
                            <span class="count" id="countNotReadWxMsg">0</span>
                        </div>
                    </div>
                </shiro:hasPermission>
                <shiro:hasPermission name="menu/setMenu.do">
                    <div class="bw-tile" onclick="navClick(this, $basepath + '/menuReview/toReview.do',true,'菜单栏设置')"
                         style="cursor: pointer;">
                        <div class="bw-tile-left">
                            <i class="bw-icon-tab-message"></i>
                            <span class="bw-tile-txt">下级菜单发布<br/>审核</span>
                        </div>
                        <div class="bw-tile-right">
                            <span class="count" id="reviewMenuCount">0</span>
                        </div>
                    </div>
                </shiro:hasPermission>
            </div>
            <div class="bw-container" id="announcementContainer">
                <div class="bw-table-info">
                    <div class="bw-info-count">
                        <label class="bw-info-label">系统公告</label>
                        <span class="bw-count" id="totalAnnmtNum">共-条</span>
                    </div>
                </div>
                <form id="searchForm">
                    <input type="hidden" name="searchUrl" value="${basepath}/announcement/dataList.do"/>
                    <input type="hidden" name="refreshClass" value=".dataList"/>
                    <input type="hidden" name="toPage" id="toPage" value="${query.toPage}"/>
                    <input type="hidden" name="orgNo" value="${query.orgNo}"/>
                </form>
                <div class="dataList"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>