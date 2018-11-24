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
  <link rel="stylesheet" href="<c:url value='/plugin/ztree/css/zTreeStyle/zTreeStyle.css' />" type="text/css">
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
  <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
 </head>
   <body >
 
<div class="container-fluid">
<br>
	<div class="row-fluid">
		<div class="col-md-12">
			
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel panel-inverse">
              <div class="panel-heading" role="tab" id="headingOne">
              <h4 class="panel-title">
              <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        集群管理
              </a>
             </h4>
            </div>
           <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
             <div class="panel-body">
             <ul id="treeDemo" class="ztree" ></ul>
           </div>
              <div class="panel-footer">
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModal">新建服务器节点</button>
       </div>
          </div>
           </div>
          </div>
		</div>
		<div class="col-md-5">
		</div>
		<div class="col-md-5">
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
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">IP</span>
             <input type="text" name="ip" class="form-control" placeholder="IP" aria-describedby="sizing-addon2" >
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
        <input type="hidden" name="id"  >
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">IP</span>
             <input type="text" name="ip" class="form-control" placeholder="IP" aria-describedby="sizing-addon2" >
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



<div class="modal fade" role="dialog" id="addWebAppInstanceModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >新建应用实例</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
        <input type="hidden" name="serverNode.id"  id="serverNodeId" >
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">FTP端口</span>
             <input type="text" name="ftpPort" class="form-control" placeholder="FTP端口" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">FTP密码</span>
             <input type="text" name="ftpPassword" class="form-control" placeholder="FTP密码" aria-describedby="sizing-addon2" >
             </div>
          </div>  
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="saveWebAppInstance();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" role="dialog" id="editWebAppInstanceModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >编辑应用实例</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
         <input type="hidden" name="id" >
         <input type="hidden" name="serverNode.id"  >
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">FTP端口</span>
             <input type="text" name="ftpPort" class="form-control" placeholder="FTP端口" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">FTP密码</span>
             <input type="text" name="ftpPassword" class="form-control" placeholder="FTP密码" aria-describedby="sizing-addon2" >
             </div>
          </div>  
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="updateWebAppInstance();">保存</button>
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
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.excheck-3.5.js' />"></script>
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.exedit-3.5.js' />"></script>
   <script src="<c:url value='/resources/js/baseframework/config/cluster_manage.js' />"></script>
  
   </body>
</html>
