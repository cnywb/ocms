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
    <link rel="stylesheet" href="<c:url value='/plugin/ztree/css/zTreeStyle/zTreeStyle.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-table/css/bootstrap-table.css' />">
 	<link href="<c:url value='/assets/plugins/jquery-tag-it/css/jquery.tagit.css' />" rel="stylesheet" />
  
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 </head>
   <body >
 
<div class="container-fluid">
	<div class="row-fluid">
	<div class="col-md-3">
			<br>
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel panel-inverse">
              <div class="panel-heading" role="tab" id="headingOne">
              <h4 class="panel-title">
              部门
             </h4>
              </div>
               <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                 <div class="panel-body">
                  <ul id="treeDemo" class="ztree" ></ul>
                
                </div>
                </div>
              </div>
            </div>
		</div>
		
	 <div class="col-md-9">
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
    <table id="data_table" data-url="<c:url value='/wechat/qy/txl/user/queryByDepartmentId.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead> 
     <tr> 
      <th data-field="userid" data-align="center" data-sortable="false">ID</th> 
      <th data-field="name" data-align="center" data-sortable="false">名称</th> 
      <th data-field="operate" data-align="center" data-sortable="false">操作</th>  
     </tr> 
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
             <span class="input-group-addon" id="sizing-addon1">用户ID</span>
             <input type="text" name="userid" class="form-control" placeholder="成员惟一标识，不可用中文" aria-describedby="sizing-addon2" >
             </div>
          </div>
       
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">姓名</span>
             <input type="text" name="name" class="form-control" placeholder="姓名" aria-describedby="sizing-addon2" >
             </div>
          </div> 
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">职位</span>
             <input type="text"  name="position" class="form-control" placeholder="职位" aria-describedby="sizing-addon2"  data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">微信号</span>
             <input type="text" name="weixinid" class="form-control" placeholder="允许员工扫描的微信匹配关注" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名">
             </div>
          </div>              
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">手机</span>
             <input type="text" name="mobile" class="form-control" placeholder="允许手机对应的微信与员工扫描的微信匹配关注" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名">
             </div>
          </div>
             <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">邮箱</span>
             <input type="text" name="email" class="form-control" placeholder="若未匹配到员工的微信则用邮箱来验证" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名">
             </div>
          </div>
        
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">部门</span>
             <ul id="jquery-tagIt-primary" class="primary">
                         
              </ul>
              
             </div>
          </div>
           <div class="row">
             <div id="treeAdd_container" class="col-md-12 input-group" style="display:none;">
               <span class="input-group-addon" >选择</span>
               <ul id="treeAdd" class="ztree" ></ul>
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
             <span class="input-group-addon" id="sizing-addon1">用户ID</span>
             <input type="text" name="userid" class="form-control" placeholder="成员惟一标识，不可用中文" aria-describedby="sizing-addon2" >
             </div>
          </div>
       
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">姓名</span>
             <input type="text" name="name" class="form-control" placeholder="姓名" aria-describedby="sizing-addon2" >
             </div>
          </div> 
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">职位</span>
             <input type="text"  name="position" class="form-control" placeholder="职位" aria-describedby="sizing-addon2"  data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">微信号</span>
             <input type="text" name="weixinid" class="form-control" placeholder="允许员工扫描的微信匹配关注" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名">
             </div>
          </div>              
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">手机</span>
             <input type="text" name="mobile" class="form-control" placeholder="允许手机对应的微信与员工扫描的微信匹配关注" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名">
             </div>
          </div>
             <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">邮箱</span>
             <input type="text" name="email" class="form-control" placeholder="若未匹配到员工的微信则用邮箱来验证" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请不要填写文件后缀名">
             </div>
          </div>
        
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">部门</span>
             <ul id="jquery-tagIt-primary-edit" class="primary">
                         
              </ul>
              
             </div>
          </div>
           <div class="row">
             <div id="treeEdit_container" class="col-md-12 input-group" style="display:none;">
               <span class="input-group-addon" >选择</span>
               <ul id="treeEdit" class="ztree" ></ul>
             </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
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

 
    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/assets/plugins/jquery-tag-it/js/tag-it.js' />"></script>
    <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
    <script src="<c:url value='/resources/js/wechat/qy/txl/user_manage.js' />"></script>
 
   
   </body>
</html>
