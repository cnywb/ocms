<%@ page language="java" import="java.util.*,com.ternnetwork.cms.enums.CmsPageStatus" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="title" content="Create page with Bootstrap">
<meta name="description" content="Create page with Bootstrap">
<meta name="keywords" content="Create page with Bootstrap">
<title>Bootstrap可视化布局设计器</title>

<!-- Le styles -->
<link href="<c:url value='/plugin/layoutit/css/bootstrap-combined.min.css' />" rel="stylesheet" />
<link href="<c:url value='/plugin/layoutit/css/layoutit.css' />" rel="stylesheet" />
<link href="<c:url value='/plugin/layoutit/css/docs.min.css' />" rel="stylesheet" />
<link href="<c:url value='/plugin/layoutit/css/extend_component.css' />" rel="stylesheet" />
<link href="<c:url value='/plugin/layoutit/css/font-awesome.min.css' />" rel="stylesheet" />

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
		<script src="/plugin/layoutit/js/html5shiv.js"></script>
	<![endif]-->

	<!-- Fav and touch icons -->
	<link rel="shortcut icon" href="img/favicon.png">

	<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/jquery-2.0.0.min.js' />"></script>
	<!--[if lt IE 9]>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/bootstrap.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/jquery-ui.js' />"></script>
	<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/jquery.ui.touch-punch.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/jquery.htmlClean.js' />"></script>
<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/scripts_page_layout_edit.js' />"></script>
<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/FileSaver.js' />"></script>
<script type="text/javascript" src="<c:url value='/plugin/layoutit/js/blob.js' />"></script>
<script src="<c:url value='/plugin/layoutit/js/docs.min.js' />"></script>
<link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
<link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
<script src="<c:url value='/plugin/kindeditor/plugins/code/code.js' />" type="text/javascript"></script>  

<script src="<c:url value='/resources/js/commons-util.js' />"></script>
<script src="<c:url value='/resources/js/cms/page/page_layout_edit.js' />"></script>
</head>

<body style="min-height: 660px; cursor: auto;" class="edit">
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">
      <div class="nav-collapse collapse">
      	<ul class="nav" id="menu-layoutit">
          <li class="divider-vertical"></li>
          <li>
		  <div class="btn-group">
			<button onclick="resizeCanvas('lg')" class="btn btn-primary"><i class="fa fa-desktop"></i> </button>
			<button onclick="resizeCanvas('md')" class="btn btn-primary"><i class="fa fa-laptop"></i> </button>
			<button onclick="resizeCanvas('sm')" class="btn btn-primary"><i class="fa fa-tablet"></i> </button>
			<button onclick="resizeCanvas('xs')" class="btn btn-primary"><i class="fa fa-mobile-phone"></i> </button>
		  </div>
            <div class="btn-group">
              <a class="btn btn-primary" href="/" target="_blank"><i class="icon-home icon-white"></i>首页</a>
            </div>
            <div class="btn-group" data-toggle="buttons-radio">
              <button type="button" id="edit" class="btn btn-primary active"><i class="icon-edit icon-white"></i>编辑</button>
              <button type="button" class="btn btn-primary" id="devpreview"><i class="icon-eye-close icon-white"></i>开发模式</button>
              <button type="button" class="btn btn-primary" id="sourcepreview"><i class="icon-eye-open icon-white"></i>预览</button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-primary" data-target="#downloadModal" rel="/build/downloadModal" role="button" data-toggle="modal"><i class="icon-chevron-down icon-white"></i>保存</button>
              <button class="btn btn-primary" href="#clear" id="clear"><i class="icon-trash icon-white"></i>清除</button>
            </div>
          </li>
        </ul>
      </div>
      <!--/.nav-collapse --> 
    </div>
  </div>
</div>
<div class="container-fluid">
<div class="changeDimension">
  <div class="row-fluid">
    <div class="">
      <div class="sidebar-nav">
        <ul class="nav nav-list accordion-group">
          <li class="nav-header">
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">提示</h3>
                <div class="popover-content">这里有基础的布局方式，您只需将它们拖到容器中即可。</div>
              </div>
            </div>
            <i class="icon-plus icon-white"></i> 栅格布局</li>
          <li style="display: list-item;" class="rows" id="estRows">
         <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="12" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span12 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="6 6" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span6 column"></div>
                  <div class="span6 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="8 4" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span8 column"></div>
                  <div class="span4 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="4 4 4" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span4 column"></div>
                  <div class="span4 column"></div>
                  <div class="span4 column"></div>
                </div>
              </div>
            </div>
            <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="2 6 4" type="text">
              </div>
              <div class="view">
                <div class="row-fluid clearfix">
                  <div class="span2 column"></div>
                  <div class="span6 column"></div>
                  <div class="span4 column"></div>
                </div>
              </div>
            </div>
			 <div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="6 4 4" type="text">
              </div>
              <div class="view">
              <div class="row-fluid clearfix">
					<div class="col-xs-6 span4 column"></div>
					<div class="col-xs-6 span4 column"></div>
					<div class="col-xs-6 span4 column"></div>
			</div>
              </div>
            </div>
			<div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="9 3" type="text">
              </div>
              <div class="view">
              <div class="row-fluid clearfix">
					<div class="col-xs-6 span9 column" ></div>
					<div class="col-xs-6 span3 column "></div>
			</div>
              </div>
            </div>
			<div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="3 3 3 3" type="text">
              </div>
              <div class="view">
              <div class="row-fluid clearfix">
					<div class="col-xs-3 span3 column" ></div>
					<div class="col-xs-3 span3 column "></div>
					<div class="col-xs-3 span3 column"></div>
					<div class="col-xs-3 span3 column"></div>
			</div>
              </div>
            </div>
				<div class="lyrow ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">
                <input value="Nested Columns" type="text">
              </div>
              <div class="view">
               <div class="row-fluid clearfix">
				<div class="span12 column">
				<div class="row-fluid clearfix">
					  <div class="span6 column">
			
					  </div>
					  <div class="span6 column">
					
					  </div>
					</div>
			  </div>
			</div>
              </div>
            </div>
          </li>
        </ul>
        
   
        
        
        <ul class="nav nav-list accordion-group">
          <li class="nav-header"><i class="icon-plus icon-white"></i> 基本样式
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">提示</h3>
                <div class="popover-content">这里提供了基本的样式主件.</div>
              </div>
            </div>
          </li>
          <li style="display: none;" class="boxes" >
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> 
            	 <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Align <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="text-left">Left</a></li>
                <li class=""><a href="#" rel="text-center">Center</a></li>
                <li class=""><a href="#" rel="text-right">Right</a></li>
              </ul>
              </span> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Emphasis <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="muted">Muted</a></li>
                <li class=""><a href="#" rel="text-warning">Warning</a></li>
                <li class=""><a href="#" rel="text-error">Error</a></li>
                <li class=""><a href="#" rel="text-info">Info</a></li>
                <li class=""><a href="#" rel="text-success">Success</a></li>
              </ul>
              </span> </span>
              <div class="preview">标题</div>
              <div class="view">
                <h3 class="component_container" name="component1" contenteditable="true">h3. Lorem ipsum dolor sit amet.</h3>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Align <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="text-left">Left</a></li>
                <li class=""><a href="#" rel="text-center">Center</a></li>
                <li class=""><a href="#" rel="text-right">Right</a></li>
              </ul>
              </span> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Emphasis <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="muted">muted</a></li>
                <li class=""><a href="#" rel="text-warning">Warning</a></li>
                <li class=""><a href="#" rel="text-error">Error</a></li>
                <li class=""><a href="#" rel="text-info">Info</a></li>
                <li class=""><a href="#" rel="text-success">Success</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="lead">Lead</a> </span>
              <div class="preview">段落</div>
              <div class="view" contenteditable="true">
                <p class="component_container" name="component2" >Lorem ipsum dolor sit amet, <strong>consectetur adipiscing elit</strong>. Aliquam eget sapien sapien. Curabitur in metus urna. In hac habitasse platea dictumst. Phasellus eu sem sapien, sed vestibulum velit. Nam purus nibh, lacinia non faucibus et, pharetra in dolor. Sed iaculis posuere diam ut cursus. Morbi commodo sodales nisi id sodales. Proin consectetur, nisi id commodo imperdiet, metus nunc consequat lectus, id bibendum diam velit et dui. Proin massa magna, vulputate nec bibendum nec, posuere nec lacus. Aliquam mi erat, aliquam vel luctus eu, pharetra quis elit. Nulla euismod ultrices massa, et feugiat ipsum consequat eu. </p>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">地址</div>
              <div class="view">
                <address class="component_container" name="component3" contenteditable="true">
                <strong>Twitter, Inc.</strong><br>
                795 Folsom Ave, Suite 600<br>
                San Francisco, CA 94107<br>
                <abbr title="Phone">P:</abbr> (123) 456-7890
                </address>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> <a class="btn btn-mini" href="#" rel="pull-right">Pull Right</a> </span>
              <div class="preview">块引用</div>
              <div class="view clearfix">
                <blockquote name="component4" contenteditable="true">
                  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
                  <small>Someone<cite title="Source Title"> famous Source Title</cite></small> </blockquote>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="unstyled">Unstyled</a> <a class="btn btn-mini" href="#" rel="inline">Inline</a> </span>
              <div class="preview">无序列表</div>
              <div class="view">
                <ul class="component_container" name="component5" contenteditable="true">
                  <li>Lorem ipsum dolor sit amet</li>
                  <li>Consectetur adipiscing elit</li>
                  <li>Integer molestie lorem at massa</li>
                  <li>Facilisis in pretium nisl aliquet</li>
                  <li>Nulla volutpat aliquam velit</li>
                  <li>Faucibus porta lacus fringilla vel</li>
                  <li>Aenean sit amet erat nunc</li>
                  <li>Eget porttitor lorem</li>
                </ul>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="unstyled">Unstyled</a> <a class="btn btn-mini" href="#" rel="inline">Inline</a> </span>
              <div class="preview">有序列表</div>
              <div class="view">
                <ol class="component_container" name="component6" contenteditable="true">
                  <li>Lorem ipsum dolor sit amet</li>
                  <li>Consectetur adipiscing elit</li>
                  <li>Integer molestie lorem at massa</li>
                  <li>Facilisis in pretium nisl aliquet</li>
                  <li>Nulla volutpat aliquam velit</li>
                  <li>Faucibus porta lacus fringilla vel</li>
                  <li>Aenean sit amet erat nunc</li>
                  <li>Eget porttitor lorem</li>
                </ol>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="dl-horizontal">Horizontal</a> </span>
              <div class="preview">描述</div>
              <div class="view">
                <dl class="component_container" name="component7" contenteditable="true">
                  <dt>Rolex</dt>
                  <dd>A description list is perfect for defining terms.</dd>
                  <dt>Euismod</dt>
                  <dd>Vestibulum id ligula porta felis euismod semper eget lacinia odio sem nec elit.</dd>
                  <dd>Donec id elit non mi porta gravida at eget metus.</dd>
                  <dt>Malesuada porta</dt>
                  <dd>Etiam porta sem malesuada magna mollis euismod.</dd>
                  <dt>Felis euismod semper eget lacinia</dt>
                  <dd>Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</dd>
                </dl>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Style <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="table-striped">Striped</a></li>
                <li class=""><a href="#" rel="table-bordered">Bordered</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="table-hover">Hover</a> <a class="btn btn-mini" href="#" rel="table-condensed">Condensed</a> </span>
              <div class="preview">表格</div>
              <div class="view">
                <table class="component_container" name="component8" class="table" contenteditable="true">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Product</th>
                      <th>Payment Taken</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>TB - Monthly</td>
                      <td>01/04/2012</td>
                      <td>Default</td>
                    </tr>
                    <tr class="success">
                      <td>1</td>
                      <td>TB - Monthly</td>
                      <td>01/04/2012</td>
                      <td>Approved</td>
                    </tr>
                    <tr class="error">
                      <td>2</td>
                      <td>TB - Monthly</td>
                      <td>02/04/2012</td>
                      <td>Declined</td>
                    </tr>
                    <tr class="warning">
                      <td>3</td>
                      <td>TB - Monthly</td>
                      <td>03/04/2012</td>
                      <td>Pending</td>
                    </tr>
                    <tr class="info">
                      <td>4</td>
                      <td>TB - Monthly</td>
                      <td>04/04/2012</td>
                      <td>Call in to confirm</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="form-inline">Inline</a> </span>
              <div class="preview">表单</div>
              <div class="view">
				<form class="component_container" name="component9" >
					<fieldset>
					<legend contenteditable="true">Legend</legend>
					<label contenteditable="true">Label name</label>
					<input type="text" placeholder="Type something…">
					<span class="help-block" contenteditable="true">Example block-level help text here..</span>
					<label class="checkbox" contenteditable="true">
					<input type="checkbox"> Check me out
					</label>
					<button type="submit" class="btn" contenteditable="true">Submit</button>
					</fieldset>
				</form>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="form-inline">Inline</a> </span>
              <div class="preview">搜索</div>
              <div class="view">
                <form class="component_container" name="component10" class="form-search">
                  <input class="input-medium search-query" type="text">
                  <button type="submit" class="btn" contenteditable="true">Search</button>
                </form>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> </span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">水平表单</div>
              <div class="view">
                <form class="component_container" name="component11" class="form-horizontal">
                  <div class="control-group">
                    <label class="control-label" for="inputEmail" contenteditable="true">Email</label>
                    <div class="controls">
                      <input id="inputEmail" placeholder="Email" type="text">
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="inputPassword" contenteditable="true">Password</label>
                    <div class="controls">
                      <input id="inputPassword" placeholder="Password" type="password">
                    </div>
                  </div>
                  <div class="control-group">
                    <div class="controls">
                      <label class="checkbox" contenteditable="true">
                        <input type="checkbox">
                        Remember me </label>
                      <button type="submit" class="btn" contenteditable="true">Sign In</button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="btn-primary">Primary</a></li>
                <li class=""><a href="#" rel="btn-info">Info</a></li>
                <li class=""><a href="#" rel="btn-success">Success</a></li>
                <li class=""><a href="#" rel="btn-warning">Warning</a></li>
                <li class=""><a href="#" rel="btn-danger">Danger</a></li>
                <li class=""><a href="#" rel="btn-inverse">Inverse</a></li>
                <li class=""><a href="#" rel="btn-link">Link</a></li>
              </ul>
              </span> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Size <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class=""><a href="#" rel="btn-large">Large</a></li>
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="btn-small">Small</a></li>
                <li class=""><a href="#" rel="btn-mini">Mini</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="btn-block">Block</a> <a class="btn btn-mini" href="#" rel="disabled">Disabled</a> </span>
              <div class="preview">按钮</div>
              <div class="view">
                <button  name="component12" class="btn component_container" type="button" contenteditable="true">Button</button>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="img-rounded">Rounded</a></li>
                <li class=""><a href="#" rel="img-circle">Circle</a></li>
                <li class=""><a href="#" rel="img-polaroid">Polaroid</a></li>
              </ul>
              </span> </span>
              <div class="preview">图片</div>
              <div class="view"> <img alt="140x140" class="component_container" name="component13" src="/plugin/layoutit/img/a.jpg"> </div>
            </div>
			  <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> </span>
              <div class="preview">仪表盘 </div>
              <div class="view"> <div name="component14" class="container-fluid component_container">
      <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">Dashboard</h1>

          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
          </div>

          <h2 class="sub-header">Section title</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Header</th>
                  <th>Header</th>
                  <th>Header</th>
                  <th>Header</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1,001</td>
                  <td>Lorem</td>
                  <td>ipsum</td>
                  <td>dolor</td>
                  <td>sit</td>
                </tr>
                <tr>
                  <td>1,002</td>
                  <td>amet</td>
                  <td>consectetur</td>
                  <td>adipiscing</td>
                  <td>elit</td>
                </tr>
                <tr>
                  <td>1,003</td>
                  <td>Integer</td>
                  <td>nec</td>
                  <td>odio</td>
                  <td>Praesent</td>
                </tr>
                <tr>
                  <td>1,003</td>
                  <td>libero</td>
                  <td>Sed</td>
                  <td>cursus</td>
                  <td>ante</td>
                </tr>
                <tr>
                  <td>1,004</td>
                  <td>dapibus</td>
                  <td>diam</td>
                  <td>Sed</td>
                  <td>nisi</td>
                </tr>
                <tr>
                  <td>1,005</td>
                  <td>Nulla</td>
                  <td>quis</td>
                  <td>sem</td>
                  <td>at</td>
                </tr>
                <tr>
                  <td>1,006</td>
                  <td>nibh</td>
                  <td>elementum</td>
                  <td>imperdiet</td>
                  <td>Duis</td>
                </tr>
                <tr>
                  <td>1,007</td>
                  <td>sagittis</td>
                  <td>ipsum</td>
                  <td>Praesent</td>
                  <td>mauris</td>
                </tr>
                <tr>
                  <td>1,008</td>
                  <td>Fusce</td>
                  <td>nec</td>
                  <td>tellus</td>
                  <td>sed</td>
                </tr>
                <tr>
                  <td>1,009</td>
                  <td>augue</td>
                  <td>semper</td>
                  <td>porta</td>
                  <td>Mauris</td>
                </tr>
                <tr>
                  <td>1,010</td>
                  <td>massa</td>
                  <td>Vestibulum</td>
                  <td>lacinia</td>
                  <td>arcu</td>
                </tr>
                <tr>
                  <td>1,011</td>
                  <td>eget</td>
                  <td>nulla</td>
                  <td>Class</td>
                  <td>aptent</td>
                </tr>
                <tr>
                  <td>1,012</td>
                  <td>taciti</td>
                  <td>sociosqu</td>
                  <td>ad</td>
                  <td>litora</td>
                </tr>
                <tr>
                  <td>1,013</td>
                  <td>torquent</td>
                  <td>per</td>
                  <td>conubia</td>
                  <td>nostra</td>
                </tr>
                <tr>
                  <td>1,014</td>
                  <td>per</td>
                  <td>inceptos</td>
                  <td>himenaeos</td>
                  <td>Curabitur</td>
                </tr>
                <tr>
                  <td>1,015</td>
                  <td>sodales</td>
                  <td>ligula</td>
                  <td>in</td>
                  <td>libero</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div></div>
            </div>
				  <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span><span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> </span>
              <div class="preview">颜色</div>
              <div class="view"> 
			 
				<div name="component15" class="bs-example component_container">
    <div class="color-swatches">
      <div class="color-swatch brand-primary"></div>
      <div class="color-swatch brand-success"></div>
      <div class="color-swatch brand-info"></div>
      <div class="color-swatch brand-warning"></div>
      <div class="color-swatch brand-danger"></div>
    </div>
	<br />
	<div class="color-swatches">
	  <div class="color-swatch gray-darker"></div>
      <div class="color-swatch gray-dark"></div>
      <div class="color-swatch gray"></div>
      <div class="color-swatch gray-light"></div>
      <div class="color-swatch gray-lighter"></div>
	</div>
  </div>
	</div>
			  </div>
          </li>
        </ul>
        <ul class="nav nav-list accordion-group">
          <li class="nav-header"><i class="icon-plus icon-white"></i> 组件
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">提示</h3>
                <div class="popover-content">这里提供了更加丰富的组件</div>
              </div>
            </div>
          </li>
          <li style="display: none;" class="boxes" id="elmComponents">
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Orientation<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="btn-group-vertical">Vertical</a></li>
              </ul>
              </span> </span>
              <div class="preview">按钮组</div>
              <div class="view">
                <div name="component16" class="btn-group component_container">
                  <button class="btn" type="button"><i class="icon-align-left"></i></button>
                  <button class="btn" type="button"><i class="icon-align-center"></i></button>
                  <button class="btn" type="button"><i class="icon-align-right"></i></button>
                  <button class="btn" type="button"><i class="icon-align-justify"></i></button>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="dropup">Dropup</a> </span>
              <div class="preview">下拉按钮</div>
              <div class="view">
                <div name="component17" class="btn-group component_container">
                  <button class="btn" contenteditable="true">Action</button>
                  <button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
                  <ul class="dropdown-menu" contenteditable="true">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another Action</a></li>
                    <li><a href="#">Something Else here</a></li>
                    <li class="divider"></li>
                    <li class="dropdown-submenu"> <a tabindex="-1" href="#">More Option</a>
                      <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
						<li><a href="#">Another Action</a></li>
						<li><a href="#">Something Else here</a></li>
                      </ul>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class=""><a href="#" rel="nav-tabs">Default</a></li>
                <li class=""><a href="#" rel="nav-pills">Pills</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="nav-stacked">Stacked</a> </span>
              <div class="preview">导航</div>
              <div class="view">
                <ul name="component18" class="nav nav-tabs component_container" contenteditable="true">
                  <li class="active"><a href="#">Home</a></li>
                  <li><a href="#">Profile</a></li>
                  <li class="disabled"><a href="#">Messages</a></li>
                  <li class="dropdown pull-right"> <a href="#" data-toggle="dropdown" class="dropdown-toggle">Dropdown <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                      <li><a href="#">Action</a></li>
                      <li><a href="#">Another Action</a></li>
                      <li><a href="#">Something else here</a></li>
                      <li class="divider"></li>
                      <li><a href="#">Separated Link</a></li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="well">Well</a> </span>
              <div class="preview">导航列表</div>
              <div class="view">
                <ul name="component19" class="nav nav-list component_container" contenteditable="true">
                  <li class="nav-header">Headers</li>
                  <li class="active"><a href="#">Home</a></li>
                  <li><a href="#">Library</a></li>
                  <li><a href="#">Application</a></li>
                  <li class="nav-header">Another List Header</li>
                  <li><a href="#">Profile</a></li>
                  <li><a href="#">Settings</a></li>
                  <li class="divider"></li>
                  <li><a href="#">Help</a></li>
                </ul>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">面包屑导航</div>
              <div class="view">
                <ul name="component20" class="breadcrumb component_container" contenteditable="true">
                  <li><a href="#">Home</a> <span class="divider">/</span></li>
                  <li><a href="#">Library</a> <span class="divider">/</span></li>
                  <li class="active">Application</li>
                </ul>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Size <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class=""><a href="#" rel="pagination-large">Large</a></li>
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="pagination-small">Small</a></li>
                <li class=""><a href="#" rel="pagination-mini">Mini</a></li>
              </ul>
              </span> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Position <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="pagination-centered">Centered</a></li>
                <li class=""><a href="#" rel="pagination-right">Right</a></li>
              </ul>
              </span> </span>
              <div class="preview">分页</div>
              <div class="view">
                <div name="component21" class="pagination component_container">
                  <ul contenteditable="true">
                    <li><a href="#">Prev</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">Next</a></li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="badge-success">Success</a></li>
                <li class=""><a href="#" rel="badge-warning">Warning</a></li>
                <li class=""><a href="#" rel="badge-important">Important</a></li>
                <li class=""><a href="#" rel="badge-info">Info</a></li>
                <li class=""><a href="#" rel="badge-inverse">Inverse</a></li>
              </ul>
              </span> </span>
              <div class="preview">标签</div>
              <div class="view"> <span name="component22" class="label component_container" contenteditable="true">Label</span> </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="badge-success">Success</a></li>
                <li class=""><a href="#" rel="badge-warning">Warning</a></li>
                <li class=""><a href="#" rel="badge-important">Important</a></li>
                <li class=""><a href="#" rel="badge-info">Info</a></li>
                <li class=""><a href="#" rel="badge-inverse">Inverse</a></li>
              </ul>
              </span> </span>
              <div class="preview">徽章</div>
              <div class="view"> <span name="component23" class="badge component_container" contenteditable="true">1</span> </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="well">Well</a> </span>
              <div class="preview">巨幕</div>
              <div class="view">
                <div name="component24" class="hero-unit component_container" contenteditable="true">
                  <h1>Hello, world!</h1>
                  <p>This is a template for a simple marketing or information website.
				  It includes a large callout called the herop unit and three  supporting pieaces of content. Use iot as starting point to create something more unique</p>
                  <p><a class="btn btn-primary btn-large" href="#">Learn More »</a></p>
                </div>
              </div>
            </div>
			  <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> </span>
              <div class="preview">自定义巨幕</div>
              <div class="view">
              <div name="component25" class="component_container">
                  <div class="header">
        <ul class="nav nav-pills pull-right">
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
        <h3 class="text-muted">Project name</h3>
      </div>

      <div class="jumbotron well">
        <h1>Jumbotron heading</h1>
        <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
        <p><a class="btn btn-lg btn-success" href="#" role="button">Sign up today</a></p>
      </div>

      <div class="row marketing">
        <div class="col-lg-6">
          <h4>Subheading</h4>
          <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>
         </div>

        <div class="col-lg-6">
          <h4>Subheading</h4>
          <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>

        </div>
      </div>
              </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">页头</div>
              <div class="view">
                <div name="component26" class="page-header component_container" contenteditable="true">
                  <h1>Example Page Header<small>Subtext for header</small></h1>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">文本</div>
              <div class="view">
              <div name="component27" class="component_container">
                <h2 contenteditable="true">Header</h2>
                <p contenteditable="true">Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</p>
                <p><a class="btn" href="#" contenteditable="true">View Deatils »</a></p>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">缩略图</div>
              <div class="view">
                <ul name="component28" class="thumbnails component_container">
                  <li class="span4">
                    <div class="thumbnail"> <img alt="300x200" src="/plugin/layoutit/img/people.jpg">
                      <div class="caption" contenteditable="true">
                        <h3>Thumbnail label</h3>
                        <p> Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</p>
                        <p><a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a></p>
                      </div>
                    </div>
                  </li>
                  <li class="span4">
                    <div class="thumbnail"> <img alt="300x200" src="/plugin/layoutit/img/city.jpg">
                      <div class="caption" contenteditable="true">
                        <h3>Thumbnail label</h3>
                        <p> Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</p>
                        <p><a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a></p>
                      </div>
                    </div>
                  </li>
                  <li class="span4">
                    <div class="thumbnail"> <img alt="300x200" src="/plugin/layoutit/img/sports.jpg">
                      <div class="caption" contenteditable="true">
                       <h3>Thumbnail label</h3>
                        <p> Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</p>
                        <p><a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a></p>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Colors<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="progress-info">Info</a></li>
                <li class=""><a href="#" rel="progress-success">Success</a></li>
                <li class=""><a href="#" rel="progress-warning">Warning</a></li>
                <li class=""><a href="#" rel="progress-danger">Danger</a></li>
              </ul>
              </span> <a class="btn btn-mini" href="#" rel="progress-striped">Striped</a> <a class="btn btn-mini" href="#" rel="active">Active</a> </span>
              <div class="preview">进度条</div>
              <div class="view">
                <div name="component29" class="progress component_container">
                  <div class="bar" style="width: 60%;"></div>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="well">Well</a> </span>
              <div class="preview">媒体对象</div>
              <div class="view">
                <div name="component30" class="media component_container"> <a href="#" class="pull-left"> <img src="/plugin/layoutit/img/a_002.jpg" class="media-object"> </a>
                  <div class="media-body" contenteditable="true">
                    <h4 class="media-heading">Nested Media Header</h4>
                   Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</div>
                </div>
              </div>
            </div>
	<div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
	<div class="preview">列表组</div>
	<div class="view">
		<div name="component31" class="list-group component_container" contenteditable="true">
			<a href="#" class="list-group-item active">Home</a>
      <div class="list-group-item">List header</div>
			<div class="list-group-item">
        <h4 class="list-group-item-heading">List group item heading</h4>
        <p class="list-group-item-text">...</p>
      </div>
			<div class="list-group-item"><span class="badge">14</span>Help</div>
			<a class="list-group-item active"><span class="badge">14</span>Help</a>
		</div>
	</div>
</div>
	
	 <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="panel-default">Default</a></li>
				<li class="" ><a href="#" rel="panel-primary">Primary</a></li>
				<li class="" ><a href="#" rel="panel-success">Success</a></li>
				<li class="" ><a href="#" rel="panel-info">Info</a></li>
				<li class="" ><a href="#" rel="panel-warning">Warning</a></li>
				<li class="" ><a href="#" rel="panel-danger">Danger</a></li>
              </ul>
              </span> </span>
	<div class="preview">面版</div>
	<div class="view">
		<div name="component32" class="panel panel-default component_container">
      <div class="panel-heading">
        <h3 class="panel-title" contenteditable="true">Panel title</h3>
      </div>
      <div class="panel-body" contenteditable="true">
        Panel content
      </div>
      <div class="panel-footer" contenteditable="true">
        Panel footer
      </div>
    </div>
	</div>
</div>
 <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">图标 </div>
              <div class="view">
                <ul name="component33" class="glyphicons component_container">
      <li>
        <span class="glyphicon glyphicon-adjust"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-adjust</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-align-center"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-align-center</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-align-justify"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-align-justify</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-align-left"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-align-left</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-align-right"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-align-right</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-arrow-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-arrow-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-arrow-left"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-arrow-left</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-arrow-right"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-arrow-right</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-arrow-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-arrow-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-asterisk"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-asterisk</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-backward"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-backward</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-ban-circle"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-ban-circle</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-barcode"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-barcode</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-bell"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-bell</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-bold"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-bold</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-book"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-book</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-bookmark"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-bookmark</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-briefcase"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-briefcase</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-bullhorn"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-bullhorn</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-calendar"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-calendar</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-camera"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-camera</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-certificate"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-certificate</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-check"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-check</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-chevron-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-chevron-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-chevron-left"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-chevron-left</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-chevron-right"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-chevron-right</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-chevron-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-chevron-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-circle-arrow-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-circle-arrow-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-circle-arrow-left"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-circle-arrow-left</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-circle-arrow-right"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-circle-arrow-right</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-circle-arrow-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-circle-arrow-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-cloud"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-cloud</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-cloud-download"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-cloud-download</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-cloud-upload"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-cloud-upload</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-cog"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-cog</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-collapse-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-collapse-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-collapse-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-collapse-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-comment"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-comment</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-compressed"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-compressed</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-copyright-mark"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-copyright-mark</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-credit-card"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-credit-card</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-cutlery"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-cutlery</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-dashboard"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-dashboard</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-download"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-download</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-download-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-download-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-earphone"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-earphone</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-edit"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-edit</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-eject"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-eject</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-envelope"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-envelope</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-euro"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-euro</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-exclamation-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-exclamation-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-expand"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-expand</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-export"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-export</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-eye-close"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-eye-close</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-eye-open"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-eye-open</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-facetime-video"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-facetime-video</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-fast-backward"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-fast-backward</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-fast-forward"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-fast-forward</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-file"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-file</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-film"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-film</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-filter"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-filter</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-fire"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-fire</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-flag"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-flag</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-flash"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-flash</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-floppy-disk"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-floppy-disk</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-floppy-open"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-floppy-open</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-floppy-remove"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-floppy-remove</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-floppy-save"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-floppy-save</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-floppy-saved"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-floppy-saved</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-folder-close"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-folder-close</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-folder-open"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-folder-open</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-font"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-font</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-forward"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-forward</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-fullscreen"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-fullscreen</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-gbp"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-gbp</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-gift"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-gift</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-glass"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-glass</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-globe"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-globe</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-hand-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-hand-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-hand-left"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-hand-left</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-hand-right"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-hand-right</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-hand-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-hand-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-hd-video"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-hd-video</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-hdd"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-hdd</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-header"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-header</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-headphones"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-headphones</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-heart"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-heart</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-heart-empty"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-heart-empty</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-home"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-home</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-import"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-import</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-inbox"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-inbox</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-indent-left"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-indent-left</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-indent-right"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-indent-right</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-info-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-info-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-italic"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-italic</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-leaf"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-leaf</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-link"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-link</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-list"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-list</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-list-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-list-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-lock"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-lock</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-log-in"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-log-in</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-log-out"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-log-out</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-magnet"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-magnet</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-map-marker"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-map-marker</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-minus"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-minus</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-minus-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-minus-sign</span>
      </li>
      <li>
        <span class="icon-move"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-move</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-music"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-music</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-new-window"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-new-window</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-off"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-off</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-ok"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-ok</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-ok-circle"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-ok-circle</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-ok-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-ok-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-open"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-open</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-paperclip"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-paperclip</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-pause"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-pause</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-pencil"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-pencil</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-phone"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-phone</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-phone-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-phone-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-picture"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-picture</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-plane"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-plane</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-play"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-play</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-play-circle"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-play-circle</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-plus"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-plus</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-plus-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-plus-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-print"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-print</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-pushpin"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-pushpin</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-qrcode"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-qrcode</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-question-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-question-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-random"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-random</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-record"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-record</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-refresh"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-refresh</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-registration-mark"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-registration-mark</span>
      </li>
      <li>
        <span class="icon-remove icon-white"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-remove</span>
      </li>
      <li>
        <span class="icon-remove icon-white-circle"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-remove-circle</span>
      </li>
      <li>
        <span class="icon-remove icon-white-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-remove-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-repeat"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-repeat</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-resize-full"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-resize-full</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-resize-horizontal"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-resize-horizontal</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-resize-small"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-resize-small</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-resize-vertical"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-resize-vertical</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-retweet"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-retweet</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-road"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-road</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-save"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-save</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-saved"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-saved</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-screenshot"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-screenshot</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sd-video"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sd-video</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-search"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-search</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-send"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-send</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-share"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-share</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-share-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-share-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-shopping-cart"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-shopping-cart</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-signal"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-signal</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort-by-alphabet"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort-by-alphabet</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort-by-alphabet-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort-by-alphabet-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort-by-attributes"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort-by-attributes</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort-by-attributes-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort-by-order"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort-by-order</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sort-by-order-alt"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sort-by-order-alt</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sound-5-1"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sound-5-1</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sound-6-1"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sound-6-1</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sound-7-1"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sound-7-1</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sound-dolby"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sound-dolby</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-sound-stereo"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-sound-stereo</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-star"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-star</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-star-empty"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-star-empty</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-stats"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-stats</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-step-backward"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-step-backward</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-step-forward"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-step-forward</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-stop"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-stop</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-subtitles"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-subtitles</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tag"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tag</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tags"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tags</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tasks"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tasks</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-text-height"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-text-height</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-text-width"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-text-width</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-th"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-th</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-th-large"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-th-large</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-th-list"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-th-list</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-thumbs-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-thumbs-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-thumbs-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-thumbs-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-time"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-time</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tint"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tint</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tower"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tower</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-transfer"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-transfer</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-trash"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-trash</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tree-conifer"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tree-conifer</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-tree-deciduous"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-tree-deciduous</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-unchecked"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-unchecked</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-upload"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-upload</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-usd"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-usd</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-user"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-user</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-volume-down"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-volume-down</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-volume-off"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-volume-off</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-volume-up"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-volume-up</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-warning-sign"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-warning-sign</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-wrench"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-wrench</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-zoom-in"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-zoom-in</span>
      </li>
      <li>
        <span class="glyphicon glyphicon-zoom-out"></span>
        <span class="glyphicon-class">.glyphicon .glyphicon-zoom-out</span>
      </li>
    </ul>
              </div>
            </div>
          </li>
        </ul>
        <ul class="nav nav-list accordion-group">
          <li class="nav-header"><i class="icon-plus icon-white"></i> JS交互组件
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">Help</h3>
                <div class="popover-content">DRAG & DROP THE ELEMENTS INSIDE THE COLUMNS WHERE YOU WANT TO INSERT IT. AND FROM THERE, YOU CAN CONFIGURE THE STYLE OF THAT JAVASCRIPT. IF YOU NEED MORE INFO PLEASE VISIT <a target="_blank" href="http://twitter.github.io/bootstrap/javascript.html">JAVASCRIPT.</a></div>
              </div>
            </div>
			
          </li>
          <li style="display: none;" class="boxes mute" id="elmJS">
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <div class="preview">模态窗口</div>
              <div class="view"> 
                <!-- Button to trigger modal --> 
                <div name="component34" class="component_container">
                <a id="myModalLink" href="#myModalContainer" role="button" class="btn" data-toggle="modal" contenteditable="true">Launch Demo Modal</a> 
                
                <!-- Modal -->
                <div id="myModalContainer" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel" contenteditable="true">Title</h3>
                  </div>
                  <div class="modal-body">
                    <p contenteditable="true"> Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</p>
                  </div>
                  <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true" contenteditable="true">Cancel</button>
                    <button class="btn btn-primary" contenteditable="true">Save Changes</button>
                  </div>
                </div>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <a class="btn btn-mini" href="#" rel="navbar-inverse">Inverse</a> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Position <span class="caret"></span></a>
              <ul class="dropdown-menu">
                  <li class="active"><a href="#" rel="">Default</a></li>
				    <li class=""><a href="#" rel="navbar-static-top">Static to Top</a></li>
                  <li class=""><a href="#" rel="navbar-fixed-top">Fixed to Top</a></li>
				   <li class=""><a href="#" rel="navbar-fixed-bottom">Fixed to Bottom</a></li>
              </ul>
              </span> </span>
              <div class="preview">导航条</div>
              <div class="view">
               
                <div name="component35" class="navbar component_container">
                  <div class="navbar-inner">
                    <div class="container-fluid"> <a data-target=".navbar-responsive-collapse" data-toggle="collapse" class="btn btn-navbar"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a href="#" class="brand" contenteditable="true">Title</a>
                      <div class="nav-collapse collapse navbar-responsive-collapse">
                        <ul class="nav" contenteditable="true">
                          <li class="active"><a href="#">Home</a></li>
                        
                          <li class="dropdown"> <a data-toggle="dropdown" class="dropdown-toggle" href="#">Dropdown <b class="caret"></b></a>
                           <ul class="dropdown-menu" contenteditable="true">
                             <li><a href="#">Action</a></li>
                             <li><a href="#">Another Action</a></li>
                             <li><a href="#">Something Else here</a></li>
                             <li class="divider"></li>
                             <li class="dropdown-submenu"> <a tabindex="-1" href="#">More Option</a>
                                 <ul class="dropdown-menu">
                                                 <li><a href="#">Action</a></li>
						<li><a href="#">Another Action</a></li>
						<li><a href="#">Something Else here</a></li>
                                 </ul>
                              </li>
                            </ul>
                          </li>
                     
                        </ul>
                        
                      </div>
                      <!-- /.nav-collapse --> 
                    </div>
                  </div>
                  <!-- /navbar-inner --> 
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Position <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="tabs-below">Bottom</a></li>
                <li class=""><a href="#" rel="tabs-left">Left</a></li>
                <li class=""><a href="#" rel="tabs-right">Right</a></li>
              </ul>
              </span> </span>
              <div class="preview">选项卡</div>
              <div class="view">
                <div name="component36" class="tabbable component_container" id="myTabs"> <!-- Only required for left/right tabs -->
                  <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab1" data-toggle="tab" contenteditable="true">Section 1</a></li>
                    <li><a href="#tab2" data-toggle="tab" contenteditable="true">Section 2</a></li>
                  </ul>
                  <div class="tab-content">
                    <div class="tab-pane active" id="tab1" contenteditable="true">
                      <p>Bacon ipsum dolor sit amet doner ham leberkas short loin hamburger, flank drumstick corned beef. Doner meatball venison bresaola biltong chicken. Turkey bacon shoulder strip steak spare ribs tri-tip. Rump ground round strip steak kielbasa short loin t-bone. Biltong capicola corned beef, ribeye chuck andouille sausage ham hock turkey spare ribs beef tail sirloin shank.</p>
                    </div>
                    <div class="tab-pane" id="tab2" contenteditable="true">
                      <p>Sausage jerky hamburger, andouille salami meatloaf ham hock shank pork corned beef. Boudin spare ribs flank pork loin pork kevin chicken rump cow, ribeye ham tongue. Pork loin jowl filet mignon swine bresaola andouille doner tenderloin ball tip pork. Chicken meatball chuck turkey, jowl ham hamburger salami tenderloin jerky kevin capicola cow andouille. Pig tail pork filet mignon hamburger ham hock. Capicola brisket pork belly, doner cow pastrami corned beef.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">Styles <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li class="active"><a href="#" rel="">Default</a></li>
                <li class=""><a href="#" rel="alert-info">Info</a></li>
                <li class=""><a href="#" rel="alert-error">Error</a></li>
                <li class=""><a href="#" rel="alert-success">Success</a></li>
              </ul>
              </span> </span>
              <div class="preview">警报</div>
              <div class="view">
                  <div name="component37" class="alert component_container" contenteditable="true">
                  <button type="button" class="close" data-dismiss="alert">×</button>
                  <h4>Alert!</h4>
                  <strong>Warning!</strong> Bacon ipsum dolor sit amet ground round culpa elit, irure incididunt short ribs tongue sed. 
                  </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">折叠菜单</div>
              <div class="view">
                <div name="component38" class="accordion component_container" id="myAccordion">
                  <div class="accordion-group">
                    <div class="accordion-heading"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#myAccordion" href="#collapseOne" contenteditable="true">Group Item  #1 </a> </div>
                    <div id="collapseOne" class="accordion-body collapse in">
                      <div class="accordion-inner" contenteditable="true">Bacon ipsum dolor sit amet ground round culpa elit, irure incididunt short ribs tongue sed. Chicken swine cupidatat deserunt. Tongue short ribs bacon bresaola sausage. Rump biltong ribeye tri-tip tenderloin kielbasa. Meatloaf consequat voluptate dolor pork belly t-bone, turducken cow sunt sint.</div>
                    </div>
                  </div>
                  <div class="accordion-group">
                    <div class="accordion-heading"> <a class="accordion-toggle" data-toggle="collapse" data-parent="#myAccordion" href="#collapseTwo" contenteditable="true"> Group Item #2 </a> </div>
                    <div id="collapseTwo" class="accordion-body collapse">
                      <div class="accordion-inner" contenteditable="true"> Flank drumstick culpa ground round mollit enim turducken eu bacon pork chop. Prosciutto short loin est short ribs drumstick pork loin salami cillum hamburger beef excepteur veniam meatloaf. Turducken consectetur jowl occaecat eu pancetta sunt ut pork loin. Non shank boudin frankfurter bresaola.</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box box-element ui-draggable"> <a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i>删除</a> <span class="drag label label-default"><i class="icon-move"></i>拖动</span>
              <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
              <div class="preview">图片轮播</div>
              <div class="view">
                <div name="component39" class="carousel slide component_container" id="myCarousel">
                  <ol class="carousel-indicators">
                    <li class="active" data-slide-to="0" data-target="#myCarousel"></li>
                    <li data-slide-to="1" data-target="#myCarousel" class=""></li>
                    <li data-slide-to="2" data-target="#myCarousel" class=""></li>
                  </ol>
                  <div class="carousel-inner">
                    <div class="item active"> <img alt="" src="/plugin/layoutit/img/1.jpg">
                      <div class="carousel-caption" contenteditable="true">
                        <h4>First Thumbnail Label</h4>
                        <p>Bacon ipsum dolor sit amet ground round culpa elit, irure incididunt short ribs tongue sed. Chicken swine cupidatat deserunt. Tongue short ribs bacon bresaola sausage. Rump biltong ribeye tri-tip tenderloin kielbasa. Meatloaf consequat voluptate dolor pork belly t-bone, turducken cow sunt sint.</p>
                      </div>
                    </div>
                    <div class="item"> <img alt="" src="/plugin/layoutit/img/2.jpg">
                      <div class="carousel-caption" contenteditable="true">
                        <h4>Second Thumbnail Label</h4>
                        <p>Flank drumstick culpa ground round mollit enim turducken eu bacon pork chop. Prosciutto short loin est short ribs drumstick pork loin salami cillum hamburger beef excepteur veniam meatloaf. Turducken consectetur jowl occaecat eu pancetta sunt ut pork loin. Non shank boudin frankfurter bresaola.</p>
                      </div>
                    </div>
                    <div class="item"> <img alt="" src="/plugin/layoutit/img/3.jpg">
                      <div class="carousel-caption" contenteditable="true">
                        <h4>Third Thumbnail Label</h4>
                        <p>Veniam in aute, consequat hamburger mollit nisi leberkas chuck ut prosciutto drumstick short loin frankfurter. Aute salami duis voluptate magna kielbasa swine in. Magna pancetta chuck, aliqua laboris ribeye consectetur jerky prosciutto culpa voluptate brisket sausage bresaola. Jerky ut hamburger tempor, ribeye qui pastrami veniam shoulder.</p>
                      </div>
                    </div>
                  </div>
                   <a data-slide="prev" href="#carousel-965883" class="left carousel-control">&lsaquo;</a> 
      <a data-slide="next" href="#carousel-965883" class="right carousel-control">&rsaquo;</a> </div>
              </div>
            </div>
          </li>
        </ul>
        
        
        <ul class="nav nav-list accordion-group">
	<li class="nav-header"><i class="icon-plus icon-white"></i> 拓展组件
	 <div class="pull-right popover-info"><i class="icon-question-sign"></i> 
	 <div class="popover fade right"><div class="arrow"></div> 
		<h3 class="popover-title">提示：</h3> 
		<div class="popover-content">这里提供了一系拓展组件。</div></div> 
	 </div>
	</li>
	<li class="boxes" >
		<div class="box box-element">
			<a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
			<span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
			<span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
			<div class="preview">网站顶部</div>
			<div class="view">
				<!-- 网站顶部 start -->
				<div name="component40" id="top" class="component_container">
					<div id="topBar">
						<div class="userPanel">
							<ul>
							<li><a href="#">手机版</a></li>
							<li><a href="#">设为首页</a></li>
							<li><a href="#">加入收藏</a></li>
							<li><a href="#">联系我们</a></li>
							</ul>
						</div>
						<div class="welcome">欢迎光临！</div>
					</div>
					<!-- topBar end -->
				</div>
				<!-- 网站顶部 end -->
			</div>
		</div>
		<div class="box box-element">
			<a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
			<span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
			<span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
			<div class="preview">网站LOGO</div>
			<div class="view">
				<!-- 网站LOGO start -->
				<div name="component41" id="header" class="component_container">
					<div class="topAD">专业、专注、专心	</div>
					<div class="logo">标准网站</div>
				</div>
				<!-- 网站LOGO end -->
			</div>
		</div>
		<div class="box box-element">
			<a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
			<span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
           <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>
			
			<div class="preview">通用信息列表</div>
			<div class="view">
				<!-- 通用信息列表 start -->
					<ul name="component42" class="infoList component_container">
						<li>
							<span class="date">01-02</span>
							<a href="#">红木家具保养七绝技</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">何时购买红木家具最合适？</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">红木家具绿色环保</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">选购和定制家具，消费者如…</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">享受古典家具 红木家具难弃爱</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">红木家具保养七绝技</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">何时购买红木家具最合适？</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">红木家具绿色环保</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">选购和定制家具，消费者如…</a>
						</li>
						<li>
							<span class="date">01-02</span>
							<a href="#">享受古典家具 红木家具难弃爱</a>
						</li>
					</ul>
				<!-- 通用信息列表 end -->
			</div>
		</div>
		<div class="box box-element">
			<a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
			<span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
           <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>

			<div class="preview">网站底部</div>
			<div class="view">
				<!-- 网站底部 start -->
				<div name="component43" id="footer" class="component_container">
					<div class="footerNav">
						<a href="#">关于我们</a> |
						<a href="#">服务条款</a> |
						<a href="#">免责声明</a> |
						<a href="#">网站地图</a> |
						<a href="#">联系我们</a>
					</div>
					<div class="copyRight">Copyright ©2010-2014公司名称 版权所有</div>
				</div>
				<!-- 网站底部 end -->
			</div>
		</div>

		<div class="box box-element">
		   <a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
		   <span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
           <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>

			<div class="preview">侧栏导航菜单</div>
			<div class="view">
				<!-- 侧栏导航菜单 start -->
				<div name="component44" class="sideBox component_container" id="sideMenu">
					<div class="hd">
						<h3><span>关于我们</span></h3>
					</div>
					<div class="bd">
						<ul class="menuList">
							<li><a href="#">公司简介</a></li>
							<li><a href="#">企业文化</a></li>
							<li><a href="#">组织结构</a></li>
							<li><a href="#">资质认证</a></li>
							<li><a href="#">联系我们</a></li>
						</ul>
					</div>
				</div>
				<!-- 侧栏导航菜单 end -->
			</div>
		</div>
		
		
		<div class="box box-element ui-draggable">
		   <a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
		   <span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
           <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>

		<div class="preview">文章内容页</div>
		<div class="view">
		    
			<!-- 文章内容页 start -->
			<div name="component45" class="articleContent component_container">
				<h2 class="title">选购和定制家具，消费者如何少花钱？</h2>
				<div class="property">
					<span>文章来源：互联网</span>
					<span>作者：朱春</span>
					<span>发布时间：2013年12月03日</span>
					<span>点击数：25</span>
					<span>【字号：<a class="fontZoomA" href="javascript:fontZoomA();">小</a><a class="fontZoomB" href="javascript:fontZoomB();">大</a>】</span>
				</div>
				<div id="contentTxt">
					
					<p>    五金、建材的价格涨了，家具也要提价了，打算定制家具的业主要如何省钱？一些整体衣柜企业从厂家的角度提出了几条非常实用的建议，消费者不妨看看。</p>
					<p>    第一可以选择到性价比比较高的厂家定制家具。高性价比意味着产品品质有保证，价钱又比较实惠。而且即使涨价，这类品牌的产品也不会涨太多。</p>
					<p>    还有就是可以选择参加厂家举行的团购。一般来说，厂家在团购会提供的产品，价格要比市场价优惠，而且产品质量也有保证。对于定制衣柜等大量家具的消费者来说，通过团购，能节省不少费用。这也是团购会收到热烈欢迎的一个原因。同时，通过参加团购提早签单，也是避免涨价风险的一个好办法。厂家推荐的其他装修省钱之道还有多了解市场行情、在节假日时选购家具、参加网购等。</p>
				</div>
			</div>
			<!-- 文章内容页 end -->
		</div>
	</div>
	
		
		<div class="box box-element">
				<a href="#close" class="remove label label-danger"><i class="icon-remove icon-white"></i> 删除</a>
				<span class="drag label label-default label-default"><i class="icon-move"></i> 拖动</span>
           <span class="configuration"><button type="button" class="btn btn-mini" data-target="#editorModal" role="button" data-toggle="modal">编辑</button></span>

			<div class="preview">文章子列表页</div>
			<div class="view">
				<!-- 文章子列表页 start -->
				<div name="component46" class="box component_container" id="mainBox">
					<div class="mHd">
						<div class="path"><em>您现在位置：</em><a href="index.html" class="home">首页</a>&gt;<a target="_self" href="#">新闻中心</a></div>
						<h3><span>新闻中心</span></h3>
					</div>
					<div class="mBd">
						<ul class="newsList">
							<li>
								<span class="date">01-02</span>
								<a href="#">红木家具保养七绝技</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">何时购买红木家具最合适？</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">红木家具绿色环保</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">选购和定制家具，消费者如…</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">享受古典家具 红木家具难弃爱</a>
							</li>
							<li class="split"></li>
							<li>
								<span class="date">01-02</span>
								<a href="#">红木家具保养七绝技</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">何时购买红木家具最合适？</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">红木家具绿色环保</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">选购和定制家具，消费者如…</a>
							</li>
							<li>
								<span class="date">01-02</span>
								<a href="#">享受古典家具 红木家具难弃爱</a>
							</li>
						</ul>
					</div>
				</div>
				<!-- 文章子列表页 end -->
			</div>
		</div>
	</li>
</ul>
        
           
        
           <ul class="nav nav-list accordion-group">
            <li class="nav-header"><i class="icon-plus icon-white"></i>自定义组件
            <div class="pull-right popover-info"><i class="icon-question-sign "></i>
              <div class="popover fade right">
                <div class="arrow"></div>
                <h3 class="popover-title">提示</h3>
                <div class="popover-content">用户自定义组件是由用户自己设计并保存的组件，您可以将它们拖到布局中使用。</div>
              </div>
            </div>
            </li>
            <li class="boxes" id="selfDefComponent" >
            </li>
           </ul>
        
      </div>
    </div>
    <!--/span-->
    <div class="demo ui-sortable" style="min-height: 304px; ">
      
    

  
  
    </div>
    <!-- end demo -->
    <!--/span-->
	
    <div id="download-layout">
     <!--  <div class="container-fluid"></div> -->
     ${page.html}
    </div>
  </div>
  <!--/row--> 
</div>
<!--/.fluid-container--> 
<div class="modal hide fade" role="dialog" id="editorModal" style="width: auto;">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>编辑组件</h3>
  </div>
  <div class="modal-body">
    <p>
      <textarea id="contenteditor" style="width:800px;height:400px;"></textarea>
    </p>
  </div>
  <div class="modal-footer"><a id="saveToSelfComponent" class="btn btn-primary" >另存为自定义组件</a> <a id="savecontent" class="btn btn-primary" data-dismiss="modal">确定</a> <a class="btn" data-dismiss="modal">取消</a> </div>
</div>
<div class="modal hide fade" role="dialog" id="downloadModal">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>Save</h3>
  </div>
  <div class="modal-body">
    <p>选择保存布局的方式</p>
    <div class="btn-group">
      <button type="button" id="fluidPage" class="active btn btn-info"><i class="icon-fullscreen icon-white"></i> Fluid Page</button>
      <button type="button" class="btn btn-info" id="fixedPage"><i class="icon-screenshot icon-white"></i> Fixed page</button>
    </div>
    <br>
    <br>
    <p>
      <textarea></textarea>
    </p>
  </div>
  <div class="modal-footer"> <a class="btn" data-dismiss="modal" onclick="javascript:savePageLayout();">保存</a> <a class="btn" data-dismiss="modal" onclick="javascript:saveAndPublishPageLayout();">保存并发布</a></div>
</div>


 <div class="modal hide fade" role="dialog" id="saveToSelfComponentModal">
  <div class="modal-header"> <a class="close" data-dismiss="modal">×</a>
    <h3>另存为自定义模块</h3>
  </div>
  <div class="modal-body">
    <div class="container-fluid">
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="组件名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
	  
	        <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="代码" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">备注</span>
             <input type="text" name="memo" class="form-control" placeholder="备注" aria-describedby="sizing-addon2" >
             </div>
            </div>
     </div>
  </div>
  <div class="modal-footer"> <a class="btn"  onclick="addPageComponent();">保存</a> </div>
</div>

</div>



<script>
function resizeCanvas(size)
{

var containerID = document.getElementsByClassName("changeDimension");
var containerDownload = document.getElementById("download-layout").getElementsByClassName("container-fluid")[0];
var row = document.getElementsByClassName("demo ui-sortable");
var container1 = document.getElementsByClassName("container1");
if (size == "md")
{
$(containerID).width('id', "MD");
$(row).attr('id', "MD");
$(container1).attr('id', "MD");
$(containerDownload).attr('id', "MD");
}
if (size == "lg")
{
$(containerID).attr('id', "LG");
$(row).attr('id', "LG");
$(container1).attr('id', "LG");
$(containerDownload).attr('id', "LG");
}
if (size == "sm")
{
$(containerID).attr('id', "SM");
$(row).attr('id', "SM");
$(container1).attr('id', "SM");
$(containerDownload).attr('id', "SM");
}
if (size == "xs")
{
$(containerID).attr('id', "XS");
$(row).attr('id', "XS");
$(container1).attr('id', "XS");
$(containerDownload).attr('id', "XS");

}
}
</script>
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
<input type="hidden"  value="${page.id}" id="pageId">
</body>
</html>

