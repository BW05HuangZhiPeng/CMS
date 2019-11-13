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
<script src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">

		<div>
			文章状态 <select class="form-control-sm">
				<option value="0">全部</option>
				<option value="1">待审核</option>
				<option value="2">已审核</option>
				<option value="3">审核未通过</option>
			</select>

		</div>
		<table class="table table-sm  table-hover table-bordered ">
			<thead class="thead-light">
				<tr align="center">
					<td>序号</td>
					<td>作者</td>
					<td>标题</td>
					<td>当前状态</td>
					<td>发布时间</td>
					<td>栏目</td>
					<td>分类</td>
					<td>操作</td>
				</tr>
			</thead>
			<c:forEach items="${articles.list}" var="article" varStatus="index">
				<tr align="center">
					<td>${index.index+1 }</td>
					<td>${article.username}</td>
					<td>${article.title}</td>
					<td>${article.status==1?"待审核":article.status==2?"已审核":"未通过" }</td>
					<td><fmt:formatDate value="${article.created}"
							pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
					<td>${article.chnName}</td>
					<td>${article.catName}</td>
					<td><button type="button" class="btn btn-info"
							onclick="toDetail(${article.id})">详情</button></td>
				</tr>

			</c:forEach>

		</table>
		<div>${pageStr }</div>
	</div>
	
	
	<script type="text/javascript">
	
		$(function(){
		
		
			//下拉框回显
			$(".form-control-sm").val('${param.status }')
			
			$(".form-control-sm").change(function(){
			
				$("#content-wrapper").load("/article/checkList?status="+$(this).val())
			})
			
		})


		//查看文章详情
		function toDetail(id){
			$("#content-wrapper").load("/article/get?id="+id)
		
		}
		$(".page-link").click(function() {
			//alert("点击了全部")
			//获取点击的的url
			var url = $(this).attr('data');
			// console.log(url);
			//在中间区域显示地址的内容
			$('#container-fluid').load(url);
		})
	</script>
</body>
</html>