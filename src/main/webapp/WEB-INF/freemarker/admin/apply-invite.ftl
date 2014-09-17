<#import "../common/custom_macro.ftl" as customMacro/>
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
	<form action="${requestContext.contextPath}/admin/apply-invite/${data.id}" method="POST" >
		<table width="500px" border="1">
			<tr>
    			<td width="100px">会员帐号</td>
    			<td>
    				<@spring.bind "applyInviteFormVO.login"/>
      				<input type="textfield" name="${spring.status.expression}" value="${spring.status.value?default("")}"/>
					<#list spring.status.errorMessages as error>
					<span style="color:red;">${error}</span>
					</#list>
				</td>
			</tr>
  			 <tr>
    			<td>会员密码</td>
    			<td>
    				<@spring.bind "applyInviteFormVO.password"/>
					<input type="password" name="${spring.status.expression}" value="${spring.status.value?default("123456")}"/>
					<#list spring.status.errorMessages as error>
					<span style="color:red;">${error}</span>
					</#list>
    			</td>
    	   </tr>
			<tr>
    			<td>用户类型</td>
    			<td>
    				<@spring.bind "applyInviteFormVO.group"/>
      				<select name="${spring.status.expression}">
                        <@customMacro.optionTag value="MEMBER" selected=spring.status.value?default("MEMBER")>会员</@customMacro.optionTag>
                        <@customMacro.optionTag value="DIRECTOR" selected=spring.status.value?default("MEMBER")>理事</@customMacro.optionTag>
                        <@customMacro.optionTag value="CHAIRMAN" selected=spring.status.value?default("MEMBER")>会长</@customMacro.optionTag>
                        <@customMacro.optionTag value="ADMIN" selected=spring.status.value?default("MEMBER")>管理员</@customMacro.optionTag>
					</select>
					<#list spring.status.errorMessages as error>
					<span style="color:red; ">${error}</span>
					</#list>
				</td>
			<tr>
				<td>邀请人</td>
				<td>${data.inviter.login}</td>
			</tr>
			<tr>
				<td>申请时间</td>
				<td>${data.registered?string}</td>
			</tr>
			<tr>
				<td>会员姓名</td>
				<td>
					<@spring.bind "applyInviteFormVO.nicename"/>
					<input type="textfield" name="${spring.status.expression}" value="${spring.status.value?default(data.nicename)}"/>
					<#list spring.status.errorMessages as error>
					<span style="color:red;">${error}</span>
					</#list>
				</td>
			</tr>
			<tr>
				<td>会员电话</td>
				<td>    
					<@spring.bind "applyInviteFormVO.phone"/>
					<input type="textfield" name="${spring.status.expression}" value="${spring.status.value?default(data.phone)}"/>
					<#list spring.status.errorMessages as error>
					<span style="color:red;">${error}</span>
					</#list>
				</td>
  			</tr>
			<tr>
    			<td>电子邮箱</td>
				<td>
					<@spring.bind "applyInviteFormVO.email"/>
					<input type="textfield" name="${spring.status.expression}" value="${spring.status.value?default(data.email)}"/>
					<#list spring.status.errorMessages as error>
					<span style="color:#ff0000;">${error}</span>
					</#list>
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
    </div>
  </body>
</html>

