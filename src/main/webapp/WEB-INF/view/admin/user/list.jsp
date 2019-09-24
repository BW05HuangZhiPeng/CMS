<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户列表</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script type="text/javascript">

function query(){
	 //在中间区域显示地址的内容
    $('#content-wrapper').load("/user/list?name="+$("[name='username']").val());
}

</script>
</head>
<body>

	<div class="container-fluid">

		<div class="form-inline">
			<label for="username">用户名:</label> <input id="username" type="text"
				name="username" value="${username }" class="form-control ">
			<button type="button" class="btn btn-info" onclick="query()">查询</button>
		</div>

		<table class="table table-sm  table-hover table-bordered ">
			<thead class="thead-light">
				<tr align="center">
					<td>序号</td>
					<td>姓名</td>
					<td>当前状态</td>
					<td>性别</td>
					<!-- <td>出生日期</td> -->
					<td>注册日期</td>
					<td>更新日期</td>
				</tr>
			</thead>
			<c:forEach items="${pageuser.list}" var="u" varStatus="index">
				<tr align="center">
					<td>${index.index+1 }</td>
					<td>${u.username}</td>
					<td><button type="button" class="btn btn-success" onclick="moption('${u.id}',this)">${u.locked=="0"?"正常":"禁用"}</button></td>
					
					<td>${u.gender==0?"女":"男"}</td>
					<%-- <td>${u.birthday}</td> --%>
					<td>
					 	<fmt:formatDate  value="${u.createTime}" />
					 	<%-- <c:if test="${u.createTime!=null}"></c:if> --%>
					 </td>
					<td><fmt:formatDate  value="${u.updateTime}" /></td>
				</tr>

			</c:forEach>

		</table>
		<div>
		  ${pageStr}
		</div>
	</div>

<script type="text/javascript">



function moption(userid,obj){
	
	$.ajax({
		type:"post",
		data:{id:userid,locked:$(obj).text()=="正常"?1:0},
		url:"/user/update",
		success:function(flag){
		  	if(flag){
			 $(obj).text($(obj).text()=="正常"?"禁用":"正常");
		  	}
		}
		
	}) 
}


</script>
</body>
</html>