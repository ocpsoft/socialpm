$(document).ready(function() {

	// SET ACTIVE LINKS

	var path = window.location.pathname;
	
	$('a').each(function() {
		
		var href = $(this).attr('href');
		if (href == path) {
			$(this).addClass('active');
		}
	});

    $('.nav>li>a[href="' + path + '"]').each(function() {
    	$(this).parent().addClass('active');
	});

	// Dropdown example for topbar nav
	// ===============================

	/*
	 * Clicking menus $("body").bind("click", function (e) {
	 * $('.dropdown-toggle, .menu').parent("li").removeClass("open"); });
	 * 
	 * $(".dropdown-toggle, .menu").click(function (e) { var $li =
	 * $(this).parent("li").toggleClass('open'); return false; });
	 */

	// Hoverin menus
	$(".dropdown, .menu").hover(function(e) {
		$(this).toggleClass('open');
	}, function(e) {
		$(this).removeClass('open');
	});

	$('a').focus(function() {
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
		setTimeout(function() {
			$(el).select();
		}, 1);
	});

	// POSITION STATIC TWIPSIES
	// ========================
	$(window).bind('load resize', function() {
		$('body > .topbar').scrollSpy();
		$(".twipsies a").each(function() {
			$(this).twipsy({
				live : false,
				placement : $(this).attr('title'),
				trigger : 'manual',
				offset : 2
			}).twipsy('show');
		});
	});

});
