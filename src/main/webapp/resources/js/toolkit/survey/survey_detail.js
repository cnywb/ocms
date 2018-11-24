$(document).ready(function(){
	loadQuestion();
});


var questionList=new Array();
function loadQuestion(){
	 var surveyId=$("#surveyId").val();
	 var submitData={"surveyId":surveyId};
	 $.ajax( {
			url :'toolkit/survey/question/findAllBySurveyId.htm',
			type : 'post',
			data:submitData,
			dataType:'json',
			success : function(result) {
				questionList=result;
				setQuestions(result);
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	alert("加载问题发生异常，请刷新重试！");
		       }
		});
}

function setQuestions(result){
	var html='';
	for(var i=0;i<result.length;i++){
		var q=result[i];
	
		html=html+'<div class="form-group"><div class="col-md-9"><h4 class="m-t-0">'+(i+1)+"."+q.name+'</h4></div></div>';
      
		var answerList=q.answerList;
		var inputType="radio";
		if(q.type==1){
			inputType="checkbox";
		}
		html=html+'<div class="form-group"> <div class="col-md-9">';
		for(var j=0;j<answerList.length;j++){
			var a=answerList[j];
		    var memo='';
			if(a.needMemo==true){
				memo='<input type="text" value="(请填写）" id="m_a_'+a.id+'" class="reason" onFocus="if (value ==\'(请填写\'){value =\'\'}"  onBlur="if (value ==\'\'){value=\'(请填写）\'};" />';
			}
			html=html+'<div class="checkbox"><label><input  id="a_'+a.id+'" type="'+inputType+'" value="'+a.id+'" name="a_'+q.id+'"   needMemo="'+a.needMemo+'" />'+a.name+'</label>'+memo+'</div>';
		}
            
      html=html+'</div></div>';
	}
	$("#question_container").html(html);
}

function doSave(){
	var submitData={};
	var surveyCode=$("#surveyCode").val();
 	submitData["surveyCode"]=surveyCode;
	var j=0;
	for(var i=0;i<questionList.length;i++){
		var q=questionList[i];
		var a_name='a_'+q.id;
		if(q.type==1){//多选框处理
			var checkBoxCount=0;
			$("input[name='"+a_name+"']").each(function (){
				if($(this).is(":checked")){
					submitData["answerSheetlist["+j+"].answer.id"]=$(this).val();
					j=j+1;
					if($(this).attr("needMemo")==true){
						   submitData["answerSheetlist["+j+"].answerMemo"]=$("#m_"+$(this).attr("id")).val();
					}
					checkBoxCount=checkBoxCount+1;
				}
			});
			if(checkBoxCount==0){
				 showAlertModal("注意","您有未完成的问题!"); return;
			}
		}else{//单选框处理
		   var rd=$("input[name='"+a_name+"']:checked");
		   var  value=$(rd).val();
		   if(value==undefined){
			   showAlertModal("注意","您有未完成的问题!"); return;
		   }
		   submitData["answerSheetlist["+j+"].answer.id"]=value;
		   if($(rd).attr("needMemo")=="true"){
			   var memo=$("#m_"+$(rd).attr("id")).val();
			   if(memo==""||memo=="(请填写）"){
				   showAlertModal("注意","您有未完成的问题补充说明!"); return;
			   }
			   submitData["answerSheetlist["+j+"].answerMemo"]=memo;
		   }
		   j=j+1;
		}
	}
	
	 showBlock();
	 $.ajax( {
			url :'toolkit/survey/answer/sheet/add.htm',
			type : 'post',
			data:submitData,
			dataType:'json',
			success : function(result) {
				 $.unblockUI();
				 showAlertModal("注意",result.message);
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	 $.unblockUI();
	        	alert("保存问卷发生异常，请刷新重试！");
		    }
		});
	
}

function showBlock(){
	$.blockUI({
        message: '<h1>正在执行操作,请稍后...</h1>',
        css: {
            border: 'none',                   // 无边界
            width:"180px",
            top:"50%",                        // 高居中
            left:(($(this).width()-180)/2)+"px"                        // 左居中
       }
    });
}
function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}
