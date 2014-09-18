<#import "../common/custom_macro.ftl" as customMarco/>

<#macro channelBar current>
<div class="nav">
    <ul id="menu">
    	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/index" class="${classValue}">首 页</a>
    	</@customMarco.channelTag></li>
    	
        <li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/news" class="${classValue}">新闻荟萃</a>
    	</@customMarco.channelTag></li>
    	
    	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/work" class="${classValue}">工作动态</a>
    	</@customMarco.channelTag></li>

    	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/comm" class="${classValue}">对外交流</a>
    	</@customMarco.channelTag></li>
    	
       	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/member" class="${classValue}">会员风采</a>
    	</@customMarco.channelTag></li>
       
        <li><@customMarco.channelTag 1 current "current" "" ; classValue>
    	<a href="${requestContext.contextPath}/message/inbox/1" class="${classValue}">会员服务</a>
    	</@customMarco.channelTag></li>
        
        <li><@customMarco.channelTag 2 current "current" "" ; classValue>
    	<a href="${requestContext.contextPath}/content/list/q/1" class="${classValue}">资料库</a>
    	</@customMarco.channelTag></li>
       	 
       	<li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/msg" class="${classValue}">信息链接</a>
    	</@customMarco.channelTag></li>
       
        <li><@customMarco.channelTag 0 current "current" "" ; classValue>
    	<a href="/association/diplomacy/site/conn" class="${classValue}">联系我们</a>
    	</@customMarco.channelTag></li>
    </ul>
</div>
</#macro>