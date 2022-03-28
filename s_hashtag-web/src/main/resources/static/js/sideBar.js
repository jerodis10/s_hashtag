document.getElementById("btn_cafe").addEventListener('click', function(e) {

    var flag_selected = true;

    if(e.target.className === 'selected') {
        e.target.classList.remove('selected');
        console.log(marker_list);
        console.log(marker_object);
        $.each(marker_object['CE7'], function(index, item){
            item.setMap(null);
        });
    }
    else {
        e.target.classList.add('selected');
        flag_selected = false;
        create_marker_test(map, 'CE7');
    }
});

document.getElementById("btn_food").addEventListener('click', function(e) {

    var flag_selected = true;

    if(e.target.className === 'selected') {
        e.target.classList.remove('selected');
        $.each(marker_object['FD6'], function(index, item){
            item.setMap(null);
        });
    }
    else {
        e.target.classList.add('selected');
        flag_selected = false;
        create_marker_test(map, 'FD6');
    }
});

document.getElementById("searchBox").addEventListener('keypress', function(e) {
    if(e.keyCode === 13) {
        getHashtagByKeyword(document.getElementById("searchBox").value);
    }
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