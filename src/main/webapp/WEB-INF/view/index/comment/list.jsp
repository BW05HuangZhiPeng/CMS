<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<ul class="nav">
	

	<ul class="list-unstyled">
		<hr>
	   <!-- 栏目下所有文章 -->
		<c:forEach items="${commenPage.list }" var="a">
			<li class="media">
				<div class="media-body">
					<h5 class="mt-0 mb-1"><small>${a.content }</small></h5>
					<br>
					<br>
					<h5 class="mt-0 mb-1"><small> ${a.userName } &nbsp;  <fmt:formatDate value="${a.created }" pattern="yyyy-MM-dd"/> </small></h5>
				</div>
				</li>
			<hr>
		</c:forEach>
	</ul>
	<hr>
		
	
	</ul>
</div>
<div align="center">
	${pagestr}
</div>

	<script type="text/javascript">
	 function myopen(id){
		 //在新窗口打开文章的详情J
		 window.open("/article/getDetail?aId="+id,"_blank")
	 }
	 

	 $('.page-link').click(function (e) {
	 	  //获取点击的的url
		 var url = $(this).attr('data');
		 //在中间区域显示地址的内容
		 $('#commentList').load(url);
	 });
	</script>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>