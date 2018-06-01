/**
 * Created by Administrator on 4/30/2018.
 */

;
$(document).ready(function () {

    var mycharts  = document.getElementsByClassName("echart");
    $.post(
        ctx + "/member/doQuesStat.do",
        {},
        function (res) {
            var ques = res.data.stat;
            var index = 0;
            for (var i = 0; i < ques.length; i++) {

                var dataArr = [];
                var q = ques[i];
                var items = q.questionItems;
                var total = 0;
                var option = {
                    title: {
                        text: q.names,
                        textStyle:{
                            //文字颜色
                            color:'#000',
                            //字体风格,'normal','italic','oblique'
                            fontStyle:'normal',
                            //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                            fontWeight:'bold',
                            //字体系列
                            fontFamily:'sans-serif',
                            //字体大小
                            fontSize:12
                        },
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params,ticket,callback) {
                            var name = params.name.split(":")[0];
                            var cnt = params.name.split(":")[1];
                            var per = params.percent;
                            var res = name+"统计:<br>" + "选择人数:" + cnt+"<br>所占比例:" + per + "%";
                            return res;
                        }
                    },
                    calculable: true,
                    series: [
                        {
                            name: '',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%']
                        }
                    ]
                };
                for (var j = 0; j < items.length; j++) {
                    total = total + items[j].cnt;
                }
                for (var j = 0; j < items.length; j++) {
                    var item = items[j];
                    var data = {
                        name: item.desc +":" +item.cnt,
                        value: item.cnt / total * 100
                    };
                    dataArr.push(data);
                }
                option.series[0].data = dataArr;
                var chart = mycharts[index++];
                var echart = echarts.init(chart);
                echart.setOption(option);

            }
        }
    );
});