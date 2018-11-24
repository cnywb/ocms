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
		    html=html+'<div class="checkbox"><label>'+a.name+'（答题人数：'+a.sheetCount+'）</label></div>';
		}
        html=html+'</div></div>';
	}
	$("#question_container").html(html);
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
