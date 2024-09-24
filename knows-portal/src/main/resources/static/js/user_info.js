let userApp=new Vue({
    el:"#userApp",
    data:{
        user:{}
    },
    methods:{
        loadUserVO:function(){
            axios({
                url:"/v1/users/me",
                method:"get",
            }).then(function(response){
                userApp.user=response.data;
            })
        }
    },
    created:function () {
        this.loadUserVO();
    }
})
