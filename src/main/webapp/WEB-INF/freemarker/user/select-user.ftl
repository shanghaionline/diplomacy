<html>
  <head>
    <link rel="stylesheet" type="text/css"
	  href="${requestContext.contextPath}/static/stylesheets/sys.css"/>
    <script type="text/javascript" 
	    src="${requestContext.contextPath}/static/javascripts/jquery-1.9.0.min.js">
    </script>
    <script>
      function queryContact() {
        var url = "/association/user/list-contact/q%23/1";
        var v = document.getElementById("query_key").value;
        window.location.href = url.replace("%23", v);
      }
      function putContact(contact) {
        var $doc = $(window.opener.document);
        $doc.find("#message_receiver_value").val(contact);
        $doc.find("#group_id_select").val("");
        window.close();
      }
    </script>
  </head>
  <body>
    <input type="textfield" id="query" value="${query}"/>
    <a href="#" onClick="queryContact()">查询</a>
    <table width="300px" border="1">
      <tr>
	<th>帐号名</th>
	<th>姓名</th>
	<th>操作</th>
      </tr>
      <#list userList.list as item>
      <tr>
        <td>${item.login}</td>
        <td>${item.nicename}</td> 
        <td>
          <a href="#" onClick="putContact('${item.login}')">选择</a>
        </td>
      </tr>
      </#list>
    </table>
    <#list 1..userList.page as pg>
    <a href="${requestContext.contextPath}/user/select-user/q${query}/${pageNum}">${pg}</a>
    </#list>
  </body>
</html>
