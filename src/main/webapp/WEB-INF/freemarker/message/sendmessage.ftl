<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会" />
    <title>上海公共外交协会 发送短信</title>
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
<script>
function show_contact_list() {
  open("/association/user/list-contact/q/1","",
    "resizable=1,scrollbars=1,status=no,toolbar=no,menu=no,width=500,height=400,left=150,top=50");
}
$(document).ready(function() {
  $("#group_id_select").bind("change", function() {
    var $v = $("#group_id_select option:selected")
    if ($v.val() == "") {
      $("#message_receiver_value").val("")
    } else {
      $("#message_receiver_value").val($v.text())
    }
  })
});
</script>

 

<form action="/association/msg/send-message" method="POST" enctype="multipart/form-data" id="fm">
<table width="100%" board="0">
  <tr>
    <td width="15%">收件人:</td>
    <td align="left">
      <input type="textfield" id="message_receiver_value"
	     class="ipt_1" 
	     name="receiver"
	     value=""/>
      <input type="button" value="选择" onClick="show_contact_list()"/>
      <span></span>
    </td>
  </tr>
  
  <tr>
    <td>&nbsp;</td>
    <td align="left">
      <select id="group_id_select" name="groupid">
	<option value="">单会员</option><option value="1">会员</option><option value="2">理事</option><option value="3">会长</option>
      </select>
      <span></span>
    </td>
  </tr>
  
  <tr>
    <td>标&nbsp;&nbsp;题:</td>
    <td align="left">
      <input type="textfield" 
	     class="ipt_1"
	     name="title"
	     value=""/>
      <span></span>
    </td>
  </tr>
  <tr>
    <td>内&nbsp;&nbsp;容</td>
    <td align="left">
      <textarea name="content"
		rows="10" cols="50"></textarea>
      <span></span>
    </td>
  </tr>
  <tr>
    <td>附&nbsp;&nbsp;件</td>
    <td align="left">
      <input type="file" name="attach"/>
    </td>
  <tr>
    <td colspan="2" align="center">
      <br/>
      <input type="submit" value="发送短信"/>
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
  <div class="interactive">
    <#include "../include/right_include.ftl">
  </div>
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


