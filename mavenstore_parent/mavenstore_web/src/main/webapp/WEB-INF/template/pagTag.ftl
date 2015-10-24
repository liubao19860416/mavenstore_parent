<#--分页工具条升级-->
<div align="right">
	<#-- 分页部分文字信息 --> 
	<span>总记录数 ： ${totalCount}条</span>
	<span>每页记录数： ${numberPerPage}条</span>
	<span>共${totalPageCount}页</span>
	<span>当前第${pageNum}页</span>
	<#-- 首页 上一页-->
	<#if pageNum==1>
		<#-- 当前页是第一页 -->
		<a>首页</a><a>上一页</a>
	<#else>
		<#-- 当前页不是第一页 -->
		<a href="${contextPath}/${pageQueryUrl}?pageNum=1&${paramUrl}">首页</a>	
		<a href="${contextPath}/${pageQueryUrl}?pageNum=${pageNum-1}&${paramUrl}">上一页</a>	
	</#if>
	
		
	<#list begin..end as i>
		<#if pageNum==i>
			<#-- 当前页码  -->
			<a>[ ${i} ]</a>
		<#else>
			<#-- 不是当前页码-->
			<a href="${contextPath}/${pageQueryUrl}?pageNum=${i}&${paramUrl}">[ ${i} ]</a>	
		</#if>
	</#list>
	
	
	
	<#-- 下一页 尾页 -->
	<#if pageNum==totalPageCount>
		<#--当前页已经是最后一页 -->
		<a>下一页</a><a>尾页</a>
	<#else>
		<#-- 不是最后一页 -->
		<a href="${contextPath}/${pageQueryUrl}?pageNum=${pageNum+1}&${paramUrl}">下一页</a>	
		<a href="${contextPath}/${pageQueryUrl}?pageNum=${totalPageCount}&${paramUrl}">尾页</a>
	</#if>	
	
	<#-- 跳转 -->
	<input type="text" size="2" name="selectedPageNum2"/>
	<input type="button" value="go" id="selectedPageNum1" size="2" />
	<input type="hidden" value="${paramUrl}" name="selectedPageNum1" id="paramUrl" />
	<input type="hidden" value="${totalPageCount}" id="totalPageCount" />
	
</div>