//获取选取的多选框的值
function splitCheckBoxValue(checkBoxName) {
	var temp=0;
	var ids="";
	var o = document.getElementsByName(checkBoxName);
	for(var i = 0; i < o.length ; i ++){
	        if(o[i].checked == true){
	        	
	        	if(temp!=0){
	        		ids=ids+",";
	        	}
	        	ids=ids+o[i].value;
	        	temp++;
	        	}    
	}
	return ids;
}


 //把获未取选取的多选框的值
function splitUnCheckBoxValue(checkBoxName) {
	var temp=0;
	var ids="";
	var o = document.getElementsByName(checkBoxName);
	for(var i = 0; i < o.length ; i ++){
	        if(o[i].checked ==false){
	        	
	        	if(temp!=0){
	        		ids=ids+",";
	        	}
	        	ids=ids+o[i].value;
	        	temp++;
	        	}    
	}
	return ids;
}



 //把获未取选取的多选框的值
function unCheckCheckBox(checkBoxName) {
	var o = document.getElementsByName(checkBoxName);
	for(var i = 0; i < o.length ; i ++)
	{
	        o[i].checked=false;
	}
}


 function CheckInputFloat(oInput)//只能输入数字
 {
     if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
     {
         oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
     }
 } 
 
 function CheckInputInt(oInput)//限制只能输入整数
{
    if  ('' != oInput.value.replace(/\d/g,''))
    {
        oInput.value = oInput.value.replace(/\D/g,'');
    }
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {   
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {   
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);   
    } else {   
        return this.replace(reallyDo, replaceWith);   
    }   
}; 



function isPositiveInteger(str){
	var reg1 =  /^\d+$/;
	return str.match(reg1);
}

function getCookie(name){
  	 var strCookie=document.cookie;
  	 var arrCookie=strCookie.split("; ");
  	 for(var i=0;i<arrCookie.length;i++){
  	 var arr=arrCookie[i].split("=");
  	     if(arr[0]==name){
  	  	 	return arr[1];
  	 	}
  	 }
  	 return "";
} 

function getHostPath() {
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos);
    return(localhostPath);
}