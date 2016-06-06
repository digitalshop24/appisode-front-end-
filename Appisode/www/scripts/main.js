$( ".js_activ" ).click(function() {
  $( this ).toggleClass( "active" );
});

$( ".js_active_like" ).click(function() {
  $( this ).toggleClass( "active" );
});
    
$( ".js_active_info" ).click(function() {
  $( this ).toggleClass( "active" );
});

$(document).ready(function(){
	$(".tabs").lightTabs();
});

$(".js_popup1").click(function () {
  $( ".popup1" ).toggleClass( "open" );
});
$(".js-closed").click(function () {
  $(".popup1").toggleClass( "open");
});

$( ".js_popup1" ).click(function() {
  $( ".wrap-po" ).toggleClass( "openone" );
});
$(".js-closed").click(function () {
  $(".wrap-po").toggleClass( "openone");
});


jQuery(function($) {
  $('input[type="checkbox"]').onoff();
});

//$('.center').slick({
//  centerMode: true,
//  centerPadding: '120px',
//  arrows:false,
//  slidesToShow: 3,
//  responsive: [
//    {
//      breakpoint: 690,
//      settings: {
//        arrows: false,
//        centerMode: true,
//        centerPadding: '40px',
//        slidesToShow: 3
//      }
//    },
//    {
//      breakpoint: 480,
//      settings: {
//        arrows: false,
//        centerMode: true,
//        centerPadding: '40px',
//        slidesToShow: 1
//      }
//    }
//  ]
//});





