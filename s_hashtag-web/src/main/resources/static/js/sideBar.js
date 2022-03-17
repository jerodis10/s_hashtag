var category_wrap = document.querySelector('.category_wrap');
function select(El){
    if(El.className === 'selected') El.classList.remove('selected');
    else El.classList.add('selected');
}

category_wrap.addEventListener('click', e => {
    const selected = e.target;
    select(e.target);
})


document.getElementById("btn_cafe").addEventListener('click', function() {

var category_list = [];

$.each(document.querySelector('.category_wrap').children, function(index, item){
    if(item.className === 'selected'){
        category_list.push(item.value);
    }
});


//    $.ajax({
//       url:'/searchCategory',
//       type:'POST',
//       dataType: 'json',
//            data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564},
//       success: function(data) {
//
//       },
//       error: function(e){
//       }
//    });
});

document.getElementById("btn_food").addEventListener('click', function() {
//    $.ajax({
//       url:'/searchCategory',
//       type:'POST',
//       dataType: 'json',
//            data: {ha: 126.960, oa: 126.970, pa: 37.563, qa: 37.564},
//       success: function(data) {
//
//       },
//       error: function(e){
//       }
//    });
});






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