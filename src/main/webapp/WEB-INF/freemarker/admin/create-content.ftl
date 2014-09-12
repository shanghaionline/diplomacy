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
      	<script>
  var editor;
  KindEditor.ready(function(K) {
  editor = K.create('#editor_id',{
  items:[
  'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'cut', 'copy', 'paste',
  'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
  'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
  'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
  'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
  'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
  'table', 'hr', 'emoticons', 'pagebreak',
  'link', 'unlink', '|', 'about'
  ]}
  );
  });
</script>
	<form action="${requestContext.contextPath}/admin/create-content" method="POST" >
	<div>
  		标题:
  		<@spring.bind "contentFormVO.title"/>
		<input type="textfield"
       	name="${spring.status.expression}"
       	value="${spring.status.value?default("")}"/>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
	</div>
	<div>
  		来源:
  		<@spring.bind "contentFormVO.source"/>
		<input type="textfield"
        name="${spring.status.expression}"
        value="${spring.status.value?default("")}"/>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
		 作者:
		 <@spring.bind "contentFormVO.author"/>
		<input type="textfield"
        name="${spring.status.expression}"
        value="${spring.status.value?default("")}"/>
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
	</div>

		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
		<@spring.bind "contentFormVO.content"/>
		<textarea style="width:700px;height:300px;"
	  	name="${spring.status.expression}"
	  	id="editor_id"></textarea>

摘要:
<div>
  
		<#list spring.status.errorMessages as error>
		<span style="color:red;">${error}</span>
		</#list>
		<@spring.bind "contentFormVO.summary"/>
		<textarea style="width:700px;height:100px;"
	  	name="${spring.status.expression}"></textarea>
	</div>
<input type="submit" value="确定"/>
</form>
	  </div>
      </div>
    </div>
  </body>
</html>

