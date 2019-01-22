package ${pkg}.common.dal.model;

import java.util.Date;

public class ${objName} {
<#list list as l>
	private ${l.propertyJavaType} ${l.property};//${l.comment}
</#list>

<#list list as l>
${l.getMethod}
${l.setMethod}
</#list>

}