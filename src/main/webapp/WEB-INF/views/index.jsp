<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>
<script type="text/javascript" src="${contextPath}/webjarslocator/jquery/jquery.js"></script>
<script type="text/javascript" src="${contextPath}${urls.getForLookupPath('/js/common.js')}"></script>

<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js?v=1.0.1"></script> --%>

</head>
<body>

	<a href="${contextPath}/success">success返回的是一个jsp试图页面</a>
	<br />
	<a href="${contextPath}/json" id="json">返回json</a>
	<br />
	<a href="${contextPath}/student/findAll">student find all jdbcTemplete</a>
	<br/>
	<a href="${contextPath}/student/findAllJpa">student find all by jpa</a>
	<br/>
	<a href="${contextPath}/student/likename">student find name like '小' mybatis</a>
	<br/>
	<a href="${contextPath}/student/likename2.json">student find name like DefaultDataSource '小' mybatis</a>
	
	
	<button id="testJsonp"> testJsonp</button>
	
	<div id="show">
	
	</div>
	
	<script type="text/javascript">
		$(function(){
			test();
			var value ="";
			 $("#testJsonp").click(function(){ 
		         $.ajax({ 
		            type: "get", 
		            url:'http://localhost:8080/test-boot/testJson.json?name=Tom&name1=Lily', 
		           
		            async: false, 
		            dataType: "jsonp", 
		            jsonp:"callback", //服务端用于接收callback调用的function名的参数(请使用callback或jsonp)
		            jsonpCallback:"success_callback", //callback的function名称
		            success: function(json) { 
		               value = json.name;
		            },
		            error:function(){
		                value = "rquest Error";
		            } 
		        }); 
		         
		        $("#show").html(value); 
		    });    
			
		});
	</script>

</body>
</html>