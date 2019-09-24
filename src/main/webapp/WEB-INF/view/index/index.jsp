<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>CMS系统</title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/resource/css/cms.css?v=1.1" />
<style type="text/css">
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/view/common/top.jsp"></jsp:include>

	<div>
		<br />
	</div>
	<div class="container">
		<div class="row">
			<!-- 频道 -->
			<div class="col-md-2 ">
				<ul class="list-group">
					<li class="list-group-item  text-center"><a class="channel"
						href="/">热门</a></li>
					<c:forEach items="${channels}" var="channel">
						<li class="list-group-item text-center">
						<a class="channel"
							data="/getCategories?cid=${channel.id}" href="javascript:void(0)">${channel.name}</a></li>
					</c:forEach>
				</ul>
			</div>

			<!-- 中间内容主体区 -->
			<div class="col-md-7 split min_h_500">

				<!-- 默认显示图片轮播+热点内容 -->
				<div id="carousel" class="carousel slide" data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#carousel" data-slide-to="0" class="active"></li>
						<li data-target="#carousel" data-slide-to="1"></li>
						<li data-target="#carousel" data-slide-to="2"></li>
					</ol>
					<div class="carousel-inner">
						<div class="carousel-item active">
							<img class="d-block w-100" src="/pic/1.jpg" alt="First slide">
						</div>
						<div class="carousel-item">
							<img class="d-block w-100" src="/pic/2.jpg" alt="Second slide">
						</div>
						<div class="carousel-item">
							<img class="d-block w-100" src="/pic/3.jpg" alt="Third slide">
						</div>
					</div>
					<a class="carousel-control-prev" href="#carousel" role="button"
						data-slide="prev"> <span class="carousel-control-prev-icon"
						aria-hidden="true"></span> <span class="sr-only">Previous</span>
					</a> <a class="carousel-control-next" href="#carousel" role="button"
						data-slide="next"> <span class="carousel-control-next-icon"
						aria-hidden="true"></span> <span class="sr-only">Next</span>
					</a>
				</div>
				<br />
				<div id="hot">
					<!-- 新闻热点 -->
					<c:forEach items="${pageInfo.list}" var="c">
						<div class="media">
							<img  width="120"  height="80" class="align-self-start mr-3" src="/pic/${c.picture }"
								alt="no pic">
							<div class="media-body">
								<h5 class="mt-0">
									<a href="javascript:toDetail(${c.id })">${c.title }</a>
								</h5>
								<p class="blog_item_footer">
									<span class="glyphicon glyphicon-user" title="作者"></span>作者：${c.username}&nbsp;&nbsp;
									&nbsp; <span class="glyphicon glyphicon-time" title="发布时间"></span>发布：&nbsp;
									<fmt:formatDate value="${c.created }" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;
								</p>
							</div>
						


						</div>
                      <br/>
					</c:forEach>
					<div>${pageStr}</div>
				</div>
				<!-- 分类 -->
				<div id="category">
				
				</div>




				<br />

				<!-- 文章 -->
				<div id="article">
				
				
				</div>

			</div>

			<div class="col-md-3">
				<div class="card">
					<div class="card-header">最新文章</div>
					<div class="card-body">
						<ol>
							<c:forEach items="${lasts}" var="article">
								<li class="text-truncate"><a href="/article/getDetail?aId=${article.id}">${article.title}</a></li>
							</c:forEach>
						</ol>
					</div>
				</div>
			   <div class="card">
			   <div class="card-header">友情链接</div>
			      <ol>
			    <c:forEach items="${links}" var="l">
			      <li class="text-truncate text-center"><a href="${l.url }" target="_blank">${l.text}</a></li>
			    
			    </c:forEach>
			     </ol>
			   </div>
			
			</div>
		</div>
	</div>

	<br />

	<jsp:include page="/WEB-INF/view/common/footer.jsp" />

	<script type="text/javascript">
		//为左侧频道绑定点击事件
		$(function() {
			$('ul li').click(function() {
				//获取当前默认高亮的属性
				var li = $('ul li.list-group-item-warning');
				//移除目前高亮的样式
				li.removeClass('list-group-item-warning');
			
				//为当前点击行添加高亮的样式
				$(this).addClass('list-group-item-warning');

			});
			<!--当点击左侧菜单时-->
			$('.channel').click(function(e) {
				//获取点击的的url
				var url = $(this).attr('data');
				/* // alert("点击了 " +url ); */
				// console.log(url);
				//隐藏轮播
				$("#carousel").hide();
				//隐藏热点
				$("#hot").hide();
				//让原有的分类数据清空
				$('#category').empty();
				//在中间区域显示点击channel 对应的内容
				$('#category').load(url);
			});
			
			//分页的点击事件
			 
	        $('.page-link').click(function (e) {
	        	
	        	  //获取点击的的url
	            var url = $(this).attr('data');
	           // console.log(url);
	           //在中间区域显示地址的内容
	            $('#hot').load(url);
	        });

		})
		//查看文章明细
		 function toDetail(id){
		
		//	 window.open("/getArticleDetail?id="+id);
		 window.open("/article/getDetail?aId="+id,"_blank")
		 }
		 
	</script>
</body>
</html>