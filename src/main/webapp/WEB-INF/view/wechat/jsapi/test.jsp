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
    <title>订阅号JSAPI测试</title>
  	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="1">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="<c:url value='/plugin/jquery/jquery-1.11.2.js' />"></script>
     <script type="text/javascript" src="<c:url value='/resources/js/wechat/wechat.1.0.0.js' />"></script>
   <script type="text/javascript">
   wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${jsapiConfig.appId}', // 必填，企业号的唯一标识，此处填写企业号corpid
	    timestamp:'${jsapiConfig.timestamp}' , // 必填，生成签名的时间戳
	    nonceStr: '${jsapiConfig.nonceStr}', // 必填，生成签名的随机串
	    signature: '${jsapiConfig.signature}',// 必填，签名，见附录1
	    jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function(){
		
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});
	wx.error(function(res){
		
	   alert(JSON.stringify(res));
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});

	
	$(document).ready(function(){
		
		$("#btn_test_scan").click(function(){
			
			 wx.scanQRCode({
				    desc: 'scanQRCode desc',
				    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
				    success: function (res) {
				    alert(res.resultStr.split(",")[1]);
					}
				});
			
		});
		
		
	});
	
   
    </script>
  
  </head>
 <body >
 
 <input id="btn_test_scan" type="button" value="测试扫描"  >
   


 </body>
</html>
