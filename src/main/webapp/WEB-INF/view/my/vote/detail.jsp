<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>	
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">

</head>
<body>  
<div class="container">


		<form action="" id="form">
			<input type="hidden"  name="articleId" value="${voteArticle.id}">
			<dl>
				<dt>${voteArticle.title }</dt>
					<hr>
				
				<dd> <c:forEach items="${lmap}" var="op">
					<input type="radio" name="option" value="${op.value.optionKey}" >${op.value.optionTitle}
					
					<div class="progress">
					  <div class="progress-bar" role="progressbar" aria-valuenow="${op.value.voteNum}" aria-valuemin="0" aria-valuemax="${op.value.voteNumTotal}" style="width: ${100.0*op.value.voteNum/op.value.voteNumTotal}%;">
					    <span class="sr-only">${100.0*op.value.voteNum/op.value.voteNumTotal}% Complete</span>
					  </div>
					</div>
					
					<br/>
					
				 </c:forEach> </dd>
				 <dd> <input type="button" onclick="vote()" value="投票"> </dd>
			</dl>
		</form>
		<script type="text/javascript">
			function vote(){
				var option=$("[name=option]:checked");
				if (option.length<=0) {
					alert("抱歉您还没有选择")
					return ;
				}
				var data = $("#form").serialize();
				$.post("/vote/vote",data,function(obj){
					if(obj){
						alert("恭喜您，投票成功了");
						history.go(0)
					}else{
						alert("很遺憾，投票失敗，請稍後再試");
					}
					
				})
				
			}
		</script>
	

</div>


</body>
</html>