function bubbleClick() {
    $(".bubble").click(function(){
        console.log($(this));
        var hashTag_name = $(".bubble p").text();
        $(".modal-title").text(hashTag_name);
        $('#myModal').modal("show");
    });
}

//$.ajax({
//   url:'/api/admin/schedule/start',
//   type:'POST',
//   dataType: 'json',
//   contentType : "application/json; charset=utf-8",
//   data: {
//        checkedList: checkedList
//   },
//   success:function(data){
//   },
//   error : function(e){
//   }
//});