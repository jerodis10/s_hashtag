document.getElementById("btn_cafe").addEventListener('click', function(e) {

    var flag_selected = true;
    var btn_cafe_value = document.getElementById("btn_cafe").value;

    if(e.target.className === 'selected') {
        e.target.classList.remove('selected');
        $.each(marker_object[btn_cafe_value], function(index, item){
            item.setMap(null);
        });
        $.each(overlay_object[btn_cafe_value], function(index, item){
            item.setMap(null);
        });

        var arr = category_list.filter(function(category){
            return category !== btn_cafe_value;
        });
        category_list = arr;
    }
    else {
        e.target.classList.add('selected');
        flag_selected = false;
        getHashtagByCategory(map, btn_cafe_value);

        category_list.push(btn_cafe_value);
    }

    setTimeout(function(){ bubbleClick()}, 1500);
});

document.getElementById("btn_food").addEventListener('click', function(e) {

    var flag_selected = true;
    var btn_food_value = document.getElementById("btn_food").value;

    if(e.target.className === 'selected') {
        e.target.classList.remove('selected');
        $.each(marker_object[btn_food_value], function(index, item){
            item.setMap(null);
        });
        $.each(overlay_object[btn_food_value], function(index, item){
            item.setMap(null);
        });

        var arr = category_list.filter(function(category){
            return category !== btn_food_value;
        });
        category_list = arr;
    }
    else {
        e.target.classList.add('selected');
        flag_selected = false;
        getHashtagByCategory(map, btn_food_value);

        category_list.push(btn_food_value);
    }

    setTimeout(function(){ bubbleClick()}, 1500);
});

document.getElementById("searchBox").addEventListener('keypress', function(e) {
    if(e.keyCode === 13) {
        getHashtagByKeyword(document.getElementById("searchBox").value);
    }
    setTimeout(function(){ bubbleClick() }, 1500);
});

document.getElementById("btn_check1").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check1', true);
    } else {
        getHashtagByCount('check1', false);
    }
});

document.getElementById("btn_check2").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check2', true);
    } else {
        getHashtagByCount('check2', false);
    }
});

document.getElementById("btn_check3").addEventListener('click', function(e){
    var btn_food_value = document.getElementById("btn_food").value;
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check3', true);
    } else {
        getHashtagByCount('check3', false);
    }
});

document.getElementById("btn_check4").addEventListener('click', function(e){
    var btn_food_value = document.getElementById("btn_food").value;
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check4', true);
    } else {
        getHashtagByCount('check4', false);
    }
});

document.getElementById("btn_check5").addEventListener('click', function(e){
    var btn_food_value = document.getElementById("btn_food").value;
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check5', true);
    } else {
        getHashtagByCount('check5', false);
    }
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
            if(data != null && data.length > 0){

                test(data);

//                removeAll_marker();
//                create_marker(data);



//                var overlay_CE7_temp = [];
//                var overlay_FD6_temp = [];
//                var dir = (level - be_level) > 0 ? -1 : 0;
//                var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
//                var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
//                $.each(data, function(d_index, d_item){
//                    $.each(marker_object['CE7'], function(index, item){
//                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
//                            item.setMap(null);
//                    });
//                    $.each(overlay_object['CE7'], function(index, item){
//                        if(d_item.longitude.toFixed(13) !== String((Number(item.getPosition().La.toFixed(13)) + longitude_adjust).toFixed(13)) ||
//                            d_item.latitude.toFixed(13) !== String((Number(item.getPosition().Ma.toFixed(13)) - latitude_adjust).toFixed(13)))
//                            item.setMap(null);
//                        else overlay_CE7_temp.push(item);
//                    });
//                    $.each(marker_object['FD6'], function(index, item){
//                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
//                            item.setMap(null);
////                        else console.log(index);
//                    });
//
//                    $.each(overlay_object['FD6'], function(index, item){
////                        console.log('name: ', item.getContent());
////                        console.log('index : ', index ,' db data : ' , d_item.longitude.toFixed(13), ' 지도 data : ', item.getPosition().La.toFixed(13), ' 차이 : ', d_item.longitude.toFixed(13)-item.getPosition().La.toFixed(13));
////                        console.log('조정 후 차이: ', Number(d_item.longitude.toFixed(13)) - (Number(item.getPosition().La.toFixed(13)) + longitude_adjust));
//                        if(d_item.longitude.toFixed(13) !== String((Number(item.getPosition().La.toFixed(13)) + longitude_adjust).toFixed(13)) ||
//                            d_item.latitude.toFixed(13) !== String((Number(item.getPosition().Ma.toFixed(13)) - latitude_adjust).toFixed(13)))
//                            item.setMap(null);
//                        else overlay_FD6_temp.push(item);
//                    });
//                });
//                overlay_list = overlay_CE7_temp.concat(overlay_FD6_temp);
//            } else {
//                customOverlay_flag = false;
//
//                $.each(marker_object['CE7'], function(index, item){
//                    item.setMap(null);
//                });
//
//                $.each(marker_object['FD6'], function(index, item){
//                    item.setMap(null);
//                });
//
//                $.each(overlay_object['CE7'], function(index, item){
//                    item.setMap(null);
//                });
//
//                $.each(overlay_object['FD6'], function(index, item){
//                    item.setMap(null);
//                });
//            }
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
                removeAll_marker();
                create_marker(data);

//               marker_list = [];
//               overlay_list = [];
//               var dir = (level - be_level) > 0 ? -1 : 0;
//               var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
//               var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
//               $.each(data, function(index, item){
//                    if(check_flag) {
//                        var img_src = "";
//                        if(item.hashtagCount < 10) img_src = '../img/markers/mapMarker1.png';
//                        if(10 <= item.hashtagCount && item.hashtagCount < 100) img_src = '../img/markers/mapMarker2.png';
//                        if(100 <= item.hashtagCount && item.hashtagCount < 1000) img_src = '../img/markers/mapMarker3.png';
//                        if(1000 <= item.hashtagCount && item.hashtagCount < 10000) img_src = '../img/markers/mapMarker4.png';
//                        if(10000 <= item.hashtagCount) img_src = '../img/markers/mapMarker5.png';
//
//                        var imageSrc = img_src,
//                        imageSize = new kakao.maps.Size(34, 39), // 마커이미지의 크기입니다
//                        imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
//
//                        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
//                            markerPosition = new kakao.maps.LatLng(item.latitude, item.longitude); // 마커가 표시될 위치입니다
//
//                        // 마커를 생성합니다
//                        var marker = new kakao.maps.Marker({
//                          position: markerPosition,
//                          image: markerImage // 마커이미지 설정
//                        });
//
//                        // 마커가 지도 위에 표시되도록 설정합니다
//    //                    if((marker_object['CE7'] != undefined && marker_object['CE7'].find(marker) === -1) && (marker_object['FD6'] != undefined && marker_object['FD6'].find(marker) === -1)){
//                         if(marker_object['CE7'] != undefined){
//                            $.each(marker_object['CE7'], function(index, item){
//                                if(item.getPosition().La.toFixed(13) != marker.getPosition().La.toFixed(13) && item.getPosition().Ma.toFixed(13) != marker.getPosition().Ma.toFixed(13)){
//                                    marker.setMap(map);
//                                    marker_object['CE7'].push(marker);
//                                }
//                            });
//                         }
//
//                         if(marker_object['FD6'] != undefined){
//                             $.each(marker_object['FD6'], function(index, item){
//                                 if(item.getPosition().La.toFixed(13) != marker.getPosition().La.toFixed(13) && item.getPosition().Ma.toFixed(13) != marker.getPosition().Ma.toFixed(13)){
//                                     marker.setMap(map);
//                                     marker_object['FD6'].push(marker);
//                                 }
//                             });
//                         }
//
//                        // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
//                        var hashtag_count = item.hashtagCount;
//                        var hashtag_name = item.hashtagName;
//                        var content =
//                            '<div class="bubble">' +
//                            '   <p>' + hashtag_name + '</p>' +
//                            '   <ion-icon name="heart" style="color: red;"></ion-icon>' +
//                            '   <span id="sp">' + hashtag_count + '</span>' +
//                            '</div>'
//
//                        // 커스텀 오버레이가 표시될 위치입니다
//                        var position = new kakao.maps.LatLng(item.latitude + latitude_adjust, item.longitude - longitude_adjust);
//
//                        // 커스텀 오버레이를 생성합니다
//                        var customOverlay = new kakao.maps.CustomOverlay({
//                            map: map,
//                            position: position,
//                            content: content,
//                            yAnchor: 1
//                        });
//
//                        if(overlay_object['CE7'] != undefined){
//                            $.each(overlay_object['CE7'], function(index, item){
//                                if(item.getPosition().La.toFixed(13) != customOverlay.getPosition().La.toFixed(13)
//                                    && item.getPosition().Ma.toFixed(13) != (customOverlay.getPosition().Ma).toFixed(13)){
//                                    overlay_object['CE7'].push(customOverlay);
//                                }
//                            });
//                        }
//
//                        if(overlay_object['FD6'] != undefined){
//                             $.each(overlay_object['FD6'], function(index, item){
//                                 if(item.getPosition().La.toFixed(13) != customOverlay.getPosition().La.toFixed(13)
//                                    && item.getPosition().Ma.toFixed(13) != (customOverlay.getPosition().Ma).toFixed(13)){
//                                     overlay_object['FD6'].push(customOverlay);
//                                 }
//                             });
//                        }
//                    } else {
//                        if(marker_object['CE7'] != undefined){
//                            $.each(marker_object['CE7'], function(m_index, m_item){
//                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == m_item.getPosition().Ma.toFixed(13)){
//                                    m_item.setMap(null);
//                                }
//                            });
//                        }
//                        if(marker_object['FD6'] != undefined){
//                            $.each(marker_object['FD6'], function(m_index, m_item){
//                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == m_item.getPosition().Ma.toFixed(13)){
//                                    m_item.setMap(null);
//                                }
//                            });
//                        }
//
//                        if(overlay_object['CE7'] != undefined){
//                            $.each(overlay_object['CE7'], function(m_index, m_item){
//                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == (m_item.getPosition().Ma).toFixed(13)){
//                                    m_item.setMap(null);
//                                }
//                            });
//                        }
//                        if(overlay_object['FD6'] != undefined){
//                            $.each(overlay_object['FD6'], function(m_index, m_item){
////                                console.log('name: ', m_item.getContent());
////                                console.log('index : ', index ,' db data : ' , item.longitude.toFixed(13), ' 지도 data : ', m_item.getPosition().La.toFixed(13), ' 차이 : ', item.longitude.toFixed(13)-m_item.getPosition().La.toFixed(13));
////                                console.log('조정 후 차이: ', Number(item.longitude.toFixed(13)) - (Number(m_item.getPosition().La.toFixed(13)) + longitude_adjust));
//
//                                if(item.longitude == (m_item.getPosition().La + longitude_adjust).toFixed(13)
//                                    && item.latitude == (m_item.getPosition().Ma - latitude_adjust).toFixed(13)){
//                                    m_item.setMap(null);
//                                }
//                            });
//                        }
//                    }
//               });
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

function test(data) {
    var overlay_CE7_temp = [];
    var overlay_FD6_temp = [];
    var dir = (level - be_level) > 0 ? -1 : 0;
    var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
    var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
    $.each(data, function(d_index, d_item){
        $.each(marker_object['CE7'], function(index, item){
            if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
                item.setMap(null);
        });
        $.each(overlay_object['CE7'], function(index, item){
            if(d_item.longitude.toFixed(13) !== String((Number(item.getPosition().La.toFixed(13)) + longitude_adjust).toFixed(13)) ||
                d_item.latitude.toFixed(13) !== String((Number(item.getPosition().Ma.toFixed(13)) - latitude_adjust).toFixed(13)))
                item.setMap(null);
            else overlay_CE7_temp.push(item);
        });
        $.each(marker_object['FD6'], function(index, item){
            if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
                item.setMap(null);
//                        else console.log(index);
        });

        $.each(overlay_object['FD6'], function(index, item){
//                        console.log('name: ', item.getContent());
//                        console.log('index : ', index ,' db data : ' , d_item.longitude.toFixed(13), ' 지도 data : ', item.getPosition().La.toFixed(13), ' 차이 : ', d_item.longitude.toFixed(13)-item.getPosition().La.toFixed(13));
//                        console.log('조정 후 차이: ', Number(d_item.longitude.toFixed(13)) - (Number(item.getPosition().La.toFixed(13)) + longitude_adjust));
            if(d_item.longitude.toFixed(13) !== String((Number(item.getPosition().La.toFixed(13)) + longitude_adjust).toFixed(13)) ||
                d_item.latitude.toFixed(13) !== String((Number(item.getPosition().Ma.toFixed(13)) - latitude_adjust).toFixed(13)))
                item.setMap(null);
            else overlay_FD6_temp.push(item);
        });
    });
    overlay_list = overlay_CE7_temp.concat(overlay_FD6_temp);
} else {
    customOverlay_flag = false;

    $.each(marker_object['CE7'], function(index, item){
        item.setMap(null);
    });

    $.each(marker_object['FD6'], function(index, item){
        item.setMap(null);
    });

    $.each(overlay_object['CE7'], function(index, item){
        item.setMap(null);
    });

    $.each(overlay_object['FD6'], function(index, item){
        item.setMap(null);
    });
}
}
