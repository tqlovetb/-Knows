
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
        loadQuestions:function (pageNum) {
            if(! pageNum){
                pageNum = 1;
            }
            axios({
                url: '/v1/questions/teacher',
                method: "GET",
                params:{
                    pageNum:pageNum
                }
            }).then(function(r){
                console.log("成功加载数据");
                console.log(r);
                if(r.status == OK){
                    questionsApp.questions = r.data.list;
                    questionsApp.pageinfo = r.data;
                    //为question对象添加持续时间属性
                    questionsApp.updateDuration();
                    questionsApp.updateTagImage();
                }
            })
        },
        updateTagImage:function(){
            let questions = this.questions;
            for(let i=0; i<questions.length; i++){
                let tags = questions[i].tags;
                // 判断tags!=null 或理解为判断tags存在
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
                //创建问题时候的时间毫秒数
                addDuration(questions[i]);
            }
        }
    },
    created:function () {
        console.log("执行了方法");
        this.loadQuestions(1);

    }
});










