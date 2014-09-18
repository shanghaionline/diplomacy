<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 Test</title>
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
	  <div class="flash">
	  </div>
	</div>
    <#import "../include/top_include.ftl" as topInclude/>
    <@topInclude.channelBar 2/>
	<div class="content">
	  <div class="main">
	  <div class="document">
	  <div class="crump">
    	<p>
    		<a href="#">首页</a>-&gt;
    		<a href="#">工作动态</a>-&gt;
    		<span>文章</span>
    	</p>
      </div>	
  	<div class="doc">
    <h1>${content.title}</h1>
    <h2>
      ${content.created?string}
      来源: ${content.source}
      作者: ${content.author}
    </h2>
    <div class="content" style="text-align:left;">
      ${content.content}
    </div>
    <div class="prvnxt">
    </div>
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


