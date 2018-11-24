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
   <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-table/css/bootstrap-table.css' />">
   <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 </head>
   <body >
 
<div class="container-fluid">
	<div class="row-fluid">
	
	 <div class="col-md-12">
       <br>
       <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success" id="btnAdd">新建</button>
            </div>
        </div>
        <div class="form-group">
          <input type="text" id="queryKeyword" class="form-control" placeholder="输入名称">
        </div>
         <div class="form-group">
         <span class="input-group-btn">
        <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
      </span>
        </div>
    </div>
     <br>
    <table id="data_table" data-url="<c:url value='/cms/page/component/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead> 
   
    </thead> 
   </table> 
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
   <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
   <script src="<c:url value='/resources/js/cms/page/page_component_manage.js' />"></script>
 
   
   </body>
</html>
