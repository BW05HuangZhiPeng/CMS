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
function toUpdate(id) {
	$('#center').load("/article/toUpdate?id="+id);
}
</script>
</head>
<body>

	<c:forEach items="${myarticles.list}" var="article">
		<dl>
			<dt><a href="javascript:myopen(${article.id })">${article.title }</a></dt>
			<dd>作者:${sessionScope.USER_SESSION_KEY.username} 发布时间:
			  <fmt:formatDate value="${article.created}"/>
				频道:${article.chnName}  分类:${article.catName}
			    <a href="javascript:toUpdate(${article.id })">修改</a>
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