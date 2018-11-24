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
 </head>
   <body >
 
<div class="container-fluid">
	<div class="row-fluid">
	
	<br>
   <div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">
         ${page.templateFileName}.html
        </h3>
      </div>
      <div class="panel-body">
     
      <textarea id="pageHtml" class="form-control codepress html" rows="20" >
      ${page.html}
      </textarea>
      <input type="hidden"  value="${page.id}" id="pageId">
      </div>
      <div class="panel-footer">
        <button type="button" class="btn btn-primary" id="btn_save" >保存</button>
        <button type="button" class="btn btn-primary" id="btn_save_publish" >保存并发布</button>
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
     <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
     <script src="<c:url value='/plugin/codepress/codepress.js' />"></script>
     <script src="<c:url value='/resources/js/cms/page/page_html_edit.js' />"></script>

   </body>
</html>
