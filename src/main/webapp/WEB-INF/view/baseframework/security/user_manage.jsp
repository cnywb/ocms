<%@ page language="java" import="java.util.*,com.ternnetwork.baseframework.enums.Gender" pageEncoding="UTF-8"%>
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
		<div class="span12">


      <br>
    <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal">新建</button>
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
      <table id="data_table" data-url="<c:url value='/baseframework/security/user/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
   
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
             <span class="input-group-addon" id="sizing-addon1">用户名</span>
             <input type="text" name="name" class="form-control" placeholder="用户名" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">微信ID</span>
             <input type="text" name="wechatId" class="form-control" placeholder="微信ID" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">真实姓名</span>
             <input type="text" name="realName" class="form-control" placeholder="真实姓名" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">电子邮箱</span>
             <input type="text" name="email" class="form-control" placeholder="电子邮箱" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">手机号码</span>
             <input type="text" name="mobilePhone" class="form-control" placeholder="手机号码" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">登录密码</span>
             <input type="password" name="password" class="form-control" placeholder="登录密码" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请填写6-18位密码">
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">确认密码</span>
             <input type="password" name="confirmPassword" class="form-control" placeholder="确认密码" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="确认密码必须与登录密码一致">
             </div>
          </div>  
           <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">性别</span>
            <select class="form-control" name="gender">
               <%
                for(Gender t: Gender.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
            </select>
             </div>
          </div>
          <br>        
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">部门</span>
             <input type="text"  readonly id="department" class="form-control" placeholder="部门" aria-describedby="sizing-addon2"   >
             <input type="hidden" name="department.id" id="departmentId">
             </div>
          </div>
          <div class="row" id="menuContent">
             <div class="col-md-offset-1 col-md-11 input-group">
              <ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
             </div>
          </div>
           
           <br>
           <div class="row" >
             <div class="col-md-12 input-group">
              <span class="input-group-addon" id="sizing-addon1">角色</span>
              <c:forEach var="item" items="${roleList}" varStatus="status">
              <div class="checkbox">
                <label><input type="checkbox" name="role_chk"  value="${item.id}" id="role_${item.id}" >${item.nameZh}</label>
              </div> 
              </c:forEach>
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
         <input type="hidden" name="id" />
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">用户名</span>
             <input type="text" name="name" class="form-control"  readonly="readonly" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">微信ID</span>
             <input type="text" name="wechatId" class="form-control" placeholder="微信ID" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">真实姓名</span>
             <input type="text" name="realName" class="form-control" placeholder="真实姓名" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">电子邮箱</span>
             <input type="text" name="email" class="form-control" placeholder="电子邮箱" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">手机号码</span>
             <input type="text" name="mobilePhone" class="form-control" placeholder="手机号码" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">登录密码</span>
             <input type="password" name="password" class="form-control" placeholder="不修改密码请留空" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请填写6-18位密码">
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">确认密码</span>
             <input type="password" name="confirmPassword" class="form-control" placeholder="不修改密码请留空" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="确认密码必须与登录密码一致">
             </div>
          </div>  
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">性别</span>
            <select class="form-control" name="gender">
               <%
                for(Gender t: Gender.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
            </select>
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">是否锁定</span>
            <select class="form-control" name="accountNonLocked">
              <option value="false">是</option>
              <option value="true">否</option>
            </select>
             </div>
          </div>
          <br>        
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">部门</span>
             <input type="text"  readonly id="edit_department" class="form-control" name="departmentName" placeholder="部门" aria-describedby="sizing-addon2"   >
             <input type="hidden" name="departmentId" id="edit_departmentId">
             </div>
          </div>
          <div class="row" id="edit_menuContent">
             <div class="col-md-offset-1 col-md-11 input-group">
              <ul id="edit_tree" class="ztree" style="margin-top:0; width:160px;"></ul>
             </div>
          </div>
           
           <br>
           <div class="row" >
             <div class="col-md-12 input-group">
              <span class="input-group-addon" id="sizing-addon1">角色</span>
              <c:forEach var="item" items="${roleList}" varStatus="status">
              <div class="checkbox">
                <label><input type="checkbox" name="edit_role_chk"  value="${item.id}" id="edit_role_${item.id}" >${item.nameZh}</label>
              </div> 
              </c:forEach>
             </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="update();" >保存</button>
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
   
   <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
   <script src="<c:url value='/resources/js/baseframework/security/user_manage.js' />"></script>
   <link rel="stylesheet" href="<c:url value='/plugin/ztree/css/zTreeStyle/zTreeStyle.css' />" type="text/css">
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
   <script src="<c:url value='/resources/js/commons-util.js' />"></script>
   </body>
</html>
