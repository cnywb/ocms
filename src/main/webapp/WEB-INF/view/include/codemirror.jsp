<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/lib/codemirror.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/addon/hint/show-hint.css' />">
 	
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/blackboard.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/3024-day.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/3024-night.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/abcdef.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/ambiance.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/base16-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/bespin.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/base16-light.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/cobalt.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/colorforth.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/dracula.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/duotone-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/duotone-light.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/eclipse.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/elegant.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/erlang-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/hopscotch.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/icecoder.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/isotope.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/lesser-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/liquibyte.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/material.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/mbo.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/mdn-like.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/midnight.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/monokai.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/neat.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/neo.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/night.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/panda-syntax.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/paraiso-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/paraiso-light.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/pastel-on-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/railscasts.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/rubyblue.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/seti.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/solarized.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/the-matrix.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/tomorrow-night-bright.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/tomorrow-night-eighties.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/ttcn.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/twilight.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/vibrant-ink.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/xq-dark.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/xq-light.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/yeti.css' />">
<link rel="stylesheet" href="<c:url value='/plugin/codemirror/theme/zenburn.css' />">
 	
 	
<script charset="utf-8" src="<c:url value='/plugin/codemirror/lib/codemirror.js' />"></script>
<script charset="utf-8" src="<c:url value='/plugin/codemirror/mode/javascript/javascript.js' />"></script> 
<script charset="utf-8" src="<c:url value='/plugin/codemirror/mode/css/css.js' />"></script> 
<script charset="utf-8" src="<c:url value='/plugin/codemirror/mode/xml/xml.js' />"></script> 
<script charset="utf-8" src="<c:url value='/plugin/codemirror/mode/vbscript/vbscript.js' />"></script> 
<script charset="utf-8" src="<c:url value='/plugin/codemirror/mode/htmlmixed/htmlmixed.js' />"></script> 
<script charset="utf-8" src="<c:url value='/plugin/codemirror/addon/selection/selection-pointer.js' />"></script> 
 
<script charset="utf-8" src="<c:url value='/plugin/codemirror/addon/selection/active-line.js' />"></script>
<script charset="utf-8" src="<c:url value='/plugin/codemirror/addon/edit/matchbrackets.js' />"></script>
 	
<script src="<c:url value='/plugin/codemirror/addon/hint/show-hint.js' />"></script>
<script src="<c:url value='/plugin/codemirror/addon/hint/css-hint.js' />"></script>
<script src="<c:url value='/plugin/codemirror/addon/hint/html-hint.js' />"></script>
<script src="<c:url value='/plugin/codemirror/addon/hint/javascript-hint.js' />"></script>
<script src="<c:url value='/plugin/codemirror/addon/display/fullscreen.js' />"></script>
<script src="<c:url value='/plugin/codemirror/mode/markdown/markdown.js' />"></script>
