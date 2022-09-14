$.each($('.btn_category_wrap').find('button'), function(index, btn_category){
    document.getElementById(btn_category.id).addEventListener('click', function(e) {
        var flag_selected = true;

        if(e.target.className === 'selected') {
            e.target.classList.remove('selected');
            $.each(marker_object[btn_category.value], function(index, item){
                item.setMap(null);
            });
            $.each(overlay_object[btn_category.value], function(index, item){
                item.setMap(null);
            });

            category_list.pop(btn_category.value);
        } else {
            e.target.classList.add('selected');
            flag_selected = false;
            category_list.push(btn_category.value);
            getHashtagByCategory(map, btn_category.value);
        }

        setTimeout(function(){ bubbleClick()}, 1500);
    });
});

document.getElementById("searchBox").addEventListener('keypress', function(e) {
    if(e.keyCode === 13) {
        getHashtagByKeyword(document.getElementById("searchBox").value);
    }
    setTimeout(function(){ bubbleClick() }, 1500);
});

$.each($('.btn_check_wrap').find('button'), function(index, btn_check){
    document.getElementById(btn_check.id).addEventListener('click', function(e){
        e.target.classList.toggle('bi-check-lg');

        if(e.target.className === 'bi-check-lg') {
            getHashtagByCount(btn_check.value, true);
        } else {
            getHashtagByCount(btn_check.value, false);
        }
    });
});

function getHashtagByKeyword(searchText, category) {
   var json = map.getBounds();
   json["category_list"] = category_list;
   json["searchText"] = searchText;
   param = JSON.stringify(json);

    $.ajax({
        url:'/api/getHashtagByKeyword',
        type:'POST',
        dataType: 'json',
        contentType : "application/json; charset=utf-8",
        data: param,
        success:function(data){
            removeAll_marker();
            $.each(category_list, function(index, category){
                create_marker(data, category);
            });
        },
        error : function(e){
        }
    });

    setTimeout(function(){ bubbleClick() }, 1500);
}

function getHashtagByCount(check_type, check_flag) {
    var json = map.getBounds();
    json["category_list"] = category_list;
    json["check"] = check_type;
    param = JSON.stringify(json);

   $.ajax({
       url:'/api/getHashtagByCount',
       type:'POST',
       dataType: 'json',
       contentType : "application/json; charset=utf-8",
       data: param,
       success:function(data){
           if(check_flag){
               $.each(category_list, function(index, category){
                    create_marker(data, category);
               });
           } else {
                remove_marker_byCount(data);
           }
       },
       error : function(e){
       }
   });

    setTimeout(function(){ bubbleClick() }, 1500);
}

function removeAll_marker() {
    $.each(category_list, function(index, category){
        $.each(overlay_object[category], function(o_index, overlay){
            overlay.setMap(null);
        });

        $.each(marker_object[category], function(o_index, marker){
            marker.setMap(null);
        });
    });
}

function remove_marker_byCount(data) {
    var dir = (level - be_level) > 0 ? -1 : 0;
    var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
    var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
    $.each(data, function(index, item){
        $.each(category_list, function(index, category){
            $.each(overlay_object[category], function(o_index, overlay){
                if(item.longitude.toFixed(10) == (overlay.getPosition().La + longitude_adjust).toFixed(10)
                  && item.latitude.toFixed(10) == (overlay.getPosition().Ma - latitude_adjust).toFixed(10)){
                    overlay.setMap(null);
                }
            });

            $.each(marker_object[category], function(o_index, marker){
                if(item.longitude.toFixed(10) == marker.getPosition().La.toFixed(10)
                  && item.latitude.toFixed(10) == marker.getPosition().Ma.toFixed(10)){
                    marker.setMap(null);
                }
            });
        });
    });
}

//function test(data) {
//    if(data != null && data.length > 0){
//        var overlay_CE7_temp = [];
//        var overlay_FD6_temp = [];
//        var dir = (level - be_level) > 0 ? -1 : 0;
//        var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
//        var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
//        $.each(data, function(d_index, d_item){
//            $.each(marker_object['CE7'], function(index, item){
//                if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
//                    item.setMap(null);
//            });
//            $.each(overlay_object['CE7'], function(index, item){
//                if(d_item.longitude.toFixed(13) !== String((Number(item.getPosition().La.toFixed(13)) + longitude_adjust).toFixed(13)) ||
//                    d_item.latitude.toFixed(13) !== String((Number(item.getPosition().Ma.toFixed(13)) - latitude_adjust).toFixed(13)))
//                    item.setMap(null);
//                else overlay_CE7_temp.push(item);
//            });
//            $.each(marker_object['FD6'], function(index, item){
//                if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
//                    item.setMap(null);
//    //                        else console.log(index);
//            });
//
//            $.each(overlay_object['FD6'], function(index, item){
//    //                        console.log('name: ', item.getContent());
//    //                        console.log('index : ', index ,' db data : ' , d_item.longitude.toFixed(13), ' 지도 data : ', item.getPosition().La.toFixed(13), ' 차이 : ', d_item.longitude.toFixed(13)-item.getPosition().La.toFixed(13));
//    //                        console.log('조정 후 차이: ', Number(d_item.longitude.toFixed(13)) - (Number(item.getPosition().La.toFixed(13)) + longitude_adjust));
//                if(d_item.longitude.toFixed(13) !== String((Number(item.getPosition().La.toFixed(13)) + longitude_adjust).toFixed(13)) ||
//                    d_item.latitude.toFixed(13) !== String((Number(item.getPosition().Ma.toFixed(13)) - latitude_adjust).toFixed(13)))
//                    item.setMap(null);
//                else overlay_FD6_temp.push(item);
//            });
//        });
//        overlay_list = overlay_CE7_temp.concat(overlay_FD6_temp);
//    } else {
//        customOverlay_flag = false;
//
//        $.each(marker_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//
//        $.each(marker_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//
//        $.each(overlay_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//
//        $.each(overlay_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//    }
//}
