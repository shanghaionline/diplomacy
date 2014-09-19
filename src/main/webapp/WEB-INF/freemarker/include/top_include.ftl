<#import "../common/custom_macro.ftl" as customMarco/>

<#macro channelBar current>
<div class="nav">
    <ul id="menu">
    	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/" class="${classValue}">首 页</a>
    	</@customMarco.channelTag></li>
    	
        <li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/node_73615.htm" class="${classValue}">新闻荟萃</a>
    	</@customMarco.channelTag></li>
    	
    	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/node_73618.htm" class="${classValue}">工作动态</a>
    	</@customMarco.channelTag></li>

    	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/node_74056.htm" class="${classValue}">对外交流</a>
    	</@customMarco.channelTag></li>
    	
       	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/node_81994.htm" class="${classValue}">会员风采</a>
    	</@customMarco.channelTag></li>
       
        <li><@customMarco.channelTag 1 current "current" "" ; classValue>
    	<a href="${requestContext.contextPath}/message/inbox/1" class="${classValue}">会员服务</a>
    	</@customMarco.channelTag></li>
        
        <li><@customMarco.channelTag 2 current "current" "" ; classValue>
    	<a href="${requestContext.contextPath}/content/list/q/1" class="${classValue}">资料库</a>
    	</@customMarco.channelTag></li>
       	 
       	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/content/2011-11/30/content_4986460.htm" class="${classValue}">信息链接</a>
    	</@customMarco.channelTag></li>
       
        <li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="http://www.spda.org.cn/content/2012-08/24/content_5525441.htm" class="${classValue}">联系我们</a>
    	</@customMarco.channelTag></li>
    </ul>
</div>
</#macro>