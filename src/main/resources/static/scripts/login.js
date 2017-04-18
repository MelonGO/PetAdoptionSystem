$(function(){
	$("#btnlogin").click(function(){
		$("#corBackground").animate({ opacity: "show" }, "slow");
		$("#corInsertHref").animate({ opacity: "show" }, "slow");
		autoSize($("#corInsertHref"));
	});

	$(window).resize(function(){
		autoSize($("#corInsertHref"));
	});

	var autoSize = function(corObj){
		var wWidth = $(window).width(), wHeight = $(window).height();
		var ihWidth = corObj.outerWidth(true), ihHeight = corObj.outerHeight(true);
		corObj.css({ "top": ((wHeight - ihHeight)/2) + "px", "left": ((wWidth - ihWidth) / 2) + "px" });
	}
 });