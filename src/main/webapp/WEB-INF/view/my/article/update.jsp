<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String htmlData = request.getAttribute("content1") != null ? (String)request.getAttribute("content1") : "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文章发布</title>
<script>
		KindEditor.ready(function(K) {
			window.editor1 = K.create('textarea[name="content1"]', {
				cssPath : '/resource/kindeditor/plugins/code/prettify.css',
				uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
		function query(){
		alert(editor1.html())
			//alert( $("[name='content1']").attr("src"))
		} 
	</script>
</head>
<body>
	<form action="" id="form">
		<input type="hidden" value="${article.id}" name="id">
		<div class="form-group row ">
			<label for="title">文章标题</label> <input type="text"
				class="form-control" id="title" value="${article.title}" name="title" placeholder="请输入标题">
		</div>




		<div class="form-group row ">
			<textarea name="content1" cols="100" rows="8"
				style="width: 860px; height: 250px; visibility: hidden;" ><%=htmlspecialchars(htmlData)%></textarea>
			<br />
		</div>
		<div class="form-group row ">
			<label for="title">文章标题图片</label> <input type="file"
				class="form-control" id="file" name="file">
		</div>
		<div class="form-group row ">
		  	<label for="channel">文章栏目</label> 
			<select class="custom-select custom-select-sm mb-3" id="channel"  name="channelId">
			  <option></option>
			</select>
			<label for="category">文章分类</label> 
			<select class="custom-select custom-select-sm mb-3" id="category" name="categoryId">
			</select>
		</div>
		
		<div class="form-group row" >
		<button type="button" class="btn btn-success" onclick="publish()">修改</button>
		
		</div>
	</form>












</body>




<script type="text/javascript">
//发布文章
function publish(){
	
	
		//序列化表单数据带文件
		 var formData = new FormData($( "#form" )[0]);
		//改变formData的值
		//editor1.html() 是富文本的内容
		 formData.set("content1",editor1.html());
		
		$.ajax({
			type:"post",
			data:formData,
			// 告诉jQuery不要去处理发送的数据
			processData : false,
			// 告诉jQuery不要去设置Content-Type请求头
			contentType : false,
			url:"/article/update",
			success:function(obj){
				if(obj)
			    {
					alert("修改成功!");
					// location="/article/listMyArticle";
					$("#center").load("/article/listMyArticle")
				}else{
					alert("修改失败")
				}
				
			}
		})
	}
	
$(function(){

	
	
	//自动加载文章的栏目
	$.ajax({
		type:"get",
		url:"/article/getAllChn",
		success:function(list){
			$("#channel").empty();
			for(var i in list){
				var a='${article.channelId}';
				if (a==list[i].id) {
			      $("#channel").append("<option selected='selected' value='"+list[i].id+"'>"+list[i].name+"</option>")
				}else{ 
				  $("#channel").append("<option value='"+list[i].id+"'>"+list[i].name+"</option>")
				}
			}
			
		 	var cid=$("#channel").val();
			$.get("/article/getCatsByChn",{channelId:cid},function(list){
				
				 for(var i in list){
					 var b='${article.categoryId}';
					
					if (b==list[i].id) {
						$("#category").append("<option selected='selected' value='"+list[i].id+"'>"+list[i].name+"</option>")
					}else{  
				      $("#category").append("<option value='"+list[i].id+"'>"+list[i].name+"</option>")
					}

				 }
				 
			 }) 
		}
		
	})
	
	
	
	//为栏目添加绑定事件
	$("#channel").change(function(){
		 
	var cid =$(this).val();//获取当前的下拉框的id
	//根据ID 获取栏目下的分类
	 $.get("/article/getCatsByChn",{channelId:cid},function(list){
		//先清空原有的栏目下的分类
		 $("#category").empty();
		 for(var i in list){
		  $("#category").append("<option value='"+list[i].id+"'>"+list[i].name+"</option>")
		 }
		 
	 })
	})
})
	


</script>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>
</html>