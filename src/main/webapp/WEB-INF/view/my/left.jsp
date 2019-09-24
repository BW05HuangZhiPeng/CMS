<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="avatar">
	<img alt="" src="/images/default_avatar.png" class="img-thumbnail">
</div>
<br />
<div>
	<div class="list-group">
		<ul class="list-group">
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)"  data="/article/listMyArticle">我的文章
			</a></li>
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" data="/article/toPublish"
				class="list-group-item">发布文章</a></li>
				
			<!-- 我的投票 开始-->	
		    <li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)"  data="/vote/list">我的投票
			</a></li>
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" data="/vote/push"
				class="list-group-item">发布投票</a></li>
			<!-- 我的投票 结束-->		
				
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" class="list-group-item" data="/commnent/getmylist">我的评论</a></li>
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" class="list-group-item">上传头像</a></li>
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" class="list-group-item">个人设置</a></li>
				<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" data="/toCollectList" class="list-group-item">个人收藏</a></li>
			<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" data="/blog/toBlog" class="list-group-item">写博客</a></li>
		
		<li class="list-group-item  text-center"><a class="channel"
				href="javascript:void(0)" data="/blog/toList" class="list-group-item">博客管理</a></li>
		
		
		
		</ul>
	</div>
</div>
  <script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="/resource/js/cms.js"></script>