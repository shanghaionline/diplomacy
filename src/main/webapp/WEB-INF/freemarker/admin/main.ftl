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
      <div style="background:url(${requestContext.contextPath}/static/images/welcome_pic.gif) no-repeat 220px 80px; width:800px;height:600px">
	  </div>
      </div>
    </div>
  </body>
</html>

