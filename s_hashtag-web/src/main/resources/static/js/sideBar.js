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
    }
    else {
        e.target.classList.add('selected');
        flag_selected = false;
        create_marker_test(map, btn_cafe_value);
    }
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
    }
    else {
        e.target.classList.add('selected');
        flag_selected = false;
        create_marker_test(map, btn_food_value);
    }
});

document.getElementById("searchBox").addEventListener('keypress', function(e) {
    if(e.keyCode === 13) {
        getHashtagByKeyword(document.getElementById("searchBox").value);
    }
});

document.getElementById("btn_check1").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check1', true);
    } else {
        getHashtagByCount('check1', false);
    }

//    if(e.target.className === 'bi-check-lg') {
//        $.each(marker_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(marker_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//    }
//    else {
//        getHashtagByCount('check1');
//    }
});

document.getElementById("btn_check2").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check2', true);
    } else {
        getHashtagByCount('check2', false);
    }

//    if(e.target.className === 'bi-check-lg') {
//        $.each(marker_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(marker_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//    }
//    else {
//        getHashtagByCount('check2');
//    }
});

document.getElementById("btn_check3").addEventListener('click', function(e){
    var btn_food_value = document.getElementById("btn_food").value;
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check3', true);
    } else {
        getHashtagByCount('check3', false);
    }

//    if(e.target.className === 'bi-check-lg') {
//        $.each(marker_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(marker_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//    }
//    else {
//        getHashtagByCount('check3');
//    }
});

document.getElementById("btn_check4").addEventListener('click', function(e){
    var btn_food_value = document.getElementById("btn_food").value;
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check4', true);
    } else {
        getHashtagByCount('check4', false);
    }

//    if(e.target.className === 'bi-check-lg') {
//        $.each(marker_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(marker_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//    }
//    else {
//        getHashtagByCount('check4');
//    }
});

document.getElementById("btn_check5").addEventListener('click', function(e){
    var btn_food_value = document.getElementById("btn_food").value;
    e.target.classList.toggle('bi-check-lg');

    if(e.target.className === 'bi-check-lg') {
        getHashtagByCount('check5', true);
    } else {
        getHashtagByCount('check5', false);
    }

//    if(e.target.className === 'bi-check-lg') {
//        $.each(marker_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['CE7'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(marker_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//        $.each(overlay_object['FD6'], function(index, item){
//            item.setMap(null);
//        });
//    }
//    else {
//        getHashtagByCount('check5');
//    }
});

function getHashtagByKeyword(searchText, category) {

    var category_arr = [];
    if(document.getElementById("btn_cafe").className === 'selected') category_arr.push(document.getElementById("btn_cafe").value);
    if(document.getElementById("btn_food").className === 'selected') category_arr.push(document.getElementById("btn_food").value);
    var category_param = category_arr.join(',')

    $.ajax({
       url:'/api/getHashtagByKeyword',
       type:'GET',
       dataType: 'json',
//       data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: 'CE7'},
//       data: {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa, category_list: 'CE7'},
        data: {ha: 126.96190764549995, oa: 126.99019733287525, pa: 37.56300112995975, qa: 37.5696007924915, category_list: category_param, searchText: searchText}, // ???????????? ??????
//        data: {ha: 126.75578831035362, oa: 127.2251487382762, pa: 37.41847533960485, qa: 37.70625487247741, category_list: 'CE7'}, // ????????? ??????

       success:function(data){
            console.log(data);

            if(data){
                $.each(data, function(d_index, d_item){
                    $.each(marker_object['CE7'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
                            item.setMap(null);
                    });
                    $.each(overlay_object['CE7'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== String(item.getPosition().Ma.toFixed(13)-0.001))
                            item.setMap(null);
                    });
                    $.each(marker_object['FD6'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
                            item.setMap(null);
                    });
                    $.each(overlay_object['FD6'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== String(item.getPosition().Ma.toFixed(13)-0.001))
                            item.setMap(null);
                    });
                });
            }
       },
       error : function(e){
       }
    });
}

function getHashtagByCount(check_type, check_flag) {
    var category_arr = [];
    if(document.getElementById("btn_cafe").className === 'selected') category_arr.push(document.getElementById("btn_cafe").value);
    if(document.getElementById("btn_food").className === 'selected') category_arr.push(document.getElementById("btn_food").value);
    var category_param = category_arr.join(',');

    $.ajax({
           url:'/api/getHashtagByCount',
           type:'GET',
           dataType: 'json',
           data: {ha: 126.96190764549995, oa: 126.99019733287525, pa: 37.56300112995975, qa: 37.5696007924915,   // ???????????? ??????
                  category_list: category_param, check: check_type},
           success:function(data){
               marker_list = [];
               overlay_list = [];
               $.each(data, function(index, item){
                    if(check_flag) {
                        var img_src = "";
                        if(item.hashtagCount < 10) img_src = '../img/markers/mapMarker1.png';
                        if(10 <= item.hashtagCount && item.hashtagCount < 100) img_src = '../img/markers/mapMarker2.png';
                        if(100 <= item.hashtagCount && item.hashtagCount < 1000) img_src = '../img/markers/mapMarker3.png';
                        if(1000 <= item.hashtagCount && item.hashtagCount < 10000) img_src = '../img/markers/mapMarker4.png';
                        if(10000 <= item.hashtagCount) img_src = '../img/markers/mapMarker5.png';

                        var imageSrc = img_src,
                        imageSize = new kakao.maps.Size(34, 39), // ?????????????????? ???????????????
                        imageOption = {offset: new kakao.maps.Point(27, 69)}; // ?????????????????? ???????????????. ????????? ????????? ???????????? ????????? ???????????? ????????? ???????????????.

                        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                            markerPosition = new kakao.maps.LatLng(item.latitude, item.longitude); // ????????? ????????? ???????????????

                        // ????????? ???????????????
                        var marker = new kakao.maps.Marker({
                          position: markerPosition,
                          image: markerImage // ??????????????? ??????
                        });

                        // ????????? ?????? ?????? ??????????????? ???????????????
    //                    if((marker_object['CE7'] != undefined && marker_object['CE7'].find(marker) === -1) && (marker_object['FD6'] != undefined && marker_object['FD6'].find(marker) === -1)){
                         if(marker_object['CE7'] != undefined){
                            $.each(marker_object['CE7'], function(index, item){
                                if(item.getPosition().La.toFixed(13) != marker.getPosition().La.toFixed(13) && item.getPosition().Ma.toFixed(13) != marker.getPosition().Ma.toFixed(13)){
                                    marker.setMap(map);
                                    marker_object['CE7'].push(marker);
//                                    marker_list_CE7.push(marker);
                                }
                            });
                         }

                         if(marker_object['FD6'] != undefined){
                             $.each(marker_object['FD6'], function(index, item){
                                 if(item.getPosition().La.toFixed(13) != marker.getPosition().La.toFixed(13) && item.getPosition().Ma.toFixed(13) != marker.getPosition().Ma.toFixed(13)){
                                     marker.setMap(map);
                                     marker_object['FD6'].push(marker);
//                                     marker_list_FD6.push(marker);
                                 }
                             });
                         }

    //                      && !marker_object['CE7'].includes(marker)) && (marker_object['FD6'] != undefined && !marker_object['FD6'].includes(marker))){
    //                        marker.setMap(map);
    //                        if(item.category_group_code === 'CE7') marker_list_CE7.push(marker);
    //                        if(item.category_group_code === 'FD6') marker_list_FD6.push(marker);
    //                    }

                        // ????????? ??????????????? ????????? ???????????? HTML ??????????????? document element??? ???????????????
                        var hashtag_count = item.hashtagCount;
                        var hashtag_name = item.hashtagName;
                        var content =
                            '<div class="bubble">' +
                            '   <p>' + hashtag_name + '</p>' +
                            '   <ion-icon name="heart" style="color: red;"></ion-icon>' +
                            '   <span id="sp">' + hashtag_count + '</span>' +
                            '</div>'

                        // ????????? ??????????????? ????????? ???????????????
                        var position = new kakao.maps.LatLng(item.latitude+0.001, item.longitude);

                        // ????????? ??????????????? ???????????????
                        var customOverlay = new kakao.maps.CustomOverlay({
                            map: map,
                            position: position,
                            content: content,
                            yAnchor: 1
                        });

//                        overlay_object['CE7'].push(customOverlay);
//                        overlay_object['FD6'].push(customOverlay);

                        if(overlay_object['CE7'] != undefined){
                            $.each(overlay_object['CE7'], function(index, item){
                                if(item.getPosition().La.toFixed(13) != customOverlay.getPosition().La.toFixed(13) && item.getPosition().Ma.toFixed(13) != (customOverlay.getPosition().Ma-0.001).toFixed(13)){
                                    overlay_object['CE7'].push(customOverlay);
                                }
                            });
                        }

                        if(overlay_object['FD6'] != undefined){
                             $.each(overlay_object['FD6'], function(index, item){
                                 if(item.getPosition().La.toFixed(13) != customOverlay.getPosition().La.toFixed(13) && item.getPosition().Ma.toFixed(13) != (customOverlay.getPosition().Ma-0.001).toFixed(13)){
                                     overlay_object['FD6'].push(customOverlay);
                                 }
                             });
                        }
                    } else {
                        if(marker_object['CE7'] != undefined){
                            $.each(marker_object['CE7'], function(m_index, m_item){
                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == m_item.getPosition().Ma.toFixed(13)){
                                    m_item.setMap(null);
                                }
                            });
                        }
                        if(marker_object['FD6'] != undefined){
                            $.each(marker_object['FD6'], function(m_index, m_item){
                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == m_item.getPosition().Ma.toFixed(13)){
                                    m_item.setMap(null);
                                }
                            });
                        }

                        if(overlay_object['CE7'] != undefined){
                            $.each(overlay_object['CE7'], function(m_index, m_item){
                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == (m_item.getPosition().Ma-0.001).toFixed(13)){
                                    m_item.setMap(null);
                                }
                            });
                        }
                        if(overlay_object['FD6'] != undefined){
                            $.each(overlay_object['FD6'], function(m_index, m_item){
                                if(item.longitude == m_item.getPosition().La.toFixed(13) && item.latitude == (m_item.getPosition().Ma-0.001).toFixed(13)){
                                    m_item.setMap(null);
                                }
                            });
                        }
                    }


               });

//               $.each(category_arr, function(index, category){
//                   if(category === 'CE7'){
//                       marker_object[category] = marker_list_CE7;
//                       overlay_object[category] = overlay_list_CE7;
//                   } else {
//                       marker_object[category] = marker_list_FD6;
//                       overlay_object[category] = overlay_list_FD6;
//                   }
//               });
           },
           error : function(e){
           }
    });
}


function getHashtagByCount2() {

    var category_arr = [];
    if(document.getElementById("btn_cafe").className === 'selected') category_arr.push(document.getElementById("btn_cafe").value);
    if(document.getElementById("btn_food").className === 'selected') category_arr.push(document.getElementById("btn_food").value);
    var category_param = category_arr.join(',');

    var check1, check2, check3, check4, check5;
    if(document.getElementById("btn_check1").className === 'bi-check-lg') check1 = true;
    else check1 = false;
    if(document.getElementById("btn_check2").className === 'bi-check-lg') check2 = true;
    else check2 = false;
    if(document.getElementById("btn_check3").className === 'bi-check-lg') check3 = true;
    else check3 = false;
    if(document.getElementById("btn_check4").className === 'bi-check-lg') check4 = true;
    else check4 = false;
    if(document.getElementById("btn_check5").className === 'bi-check-lg') check5 = true;
    else check5 = false;

    $.ajax({
       url:'/api/getHashtagByCount',
       type:'GET',
       dataType: 'json',
       data: {ha: 126.96190764549995, oa: 126.99019733287525, pa: 37.56300112995975, qa: 37.5696007924915, category_list: category_param,
              check1: check1, check2: check2, check3: check3, check4: check4, check5: check5,}, // ???????????? ??????
       success:function(data){
            console.log(data);

            if(data){
//                $.each(marker_object['CE7'], function(index, item){
//                    var flag = false;
//                    $.each(data, function(d_index, d_item){
//                        if(d_item.longitude.toFixed(13) === item.getPosition().La.toFixed(13)
//                            && d_item.latitude.toFixed(13) === item.getPosition().Ma.toFixed(13)){
//                                flag = true;
//                                return false;
//                        }
//                    });
//                    if(!flag) item.setMap(null);
//                });
//
//                $.each(overlay_object['CE7'], function(index, item){
//                    var flag = false;
//                    $.each(data, function(d_index, d_item){
//                        if(d_item.longitude.toFixed(13) === item.getPosition().La.toFixed(13)
//                            && d_item.latitude.toFixed(13) === String((item.getPosition().Ma-0.001).toFixed(13))){
//                                flag = true;
//                                return false;
//                        }
//                    });
//                    if(!flag) item.setMap(null);
//                });
//
//                $.each(marker_object['FD6'], function(index, item){
//                    var flag = false;
//                    $.each(data, function(d_index, d_item){
//                        if(d_item.longitude.toFixed(13) === item.getPosition().La.toFixed(13)
//                            && d_item.latitude.toFixed(13) === item.getPosition().Ma.toFixed(13)){
//                                flag = true;
//                                return false;
//                        }
//                    });
//                    if(!flag) item.setMap(null);
//                });
//
//                $.each(overlay_object['FD6'], function(index, item){
//                    var flag = false;
//                    $.each(data, function(d_index, d_item){
//                        if(d_item.longitude.toFixed(13) === item.getPosition().La.toFixed(13)
//                            && d_item.latitude.toFixed(13) === String((item.getPosition().Ma-0.001).toFixed(13))){
//                                flag = true;
//                                return false;
//                        }
//                    });
//                    if(!flag) item.setMap(null);
//                });


                var marker_list_CE7 = [];
                var overlay_list_CE7 = [];
                var marker_list_FD6 = [];
                var overlay_list_FD6 = [];

                $.each(data, function(d_index, d_item){
//                    var marker_FD6, overlay_FD6, marker_CE7, overlay_CE7;

                    $.each(marker_object['CE7'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13)){
                            create_overlay(d_item, 'CE7');
                        } else {
                            item.setMap(null);
                        }
                    });
                    $.each(overlay_object['CE7'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== String((item.getPosition().Ma-0.001).toFixed(13))){
                            create_overlay(d_item, 'CE7');
                        } else {
                            item.setMap(null);
                        }
                    });
                    $.each(marker_object['FD6'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13)){
                            create_overlay(d_item, 'FD6');
                        } else {
                            item.setMap(null);
                        }
                    });
                    $.each(overlay_object['FD6'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== String((item.getPosition().Ma-0.001).toFixed(13))){
                            create_overlay(d_item, 'FD6');
                        } else {
                            item.setMap(null);
                        }
                    });

//                    if(marker_CE7 != null && marker_CE7 != undefined) marker_object['CE7'].push(marker_CE7);
//                    if(overlay_CE7 != null && overlay_CE7 != undefined) overlay_object['CE7'].push(overlay_CE7);
//                    if(marker_FD6 != null && marker_FD6 != undefined) marker_object['FD6'].push(marker_FD6);
//                    if(overlay_FD6 != null && overlay_FD6 != undefined) overlay_object['FD6'].push(overlay_FD6);
                });

                $.each(marker_list_CE7, function(index, item){
                    marker_object['CE7'].push(item);
                });

                $.each(marker_list_FD6, function(index, item){
                    marker_object['FD6'].push(item);
                });

                $.each(overlay_list_CE7, function(index, item){
                    overlay_object['CE7'].push(item);
                });

                $.each(overlay_list_FD6, function(index, item){
                    overlay_object['FD6'].push(item);
                });


            }
       },
       error : function(e){
       }
    });
}


/* EXPANDER MENU */
const showMenu = (toggleId, navbarId, bodyId) => {
    const toggle = document.getElementById(toggleId),
    navbar = document.getElementById(navbarId),
    bodypadding = document.getElementById(bodyId)

    if( toggle && navbar ) {
        toggle.addEventListener('click', ()=>{
            navbar.classList.toggle('expander');

            bodypadding.classList.toggle('body-pd')
        })
    }
}

showMenu('nav-toggle', 'navbar', 'body-pd')

/* LINK ACTIVE */
const linkColor = document.querySelectorAll('.nav__link')
function colorLink() {
    linkColor.forEach(l=> l.classList.remove('active'))
    this.classList.add('active')
}
linkColor.forEach(l=> l.addEventListener('click', colorLink))

/* COLLAPSE MENU */
const linkCollapse = document.getElementsByClassName('collapse__link')
var i

for(i=0;i<linkCollapse.length;i++) {
    linkCollapse[i].addEventListener('click', function(){
        const collapseMenu = this.nextElementSibling
        collapseMenu.classList.toggle('showCollapse')

        const rotate = collapseMenu.previousElementSibling
        rotate.classList.toggle('rotate')
    });
}