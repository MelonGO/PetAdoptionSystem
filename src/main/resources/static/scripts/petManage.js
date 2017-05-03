$(function(){
	$('.btnUpdatePet').click(function (event) {  
		var tr=$(this).closest("tr");
		
        var td=tr.eq(0).find("td");
        var petID=td.eq(0).text();
        var name=td.eq(1).text();
        var type=td.eq(2).text();
        var age=td.eq(3).text();
        var sex=td.eq(4).text();
        var price=td.eq(5).text();
        var profile=td.eq(6).text();      
        var modal=$("#updatePetModal");
        modal.find('.modal-body #updatePetID').val(petID) ;
        modal.find('.modal-body #updatePetName').val(name) ;
        modal.find('.modal-body #updatePetAge').val(age) ;
        modal.find('.modal-body #updatePetType').val(type) ;
        modal.find('.modal-body #updatePetSex').val(sex) ;
        modal.find('.modal-body #updatePetPrice').val(price) ;
        modal.find('.modal-body #updatePetProfile').val(profile) ;
	}) ;
	
	
	
	$("#updateConfirm").click(function(){
		var petID=$("#updatePetID").val();
        var name=$("#updatePetName").val();
        var type=$("#updatePetType").val();
        var age=$("#updatePetAge").val();
        var sex=$("#updatePetSex").val();
        var price=$("#updatePetPrice").val();
        var profile=$("#updatePetProfile").val();

		if(name.replace(/(^\s+)|(\s+$)/,"")==""||type.replace(/(^\s+)|(\s+$)/,"")==""||!/^\d+$/.test(age)||profile.replace(/(^\s+)|(\s+$)/,"")==""
			||sex.replace(/(^\s+)|(\s+$)/,"")==""||!(/^\d+(?:\.\d{1,2})?$/.test(money))){
			
		}else{
			$.getJSON("updatePet.do",{"petID":petID,"name":name,"type":type,"age":age,"sex":sex,"price":price,"profile":profile},function(d){
				if(d=="0"){
					$("#alertInfo").text("请以管理员身份登录");
					$("#alertInfo").css("display","block");
					setTimeout(function(){  
						$("#alertInfo").css("display","none");
					},1000)
				}if(d=="1"){
					$("#alertDanger").text("宠物信息修改失败，请重新尝试");
					$("#alertDanger").css("display","block");
					setTimeout(function(){  
						$("#alertDanger").css("display","none");
					},1000)
				}if(d=="2"){
					$("#updatePetModal").modal('hide');
					$("#alertSuccess").text("宠物信息修改成功");
					$("#alertSuccess").css("display","block");
					setTimeout(function(){  
						$("#alertSuccess").css("display","none");
						location.reload();
					},1000);
				}
			});
		}					
	});
	$("#updatePetName").blur(function(){
		var name=$("#updatePetName").val();
		if(name.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#pet_name").css('display','inline-block');
		}else{
			$("#pet_name").css('display','none');
		}
	});
	
	$("#updatePetType").blur(function(){
		var type=$("#updatePetType").val();
		if(type.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#pet_type").css('display','inline-block');
		}else{
			$("#pet_type").css('display','none');
		}
	});
	
	$("#updatePetAge").blur(function(){
		var age=$("#updatePetAge").val();
		if(!/^\d+$/.test(age) ){
			$("#pet_age").css('display','inline-block');
		}else{
			$("#pet_age").css('display','none');
		}
	});
	
	$("#updatePetProfile").blur(function(){
		var profile=$("#updatePetProfile").val();
		if(profile.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#pet_profile").css('display','inline-block');
		}else{
			$("#pet_profile").css('display','none');
		}
	});
	
	$("#updatePetSex").blur(function(){
		var sex=$("#updatePetSex").val();
		if(sex.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#pet_sex").css('display','inline-block');
		}else{
			$("#pet_sex").css('display','none');
		}
	});
	
	$("#updatePetMoney").blur(function(){
		var money=$("#updatePetMoney").val();
		var money_test=/^\d+(?:\.\d{1,2})?$/;
		if(!(money_test.test(money))){
			$("#pet_price").css('display','inline-block');
		}else{
			$("#pet_price").css('display','none');
		}
	});
	
	$("#selectSex").click(function(){  
		var modal=$("#updatePetModal");
		modal.find('.modal-body #selectSex').val("") ;
		var modal2=$("#addPetModal");
		modal2.find('.modal-body #selectSex').val("") ;
	});
	
	
	
	$("#addConfirm").click(function(){
		var petID=$("#addPetID").val();
        var name=$("#addPetName").val();
        var type=$("#addPetType").val();
        var age=$("#addPetAge").val();
        var sex=$("#addPetSex").val();
        var price=$("#addPetPrice").val();
        var profile=$("#addPetProfile").val();        
        var file = $("#picture").val();
		if(name.replace(/(^\s+)|(\s+$)/,"")==""||type.replace(/(^\s+)|(\s+$)/,"")==""||!/^\d+$/.test(age)||profile.replace(/(^\s+)|(\s+$)/,"")==""
			||sex.replace(/(^\s+)|(\s+$)/,"")==""||!(/^\d+(?:\.\d{1,2})?$/.test(price))){
			return false;
		}
		$("#addConfirm").attr("disabled", true); 
		$("#add-pet-form").submit();		
	});

	/*
	$("#addConfirm").click(function(){
		var petID=$("#addPetID").val();
        var name=$("#addPetName").val();
        var type=$("#addPetType").val();
        var age=$("#addPetAge").val();
        var sex=$("#addPetSex").val();
        var price=$("#addPetPrice").val();
        var profile=$("#addPetProfile").val();        
        var file = $("#picture").val();

        

		if(name.replace(/(^\s+)|(\s+$)/,"")==""||type.replace(/(^\s+)|(\s+$)/,"")==""||!/^\d+$/.test(age)||profile.replace(/(^\s+)|(\s+$)/,"")==""
			||sex.replace(/(^\s+)|(\s+$)/,"")==""||!(/^\d+(?:\.\d{1,2})?$/.test(price))){
			
		}else{
			$.getJSON("addPet.do",{"name":name,"type":type,"age":age,"sex":sex,"price":price,"profile":profile,"file":file},function(d){
				if(d=="0"){
					$("#alertInfo").text("请以管理员身份登录");
					$("#alertInfo").css("display","block");
					setTimeout(function(){  
						$("#alertInfo").css("display","none");
					},1000)
				}if(d=="1"){
					$("#alertDanger").text("宠物添加失败，请重新尝试");
					$("#alertDanger").css("display","block");
					setTimeout(function(){  
						$("#alertDanger").css("display","none");
					},1000)
				}if(d=="2"){
					$("#addPetModal").modal('hide');
					$("#alertSuccess").text("宠物添加成功");
					$("#alertSuccess").css("display","block");
					setTimeout(function(){  
						$("#alertSuccess").css("display","none");
						location.reload();
					},1000);
				}
			});
			
		}					
	});
	*/
	$("#addPetName").blur(function(){
		var name=$("#addPetName").val();
		if(name.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#add_pet_name").css('display','inline-block');
		}else{
			$("#add_pet_name").css('display','none');
		}
	});
	
	$("#addPetType").blur(function(){
		var type=$("#addPetType").val();
		if(type.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#padd_et_type").css('display','inline-block');
		}else{
			$("#add_pet_type").css('display','none');
		}
	});
	
	$("#addPetAge").blur(function(){
		var age=$("#addPetAge").val();
		if(!/^\d+$/.test(age) ){
			$("#add_pet_age").css('display','inline-block');
		}else{
			$("#add_pet_age").css('display','none');
		}
	});
	
	$("#addPetProfile").blur(function(){
		var profile=$("#addPetProfile").val();
		if(profile.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#add_pet_profile").css('display','inline-block');
		}else{
			$("#add_pet_profile").css('display','none');
		}
	});
	
	$("#addPetSex").blur(function(){
		var sex=$("#addPetSex").val();
		if(sex.replace(/(^\s+)|(\s+$)/,"")==""){
			$("#add_pet_sex").css('display','inline-block');
		}else{
			$("#add_pet_sex").css('display','none');
		}
	});
	
	$("#addPetMoney").blur(function(){
		var money=$("#addPetMoney").val();
		var money_test=/^\d+(?:\.\d{1,2})?$/;
		if(!(money_test.test(money))){
			$("#add_pet_price").css('display','inline-block');
		}else{
			$("#add_pet_price").css('display','none');
		}
	});
	
	
	$("#petDelBtn").click(function(){
		$.getJSON("delPet.do",{"id":petID},function(d){
			if(d=="0"){
				$("#petDelModal").modal('hide');
				$("#alertInfo").text("请先登录你的账号");
				$("#alertInfo").css("display","block");
				setTimeout(function(){  
					$("#alertSuccess").css("Info","none");
				},1000);
			}if(d=="1"){
				$("#petDelModal").modal('hide');
				$("#alertDanger").text("删除失败")
				$("#alertDanger").css("display","block");
				setTimeout(function(){  
					$("#alertDanger").css("display","none");					
				},1000)
			}if(d=="2"){
				$("#petDelModal").modal('hide');
				$("#alertSuccess").text("删除成功")
				$("#alertSuccess").css("display","block");
				setTimeout(function(){  
					$("#alertSuccess").css("display","none");
					location.reload();
				},1000)
			}
		});
		
	});
	
	
	
	$(".btnAddPet").click(function () {  
		initFileInput("picture")
	}) ;
	
	

	
	function initFileInput(ctrlName, uploadUrl) {    
		var control = $('#' + ctrlName);   
		control.fileinput({  
			language: 'zh', //设置语言  
			showUpload: false, //是否显示上传按钮  
			showRemove:true,  
			dropZoneEnabled: false,  
			showCaption: true,//是否显示标题  
			allowedPreviewTypes: ['image'],  
			allowedFileTypes: ['image'],  
			allowedFileExtensions:  ['jpg', 'png'],  
			maxFileSize : 2000,  
			maxFileCount: 1,  
			uploadAsync: false, 
		});
	}	

});