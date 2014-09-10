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
	  <table border="1" width="100%">
  			<tr>
    			<th>会员帐号</th>
    			<th>帐号类型</th>
    			<th>会员姓名</th>
    			<th>会员电话</th>
    			<th>会员邮箱</th>
   				<th>创建时间</th>
    			<th>操作</th>
  			</tr>
  			<#list userList.list as item>
  			<tr>
    			<td>${item.login}</td>
    			<td>${item.group}</td>
    			<td>${item.nicename}</td>
    			<td>${item.phone}</td>
    			<td>${item.email}</td>
    			<td>${item.registered?string}</td>
    			<td>
      				<a href="/association/mangr/user/show-member/10">查看</a>
      				<a href="/association/mangr/user/edit-member/10">编辑</a>
      				<a href="/association/mangr/user/delete-member/10">删除</a>
    			</td>
  			</tr>
  			</#list>
  	  </table>
  	  <#list 1..userList.page as pg>
	  <a href="${requestContext.contextPath}/admin/list-user/${pg}">${pg}</a>
	  </#list>
      </div>
    </div>
  </body>
</html>

