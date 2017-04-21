$(function(){
	
	$('.btnRecycle').click(function (event) {  
		var trs=$("table#table1 tbody tr");
		var tr=$(this).closest("tr");
        var index=trs.index(tr);
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
	
	
	$("#recycleConfirm").click(function(){  
		var reason=$("#recycleReason").val();
		var pid=$("#recyclePetID").val();
		var address=$("#recycleAddress").val();
		var phone=$("#recyclePhone").val();
		var money=$("#recycleMoney").val();
		var sponsorship=$("#recycleSponsorship").val();
		$.getJSON("recycle.do",{"reason":reason,"pid":pid,"address":address,"phone":phone,"money":money,"sponsorship":sponsorship},function(d){
			
			if(d=="0"){
			   alert("请登录");
		   }
			if(d=="1"){
			   alert("无权限，请尝试重新登录");
		   }
			if(d=="2"){

			   setTimeout(function(){  
				   window.location.reload();
			   },2000);
		   }
		});		
	});
	
	
	$("#recycleSponsorship").click(function(){  
		var modal=$("#recycleModal");
		 modal.find('.modal-body #recycleSponsorship').val("") ;

	});
	
	
});