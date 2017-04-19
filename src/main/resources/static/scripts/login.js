$(function(){
	$("#btnlogin").click(function(){
		$("#corBackground").animate({ opacity: "show" ,height:document.body.scrollHeight }, "slow");
		$("#corInsertHref").animate({ opacity: "show" }, "slow");
		autoSize($("#corInsertHref"));
	});

	$(window).resize(function(){
		autoSize($("#corInsertHref"));
	});

	var autoSize = function(corObj){
		var wWidth = $(window).width(), wHeight = $(window).height();
		var ihWidth = corObj.outerWidth(true), ihHeight = corObj.outerHeight(true);
		corObj.css({ "top": ((wHeight - ihHeight)/2) +document.body.scrollTop+ "px", "left": ((wWidth - ihWidth) / 2) + "px" });
		
		$().scroll(function() {
			$("#corInsertHref").css({"top": ((wHeight - ihHeight)/2) +document.body.scrollTop+ "px"});
		});
	}
	

 });
$(function(){
	$(".loginclosebtn").click(function(){
		$("#corBackground").fadeOut("slow");		
		$("#corInsertHref").fadeOut("slow");
	});
	
});


/*登录表单输入验证*/
$(function(){
    $("#loginusername").focus(function(){
          /*
           * */
    }).blur(function(){
         $(this).removeClass("focus");
         /*
          * 
          */
         
         
    });
})

