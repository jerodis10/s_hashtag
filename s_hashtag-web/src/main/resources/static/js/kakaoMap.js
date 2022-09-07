var marker_list = [];
var marker_object = {};
var overlay_list = [];
var overlay_object = {};

var marker_list_CE7 = [];
var overlay_list_CE7 = [];
var marker_list_FD6 = [];
var overlay_list_FD6 = [];

var be_level;
var level = 3;

var customOverlay_flag = true;

var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.56667120440735, 126.97962007985399),
    level: 3
};

var map = new kakao.maps.Map(container, options);

// 마우스 이동 이벤트
kakao.maps.event.addListener(map, 'dragend', function() {

    // 지도 중심좌표를 얻어옵니다
    var latlng = map.getCenter();

    var message = '마우스 이동 이벤트: 변경된 지도 중심좌표는 ' + latlng.getLat() + ' 이고, ';
    message += '경도는 ' + latlng.getLng() + ' 입니다';

    var resultDiv = document.getElementById('result');
    resultDiv.innerHTML = message;

    // 현재 지도 영역 구하기
    var bounds = map.getBounds();
    message = '현재 지도 영역:  ' + bounds.toString() + ' 입니다';
    var resultDiv = document.getElementById('result3');
    resultDiv.innerHTML = message;

    var rect = {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa};

<!--        kakaoMap(rect);-->
});

// 지도 확대 축소 이벤트
kakao.maps.event.addListener(map, 'zoom_changed', function() {

    // 지도의 현재 레벨을 얻어옵니다
    be_level = be_level != null ? level : 3;
    level = map.getLevel();

    var message = '지도 확대 축소 이벤트: 현재 지도 레벨은 ' + level + ' 입니다';
    var resultDiv = document.getElementById('result2');
    resultDiv.innerHTML = message;


    // 지도 현재 영역 구하기
    var bounds = map.getBounds();
    message = '지도 현재 영역:  ' + bounds.toString() + ' 입니다';
    var resultDiv = document.getElementById('result3');
    resultDiv.innerHTML = message;

    var rect = {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa};

    var category_list = [];
    $.each(document.querySelector('.category_wrap').children, function(index, item){
        if(item.className === 'selected'){
            category_list.push(item.value);
        }
    });

    if(customOverlay_flag) {
        $.each(category_list, function(c_index, category){
            var customOverlay_list = [];
            $.each(overlay_list, function(index, overlay){
                overlay.setMap(null);
                var dir = (level - be_level) > 0 ? -1 : 0;
                var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
                var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
                var position = new kakao.maps.LatLng(overlay.getPosition().Ma + (level - be_level) * latitude_adjust, overlay.getPosition().La - (level - be_level) * longitude_adjust);
                var content = overlay.getContent();
                var customOverlay = new kakao.maps.CustomOverlay({
                    map: map,
                    position: position,
                    content: content,
                    yAnchor: 1
                });
                customOverlay_list.push(customOverlay);
            });

            overlay_list = [];
            $.each(customOverlay_list, function(index, overlay){
                overlay_list.push(overlay);
            });

            overlay_object[category] = overlay_list;
        });
    }
});

//var rect_json = {"ha": 126.66578831035362, "oa": 126.9951487382762, "pa": 37.40847533960485, "qa": 37.59625487247741};
//var rect_json = {"ha": 126.66578831035362, "oa": 126.6751487382762, "pa": 37.52847533960485, "qa": 37.53625487247741}; // 19개 - 인천
//var rect_json = {"ha": 126.66578831035362, "oa": 126.6751487382762, "pa": 37.52847533960485, "qa": 37.53625487247741}; // 3개 - 인천
var rect_json = {"ha": 126.97895739177251, "oa": 126.97940437779806, "pa": 37.565536149395754, "qa": 37.566816079358794}; // 7개 - 서울시청
//var rect_json = {"ha": 126.97895739177251, "oa": 126.98090437779806, "pa": 37.565536149395754, "qa": 37.566816079358794}; // 25개 - 서울시청

//var param = JSON.stringify(rect_json);

function kakaoMap() {
    $.ajax({
       url:'/api/kakaoMap',
       type:'POST',
       dataType: 'json',
       contentType : "application/json; charset=utf-8",
       data: JSON.stringify(rect_json),
//          data: {ha: 124, oa: 132, pa: 33, qa: 43},
//        data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564},
//           data: {ha: 126.960, oa: 126.970, pa: 37.560, qa: 37.563},
//            data: {ha: 126.900, oa: 126.990, pa: 37.560, qa: 37.570},
//            data : {ha: 126.75578831035362, oa: 126.7651487382762, pa: 37.41847533960485, qa: 37.42825487247741},
//                data : {ha: 126.75578831035362, oa: 126.7771487382762, pa: 37.41847533960485, qa: 37.44625487247741}, // 1/16
//                data : {ha: 126.75578831035362, oa: 126.7951487382762, pa: 37.41847533960485, qa: 37.45625487247741}, // 1/10
//                data : {ha: 126.66578831035362, oa: 126.6851487382762, pa: 37.52847533960485, qa: 37.55625487247741},
//                data : {ha: 126.66578831035362, oa: 126.6751487382762, pa: 37.52847533960485, qa: 37.53625487247741}, // total_count = 20
//             data : {ha: 126.75578831035362, oa: 126.8051487382762, pa: 37.41847533960485, qa: 37.46625487247741}, // 1/8
//             data : {ha: 126.75578831035362, oa: 126.8551487382762, pa: 37.41847533960485, qa: 37.51625487247741}, // 1/4
//            data : {ha: 126.75578831035362, oa: 126.9551487382762, pa: 37.41847533960485, qa: 37.55625487247741}, // 1/2
//            data : {ha: 126.75578831035362, oa: 127.2251487382762, pa: 37.41847533960485, qa: 37.70625487247741}, // 서울시 전체
//       followRedirect: false,
       success:function(data){
               console.log(data);
       },
       error : function(e){
       }
    });
}

function create_marker_test(map, category) {

//    var latlng = map.getCenter();
//    var container = document.getElementById('map');
//    var options = {
////        center: new kakao.maps.LatLng(37.56667120440735, 126.97962007985399),
//        center: new kakao.maps.LatLng(latlng.getLat(), latlng.getLng()),
//        level: 3
//    };
//
//    var new_map = new kakao.maps.Map(container, options);



    var category_list = [];
    var category_delete_list = [];
    $.each(document.querySelector('.category_wrap').children, function(index, item){
        if(item.className === 'selected'){
            category_list.push(item.value);
        }
    });

    var rect_json = {"ha": 126.66578831035362, "oa": 126.9951487382762, "pa": 37.40847533960485, "qa": 37.59625487247741,
                    "category_list": category};
    var param = JSON.stringify(rect_json);

    $.ajax({
       url:'/api/getHashtag',
//        url:'/getHashtag',
//           url:'/api/admin/members/getHashtag',
//       type:'GET',
        type:'POST',
       dataType: 'json',
       contentType : "application/json; charset=utf-8",
//       contentType : "application/x-www-form-urlencoded",
//       data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: category_list},
//        data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: category},
//        data: {ha: 125, oa: 128, pa: 36, qa: 38, category_list: category},
        data: param,
//        data: {"ha": "125", "oa": "128", "pa": "36", "qa": "38", "category_list": category},
       success:function(data){
//            console.log(data);
           marker_list = [];
           overlay_list = [];
           $.each(data, function(index, item){
                var img_src = "";
                if(item.hashtagCount < 10) img_src = '../img/markers/mapMarker1.png';
                if(10 <= item.hashtagCount && item.hashtagCount < 100) img_src = '../img/markers/mapMarker2.png';
                if(100 <= item.hashtagCount && item.hashtagCount < 1000) img_src = '../img/markers/mapMarker3.png';
                if(1000 <= item.hashtagCount && item.hashtagCount < 10000) img_src = '../img/markers/mapMarker4.png';
                if(10000 <= item.hashtagCount) img_src = '../img/markers/mapMarker5.png';

                var imageSrc = img_src,
                imageSize = new kakao.maps.Size(34, 39), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                    markerPosition = new kakao.maps.LatLng(item.latitude, item.longitude); // 마커가 표시될 위치입니다

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                  position: markerPosition,
                  image: markerImage // 마커이미지 설정
                });

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(map);
                marker_list.push(marker);

                // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                var hashtag_count = item.hashtagCount;
                var hashtag_name = item.hashtagName;
                var background_color;
                if(item.hashtagCount < 10) background_color = 'style="background-color: blue;"';
                if(10 <= item.hashtagCount && item.hashtagCount < 100) background_color = 'style="background-color: red;"';
                if(100 <= item.hashtagCount && item.hashtagCount < 1000) background_color = 'style="background-color: darkorchid;"';
                if(1000 <= item.hashtagCount && item.hashtagCount < 10000) background_color = 'style="background-color: aqua;"';
                if(10000 <= item.hashtagCount) background_color = 'style="background-color: brown;"';
                var content =
                    '<div class="bubble">' +
//                    '<div class="bubble" ' + background_color + '>'  +
                    '   <p>' + hashtag_name + '</p>' +
                    '   <ion-icon name="heart" style="color: red;"></ion-icon>' +
                    '   <span id="sp">' + hashtag_count + '</span>' +
                    '</div>'

                // 커스텀 오버레이가 표시될 위치입니다
                var dir = (level - be_level) > 0 ? -1 : 0;
                var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
                var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
                var position = new kakao.maps.LatLng(item.latitude + latitude_adjust, item.longitude - longitude_adjust);
//                var latitude_adjust = 0.00018 * Math.pow(level-1, 2);
//                var longitude_adjust = 0.000027 * Math.pow(level-1, 2);
//                var position = new kakao.maps.LatLng(item.latitude + latitude_adjust, item.longitude - longitude_adjust);

                // 커스텀 오버레이를 생성합니다
                var customOverlay = new kakao.maps.CustomOverlay({
                    map: map,
                    position: position,
                    content: content,
                    yAnchor: 1
                });

                overlay_list.push(customOverlay);
//                console.log('item.hashtagName : ', item.hashtagName ,' item.longitude : ', item.longitude ,' customOverlay La : ', customOverlay.getPosition().La.toFixed(13), ' 차이 : ', item.longitude - customOverlay.getPosition().La.toFixed(13));
//                console.log('customOverlay Ma : ', customOverlay.getPosition().Ma.toFixed(13));
           });

           marker_object[category] = marker_list;
           overlay_object[category] = overlay_list;
       },
       error : function(e){
        console.log(e);
       }
    });
}

function create_overlay(item, category){
    var img_src = "";

    if(item.hashtagCount < 10) img_src = '../img/markers/mapMarker1.png';
    if(10 <= item.hashtagCount && item.hashtagCount < 100) img_src = '../img/markers/mapMarker2.png';
    if(100 <= item.hashtagCount && item.hashtagCount < 1000) img_src = '../img/markers/mapMarker3.png';
    if(1000 <= item.hashtagCount && item.hashtagCount < 10000) img_src = '../img/markers/mapMarker4.png';
    if(10000 <= item.hashtagCount) img_src = '../img/markers/mapMarker5.png';

    var imageSrc = img_src,
    imageSize = new kakao.maps.Size(34, 39), // 마커이미지의 크기입니다
    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
        markerPosition = new kakao.maps.LatLng(item.latitude, item.longitude); // 마커가 표시될 위치입니다

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      position: markerPosition,
      image: markerImage // 마커이미지 설정
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    if(category === 'CE7') marker_list_CE7.push(marker);
    if(category === 'FD6') marker_list_FD6.push(marker);
//    if(category === 'CE7') marker_CE7 = marker;
//    if(category === 'FD6') marker_FD6 = marker;

    // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    var hashtag_count = item.hashtagCount;
    var hashtag_name = item.hashtagName;
    var content =
        '<div class="bubble" id="bubble" data-target="#myModal">' +
        '   <p>' + hashtag_name + '</p>' +
        '   <ion-icon name="heart" style="color: red;"></ion-icon>' +
        '   <span id="sp">' + hashtag_count + '</span>' +
        '</div>'

    // 커스텀 오버레이가 표시될 위치입니다
    var position = new kakao.maps.LatLng(item.latitude+0.001, item.longitude);

    // 커스텀 오버레이를 생성합니다
    var customOverlay = new kakao.maps.CustomOverlay({
        map: map,
        position: position,
        content: content,
        yAnchor: 1
    });

    overlay_list.push(customOverlay);
    if(category === 'CE7') overlay_list_CE7.push(customOverlay);
    if(category === 'FD6') overlay_list_FD6.push(customOverlay);

//    if(category === 'CE7') overlay_CE7 = customOverlay;
//    if(category === 'FD6') overlay_FD6 = customOverlay;

//    marker_object[category] = marker_list;
//    overlay_object[category] = overlay_list;
}

create_marker_test(map, 'FD6');

//$("div[class=bubble]").click(function(){
//    alert("1");
//});

//document.getElementsByClassName("bubble").onclick(function(){
//    alert("1");
//});

//$(".bubble").click(function(){
//    alert("1");
//    console.log("log");
//});

//document.getElementsByClassName("bubble").addEventListener('click', function(){
//    console.log("11");
//});

setTimeout(bubbleClick, 1500);

function bubbleClick() {
    $(".bubble").click(function(event){
//        console.log($(this));

        var $target = $(event.target);
        var json = {"ha": 126.66578831035362, "oa": 126.9951487382762, "pa": 37.40847533960485, "qa": 37.59625487247741,
                            "category_list": 'FD6', "hashtag_name": $target.children()[0].innerText
                            };
        var param = JSON.stringify(json);

        $.ajax({
           url:'/api/findByHashtagName',
           type:'POST',
           dataType: 'json',
           contentType : "application/json; charset=utf-8",
           data: param,
           success:function(data){
                if(data) {
                    var image_id;
                    var controlClass;

                    /* remove slide-control */
                    var parent_div = document.querySelector(".slide_control_wrap");
                    var remove_div = document.querySelector(".slide-control");
                    if(remove_div != null) parent_div.removeChild(remove_div); // 부모로부터 myDiv 객체 떼어내기

                    /* create slide-control */
                    var slide_control_wrap = document.querySelector(".slide_control_wrap");
                    var new_slideDiv = document.createElement('div');
                    new_slideDiv.setAttribute('class', "slide-control");
                    slide_control_wrap.appendChild(new_slideDiv);

                    $.each(data, function(index, item){
                        /* apply image */
                        image_id = "image" + String(index+1);
                        document.getElementById(image_id).src = data[index].imageUrl;

                        /* create control */
                        var slide_control = document.querySelector(".slide-control");
                        var newDiv = document.createElement('div');
                        controlClass = "control0";
                        newDiv.setAttribute('class', controlClass + String(index+1));
                        slide_control.appendChild(newDiv);

                        /* create label_left */
                        var control_div = document.querySelector("." + controlClass + String(index+1));
                        var new_label_left = document.createElement('label');
                        new_label_left.setAttribute('class', 'left');
                        var label_for = "slide0";
                        if(index == 0){
                            label_for += String(data.length);
                        } else {
                            label_for += String(index);
                        }
                        new_label_left.setAttribute('for', label_for);
                        control_div.appendChild(new_label_left);

                        /* create label_right */
                        var new_label_right = document.createElement('label');
                        new_label_right.setAttribute('class', 'right');
                        var label_for = "slide0";
                        if(index == data.length-1){
                            label_for += "1";
                        } else {
                            label_for += String(index+2);
                        }
                        new_label_right.setAttribute('for', label_for);
                        control_div.appendChild(new_label_right);
                    });
                }
           },
           error : function(e){
                console.log(e);
           }
        });

        $(".modal-title").text($target.children()[0].innerText);
        $('#myModal').modal("show");
    });
}



