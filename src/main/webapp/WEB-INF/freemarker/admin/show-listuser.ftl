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
      <table width="500px" border="1">
  <tr>
    <td width="100px">
     	 会员帐号
    </td>
    <td>
      ${listUser.login}
    </td>
  </tr>
  <tr>
    <td>会员类型</td>
    <td>
     ${listUser.group}
    </td>
  </tr>
  <tr>
    <td>邀请人</td>
    <td><#if listUser.inviter??>${listUser.inviter.login}</#if></td>
  </tr>
  <tr>
    <td>创建时间</td>
    <td>${listUser.registered?string}</td>
  </tr>
  <tr>
    <td>会员姓名</td>
    <td>${listUser.nicename}</td>
  </tr>
  <tr>
    <td>会员电话</td>
    <td>${listUser.phone}</td>
  </tr>
  <tr>
    <td>电子邮箱</td>
    <td>${listUser.email}</td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      &nbsp;
    </td>
  </tr>
</table>
	  </div>
      </div>
    </div>
  </body>
</html>