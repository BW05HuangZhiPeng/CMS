<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div id="hot">
		<!-- 新闻热点 -->
		<c:forEach items="${pageInfo.list}" var="c">
			<div class="media">
				<img width="120" height="80" class="align-self-start mr-3"
					src="/pic/${c.picture }" alt="no pic">
				<div class="media-body">
					<h5 class="mt-0">
						<a href="javascript:toDetail(${c.id })">${c.title }</a>
					</h5>
					<p class="blog_item_footer">
						<span class="glyphicon glyphicon-user" title="作者"></span>作者：${c.username}&nbsp;&nbsp;
						&nbsp; <span class="glyphicon glyphicon-time" title="发布时间"></span>发布：&nbsp;
						<fmt:formatDate value="${c.created }" pattern="yyyy-MM-dd" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
				</div>



			</div>
			<br />
		</c:forEach>
		<div>${pageStr}</div>
	</div>

	<script type="text/javascript">
		//分页的点击事件

		$('.page-link').click(function(e) {

			//获取点击的的url
			var url = $(this).attr('data');
			// console.log(url);
			//在中间区域显示地址的内容
			$('#hot').load(url);
		});
	</script>
</body>
</html>