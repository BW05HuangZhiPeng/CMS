<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">

function myopen(id){
	// alert(id)
	window.open("/vote/getVote?arId="+id,"_blank");
	
}

</script>
</head>
<body>

	<c:forEach items="${list}" var="article">
		<dl>
			<dt><a href="javascript:myopen(${article.id })">${article.title }</a></dt>
		</dl>
		<hr>
	</c:forEach>
	


</body>
<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	
	    	
	    	  //获取点击的的url
	        var url = $(this).attr('data');
	        alert(url);
	    
	       //在中间区域显示地址的内容
	       $('#center').load(url);
	    });
		
	})
</script>
</html>