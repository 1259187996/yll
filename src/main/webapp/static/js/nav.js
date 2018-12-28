$(function() {
	"use strict";

	// custom scrollbar
	// $("html").niceScroll({
	// styler : "fb",
	// cursorcolor : "#12c4c8",
	// cursorwidth : '6',
	// cursorborderradius : '0px',
	// background : '#FAF9F9',
	// spacebarenabled : false,
	// cursorborder : '0',
	// zindex : '1000'
	// });

	// Left Menu
	jQuery('.menu-list > a').click(function() {

		var parent = jQuery(this).parent();
		var sub = parent.find('> ul');

		if (!jQuery('body').hasClass('left-side-collapsed')) {
			if (sub.is(':visible')) {
				sub.slideUp(100, function() {
					parent.removeClass('nav-active');
					jQuery('.main-content').css({
						height : ''
					});
					mainContentHeightAdjust();
				});
			} else {
				visibleSubMenuClose();
				parent.addClass('nav-active');
				sub.slideDown(100, function() {
					mainContentHeightAdjust();
				});
			}
		}
		return false;
	});

	// class add mouse hover
	jQuery('.custom-nav > li').hover(function() {
		jQuery(this).addClass('nav-hover');
	}, function() {
		jQuery(this).removeClass('nav-hover');
	});

});

function visibleSubMenuClose() {
	jQuery('.menu-list').each(function() {
		var t = jQuery(this);
		if (t.hasClass('nav-active')) {
			t.find('> ul').slideUp(200, function() {
				t.removeClass('nav-active');
			});
		}
	});
}

function mainContentHeightAdjust() {
	// Adjust main content height
	// var docHeight = jQuery(document).height() - 30;
	// if (docHeight > jQuery('.main-content').height())
	// jQuery('.main-content').height(docHeight);
}

function navSel(id) {
	var parent = (jQuery("#" + id)).parent().parent().parent();
	var sub = parent.find('> ul');
	if (!jQuery('body').hasClass('left-side-collapsed')) {
		if (sub.is(':visible')) {
			sub.slideUp(100, function() {
				parent.removeClass('nav-active');
				jQuery('.main-content').css({
					height : ''
				});
				mainContentHeightAdjust();
			});
		} else {
			visibleSubMenuClose();
			parent.addClass('nav-active');
			sub.slideDown(100, function() {
				mainContentHeightAdjust();
			});
		}
	}
	(jQuery("#" + id)).parent().addClass('active');
}