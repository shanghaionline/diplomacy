<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 收件箱</title>
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
<div class="sendmsg">
  <a href="${requestContext.contextPath}/message/sendmessage">
    <img src="${requestContext.contextPath}/static/images/btn_sendmsg.gif"
	 alt="发送新消息"/>
  </a>
</div>
<div class="mailbox">
  <ul id="a2">
    <li class="style1">
      <a href="${requestContext.contextPath}/message/inbox">收件箱</a>
    </li>
    <li class="style2">
      <a href="${requestContext.contextPath}/message/outbox">发件箱</a>
    </li>
  </ul>
  <div id="b2">
    <div class="maillist">
       

<form action="/association/msg/delete-send" method="POST" >
    
     <table>
	<tr>
	  <th class="tcheck"></th>
	  <th>标题</th>
	  <th class="tsource">发件人</th>
	  <th class="ttime">发送时间</th>
	</tr>
	
	<tr>
	  <td colspan="4" class="access">
	    <input type="submit" id="button" class="btn_delete" value=""/>
	  </td>
	</tr>
	<tr>
	  <td colspan="4" class="pswitch">
	    
	  </td>
	</tr>
      </table>
      
</form>

    </div>
  </div>
</div>
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

