<%@ page language="java" import="java.util.*,com.ternnetwork.cms.enums.CmsPageStatus" pageEncoding="UTF-8"%>
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
  <style type="text/css">.hidden-code {display:none;}</style>
   <style type="text/css">
  
  .demo {       border-top: solid 1px #BBB;
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
 

	
	
	
	<div class="container-fluid" id="content_container">
	<div class="row-fluid">
	<input  type="hidden" name="id" value="${t.id}" />
          <br>
	      <div class="col-md-12">
	        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" value="${t.name}" class="form-control" placeholder="组件名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
	  
	        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" value="${t.code}" class="form-control" placeholder="代码" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">备注</span>
             <input type="text" name="memo" value="${t.memo}" class="form-control" placeholder="备注" aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
           <div class="row">
           
           <ul class="nav nav-tabs">
					<li class="active">
						 <a href="#panel-568966" data-toggle="tab">代码</a>
					</li>
					<li>
						 <a href="#panel-384652" data-toggle="tab">文件</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-568966">
						  <div class="btn-group" data-toggle="buttons">
                          <label class="btn btn-success active" onclick="changeEditor('html');"><input type="radio" name="options" id="option1" autocomplete="off" checked>HTML</label>
                          <label class="btn btn-success" onclick="changeEditor('javaScript');"><input type="radio" name="options" id="option2"  autocomplete="off">JS</label>
                          <label class="btn btn-success" onclick="changeEditor('css');"><input type="radio" name="options" id="option3" autocomplete="off">CSS</label>
                          </div>
                           <textarea id="sourceCode" class="form-control codepress html" rows="18" >
                            ${t.sourceCode}
                           </textarea>
                           
                            <div class="col-md-5 input-group">
             <span class="input-group-addon" id="sizing-addon1">操作</span>
             <button type="button" class="btn btn-success" id="btn_save">保存</button>
            </div>
					</div>
					<div class="tab-pane" id="panel-384652">
					  <div class="alert alert-danger" role="alert">
                      <strong>注意!</strong>请勿删除自动生成的css和js文件。当前组件的文件根目录为: /resources/component/${t.id}/
                      </div>
                      <div id="fileTree" class="demo"></div>
                      <button type="button" class="btn btn-primary" id="btn_upload" >上传文件</button>
                      <button type="button" class="btn btn-primary" id="btn_delete" >删除</button>
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

<div class="modal fade " role="dialog" id="deleteModal" aria-labelledby="gridSystemModalLabel">
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
      <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
       <button type="button" class="btn btn-primary" onclick="doDelete();">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

 <textarea id="html" class="hidden-code" rows="15" >${t.sourceCode}</textarea>
 <textarea id="javaScript" class="hidden-code" rows="15" >${t.javaScript}</textarea>
 <textarea id="css" class="hidden-code" rows="15" >${t.css}</textarea>
  <input type="hidden" id="deleteFile" >
 <input type="hidden" id="currentDir" name="currentDir" value="/component/${t.id}/" >
 <div id="btnc04">
<input type="button" value="上传文件" id="btn_upload_file" >
</div>

   <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/codepress/codepress.js' />"></script>
    <script src="<c:url value='/resources/js/cms/page/page_component_edit.js' />"></script>
    <script src="<c:url value='/plugin/jqueryFileTree/jquery.easing.js' />"></script>
    <script src="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.js' />"></script>
 	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
 
   </body>
</html>
