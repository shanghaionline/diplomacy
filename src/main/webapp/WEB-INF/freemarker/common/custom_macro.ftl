<#macro optionTag value selected>
<#if value = selected>
<option value="${value}" selected><#nested/></option>
<#else>
<option value="${value}"><#nested/></option>
</#if>
</#macro>

<#macro hasPerm user perm>
    <#if user.metas[perm].value = "PERM_ENABLED">
        <#nested/>
    </#if>
</#macro>

<#macro groupName group>
    <#switch group>
    <#case "ADMIN">管理员<#break>
    <#case "DIRECTOR">理事<#break>
    <#case "MEMBER">会员<#break>
    <#case "CHAIRMAN">会长<#break>
    </#switch>
</#macro>