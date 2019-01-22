package com.yzz.lr.codegen;

public class FiledEntity {
	String property; //属性名，用于mapper文件和实体中的属性名 
	String column;   //列名
	String jdbcType; //mapper文件对应的类型
	String propertyJavaType;//用于生成实体java类型
	String getMethod; //用于生成实体中get方法
	String setMethod; //用于生成实体中set方法
	String comment;  //字段注释用于生成注释
	
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	public String getPropertyJavaType() {
		return propertyJavaType;
	}
	public void setPropertyJavaType(String propertyJavaType) {
		this.propertyJavaType = propertyJavaType;
	}
	public String getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	public String getSetMethod() {
		return setMethod;
	}
	public void setSetMethod(String setMethod) {
		this.setMethod = setMethod;
	}
	
}
