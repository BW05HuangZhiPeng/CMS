<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">
	<script type="text/javascript">
	function pass(status){
		
		$.post("/article/pass",{status:status,id:'${article.id}'},function(obj){
			if(obj){
				alert("操作成功!")
				$("#content-wrapper").load("/article/checkList")
			}
		})
		
	}
	
	
function hot(status){
		
		$.post("/article/sethot",{status:status,id:'${article.id}'},function(obj){
			if(obj){
				alert("操作成功!")
				$("#content-wrapper").load("/article/checkList")
			}
		})
		
	}
	
	function goBack(){
		$("#content-wrapper").load("/article/checkList")
	}
	
	</script>
</head>
<body>  
<div class="container">

	<button type="button" onclick="pass(2)" class="btn btn-info">通过</button>
	<button type="button" onclick="pass(3)" class="btn btn-warning">不通过</button>
	
	<button type="button" onclick="hot(1)" class="btn btn-info">设置热门</button>
	<button type="button" onclick="hot(0)" class="btn btn-warning">设置不热门</button>
	
	<button type="button" onclick="goBack()" class="btn btn-green">返回</button>
	


		<dl>
			<dt>${article.title }</dt>
			<hr>
			
			<dd>${article.content }</dd>
		</dl>
	
</div>


</body>
</html>