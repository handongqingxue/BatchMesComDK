<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<%=basePath %>resource/js/jquery-3.3.1.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
var path='<%=basePath %>';
$(function(){
	//getItem();
});

function getItem(){
	var item=$("#item_sel").val();
	$.post(path+"batch/getItem",
		{item:item},
		function(result){
			console.log("result==="+result);
		}
	,"json");
}
</script>
</head>
<body>
<select id="item_sel">
	<option value="UnitsList">UnitsList</option>
	<option value="BadTagCount">BadTagCount</option>
	<option value="Batchlist">Batchlist</option>
	<option value="BatchListCt">BatchListCt</option>
</select>
<input type="button" value="send" onclick="getItem()"/>
</body>
</html>