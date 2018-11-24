<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<base href="<%=basePath%>">
<head>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<script type="text/javascript">
if(window.parent.initTree!=undefined){
	window.parent.location.href="<%=basePath%>login.jsp";
}
</script>
<c:import url="/WEB-INF/view/include/color_admin_header.jsp"/>
<title>用户登录</title>
</head>
<body class="pace-top">
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<div class="login-cover">
	    <div class="login-cover-image"><img src="<c:url value='/assets/img/login-bg/bg-1.jpg' />" data-id="login-cover-image" alt="" /></div>
	    <div class="login-cover-bg"></div>
	</div>
	<!-- begin #page-container -->
	<div id="page-container" class="fade">
	    <!-- begin login -->
        <div class="login login-v2" data-pageload-addclass="animated fadeIn">
            <!-- begin brand -->
            <div class="login-header">
                <div class="brand">
                    <span class="logo"></span>用户登录
                    <small>
                       <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.authentication.AuthenticationServiceException'}">
                   
                  <div class="alert alert-danger" role="alert"> 用户名不存在</div>
          </c:if>
          <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.authentication.BadCredentialsException: Bad credentials'}">
                         <div class="alert alert-danger" role="alert">用户名或密码错误</div>
         </c:if>
         <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.authentication.LockedException: User account is locked'}">
                         <div class="alert alert-danger" role="alert">用户被锁定，请联系管理员解锁。</div>
         </c:if>
          <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.web.authentication.session.SessionAuthenticationException: Maximum sessions of 1 for this principal exceeded'}">
                        <div class="alert alert-danger" role="alert"> 该用户已在别处登录，请先退出后再登录，或联系管理员踢除已登录账户。</div>
         </c:if>
                    
                    </small>
                </div>
                <div class="icon">
                    <i class="fa fa-sign-in"></i>
                </div>
            </div>
            <!-- end brand -->
            <div class="login-content">
            <form id="form_user_login" class="margin-bottom-0" action="<%=basePath%>login" method="post">
               
                    <div class="form-group m-b-20">
                        <input type="text" name="username"  class="form-control input-lg" placeholder="用户名" />
                    </div>
                    <div class="form-group m-b-20">
                        <input type="password"  name="password" class="form-control input-lg" placeholder="密码" />
                    </div>
                  
                    <div class="login-buttons">
                        <button type="submit" class="btn btn-success btn-block btn-lg">登录</button>
                    </div>
                    <div class="m-t-20">
                      
                    </div>
                </form>
            </div>
        </div>
        <!-- end login -->
        
       
        
       
	</div>
	<!-- end page container -->
    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
	<!-- ================== BEGIN PAGE LEVEL JS ================== -->
	<script src="<c:url value='/assets/js/login-v2.demo.min.js' />"></script>
    <script src="<c:url value='/resources/js/login.js' />"></script>
	</body>
</html>