$(document).ready(function(){
	initSelect('province','city','dealer');
});
function initSelect(provinceSelectId,citySelectId,dealerSelectId){
	loadDealerData(provinceSelectId);
	$("#"+provinceSelectId).on("change",function(){
		appendCity($(this).val(),citySelectId,loadedData);
		$("#"+dealerSelectId).empty();
		$("#"+dealerSelectId).append('<option value="">请选择经销商</option>');
	});
    $("#"+citySelectId).on("change",function(){
    	appendDealer($(this).val(),$("#"+provinceSelectId).val(),dealerSelectId,loadedData);
	});
}

var loadedData={}
function loadDealerData(provinceSelectId){
	$.ajax( {
		url :'toolkit/dealer/bigarea/findAll.htm',
		type : 'post',
		dataType:'json',
		data:{},
		success : function(result) {
			loadedData=result;
			appendProvince(provinceSelectId,result);
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	 	    	alert('请求错误！'+errorThrown);
     }
	});
}

function appendProvince(provinceSelectId,data){
	var provinces=getProvinces(data);
	for(var i=0;i<provinces.length;i++ ){
		$("#"+provinceSelectId).append('<option value="'+provinces[i].id+'">'+provinces[i].title+'</option>');
	}
}

function appendCity(provinceId,citySelectId,data){
	$("#"+citySelectId).empty();
	$("#"+citySelectId).append('<option value="">请选择市</option>');
	var citys=getCitys(data,provinceId);
	for(var i=0;i<citys.length;i++ ){
		$("#"+citySelectId).append('<option value="'+citys[i].id+'">'+citys[i].title+'</option>');
	}
}

function appendDealer(cityId,provinceId,dealerSelectId,data){
	$("#"+dealerSelectId).empty();
	$("#"+dealerSelectId).append('<option value="">请选择经销商</option>');
	var dealers=getDealers(cityId,provinceId,data);
	for(var i=0;i<dealers.length;i++ ){
		$("#"+dealerSelectId).append('<option value="'+dealers[i].dealer_code+'">'+dealers[i].dealer_name+'</option>');
	}
}

function getProvinces(data){
	var provinces=[];
	for(var i=0;i<data.length;i++){
		provinces=provinces.concat(data[i].provinces);
	}
	return provinces;
}

function getCitys(data,provinceId){
	var provinces=getProvinces(data);
	var citys=[];
	for(var i=0;i<provinces.length;i++ ){
	  if(provinces[i].id==provinceId){
		  citys=citys.concat(provinces[i].citys);
	  }
	}
	return citys;
}
function getDealers(cityId,provinceId,data){
	var citys=getCitys(data,provinceId);
	var dealers=[];
	for(var i=0;i<citys.length;i++){
		if(citys[i].id==cityId){
			dealers=dealers.concat(citys[i].dealers);
		}
	}
	return dealers;
}


