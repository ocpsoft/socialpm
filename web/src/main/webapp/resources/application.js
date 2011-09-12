$(document).ready(function(){

  // Dropdown example for topbar nav
  // ===============================

	/* Clicking menus
  $("body").bind("click", function (e) {
    $('.dropdown-toggle, .menu').parent("li").removeClass("open");
  });
  $(".dropdown-toggle, .menu").click(function (e) {
    var $li = $(this).parent("li").toggleClass('open');
    return false;
  });
  */

	// Hoverin menus
	$(".dropdown, .menu").hover(
	  	function (e) { $(this).toggleClass('open'); },
	  	function (e) { $(this).removeClass('open'); }
  	);

  $('a').focus(function(){
     $(this).blur();
  });


  // add on logic
  // ============

  $('.add-on :checkbox').click(function() {
    if ($(this).attr('checked')) {
      $(this).parents('.add-on').addClass('active');
    } else {
      $(this).parents('.add-on').removeClass('active');
    }
  });

  // Copy code blocks in docs
  $(".copy-code").focus(function() {
    var el = this;
    // push select to event loop for chrome :{o
    setTimeout(function () { $(el).select(); }, 1);
  });


  // POSITION TWIPSIES
  // =================

  $('.twipsies.well a').each(function () {
    var type = this.title
      , $anchor = $(this)
      , $twipsy = $('.twipsy.' + type)

      , twipsy = {
          width: $twipsy.width() + 10
        , height: $twipsy.height() + 10
        }

      , anchor = {
          position: $anchor.position()
        , width: $anchor.width()
        , height: $anchor.height()
        }

      , offset = {
          above: {
            top: anchor.position.top - twipsy.height
          , left: anchor.position.left + (anchor.width/2) - (twipsy.width/2)
          }
        , below: {
            top: anchor.position.top + anchor.height
          , left: anchor.position.left + (anchor.width/2) - (twipsy.width/2)
          }
        , left: {
            top: anchor.position.top + (anchor.height/2) - (twipsy.height/2)
          , left: anchor.position.left - twipsy.width - 5
          }
        , right: {
            top: anchor.position.top + (anchor.height/2) - (twipsy.height/2)
          , left: anchor.position.left + anchor.width + 5
          }
      };

    $twipsy.css(offset[type]);

  });

});
