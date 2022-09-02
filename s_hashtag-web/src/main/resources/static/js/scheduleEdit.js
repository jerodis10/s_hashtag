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

$("button[id=btn_scheduleChange]").click(function(){
    var checkedList = [];
    var check_item = {};
    var check_list= [];
    $.each(document.querySelectorAll("input[name='check']:checked"), function(index, item) {
//        checkedList.push(item.value);
        check_item['schedule_document_id'] = item.value;
        check_item['cron_period'] = document.getElementById("cron_period"+item.value).value;
        check_item['min_latitude'] = document.getElementById("min_latitude"+item.value).value;
        check_item['max_latitude'] = document.getElementById("max_latitude"+item.value).value;
        check_item['min_longitude'] = document.getElementById("min_longitude"+item.value).value;
        check_item['max_longitude'] = document.getElementById("max_longitude"+item.value).value;

        check_list.push(check_item);
    });

//    var json = {
//        "checkedList": checkedList,
//        "cron_period": document.getElementById("cron_period").value,
//        "min_latitude": document.getElementById("min_latitude").value,
//        "max_latitude": document.getElementById("max_latitude").value,
//        "min_longitude": document.getElementById("min_longitude").value,
//        "max_longitude": document.getElementById("max_longitude").value
//    }

    $.ajax({
       url:'/api/admin/schedule/change',
       type:'POST',
       dataType: 'json',
       contentType : "application/json; charset=utf-8",
       data:
//            JSON.stringify(json),
            JSON.stringify(check_list),
//       {
//            checkedList: checkedList,
//            expression: document.getElementById("cron_period").value,
//            min_latitude: document.getElementById("min_latitude").value,
//            max_latitude: document.getElementById("max_latitude").value,
//            min_longitude: document.getElementById("min_longitude").value,
//            max_longitude: document.getElementById("max_longitude").value
//       },
       success:function(data){
            alert("스케줄 변경에 성공했습니다.");
       },
       error : function(request,status,error){
            alert("스케줄 변경에 실패했습니다.");
            console.log(error);
       }
    });
});
