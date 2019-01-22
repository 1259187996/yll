<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${pkg}.mapper.${objName}Mapper">
<!-- 
	<cache type="org.mybatis.caches.ehcache.LoggingEhcache" readOnly="true"/>
 -->
	<resultMap id="BaseResultMap" type="${pkg}.model.${objName}" >
		<id column="${pkColumn}" property="${pkProperty}"  />
	<#list list as l>
	    <#if pkColumn!=l.column>
	    <result column="${l.column}" property="${l.property}"  />
	    </#if>
	</#list>

    </resultMap>
	
	<sql id="Base_Column_List">
		<#list list as l>
    		${l.column} <#if l_has_next>,</#if>
    	</#list>
	</sql>
	
	
	<sql id="Base_Where_Clause">
		<trim prefix="where" prefixOverrides="AND ">
			<#list list as l>
	    		<if test="${l.property} != null and ${l.property} !='' "> and ${l.column} = ${"#"}{${l.property}}</if>
	    	</#list>
		</trim>
		<if test="sorting != null">order by  ${"$"}${"{sorting}"}</if>
		<if test="offset != null and limit != null">
			limit ${"#"}{offset}, ${"#"}{limit}
		</if>
	</sql>
	
	<sql id="Base_Where_Count">
		<trim prefix="where" prefixOverrides="AND ">
			<#list list as l>
	    		<if test="${l.property} != null and ${l.property} !='' "> and ${l.column} = ${"#"}{${l.property}}</if>
	    	</#list>
		</trim>
	</sql>
	
	<!-- 查询总数 -->
	<select id="selectCount" resultType="java.lang.Long" parameterType="java.util.Map">
		select count(${pkColumn})
		from ${tableName}
		<include refid="Base_Where_Count" />
	</select>
	
	<!-- 查询 -->
	<select id="select" resultMap="BaseResultMap" parameterType="java.util.Map">
		select
		<include refid="Base_Column_List" />
		from ${tableName}
		<include refid="Base_Where_Clause" />
	</select>
	
	<!-- 根据ID查询 -->
	<select id="selectById" resultMap="BaseResultMap" parameterType="java.lang.String">
		select
		<include refid="Base_Column_List" />
		from ${tableName}
		where ${pkColumn} = ${"#"}{_parameter}
	</select>
	
	<!-- 根据ID删除 -->
	<delete id="deleteById" parameterType="java.lang.String">
		delete from ${tableName}
		where ${pkColumn} = ${"#"}{_parameter}
	</delete>
	
	<!-- 删除 -->
	<delete id="delete" parameterType="java.util.Map">
		delete from ${tableName}
		<include refid="Base_Where_Clause" />
	</delete>
	
	<!-- 添加 -->	
	<insert id="insert" parameterType="${pkg}.model.${objName}">
		insert into ${tableName}(
			<#list list as l>
    		${l.column}<#if l_has_next>,</#if>
	    	</#list>
		)
		values (
			<#list list as l>
    		${"#"}{${l.property}}<#if l_has_next>,</#if>
	    	</#list>
		)
	</insert>
		
	
	<!-- 通过ID更新 -->
	<update id="updateByIdSelective" parameterType="${pkg}.model.${objName}">
		update ${tableName}
		<trim prefix="SET" prefixOverrides=", ">
			<#list list as l>
	    	<if test="${l.property} != null"> , ${l.column} = ${"#"}{${l.property}}</if>
	    	</#list>
		</trim>
		where ${pkColumn} = ${"#"}{${pkProperty}}
	</update>
	
	<update id="updateById" parameterType="${pkg}.model.${objName}">
		update ${tableName}
		<trim prefix="SET" prefixOverrides=", "> 
		<#list list as l>
    		${l.column} = ${"#"}{${l.property}}<#if l_has_next>,</#if>
    	</#list>
    	</trim>
		where ${pkColumn} = ${"#"}{${pkProperty}}
	</update>
</mapper>