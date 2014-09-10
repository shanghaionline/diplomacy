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
    		<th>申请人</th>
   			<th>邮箱</th>
    		<th>电话</th>
    		<th>推荐帐号</th>
    		<th>申请时间</th>
    		<th>操作</th>
  		</tr>
  	   <#list userList.list as item>
  		<tr>
    		<td>${item.nicename}</td>
    		<td>${item.email}</td>
    		<td>${item.phone}</td>
    		<td>${item.inviter.login}</td>
    		<td>${item.registered?string}</td>
    		<td>
      		<a href="/association/mangr/user/apply-invita/14">通过</a>
      		<a href="${requestContext.contextPath}/admin/handle-invite/delete/${item.id}">拒绝</a>
    		</td>
  		</tr>
       </#list>
  	  </table>
  	  <#list 1..userList.page as pg>
	  <a href="${requestContext.contextPath}/admin/handle-invite/${pg}">${pg}</a>
	  </#list>
      </div>
    </div>
  </body>
</html>


