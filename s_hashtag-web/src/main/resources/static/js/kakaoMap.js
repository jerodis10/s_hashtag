var marker_list = [];
var marker_object = {};
var overlays = [];

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
    var level = map.getLevel();

    var message = '지도 확대 축소 이벤트: 현재 지도 레벨은 ' + level + ' 입니다';
    var resultDiv = document.getElementById('result2');
    resultDiv.innerHTML = message;


    // 지도 현재 영역 구하기
    var bounds = map.getBounds();
    message = '지도 현재 영역:  ' + bounds.toString() + ' 입니다';
    var resultDiv = document.getElementById('result3');
    resultDiv.innerHTML = message;

    var rect = {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa};

<!--        kakaoMap(rect);-->

});


function kakaoMap() {
    var bounds = map.getBounds();

    $.ajax({
       url:'/kakaoMap',
       type:'POST',
       dataType: 'json',
//          data: {ha: 124, oa: 132, pa: 33, qa: 43},
        data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564},
//            data : {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa},
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

    $.ajax({
       url:'/getHashtag',
       type:'GET',
       dataType: 'json',
//       data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: category_list},
        data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: category},
       success:function(data){
//            console.log(data);
           marker_list = [];
           $.each(data, function(index, item){
                var img_src = "";
                if(item.HASHTAG_COUNT < 10) img_src = '../img/markers/mapMarker1.png';
                if(10 <= item.HASHTAG_COUNT && item.HASHTAG_COUNT < 100) img_src = '../img/markers/mapMarker2.png';
                if(100 <= item.HASHTAG_COUNT && item.HASHTAG_COUNT < 1000) img_src = '../img/markers/mapMarker3.png';
                if(1000 <= item.HASHTAG_COUNT && item.HASHTAG_COUNT < 10000) img_src = '../img/markers/mapMarker4.png';
                if(10000 <= item.HASHTAG_COUNT) img_src = '../img/markers/mapMarker5.png';

                var imageSrc = img_src,
                imageSize = new kakao.maps.Size(34, 39), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                    markerPosition = new kakao.maps.LatLng(item.LATITUDE, item.LONGITUDE); // 마커가 표시될 위치입니다

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                  position: markerPosition,
                  image: markerImage // 마커이미지 설정
                });
//                if(index == 0) a = marker;
//                markers.push(marker);

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(map);
                marker_list.push(marker);

//                if(!selected) marker_list.push(marker);

                // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                var hashtag_count = item.HASHTAG_COUNT;
                var hashtag_name = item.HASHTAG_NAME;
                var content =
                    '<div class="bubble">' +
                    '   <p>' + hashtag_name + '</p>' +
                    '   <ion-icon name="heart" style="color: red;"></ion-icon>' +
                    '   <span id="sp">' + hashtag_count + '</span>' +
                    '</div>'

                // 커스텀 오버레이가 표시될 위치입니다
                var position = new kakao.maps.LatLng(item.LATITUDE+0.001, item.LONGITUDE);

                // 커스텀 오버레이를 생성합니다
                var customOverlay = new kakao.maps.CustomOverlay({
                    map: map,
                    position: position,
                    content: content,
                    yAnchor: 1
                });
//                overlays.push(customOverlay);
           });
//           marker_object[category] = null;
           marker_object[category] = marker_list;
       },
       error : function(e){
       }
    })
//    .done(function(data){
//        $.each(marker_list, function(index, item){
//            item.setMap(null);
//        });
//    });

//    if(!Unselected){
//        $.ajax({
//           url:'/getHashtag',
//           type:'GET',
//           dataType: 'json',
//           data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: Unselected},
//           success:function(data){
////                console.log(data);
////                console.log(marker_list);
//
//                $.each(marker_list, function(index, item){
////                    console.log("marker_list", index, item);
//                    console.log(item);
//                    item.setMap(null);
//                });
//           },
//           error : function(e){
//           }
//        });
//    }
}



function create_marker() {
    $.ajax({
       url:'/createMarker',
       type:'GET',
        data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564},
       success:function(data){
//           console.log(data);

           $.each(data, function(index, item){
                var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다
                imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                    markerPosition = new kakao.maps.LatLng(item.latitude, item.longitude); // 마커가 표시될 위치입니다

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                  position: markerPosition,
                  image: markerImage // 마커이미지 설정
                });

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(map);
           });
       },
       error : function(e){
//<!--               console.log(e);-->
       }
    });
}


create_marker_test(map, 'FD6');
