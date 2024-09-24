let userApp=new Vue({
    el:"#userApp",
    data:{
        user:{}
    },
    methods:{
        loadUserVO:function(){
            axios({
                url:"http://localhost:9000/v1/users/me",
                method:"get",
                params:{
                    accessToken:token
                }
            }).then(function(response){
                userApp.user=response.data;
            })
        }
    },
    created:function () {
        this.loadUserVO();
    }
})
