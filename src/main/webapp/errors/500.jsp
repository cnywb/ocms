<jsp:directive.page import="org.apache.commons.logging.Log"/>
<jsp:directive.page import="org.apache.commons.logging.LogFactory"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>500错误</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
    <c:import url="/WEB-INF/view/include/color_admin_header.jsp"/>
</head>
<body class="pace-top">
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<!-- begin #page-container -->
	<div id="page-container" class="fade">
	    <!-- begin error -->
        <div class="error">
            <div class="error-code m-b-10">500 <i class="fa fa-warning"></i></div>
            <div class="error-content">
                <div class="error-message">您请求的资源发生错误...</div>
                <div class="error-desc m-b-20">
               

                </div>
                <div>
                    <a href="<%=request.getHeader("Referer")==null?"":request.getHeader("Referer").toString()%>" class="btn btn-success">返回上一页</a>
                </div>
            </div>
        </div>
        <!-- end error -->
        
      
        
		
	</div>
	<!-- end page container -->
	
	 <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
	
	<script>
		$(document).ready(function() {
			App.init();
		});
	</script>
</body>
</html>
