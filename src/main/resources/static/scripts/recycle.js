$(function(){
	
	$('.btnRecycle').click(function (event) {  
		var trs=$("table#table1 tbody tr");
		var tr=$(this).closest("tr");
        var td=tr.eq(0).find("td")
        var type=td.eq(0).text();
        var age=td.eq(1).text();
        var name=td.eq(2).text();
        var sex=td.eq(3).text();
        var price=td.eq(4).text();
        var adoptTime=td.eq(5).text().substring(0,10);
        var petID=td.eq(6).text();
       
        var modal=$("#recycleModal");
        modal.find('.modal-body #recyclePetID').val(petID) ;
        modal.find('.modal-body #recyclePetName').val(name) ;
        modal.find('.modal-body #recyclePetAge').val(age) ;
        modal.find('.modal-body #recyclePetType').val(type) ;
        modal.find('.modal-body #recyclePetAdoptTime').val(adoptTime) ;
	}) ;
	
	$('.btnRecycleCancel').click(function (event) {  
		var trs=$("table#table1 tbody tr");
		var tr=$(this).closest("tr");
        var td=tr.eq(0).find("td")
        var type=td.eq(0).text();
        var age=td.eq(1).text();
        var name=td.eq(2).text();
        var sex=td.eq(3).text();
        var price=td.eq(4).text();
        var state=td.eq(13).text();
        var petID=td.eq(6).text();
        var reason=td.eq(7).text();
        var phone=td.eq(8).text();
        var address=td.eq(9).text();
        var sponsorship=td.eq(10).text();
        if(sponsorship==0){
        	var isSponsorship="直接送回";
        }else if(sponsorship==1){
        	var isSponsorship="进行助养";
        }
        var money=td.eq(11).text();
        var applyTime=td.eq(12).text().substring(0,19);
       
        var modal=$("#recycleCancelModal");
        modal.find('.modal-body #recycleCancelPetID').text(petID) ;
        modal.find('.modal-body #recycleCancelPetName').text(name) ;
        modal.find('.modal-body #recycleCancelPetAge').text(age) ;
        modal.find('.modal-body #recycleCancelPetType').text(type) ;
        modal.find('.modal-body #recycleCancelState').text(state) ;
        modal.find('.modal-body #recycleCancelRecycleReason').text(reason) ;
        modal.find('.modal-body #recycleCancelSponsorship').text(sponsorship) ;
        modal.find('.modal-body #recycleCancelMoney').text(money) ;
        modal.find('.modal-body #recycleCancelApplyTime').text(applyTime) ;
	}) ;
	
	$('.btnRecycleShow').click(function (event) {  
		var trs=$("table#table1 tbody tr");
		var tr=$(this).closest("tr");
        var td=tr.eq(0).find("td")
        var type=td.eq(0).text();
        var age=td.eq(1).text();
        var name=td.eq(2).text();
        var sex=td.eq(3).text();
        var price=td.eq(4).text();
        var state=td.eq(13).text();
        var petID=td.eq(6).text();
        var reason=td.eq(7).text();
        var phone=td.eq(8).text();
        var address=td.eq(9).text();
        var sponsorship=td.eq(10).text();
        if(sponsorship==0){
        	var isSponsorship="直接送回";
        }else if(sponsorship==1){
        	var isSponsorship="进行助养";
        }
        var money=td.eq(11).text();
        var applyTime=td.eq(12).text().substring(0,19);
       
        var modal=$("#recycleShowModal");
        modal.find('.modal-body #recycleShowPetID').text(petID) ;
        modal.find('.modal-body #recycleShowPetName').text(name) ;
        modal.find('.modal-body #recycleShowPetAge').text(age) ;
        modal.find('.modal-body #recycleShowPetType').text(type) ;
        modal.find('.modal-body #recycleShowState').text(state) ;
        modal.find('.modal-body #recycleShowRecycleReason').text(reason) ;
        modal.find('.modal-body #recycleShowSponsorship').text(sponsorship) ;
        modal.find('.modal-body #recycleShowMoney').text(money) ;
        modal.find('.modal-body #recycleShowApplyTime').text(applyTime) ;
	}) ;
	
	$("#recycleConfirm").click(function(){  
		var reason=$("#recycleReason").val();
		var pid=$("#recyclePetID").val();
		var address=$("#recycleAddress").val();
		var phone=$("#recyclePhone").val();
		var money=$("#recycleMoney").val();
		var sponsorship=$("#recycleSponsorship").val();
		if(reason.replace(/(^\s+)|(\s+$)/,"")==""){
			alert("原因不能为空");
		}else if(address.replace(/(^\s+)|(\s+$)/,"")==""){
			alert("地址不能为空");
		}else if(phone.replace(/(^\s+)|(\s+$)/,"")==""){
			alert("联系电话不能为空");
		}else if(money.replace(/(^\s+)|(\s+$)/,"")==""){
			alert("助养金不能为空");
		}else if(!(sponsorship=="进行助养"||sponsorship=="直接送回")){
			alert("请选择是否助养");
		}else{
			$.getJSON("recycle.do",{"reason":reason,"pid":pid,"address":address,"phone":phone,"money":money,"sponsorship":sponsorship},function(d){
				if(d=="0"){
					alert("请登录");
				}if(d=="1"){
					alert("无权限，请尝试重新登录");
				}if(d=="2"){
					$("#alert_submit").css("display","inline-block");
					setTimeout(function(){  
						window.location.reload();
					},1000);
				}
			});
		}					
	});
	
	
	$("#recycleCancel").click(function(){  
		var pid=$("#recycleCancelPetID").text();
		$.getJSON("recycleCancel.do",{"pid":pid,},function(d){
			if(d=="0"){
				alert("请登录");
			}if(d=="1"){
				alert("无权限，请尝试重新登录");
			}if(d=="2"){
				$("#alert_submit").css("display","inline-block");
				setTimeout(function(){  
					window.location.reload();
				},1000);
			}
		});
	});
	
	
	
	$("#recycleReason").blur(function(){
		var reason=$("#recycleReason").val();
		if(reason.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#alert_reason").css('display','inline-block');
		}else{
			$("#alert_reason").css('display','none');
		}
	});
	
	$("#recycleAddress").blur(function(){
		var address=$("#recycleAddress").val();
		if(address.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#alert_address").css('display','inline-block');
		}else{
			$("#alert_address").css('display','none');
		}
	});
	
	$("#recyclePhone").blur(function(){
		var phone=$("#recyclePhone").val();
		if(!(/^1[34578]\d{9}$/.test(phone))){
			$("#alert_phone").css('display','inline-block');
		}else{
			$("#alert_phone").css('display','none');
		}
	});
	
	$("#recycleSponsorship").blur(function(){
		var sponsorship=$("#recycleSponsorship").val();
		if(!(sponsorship=="进行助养"||sponsorship=="直接送回")){
			$("#alert_sponsorship").css('display','inline-block');
		}else{
			$("#alert_sponsorship").css('display','none');
		}
	});
	
	$("#recycleMoney").blur(function(){
		var money=$("#recycleMoney").val();
		var money_test=/^\d+(?:\.\d{1,2})?$/;
		if(!(money_test.test(money))){
			$("#alert_money").css('display','inline-block');
		}else{
			$("#alert_money").css('display','none');
		}
	});
	
	
	
	
	$("#recycleSponsorship").click(function(){  
		var modal=$("#recycleModal");
		 modal.find('.modal-body #recycleSponsorship').val("") ;
	});
	
	
});