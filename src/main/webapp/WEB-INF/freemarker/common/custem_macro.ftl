<#macro optionTag value selected>
<#if value = selected>
<option value="${value}" selected><#nested/></option>
<#else>
<option value="${value}"><#nested/></option>
</#if>
</#macro>