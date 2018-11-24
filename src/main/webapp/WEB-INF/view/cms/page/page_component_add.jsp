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
    
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
  <style type="text/css">.hidden-code {display:none;}</style>
 </head>
   <body >
 

	
	
	
	<div class="container-fluid" id="content_container">
	<div class="row-fluid">
          <br>
	      <div class="col-md-12">
	        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="组件名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
	  
	        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="代码" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">备注</span>
             <input type="text" name="memo" class="form-control" placeholder="备注" aria-describedby="sizing-addon2" >
             </div>
            </div>
            <br>
           <div class="row">
             <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-success active" onclick="changeEditor('html');"><input type="radio" name="options" id="option1" autocomplete="off" checked>HTML</label>
                <label class="btn btn-success" onclick="changeEditor('javaScript');"><input type="radio" name="options" id="option2"  autocomplete="off">JS</label>
                <label class="btn btn-success" onclick="changeEditor('css');"><input type="radio" name="options" id="option3" autocomplete="off">CSS</label>
             </div>
           </div>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1"></span>
            <textarea id="sourceCode" class="form-control codepress html" rows="18" >
           <div class="box box-element ui-draggable"><a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a><span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span><span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
           <div class="preview">组件标题</div>
			<div class="view">
			     <div class="component_container"> 
				<!-- 请在此处添加组件源码,不要修改外层代码. -->
				</div>
			</div>
		  </div>
            </textarea>
             </div>
            </div>
           <br>
           <div class="row">
             <div class="col-md-5 input-group">
             <span class="input-group-addon" id="sizing-addon1">操作</span>
             <button type="button" class="btn btn-success" id="btn_save">保存</button>
            
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
 <textarea id="html" class="hidden-code" rows="15" ></textarea>
 <textarea id="javaScript" class="hidden-code" rows="15" ></textarea>
 <textarea id="css" class="hidden-code" rows="15" ></textarea>
 
    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
     <script src="<c:url value='/plugin/codepress/codepress.js' />"></script>
      <script src="<c:url value='/resources/js/cms/page/page_component_add.js' />"></script>
 
   </body>
</html>
