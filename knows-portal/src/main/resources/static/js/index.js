
/*
显示当前用户的问题
 */
let questionsApp = new Vue({
    el:'#questionsApp',
    data: {
        questions:[],
        pageinfo:{},
    },
    methods: {
        loadQuestions:function () {
            let index=location.href.lastIndexOf("#");
            let pageNum=1;
            if(index!=-1){
                pageNum=location.href.substring(index+1);
            }
            console.log(pageNum)
            if(! pageNum){
                pageNum = 1;
            }
            axios({
                url: '/v1/questions/my',
                method: "GET",
                params:{
                    pageNum:pageNum
                }
            }).then(function(r){
                console.log("成功加载数据");
                console.log(r);
                if(r.status == OK){
                    console.log("1"+r.data)
                    /* PageInfo 类是 MyBatis 的分页插件 PageHelper 提供的一个分页结果对象。它包含了分页信息以及当前页的数据列表。当后端返回 PageInfo 对象时，数据会被包含在 PageInfo 的 list 属性中。*/
                    questionsApp.questions = r.data.list;
                    questionsApp.pageinfo = r.data;
                    //为question对象添加持续时间属性
                    questionsApp.updateDuration();
                    questionsApp.updateTagImage();
                    // window.onhashchange=questionsApp.loadQuestions;
                }
            })
        },
        updateTagImage:function(){
            let questions = this.questions;
            for(let i=0; i<questions.length; i++){
               let tags = questions[i].tags;
               if(tags){
                   let tagImage = '/img/tags/'+tags[0].id+'.jpg';
                   console.log(tagImage);
                   questions[i].tagImage = tagImage;
               }
            }
        },
        updateDuration:function () {
            let questions = this.questions;
            for(let i=0; i<questions.length; i++){
                addDuration(questions[i]);
                /*//创建问题时候的时间毫秒数
                let createtime = new Date(questions[i].createtime).getTime();
                //当前时间毫秒数
                let now = new Date().getTime();
                let duration = now - createtime;
                if (duration < 1000*60){ //一分钟以内
                    questions[i].duration = "刚刚";
                }else if(duration < 1000*60*60){ //一小时以内
                    questions[i].duration =
                        (duration/1000/60).toFixed(0)+"分钟以前";
                }else if (duration < 1000*60*60*24){
                    questions[i].duration =
                        (duration/1000/60/60).toFixed(0)+"小时以前";
                }else {
                    questions[i].duration =
                        (duration/1000/60/60/24).toFixed(0)+"天以前";
                }*/
            }
        }
    },
    created:function () {
        console.log("执行了方法");
        this.loadQuestions();

    }
});










