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
      <div>
  		<a href="${requestContext.contextPath}/admin/create-content">新建</a>
  	  </div>
		<table>
  		  <tr>
    		<th>标题</th>
    		<th>来源</th>
    		<th>作者</th>
    		<th>创建时间</th>
    		<th>修改时间</th>
    		<th>操作</th>
  		  </tr>
  		  <#list contentList.list as item>
		  <tr>
			<td>${item.title}</td>
			<td>${item.source!}</td>
			<td>${item.author!}</td>
			<td>${item.created?string}</td>
			<td>${item.modified?string}</td>
			<td>
      		<a target="_blank" href="${requestContext.contextPath}/content/show-content/${item.id}">查看</a>
      		<a href="${requestContext.contextPath}/admin/modify-content/${item.id}">编辑</a>
      		<a href="${requestContext.contextPath}/admin/list-content/delete/${item.id}">删除</a>
   			</td>
  		  </tr>
  		  </#list>
		</table>
		<div>
		<#list 1..contentList.page as pg>
	  <a href="${requestContext.contextPath}/admin/list-content/${pg}">${pg}</a>
	  </#list>
		</div>
	  </div>
      </div>
    </div>
  </body>
</html>

