$(function(){
	$("#sendBtn").click(function(){
	   var usn=$("#userName").val();
	   var content=$("#msgContentT").val();
	   if(usn.replace(/(^\s+)|(\s+$)/,"")==""){
		   alert("接收者不能为空");
	   }else if(content.replace(/(^\s+)|(\s+$)/,"")==""){
		   alert("消息不能为空");
	   }else{
		   $.getJSON("sendmsg.do",{"userName":usn,"msgConent":content},function(d){
			   if(d=="0"){
				   $("#msgModal").modal('hide');
				   $("#alertInfo").text("请先登录你的账号");
				   $("#alertInfo").css("display","block");
				   setTimeout(function(){  
					   $("#alertSuccess").css("Info","none");
					},1000);
			   }
			   else{
				  
				   $("#msgModal").modal('hide');
				   $("#alertSuccess").text("发送消息成功");
				   $("#alertSuccess").css("display","block");
				   setTimeout(function(){  
					   $("#alertSuccess").css("display","none");
					},1000);
			   } 
		   });	   
	   }
	});
	
	$("#sendNotifyBtn").click(function(){
		   var usn=$("#notifyUserName").val();
		   var content=$("#notifyContentT").val();
		   if(usn.replace(/(^\s+)|(\s+$)/,"")==""){
			   alert("接收者不能为空");
		   }else if(content.replace(/(^\s+)|(\s+$)/,"")==""){
			   alert("消息不能为空");
		   }else{
			   $.getJSON("sendNotifyMsg.do",{"userName":usn,"msgConent":content},function(d){
				   if(d=="0"){
					   $("#msgNotifyModal").modal('hide');
					   $("#alertInfo").text("请先登录你的账号");
					   $("#alertInfo").css("display","block");
					   setTimeout(function(){  
						   $("#alertSuccess").css("Info","none");
						},1000);
				   }
				   else{
					   $("#msgNotifyModal").modal('hide');
					   $("#alertSuccess").text("发送通知成功");
					   $("#alertSuccess").css("display","block");
					   setTimeout(function(){  
						   $("#alertSuccess").css("display","none");
						},1000);
				   } 
			   });	   
		   }
		});
	
	$("#msgDelBtn").click(function(){
		$.getJSON("del_msg.do",{"mid":msgID},function(d){
			if(d=="0"){
				$("#msgDelModal").modal('hide');
				$("#alertInfo").text("请先登录你的账号");
				$("#alertInfo").css("display","block");
				setTimeout(function(){  
					$("#alertSuccess").css("Info","none");
				},1000);
			}if(d=="1"){
				$("#msgDelModal").modal('hide');
				$("#alertDanger").text("删除失败")
				$("#alertDanger").css("display","block");
				setTimeout(function(){  
					$("#alertDanger").css("display","none");					
				},1000)
			}if(d=="2"){
				$("#msgDelModal").modal('hide');
				$("#alertSuccess").text("删除成功")
				$("#alertSuccess").css("display","block");
				setTimeout(function(){  
					$("#alertSuccess").css("display","none");
					location.reload();
				},1000)
			}
		});
		
	});
});	

$(function(){ 	
	var msg_num=0;
	var private_num=0;
	var comment_num=0;
	var notify_num=0;
	getMsg();
	$.getJSON("hasPrivateMsg.do",{},function(d){
		if(d>0){
			private_num=d;
			if(d>99){
				$("#private_notice").text("99+");
			}else{
				$("#private_notice").text(d);
			}
			$("#private_notice").css("display","inline");  
		}else{
			$("#private_notice").css("display","none");  
		}
	});	
	$.getJSON("hasCommentMsg.do",{},function(d){
		if(d>0){
			comment_num=d;
			if(d>99){
				$("#comment_notice").text("99+");
			}else{
				$("#comment_notice").text(d);
			}
			$("#comment_notice").css("display","inline");  
		}else{
			$("#comment_notice").css("display","none");  
		}
	});
	$.getJSON("hasNotifyMsg.do",{},function(d){
		if(d>0){
			notify_num=d;
			if(d>99){
				$("#notify_notice").text("99+");
			}else{
				$("#notify_notice").text(d);
			}
			$("#notify_notice").css("display","inline");  
		}else{
			$("#notify_notice").css("display","none");  
		}
	});

	function getMsg(){		
		$.getJSON("hasMsg.do",{},function(d){
			if(d){
				msg_num=d;
			}
		});		
		var total_msg=msg_num+private_num+comment_num+notify_num;
		if(total_msg>0){
			if(total_msg>99){
				$("#nav_msg_num").text("99+");
			}else{
				$("#nav_msg_num").text(total_msg);
			}
			$("#nav_msg_num").css("display","inline");  
		}else{
			$("#nav_msg_num").css("display","none");  
		}
	}
	setInterval(getMsg,3000);

	$("#li_msg_private").click(function(){  
		
		
		$("#li_msg_private").css("background-color","#fff");
		$("#li_msg_comment").css("background-color","#f9f9f9");
		$("#li_msg_notify").css("background-color","#f9f9f9");	
		$("#send_private_msg").css("display","block");
		$("#send_notify_msg").css("display","none");
		$("#private_content").css("display","block");
		$("#comment_content").css("display","none");
		$("#notify_content").css("display","none");	
		$.getJSON("msg_private.do",{},function(d){
			if(d=="0"){
				login();	
			}else{
				private_num=0;
				getMsg();
				$.getJSON("hasPrivateMsg.do",{},function(d2){
					if(d2){
						private_num=d2;
						if(d2>99){
							$("#private_notice").text("99+");
						}else{
							$("#private_notice").text(d2);
						}
						$("#private_notice").css("display","inline");  
					}else{
						$("#private_notice").css("display","none");  
					}
				});	
			}
		});	
	});
	
	$("#li_msg_comment").click(function(){ 
		
		$("#li_msg_private").css("background-color","#f9f9f9");
		$("#li_msg_comment").css("background-color","#fff");
		$("#li_msg_notify").css("background-color","#f9f9f9");
		$("#send_private_msg").css("display","none");
		$("#send_notify_msg").css("display","none");
		$("#private_content").css("display","none");
		$("#comment_content").css("display","block");
		$("#notify_content").css("display","none");
		$.getJSON("msg_comment.do",{},function(d){
			if(d=="0"){
				login();			
			}else{
				comment_num=0;
				getMsg();
				$.getJSON("hasCommentMsg.do",{},function(d2){
					if(d2){
						comment_num=d2;
						if(d2>99){
							$("#comment_notice").text("99+");
						}else{
							$("#comment_notice").text(d2);
						}
						$("#comment_notice").css("display","inline");  
					}else{
						$("#comment_notice").css("display","none");  
					}
				});
			}
		});	   
		getMsg();
	});
	
	$("#li_msg_notify").click(function(){  
		$("#li_msg_private").css("background-color","#f9f9f9");
		$("#li_msg_comment").css("background-color","#f9f9f9");
		$("#li_msg_notify").css("background-color","#fff");
		$("#send_private_msg").css("display","none");
		$("#send_notify_msg").css("display","block");	
		$("#private_content").css("display","none");
		$("#comment_content").css("display","none");
		$("#notify_content").css("display","block");
	   $.getJSON("msg_notify.do",{},function(d){
		   if(d=="0"){
				login();				
		   }else{
			   notify_num=0;
			   getMsg();
			   $.getJSON("hasNotifyMsg.do",{},function(d2){
					if(d2){
						notify_num=d2;
						if(d2>99){
							$("#notify_notice").text("99+");
						}else{
							$("#notify_notice").text(d2);
						}
						$("#notify_notice").css("display","inline");  
					}else{
						$("#notify_notice").css("display","none");  
					}
				});
		   }
	   });	
	   getMsg();
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

