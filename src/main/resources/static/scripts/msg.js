$(function(){
	$("#sendBtn").click(function(){
	   var usn=$("#userName").val();
	   var content=$("#msgContent").val();

	   if(usn.replace(/(^\s+)|(\s+$)/,"")=="")
	   {
		   alert("接收者不能为空");
	   }else if(content.replace(/(^\s+)|(\s+$)/,"")==""){
		   alert("消息不能为空");
	   }
		   
	   else
	   {
		   
		   $.getJSON("sendmsg.do",{"userName":usn,"msgConent":content},function(d){
			   if(d=="0")
			   {
				   alert("请以管理员身份登录后发送!");
			   }
			   else
			   {
				   alert("发送成功!");
			   }
			   
		   });
		   
	   }
	});
});	





$(function(){ 	
	function getMsg(){		
		$.getJSON("hasMsg.do",{},function(d){
			if(d){
				$(".notice").css("display","inline");  
			}else{
				$(".notice").css("display","none");  
			}
		});		
	}
	setInterval(getMsg,3000);
});