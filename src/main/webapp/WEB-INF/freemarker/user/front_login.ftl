<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" 
	  href="${requestContext.contextPath}/static/stylesheets/style.css" 
	  type="text/css"/>
    <title>用户登录</title>
  </head>
  <body style="padding:0;margin:0;">
  	<#if !user??>
  
  
  
    <div class="member">
      <h1>会员服务</h1>

<form action="${requestContext.contextPath}/user/front_login" method="POST" >
      <table>
	<tr>
	  <th style="font-size:12px;">用户名</th>
	  <td>
	    <@spring.bind "loginFormVO.username"/>
	    <input style="height:20px;" class="ipt_1" type="textfield" 
		   name="${spring.status.expression}"
		   value="${spring.status.value?default("")}"/>
		<#list spring.status.errorMessages as error>
        <span>${error}</span>
        </#list>
	  </td>
	</tr>
	<tr>
	  <th style="font-size:12px;">密码</th>
	  <td>
	  	<@spring.bind "loginFormVO.password"/>
	    <input style="height:20px;" class="ipt_1" type="password"
		   name="${spring.status.expression}" value=""/>
		<#list spring.status.errorMessages as error>
        <span>${error}</span>
        </#list>
	  </td>
	</tr>
	<tr>
	  <th></th>
	  <td>
	    <input style="float:right;cursor:pointer;" 
		   class="btn_1"
		   type="submit"
		   value=""/>
	  </td>
	</tr>
	<tr>
	  <td></td>
	  <td style="font-size:12px">
	    <span style="color:red;"></span>
	  </td>
	</tr>
      </table>
</form>
    </div>
    <#else>
    <div class="member">
      <h1>会员服务</h1>
      
      <table align="center" style="font-size:12px;">
	<tr>&nbsp;</tr>
	<tr align="center">
	  <th>用户${user.login}, 欢迎您登录!</th>
	</tr>
	<tr align="right">
	  <th>
	    <a href="#" onClick="window.parent.location.href='${requestContext.contextPath}/user/logout'">
	      退出
	    </a>
	  </th>
	</tr>
      </table>
      
    </div>
    </#if>
  </body>
</html>
