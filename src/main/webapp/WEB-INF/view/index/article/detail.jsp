<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文章详情</title>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/resource/css/cms.css?v=1.1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resource/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/resource/kindeditor/lang/zh-CN.js" ></script>
<script>
        KindEditor.ready(function(K) {
        	window.editor = K.create('#editor_id',{
                afterBlur:function(){
                   this.sync();
                }
             });

               // var editor1 = K.create('#editor_id');
        });
</script>
</head>
<body>  
 <div class="container">
<table>
   <tbody>
      <tr>
         <td>
          
            <dl>
		   <dt><a href="javascript:window.close();history.back()">关闭窗口</a></dt>
			<dt>${article.title }</dt>
				<hr>
			
			<dd>${article.content }</dd>
			<p>
			<c:if test="${mes=='only page' }">
				<a href="javascript:void()">此频道只有一篇文章</a><br>
			</c:if>
			<c:if test="${mes=='this is one page' }">
				<a href="javascript:void()">上一篇文章：已经是第一篇文章了</a><br>
				<a href="/article/getDetail?aId=${nextArticle.id }">下一篇文章：${nextArticle.title }</a>
			</c:if>
			<c:if test="${mes=='this is last page' }">
				<a href="/article/getDetail?aId=${lastArticle.id }">上一篇文章：${lastArticle.title }</a><br>
				<a href="javascript:void()">下一篇文章：此文章是最后一篇</a>
			</c:if>
			<c:if test="${mes=='it is ok' }">
				<a href="/article/getDetail?aId=${lastArticle.id }">上一篇文章：${lastArticle.title }</a><br>
				<a href="/article/getDetail?aId=${nextArticle.id }">下一篇文章：${nextArticle.title }</a>
			</c:if>
			
			</p>
			<dd><div>
				<form action="">
					<textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
					<input type="button" value="评论" onclick="commnent()">
				</form>
			</div>
				<hr>
				评论数量：${article.commentCnt }
			</dd>
			<dd><div id="commentList"></div></dd>
			
		</dl>
	 
	 
         </td>
         
         
        <td>
          <div class="col-md-3" style="width:1520px;">
				<div class="card">
					<div class="card-header">评论排行榜</div>
					<div class="card-body">
						<ol>
							<c:forEach items="${CommentDesc}" var="article">
								<li class="text-truncate"><a href="/article/getDetail?aId=${article.id}">${article.title}</a></li>
							</c:forEach>
						</ol>
					</div>
				</div>
	     </div>
        </td>
      </tr>
   </tbody>

</table>
</div>
<script type="text/javascript">



	
	$(function(){
		$("#commentList").load("/commnent/getlist?articleId=${article.id}" );
	});
	
	function commnent(){
		
		var retext=$("[name='content']").val();
		//alert(retext)
		var id=${article.id}
		//alert(id)
		if(retext!=""){
		$.ajax({ 
			type:"post",
			data:{content:retext,articleId:id},
			url:"/commnent/post",
			success:function(msg){
				if(msg=="success"){ 
					alert("发表成功") 
					$("#commentList").load("/commnent/getlist?articleId=${article.id}" );
					history.go(0)
					//location.href="getDetail" 
				}else{
					if (msg=="You are not logged in and cannot comment") {
						 alert("请登录后再发布评论");
					}else{
						alert(msg);
					}
					
				}
			}
		})
		}else{
			alert("请输入评论内容")
		}
	}
</script>


</body>
</html>