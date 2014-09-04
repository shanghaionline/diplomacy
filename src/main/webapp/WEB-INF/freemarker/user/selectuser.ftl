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
    <input type="textfield" id="query_key" value=""/>
    <a href="#" onClick="queryContact()">查询</a>
    <table width="300px" border="1">
      <tr>
	<th>帐号名</th>
	<th>姓名</th>
	<th>操作</th>
      </tr>
      <tr>
        <td>002</td>
        <td>张治承</td> 
        <td>
          <a href="#" onClick="putContact('002')">选择</a>
        </td>
      </tr>
      <tr>
        <td>001</td>
        <td>冒霜霜</td> 
        <td>
          <a href="#" onClick="putContact('001')">选择</a>
        </td>
      </tr>
      <tr>
        <td>admin</td>
        <td>admin</td> 
        <td>
          <a href="#" onClick="putContact('admin')">选择</a>
        </td>
      </tr>
    </table>
    <a href="/association/user/list-contact/q/1">1</a>
  </body>
</html>
