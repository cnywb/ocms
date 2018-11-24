<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="wechatLogin">
  <head>
    <base href="<%=basePath%>">
    <title>微信订阅号授权</title>
  	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="1">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="<c:url value='/plugin/jquery/jquery-1.11.2.js' />"></script>
  </head>
 <body >
 
 
    <script type="text/javascript">
    
    $(document).ready(function(){
    	var wechatAuthCode=$("#wechatDyAuthCode").val();
    	 var fontSize=$("font[color='red']").size();
         if(fontSize>0){
        	 $("font[color='green']").each(function(){
        		 $(this).html("");
        	 });
         }
       	if(wechatAuthCode==""&&fontSize==0){//没有错误信息
           window.location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${wechatLoginURL}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
     	}else if(fontSize==0){
     		$("#formLogin").submit();
     	}
     });
    
    
    </script>

    <form action="<%=basePath%>login" id="formLogin" method="post" >
          <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.authentication.AuthenticationServiceException'}">
                      <font color="red">用户名不存在</font>
          </c:if>
          <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.authentication.BadCredentialsException: Bad credentials'}">
                      <font color="red"> 密码错误</font>
         </c:if>
         <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.authentication.LockedException: User account is locked'}">
                       <font color="red">用户被锁定，请联系管理员解锁。</font>
         </c:if>
         <c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION=='org.springframework.security.web.authentication.session.SessionAuthenticationException: Maximum sessions of 1 for this principal exceeded'}">
                      <font color="red"> 该用户已在别处登录，请先退出<br/>后再登录或联系管理员<br/>踢除已登录账户。</font>
         </c:if>
 
            <input name="username"     type="hidden">
            <input name="password"     type="hidden">
            <input name="wechatDyAuthCode" id="wechatDyAuthCode" value="${wechatDyAuthCode}" type="hidden">
       
</form>
  <font color="green">登录中....</font>
 </body>
</html>
