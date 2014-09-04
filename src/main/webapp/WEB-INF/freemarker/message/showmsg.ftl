<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 查看消息</title>
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
	<#include "../include/top_include.ftl"/>
	<div class="content">
	  <div class="main">
<div class="login_left">
<table width="100%" border="0">
  <tr>
    <td width="15%" align="left">发件人</td>
    <td align="left">${message.sender.login}</td>
  </tr>
  <tr>
    <td align="left">收件人</td>
    <td align="left">${message.receiver.login}</td>
  </tr>
  <tr>
    <td align="left">标题</td>
    <td align="left">${message.title}</td>
  </tr>
  <tr>
    <td align="left">发送时间</td>
    <td align="left">${message.created?string}</td>
  </tr>
  <tr>
    <td align="left">内容</td>
    <td align="left">${message.content}</td>
  </tr>
  
  <tr>
    <td align="left">附件</td>
    <td align="left">
      <a href="/association/msg/down-attachment/8">中文附件名测试.txt</a>
    </td>
  </tr>
  
</table>
<div>
  <a href="${requestContext.contextPath}/message/outbox/1">返回</a>
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

