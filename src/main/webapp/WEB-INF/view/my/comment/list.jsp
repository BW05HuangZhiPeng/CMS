<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">

function myopen(id){
	// alert(id)
	window.open("/article/getDetail?aId="+id,"_blank");
	
}
function toDel(cid) {
	$.ajax({url:"/commnent/del",
		  data:{id:cid},
		  method:"post",
		  success:function(data){
			  $('#center').load("/commnent/getmylist"); 
		  }	
	})
}
</script>
</head>
<body>

	<c:forEach items="${commenPage.list}" var="comment">
		<dl>
			<dt>文章标题：<a href="javascript:myopen(${comment.articleId })">${comment.articleTitle }</a></dt>
			<dt>评论内容：${comment.content}</dt>
			<dd>发布时间:
			  <fmt:formatDate value="${comment.created}"/>
			    <a href="javascript:toDel(${comment.id })">删除</a>
			</dd>
		</dl>
		<hr>
	</c:forEach>
	${pageStr}
</body>

<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	
	    	  //获取点击的的url
	        var url = $(this).attr('data');
	        // alert(url);
	       //在中间区域显示地址的内容
	       $('#center').load(url);
	    });
		
	})
	
</script>
</html>