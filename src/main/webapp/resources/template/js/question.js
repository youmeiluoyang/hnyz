$(document).ready(function(){

    function createQuestion(ques,container){
        var $row = $(document.createElement("div"));
        container.append($row);
        $row.addClass("row");
        var $col = $(document.createElement("div"));
        $col.addClass("col-lg-12 col-md-12 col-sm-12");
        var $h4 =  $(document.createElement("h4"));
        var $label =  $(document.createElement("label"));
        $row.append($col);
        $col.append($h4).append($label);
        $h4.text("Q" + ques.ids +":");
        $label.text(ques.names);
        var items = ques.questionItems;
        for(var i = 0;i<items.length;i++){
            var item = items[i];
            var $checkboxDiv =  $(document.createElement("div"));
            $checkboxDiv.addClass("checkbox");
            var $label2 =  $(document.createElement("label"));
            $checkboxDiv.append($label2);
            $col.append($checkboxDiv);
            $label2.html("<input type='checkbox'>" + item.desc);
            var $input = $label2.children("input");
            $input.attr("itemid",item.ids).attr("sortnum",item.sortNum).attr("quesid",ques.ids);
           // $label2[0].textContent = "1111";
        }

        return $row;
    }

    /*

    Util.showLoading();
    $.post(
        Address.isLogin,
        {},
        function(response){
            console.log("判断是否登录返回");
            console.log(response);
            if(response.status == Status.success){
                var isLogin = response.data.isLogin;
                //没有登录的话登录
                if("false" == isLogin || isLogin == false){
                    location.href = Address.loginUser+"&callBackUrl="+basePath+"/res/front/html/question.html";
                }else{
                    //如果已经登录,那么就去拿粉丝信息,判断是不是粉丝
                    $.post(
                        Address.fansInfo,
                        {},
                        function(response){

                            if(response.status == Status.success){
                                Util.hideLoading();
                                var fans = response.data.fans;
                                if(fans.subscribe == 1){
                                    
                                }
                                //不是粉丝的话不能进行活动
                                else{
                                    Util.showModal("提示","只有粉丝才能参与问卷调查和抽奖哦"); 
                                }
                            }else{
                                Util.hideLoading();
                                Util.showModal("出错啦","攻城狮犯错误啦,我们回去好好教训他!");
                            }
                        },
                        "json"
                    );
                }
            }
            else{
                Util.hideLoading();
                Util.showModal("出错啦","攻城狮犯错误啦,我们回去好好教训他!");
            }
        },
        "json"
    );

    */

    var basePath = "http://localhost:8080/ymly";

    $.post(
        basePath + "/api/front/getSurveyInfo.do?ids=1",
        {},
        function (res) {
            var survey = res.data.survey;
            var questions = res.data.questions;
            $("#surveyName").text(survey.names);
            $("#surveyDesc").text(survey.desc);
            var len = questions.length;
            for(var i = 0;i < len;i++){
                var $row = createQuestion(questions[i],$("#quesCon"));
              //  $("#quesCon").append($row);
            }

            $("#quesCon").find(".row").each(function () {
                var quesItems = $(this).find("input[type='checkbox']");
                quesItems.each(function (index) {
                    var $this = $(this);
                    if(index == 1){
                        $(this).attr("checked","checked");
                    }
                });
            });

        },
        'json'
    )

    /**
     * 选择或者不选择
     */
    $("#quesCon").delegate("input","click",function () {
        var $this = $(this);
        //如果是其他
        if($this.attr("sortnum") == 100){
            //选中的话插入输入框
            if($this.is(":checked")){
                var html = "<input class='other-input' maxlength='20' placeholder='请输入'>";
                $this.parent().append(html);
            }
            //移除
            else{
                $this.parent().find(".other-input").remove();
            }
        }
    })



    /**
     * 点击提交的函数
     */
    $("#commit").click(function () {
        //判断答案是否完成
        var complete = true;
        $("#quesCon").find(".row").each(function (index) {
            var quesId =undefined;
            var cnt = 0;
            var stop = false;
            var quesItems = $(this).find("input[type='checkbox']");
            quesItems.each(function (index) {
                var $this = $(this);
                quesId = $this.attr("quesid");
                if(!$this.is(":checked")){
                   cnt++;
                }
                //选择了的话,看看是不是选择了其他,看看其他是否有输入
                else{
                    var sortnum = $this.attr("sortnum");
                    if(sortnum == 100){
                        var other = $this.parent().find(".other-input");
                        if(other.val().length == 0){
                            stop = true;
                            Util.showModal("提示","请输入问题" + quesId+"其他选项的内容",true);
                        }
                    }
                }
            });
            if(stop){
                complete = false;
                return false;
            }
            if(cnt == quesItems.length){
                Util.showModal("提示","问题" + quesId+"未选择答案哦",true);
                complete = false;
                return false;
            }
        });
        //全部的答卷填写完成
        if(complete){
            var allAnswer = new Array();
            var answers = new Array();
            $("#quesCon").find(".row").each(function (index) {
                var quesId =undefined;
                var quesItems = $(this).find("input[type='checkbox']");
                quesItems.each(function (index) {
                    var $this = $(this);
                    quesId = $this.attr("quesid");
                    if($this.is(":checked")){
                        var ids = $this.attr("itemid");
                        var answer = {questionItemId:ids,value:'1',openId:"openid"};
                        answers.push(answer);
                        var sortnum = $this.attr("sortnum");
                        if(sortnum == 100){
                            var other = $this.parent().find(".other-input");
                            answer.remark = other.val();
                        }
                    }
                });
            });

            Util.showLoading();
            $.post(
                basePath + "/api/front/addAnswers.do",
                {answers:JSON.stringify(answers)},
                function (res) {
                    Util.hideLoading();
                    if(res.status == Status.success){
                        Util.showModal("谢谢您参与回答","点我前往抽奖哦",true);
                    }else{
                        Util.showModal("哦哦","系统发生错误啦,请重试");
                    }
                },
                'json'
            )
        }
    });

});