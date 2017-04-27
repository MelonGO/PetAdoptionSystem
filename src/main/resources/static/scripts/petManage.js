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

		if(false){
			
		}else{
			$.getJSON("updatePet.do",{"petID":petID,"name":name,"type":type,"age":age,"sex":sex,"price":price,"profile":profile},function(d){
				if(d=="0"){
					alert("请以管理员身份登录");
				}if(d=="1"){
					alert("修改失败，请重试");
				}if(d=="2"){
					$("#updatePetModal").modal('hide');
				}
			});
		}					
	});
});