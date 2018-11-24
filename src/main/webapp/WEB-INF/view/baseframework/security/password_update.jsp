<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	
	<!-- ================== BEGIN BASE CSS STYLE ================== -->
	<link href="<c:url value='/assets/css/googlefonts.css' />" rel="stylesheet">
	<link href="<c:url value='/assets/plugins/jquery-ui/themes/base/minified/jquery-ui.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/assets/plugins/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/assets/css/animate.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/assets/css/style.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/assets/css/style-responsive.min.css' />" rel="stylesheet" />
	<link href="<c:url value='/assets/css/theme/default.css' />" rel="stylesheet" id="theme" />
	<!-- ================== END BASE CSS STYLE ================== -->
	
	<!-- ================== BEGIN BASE JS ================== -->
	<script src="<c:url value='/assets/plugins/pace/pace.min.js' />"></script>
	<!-- ================== END BASE JS ================== -->
	
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
  
 </head>
   <body >
 <!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<!-- begin #page-container -->
	<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
	<!-- begin container-fluid -->
   <div class="container-fluid" id="content_container">
	<div class="row-fluid">
          
	      <div class="col-md-6 col-md-offset-3">
	         <div class="panel panel-inverse" data-sortable-id="form-stuff-4">
                        <div class="panel-heading">
                         
                            <h4 class="panel-title">修改密码</h4>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" action="/" method="POST">
                                <fieldset>
                                   
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">输入旧密码</label>
                                        <div class="col-md-8">
                                          <input type="password" name="orinPassword" class="form-control" placeholder="旧密码"  >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">输入新密码</label>
                                        <div class="col-md-8">
                                            <input type="password" name="newPassword" class="form-control" placeholder="新密码"  >
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <label class="col-md-4 control-label">确认新密码</label>
                                        <div class="col-md-8">
                                                <input type="password" name="confirmNewPassword" class="form-control" placeholder="确认新密码"  >
 
                                        </div>
                                    </div>
                                   
                                    <div class="form-group">
                                        <div class="col-md-8 col-md-offset-4">
                                            <button type="button" id="btn_save" class="btn btn-sm btn-primary m-r-5">保存</button>
                                           
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                    <!-- end panel -->
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
 
 
 
 	<!-- ================== BEGIN BASE JS ================== -->
	<script src="<c:url value='/assets/plugins/jquery/jquery-1.9.1.min.js' />"></script>
	<script src="<c:url value='/assets/plugins/jquery/jquery-migrate-1.1.0.min.js' />"></script>
	<script src="<c:url value='/assets/plugins/jquery-ui/ui/minified/jquery-ui.min.js' />"></script>
	<script src="<c:url value='/assets/plugins/bootstrap/js/bootstrap.min.js' />"></script>
	<!--[if lt IE 9]>
		<script src="../../assets/crossbrowserjs/html5shiv.js' />"></script>
		<script src="../../assets/crossbrowserjs/respond.min.js' />"></script>
		<script src="../../assets/crossbrowserjs/excanvas.min.js' />"></script>
	<![endif]-->
	<script src="<c:url value='/assets/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
	<script src="<c:url value='/assets/plugins/jquery-cookie/jquery.cookie.js' />"></script>
	<!-- ================== END BASE JS ================== -->
	
	<!-- ================== BEGIN PAGE LEVEL JS ================== -->
	<script src="<c:url value='/assets/js/apps.min.js' />"></script>
	<!-- ================== END PAGE LEVEL JS ================== -->
 
 	<script src="<c:url value='/resources/js/baseframework/security/password_update.js' />"></script>
 
 
   </body>
</html>
