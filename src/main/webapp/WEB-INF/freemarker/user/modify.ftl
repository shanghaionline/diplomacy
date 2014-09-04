<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 修改用户信息</title>
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
<form action="/association/user/edit-info" method="POST" >
  <table width="100%" border="0" cellpadding="2">
    <tr>
      <td width="35%"></td>
      <td width="12%" align="left">会员帐号:</td>
      <td align="left">
		${user.login}
      </td>
    </tr>
    <tr>
      <td width="10%"></td>
      <td align="left">会员姓名:</td>
      <td>
	<input type="textfield" id="name" name="realname"
	       value="${user.nicename}" class="ipt_1"/>
	<span></span>
      </td>
    </tr>
    <tr>
      <td width="10%"></td>
      <td align="left">会员手机:</td>
      <td>
	<input type="textfield" id="name" name="phone"
	       value="${user.phone}" class="ipt_1"/>
	<span></span>
      </td>
    </tr>
    <tr>
      <td width="10%"></td>
      <td align="left">邮箱地址:</td>
      <td>
	<input type="textfield" id="name" name="email"
	       value="${user.email}" class="ipt_1"/>
	<span></span>
      </td>
    </tr>
    <tr>
      <td colspan="3">
	<a href="${requestContext.contextPath}/user/modifypwd">修改密码</a>&nbsp;
	<a href="${requestContext.contextPath}/user/modifyphone">修改手机</a>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center">
	<input type="submit" id="add" name="add" value="确定"/>
      </td>
    </tr>
  </table>
</form>
</div>
<div class="login_right">
  <div class="user_info">
    <p>用户${user.login}，欢迎您登录</p>
    <input type="submit" 
	   name="button" 
	   id="button" 
	   value="退出" 
	   class="btn_logout"
	   onClick="window.location.href='/association/mangr/logout?back=%2Fassociation%2Fdiplomacy%2Fsite%2Findex'"/>
  </div>
 <#include "../include/right_include.ftl">
</div>
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


