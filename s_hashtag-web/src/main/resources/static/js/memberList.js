$("button[id=btnMemberDelete]").click(function(){
    var checkedList = [];
    $.each(document.querySelectorAll("input[name='check']:checked"), function(index, item) {
        checkedList.push(item.value);
    });

    var params = {checkedList: checkedList};

    $.ajax({
       url:'/api/admin/members/delete',
       type:'POST',
       dataType: 'json',
       data: {checkedList: checkedList},
       success:function(data){
       },
       error : function(e){
       }
    });
});
