<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 邀请注册</title>
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
	<#include "../include/top_include.ftl">
	<div class="content">
	  <div class="main">
	  <form method="POST" >
	  <table width="30%" border="0" align="center">
	  <tr>
	  	<td width="50%" align="left">姓&nbsp;&nbsp;名:</td>
	  	<td align="left">
	  		<input type="textfield" id="name" name="nicename" value="${nicename!}" class="ipt_1"/>
	  		<span></span>
	  	</td>
	  </tr>
	  <tr>
	  	<td width="50%" align="left">手机号码:</td>
	  	<td align="left">
	  		<input type="textfield" id="name" name="phone" value="${phone!}" class="ipt_1"/><input type="button" value="发送验证码"/>
      	<span></span>
     	</td>
      </tr>
      <tr>
	  	<td width="50%" align="left">验证码:</td>
	  	<td align="left">
	  		<input type="textfield" id="name" name="code" value="${code!}" class="ipt_1"/>
	  		<span>${errCodeMsg!}</span>
	  	</td>
	  </tr>
      <tr>
      	<td width="50%" align="left">email:</td>
      	<td align="left">
      		<input type="textfield" id="name" name="email" value="${email!}" class="ipt_1"/>
      	<span></span>
      	</td>
      </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="submit" value="确定"/>
    </td>
  </tr>
</table>
</form>
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
