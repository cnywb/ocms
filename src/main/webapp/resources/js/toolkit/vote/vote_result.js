$(document).ready(function(){
	loadItems();
});
function loadItems(){
	var submitData={};
	submitData["voteId"]=$("#voteId").val();
	$.ajax( {
		url :'toolkit/vote/item/findAllByVoteId.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			setItems(result);
			handleDonutChart(result);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	 showAlertModal('注意','系统异常！'+errorThrown);
  }
	});
}

function setItems(result){
	var html='';
	var multipleVote=$("#multipleVote").val();
	for(var i=0;i<result.length;i++){
		var t=result[i];
	   if(multipleVote=="true"){
		 title='<input type="checkbox" name="voteItem" value="'+t.id+'" id="chk_'+t.id+'" /><label for="chk_'+t.id+'">'+t.name+'</label><br>';
	   }else{
		 title='<input type="radio" name="voteItem" value="'+t.id+'" id="chk_'+t.id+'" /><label for="chk_'+t.id+'">'+t.name+'</label> <br> ';
	   }
	   html=html+title;
	}
	 $("#itemContianer").html(html);
}


function addVoteLog(){
	
	var submitData={};
	var voteId=$("#voteId").val();
	submitData["voteId"]=voteId;
	 var i=0;
	 $("input[name='voteItem']:checked").each(function(){
		 submitData["items["+i+"].voteItem.id"]=$(this).val();
		 i=i+1;
	});
	 if(i==0){
		 showAlertModal("注意","请选择投票项！"); return;
	 }
	 
	 $.ajax( {
			url :'toolkit/vote/log/add.htm',
			type : 'post',
			data: submitData,
			dataType:'json',
			success : function(result) {
				showAlertModal("注意",result.message);
				if(result.status==5){
					loadItems();
				}
			
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	       	    alert('投票出错！');
	       }
		});
}

var blue = "#348fe2", blueLight = "#5da5e8", blueDark = "#1993E4", aqua = "#49b6d6", aquaLight = "#6dc5de", aquaDark = "#3a92ab", green = "#00acac", greenLight = "#33bdbd", greenDark = "#008a8a", orange = "#f59c1a", orangeLight = "#f7b048", orangeDark = "#c47d15", dark = "#2d353c", grey = "#b6c2c9", purple = "#727cb6", purpleLight = "#8e96c5", purpleDark = "#5b6392", red = "#ff5b57";

var handleDonutChart=function(dataList) {
	"use strict";
	if ($("#donut-chart").length !== 0) {
		var e = [];
	
		var n = [ dark, green, purple ];
		var r = [ "Unique Visitor", "Bounce Rate", "Total Page Views","Avg Time On Site", "% New Visits" ];
		var i = [ 60, 14, 12, 31, 23 ];
		for (var s = 0; s < dataList.length; s++) {
			var t= {
				label : dataList[s].name,
				data : dataList[s].voteCount,
				color : n[s]
			}
			e.push(t);
			
		}
		
		/*for (var s = 0; s < r.length; s++) {
			e[s] = {
				label : r[s],
				data : i[s],
				color : n[s]
			}
		}*/
		$.plot($("#donut-chart"), e, {
			series : {
				pie : {
					innerRadius : .5,
					show : true,
					combine : {
						color : "#999",
						threshold : .1
					}
				}
			},
			grid : {
				borderWidth : 0,
				hoverable : true,
				clickable : true
			},
			legend : {
				show : false
			}
		})
	}
};



function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}