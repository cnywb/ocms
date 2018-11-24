<%@ page language="java" import="java.util.*,com.ternnetwork.baseframework.enums.RoleType" pageEncoding="UTF-8"%>
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
 
 <br>
    <div class="row-fluid">
     <div class="col-md-12">
    <div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">查询条件</h3>
      </div>
      <div class="panel-body" id="query_con">
        <div class="form-inline" role="form">
        
         <div class="form-group">
           <label>所在分组</label>
           <select  class="form-control" name="groupId">
           <option value="">不限</option>
           <c:forEach var="item" items="${groupList}" varStatus="status">
             <option value="${item.id}">${item.name}</option>
           </c:forEach>
            </select>
        </div>
       
            <div class="form-group">
           <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
            </div>
           </div>
      </div>
      </div>
   </div>
 </div>

	<div class="row-fluid">
		<div class="span12">

   <br>
    <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal">新建</button>
            </div>
        </div>
     </div>
     <br>
    <table id="data_table"  data-url="<c:url value='/toolkit/survey/question/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead> 
   
    </thead> 
   </table> 
     
     </div>
	</div>
	
	
</div>


<div class="modal fade" role="dialog" id="addModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >新建</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="请填写大写英文字母" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
            <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">所在分组</span>
             <select  class="form-control" name="group.id">
             <c:forEach var="item" items="${groupList}" varStatus="status">
             <option value="${item.id}">${item.name}</option>
           </c:forEach>
            </select>
             </div>
           </div>
         
         <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">排序</span>
             <input type="text" name="sequence" class="form-control" placeholder="排序" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类型</span>
          <select  class="form-control" name="type">
           <option value="REDIO">单选</option>
            <option value="CHECK_BOX">多选</option>
             <option value="TEXT">文本字段</option>
            </select>
          </div>
          </div>
          
          
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="save();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" role="dialog" id="editModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >编辑</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
              <input type="hidden" name="id" id="questionId">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="请填写大写英文字母" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
                    <br>
          <div class="row">
            <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">所在分组</span>
             <select  class="form-control" name="group.id">
             <c:forEach var="item" items="${groupList}" varStatus="status">
             <option value="${item.id}">${item.name}</option>
           </c:forEach>
            </select>
             </div>
           </div>
         
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">排序</span>
             <input type="text" name="sequence" class="form-control" placeholder="排序" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类型</span>
          <select  class="form-control" name="type">
           <option value="REDIO">单选</option>
            <option value="CHECK_BOX">多选</option>
            <option value="TEXT">文本字段</option>
            </select>
          </div>
          </div>
          
          
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="answerManage();">答案管理</button>
        <button type="button" class="btn btn-primary" onclick="update();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
   
   
 
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
   
<input  type="hidden" id="surveyId" value="${surveyId}"/> 
<c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/survey/survey_question_manage.js' />"></script>
   
   </body>
</html>
