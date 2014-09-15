<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 工作动态</title>
   <link rel="stylesheet" media="screen"
          href="${requestContext.contextPath}/static/stylesheets/main.css">
    <link rel="shortcut icon" type="image/png"
          href="${requestContext.contextPath}/static/images/favicon.png">
    <link rel="stylesheet" media="screen"
          href="${requestContext.contextPath}/static/stylesheets/style.css">
    <link rel="stylesheet" media="screen"
          href="${requestContext.contextPath}/static/stylesheets/article.css">
    <script type="text/javascript"
            src="${requestContext.contextPath}/static/javascripts/jquery-1.9.0.min.js">
    </script>
  </head>
  <body>
    <div class="bg">
      <div class="page">
		<div class="head">
	  		<div class="flash"></div>
		</div>
		<#include "../include/top_include.ftl"/>
		<div class="content">
			<div class="main">
				<div class="document">
				<div class="crump">
				<p><a href="#">首页</a>-&gt;<span>工作动态</span></p>
			</div>
			<div align="left">
			<form action="${requestContext.contextPath}/content/list" method="POST">
			<input type="textfield" name="query" value="${query!}" class="ipt_3"/>
			<input type="submit" value="" class="btn_search"/>
			</form>
		</div>
		<div class="doc">
      
      <#list content.list as item>
      <div class="article-list-div" style="text-align:left;">
      <div class="article-title-div">
      <a href="${requestContext.contextPath}/content/show-content/${item.id}">${item.title}</a>
      </div>
      <div class="article-summary-div">${item.summary}</div>
      <div class="article-attr-div">
		<span>${item.created?string}</span>
		<span>来源: ${item.source}</span>
		<span>作者: ${item.author}</span>
      </div>
    </div>
    </#list>
  </div>
  
  <div>
  	<#list 1..content.page as pg>
    <a href="@{requestContext.contextPath}/content/content${pg}">${pg}</a>
    </#list>
  </div>
</div>
<#include "../include/right_include.ftl">
	  </div>
	  <div class="bottom">
	    <p>上海公共外交协会 版权所有 | 网络技术支持：
	      <a href="http://www.online.sh.cn" target="_blank">上海热线</a>
	    </p>
	    <p>
	      <a href="" target="_blank">内容纠错</a> | 
	      <a href="" target="_blank">网站法律顾问</a>
	    </p>
	  </div>
	</div>
      </div>
    </div>
  </body>
</html>


