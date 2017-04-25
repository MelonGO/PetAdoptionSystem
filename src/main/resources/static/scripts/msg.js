$(function(){
	$("#sendBtn").click(function(){
	   var usn=$("#userName").val();
	   var content=$("#msgContent").val();
	   if(usn.replace(/(^\s+)|(\s+$)/,"")==""){
		   alert("接收者不能为空");
	   }else if(content.replace(/(^\s+)|(\s+$)/,"")==""){
		   alert("消息不能为空");
	   }else{
		   $.getJSON("sendmsg.do",{"userName":usn,"msgConent":content},function(d){
			   if(d=="0"){
				   alert("请先登录!");
			   }
			   else{
				   alert("发送成功!");
			   } 
		   });	   
	   }
	});
});	

$(function(){ 	

	getMsg();

	function getMsg(){		
		$.getJSON("hasMsg.do",{},function(d){
			if(d){
				if(d>99){
					$("#nav_msg_num").text("99+");
				}else{
					$("#nav_msg_num").text(d);
				}
				$(".notice").css("display","inline");  
			}else{
				$(".notice").css("display","none");  
			}
		});		
	}
	setInterval(getMsg,3000);
});

$(function(){
	$("#nav_msg").click(function(){  
	   $.getJSON("nav_msg.do",{},function(d){
		   if(d=="0"){
			   alert("请先登录!");
		   }
	   });	   
	});
});	

$(function(){
	$("#li_msg_private").click(function(){  
		$("#li_msg_private").css("background-color","#fff");
		$("#li_msg_comment").css("background-color","#f9f9f9");
		$("#li_msg_notify").css("background-color","#f9f9f9");	
		$.getJSON("msg_private.do",{},function(d){
			if(d=="0"){
				login();	
			}
		});	   
	});
	
	$("#li_msg_comment").click(function(){ 
		$("#li_msg_private").css("background-color","#f9f9f9");
		$("#li_msg_comment").css("background-color","#fff");
		$("#li_msg_notify").css("background-color","#f9f9f9");
	   $.getJSON("msg_comment.do",{},function(d){
		   if(d=="0"){
			   login();			
		   }
	   });	   
	});
	
	$("#li_msg_notify").click(function(){  
		$("#li_msg_private").css("background-color","#f9f9f9");
		$("#li_msg_comment").css("background-color","#f9f9f9");
		$("#li_msg_notify").css("background-color","#fff");
	   $.getJSON("msg_notify.do",{},function(d){
		   if(d=="0"){
				login();				
		   }
	   });	   
	});
	
	var login=function(){
		$("#corBackground").animate({ opacity: "show" ,height:document.body.scrollHeight }, "slow");
		$("#corInsertHref").animate({ opacity: "show" }, "slow");
		autoSize($("#corInsertHref"));	
		var autoSize = function(corObj){
			var wWidth = $(window).width(), wHeight = $(window).height();
			var ihWidth = corObj.outerWidth(true), ihHeight = corObj.outerHeight(true);
			corObj.css({ "top": ((wHeight - ihHeight)/2) +document.body.scrollTop+ "px", "left": ((wWidth - ihWidth) / 2) + "px" });
			
			$().scroll(function() {
				$("#corInsertHref").css({"top": ((wHeight - ihHeight)/2) +document.body.scrollTop+ "px"});
			});
		}
	}
	
	
	
	
	
	
});	