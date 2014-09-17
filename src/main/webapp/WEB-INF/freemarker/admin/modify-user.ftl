<#import "../common/custem_macro.ftl" as customMacro/>
<html>
  <#include "../include/admin-main.ftl"/>
  <body>
    <!--标题窗口-->
   <#include "../include/admin-title.ftl"/>
    <!--主窗口-->
    <div>
      <!--窗口左侧菜单-->
      <#include "../include/admin-left.ftl"/>
      <!--窗口主要部分-->
      <div style="float:left">
      <form action="${requestContext.contextPath}/admin/modify-user/${modifyUser.id}" method="POST" >
		<table width="500px" border="1">
  <tr>
    <td width="100px">会员帐号</td>
    <td>
      ${modifyUser.login}
    </td>
  </tr>
  <tr>
    <td>邀请人</td>
    <td><#if modifyUser.inviter??>${modifyUser.inviter.login}</#if></td>
  </tr>
  <tr>
    <td>创建时间</td>
    <td>${modifyUser.registered?string}</td>
  </tr>
  <tr>
    <td>会员类型</td>
    <td>
    	<@spring.bind "modifyUserFormVO.group"/>
		<select name="${spring.status.expression}">
        <@customMacro.optionTag value="MEMBER" selected=spring.status.value?default(modifyUser.group)>会员</@customMacro.optionTag>
        <@customMacro.optionTag value="DIRECTOR" selected=spring.status.value?default(modifyUser.group)>理事</@customMacro.optionTag>
        <@customMacro.optionTag value="CHAIRMAN" selected=spring.status.value?default(modifyUser.group)>会长</@customMacro.optionTag>
        <@customMacro.optionTag value="ADMIN" selected=spring.status.value?default(modifyUser.group)>管理员</@customMacro.optionTag>
		</select>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
    </td>
  </tr>
  <tr>
    <td>会员姓名</td>
    <td>
    	<@spring.bind "modifyUserFormVO.nicename"/>
		<input type="textfield"
       	name="${spring.status.expression}"
       	value="${spring.status.value?default(modifyUser.nicename)}"/>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
    </td>
  </tr>
  <tr>
    <td>会员电话</td>
    <td>
    	<@spring.bind "modifyUserFormVO.phone"/>
    	<input type="textfield"
		name="${spring.status.expression}"
		value="${spring.status.value?default(modifyUser.phone)}"/>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
    </td>
  </tr>
  <tr>
    <td>电子邮箱</td>
    <td>
    	<@spring.bind "modifyUserFormVO.email"/>
		<input type="textfield"
		name="${spring.status.expression}"
		value="${spring.status.value?default(modifyUser.email)}"/>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="submit" value="确定"/>
    </td>
</table>
</form>
	  </div>
      </div>
    </div>
  </body>
</html>
