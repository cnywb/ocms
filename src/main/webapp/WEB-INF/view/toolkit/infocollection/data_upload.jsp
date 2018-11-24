<%@ page language="java" import="java.util.*,com.ternnetwork.cms.enums.CmsPageStatus,com.ternnetwork.cms.enums.CmsPageType" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    <c:import url="/WEB-INF/view/include/color_admin_header.jsp"/>
    <link rel="stylesheet" href="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.css' />">
    <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
	<link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
	
   <script type="text/javascript">
  var userPath='<%=basePath%>';
 
  </script>
  
  <style type="text/css">
  
  .demo {
				
				
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
				overflow: scroll;
				padding: 5px;
			}
  </style>
 </head>
   <body >
 
<div class="container-fluid">
	<div class="row-fluid">
	
	 <div class="col-md-12">
   	    <br>
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel panel-inverse">
              
              <div class="panel-heading" role="tab" id="headingOne">
              <h4 class="panel-title">
                         钣喷数据上传
             </h4>
              </div>
                 
             
                 <div class="panel-body">
                 
                 <div id="fileTree" class="fileTree"></div>
                 
                  
                 </div>
                 <div class="panel-footer">
                   <button type="button" class="btn btn-primary btn-sm" id="btn_upload" >上传文件</button>请勿重复上传,只支持csv格式
                
                </div>
                </div>
              </div>
            </div>
  
     </div> 
	</div>
	
	
</div>




 
   <div class="modal fade " role="dialog" id="alertModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >警告</h4>
      </div>
      <div class="modal-body">
      <p>
	  </p>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



   
 <div id="btnc04">
<input type="button" value="上传文件" id="btn_upload_file" >
</div>
 
 
   <input type="hidden" id="currentDir" value="/resources/campaign/bpdata" >
   
     <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/jqueryFileTree/jquery.easing.js' />"></script>
    <script src="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.js' />"></script>
 	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
	<script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
	<script src="<c:url value='/resources/js/toolkit/infocollection/data_upload.js' />"></script>
  
   </body>
</html>
