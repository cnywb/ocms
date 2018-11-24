<%@ page language="java" import="java.util.*,com.ternnetwork.baseframework.enums.Gender,com.ternnetwork.baseframework.model.security.User" pageEncoding="UTF-8"%>
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
                         
                            <h4 class="panel-title">修改帐户信息</h4>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" action="/" method="POST">
                                <fieldset>
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">真实姓名</label>
                                        <div class="col-md-8">
                                                     <input type="text" name="realName" value="${user.realName}" class="form-control" placeholder="真实姓名" >

                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">性别</label>
                                        <div class="col-md-8">
                                     <select class="form-control" name="gender">
                                     <%
                                     for(Gender t: Gender.values()){
                	                  User user=(User)request.getAttribute("user");
                	                  if(user.getGender().equals(t)){
                		               out.println(" <option selected=\"selected\" value=\""+t.name()+"\">"+t.getName()+"</option>");
                	                  }else{
                		               out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
                	                  }
	                                 }
                                    %>
                                     </select>                        
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">电子邮箱</label>
                                        <div class="col-md-8">
                                        <input type="text" name="email" value="${user.email}" class="form-control" placeholder="电子邮箱" aria-describedby="sizing-addon2" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">手机号码</label>
                                        <div class="col-md-8">
                                                     <input type="text"  value="${user.mobilePhone}" name="mobilePhone" class="form-control" placeholder="手机号码" >
 
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-4 control-label">联系地址</label>
                                        <div class="col-md-8">
                          <input type="text"  value="${user.address}" name="address" class="form-control" placeholder="联系地址"  >
      
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <label class="col-md-4 control-label">所在单位</label>
                                        <div class="col-md-8">
                                                      <input type="text"  value="${user.company}" name="company" class="form-control" placeholder="所在单位"  >
     
 
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
 <script src="<c:url value='/resources/js/baseframework/security/user_info_update.js' />"></script>
 
   </body>
</html>
