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

  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 
 </head>
   <body >
 
<div class="container-fluid">
   
	   <br>
    <div class="row-fluid">
     <div class="col-md-12">
    <div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">
        
        <c:if test="${t!=null}">
        ${t.name}
        </c:if>
        </h3>
      </div>
      <div class="panel-body" >
        <div id="question_container" role="form">
                    <!--            <div class="form-group">
                                    <div class="col-md-9">
                                       <h4 class="m-t-0">Input Sizing</h4>
                                    </div>
                                </div>
                              <div class="form-group">
                                   
                                    <div class="col-md-9">
                                        <div class="checkbox">
                                            <label>
                                                <input value="" type="checkbox">
                                                Checkbox Label 1
                                            </label>
                                        </div>
                                        <div class="checkbox">
                                            <label>
                                                <input value="" type="checkbox">
                                                Checkbox Label 2
                                            </label>
                                        </div>
                                    </div>
                                </div> -->
      
       </div>
      
      </div>
       <div class="panel-footer">
        <button type="button" class="btn btn-primary" onclick="doSave();" >保存</button>
       </div>
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
   
  <input  type="hidden" id="surveyId" value="${t.id}"/> 
  <input  type="hidden" id="surveyCode" value="${t.code}"/> 
<c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
<script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/survey/survey_detail.js' />"></script>
   
   </body>
</html>
