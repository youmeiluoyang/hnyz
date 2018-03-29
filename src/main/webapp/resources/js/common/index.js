/**
 * Created by Administrator on 3/29/2018.
 * index页面JS
 */
;
$(document).ready(function () {

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart'));
    $.post(
        ctx+"/member/memberIncrease.do",
        function(data){
            var xAxis = new Array();
            var yAxis = new Array();
            for(var i = 0;i<data.length;i++){
                var item=data[i];
                xAxis.push(item.orgName);
                yAxis.push(item.count);
            }
            // 指定图表的配置项和数据
            var option = {
                title : {
                    text: '统计图表'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['会员人数']
                },
                toolbox: {
                    show : true,
                    feature : {
                        dataView : {show: true, readOnly: true},
                        magicType : {show: true, type: ['line', 'bar','pie']}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : xAxis,
                        axisLabel:{
                            interval:0,//横轴信息全部显示
                            rotate:-30//-30度角倾斜显示
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'用户人数',
                        type:'bar',
                        data:yAxis,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },
        "json"
    );




});
