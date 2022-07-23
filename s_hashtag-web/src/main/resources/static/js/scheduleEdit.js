$("button[id=btn_scheduleStart]").click(function(){
    var checkedList = [];
    $.each(document.querySelectorAll("input[name='check']:checked"), function(index, item) {
        checkedList.push(item.value);
    });

    var params = {checkedList: checkedList};

    $.ajax({
       url:'/api/admin/schedule/start',
       type:'POST',
       dataType: 'json',
       data: {checkedList: checkedList},
       success:function(data){
       },
       error : function(e){
       }
    });
});

$("button[id=btn_scheduleStop]").click(function(){
    var checkedList = [];
    $.each(document.querySelectorAll("input[name='check']:checked"), function(index, item) {
        checkedList.push(item.value);
    });

    var params = {checkedList: checkedList};

    $.ajax({
       url:'/api/admin/schedule/stop',
       type:'POST',
       dataType: 'json',
       data: {checkedList: checkedList},
       success:function(data){
       },
       error : function(e){
       }
    });
});

$("button[id=btn_scheduleTimeChange]").click(function(){
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
