function GetUrlParms(){ 
    var args=new Object();   
    var query=location.search.substring(1);//获取查询串   
    var pairs=query.split("&");//在逗号处断开   

    for(var   i=0;i<pairs.length;i++){
    	var pos=pairs[i].indexOf('=');//查找name=value   
    	if(pos==-1)   continue;//如果没有找到就跳过   
		var argname=pairs[i].substring(0,pos);//提取name   
		var value=pairs[i].substring(pos+1);//提取value   
		args[argname]=unescape(value);//存为属性   
    }
    return args;
}

$(function(){
	$('.btnAdoptionShow').click(function (event) {  
		var trs=$("table#table_adopt tbody tr");
		var tr=$(this).closest("tr");
        var td=tr.eq(0).find("td")
        var type=td.eq(0).text();
        var name=td.eq(1).text();
        var userName=td.eq(2).text();
        var createTime=td.eq(3).text();
        var state=td.eq(4).text();
        var age=td.eq(5).text();
        var sex=td.eq(6).text();
        var price=td.eq(7).text();
        var userSex=td.eq(8).text();
        var realName=td.eq(9).text();
        var address=td.eq(10).text();
        var phone=td.eq(11).text();
       
        var modal=$("#adoptionShowModal");
        modal.find('.modal-body #adoptionShowPetType').text(type) ;
        modal.find('.modal-body #adoptionShowPetName').text(name) ;
        modal.find('.modal-body #adoptionShowPetAge').text(age) ;
        modal.find('.modal-body #adoptionShowPetSex').text(sex) ;
        modal.find('.modal-body #adoptionShowPetPrice').text(price) ;
        modal.find('.modal-body #adoptionShowUserName').text(userName) ;
        modal.find('.modal-body #adoptionShowUserRealName').text(realName) ;
        modal.find('.modal-body #adoptionShowUserSex').text(userSex) ;
        modal.find('.modal-body #adoptionShowAddress').text(address) ;
        modal.find('.modal-body #adoptionShowPhone').text(phone) ;
        modal.find('.modal-body #adoptionShowCreateTime').text(createTime) ;
        modal.find('.modal-body #adoptionShowState').text(state) ;
	}) ;
	
	$('.recycleManageShow').click(function (event) {  
		var trs=$("table#table_recycle_manage tbody tr");
		var tr=$(this).closest("tr");
        var td=tr.eq(0).find("td")
        var type=td.eq(0).text();
        var name=td.eq(1).text();
        var userName=td.eq(2).text();
        var applyTime=td.eq(3).text();
        var sponsorship=td.eq(4).text();
        var state=td.eq(5).text();
        var age=td.eq(6).text();
        var sex=td.eq(7).text();
        var price=td.eq(8).text();
        var userSex=td.eq(9).text();
        var address=td.eq(10).text();
        var phone=td.eq(11).text();
       
        var modal=$("#recycleManageShowModal");
        modal.find('.modal-body #recycleShowPetType').text(type) ;
        modal.find('.modal-body #recycleShowPetName').text(name) ;
        modal.find('.modal-body #recycleShowPetAge').text(age) ;
        modal.find('.modal-body #recycleShowPetSex').text(sex) ;
        modal.find('.modal-body #recycleShowPetPrice').text(price) ;
        modal.find('.modal-body #recycleShowUserName').text(userName) ;
        modal.find('.modal-body #recycleShowUserSex').text(userSex) ;
        modal.find('.modal-body #recycleShowAddress').text(address) ;
        modal.find('.modal-body #recycleShowPhone').text(phone) ;
        modal.find('.modal-body #recycleShowSponsorship').text(sponsorship) ;
        modal.find('.modal-body #recycleShowApplyTime').text(applyTime) ;
        modal.find('.modal-body #recycleShowState').text(state) ;
	}) ;
	
});