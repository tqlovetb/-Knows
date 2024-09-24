let questionApp = new Vue({
    el:"#questionApp",
    data:{
        question:{}
    },
    methods:{
        loadQuestion:function(){
            let qid = location.search;
            if(!qid){
                alert("请指定问题的id");
                return;
            }
            qid = qid.substring(1);
            axios({
                url:"http://localhost:9000/v2/questions/"+qid,
                method:"get"
            }).then(function (response){
                questionApp.question = response.data;
                addDuration(response.data);
            })
        },
        /*updateDuration:function () {
            //创建问题时候的时间毫秒数
            let createtime = new Date(this.question.createtime).getTime();
            //当前时间毫秒数
            let now = new Date().getTime();
            let duration = now - createtime;
            if (duration < 1000*60){ //一分钟以内
                this.question.duration = "刚刚";
            }else if(duration < 1000*60*60){ //一小时以内
                this.question.duration =
                    (duration/1000/60).toFixed(0)+"分钟以前";
            }else if (duration < 1000*60*60*24){
                this.question.duration =
                    (duration/1000/60/60).toFixed(0)+"小时以前";
            }else {
                this.question.duration =
                    (duration/1000/60/60/24).toFixed(0)+"天以前";
            }
        }*/
    },
    created:function(){
        this.loadQuestion()
    }
})

let postAnswerApp = new Vue({
    el:"#postAnswerApp",
    data:{},
    methods:{
        postAnswer:function(){
            let qid=location.search;
            if(!qid){
                alert("qid没有值!!!!");
                return;
            }
            qid=qid.substring(1);
            let content = $("#summernote").val();
            let form = new FormData;
            form.append("questionId",qid);
            form.append("content",content);
            form.append("accessToken",token);
            axios({
                url:"http://localhost:9000/v2/answers",
                method:"post",
                data:form
            }).then(function (response) {
                console.log(response.data);
                let answer = response.data;
                answer.duration = "刚刚";
                answersApp.answers.push(answer);
                $("#summernote").summernote("reset");

            })
        }
    }
})
let answersApp=new Vue({
    el:"#answersApp",
    data:{
        answers:[]
    },
    methods:{
        loadAnswers:function(){
            // 这个方法也需要问题的id,就在url?之后
            let qid=location.search;
            if(!qid){
                return;
            }
            qid=qid.substring(1);
            axios({
                url:"http://localhost:9000/v2/answers/question/" + qid,
                method:"get"
            }).then(function(response){
                answersApp.answers=response.data;
                answersApp.updateDuration();
            })
        },
        updateDuration:function(){
            let answers=this.answers;
            for(let i=0;i<answers.length;i++){
                addDuration(answers[i]);
            }
        },
        postComment:function(answerId){
            /*#addComment：选择 ID 为 addComment 的元素。
+ answerId：将变量 answerId 的值附加到 #addComment 后面。
textarea：选择 #addComment 元素内的 textarea 元素。*/
            let textarea=$("#addComment"+answerId+" textarea");
            //textarea.val()：这是 jQuery 提供的方法，用于获取（或设置）表单元素的值。在这里，它获取 textarea 元素的值。
            let content = textarea.val();
            let form = new FormData();
            form.append("answerId",answerId);
            form.append("content",content);
            form.append("accessToken",token);
            console.log("answerId:"+answerId)
            axios({
                url:"http://localhost:9000/v2/comments",
                method:"post",
                data:form
            }).then(function (response) {
                let comment = response.data;
                let answers = answersApp.answers;
                for(let i = 0;i<answers.length; i++){
                    if(answers[i].id == answerId){
                        answers[i].comments.push(comment);
                        textarea.val("");
                        break;
                    }
                }

            })
        },
        removeComment:function (commentId, index, comments) {
            axios({
                url:"http://localhost:9000/v2/comments/"+commentId+"/delete",
                method:"get",
                params:{
                    accessToken:token
                }
            }).then(function (response) {
                console.log(response.data);
                if(response.data == "ok"){
                    console.log("index"+index)
                    comments.splice(index, 1);
                }

            })

        },
        updateComment:function(commentId,index,answer) {
            let textarea = $("#editComment"+commentId +" textarea");
            let content = textarea.val();
            if(!content){
                return;
            }
            let form = new FormData();
            form.append("answerId",answer.id);
            form.append("content",content);
            form.append("accessToken",token);
            axios({
                url:"http://localhost:9000/v2/comments/"+commentId+"/update",
                method:"post",
                data:form,
            }).then(function(response){
                console.log("使用typeof判断返回值的类型:"
                    +typeof(response.data))
                if(typeof(response.data)=="object") {
                    // 控制器返回的是修改成功后的评论对象,直接获取
                    let comment = response.data;
                    // 本次修改操作没有变化数组元素的数量(长度没变化)
                    // 数组元素数量不变化时,Vue不会自动更新页面上的内容
                    // 既然它不会自动修改,就需要我们手动修改
                    // Vue提供了我们手动修改元素内容的方法,
                    // 这个方法引起的变化会显示在页面上
                    // Vue.set([要修改的数组],[要修改的元素下标],[修改成什么])
                    Vue.set(answer.comments, index, comment);
                    // 修改成功后,编辑框自动收缩
                    $("#editComment" + commentId).collapse("hide");
                }else{
                    alert(response.data);
                }
            })
        },
        answerSolved:function(answerId){
            axios({
                url:"http://localhost:9000/v2/answers/"+answerId+"/solved",
                method:"get",
                params:{
                    accessToken:token
                }
            }).then(function (response) {
                console.log(response.data);

            })
        }
    },
    created:function(){
        this.loadAnswers();
    }
})
