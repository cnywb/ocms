$(document).ready(function(){
	initTable();
	initEditor();
	$("#btn_upload").click(function(){
		$("#btnc04 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
	$("#btnc04").hide();
	$(document).ajaxStart($.blockUI({ message:'正在执行操作，请稍等...' })).ajaxStop($.unblockUI());
	
});



function initTable(){
	  $('#data_table').bootstrapTable({
      cache: false,
      height: 400,
      striped: true,
      pagination: true,
      pageSize: 10,
      pageList: [10,20,50,100],
      //search: true,
     // showColumns: true,
      //showRefresh: true,
      minimumCountColumns: 1,
      clickToSelect: true,
      queryParams: function (params) {
    	  params=getQueryParams("query_con",params);
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'bigArea',
          title: '大区'
      },{
          field: 'province',
          title: '省'
      },{
          field: 'cityOrTerritory',
          title: '市'
      },{
          field: 'dealer_code',
          title: '经销商代码'
      },{
          field: 'dealer_name',
          title: '经销商名称'
      },{
          field: 'address',
          title: '福特品牌店地址'
      },{
          field: 'tel',
          title: '销售热线'
      },{
          field: 'addressUpdatedName',
          title: '是否需要重新定位'
      },{
          field: 'createTime',
          title: '创建时间'
      },{
          field: 'operate',
          title: '操作',
          align: 'center',
          events: operateEvents,
          formatter: operateFormatter
      }]
  });

}



function operateFormatter(value, row, index) {
	 return [
	         '<a class="edit" href="javascript:void(0)" title="edit">',
	         '<i class="glyphicon glyphicon-edit"></i>',
	         '</a>  ',
	         '<a class="remove" href="javascript:void(0)" title="Remove">',
	         '<i class="glyphicon glyphicon-remove"></i>',
	         '</a>'
	     ].join('');
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	 setEditParams('editModal',row);
	 $('#editModal').modal('show');
	 searchAddress();
},
'click .remove': function (e, value, row, index) {
	doDelete(row.id);
}
};

function setEditParams(containerId,obj){
	    clearEditParams(containerId);
		$("#"+containerId+" input[type='text']").each(function(){
			var attrName=$(this).attr("name");
			if(attrName=="fordBrandShopAddress"){
				$(this).val(obj["address"]);
			}else if(attrName=="longitude"){
				$(this).val(obj["pointx"]);
			}else if(attrName=="latitude"){
				$(this).val(obj["pointy"]);
			}else{
				$(this).val(obj[attrName]);
			}
			
		});
		$("#"+containerId+" select").each(function(){
            var attrName=$(this).attr("name");
            $(this).val(obj[attrName]+"");
         });
	    $("#"+containerId+" input[type='hidden']").each(function(){
            var attrName=$(this).attr("name");
			$(this).val(obj[attrName]);
		});
	   
}

function searchAddress(){
	var city=$("#edit_cityOrTerritory").val();
	var address=$("#edit_fordBrandShopAddress").val();
	createMap(city,address);
}
function createMap(city,address){
	var map = new BMap.Map("bdmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(address, function(point){
		if (point) {
			map.centerAndZoom(point, 16);
			map.addOverlay(new BMap.Marker(point));
			$("#edit_latitude").val(point.lat);
			$("#edit_longitude").val(point.lng);
		}else{
			alert("您选择地址没有解析到结果!");
		}
	}, city);
}

function clearEditParams(containerId){
		$("#"+containerId+" input[type='text']").each(function(){
			$(this).val("");
		});
		$("#"+containerId+" select").each(function(){
			$(this).val("");
		});
	    $("#"+containerId+" input[type='hidden']").each(function(){
	    	$(this).val("");
		});
	    $("#"+containerId+" textarea").each(function(){
	    	$(this).val("");
		});
}

function getParams(containerId){
	var submitData={};
	$("#"+containerId+" input[type='text']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	$("#"+containerId+" select").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	$("#"+containerId+" input[type='hidden']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	
	return submitData;
}


function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}


function save(){
	var submitData=getParams('addModal');
	$('#addModal').modal('hide');
	$.ajax( {
		url :'toolkit/infocollection/channel/add.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			   showAlertModal('注意',result.message);
		       if(result.status==3){
		    	   refreshTable();
		       }
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	 	    	showAlertModal('注意','请求错误！'+textStatus);
     }
	});
}


function update(){
	var submitData=getParams('editModal');
	if(isNaN(submitData["latitude"])){
		 showAlertModal('注意',"纬度必须为数字!");return;
	}
	if(isNaN(submitData["longitude"])){
		 showAlertModal('注意',"经度必须为数字!");return;
	}
    $('#editModal').modal('hide');
    $.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
		url :'toolkit/dealer/idoUpdateLocationAndName.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
		    	$.unblockUI();
			   showAlertModal('注意',result.message);
		       if(result.status==1){
		    	   refreshTable();
		       }
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	 	    	showAlertModal('注意','请求错误！'+textStatus);
     }
	});
}

function doDelete(id){
	if(!confirm("确认删除该条数据吗？")){
		return;
	}
	var submitData={};
	submitData["id"]=id;
	$.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
			url :'toolkit/dealer/delete.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				   $.unblockUI();
				   showAlertModal('注意',result.message);
			       if(result.status==1){
			    	   refreshTable();
			       }
		     },error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	showAlertModal('注意','请求错误！'+textStatus);
	     }
		});
}


function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function getQueryParams(containerId,submitData){
	$("#"+containerId+" input[type='text']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	$("#"+containerId+" select").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	$("#"+containerId+" input[type='hidden']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	return submitData;
}

function initEditor(){
	KindEditor.ready(function(K) {
	initUploadBtn(K,'file');
	});
}

function initUploadBtn(K,type){
	var uploadbutton = K.uploadbutton({
		button : K('#btn_upload_'+type)[0],
		fieldName : 'imgFile',
		extraParams:{},
		url : 'toolkit/dealer/upload.htm?dir='+type,
		afterUpload : function(data) {
			$.unblockUI();
			if (data.error === 0) {
			   showAlertModal('注意','上传成功！');
			   refreshTable();;
			}else{
				showAlertModal('注意',data.message);
			}
		},
		afterError : function(str) {
			
		}
	});
	uploadbutton.fileBox.change(function(e) {
		$.blockUI({ message:'正在执行操作，请稍等...' });
		uploadbutton.submit();
	});
}

