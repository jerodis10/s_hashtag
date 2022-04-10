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
    getHashtagByCount();
});

document.getElementById("btn_check2").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');
    getHashtagByCount();
});

document.getElementById("btn_check3").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');
    getHashtagByCount();
});

document.getElementById("btn_check4").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');
    getHashtagByCount();
});

document.getElementById("btn_check5").addEventListener('click', function(e){
    e.target.classList.toggle('bi-check-lg');
    getHashtagByCount();
});

function getHashtagByKeyword(searchText, category) {

    var category_arr = [];
    if(document.getElementById("btn_cafe").className === 'selected') category_arr.push(document.getElementById("btn_cafe").value);
    if(document.getElementById("btn_food").className === 'selected') category_arr.push(document.getElementById("btn_food").value);
    var category_param = category_arr.join(',')

    $.ajax({
       url:'/getHashtagByKeyword',
       type:'GET',
       dataType: 'json',
//       data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564, category_list: 'CE7'},
//       data: {ha: bounds.ha, oa: bounds.oa, pa: bounds.pa, qa: bounds.qa, category_list: 'CE7'},
        data: {ha: 126.96190764549995, oa: 126.99019733287525, pa: 37.56300112995975, qa: 37.5696007924915, category_list: category_param, searchText: searchText}, // 서울시청 주변
//        data: {ha: 126.75578831035362, oa: 127.2251487382762, pa: 37.41847533960485, qa: 37.70625487247741, category_list: 'CE7'}, // 서울시 전체

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


function getHashtagByCount() {

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
       url:'/getHashtagByCount',
       type:'GET',
       dataType: 'json',
       data: {ha: 126.96190764549995, oa: 126.99019733287525, pa: 37.56300112995975, qa: 37.5696007924915, category_list: category_param,
              check1: check1, check2: check2, check3: check3, check4: check4, check5: check5,}, // 서울시청 주변
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

                $.each(data, function(d_index, d_item){
                    $.each(marker_object['CE7'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
                            create_overlay(d_item);
                        else {
                            item.setMap(null);
                        }
                    });
                    $.each(overlay_object['CE7'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== String((item.getPosition().Ma-0.001).toFixed(13)))
                            create_overlay(d_item);
                        else {
                            item.setMap(null);
                        }
                    });
                    $.each(marker_object['FD6'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== item.getPosition().Ma.toFixed(13))
                            create_overlay(d_item);
                        else {
                            item.setMap(null);
                        }
                    });
                    $.each(overlay_object['FD6'], function(index, item){
                        if(d_item.longitude.toFixed(13) !== item.getPosition().La.toFixed(13) || d_item.latitude.toFixed(13) !== String((item.getPosition().Ma-0.001).toFixed(13)))
                            create_overlay(d_item);
                        else {
                            item.setMap(null);
                        }
                    });
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