$(function(){


    //主菜单点击
    $('.cate_hd').each(function(){
        $(this).click(function(){
            if(!$(this).parent().hasClass('current')){
                $('.cate').removeClass('current');
                $(this).parent().addClass('current');
                $('.cate_bd').slideUp();
                $(this).next().slideDown();
            }
            else{
            	 $(this).next().slideUp();
            	 $(this).parent().removeClass('current');
            }
        });
    });


    //子菜单
    $('.cate_item').each(function(){
        $(this).click(function(e){
            $('.cate_item').removeClass('on');
            $(this).addClass('on');
            //阻止自动跳转
            e.preventDefault();
            var url = $(this).attr("href");
            var sortNum= $(this).attr("sort_num");
            if(url.indexOf("?") == -1){
                url = url+"?sortNum=" + sortNum;
            }else{
                url = url+"&sortNum=" + sortNum;
            }
            window.location.href = url;
        });
    });



    //判断菜单是否高亮
    function testHighLight() {
        var url =window.location.href.toString();
        if(url.indexOf("sortNum") !=-1){
            var sortNum = undefined;
            var parameters = url.split("?")[1];
            var parArr = parameters.split("&");
            for(var i = 0;i<parArr.length;i++){
                var par = parArr[i];
                if(par.indexOf("sortNum") != 1){
                    sortNum = par.split("=")[1];
                    break;
                }
            }
            $('.cate_item').each(function(){
                var cur = $(this).attr("sort_num");
                if(cur == sortNum){
                    var par = $(this).parent().prev();
                    //展开父菜单
                    $('.cate').removeClass('current');
                    par.parent().addClass('current');
                    $('.cate_bd').slideUp();
                    par.next().slideDown();
                    $(this).addClass("on");
                }
            });
        }
    }
    testHighLight();



    //字数限制
    $('.wzxq').each(function(i){
        if($(this).text().length>20){
            $(this).attr("title",$(this).text());
            var text=$(this).text().substring(0,19)+"...";
            $(this).text(text);
        }
    });
});


/**
 * 计算两个日期间隔天数
 * @param strDateStart 起始日期 2015-10-22
 * @param strDateEnd 结束日期 2015-10-30
 * @returns 返回相隔天数，注意如果两个日期是同一天，则返回0
 */
function getDays(strDateStart,strDateEnd){
   var strSeparator = "-"; //日期分隔符
   var oDate1;
   var oDate2;
   var iDays;
   oDate1= strDateStart.split(strSeparator);
   oDate2= strDateEnd.split(strSeparator);
   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 
   return iDays ;
}

/**
 * 日期加减天数后的日期
 * @param dateStr 原始日期字符串 2015-10-22
 * @param days 需要加减的天数
 * @returns {String}
 */
function addDays(dateStr,days){
   var strSeparator = "-"; //日期分隔符
   var oDate1= dateStr.split(strSeparator);
   var d = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
   d.setDate(d.getDate()+days); 
   var m=d.getMonth()+1; 
   return d.getFullYear()+'-'+m+'-'+d.getDate();
}

/**
 * 页面跳转
 * @param toPage
 */
function pageJump(toPage){
	$("#toPage").val(toPage);
	search();
}


/**
 * 地区更改
 * @param areaNum
 * @param sel
 */
function changeArea(areaNum,sel) {
	$.ajax({
        type: "post",
        async: false, //同步执行
        url: base + "/common/getAreaByParNum.do",
        data:{"parentAreaNum":areaNum},// 你的formid
        dataType: "json", //返回数据形式为json
        success: function (result) {
            var str = "<option value=''>全部</option>";
            for(var i=0;i< result.length;i++){
            	str += "<option value='"+result[i].areaNum+"'>"+result[i].names+"</option>";
            }
            $(sel).html(str);
        },
        error: function (errorMsg) {
            alert("获取地区列表出错");
        }
    });
}

/**
 * 机构更改
 * @param orgNo
 * @param sel
 */
function changeOrg(orgNo, sel) {
    $.ajax({
        type: "post",
        async: false, //同步执行
        url: base + "/org/sonOrg.do",
        data:{"parentOrgNo":orgNo},
        dataType: "json", //返回数据形式为json
        success: function (result) {
            var str = "<option value=''>全部</option>";
            for(var i=0;i< result.length;i++){
                str += "<option value='"+result[i].orgNo+"'>"+result[i].orgName+"</option>";
            }
            $(sel).html(str);
        },
        error: function (errorMsg) {
            alert("获取机构列表出错");
        }
    });
}

/**
 * 从第一页开始查询
 */
function doSearch(){
	$("#toPage").val(1);
	search();
}

//起始时间改变
function bTimeChange(){
	var beginTime = $dp.cal.getNewDateStr();
	$("#beginTime").val(beginTime+'');
	doSearch();
}

//结束时间修改
function eTimeChange(){
	var endTime = $dp.cal.getNewDateStr();
	$("#endTime").val(endTime+'');
	doSearch();
}

//起始日期清空
function bClear(){
	$("#beginTime").val('');
	doSearch();
}

//结束日期清空
function eClear(){
	$("#endTime").val('');
	doSearch();
}

Date.prototype.format = function(format){
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	};
	
	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}
	
	for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
};