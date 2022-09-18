var marker_list = [];
var marker_object = {};
var overlay_list = [];
var overlay_object = {};

var marker_list_CE7 = [];
var overlay_list_CE7 = [];
var marker_list_FD6 = [];
var overlay_list_FD6 = [];
var category_list = [];

var be_level;
var level = 3;

var customOverlay_flag = true;

// 초기 지도 설정
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.56667120440735, 126.97962007985399),
    level: 3
};
var map = new kakao.maps.Map(container, options);

// 카테고리 - 카페 (디폴트)
$.each(document.querySelector('.btn_category_wrap').children, function(index, item){
    if(item.className === 'selected'){
        category_list.push(item.value);
    }
});

getHashtagByCategory(map, 'FD6');

setTimeout(function(){ bubbleClick()}, 1500);

// 마우스 이동 이벤트
kakao.maps.event.addListener(map, 'dragend', function() {

    // 지도 중심좌표를 얻어옵니다
    var latlng = map.getCenter();

    var message = '마우스 이동 이벤트: 변경된 지도 중심좌표는 ' + latlng.getLat() + ' 이고, ';
    message += '경도는 ' + latlng.getLng() + ' 입니다';
    console.log(message);

    // 현재 지도 영역 구하기
    var bounds = map.getBounds();
    message = '현재 지도 영역:  ' + bounds.toString() + ' 입니다';
    console.log(message);

    var rect = {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa};

});

// 지도 확대 축소 이벤트
kakao.maps.event.addListener(map, 'zoom_changed', function() {

    // 지도의 현재 레벨을 얻어옵니다
    be_level = be_level != null ? level : 3;
    level = map.getLevel();

    var message = '지도 확대 축소 이벤트: 현재 지도 레벨은 ' + level + ' 입니다';
    console.log(message);

    // 지도 현재 영역 구하기
    var bounds = map.getBounds();
    message = '지도 현재 영역:  ' + bounds.toString() + ' 입니다';
    console.log(message);

    var rect = {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa};

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

    setTimeout(function(){ bubbleClick() }, 1500);
});

//var rect_json = {"ha": 126.66578831035362, "oa": 126.9951487382762, "pa": 37.40847533960485, "qa": 37.59625487247741};
//var rect_json = {"ha": 126.66578831035362, "oa": 126.6751487382762, "pa": 37.52847533960485, "qa": 37.53625487247741}; // 19개 - 인천
//var rect_json = {"ha": 126.66578831035362, "oa": 126.6751487382762, "pa": 37.52847533960485, "qa": 37.53625487247741}; // 20개 - 인천 FD6
//var rect_json = {"ha": 126.97895739177251, "oa": 126.97940437779806, "pa": 37.565536149395754, "qa": 37.566816079358794}; // 7개 - 서울시청 FD6
//var rect_json = {"ha": 126.97895739177251, "oa": 126.98090437779806, "pa": 37.565536149395754, "qa": 37.566816079358794}; // 25개 - 서울시청 FD6
//var rect_json = {"ha": 126.97143175074082, "oa": 126.97603175074082, "pa": 37.56407463819326, "qa": 37.56907463819326}; // 27개 - 서울시청 FD6
//var rect_json = {"ha": 126.97603175074082, "oa": 126.98103175074082, "pa": 37.565536149395754, "qa": 37.567216079358794}; // 64개 - 서울시청 FD6
var rect_json = {"ha": 126.97043548971624, "oa": 126.98724287412578, "pa": 37.56463301059637, "qa": 37.57004199797056}; // 1098개 - 서울시청 FD6

//var param = JSON.stringify(rect_json);

function kakaoMap() {
    $.ajax({
       url:'/api/kakaoMap',
       type:'POST',
       dataType: 'json',
       contentType : "application/json; charset=utf-8",
       data: JSON.stringify(rect_json),
//       followRedirect: false,
       success:function(data){
               console.log(data);
       },
       error : function(e){
       }
    });
}

function getHashtagByCategory(map, category) {

//    var rect_json = {"ha": 126.66578831035362, "oa": 126.9951487382762, "pa": 37.40847533960485, "qa": 37.59625487247741,
//                    "category_list": category};
//    var param = JSON.stringify(rect_json);
    var json = map.getBounds();
    json["category_list"] = category_list;
    param = JSON.stringify(json);

   $.ajax({
        url:'/api/getHashtag',
        type:'POST',
        dataType: 'json',
        contentType : "application/json; charset=utf-8",
        data: param,
        success:function(data){
            create_marker(data, category);
        },
        error : function(e){
            console.log(e);
        }
   });
}

function create_marker(data, category) {
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
        var content =
            '<div class="bubble">' +
            '   <div style="margin-top: 5px;">' + hashtag_name + '</div>' +
            '   <ion-icon name="heart" style="color: red;"></ion-icon>' +
            '   <span id="sp">' + hashtag_count + '</span>' +
            '</div>'

        // 커스텀 오버레이가 표시될 위치입니다
        var dir = (level - be_level) > 0 ? -1 : 0;
        var latitude_adjust = 0.00018 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
        var longitude_adjust = 0.000027 * (level > 1 ? Math.pow(2, level - 1 + dir) : 1);
        var position = new kakao.maps.LatLng(item.latitude + latitude_adjust, item.longitude - longitude_adjust);

        // 커스텀 오버레이를 생성합니다
        var customOverlay = new kakao.maps.CustomOverlay({
            map: map,
            position: position,
            content: content,
            yAnchor: 1
        });

        overlay_list.push(customOverlay);
   });

   marker_object[category] = marker_list;
   overlay_object[category] = overlay_list;
}

function bubbleClick() {
    $(".bubble").click(function(event){
        var $target = $(event.target);

        var hashtag_name;
        if($target.prop('tagName') === 'DIV') {
            hashtag_name = $target.children().length === 3 ? $target.children()[0].innerText : event.target.innerText;
        } else {
            hashtag_name = $target.parent().children()[0].innerText;
        }

//        var json = {"ha": 126.66578831035362, "oa": 126.9951487382762, "pa": 37.40847533960485, "qa": 37.59625487247741,
//                            "category_list": 'FD6,CE7', "hashtag_name": hashtag_name
//                            };
//        var param = JSON.stringify(json);

        var json = map.getBounds();
        json["category_list"] = category_list;
        json["hashtag_name"] = hashtag_name;
        param = JSON.stringify(json);

        $.ajax({
           url:'/api/findByHashtagName',
           type:'POST',
           dataType: 'json',
           contentType : "application/json; charset=utf-8",
           data: param,
           success:function(data){
                if(data && data.length > 0) {
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
                        if(index >= 9) return false;

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

                        /* apply postUrl */
                        document.getElementById('btn_instagram').name = data[index].postUrl;

                        /* apply placeUrl */
                        document.getElementById('btn_kakaoMap').name = data[index].placeUrl;
                    });
                }
           },
           error : function(e){
                console.log(e);
           }
        });

        $(".modal-title").text(hashtag_name);
        $('#myModal').modal("show");
    });
}
